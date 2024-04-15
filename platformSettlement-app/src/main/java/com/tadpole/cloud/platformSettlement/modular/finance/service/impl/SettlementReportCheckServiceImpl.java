package com.tadpole.cloud.platformSettlement.modular.finance.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.stylefeng.guns.cloud.auth.api.model.LoginUser;
import cn.stylefeng.guns.cloud.libs.context.SpringContext;
import cn.stylefeng.guns.cloud.libs.context.auth.LoginContext;
import cn.stylefeng.guns.cloud.model.page.PageResult;
import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import cn.stylefeng.guns.cloud.system.core.dbs.annotation.DataSource;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tadpole.cloud.platformSettlement.api.finance.entity.*;
import com.tadpole.cloud.platformSettlement.api.finance.model.params.SettlementReportCheckParam;
import com.tadpole.cloud.platformSettlement.api.finance.model.result.SettlementReportCheckResult;
import com.tadpole.cloud.platformSettlement.modular.finance.enums.FinanceReportTypes;
import com.tadpole.cloud.platformSettlement.modular.finance.mapper.SettlementReportCheckMapper;
import com.tadpole.cloud.platformSettlement.modular.finance.service.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.*;

/**
* <p>
* AZ结算报告审核 服务实现类
* </p>
*
* @author gal
* @since 2021-10-25
*/
@Slf4j
@Service
public class SettlementReportCheckServiceImpl extends ServiceImpl<SettlementReportCheckMapper, SettlementReportCheck> implements ISettlementReportCheckService {

    @Autowired
    private IStatementIncomeService incomeService;

    @Autowired
    private IPaymentConfirmHandlingService handlingService ;

    @Autowired
    private ISettlementService settlementService ;

    @Autowired
    private IDatarangeService datarangeService ;

    @Autowired
    private IDatarangeDetailComfirmService datarangeDetailComfirmService;

    @Autowired
    private ISettlementDetailService settlementDetailService;

    @Autowired
    private IReportUploadRecordService reportUploadRecordService;

    @DataSource(name = "finance")
    @Override
    public PageResult<SettlementReportCheckResult> findPageBySpec(SettlementReportCheckParam param) {
        IPage<SettlementReportCheckResult> page = this.baseMapper.findPageBySpec(param.getPageContext(), param);
        return new PageResult<>(page);
    }

    /**
     * AZ结算报告审核逻辑：确认（Settlement），预估（Datarange）
     * 1、补全数据且更新【AZ结算报告审核表】状态为1：已审核；
     * 2、将结算报告根据逻辑拆分到收入记录表（不涉及金额拆分）：
     *  2-1、判断【收入记录表】是否有【确认】和【预估】数据：
     *  2-2、不存在【确认】数据，同时也不存在【预估】数据，则根据审核数据的开始时间和结束时间是否存在跨月情况，
     *      存在跨月则拆分数据，拆分为1条收入记录表汇总数据和对应跨月的收入记录表明细数据，不存在跨月则生成1条收入记录表汇总数据；
     *  2-3、不存在【确认】数据，但存在【预估】数据，此次审核为【确认】数据，则根据审核数据的开始时间和结束时间是否存在跨月情况，
     *      存在跨月则拆分数据，拆分为1条收入记录表汇总数据和对应跨月的收入记录表明细数据，不存在跨月则生成1条收入记录表汇总数据，
     *      同时对已确认的【预估】数据生成取反冲回数据入库，对未确认的预估数据作删除；
     *  2-4、不存在【确认】数据，但存在【预估】数据，此次审核为【预估】，则根据审核数据的开始时间和结束时间是否存在跨月情况，
     *      存在跨月则拆分数据，拆分为1条收入记录表汇总数据和（收入记录表没有对应会计期间的数据）对应跨月的收入记录表明细数据，不存在跨月则生成1条收入记录表汇总数据（收入记录表没有对应会计期间的数据），
     *
     * 3、将报告类型为Settlement入库【回款确认办理表】；
     * 4、更新对应的【Settlement和Datarange汇总表】状态为1：已审核。
     *
     * @param param
     * @throws Exception
     */
    @DataSource(name = "finance")
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void verify(SettlementReportCheckParam param) throws Exception {
        QueryWrapper<StatementIncome> queryWrapper = new QueryWrapper<>();

        LoginContext current = SpringContext.getBean(LoginContext.class);
        LoginUser currentUser = current.getLoginUser();

        //审核的记录
        SettlementReportCheck check = this.baseMapper.selectById(param.getId());
        if(check.getStatus() != 0){
            throw new Exception("数据不能被审核！");
        }

        //更新审核表为已审核
        if(check.getProceedsCurrency()==null){
            check.setProceedsCurrency(param.getProceedsCurrency());
        }
        if(check.getRemittanceAccount()==null){
            check.setRemittanceAccount(param.getRemittanceAccount());
        }
        if(check.getRemittanceBank()==null){
            check.setRemittanceBank(param.getRemittanceBank());
        }
        //原币和收款币种相同且收款金额为空的，则收款金额为totalMoney
        if(check.getOriginalCurrency().equals(check.getProceedsCurrency())
                && check.getRemittanceMoney()==null){
            check.setRemittanceMoney(check.getTotalMoney());
        }
        //原币金额为负数，收款金额为零
        if(check.getMoney().compareTo(BigDecimal.ZERO)==-1)
        {
            check.setRemittanceMoney(BigDecimal.ZERO);
        }
        check.setStatus(1);
        check.setVerifyBy(currentUser.getName());
        this.updateById(check);

        //补充源报告站点为空的站点
        //Settlement
        if(check.getReportType().equals("Settlement")){
            settlementDetailService.updateSite(check);
        }else {
        //Data Range
            datarangeDetailComfirmService.updateSite(check);
        }

        Date startTime = check.getStartTime();
        Date endTime = check.getEndTime();

        //跨月拆分
        List<Map> list = this.getOverMoth(startTime, endTime);
        List<StatementIncome> exsitList;      //已存在预估数据集合
        List<StatementIncome> settlementList; //已存在确认数据集合

        //预估数据
        queryWrapper.select().eq("PLATFORM_NAME", check.getPlatformName())
            .eq("SHOP_NAME", check.getShopName())
            .eq("SITE", check.getSite())
            .eq("INCOME_TYPE", "预估")
            .eq("SETTLEMENT_ID", check.getSettlementId())
            .ne("SYNC_STATUS", 4);//状态为4的不用冲回
        exsitList = incomeService.selectList(queryWrapper);
        queryWrapper.clear();

        //确认数据
        queryWrapper.select().eq("PLATFORM_NAME", check.getPlatformName())
            .eq("SHOP_NAME", check.getShopName())
            .eq("SITE", check.getSite())
            .eq("REPORT_TYPE", "Settlement")
            .eq("SETTLEMENT_ID", check.getSettlementId());
        settlementList = incomeService.selectList(queryWrapper);

        //不存在确认数据
        if (settlementList.size() == 0) {
            //不存在预估数据（即不存在确认也不存在预估数据，不用做冲回，直接入库）
            if (exsitList.size() == 0) {
                //插入收入记录表
                int i = 0;
                //存在跨月
                if (list.size() > 1) {
                    //插入汇总数据
                    StatementIncome income = new StatementIncome();
                    income.setPlatformName(check.getPlatformName());
                    income.setShopName(check.getShopName());
                    income.setSite(check.getSite());
                    income.setSettlementId(check.getSettlementId());
                    income.setReportType(check.getReportType());
                    income.setBillType(check.getBillType());
                    income.setStartTime(startTime);
                    income.setEndTime(endTime);
                    income.setOriginalCurrency(check.getOriginalCurrency());
                    income.setSettlementOrder(i);
                    income.setSyncStatus(4);
                    income.setCreateAt(DateUtil.date());
                    income.setCreateBy(currentUser.getName());
                    if (check.getReportType().equals("Settlement")) {
                        income.setIncomeType("确认");
                    } else {
                        income.setIncomeType("预估");
                    }
                    incomeService.save(income);
                    i++;
                }
                //插入拆分数据 存在跨月是即拆分数据，不存在则为主数据
                for (Map map : list) {
                    StatementIncome income = new StatementIncome();
                    income.setPlatformName(check.getPlatformName());
                    income.setShopName(check.getShopName());
                    income.setSite(check.getSite());
                    income.setSettlementId(check.getSettlementId());
                    income.setReportType(check.getReportType());
                    income.setBillType(check.getBillType());
                    income.setStartTime((Date) map.get("start"));
                    income.setEndTime((Date) map.get("end"));
                    income.setOriginalCurrency(check.getOriginalCurrency());
                    income.setFiscalPeriod(map.get("period").toString());
                    income.setSettlementOrder(i);
                    income.setSyncStatus(0);
                    income.setCreateAt(DateUtil.date());
                    income.setCreateBy(currentUser.getName());
                    if (check.getReportType().equals("Settlement")) {
                        income.setIncomeType("确认");
                    } else {
                        income.setIncomeType("预估");
                    }

                    i++;
                    incomeService.save(income);
                }
            } else {
                //存在预估数据,此审核数据为Settlement,则预估冲回
                if (check.getReportType().equals("Settlement")) {
                    //插入收入记录表
                    int i = 0;
                    //存在跨月
                    if (list.size() > 1) {
                        //插入汇总数据
                        StatementIncome income = new StatementIncome();
                        income.setPlatformName(check.getPlatformName());
                        income.setShopName(check.getShopName());
                        income.setSite(check.getSite());
                        income.setSettlementId(check.getSettlementId());
                        income.setReportType(check.getReportType());
                        income.setBillType(check.getBillType());
                        income.setStartTime(startTime);
                        income.setEndTime(endTime);
                        income.setOriginalCurrency(check.getOriginalCurrency());
                        income.setSettlementOrder(i);
                        income.setSyncStatus(4);
                        income.setIncomeType("确认");
                        income.setCreateAt(DateUtil.date());
                        income.setCreateBy(currentUser.getName());
                        incomeService.save(income);
                        i++;
                    }
                    //插入拆分数据 存在跨月是即拆分数据，不存在则为主数据
                    for (Map map : list) {
                        StatementIncome income = new StatementIncome();
                        income.setPlatformName(check.getPlatformName());
                        income.setShopName(check.getShopName());
                        income.setSite(check.getSite());
                        income.setSettlementId(check.getSettlementId());
                        income.setReportType(check.getReportType());
                        income.setBillType(check.getBillType());
                        income.setStartTime((Date) map.get("start"));
                        income.setEndTime((Date) map.get("end"));
                        income.setOriginalCurrency(check.getOriginalCurrency());
                        income.setFiscalPeriod(map.get("period").toString());
                        income.setSettlementOrder(i);
                        income.setSyncStatus(0);
                        income.setIncomeType("确认");
                        income.setCreateAt(DateUtil.date());
                        income.setCreateBy(currentUser.getName());
                        i++;
                        incomeService.save(income);
                    }
                    BigDecimal zero = BigDecimal.ZERO;
                    for (StatementIncome in : exsitList) {
                        //如果预估已确认则冲回（取反），若未确认删除预估
                        if(in.getSyncStatus()>=2){
                            //对预估数据取反，冲回
                            in.setId(null);
                            in.setIncomeType("预估冲回");
                            in.setSalesTotal(in.getSalesTotal()==null? zero:in.getSalesTotal().negate());
                            in.setSalesPromotion(in.getSalesPromotion()==null? zero:in.getSalesPromotion().negate());
                            in.setRefundTotal(in.getRefundTotal()==null? zero:in.getRefundTotal().negate());
                            in.setGiftwarpShipping(in.getGiftwarpShipping()==null? zero:in.getGiftwarpShipping().negate());
                            in.setAdvertising(in.getAdvertising()==null? zero:in.getAdvertising().negate());
                            in.setLightningDeal(in.getLightningDeal()==null? zero:in.getLightningDeal().negate());
                            in.setCommissionTotal(in.getCommissionTotal()==null? zero:in.getCommissionTotal().negate());
                            in.setAmazonFeeTotalXpt(in.getAmazonFeeTotalXpt()==null? zero:in.getAmazonFeeTotalXpt().negate());
                            in.setAmazonFeeTotal(in.getAmazonFeeTotal()==null? zero:in.getAmazonFeeTotal().negate());
                            in.setStorageFee(in.getStorageFee()==null? zero:in.getStorageFee().negate());
                            in.setReimbursement(in.getReimbursement()==null? zero:in.getReimbursement().negate());
                            in.setOther(in.getOther()==null? zero:in.getOther().negate());
                            in.setGoodwill(in.getGoodwill()==null? zero:in.getGoodwill().negate());
                            in.setWithheldTax(in.getWithheldTax()==null? zero:in.getWithheldTax().negate());
                            in.setSuccessfulCharge(in.getSuccessfulCharge()==null? zero:in.getSuccessfulCharge().negate());
                            in.setCurrentReceivableAmount(in.getCurrentReceivableAmount()==null? zero:in.getCurrentReceivableAmount().negate());
                            in.setRealReceivableAmount(in.getRealReceivableAmount()==null? zero:in.getRealReceivableAmount().negate());
                            in.setSyncStatus(1);
                            in.setVoucherNo("");
                            in.setCreateAt(DateUtil.date());
                            in.setCreateBy(currentUser.getName());
                            incomeService.save(in);
                        }else{
                            //删除预估数据
                            incomeService.removeById(in);
                        }
                    }
                } else {
                    //存在预估数据,又提交预估数据
                    //插入收入记录表
                    int i = 0;
                    //存在跨月
                    if (list.size() > 1) {
                        //插入汇总数据
                        StatementIncome income = new StatementIncome();
                        income.setPlatformName(check.getPlatformName());
                        income.setShopName(check.getShopName());
                        income.setSite(check.getSite());
                        income.setSettlementId(check.getSettlementId());
                        income.setReportType(check.getReportType());
                        income.setBillType(check.getBillType());
                        income.setStartTime(startTime);
                        income.setEndTime(endTime);
                        income.setOriginalCurrency(check.getOriginalCurrency());
                        income.setSettlementOrder(i);
                        income.setSyncStatus(4);
                        income.setIncomeType("预估");
                        income.setCreateAt(DateUtil.date());
                        income.setCreateBy(currentUser.getName());
                        incomeService.save(income);
                        i++;
                    }
                    List<StatementIncome> fiscalPeriodRange;
                    QueryWrapper<StatementIncome> fiscalPeriodRangeWrapper = new QueryWrapper<>();

                    //插入拆分数据 存在跨月是即拆分数据，不存在则为主数据
                    for (Map map : list) {
                        fiscalPeriodRangeWrapper.select().eq("FISCAL_PERIOD", map.get("period").toString())
                                .eq("SETTLEMENT_ID", check.getSettlementId());

                        fiscalPeriodRange = incomeService.selectList(fiscalPeriodRangeWrapper);
                        //不存在本期间数据
                        if(fiscalPeriodRange.size()==0){
                            StatementIncome income = new StatementIncome();
                            income.setPlatformName(check.getPlatformName());
                            income.setShopName(check.getShopName());
                            income.setSite(check.getSite());
                            income.setSettlementId(check.getSettlementId());
                            income.setReportType(check.getReportType());
                            income.setBillType(check.getBillType());
                            income.setStartTime((Date) map.get("start"));
                            income.setEndTime((Date) map.get("end"));
                            income.setOriginalCurrency(check.getOriginalCurrency());
                            income.setFiscalPeriod(map.get("period").toString());
                            income.setSettlementOrder(i);
                            income.setSyncStatus(0);
                            income.setIncomeType("预估");
                            income.setCreateAt(DateUtil.date());
                            income.setCreateBy(currentUser.getName());
                            i++;
                            incomeService.save(income);
                        }
                    }
                }
            }
        } else {//存在确认数据
            //返回提示已存在

            //记录日志
            log.info("收入记录表已经存在【确认】数据！数据为平台[{}]，账号[{}]，站点[{}]，SettlementId[{}]", check.getPlatformName(), check.getShopName(), check.getSite(), check.getSettlementId());
        }

        //插入回款确认办理
        if (check.getReportType().equals("Settlement")) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");

            PaymentConfirmHandling confirmHandling = new PaymentConfirmHandling();
            confirmHandling.setPlatformName(check.getPlatformName());
            confirmHandling.setShopName(check.getShopName());
            confirmHandling.setSite(check.getSite());
            confirmHandling.setReportType(check.getReportType());
            confirmHandling.setBillType(check.getBillType());
            confirmHandling.setSettlementId(check.getSettlementId());
            confirmHandling.setStartTime(check.getStartTime());
            confirmHandling.setEndTime(check.getEndTime());
            confirmHandling.setOriginalCurrency(check.getOriginalCurrency());
            confirmHandling.setDepositBank(check.getRemittanceBank());
            confirmHandling.setDepositAccount(check.getRemittanceAccount());
            confirmHandling.setDepositDate(check.getRemittanceDate());
            confirmHandling.setAmount(check.getMoney());
            confirmHandling.setTotalAmount(check.getTotalMoney());
            confirmHandling.setProceedsCurrency(check.getProceedsCurrency());  //收款币种
            confirmHandling.setReceiveAmount(check.getRemittanceMoney());
            confirmHandling.setReceiveOriginalAmount(check.getMoney());
            confirmHandling.setCollectionBank(check.getRemittanceBank());
            confirmHandling.setCollectionAccount(check.getRemittanceAccount());
            confirmHandling.setReceiveDate(check.getRemittanceDate());
            confirmHandling.setFiscalPeriod(sdf.format(check.getRemittanceDate()));  //会计期间
            if (check.getTotalMoney().intValue() != 0) {
                confirmHandling.setSettlementRate(check.getRemittanceMoney().divide(check.getTotalMoney(), 6, RoundingMode.FLOOR));
            }
            confirmHandling.setSyncStatus(0);
            confirmHandling.setVerifyStatus(0);
            confirmHandling.setCreateAt(DateUtil.date());
            confirmHandling.setCreateBy(currentUser.getName());
            handlingService.save(confirmHandling);
        }

        if (check.getReportType().equals("Settlement")) {
            //更新Settlement汇总表状态为1：已审核
            UpdateWrapper<Settlement> uSettlement = new UpdateWrapper<>();
            uSettlement.eq("SETTLEMENT_ID", check.getSettlementId()).eq("SYS_SHOPS_NAME", check.getShopName())
                    .set("STATUS", 1);
            settlementService.update(null, uSettlement);
        } else {
            //更新Datarange汇总表状态为1：已审核
            UpdateWrapper<Datarange> uDatarange = new UpdateWrapper<>();
            uDatarange.eq("SETTLEMENT_ID", check.getSettlementId()).eq("SYS_SHOPS_NAME", check.getShopName())
                    .eq("STATUS", 0)
                    .set("STATUS", 1);
            datarangeService.update(null, uDatarange);
        }
    }

    @DataSource(name = "finance")
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void verifyList(List<SettlementReportCheckParam> params) throws Exception {

        for (SettlementReportCheckParam param:params){
            this.verify(param);
        }
    }

    @DataSource(name = "finance")
    @Override
    public ResponseData editSite(SettlementReportCheckParam param) {
        SettlementReportCheck reportCheck = this.baseMapper.selectById(param.getId());
        if(reportCheck == null){
            return ResponseData.error("不存在此数据，修改站点失败！");
        }
        if(reportCheck.getStatus() != 0){
            return ResponseData.error("已审核/已作废数据不能修改站点，修改站点失败！");
        }

        try{
            SettlementReportCheck updateReportCheck = new SettlementReportCheck();
            updateReportCheck.setId(reportCheck.getId());
            updateReportCheck.setSite(param.getSite());
            updateReportCheck.setUpdateAt(DateUtil.date());
            this.baseMapper.updateById(updateReportCheck);

            //如果是settlement报告，需要把源表的站点、报告上传记录站点也同步更新
            if(FinanceReportTypes.YQR.getName().equals(reportCheck.getReportType())){
                UpdateWrapper<Settlement> settlementWrapper = new UpdateWrapper<>();
                settlementWrapper.eq("SETTLEMENT_ID", reportCheck.getSettlementId())
                        .eq("STATUS", 0)
                        .set("SYS_SITE", param.getSite());
                settlementService.update(settlementWrapper);

                UpdateWrapper<SettlementDetail> settlementDetailWrapper = new UpdateWrapper<>();
                settlementDetailWrapper.eq("SETTLEMENT_ID", reportCheck.getSettlementId())
                        .set("SYS_SITE", param.getSite());
                settlementDetailService.update(settlementDetailWrapper);

                UpdateWrapper<ReportUploadRecord> uploadRecordWrapper = new UpdateWrapper<>();
                uploadRecordWrapper.eq("SETTLEMENT_ID", reportCheck.getSettlementId())
                        .eq("REPORT_TYPE", FinanceReportTypes.YQR.getName())
                        .set("SITE", param.getSite());
                reportUploadRecordService.update(uploadRecordWrapper);
            }
        }catch (Exception e){
            log.error("AZ结算对账审核修改站点异常，异常信息[{}]",e.getMessage());
            return ResponseData.error("修改站点异常，请联系管理员！");
        }
        return ResponseData.success();
    }

    @DataSource(name = "finance")
    @Override
    public void editType(SettlementReportCheckParam param) {
        UpdateWrapper<SettlementReportCheck> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("ID",param.getId())
                .eq("STATUS",0)
                .set("BILL_TYPE",param.getBillType())
                .set("UPDATE_AT", new Date());
        this.baseMapper.update(null,updateWrapper);
    }

    @DataSource(name = "finance")
    @Override
    public void editProceedsCurrency(SettlementReportCheckParam param) {
        UpdateWrapper<SettlementReportCheck> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("ID",param.getId())
                .eq("STATUS",0)
                .set("PROCEEDS_CURRENCY",param.getProceedsCurrency())
                .set("UPDATE_AT", new Date());
        this.baseMapper.update(null,updateWrapper);
    }

    @DataSource(name = "finance")
    @Override
    public void editAmount(SettlementReportCheckParam param) {
        UpdateWrapper<SettlementReportCheck> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("ID",param.getId())
                .eq("STATUS",0)
                .set("REMITTANCE_MONEY",param.getRemittanceMoney())
                .set("UPDATE_AT", new Date());
        this.baseMapper.update(null,updateWrapper);
    }

    @DataSource(name = "finance")
    @Override
    public void reject(SettlementReportCheckParam param) throws Exception {
        LoginContext current= SpringContext.getBean(LoginContext.class);
        LoginUser currentUser = current.getLoginUser();

        QueryWrapper<SettlementReportCheck> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("ID",param.getId());
        SettlementReportCheck check = this.baseMapper.selectOne(queryWrapper);

        //更新未作废状态
        if(check.getStatus()==0){
            UpdateWrapper<SettlementReportCheck> updateWrapper = new UpdateWrapper<>();
            updateWrapper.eq("ID",param.getId())
                    .set("STATUS",2)
                    .set("VERIFY_BY",currentUser.getName())
                    .set("UPDATE_AT", new Date());
            this.baseMapper.update(null,updateWrapper);
            //删除对应的Settlement/Data Range汇总和明细解析数据
            invalid(param);
        }else{
            throw new Exception("数据不能被驳回！");
        }

    }

    @DataSource(name = "finance")
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void invalid(SettlementReportCheckParam param) {
        QueryWrapper<SettlementReportCheck> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("ID",param.getId());
        SettlementReportCheck check = this.baseMapper.selectOne(queryWrapper);
        //删除对应的Settlement/Data Range汇总和明细解析数据
        if (check!=null)
        {
            param.setSettlementId(check.getSettlementId());
            param.setShopName(check.getShopName());
            param.setSite(check.getSite());
            param.setReportType(check.getReportType());
            //删除对应的Settlement/Data Range明细解析数据
            this.baseMapper.deleteDataDetail(param);

            //删除对应的Settlement/Data Range汇总解析数据
            this.baseMapper.deleteData(param);
        }
    }

    @DataSource(name = "finance")
    @Override
    public List<SettlementReportCheckResult> bankList() {
        return this.baseMapper.bankList();
    }

    public int getDaysOfMonth(Date date) {
        Calendar calendar =Calendar.getInstance();

        calendar.setTime(date);

        return calendar.getActualMaximum(Calendar.DAY_OF_MONTH);

    }

    /**
     * 跨月拆分
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return
     */
    private List<Map> getOverMoth(Date startTime, Date endTime){

        Calendar calendar= Calendar.getInstance();
        calendar.setTime(startTime);
        int startYear = calendar.get(Calendar.YEAR);

        calendar.setTime(endTime);
        int endYear = calendar.get(Calendar.YEAR);

        List<Map> list = new ArrayList<Map>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
        //相差年份
        int year = endTime.getYear()-startTime.getYear();
        if(year>0){
            list.addAll(this.getOverMoth(startTime,new Date(startYear+"/12/31 23:59:59")));
            for (int i = 1;i<year;i++){
                int ooo = startYear + i;
                list.addAll(this.getOverMoth(new Date(ooo+"/01/01"),
                        new Date(ooo+"/12/31 23:59:59")));
            }
            list.addAll(this.getOverMoth(new Date(endYear+"/01/01"),endTime));
        }else {
            //相差月份
            int moth = endTime.getMonth()-startTime.getMonth();
            Calendar cal = Calendar.getInstance();
            if(moth==0){
                Map map = new HashMap();
                map.put("start",startTime);
                map.put("end",endTime);
                map.put("period",sdf.format(startTime));
                list.add(map);
            }else{

                for (int i=0;i<=moth;i++){
                    Map map = new HashMap();
                    Date start = startTime;
                    Date end = new Date();
                    Date start_new = new Date();

                    map.put("start",startTime);
                    map.put("period",sdf.format(startTime));
                    int days = getDaysOfMonth(start);
                    if(i<moth){

                        end.setYear(start.getYear());
                        end.setMonth(start.getMonth());
                        end.setDate(days);
                        end.setHours(23);
                        end.setMinutes(59);
                        end.setSeconds(59);

                        start_new.setYear(start.getYear());
                        start_new.setMonth(start.getMonth()+1);
                        start_new.setDate(1);
                        start_new.setHours(0);
                        start_new.setMinutes(0);
                        start_new.setSeconds(0);
                        startTime = start_new;
                    }else{
                        end = endTime;
                    }

                    map.put("end",end);
                    list.add(map);
                }
            }
        }
        return list;
    }

    @DataSource(name = "finance")
    @Override
    public List<SettlementReportCheckResult> exportSettlementReportCheckList(SettlementReportCheckParam param) {
        return this.baseMapper.exportSettlementReportCheckList(param);
    }
}
