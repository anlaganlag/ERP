package com.tadpole.cloud.platformSettlement.modular.finance.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.stylefeng.guns.cloud.auth.api.model.LoginUser;
import cn.stylefeng.guns.cloud.libs.context.SpringContext;
import cn.stylefeng.guns.cloud.libs.context.auth.LoginContext;
import cn.stylefeng.guns.cloud.libs.mp.page.PageFactory;
import cn.stylefeng.guns.cloud.model.exp.ServiceException;
import cn.stylefeng.guns.cloud.model.page.PageResult;
import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import cn.stylefeng.guns.cloud.system.core.dbs.annotation.DataSource;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tadpole.cloud.platformSettlement.api.finance.entity.*;
import com.tadpole.cloud.platformSettlement.api.finance.model.params.*;
import com.tadpole.cloud.platformSettlement.api.finance.model.result.DetailResult;
import com.tadpole.cloud.platformSettlement.api.finance.model.result.SettlementDetailResult;
import com.tadpole.cloud.platformSettlement.api.finance.model.result.StatementIncomeResult;
import com.tadpole.cloud.platformSettlement.api.finance.model.result.VoucherDetailResult;
import com.tadpole.cloud.platformSettlement.modular.finance.mapper.*;
import com.tadpole.cloud.platformSettlement.modular.finance.service.*;
import com.tadpole.cloud.platformSettlement.modular.manage.consumer.SyncToErpConsumer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import javax.annotation.Resource;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
* <p>
* 收入记录表 服务实现类
* </p>
*
* @author gal
* @since 2021-10-25
*/
@Slf4j
@Service
public class StatementIncomeServiceImpl extends ServiceImpl<StatementIncomeMapper, StatementIncome> implements IStatementIncomeService {

    @Autowired
    IStatementVoucherService voucherMapperService;

    @Autowired
    IStatementVoucherDetailService statementVoucherDetailService;

    @Autowired
    ISettlementService settlementService;

    @Autowired
    private ISettlementDetailListingService settlementDetailListingService;


    @Autowired
    private ISettlementDetailUsdService settlementDetailUsdService;

    @Autowired
    ISettlementDetailService settlementDetailService;

    @Autowired
    IDatarangeService datarangeService;

    @Autowired
    IDatarangeDetailComfirmService datarangeDetailComfirmService;

    @Autowired
    IReceivableDetailService receivableDetailService;

    @Autowired
    IReceivableDetailDetailService receivableDetailDetailService;

    @Autowired
    IFixedExchangeRateService rateService;

    @Autowired
    IStatementIncomeService incomeService;

    @Autowired
    ISettlementDetailListingService detailListingService;

    @Resource
    StatementVoucherMapper statementVoucherMapper;

    @Resource
    SettlementDetailListingMapper settlementDetailListingMapper;
    @Resource
    SettlementDetailUsdMapper settlementDetailUsdMapper;

    @Autowired
    SyncToErpConsumer syncToErpConsumer;

    @Resource
    IStationManualAllocationService stationManualAllocationService;


    @Resource
    NoStationAllocationMapper noStationAllocationMapper;
    @Resource
    NoAllocationAdjustMapper noAllocationAdjustMapper;


    @Resource
    StationAutoAllocationMapper stationAutoAllocationMapper;


    @Resource
    StationManualAllocationMapper stationManualAllocationMapper;


    @Resource
    SettlementRepoertMapper settlementRepoertMapper;




    @DataSource(name = "finance")
    @Override
    public PageResult<StatementIncomeResult> findPageBySpec(StatementIncomeParam param) {
        Page pageContext = getPageContext();
        IPage<StatementIncomeResult> page = this.baseMapper.findPageBySpec(pageContext, param);
        return new PageResult<>(page);
    }

    @DataSource(name = "finance")
    @Override
    public List<StatementIncomeResult> exportStatementIncomeList(StatementIncomeParam param) {
        return this.baseMapper.exportStatementIncomeList(param);
    }

    @DataSource(name = "finance")
    @Override
    public List<StatementIncome> selectList(QueryWrapper<StatementIncome> queryWrapper) {
        return this.baseMapper.selectList(queryWrapper);
    }

    @DataSource(name = "finance")
    @Override
    public void editFiscalPeriod(StatementIncomeParam param) {

        UpdateWrapper<StatementIncome> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("ID",param.getId()).set("FISCAL_PERIOD",param.getFiscalPeriod());
        this.update(null,updateWrapper);

    }

    @DataSource(name = "finance")
    @Override
    public void syncToErp(StatementIncomeParam param) throws IOException {
        this.SyncErp(param);
    }

    @DataSource(name = "finance")
    @Override
    public ResponseData syncToErpBatch(List<StatementIncomeParam> params) throws IOException {
        LoginContext current= SpringContext.getBean(LoginContext.class);
        LoginUser currentUser = current.getLoginUser();

        JSONArray Jarr = new JSONArray();
        for (StatementIncomeParam param : params) {
            StatementVoucher vParam = new StatementVoucher();
            vParam.setRecordId(param.getId());

            StatementVoucher voucher = voucherMapperService.queryVoucher(vParam);
            List<VoucherDetailResult> detail = voucherMapperService.getSyncDetail(vParam);

            String period = voucher.getFiscalPeriod();
            String[] eriodArr = period.split("-");

            JSONObject object = new JSONObject();
            object.put("FBillNo", "SRJL-" + voucher.getFiscalPeriod() + "-1" + "-" + voucher.getVoucherId().toString());//凭证编号 不传 返回
            object.put("FAccountBookID", "003");//销售中心
            object.put("FDate", period + "-1");
            object.put("FCreatorId",currentUser.getAccount());
            object.put("FVOUCHERGROUPID", "PRE001");//凭证字 记
            object.put("FATTACHMENTS", 0); //附件数
            object.put("FYEAR", eriodArr[0]);  //会计年度
            object.put("FACCBOOKORGID", "003");//销售中心
            object.put("FISADJUSTVOUCHER", false);
            object.put("FPERIOD", eriodArr[1]);//期间
            object.put("FSourceBillKey", "78050206-2fa6-40e3-b7c8-bd608146fa38"); //业务类型 手工录入

            for (VoucherDetailResult res : detail) {
                List<Map> mList = new ArrayList<>();
                Map map = new HashMap();
                if(res.getFACCOUNTID()!=null && res.getFACCOUNTID().equals("6603.001.07")){
                    map.put("HSType","部门");
                    map.put("HSNumber","BM1");
                }else{
                    map.put("HSType","组织机构");
                    map.put("HSNumber",res.getSHOPCODE());
                }
                mList.add(map);
                res.setFHSData(mList);
            }

            object.put("FEntity", detail);

            Jarr.add(object);
            log.error(Jarr.toString());

        }
        JSONObject obj = syncToErpConsumer.voucher(Jarr);

        if (obj.getString("Code") == null || !obj.getString("Code").equals("0")) {
            return  ResponseData.error("同步erp失败！"+obj.getJSONArray("Response").getJSONObject(0).getString("SubMessage"));
        } else {
            Boolean allPass = true;
            for(int j=0;j<obj.getJSONArray("Response").size();j++){

             if(obj.getJSONArray("Response").getJSONObject(j).getString("SubCode").equals("0")){

                StatementVoucher vParam = new StatementVoucher();
                vParam.setRecordId(params.get(j).getId());
                StatementVoucher voucher = voucherMapperService.queryVoucher(vParam);

                String voucherNo = obj.getJSONArray("Response").getJSONObject(j).getString("FVOUCHERGROUPNO");
                voucher.setVoucherNumber(voucherNo);
                voucher.setSyncStatus((int)1);
                voucherMapperService.updateById(voucher);

                StatementIncome income = new StatementIncome();
                income.setId(params.get(j).getId());
                income.setSyncStatus(3);
                income.setVoucherNo(voucherNo);
                incomeService.updateVoucherNo(income);
                }else{
                 allPass=false;
             }
            }
            if(allPass){
                return  ResponseData.success("同步erp成功！");
            }else{
                return  ResponseData.success("部分同步erp成功，部分失败!");
            }

        }
    }

    @DataSource(name = "finance")
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void confirm(StatementIncomeParam param) throws Exception {
        log.info("收入记录表确认入参[{}]", JSONObject.toJSON(param));

        LoginContext current= SpringContext.getBean(LoginContext.class);
        LoginUser currentUser = current.getLoginUser();

        StatementIncome income = this.baseMapper.selectById(param.getId());

        if(income.getSyncStatus()==2){
            throw new ServiceException(500, "该数据已确认！");
        }

        StatementVoucher voucher = new StatementVoucher();

        QueryWrapper<StatementVoucher> voucherQueryWrapper=new QueryWrapper<>();
        voucherQueryWrapper.eq("RECORD_ID",income.getId())
                .eq("FISCAL_PERIOD",income.getFiscalPeriod());
        StatementVoucher voucherdata = statementVoucherMapper.selectOne(voucherQueryWrapper
                .orderByDesc("RECORD_ID").apply("rownum={0}",1));

        //日期转换：上个会计期间
        DateFormat format2=new SimpleDateFormat("yyyy-MM");
        Date date=new Date();
        try{
            date=format2.parse(income.getFiscalPeriod());
        }catch(Exception e){
            e.printStackTrace();
        }
        Calendar c=Calendar.getInstance();
        c.setTime(date);
        SimpleDateFormat format=new SimpleDateFormat("yyyy-MM");
        c.add(Calendar.MONTH,-1);
        String premonth=format.format(c.getTime());

        //查询上个会计期间应收明细
        QueryWrapper<ReceivableDetail> prequeryWrapper = new QueryWrapper<>();
        prequeryWrapper.eq("PLATFORM_NAME",income.getPlatformName()).eq("SHOP_NAME",income.getShopName())
                .eq("SITE",income.getSite())
                .eq("FISCAL_PERIOD",premonth)
                .eq("STATUS",0);
        ReceivableDetail  prereceivableDetail = receivableDetailService.getBaseMapper().selectOne(prequeryWrapper);
        //效验
        if(prereceivableDetail!=null)
        {
            throw new ServiceException(500, "上个会计期间应收明细未审核！");
        }

        if(voucherdata==null)
        {
            voucher.setRecordId(income.getId());
            voucher.setAccountBook("销售中心账簿");
            voucher.setAccountingOrganization("销售中心");
            voucher.setFiscalPeriod(income.getFiscalPeriod());
            voucher.setProofWords("记");
            voucherMapperService.save(voucher);

            String dest = voucher.getFiscalPeriod() + " " + income.getPlatformName()+
                    " "+income.getShopName()+" "+income.getSite() +" "+income.getReportType()+" "+income.getSettlementId();
            String accountingDimensions = income.getPlatformName()+"_"+income.getShopName()+"_"+income.getSite();
            String exchangeRateType = "固定汇率";
            String currency = income.getOriginalCurrency();
            BigDecimal voucherId = voucher.getVoucherId();

            //取汇率日期
            SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd");

            FixedExchangeRateParam rateParam = new FixedExchangeRateParam();
            rateParam.setOriginalCurrency(currency);
            rateParam.setEffectDate(df.parse(voucher.getFiscalPeriod()+"-01"));
            BigDecimal exchangeRate = rateService.getRateByDateCurrency(rateParam).getDirectRate();// 查询汇率

            BigDecimal debitAmountTotal= new BigDecimal("0");

            List<StatementVoucherDetail> detailList = new ArrayList<>();
            //Advertising
            if(income.getAdvertising().compareTo(BigDecimal.ZERO)!=0 || income.getAdvertisingRefund().compareTo(BigDecimal.ZERO)!=0){

                StatementVoucherDetail detail1 = new StatementVoucherDetail();

                detail1.setVoucherId(voucherId);
                detail1.setCurrency(currency);
                detail1.setAccountingDimensions(accountingDimensions);
                detail1.setExchangeRate(exchangeRate);
                detail1.setExchangeRateType(exchangeRateType);

                detail1.setDigest(dest);
                detail1.setSubjectCode("6601.006.01");
                detail1.setSubjectName("销售费用_广告费_Advertising");
                detail1.setOriginalAmount(income.getAdvertising().add(income.getAdvertisingRefund()).negate());
                detail1.setDebitAmount(income.getAdvertising().add(income.getAdvertisingRefund()).multiply(exchangeRate).setScale(2,BigDecimal.ROUND_HALF_UP).negate());
                detailList.add(detail1);
                debitAmountTotal = debitAmountTotal.add(income.getAdvertising().add(income.getAdvertisingRefund())
                        .multiply(exchangeRate).setScale(2,BigDecimal.ROUND_HALF_UP).negate());
            }

            //lightning deal
            if(income.getLightningDeal().compareTo(BigDecimal.ZERO)!=0){

                StatementVoucherDetail detail2 = new StatementVoucherDetail();

                detail2.setVoucherId(voucherId);
                detail2.setCurrency(currency);
                detail2.setAccountingDimensions(accountingDimensions);
                detail2.setExchangeRate(exchangeRate);
                detail2.setExchangeRateType(exchangeRateType);

                detail2.setDigest(dest);
                detail2.setSubjectCode("6601.006.03");
                detail2.setSubjectName("销售费用_广告费_lightningdeal");
                detail2.setOriginalAmount(income.getLightningDeal().negate());
                detail2.setDebitAmount(income.getLightningDeal().multiply(exchangeRate).setScale(2,BigDecimal.ROUND_HALF_UP).negate());
                detailList.add(detail2);

                debitAmountTotal = debitAmountTotal.add(income.getLightningDeal()
                        .multiply(exchangeRate).setScale(2,BigDecimal.ROUND_HALF_UP).negate());
            }


            //Commission
            if(income.getCommissionTotal().compareTo(BigDecimal.ZERO)!=0){

                StatementVoucherDetail detail3 = new StatementVoucherDetail();

                detail3.setVoucherId(voucherId);
                detail3.setCurrency(currency);
                detail3.setAccountingDimensions(accountingDimensions);
                detail3.setExchangeRate(exchangeRate);
                detail3.setExchangeRateType(exchangeRateType);

                detail3.setDigest(dest);
                detail3.setSubjectCode("6601.007.01");
                detail3.setSubjectName("销售费用_平台费_Commission");
                detail3.setOriginalAmount(income.getCommissionTotal().negate());
                detail3.setDebitAmount(income.getCommissionTotal().multiply(exchangeRate).setScale(2,BigDecimal.ROUND_HALF_UP).negate());
                detailList.add(detail3);

                debitAmountTotal = debitAmountTotal.add(income.getCommissionTotal()
                        .multiply(exchangeRate).setScale(2,BigDecimal.ROUND_HALF_UP).negate());
            }


            //Storage fee
            if(income.getStorageFee().compareTo(BigDecimal.ZERO)!=0){

                StatementVoucherDetail detail4 = new StatementVoucherDetail();

                detail4.setVoucherId(voucherId);
                detail4.setCurrency(currency);
                detail4.setAccountingDimensions(accountingDimensions);
                detail4.setExchangeRate(exchangeRate);
                detail4.setExchangeRateType(exchangeRateType);

                detail4.setDigest(dest);
                detail4.setSubjectCode("6601.007.03");
                detail4.setSubjectName("销售费用_平台费_Storage fee");
                detail4.setOriginalAmount(income.getStorageFee().negate());
                detail4.setDebitAmount(income.getStorageFee().multiply(exchangeRate).setScale(2,BigDecimal.ROUND_HALF_UP).negate());
                detailList.add(detail4);

                debitAmountTotal = debitAmountTotal.add(income.getStorageFee()
                        .multiply(exchangeRate).setScale(2,BigDecimal.ROUND_HALF_UP).negate());
            }

            //Reimbursement
            if(income.getReimbursement().compareTo(BigDecimal.ZERO)!=0){

                StatementVoucherDetail detail5 = new StatementVoucherDetail();

                detail5.setVoucherId(voucherId);
                detail5.setCurrency(currency);
                detail5.setAccountingDimensions(accountingDimensions);
                detail5.setExchangeRate(exchangeRate);
                detail5.setExchangeRateType(exchangeRateType);

                detail5.setDigest(dest);
                detail5.setSubjectCode("6601.007.04");
                detail5.setSubjectName("销售费用_平台费_Reimbursement");
                detail5.setOriginalAmount(income.getReimbursement().negate());
                detail5.setDebitAmount(income.getReimbursement().multiply(exchangeRate).setScale(2,BigDecimal.ROUND_HALF_UP).negate());

                detailList.add(detail5);

                debitAmountTotal = debitAmountTotal.add(income.getReimbursement()
                        .multiply(exchangeRate).setScale(2,BigDecimal.ROUND_HALF_UP).negate());
            }


            //Other
            if(income.getOther().compareTo(BigDecimal.ZERO)!=0 || income.getOtherFbaItf().compareTo(BigDecimal.ZERO)!=0){

                StatementVoucherDetail detail6 = new StatementVoucherDetail();

                detail6.setVoucherId(voucherId);
                detail6.setCurrency(currency);
                detail6.setAccountingDimensions(accountingDimensions);
                detail6.setExchangeRate(exchangeRate);
                detail6.setExchangeRateType(exchangeRateType);

                detail6.setDigest(dest);
                detail6.setSubjectCode("6601.007.05");
                detail6.setSubjectName("销售费用_平台费_Other");
                detail6.setOriginalAmount(income.getOther().add(income.getOtherFbaItf()).negate());
                detail6.setDebitAmount(income.getOther().add(income.getOtherFbaItf()).multiply(exchangeRate).setScale(2,BigDecimal.ROUND_HALF_UP).negate());

                detailList.add(detail6);

                debitAmountTotal = debitAmountTotal.add(income.getOther().add(income.getOtherFbaItf())
                        .multiply(exchangeRate).setScale(2,BigDecimal.ROUND_HALF_UP).negate());
            }

            //Amazon Fee-Total小平台  取明细数据拆分
            if(income.getAmazonFeeTotalXpt().compareTo(BigDecimal.ZERO)!=0){


                if(income.getReportType().equals("Settlement")){

                    SettlementDetail settlementdetail = new SettlementDetail();
                    settlementdetail.setSettlementId(income.getSettlementId());
                    settlementdetail.setPostedDate(income.getStartTime());
                    settlementdetail.setCreateTime(income.getEndTime());
                    List<SettlementDetail> xptFee = settlementDetailService.getPlatformAmazonFee(settlementdetail);

                    for (SettlementDetail sDetail:xptFee) {

                        if(sDetail.getAmazonFee().compareTo(BigDecimal.ZERO)!=0){
                            StatementVoucherDetail detail = new StatementVoucherDetail();

                            detail.setVoucherId(voucherId);
                            detail.setCurrency(currency);
                            detail.setExchangeRate(exchangeRate);
                            detail.setExchangeRateType(exchangeRateType);

                            detail.setDigest(dest);
                            detail.setSubjectCode("6601.007.13");
                            detail.setSubjectName("销售费用_平台费_小平台费用");
                            detail.setOriginalAmount(sDetail.getAmazonFee().negate());
                            detail.setDebitAmount(sDetail.getAmazonFee().multiply(exchangeRate).setScale(2,BigDecimal.ROUND_HALF_UP).negate());

                            if(sDetail.getPlatform().equals("eBay") && income.getShopName().equals("CC")){
                                detail.setAccountingDimensions("eBay_CC_All");
                            }
                            if(sDetail.getPlatform().equals("eBay") && !income.getShopName().equals("CC")){
                                detail.setAccountingDimensions("eBay_MK_All");
                            }
                            if(sDetail.getPlatform().equals("Rakuten")){
                                detail.setAccountingDimensions("Rakuten_TS_JP");
                            }
                            if(sDetail.getPlatform().equals("B2B")){
                                detail.setAccountingDimensions("B2B_MK_ALL");
                            }
                            if(sDetail.getPlatform().equals("shopify")){
                                detail.setAccountingDimensions("shopify_Glucoracy_ALL");
                            }
                            detailList.add(detail);

                            debitAmountTotal = debitAmountTotal.add(sDetail.getAmazonFee()
                                    .multiply(exchangeRate).setScale(2,BigDecimal.ROUND_HALF_UP).negate());
                        }
                    }

                }else{

                    DatarangeDetailComfirm datarangeDetail = new DatarangeDetailComfirm();
                    datarangeDetail.setSettlementId(income.getSettlementId());
                    datarangeDetail.setPostedDate(income.getStartTime());
                    datarangeDetail.setCreateTime(income.getEndTime());
                    List<DatarangeDetailComfirm> xptFee = datarangeDetailComfirmService.getPlatformAmazonFee(datarangeDetail);

                    for (DatarangeDetailComfirm dDetail:xptFee) {

                        if (dDetail.getAmazonFee().compareTo(BigDecimal.ZERO) == 1) {
                            StatementVoucherDetail detail = new StatementVoucherDetail();

                            detail.setVoucherId(voucherId);
                            detail.setCurrency(currency);
                            detail.setExchangeRate(exchangeRate);
                            detail.setExchangeRateType(exchangeRateType);

                            detail.setDigest(dest);
                            detail.setSubjectCode("6601.007.13");
                            detail.setSubjectName("销售费用_平台费_小平台费用");
                            detail.setOriginalAmount(dDetail.getAmazonFee().negate());
                            detail.setDebitAmount(dDetail.getAmazonFee().multiply(exchangeRate).setScale(2, BigDecimal.ROUND_HALF_UP).negate());

                            if (dDetail.getPlatform().equals("eBay") && income.getShopName().equals("CC")) {
                                detail.setAccountingDimensions("eBay_CC_All");
                            }
                            if (dDetail.getPlatform().equals("eBay") && !income.getShopName().equals("CC")) {
                                detail.setAccountingDimensions("eBay_MK_All");
                            }
                            if (dDetail.getPlatform().equals("Rakuten")) {
                                detail.setAccountingDimensions("Rakuten_TS_JP");
                            }
                            if (dDetail.getPlatform().equals("B2B")) {
                                detail.setAccountingDimensions("B2B_MK_ALL");
                            }
                            if (dDetail.getPlatform().equals("shopify")) {
                                detail.setAccountingDimensions("shopify_Glucoracy_ALL");
                            }
                            detailList.add(detail);

                            debitAmountTotal = debitAmountTotal.add(dDetail.getAmazonFee()
                                    .multiply(exchangeRate).setScale(2,BigDecimal.ROUND_HALF_UP).negate());
                        }
                    }
                }
            }

            //Amazon Fee-Total非小平台
            if(income.getAmazonFeeTotal().compareTo(BigDecimal.ZERO)!=0){
                StatementVoucherDetail detail8 = new StatementVoucherDetail();

                detail8.setVoucherId(voucherId);
                detail8.setCurrency(currency);
                detail8.setAccountingDimensions(accountingDimensions);
                detail8.setExchangeRate(exchangeRate);
                detail8.setExchangeRateType(exchangeRateType);

                detail8.setDigest(dest);
                detail8.setSubjectCode("6601.007.02");
                detail8.setSubjectName("销售费用_平台费_Amazon Fee");
                detail8.setOriginalAmount(income.getAmazonFeeTotal().negate());
                detail8.setDebitAmount(income.getAmazonFeeTotal().multiply(exchangeRate).setScale(2,BigDecimal.ROUND_HALF_UP).negate());

                detailList.add(detail8);

                debitAmountTotal = debitAmountTotal.add(income.getAmazonFeeTotal()
                        .multiply(exchangeRate).setScale(2,BigDecimal.ROUND_HALF_UP).negate());
            }


            //海外VAT
            if(income.getWithheldTax().compareTo(BigDecimal.ZERO)!=0){

                StatementVoucherDetail detail9 = new StatementVoucherDetail();

                detail9.setVoucherId(voucherId);
                detail9.setCurrency(currency);
                detail9.setAccountingDimensions(accountingDimensions);
                detail9.setExchangeRate(exchangeRate);
                detail9.setExchangeRateType(exchangeRateType);

                detail9.setDigest(dest);
                detail9.setSubjectCode("6601.008");
                detail9.setSubjectName("销售费用_海外VAT");
                detail9.setOriginalAmount(income.getWithheldTax().negate());
                detail9.setDebitAmount(income.getWithheldTax().multiply(exchangeRate).setScale(2,BigDecimal.ROUND_HALF_UP).negate());

                detailList.add(detail9);

                debitAmountTotal = debitAmountTotal.add(income.getWithheldTax()
                        .multiply(exchangeRate).setScale(2,BigDecimal.ROUND_HALF_UP).negate());
            }

            //Goodwill
            if(income.getGoodwill().compareTo(BigDecimal.ZERO)!=0){

                StatementVoucherDetail detail9 = new StatementVoucherDetail();

                detail9.setVoucherId(voucherId);
                detail9.setCurrency(currency);
                detail9.setAccountingDimensions(accountingDimensions);
                detail9.setExchangeRate(exchangeRate);
                detail9.setExchangeRateType(exchangeRateType);

                detail9.setDigest(dest);
                detail9.setSubjectCode("6601.007.14");
                detail9.setSubjectName("销售费用_平台费_Goodwill");
                detail9.setOriginalAmount(income.getGoodwill().negate());
                detail9.setDebitAmount(income.getGoodwill().multiply(exchangeRate).setScale(2,BigDecimal.ROUND_HALF_UP).negate());

                detailList.add(detail9);

                debitAmountTotal = debitAmountTotal.add(income.getGoodwill()
                        .multiply(exchangeRate).setScale(2,BigDecimal.ROUND_HALF_UP).negate());
            }
            //主营业务收入
            BigDecimal businessTotal = income.getSalesTotal()
                    .add(income.getSalesPromotion())
                    .add(income.getRefundTotal())
                    .add(income.getGiftwarpShipping());
            //差异金额
            BigDecimal res =BigDecimal.ZERO;

            //应收账款
            if(income.getCurrentReceivableAmount().compareTo(BigDecimal.ZERO)!=0){
                //借方总金额
                debitAmountTotal = debitAmountTotal.add(income.getCurrentReceivableAmount()
                        .multiply(exchangeRate).setScale(2,BigDecimal.ROUND_HALF_UP));
                //借贷差额
                res = debitAmountTotal.subtract(businessTotal.multiply(exchangeRate).setScale(2,BigDecimal.ROUND_HALF_UP));

                StatementVoucherDetail detail10 = new StatementVoucherDetail();

                detail10.setVoucherId(voucherId);
                detail10.setCurrency(currency);
                detail10.setAccountingDimensions(accountingDimensions);
                detail10.setExchangeRate(exchangeRate);
                detail10.setExchangeRateType(exchangeRateType);

                detail10.setDigest(dest);
                detail10.setSubjectCode("1122.001");
                detail10.setSubjectName("应收账款_平台货款");
                //小于0时，放贷方，如果无主营业务收入没有时，调整差额
                if(income.getCurrentReceivableAmount().compareTo(BigDecimal.ZERO)<0){

                    detail10.setOriginalAmount(income.getCurrentReceivableAmount().negate());
                    if(businessTotal.compareTo(BigDecimal.ZERO)==0){
                        detail10.setCreditAmount(income.getCurrentReceivableAmount().negate()
                                .multiply(exchangeRate).setScale(2,BigDecimal.ROUND_HALF_UP).add(res));

                    }else{
                        detail10.setCreditAmount(income.getCurrentReceivableAmount().negate()
                                .multiply(exchangeRate).setScale(2,BigDecimal.ROUND_HALF_UP));

                    }
                }else {
                    //此时主营业务收入一定有
                    detail10.setOriginalAmount(income.getCurrentReceivableAmount());
                    detail10.setDebitAmount(income.getCurrentReceivableAmount().multiply(exchangeRate).setScale(2,BigDecimal.ROUND_HALF_UP));


                }

                detailList.add(detail10);
            }


            //主营业务收入   todo  不相等差值调整
            if(businessTotal.compareTo(BigDecimal.ZERO)!=0){

                StatementVoucherDetail detail11 = new StatementVoucherDetail();

                detail11.setVoucherId(voucherId);
                detail11.setCurrency(currency);
                detail11.setAccountingDimensions(accountingDimensions);
                detail11.setExchangeRate(exchangeRate);
                detail11.setExchangeRateType(exchangeRateType);

                detail11.setDigest(dest);
                detail11.setSubjectCode("6001.002");
                detail11.setSubjectName("主营业务收入_外部收入");
                detail11.setOriginalAmount(businessTotal);
                detail11.setCreditAmount(debitAmountTotal);//等于借方总金额，记录差值

                detailList.add(detail11);
            }else {

            }

            //记录差异金额
            if(res.compareTo(BigDecimal.ZERO)!=0){
                voucher.setDifferenceAmount(res);
                voucherMapperService.updateById(voucher);
            }

            BigDecimal platDebit = BigDecimal.ZERO;
            BigDecimal bankCredit = BigDecimal.ZERO;
            //信用卡借应收账款
            if(income.getSuccessfulCharge().compareTo(BigDecimal.ZERO)!=0){

                StatementVoucherDetail detail10 = new StatementVoucherDetail();

                detail10.setVoucherId(voucherId);
                detail10.setCurrency(currency);
                detail10.setAccountingDimensions(accountingDimensions);
                detail10.setExchangeRate(exchangeRate);
                detail10.setExchangeRateType(exchangeRateType);

                detail10.setDigest(dest);
                detail10.setSubjectCode("1122.001");
                detail10.setSubjectName("应收账款_平台货款");
                detail10.setOriginalAmount(income.getSuccessfulCharge());
                platDebit = income.getSuccessfulCharge().multiply(exchangeRate).setScale(2,BigDecimal.ROUND_HALF_UP);
                detail10.setDebitAmount(platDebit);

                detailList.add(detail10);

            }

            //信用卡贷银行存款
            if(income.getSuccessfulCharge().compareTo(BigDecimal.ZERO)!=0){

                StatementVoucherDetail detail10 = new StatementVoucherDetail();


                FixedExchangeRateParam rateParam2 = new FixedExchangeRateParam();
                rateParam2.setOriginalCurrency(param.getCurrency());
                rateParam2.setEffectDate(df.parse(voucher.getFiscalPeriod()+"-01"));
                BigDecimal exRate = rateService.getRateByDateCurrency(rateParam2).getDirectRate();// 查询汇率



                detail10.setVoucherId(voucherId);
                detail10.setCurrency(param.getCurrency());
                detail10.setAccountingDimensions(accountingDimensions);
                detail10.setExchangeRate(exRate);
                detail10.setExchangeRateType(exchangeRateType);

                detail10.setDigest(dest);
                detail10.setSubjectCode(param.getSubjectCode());
                detail10.setSubjectName(param.getSubjectName());
                detail10.setOriginalAmount(param.getCardDeductionAmount());

                bankCredit = param.getCardDeductionAmount().multiply(exRate).setScale(2,BigDecimal.ROUND_HALF_UP);
                detail10.setCreditAmount(param.getCardDeductionAmount().multiply(exRate).setScale(2,BigDecimal.ROUND_HALF_UP));

                detailList.add(detail10);

            }

            //差额
            if (bankCredit.subtract(platDebit).compareTo(BigDecimal.ZERO) != 0) {

                StatementVoucherDetail detail1 = new StatementVoucherDetail();

                detail1.setVoucherId(voucherId);
                detail1.setCurrency("CNY");
                detail1.setExchangeRate(new BigDecimal(1));
                detail1.setExchangeRateType("固定汇率");
                detail1.setAccountingDimensions("BM1");
                detail1.setDigest(dest);
                detail1.setSubjectCode("6603.001.07");
                detail1.setSubjectName("财务费用_银行类费用_汇兑损益");
                detail1.setOriginalAmount(bankCredit.subtract(platDebit));
                detail1.setDebitAmount(bankCredit.subtract(platDebit));
                detailList.add(detail1);
            }

            statementVoucherDetailService.saveBatch(detailList);



            //插入应收明细
            ReceivableDetail detailParam = new ReceivableDetail();
            detailParam.setPlatformName(income.getPlatformName());
            detailParam.setShopName(income.getShopName());
            detailParam.setSite(income.getSite());
            detailParam.setFiscalPeriod(income.getFiscalPeriod());
            receivableDetailService.generateReceivable(detailParam);

            QueryWrapper<ReceivableDetail> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("PLATFORM_NAME",detailParam.getPlatformName()).eq("SHOP_NAME",detailParam.getShopName())
                    .eq("SITE",detailParam.getSite()).eq("FISCAL_PERIOD",detailParam.getFiscalPeriod());
            //应收明细会计期间
            ReceivableDetail  receivableDetail = receivableDetailService.getBaseMapper().selectOne(queryWrapper);

            if(receivableDetail.getStatus().compareTo(new BigDecimal(0))!=0){
                throw new ServiceException(500, "应收明细已确认！");
            }
            ReceivableDetailDetail detailDetail = new ReceivableDetailDetail();
            detailDetail.setReceivableId(receivableDetail.getId());
            detailDetail.setSettlementId(income.getSettlementId());
            detailDetail.setIncomeType(income.getIncomeType());
            detailDetail.setOriginalCurrency(income.getOriginalCurrency());
            detailDetail.setProceedsCurrency(income.getOriginalCurrency());
            detailDetail.setCurrentReserveAmount(income.getCurrentReceivableAmount());
            detailDetail.setSuccessfulCharge(income.getSuccessfulCharge());
            detailDetail.setCreateAt(new Date());
            detailDetail.setCreateBy(currentUser.getName());
            receivableDetailDetailService.save(detailDetail);

            //刷取应收明细金额
            receivableDetailService.refresh(detailParam);

        }
        income.setSyncStatus(2);
        //** 信用卡账号 *//*
        income.setChargeCard(param.getChargeCard());
        income.setCardCurrency(param.getCurrency());
        income.setCardDeductionAmount(param.getCardDeductionAmount());
        income.setSubjectName(param.getSubjectName());
        income.setSubjectCode(param.getSubjectCode());
        this.updateById(income);

        //写入结算单明细(原币)记录
        param.setId(income.getId());
        param.setSettlementId(income.getSettlementId());
        param.setFiscalPeriod(income.getFiscalPeriod());
        insertSettlementListing(income);
    }

    @DataSource(name = "finance")
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void confirmBatch(List<StatementIncomeParam> params) throws Exception {
        for (StatementIncomeParam param:params){
            this.confirm(param);
        }
    }

    @DataSource(name = "finance")
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void syncAmount(StatementIncomeParam param) {
        log.info("刷取金额开始");
        //刷settlement金额数据
        QueryWrapper<StatementIncome> queryWrapper = new QueryWrapper<>();
        if (param.getSettlementId()!=null){
            queryWrapper.eq("SETTLEMENT_ID",param.getSettlementId());
        }
        queryWrapper.eq("REPORT_TYPE","Settlement")
                .and(wrapper->wrapper.eq("SYNC_STATUS",0).or().eq("SYNC_STATUS",4));

        List<StatementIncome> incomeList = this.baseMapper.selectList(queryWrapper);
        for (StatementIncome in : incomeList){
            SettlementDetail detail = new SettlementDetail();
            detail.setSettlementId(in.getSettlementId());
            //为了避免汇总时间区间没有覆盖到明细的时间段
            detail.setPostedDate(DateUtil.beginOfMonth(in.getStartTime()));
            detail.setCreateTime(DateUtil.endOfMonth(in.getEndTime()));
            //根据时间和settlementId汇总金额
            SettlementDetail detail2 = settlementDetailService.getSettlementMoney(detail);

            //非Amazon平台费
            SettlementDetail amazonFeeNot = settlementDetailService.getNotAmazonFee(detail);
            if(detail2!=null) {
                BigDecimal salesTotal = detail2.getSalesOther().add(detail2.getSalesTax().add(detail2.getSalesPrincipal()));
                BigDecimal refundTotal = detail2.getRefund().add(detail2.getRefundPromotion());
                BigDecimal commissionTotal = detail2.getCommission().add(detail2.getRefundCommission());
                BigDecimal amazonFeeTotal = detail2.getDisposalFee().add(detail2.getRemovalFee().add(detail2.getAmazonFee()));
                BigDecimal storageFee = detail2.getStorageFee().add(detail2.getLongTermStorageFee());

                BigDecimal currentReceivableAmount = salesTotal.add(detail2.getSalesPromotion()).add(refundTotal).add(detail2.getGiftwarpShipping())
                        .add(detail2.getAdvertising()).add(detail2.getAdvertisingRefund()).add(detail2.getLightningDealFee()).add(commissionTotal)
                        .add(amazonFeeTotal).add(storageFee).add(detail2.getReimbursement())
                        .add(detail2.getOther()).add(detail2.getOtherFbaItf()).add(detail2.getGoodWill()).add(detail2.getWithheldTax());
                BigDecimal amazonFeeTotalXpt = new BigDecimal("0");
                if(amazonFeeNot!=null){
                    amazonFeeTotal =  amazonFeeTotal.subtract(amazonFeeNot.getAmazonFee());
                    amazonFeeTotalXpt = amazonFeeNot.getAmazonFee();
                }

                BigDecimal realReceivableAmount = detail2.getAmount().subtract(detail2.getPreviousReserveAmount())
                           .subtract(detail2.getUnsuccessfulTransfer()).subtract(detail2.getCurrentReserveAmount())
                           .subtract(detail2.getSuccessfulCharge()).subtract(detail2.getPayableToAmazon());

                in.setSalesTotal(salesTotal);
                in.setSalesPromotion(detail2.getSalesPromotion());
                in.setRefundTotal(refundTotal);//?
                in.setGiftwarpShipping(detail2.getGiftwarpShipping());
                in.setAdvertising(detail2.getAdvertising());
                in.setAdvertisingRefund(detail2.getAdvertisingRefund());
                in.setLightningDeal(detail2.getLightningDealFee());
                in.setCommissionTotal(commissionTotal);
                in.setAmazonFeeTotalXpt(amazonFeeTotalXpt);
                in.setAmazonFeeTotal(amazonFeeTotal);
                in.setStorageFee(storageFee);
                in.setReimbursement(detail2.getReimbursement());
                in.setOther(detail2.getOther());
                in.setOtherFbaItf(detail2.getOtherFbaItf());
                in.setGoodwill(detail2.getGoodWill());
                in.setWithheldTax(detail2.getWithheldTax());
                in.setCurrentReceivableAmount(currentReceivableAmount);
                in.setSuccessfulCharge(detail2.getSuccessfulCharge());
                in.setRealReceivableAmount(realReceivableAmount);
                if(in.getSyncStatus()!=4){
                    in.setSyncStatus(1);//刷完数据 待确认
                    in.setRefreshStatus(0);
                }
            }else{
                QueryWrapper<Settlement> detailQueryWrapper = new QueryWrapper<>();
                detailQueryWrapper.eq("SETTLEMENT_ID",in.getSettlementId()).eq("STATUS",2);
                Settlement settlement = settlementService.getOne(detailQueryWrapper);
                if(settlement!=null){
                    BigDecimal zero = BigDecimal.ZERO;
                    in.setSalesTotal(zero);
                    in.setSalesPromotion(zero);
                    in.setRefundTotal(zero);//?
                    in.setGiftwarpShipping(zero);
                    in.setAdvertising(zero);
                    in.setAdvertisingRefund(zero);
                    in.setLightningDeal(zero);
                    in.setCommissionTotal(zero);
                    in.setAmazonFeeTotalXpt(zero);
                    in.setAmazonFeeTotal(zero);
                    in.setStorageFee(zero);
                    in.setReimbursement(zero);
                    in.setOther(zero);
                    in.setOtherFbaItf(zero);
                    in.setGoodwill(zero);
                    in.setWithheldTax(zero);
                    in.setCurrentReceivableAmount(zero);
                    in.setSuccessfulCharge(zero);
                    in.setRealReceivableAmount(zero);
                    if(in.getSyncStatus()!=4){
                        in.setSyncStatus(1);//刷完数据 待确认
                        in.setRefreshStatus(0);
                    }
                }
            }
        }

        if(incomeList.size()>0){
            this.updateBatchById(incomeList);
        }

        //刷DataRange金额数据
        QueryWrapper<StatementIncome> queryRange = new QueryWrapper<>();
        if (param.getSettlementId()!=null){
            queryRange.eq("SETTLEMENT_ID",param.getSettlementId());
        }
        queryRange.eq("REPORT_TYPE","Data Range").ne("INCOME_TYPE","预估冲回")
                .and(wrapper->wrapper.eq("SYNC_STATUS",0).or().eq("SYNC_STATUS",4));

        List<StatementIncome> rangeList = this.baseMapper.selectList(queryRange);
        for (StatementIncome in : rangeList){
            DatarangeDetailComfirm detail = new DatarangeDetailComfirm();
            detail.setSettlementId(in.getSettlementId());
            detail.setPostedDate(in.getStartTime());
            detail.setCreateTime(in.getEndTime());

            //根据时间和settlementId汇总金额
            DatarangeDetailComfirm detail2 = datarangeDetailComfirmService.getRangeMoney(detail);

            //非Amazon平台费
            DatarangeDetailComfirm amazonFeeNot = datarangeDetailComfirmService.getNotAmazonFee(detail);
            if(detail2!=null) {
                BigDecimal salesTotal = detail2.getSalesOther().add(detail2.getSalesTax().add(detail2.getSalesPrincipal()));
                BigDecimal refundTotal = detail2.getRefund().add(detail2.getRefundPromotion());
                BigDecimal commissionTotal = detail2.getCommission().add(detail2.getRefundCommission());
                BigDecimal amazonFeeTotal = detail2.getDisposalFee().add(detail2.getRemovalFee().add(detail2.getAmazonFee()));
                BigDecimal storageFee = detail2.getStorageFee().add(detail2.getLongTermStorageFee());
                BigDecimal currentReceivableAmount = salesTotal.add(detail2.getSalesPromotion()).add(refundTotal).add(detail2.getGiftwarpShipping())
                        .add(detail2.getAdvertising()).add(detail2.getLightningDealFee()).add(commissionTotal)
                        .add(amazonFeeTotal).add(storageFee).add(detail2.getReimbursement())
                        .add(detail2.getOther()).add(detail2.getGoodWill()).add(detail2.getWithheldTax());

                BigDecimal amazonFeeTotalXpt = new BigDecimal("0");
                if(amazonFeeNot!=null){
                    amazonFeeTotal =    amazonFeeTotal.subtract(amazonFeeNot.getAmazonFee());
                    amazonFeeTotalXpt =amazonFeeNot.getAmazonFee();
                }

                BigDecimal realReceivableAmount = detail2.getAmtSource().subtract(detail2.getPreviousReserveAmount())
                        .subtract(detail2.getUnsuccessfulTransfer()).subtract(detail2.getCurrentReserveAmount())
                        .subtract(detail2.getSuccessfulCharge()).subtract(detail2.getMoneyTransfer());

                in.setSalesTotal(salesTotal);
                in.setSalesPromotion(detail2.getSalesPromotion());
                in.setRefundTotal(refundTotal);//?
                in.setGiftwarpShipping(detail2.getGiftwarpShipping());
                in.setAdvertising(detail2.getAdvertising());
                in.setAdvertisingRefund(BigDecimal.ZERO);
                in.setLightningDeal(detail2.getLightningDealFee());
                in.setCommissionTotal(commissionTotal);
                in.setAmazonFeeTotalXpt(amazonFeeTotalXpt);
                in.setAmazonFeeTotal(amazonFeeTotal);
                in.setStorageFee(storageFee);
                in.setReimbursement(detail2.getReimbursement());
                in.setOther(detail2.getOther());
                in.setOtherFbaItf(BigDecimal.ZERO);
                in.setGoodwill(detail2.getGoodWill());
                in.setWithheldTax(detail2.getWithheldTax());
                in.setCurrentReceivableAmount(currentReceivableAmount);
                in.setSuccessfulCharge(detail2.getSuccessfulCharge());
                in.setRealReceivableAmount(realReceivableAmount);
                if(in.getSyncStatus()!=4){
                    in.setSyncStatus(1);//刷完数据 待确认
                    in.setRefreshStatus(0);
                }
            }else{
                QueryWrapper<Datarange> detailQueryWrapper = new QueryWrapper<>();
                detailQueryWrapper.eq("SETTLEMENT_ID",in.getSettlementId()).eq("STATUS",3)
                        .eq("SETTLEMENT_START_DATE",in.getStartTime()).eq("SETTLEMENT_END_DATE",in.getEndTime());
                Datarange datarange = datarangeService.getOne(detailQueryWrapper);

                if(datarange!=null) {
                    BigDecimal zero = BigDecimal.ZERO;
                    in.setSalesTotal(zero);
                    in.setSalesPromotion(zero);
                    in.setRefundTotal(zero);//?
                    in.setGiftwarpShipping(zero);
                    in.setAdvertising(zero);
                    in.setAdvertisingRefund(zero);
                    in.setLightningDeal(zero);
                    in.setCommissionTotal(zero);
                    in.setAmazonFeeTotalXpt(zero);
                    in.setAmazonFeeTotal(zero);
                    in.setStorageFee(zero);
                    in.setReimbursement(zero);
                    in.setOther(zero);
                    in.setOtherFbaItf(zero);
                    in.setGoodwill(zero);
                    in.setWithheldTax(zero);
                    in.setCurrentReceivableAmount(zero);
                    in.setSuccessfulCharge(zero);
                    in.setRealReceivableAmount(zero);
                    if (in.getSyncStatus() != 4) {
                        in.setSyncStatus(1);//刷完数据 待确认
                        in.setRefreshStatus(0);
                    }
                }
            }

        }
        if(rangeList.size()>0){
            this.updateBatchById(rangeList);
        }
        log.info("刷取金额结束");
    }

    @DataSource(name = "finance")
    @Transactional(rollbackFor = Exception.class)
    public void SyncErp(StatementIncomeParam param) throws IOException {

        LoginContext current= SpringContext.getBean(LoginContext.class);
        LoginUser currentUser = current.getLoginUser();

        JSONArray Jarr = new JSONArray();
        StatementVoucher vParam = new StatementVoucher();
        vParam.setRecordId(param.getId());

        StatementVoucher voucher = voucherMapperService.queryVoucher(vParam);
        List<VoucherDetailResult> detail = voucherMapperService.getSyncDetail(vParam);

        String period = voucher.getFiscalPeriod();
        String[] eriodArr = period.split("-");

        JSONObject object = new JSONObject();
        object.put("FBillNo", "SRJL-"+ voucher.getFiscalPeriod()+"-1"+"-"+voucher.getVoucherId().toString());//凭证编号 不传 返回
        object.put("FAccountBookID", "003");//销售中心
        object.put("FDate", period+"-1");
        object.put("FCreatorId",currentUser.getAccount());
        object.put("FVOUCHERGROUPID", "PRE001");//凭证字 记
        object.put("FATTACHMENTS", 0); //附件数
        object.put("FYEAR", eriodArr[0]);  //会计年度
        object.put("FACCBOOKORGID", "003");//销售中心
        object.put("FISADJUSTVOUCHER",false);
        object.put("FPERIOD", eriodArr[1]);//期间
        object.put("FSourceBillKey", "78050206-2fa6-40e3-b7c8-bd608146fa38"); //业务类型 手工录入

        for (VoucherDetailResult res:detail) {
            List<Map> mList = new ArrayList<>();
            Map map = new HashMap();
            if(res.getFACCOUNTID()!=null && res.getFACCOUNTID().equals("6603.001.07")){
                map.put("HSType","部门");
                map.put("HSNumber","BM1");
            }else{
                map.put("HSType","组织机构");
                map.put("HSNumber",res.getSHOPCODE());
            }
            mList.add(map);
            res.setFHSData(mList);
        }

        object.put("FEntity", detail);

        Jarr.add(object);
        log.info("收入记录表凭证同步erp，请求参数[{}]", Jarr.toString());
        JSONObject obj  = syncToErpConsumer.voucher(Jarr);

        if (obj.getString("Code")==null ||!obj.getString("Code").equals("0")) {
            log.error("收入记录表凭证同步erp失败！详情[{}]", obj.getString("Message"));
            throw new IOException("同步erp失败！"+obj.getString("Message"));
        }else{
            String voucherNo = obj.getJSONArray("Response").getJSONObject(0).getString("FVOUCHERGROUPNO");
            voucher.setVoucherNumber(voucherNo);
            voucher.setSyncStatus((int)1);
            voucherMapperService.updateById(voucher);
//            UpdateWrapper<StatementIncome> updateWrapper = new UpdateWrapper<>();
//            updateWrapper.eq("ID",param.getId()).set("VOUCHER_NO",voucherNo).set("SYNC_STATUS",1);
//            super.update(updateWrapper);

            StatementIncome income = new StatementIncome();
            income.setId(param.getId());
            income.setSyncStatus(3);
            income.setVoucherNo(voucherNo);
            incomeService.updateVoucherNo(income);
        }
    }

    @DataSource(name = "finance")
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateById(StatementIncome param) {

        return super.updateById(param);

    }

    @DataSource(name = "finance")
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateVoucherNo(StatementIncome income) {
        this.baseMapper.updateVoucherNo(income);
    }

    @DataSource(name = "finance")
    @Override
    public List<DetailResult> refreshFailure(StatementIncomeParam param) {

        if(param.getReportType().equals("Settlement")){
            return settlementDetailService.refreshFailure(param);
        }else{
            return datarangeDetailComfirmService.refreshFailure(param);
        }
    }

    @DataSource(name = "finance")
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void toListing(StatementIncomeParam param) {

        StatementIncome income = this.baseMapper.selectById(param.getId());
        insertSettlementListing(income);
    }


    @DataSource(name = "finance")
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void dateToListing(StatementIncomeParam param) {
        QueryWrapper<StatementIncome> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("FISCAL_PERIOD",param.getFiscalPeriod()).ne("INCOME_TYPE","预估冲回").in("SYNC_STATUS",2,3);
        List<StatementIncome> incomeList = this.baseMapper.selectList(queryWrapper);
        for (StatementIncome in :  incomeList) {
            insertSettlementListing(in);
        }
    }




    @DataSource(name = "finance")
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseData assignAllocId(StationManualAllocationParam param) {
        settlementDetailListingMapper.assignAllocId(param);
        return ResponseData.success();
    }


    @DataSource(name = "finance")
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseData shopSiteHave(StatementIncomeParam param) {
        try {
            String fiscalPeriod = param.getFiscalPeriod();
            if (ObjectUtil.isEmpty(fiscalPeriod) && ObjectUtil.isEmpty(param.getShopName()) ) {
                return ResponseData.error("会计区间和店铺不能为空");
            }
            List<String> shopNames =  new ArrayList<>();
            shopNames.add(param.getShopName());

            List<String> sites = ObjectUtil.isNotEmpty(param.getSites())?param.getSites(): new ArrayList<>();
            if (ObjectUtil.isNotEmpty(param.getSite())) {
                sites.add(param.getSite());
            }

            StringBuffer errorMsg = new StringBuffer();


            if (new LambdaQueryChainWrapper<>(stationAutoAllocationMapper)
                           .eq(ObjectUtil.isNotEmpty(param.getFiscalPeriod()), StationAutoAllocation::getFiscalPeriod,param.getFiscalPeriod())
                           .in(ObjectUtil.isNotEmpty(shopNames),StationAutoAllocation::getShopName,shopNames)
                           .in(ObjectUtil.isNotEmpty(sites),StationAutoAllocation::getSite,sites)
                           .isNull(StationAutoAllocation::getAllocId)
                           .eq(StationAutoAllocation::getConfirmStatus, BigDecimal.ONE)
                                   .count()>0) {
                errorMsg.append("站内自动分摊、");
            }
            if (new LambdaQueryChainWrapper<>(stationManualAllocationMapper)
                    .eq(ObjectUtil.isNotEmpty(param.getFiscalPeriod()), StationManualAllocation::getFiscalPeriod,param.getFiscalPeriod())
                    .in(ObjectUtil.isNotEmpty(shopNames),StationManualAllocation::getShopName,shopNames)
                    .in(ObjectUtil.isNotEmpty(sites),StationManualAllocation::getSite,sites)
                    .isNull(StationManualAllocation::getAllocId)
                    .eq(StationManualAllocation::getConfirmStatus, BigDecimal.ONE)
                    .count()>0) {
                errorMsg.append("站内手动分摊、");
            }

            if (new LambdaQueryChainWrapper<>(noStationAllocationMapper)
                    .eq(ObjectUtil.isNotEmpty(param.getFiscalPeriod()), NoStationAllocation::getFiscalPeriod,param.getFiscalPeriod())
                    .in(ObjectUtil.isNotEmpty(shopNames),NoStationAllocation::getShopName,shopNames)
                    .in(ObjectUtil.isNotEmpty(sites),NoStationAllocation::getSite,sites)
                    .eq(NoStationAllocation::getConfirmStatus, BigDecimal.ONE)
                    .count()>0) {
                errorMsg.append("无需分摊站、");
            }


            if (new LambdaQueryChainWrapper<>(noAllocationAdjustMapper)
                    .eq(ObjectUtil.isNotEmpty(param.getFiscalPeriod()), NoAllocationAdjust::getFiscalPeriod,param.getFiscalPeriod())
                    .in(ObjectUtil.isNotEmpty(shopNames),NoAllocationAdjust::getShopName,shopNames)
                    .in(ObjectUtil.isNotEmpty(sites),NoAllocationAdjust::getSite,sites)
                    .eq(NoAllocationAdjust::getConfirmStatus, BigDecimal.ONE)
                    .count()>0) {
                errorMsg.append("无需分摊调整表、");
            }
            if (errorMsg.length() >0) {
                errorMsg.insert(0, "检测到存在已确认分摊表 : [");
                String errorStr = errorMsg.toString();
                return ResponseData.success(errorStr.substring(0, errorStr.length() - 1)+"] ");
            }


        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseData.error("检测是否存在分摊表"+e.getMessage());
        }
        return ResponseData.success();
    }


    @DataSource(name = "finance")
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseData shopSiteToAlloc(StatementIncomeParam param) {
        try {
            //初始化参数
            String fiscalPeriod = param.getFiscalPeriod();
            String shopName = param.getShopName();
            if (ObjectUtil.isEmpty(fiscalPeriod) && ObjectUtil.isEmpty(shopName) ) {
                return ResponseData.error("会计区间和店铺不能为空");
            }
            List<String> shopNames =  new ArrayList<>();
            shopNames.add(shopName);

            List<String> sites = ObjectUtil.isNotEmpty(param.getSites())?param.getSites(): new ArrayList<>();
            if (ObjectUtil.isNotEmpty(param.getSite())) {
                sites.add(param.getSite());
            }
            String operator = ObjectUtil.isNotEmpty(LoginContext.me().getLoginUser())?LoginContext.me().getLoginUser().getName():"系统生成";

            //是否有结算报告已确认
            if (new LambdaQueryChainWrapper<>(settlementRepoertMapper)
                    .eq(ObjectUtil.isNotEmpty(fiscalPeriod), SettlementReport::getFiscalPeriod, fiscalPeriod)
                    .in(ObjectUtil.isNotEmpty(shopNames), SettlementReport::getShopName, shopNames)
                    .in(ObjectUtil.isNotEmpty(sites), SettlementReport::getSite, sites)
                    .eq(SettlementReport::getConfirmStatus,BigDecimal.ONE)
                    .count() >0) {
                log.error(" 会计区间[{}]、账号[{}]、站点{} 存在已确认结算报告:" , fiscalPeriod, shopNames, sites);
                return ResponseData.error(StrUtil.format("会计区间[{}]、账号[{}]、站点{} 结算报告已确认,请先反审核!" , fiscalPeriod, shopNames, sites));
            }


            //删除下游原币和美金
            UpdateWrapper<SettlementDetailListing> settlementDetailUpdateWrapper = new UpdateWrapper<>();
            settlementDetailUpdateWrapper
                    .eq("SHOP_NAME", param.getShopName())
                    .eq("FISCAL_PERIOD", param.getFiscalPeriod())
                    .in(ObjectUtil.isNotEmpty(param.getSites()),"SITE",param.getSites());
            settlementDetailListingMapper.delete(settlementDetailUpdateWrapper);


            UpdateWrapper<SettlementDetailUsd> usdUpdateWrapper = new UpdateWrapper<>();
            usdUpdateWrapper
                    .eq("SHOP_NAME", param.getShopName())
                    .eq("FISCAL_PERIOD", param.getFiscalPeriod())
                    .in(ObjectUtil.isNotEmpty(param.getSites()),"SITE",param.getSites());
            settlementDetailUsdMapper.delete(usdUpdateWrapper);
            log.info("------------------------------0000000----------------------------------已删除原币和美金数据 : 会计区间[{}]、账号[{}]、站点{}  ", fiscalPeriod, shopNames, sites);

            //重新生成原币
            LambdaQueryWrapper<StatementIncome> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(StatementIncome::getFiscalPeriod, fiscalPeriod)
                    .eq(StatementIncome::getShopName, shopName)
                    .in(ObjectUtil.isNotEmpty(sites),StatementIncome::getSite, sites)
                    .ne(StatementIncome::getIncomeType,"预估冲回")
                    .in(StatementIncome::getSyncStatus,2,3);
            List<StatementIncome> incomeList = this.baseMapper.selectList(queryWrapper);
            if (ObjectUtil.isEmpty(incomeList)) {
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                log.error(StrUtil.format("无对应已确定收入记录: 会计区间[{}]、账号[{}]、站点{} ",fiscalPeriod, shopNames, sites));
                return ResponseData.success(StrUtil.format("无对应已确定收入记录: 会计区间[{}]、账号[{}]、站点{} ",fiscalPeriod, shopNames, sites));
            }
            incomeList.stream().filter(i -> "Data Range".equals(i.getReportType())).forEach(this::insertSettlementListing);
            incomeList.stream().filter(i -> "Settlement".equals(i.getReportType())).forEach(this::insertSettlementListing);


            //确认原币
            SettlementDetailListingParam settlementDetailListingParam = SettlementDetailListingParam.builder().fiscalPeriod(fiscalPeriod)
                    .shopName(shopName).sites(sites).confirmType(0).confirmBy(operator).build();
            ResponseData listingResponseData = settlementDetailListingService.confirmBatch(settlementDetailListingParam);
            if (listingResponseData.getCode() == 500) {
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                log.error("确认原币异常:" + listingResponseData.getMessage() + listingResponseData.getData());
                return ResponseData.error("确认原币异常: " + listingResponseData.getMessage()+listingResponseData.getData());
            } else{
                log.info("------------------------------11111----------------------------------确认原币成功 : 会计区间[{}]、账号[{}]、站点{}  \n 接口返回信息:[{}]", fiscalPeriod, shopNames, sites,listingResponseData.getMessage()+listingResponseData.getData());
                if ("无可确认的数据!".equals(listingResponseData.getData())) {
                    TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                    return ResponseData.success("无可确认的原币数据!");
                }
            }


            //自动确认美金
            settlementDetailUsdService.refreshExchangeRate();
            for (String shop : shopNames) {
                SettlementDetailUsdParam settlementDetailUsdParam = SettlementDetailUsdParam.builder().fiscalPeriod(fiscalPeriod)
                        .shopName(shop).sites(sites).confirmBy(operator).build();
                //先删除美金的下游分摊表主分摊行,改导入明细确认状态0未确认
                ResponseData delUsdResponse = settlementDetailUsdService.deleteAlloc(settlementDetailUsdParam);
                if (delUsdResponse.getCode() == 500) {
                    TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                    log.error("删除美金下游分摊表异常:" + delUsdResponse.getMessage()+delUsdResponse.getData());
                    return ResponseData.error("删除美金下游分摊表异常:" + delUsdResponse.getMessage()+delUsdResponse.getData());
                } else{
                    log.info("------------------------------222222----------------------------------删除美金下游分摊表成功 : 会计区间[{}]、账号[{}]、站点{}  \n 接口返回信息:[{}]", fiscalPeriod, shop, sites,delUsdResponse.getMessage()+delUsdResponse.getData());
                }
                //美金批量确认生成站内自动、站内手动、无需分摊、无需分摊调整数据
                ResponseData usdResponse = settlementDetailUsdService.confirmBatch(settlementDetailUsdParam);
                if (usdResponse.getCode() == 500) {
                    TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                    log.error("确认美金明细异常:" + usdResponse.getMessage()+usdResponse.getData());
                    return ResponseData.error("确认美金明细异常:" + usdResponse.getMessage()+usdResponse.getData());
                } else {
                    log.info("------------------------------333333----------------------------------确认美金成功 : 会计区间[{}]、账号[{}]、站点{}  \n 接口返回信息:[{}]", fiscalPeriod, shop, sites,usdResponse.getMessage()+usdResponse.getData());
                }
                //拉仓储和销毁移除和将广告费明细挂到分摊总行
                StationManualAllocationParam stationManualAllocationParam = StationManualAllocationParam.builder().fiscalPeriod(fiscalPeriod)
                        .shopName(shop).sites(sites).build();
                ResponseData pullStorageDisposeFeeResponse = stationManualAllocationService.pullStorageDisposeFee(stationManualAllocationParam);
                if (pullStorageDisposeFeeResponse.getCode() == 500) {
                    TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                    log.error("拉取仓储销毁移除费异常:" + pullStorageDisposeFeeResponse.getData());
                    return ResponseData.error("拉取仓储销毁移除费异常:" + pullStorageDisposeFeeResponse.getData());
                } else {
                    log.info("------------------------------4444444----------------------------------拉取仓储销毁移除费成功 : 会计区间[{}]、账号[{}]、站点{} \n 接口返回信息:[{}]:[{}]", fiscalPeriod, shop, sites,pullStorageDisposeFeeResponse.getMessage() + pullStorageDisposeFeeResponse.getData());
                }
                //将明细行重新赋值给主分摊行assignAllocId,手动分摊和更新广告费用
                settlementDetailListingMapper.assignAllocId(stationManualAllocationParam);
                log.info("------------------------------55555----------------------------------站内手动明细挂分摊行成功 : 会计区间[{}]、账号[{}]、站点{} \n", fiscalPeriod, shop, sites);

            }

        } catch (Exception e) {
            log.error(e.getMessage());
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return ResponseData.error("店铺站点维度重新生成分摊异常: "+e.getMessage());
        }
        return ResponseData.success();
    }



    @DataSource(name = "finance")
    @Transactional(rollbackFor = Exception.class)
    public void insertSettlementListing(StatementIncome income){
        log.info("收入记录表确认插入结算单原币入参[{}]", JSONObject.toJSON(income));

        //日期转换：会计期间
        DateFormat format=new SimpleDateFormat("yyyy-MM");

        //将SettlementDetail的sku为null的设置为0
        this.detailListingService.updateSettlementDetailSkuNullToZero();

        //将dataRangeDetail的sku为null的设置为0
        this.detailListingService.updateRangeDetailSkuNullToZero();

        //根据id和时间获取收入记录表信息
        /**
         * 目的是检查此时有多少类型的报告
         * 1、先来了settlement报告数据确认了，此时只会有一条确认数据；
         * 2、如果先来了data range报告数据确认了，会生成一条预估数据，等settlement报告来了数据确认了，就同时会生成确认数据，同时会根据原来生成预估数据取反生成预估冲回数据
         */
        List<StatementIncome> allIncome = this.baseMapper.getConfirmIncome(income);
            List<SettlementDetailListing> settlementDetailListings = new ArrayList<>();
            //预估或确认明细金额汇总
            if(income.getIncomeType().equals("预估"))
            {
                DatarangeDetailComfirm detail = new DatarangeDetailComfirm();
                detail.setSettlementId(income.getSettlementId());
                detail.setPlatform(format.format(income.getEndTime()));//用平台承载会计期间

                //根据SettlementId和会计期间获取DataRange金额
                List<DatarangeDetailComfirm> datarangeList = this.detailListingService.getDataRangeMoney(detail);

                //根据SettlementId和会计期间获取DataRange数量
                List<DatarangeDetailComfirm> numberList = this.detailListingService.getDataRangeNumber(detail);
                for (DatarangeDetailComfirm detail2 : datarangeList){
                    SettlementDetailListing datarangeListing=new SettlementDetailListing();
                    datarangeListing.setSettlementId(income.getSettlementId());
                    datarangeListing.setFiscalPeriod(income.getFiscalPeriod());
                    datarangeListing.setStartTime(income.getStartTime());
                    datarangeListing.setEndTime(income.getEndTime());
                    datarangeListing.setReportType(income.getReportType());
                    datarangeListing.setIncomeType(income.getIncomeType());
                    datarangeListing.setPlatformName(income.getPlatformName());
                    datarangeListing.setShopName(income.getShopName());
                    datarangeListing.setSite(income.getSite());
                    datarangeListing.setOriginalCurrency(income.getOriginalCurrency());

                    datarangeListing.setSku(detail2.getSku()==null?"0":detail2.getSku());
                    datarangeListing.setDepartment("0");
                    datarangeListing.setTeam("0");
                    datarangeListing.setMaterialCode("0");
                    datarangeListing.setSalesBrand("0");

                    datarangeListing.setSalesExcludingTax(detail2.getSalesPrincipal().add(detail2.getSalesOther()));
                    datarangeListing.setSalesPromotion(detail2.getSalesPromotion());
                    datarangeListing.setRefund(detail2.getRefund());
                    datarangeListing.setGiftwarpShipping(detail2.getGiftwarpShipping());
                    datarangeListing.setCommission(detail2.getCommission());
                    datarangeListing.setGoodwill(detail2.getGoodWill());
                    datarangeListing.setAmazonFees(detail2.getAmazonFee());
                    datarangeListing.setReimbursement(detail2.getReimbursement());
                    datarangeListing.setOther(detail2.getOther());
                    datarangeListing.setWithheldTax(detail2.getWithheldTax());
                    datarangeListing.setAdvertising(detail2.getAdvertising().add(detail2.getLightningDealFee()));

                    datarangeListing.setTax(detail2.getSalesTax());
                    datarangeListing.setRefundPromotion(detail2.getRefundPromotion());
                    datarangeListing.setRefundCommission(detail2.getRefundCommission());
                    datarangeListing.setStorageFee(detail2.getStorageFee());
                    datarangeListing.setDisposeFee(detail2.getDisposalFee());
                    datarangeListing.setRemovalDeal(detail2.getRemovalFee());
                    datarangeListing.setVolumeNormal(BigDecimal.ZERO);

                    for (DatarangeDetailComfirm dataNum : numberList) {
                        if(dataNum.getSku()==null){
                            dataNum.setSku("0");
                        }
                        if(datarangeListing.getSku().equals(dataNum.getSku())){
                            datarangeListing.setVolumeNormal(datarangeListing.getVolumeNormal().add(new BigDecimal(dataNum.getQuantityPurchased())));
                        }
                    }
                    settlementDetailListings.add(datarangeListing);
                }
            }
            if(allIncome.size()==1){
                if(income.getIncomeType().equals("确认"))
                {
                    SettlementDetail detail = new SettlementDetail();
                    detail.setSettlementId(income.getSettlementId());
                    detail.setPlatform(format.format(income.getEndTime()));//用平台承载会计期间

                    //根据SettlementId和会计期间获取Settlement金额
                    List<SettlementDetail> DetailList = this.detailListingService.getSettlementMoney(detail);

                    //根据SettlementId和会计期间获取Settlement数量
                    List<SettlementDetail> DetailNumberList = this.detailListingService.getSettlementNumber(detail);
                    for (SettlementDetail detail2 : DetailList){

                        SettlementDetailListing detailListing=new SettlementDetailListing();
                        detailListing.setSettlementId(income.getSettlementId());
                        detailListing.setFiscalPeriod(income.getFiscalPeriod());
                        detailListing.setStartTime(income.getStartTime());
                        detailListing.setEndTime(income.getEndTime());
                        detailListing.setReportType(income.getReportType());
                        detailListing.setIncomeType(income.getIncomeType());
                        detailListing.setPlatformName(income.getPlatformName());
                        detailListing.setShopName(income.getShopName());
                        detailListing.setSite(income.getSite());
                        detailListing.setOriginalCurrency(income.getOriginalCurrency());

                        detailListing.setShopName(detail2.getSysShopsName());
                        detailListing.setSite(detail2.getSysSite());
                        detailListing.setSku(detail2.getSku()==null?"0":detail2.getSku());
                        detailListing.setDepartment("0");
                        detailListing.setTeam("0");
                        detailListing.setMaterialCode("0");
                        detailListing.setSalesBrand("0");

                        detailListing.setSalesExcludingTax(detail2.getSalesPrincipal().add(detail2.getSalesOther()));
                        detailListing.setSalesPromotion(detail2.getSalesPromotion());
                        detailListing.setRefund(detail2.getRefund());
                        detailListing.setGiftwarpShipping(detail2.getGiftwarpShipping());
                        detailListing.setCommission(detail2.getCommission());
                        detailListing.setGoodwill(detail2.getGoodWill());
                        detailListing.setAmazonFees(detail2.getAmazonFee());
                        detailListing.setReimbursement(detail2.getReimbursement());
                        detailListing.setOther(detail2.getOther());
                        detailListing.setWithheldTax(detail2.getWithheldTax());
                        detailListing.setAdvertising(detail2.getAdvertising().add(detail2.getLightningDealFee()));

                        detailListing.setTax(detail2.getSalesTax());
                        detailListing.setRefundPromotion(detail2.getRefundPromotion());
                        detailListing.setRefundCommission(detail2.getRefundCommission());
                        detailListing.setStorageFee(detail2.getStorageFee());
                        detailListing.setDisposeFee(detail2.getDisposalFee());
                        detailListing.setRemovalDeal(detail2.getRemovalFee());

                        for (SettlementDetail detail3 : DetailNumberList) {
                            if(detail3.getSku()==null){
                                detail3.setSku("0");
                            }
                            if(detailListing.getSku().equals(detail3.getSku()) && detail3.getType()!=null && detail3.getType().equals("Normal")){
                                detailListing.setVolumeNormal(detail3.getQuantityPurchased());
                            }
                            if(detailListing.getSku().equals(detail3.getSku()) && detail3.getType()!=null && detail3.getType().equals("Reissue")){
                                detailListing.setVolumeReissue(detail3.getQuantityPurchased());
                            }
                        }
                        settlementDetailListings.add(detailListing);
                    }
                }
            }else if(allIncome.size() == 3 && !income.getIncomeType().equals("预估")) {//存在预估冲回的，生成调整类型的
                StatementIncome confirmIncome = null;
                StatementIncome rangeIncome= null;
                //两条都审核才生成调整的
                for (StatementIncome come : allIncome) {
                    if(come.getIncomeType().equals("确认")){
                        confirmIncome = come;
                        if(come.getSyncStatus() != 2 && come.getSyncStatus() != 3){
                            return;
                        }
                    }
                    if(come.getIncomeType().equals("预估冲回")){
                        rangeIncome = come;
                        if(come.getSyncStatus() != 2 && come.getSyncStatus() != 3){
                            return;
                        }
                    }
                }

                //根据SettlementId和会计期间获取Settlement金额（确认）
                SettlementDetail detailSettlement = new SettlementDetail();
                detailSettlement.setSettlementId(confirmIncome.getSettlementId());
                detailSettlement.setPlatform(format.format(confirmIncome.getEndTime()));//用平台承载会计期间
                List<SettlementDetail> DetailList = this.detailListingService.getSettlementMoney(detailSettlement);

                //根据SettlementId和会计期间获取DataRange金额（预估）
                DatarangeDetailComfirm detailDataRange = new DatarangeDetailComfirm();
                if (ObjectUtil.isEmpty(rangeIncome)) {
                    log.info(ObjectUtil.toString(income));
                }
                detailDataRange.setSettlementId(rangeIncome.getSettlementId());
                detailDataRange.setPlatform(format.format(rangeIncome.getEndTime()));//用平台承载会计期间
                List<DatarangeDetailComfirm> datarangeList = this.detailListingService.getDataRangeMoney(detailDataRange);

                //根据SettlementId和会计期间获取Settlement（确认）和DataRange（预估）数量
                List<SettlementDetailResult> settlementDataRangeNumberList =
                         this.detailListingService.getSettlementDataRangeNumber(detailSettlement,detailDataRange);

                //根据SettlementId和会计期间获取Settlement（确认）和DataRange（预估）金额（以DataRange为主，取差集）
                List<DatarangeDetailComfirm> dataRangeDiffrencelist =
                    this.detailListingService.getDataRangeDiffrencelist(detailSettlement,detailDataRange);

                /**
                 * 1、先获取Settlement金额（确认类型），再获取SDataRange金额（预估类型），通过【sku】关联Settlement（确认）金额和DataRange（预估）金额【作差】（以Settlement为主）
                 * 2、再根据SettlementId和会计期间获取Settlement（确认）和DataRange（预估）金额（以DataRange为主，取差集）
                 */
                //先获取Settlement金额（确认类型）
                for (SettlementDetail detail2 : DetailList) {
                    //处理金额
                    SettlementDetailListing detailListing = new SettlementDetailListing();
                    detailListing.setSettlementId(confirmIncome.getSettlementId());
                    detailListing.setFiscalPeriod(confirmIncome.getFiscalPeriod());
                    detailListing.setStartTime(confirmIncome.getStartTime());
                    detailListing.setEndTime(confirmIncome.getEndTime());
                    detailListing.setReportType("");
                    detailListing.setIncomeType("调整");
                    detailListing.setPlatformName(confirmIncome.getPlatformName());
                    detailListing.setShopName(confirmIncome.getShopName());
                    detailListing.setSite(confirmIncome.getSite());
                    detailListing.setOriginalCurrency(confirmIncome.getOriginalCurrency());
                    detailListing.setSku(detail2.getSku() == null ? "0" : detail2.getSku());
                    detailListing.setDepartment("0");
                    detailListing.setTeam("0");
                    detailListing.setMaterialCode("0");
                    detailListing.setSalesBrand("0");

                    detailListing.setSalesExcludingTax(detail2.getSalesPrincipal().add(detail2.getSalesOther()));
                    detailListing.setSalesPromotion(detail2.getSalesPromotion());
                    detailListing.setRefund(detail2.getRefund());
                    detailListing.setGiftwarpShipping(detail2.getGiftwarpShipping());
                    detailListing.setCommission(detail2.getCommission());
                    detailListing.setGoodwill(detail2.getGoodWill());
                    detailListing.setAmazonFees(detail2.getAmazonFee());
                    detailListing.setReimbursement(detail2.getReimbursement());
                    detailListing.setOther(detail2.getOther());
                    detailListing.setWithheldTax(detail2.getWithheldTax());
                    detailListing.setAdvertising(detail2.getAdvertising().add(detail2.getLightningDealFee()));

                    detailListing.setTax(detail2.getSalesTax());
                    detailListing.setRefundPromotion(detail2.getRefundPromotion());
                    detailListing.setRefundCommission(detail2.getRefundCommission());
                    detailListing.setStorageFee(detail2.getStorageFee());
                    detailListing.setDisposeFee(detail2.getDisposalFee());
                    detailListing.setRemovalDeal(detail2.getRemovalFee());

                    //再获取SDataRange金额（预估类型）
                    for (DatarangeDetailComfirm detail3 : datarangeList) {
                        if(detail3.getSku()==null){
                            detail3.setSku("0");
                        }
                        //通过sku关联Settlement（确认）金额和DataRange（预估）金额，作差
                        if(detailListing.getSku().equals(detail3.getSku())){
                            detailListing.setSalesExcludingTax(detailListing.getSalesExcludingTax().subtract(detail3.getSalesPrincipal()).subtract(detail3.getSalesOther()));
                            detailListing.setSalesPromotion(detailListing.getSalesPromotion().subtract(detail3.getSalesPromotion()));
                            detailListing.setRefund(detailListing.getRefund().subtract(detail3.getRefund()));
                            detailListing.setGiftwarpShipping(detailListing.getGiftwarpShipping().subtract(detail3.getGiftwarpShipping()));
                            detailListing.setCommission(detailListing.getCommission().subtract(detail3.getCommission()));
                            detailListing.setGoodwill(detailListing.getGoodwill().subtract(detail3.getGoodWill()));
                            detailListing.setAmazonFees(detailListing.getAmazonFees().subtract(detail3.getAmazonFee()));
                            detailListing.setReimbursement(detailListing.getReimbursement().subtract(detail3.getReimbursement()));
                            detailListing.setOther(detailListing.getOther().subtract(detail3.getOther()));
                            detailListing.setWithheldTax(detailListing.getWithheldTax().subtract(detail3.getWithheldTax()));
                            detailListing.setAdvertising(detailListing.getAdvertising().subtract(detail3.getAdvertising()).subtract(detail3.getLightningDealFee()));

                            detailListing.setTax(detailListing.getTax().subtract(detail3.getSalesTax()));
                            detailListing.setRefundPromotion(detailListing.getRefundPromotion().subtract(detail3.getRefundPromotion()));
                            detailListing.setRefundCommission(detailListing.getRefundCommission().subtract(detail3.getRefundCommission()));
                            detailListing.setStorageFee(detailListing.getStorageFee().subtract(detail3.getStorageFee()));
                            detailListing.setDisposeFee(detailListing.getDisposeFee().subtract(detail3.getDisposalFee()));
                            detailListing.setRemovalDeal(detailListing.getRemovalDeal().subtract(detail3.getRemovalFee()));

                            detail3.setPlatform("ExistSettlement");
                        }
                    }

                    //处理数量
                    for (SettlementDetailResult settlementDataRangeNumber : settlementDataRangeNumberList) {
                        if (settlementDataRangeNumber.getSku() == null) {
                            settlementDataRangeNumber.setSku("0");
                        }
                        if (settlementDataRangeNumber.getSkuRange() == null) {
                            settlementDataRangeNumber.setSkuRange("0");
                        }
                        //settlement金额的sku和settlement数量的sku相等
                        if(detailListing.getSku().equals(settlementDataRangeNumber.getSku())){
                            if (settlementDataRangeNumber.getType() != null) {
                                //Normal
                                if (settlementDataRangeNumber.getType().equals("Normal")){
                                    if (!settlementDataRangeNumber.getSku().equals(settlementDataRangeNumber.getSkuRange())) {
                                        //settlement数量和DataRange数量的sku不相等，取settlement数量
                                        detailListing.setVolumeNormal(settlementDataRangeNumber.getQuantityPurchased());
                                    } else {
                                        //settlement数量和DataRange数量的sku相等，settlement和DataRange的数量作差
                                        detailListing.setVolumeNormal(settlementDataRangeNumber.getQuantityPurchased().
                                                subtract(settlementDataRangeNumber.getQuantityPurchasedRange()));
                                    }
                                }
                                if(settlementDataRangeNumber.getQuantityPurchasedRange() != null && detailListing.getVolumeNormal() == null){
                                    detailListing.setVolumeNormal(settlementDataRangeNumber.getQuantityPurchasedRange().negate());
                                }
                                //Reissue
                                if(settlementDataRangeNumber.getType().equals("Reissue")) {
                                    detailListing.setVolumeReissue(settlementDataRangeNumber.getQuantityPurchased());
                                }
                            }else if(settlementDataRangeNumber.getType() == null && detailListing.getVolumeNormal() == null && settlementDataRangeNumber.getQuantityPurchasedRange() != null){
                                detailListing.setVolumeNormal(settlementDataRangeNumber.getQuantityPurchasedRange().negate());
                            }
                        }
                    }
                    settlementDetailListings.add(detailListing);
                }

                //datarange中存在settlement中不存在的
                for (DatarangeDetailComfirm detail3 : dataRangeDiffrencelist) {
                    if(detail3.getPlatform() ==null){
                        SettlementDetailListing detailListing = new SettlementDetailListing();
                        detailListing.setSettlementId(confirmIncome.getSettlementId());
                        detailListing.setFiscalPeriod(confirmIncome.getFiscalPeriod());
                        detailListing.setStartTime(rangeIncome.getStartTime());
                        detailListing.setEndTime(rangeIncome.getEndTime());
                        detailListing.setReportType("");
                        detailListing.setIncomeType("调整");
                        detailListing.setPlatformName(confirmIncome.getPlatformName());
                        detailListing.setShopName(confirmIncome.getShopName());
                        detailListing.setSite(confirmIncome.getSite());
                        detailListing.setOriginalCurrency(rangeIncome.getOriginalCurrency());
                        detailListing.setSku(detail3.getSku() == null ? "0" : detail3.getSku());
                        detailListing.setDepartment("0");
                        detailListing.setTeam("0");
                        detailListing.setMaterialCode("0");
                        detailListing.setSalesBrand("0");

                        detailListing.setSalesExcludingTax(detail3.getSalesPrincipal().negate().subtract(detail3.getSalesOther()));
                        detailListing.setSalesPromotion(detail3.getSalesPromotion().negate());
                        detailListing.setRefund(detail3.getRefund().negate());
                        detailListing.setGiftwarpShipping(detail3.getGiftwarpShipping().negate());
                        detailListing.setCommission(detail3.getCommission().negate());
                        detailListing.setGoodwill(detail3.getGoodWill().negate());
                        detailListing.setAmazonFees(detail3.getAmazonFee().negate());
                        detailListing.setReimbursement(detail3.getReimbursement().negate());
                        detailListing.setOther(detail3.getOther().negate());
                        detailListing.setWithheldTax(detail3.getWithheldTax().negate());
                        detailListing.setAdvertising(detail3.getAdvertising().negate().subtract(detail3.getLightningDealFee()));

                        detailListing.setTax(detail3.getSalesTax().negate());
                        detailListing.setRefundPromotion(detail3.getRefundPromotion().negate());
                        detailListing.setRefundCommission(detail3.getRefundCommission().negate());
                        detailListing.setStorageFee(detail3.getStorageFee().negate());
                        detailListing.setDisposeFee(detail3.getDisposalFee().negate());
                        detailListing.setRemovalDeal(detail3.getRemovalFee().negate());

                        for (SettlementDetailResult settlementDataRangeNumber : settlementDataRangeNumberList) {
                            if(detailListing.getSku().equals(settlementDataRangeNumber.getSkuRange()) && settlementDataRangeNumber.getQuantityPurchasedRange()!=null) {
                               detailListing.setVolumeNormal(settlementDataRangeNumber.getQuantityPurchasedRange().negate());
                            }
                        }
                        settlementDetailListings.add(detailListing);
                    }
                }
            }
            detailListingService.saveBatch(settlementDetailListings);
    }

    @DataSource(name = "finance")
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void toReceive(StatementIncomeParam param) throws Exception {

        StatementIncome income = this.baseMapper.selectById(param.getId());

        LoginContext current= SpringContext.getBean(LoginContext.class);
        LoginUser currentUser = current.getLoginUser();

        //插入应收明细
        ReceivableDetail detailParam = new ReceivableDetail();
        detailParam.setPlatformName(income.getPlatformName());
        detailParam.setShopName(income.getShopName());
        detailParam.setSite(income.getSite());
        detailParam.setFiscalPeriod(income.getFiscalPeriod());
        receivableDetailService.generateReceivable(detailParam);

        QueryWrapper<ReceivableDetail> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("PLATFORM_NAME",detailParam.getPlatformName()).eq("SHOP_NAME",detailParam.getShopName())
                .eq("SITE",detailParam.getSite()).eq("FISCAL_PERIOD",detailParam.getFiscalPeriod());
        //应收明细会计期间
        ReceivableDetail  receivableDetail = receivableDetailService.getBaseMapper().selectOne(queryWrapper);

        ReceivableDetailDetail detailDetail = new ReceivableDetailDetail();
        detailDetail.setReceivableId(receivableDetail.getId());

        detailDetail.setSettlementId(income.getSettlementId());

        detailDetail.setIncomeType(income.getIncomeType());
        detailDetail.setOriginalCurrency(income.getOriginalCurrency());
        detailDetail.setProceedsCurrency(income.getOriginalCurrency());
        detailDetail.setCurrentReserveAmount(income.getCurrentReceivableAmount());
        detailDetail.setSuccessfulCharge(income.getSuccessfulCharge());

        detailDetail.setCreateAt(new Date());
        detailDetail.setCreateBy(currentUser.getName());
        receivableDetailDetailService.save(detailDetail);

        //刷取应收明细金额
        receivableDetailService.refresh(detailParam);
    }

    @DataSource(name = "finance")
    @Override
    public void toChongHui(StatementIncomeParam param) {

        StatementIncome in = this.baseMapper.selectById(param.getId());

        BigDecimal zero = BigDecimal.ZERO;
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
        incomeService.save(in);
    }

    private Page getPageContext() {
        return PageFactory.defaultPage();
    }

}
