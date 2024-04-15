package com.tadpole.cloud.platformSettlement.modular.finance.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import cn.stylefeng.guns.cloud.libs.context.auth.LoginContext;
import cn.stylefeng.guns.cloud.libs.mp.page.PageFactory;
import cn.stylefeng.guns.cloud.model.page.PageResult;
import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import cn.stylefeng.guns.cloud.system.core.dbs.annotation.DataSource;
import com.tadpole.cloud.externalSystem.api.lingxing.model.req.sourcereport.TransactionReq;
import com.tadpole.cloud.externalSystem.api.lingxing.model.resp.LingXingBaseRespData;
import com.tadpole.cloud.platformSettlement.api.finance.entity.*;
import com.tadpole.cloud.platformSettlement.modular.finance.consumer.LingXingSourceReportConsumer;
import com.tadpole.cloud.platformSettlement.modular.finance.enums.LxShopSynStatus;
import com.tadpole.cloud.platformSettlement.modular.finance.enums.LxShopSynType;
import com.tadpole.cloud.platformSettlement.modular.finance.mapper.FinancialSiteMapper;
import com.tadpole.cloud.platformSettlement.modular.finance.mapper.LingxingDatarangeMapper;
import com.tadpole.cloud.platformSettlement.modular.finance.mapper.ShopCurrencyMapper;
import com.tadpole.cloud.platformSettlement.api.finance.model.params.LingxingDatarangeParam;
import com.tadpole.cloud.platformSettlement.api.finance.model.result.FinancialSiteResult;
import com.tadpole.cloud.platformSettlement.api.finance.model.result.LingxingDatarangeResult;
import com.tadpole.cloud.platformSettlement.api.finance.model.result.ShopCurrencyResult;
import com.tadpole.cloud.platformSettlement.modular.finance.service.ICwLingxingShopInfoService;
import com.tadpole.cloud.platformSettlement.modular.finance.service.ILingxingDatarangeService;
import com.tadpole.cloud.platformSettlement.modular.finance.service.ILxShopSynRecordService;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import javax.annotation.Resource;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
* <p>
*  服务实现类
* </p>
*
* @author gal
* @since 2022-04-28
*/
@Slf4j
@Service
public class LingxingDatarangeServiceImpl extends ServiceImpl<LingxingDatarangeMapper, LingxingDatarange> implements ILingxingDatarangeService {

    @Autowired
    private LingxingDatarangeMapper mapper;

    @Autowired
    private LingXingSourceReportConsumer lingXingSourceReportConsumer;

    @Autowired
    private ICwLingxingShopInfoService lingxingShopInfoService;

    @Autowired
    private ReportUploadRecordServiceImpl uploadRecordService;

    @Autowired
    private SettlementReportCheckServiceImpl checkRecordService;

    @Resource
    private FinancialSiteMapper financialMapper;

    @Resource
    private ShopCurrencyMapper shopCurrencyMapper;

    @Resource
    private ILxShopSynRecordService lxShopSynRecordService;

    @DataSource(name = "finance")
    @Override
    public List<FinancialSiteResult> getPlatform() { return financialMapper.getPlatform(); }

    @DataSource(name = "finance")
    @Override
    public List<ShopCurrencyResult> getShop() { return shopCurrencyMapper.getShop(); }

    @DataSource(name = "finance")
    @Override
    public List<FinancialSiteResult> getSite() {
        return financialMapper.getSite();
    }

    @DataSource(name = "finance")
    @Override
    public List<Map<String, Object>> getSettlementIdSelect(LingxingDatarangeParam param) {
        return this.baseMapper.getSettlementIdSelect(param);
    }

    @DataSource(name = "finance")
    @Override
    public PageResult<LingxingDatarangeResult> findPageBySpec(LingxingDatarangeParam param) {
        String start = " 00:00:00";
        String end = " 23:59:59";
        if(StringUtils.isNotEmpty(param.getStartTime())){
            param.setStartTime(param.getStartTime() + start);
        }
        if(StringUtils.isNotEmpty(param.getEndTime())){
            param.setEndTime(param.getEndTime() + end);
        }
        IPage<LingxingDatarangeResult> page = this.baseMapper.findPageBySpec(PageFactory.defaultPage(), param);
        return new PageResult<>(page);
    }

    @DataSource(name = "finance")
    @Override
    public List<LingxingDatarangeResult> list(LingxingDatarangeParam param) {
        Page pageContext = PageFactory.defaultPage();
        pageContext.setSize(Integer.MAX_VALUE);
        String start = " 00:00:00";
        String end = " 23:59:59";
        if(StringUtils.isNotEmpty(param.getStartTime())){
            param.setStartTime(param.getStartTime() + start);
        }
        if(StringUtils.isNotEmpty(param.getEndTime())){
            param.setEndTime(param.getEndTime() + end);
        }
        IPage<LingxingDatarangeResult> page = this.baseMapper.findPageBySpec(pageContext, param);
        return page.getRecords();
    }

    @DataSource(name = "finance")
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseData verifyReportRecords(LingxingDatarangeParam param){

        if(StrUtil.isEmpty(param.getYearMonth()) || StrUtil.isEmpty(param.getSettlementId()))
        {
            return ResponseData.error("月份和结算ID不能为空");
        }

        QueryWrapper<LingxingDatarange> ld=new QueryWrapper<>();
        ld.eq("report_date_month",param.getYearMonth())
                .eq("SETTLEMENT_ID",param.getSettlementId());
        List<LingxingDatarange> datarangeList=this.baseMapper.selectList(ld);
        //月份筛选报告日期开始时间和结束时间
        if(this.count(ld)>0){
            List<Date> reportDates=datarangeList.stream().map(LingxingDatarange::getDateTime).collect(Collectors.toList());
            Date minDate=Collections.min(reportDates);
            Date maxDate=Collections.max(reportDates);

            QueryWrapper<SettlementReportCheck> qw=new QueryWrapper<>();
            qw.eq("SHOP_NAME",param.getShopName())
                    .eq("SITE",param.getSite())
                    .eq("SETTLEMENT_ID",param.getSettlementId())
                    .ge("START_TIME",minDate)
                    .le("END_TIME",maxDate)
                    .ne("STATUS",2);

            if(checkRecordService.count(qw)>0){
                return ResponseData.error("报告已存在");
            }
        }
        else
        {
            return ResponseData.error("源数据为空");
        }
        return ResponseData.success();
    }

    @DataSource(name = "finance")
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseData generateReportRecords(LingxingDatarangeParam param,String bankAccount){

        String RemittanceBank="",RemittanceAccount="";
        if(StrUtil.isEmpty(param.getYearMonth()) || StrUtil.isEmpty(param.getSettlementId()))
        {
            return ResponseData.error("月份和结算ID不能为空");
        }

        QueryWrapper<LingxingDatarange> qw=new QueryWrapper<>();
        qw.eq("report_date_month",param.getYearMonth())
          .eq("SETTLEMENT_ID",param.getSettlementId());
        List<LingxingDatarange> datarangeList=this.baseMapper.selectList(qw);

        if(this.count(qw)>0){

            if (bankAccount.contains("-"))
            {
                String[] str1=bankAccount.split("-");
                RemittanceBank=str1[0];
                RemittanceAccount=str1[1];
            }

            //月份筛选报告日期开始时间和结束时间
            List<Date> reportDates=datarangeList.stream().map(LingxingDatarange::getDateTime).collect(Collectors.toList());
            Date minDate=Collections.min(reportDates);
            Date maxDate=Collections.max(reportDates);

            //生成校验AZ报告审核记录
            QueryWrapper<SettlementReportCheck> sr=new QueryWrapper<>();
            sr.eq("SHOP_NAME",param.getShopName())
                    .eq("SITE",param.getSite())
                    .eq("SETTLEMENT_ID",param.getSettlementId())
                    .ge("START_TIME",minDate)
                    .le("END_TIME",maxDate)
                    .ne("STATUS",2);

            if(checkRecordService.count(sr)>0){
                return ResponseData.error("报告已存在");
            }

            String month=String.format(Locale.US,"%tb",minDate);
            String minDay=String.format(Locale.US,"%td",minDate);
            String maxDay=String.format(Locale.US,"%td",maxDate);

            //拼接生成文件名称
            StringBuilder sb=new StringBuilder();
            sb.append(DateUtil.year(minDate)+month+minDay);
            sb.append("-");
            sb.append(DateUtil.year(minDate)+month+maxDay);
            sb.append("CustomTransaction ");
            sb.append(param.getShopName()+"_"+param.getSite());

            //金额汇总
            BigDecimal totalMoney=datarangeList.stream().map(LingxingDatarange::getTotal).reduce(BigDecimal::add).get();

            //收款币种
            String proceedsCurrency=mapper.selectProceedsCurrency(param.getShopName(),param.getSite(),datarangeList.get(0).getCurrency());

            //1-1、领星Datarange生成AZ报告上传记录
            ReportUploadRecord uploadRecord=new ReportUploadRecord();
            BeanUtil.copyProperties(param,uploadRecord);

            uploadRecord.setFilePath(sb.toString());
            uploadRecord.setStartTime(minDate);
            uploadRecord.setEndTime(maxDate);
            uploadRecord.setOriginalCurrency(datarangeList.get(0).getCurrency());
            uploadRecord.setParseStatus("解析成功");
            uploadRecord.setUploadType("API上传");
            uploadRecord.setReportType("Data Range");
            uploadRecord.setCreateBy(LoginContext.me().getLoginUser().getName());
            uploadRecord.setCreateAt(new Date());
            uploadRecordService.save(uploadRecord);

            //1-2、领星Datarange生成AZ结算报告审核
            SettlementReportCheck reportCheck=new SettlementReportCheck();
            BeanUtil.copyProperties(param,reportCheck);

            reportCheck.setStartTime(minDate);
            reportCheck.setEndTime(maxDate);
            reportCheck.setMoney(totalMoney);
            reportCheck.setTotalMoney(totalMoney);
            reportCheck.setRemittanceAccount(RemittanceAccount);
            reportCheck.setRemittanceBank(RemittanceBank);
            uploadRecord.setReportType("Data Range");
            reportCheck.setOriginalCurrency(datarangeList.get(0).getCurrency());
            reportCheck.setProceedsCurrency(proceedsCurrency);
            reportCheck.setStatus(1);
            reportCheck.setVerifyBy(LoginContext.me().getLoginUser().getName());
            reportCheck.setCreateAt(new Date());
            checkRecordService.save(reportCheck);

            //2、领星生成数据，明细记录数据写入Datarange表
            try{
                mapper.lingXingToDatarange(reportCheck);
                mapper.lingXingToDataRangeDetailComfirm(param);
            } catch (Exception  e) {
                e.printStackTrace();
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                return ResponseData.error("刷新失败!:"+e);
            }
        }

        return ResponseData.success();
    }

    @DataSource(name = "finance")
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseData generateDateRangeRecords(TransactionReq param) {
        param.setLength(200000);//设置获取数据记录数

        LxShopSynRecord lxShopSynRecord = new LxShopSynRecord();
        String synDate = DateUtil.format(new Date(), DatePattern.PURE_DATE_PATTERN);
        lxShopSynRecord.setSynDate(synDate);
        lxShopSynRecord.setSynType(LxShopSynType.TRANSACTION.getCode());

        List<String> monthsDayList = new ArrayList<>(31);
        if(null != param.getSid()){
            //指定店铺sid，用于手动处理指定店铺和时间下载异常处理
            lxShopSynRecord.setSid(new BigDecimal(param.getSid()));
        }

        //系统默认执行所有的店铺获取上一个月数据
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MONTH, -1);
        cal.set(Calendar.DAY_OF_MONTH,1);
        int count = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
        for (int day = 1; day <= count ; day++) {
            cal.set(Calendar.DAY_OF_MONTH, day);
            monthsDayList.add(DateUtil.format(cal.getTime(), DatePattern.NORM_DATE_PATTERN));
        }

        //获取需要获取交易明细的领星店铺sid
        List<CwLingxingShopInfo> lingXingShopList = lingxingShopInfoService.getLxShopInfoBySynType(lxShopSynRecord);
        if(CollectionUtil.isNotEmpty(lingXingShopList)){
            for (CwLingxingShopInfo lingXingShopInfo : lingXingShopList) {
                param.setSid(lingXingShopInfo.getSid().longValue());
                dealGetLxDateRange(param, lingXingShopInfo, synDate, monthsDayList);
            }
        }
        return ResponseData.success();
    }

    /**
     * 处理获取领星交易明细
     * @param param
     * @param lingXingShopInfo
     * @param synDate
     */
    private void dealGetLxDateRange(TransactionReq param, CwLingxingShopInfo lingXingShopInfo, String synDate, List<String> monthsDayList) {
        UpdateWrapper<LxShopSynRecord> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("SID", lingXingShopInfo.getSid())
                .eq("SYN_DATE", synDate)
                .eq("SYN_TYPE", LxShopSynType.TRANSACTION.getCode());

        LxShopSynRecord updateShopSynRecord = new LxShopSynRecord();
        updateShopSynRecord.setSynStatus(LxShopSynStatus.ERROR.getCode());//异常

        //根据店铺sid和文件日期获取dateRange数据
        try {
            //单个店铺上个月所有数据
            List<LingxingDatarange> data = new ArrayList<>();
            for (String day : monthsDayList) {
                //设置报表日期
                param.setEvent_date(day);
                LingXingBaseRespData baseRespData = lingXingSourceReportConsumer.transaction(param);
                if (LingXingBaseRespData.DEFAULT_SUCCESS_CODE.equals(baseRespData.getCode())) {
                    List<Object> objList = baseRespData.getData();
                    if(CollectionUtil.isNotEmpty(objList)){
                        for (Object obj : objList) {
                            String jsonObject = JSON.toJSONString(obj);
                            LingxingDatarange cwLingxingDatarange = JSONObject.parseObject(jsonObject, LingxingDatarange.class);
                            //转换日期为标准时间格式
                            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
                            Date dateStr = simpleDateFormat.parse(cwLingxingDatarange.getDateIso());
                            cwLingxingDatarange.setDateTime(dateStr);
                            cwLingxingDatarange.setShopName(lingXingShopInfo.getShopName());
                            cwLingxingDatarange.setSite(lingXingShopInfo.getSite());
                            cwLingxingDatarange.setCreateDate(new Date());
                            data.add(cwLingxingDatarange);
                        }
                    }else{
                        log.info("获取领星交易明细返回数据为空，账号[{}]、站点[{}]、SID[{}]、报表日期[{}]", lingXingShopInfo.getShopName(), lingXingShopInfo.getSite(), param.getSid(), day);
                    }
                }
            }

            //批量保存入库
            if(CollectionUtil.isNotEmpty(data)){
                this.saveBatch(data);
                //SID对应的交易明细处理完成
                updateShopSynRecord.setSynStatus(LxShopSynStatus.SUCCESS.getCode());
            }else{
                //根据sid上月所有日期获取交易明细没有数据
                updateShopSynRecord.setSynStatus(LxShopSynStatus.NONE.getCode());
            }
            //处理完账号站点（sid）的价交易明细后，更新sid的任务执行情况
            updateShopSynRecord.setUpdateDate(new Date());
            lxShopSynRecordService.update(updateShopSynRecord, updateWrapper);
        } catch (Exception e) {
            log.info(e.getMessage());
        }
    }

    @DataSource(name = "EBMS")
    @Override
    public String getBankAccount(LingxingDatarangeParam param) {
        return this.baseMapper.getBankAccount(param);

    }
}
