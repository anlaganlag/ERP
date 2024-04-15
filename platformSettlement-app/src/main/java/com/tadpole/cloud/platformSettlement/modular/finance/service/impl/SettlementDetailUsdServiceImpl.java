package com.tadpole.cloud.platformSettlement.modular.finance.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.stylefeng.guns.cloud.libs.context.auth.LoginContext;
import cn.stylefeng.guns.cloud.libs.mp.page.PageFactory;
import cn.stylefeng.guns.cloud.model.page.PageResult;
import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import cn.stylefeng.guns.cloud.system.core.dbs.annotation.DataSource;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tadpole.cloud.platformSettlement.api.finance.entity.*;
import com.tadpole.cloud.platformSettlement.api.finance.model.params.SettlementDetailUsdParam;
import com.tadpole.cloud.platformSettlement.api.finance.model.params.SpotExchangeRateParam;
import com.tadpole.cloud.platformSettlement.api.finance.model.result.SettlementDetailUsdResult;
import com.tadpole.cloud.platformSettlement.modular.finance.mapper.*;
import com.tadpole.cloud.platformSettlement.modular.finance.service.*;
import com.tadpole.cloud.platformSettlement.modular.sales.entity.InventoryDemand;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.BoundValueOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
* <p>
* 结算单明细(美金) 服务实现类
* </p>
*
* @author gal
* @since 2021-12-24
*/
@Slf4j
@Service
public class SettlementDetailUsdServiceImpl extends ServiceImpl<SettlementDetailUsdMapper, SettlementDetailUsd> implements ISettlementDetailUsdService {

    @Autowired
    ISpotExchangeRateService rateService;

    @Autowired
    IStationAutoAllocationService autoAllocationService;

    @Autowired
    INoAllocationAdjustService noAllocationAdjustService;

    @Autowired
    INoStationAllocationService noStationAllocationService;

    @Autowired
    IStationManualAllocationService stationManualAllocationService;

    @Autowired
    IManualAllocationAdjustService manualAllocationAdjustService;

    @Resource
    StationAutoAllocationMapper stationAutoAllocationMapper;
    @Resource
    StationManualAllocationMapper stationManualAllocationMapper;

    @Resource
    NoStationAllocationMapper noStationAllocationMapper;

    @Resource
    SettlementRepoertMapper settlementRepoertMapper;

    @Resource
    SettlementDetailUsdMapper settlementDetailUsdMapper;

    @Resource
    NoAllocationAdjustMapper noAllocationAdjustMapper;

    @Resource
    ManualAllocationAdjustMapper manualAllocationAdjustMapper;

    @Resource
    SubsidySummaryMapper subsidySummaryMapper;

    @Resource
    OutStationAllocationMapper outStationAllocationMapper;

    @Resource
    SettlementDetailListingMapper settlementDetailListingMapper;

    @Autowired
    ISettlementReportService iSettlementReportService;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    /**
     * 批量确认结算单美金标识
     */
    @Value("${rediskey.confirmUsd}")
    public String confirmUsd;

    @DataSource(name = "finance")
    @Override
    public PageResult<SettlementDetailUsdResult> findPageBySpec(SettlementDetailUsdParam param) {
        Page pageContext = getPageContext();

        IPage<SettlementDetailUsdResult> page = this.baseMapper.findPageBySpec(pageContext, param);
        return new PageResult<>(page);
    }

    @DataSource(name = "finance")
    @Override
    public List<SettlementDetailUsdResult> export(SettlementDetailUsdParam param) {
        Page pageContext = PageFactory.defaultPage();
        pageContext.setSize(Integer.MAX_VALUE);
        IPage<SettlementDetailUsdResult> page = this.baseMapper.findPageBySpec(pageContext, param);
        return page.getRecords();
    }

    @DataSource(name = "finance")
    @Override
    public List<SettlementDetail> getSettlementMoney(SettlementDetail param) {
        return this.baseMapper.getSettlementMoney(param);
    }

    @DataSource(name = "finance")
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseData confirm(SettlementDetailUsdParam param) throws Exception {

        String name = "系统生成";
        if (ObjectUtil.isNotEmpty(LoginContext.me().getLoginUser())) {
            name = LoginContext.me().getLoginUser().getName();
        }

        SettlementDetailUsd usd = this.baseMapper.selectById(param.getId());
        usd.setProductType(param.getProductType());
        usd.setConfirmDate(new Date());
        usd.setConfirmBy(name);
        usd.setConfirmStatus(new BigDecimal(1));

        QueryWrapper<SettlementReport> queryReport=new QueryWrapper<>();
        queryReport
                .eq("SHOP_NAME", usd.getShopName())
                .eq("FISCAL_PERIOD", usd.getFiscalPeriod())
                .eq("SITE",usd.getSite());
        if (iSettlementReportService.count(queryReport) > 0) {
            return ResponseData.error("结算报告已存在，请先反审核!");
        }


        this.updateById(usd);

        param.setConfirmBy(name);
        param.setConfirmDate(new Date());
        if((usd.getIncomeType().equals("预估") || usd.getIncomeType().equals("确认")) && usd.getSku().equals("0") ){
            //站内手工分摊表
            if(!BigDecimal.ZERO.equals(usd.getStorageFee()) || !BigDecimal.ZERO.equals(usd.getAdvertising())){
                this.insertStationManualAllocation(param,usd);
            }

            //费用项汇总非空
            SettlementDetailUsdResult sumAmount=this.baseMapper.autoAmountSummary(usd);

            if(BigDecimal.ZERO.compareTo(sumAmount.getSumAll()) == -1)
            {
                //站内费用自动分摊
                this.insertStationAllocation(true, param, usd);
            }
        }
        if((usd.getIncomeType().equals("预估") || usd.getIncomeType().equals("确认")) && !usd.getSku().equals("0") ){
            //无需分摊站内费用
            this.insertNoStationAllocation(param,usd);
        }
        if(usd.getIncomeType().equals("调整") && !usd.getSku().equals("0") ){
            //无需分摊调整表
            this.insertNoAllocationAdjust(param,usd);
        }
       if(usd.getIncomeType().equals("调整") && usd.getSku().equals("0") ){
            //原手动分摊调整表
//            this.insertManualAllocationAdjust(param,usd);
           //站内手工分摊表
           if(!BigDecimal.ZERO.equals(usd.getStorageFee()) || !BigDecimal.ZERO.equals(usd.getAdvertising())){
               this.insertStationManualAllocation(param,usd);
           }

           //费用项汇总非空
           SettlementDetailUsdResult sumAmount=this.baseMapper.autoAmountSummary(usd);

           if(BigDecimal.ZERO.compareTo(sumAmount.getSumAll()) == -1)
           {
               //站内费用自动分摊
               this.insertStationAllocation(true, param, usd);
           }
        }

        if((usd.getIncomeType().equals("预估") || usd.getIncomeType().equals("确认"))
                && !usd.getSku().equals("0")) {
            insertStationAllocation(false, param, usd);
        }
        return ResponseData.success();
    }


    @DataSource(name = "finance")
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseData unconfirm(SettlementDetailUsdParam param) throws Exception {
        if (StrUtil.isEmpty(param.getShopName()) || StrUtil.isEmpty(param.getFiscalPeriod()) ){
            return ResponseData.error("反审核操作账号、会计期间必填!");
        }

        //补贴汇总表
        UpdateWrapper<SettlementDetailUsd> usdUpdateWrapper = new UpdateWrapper<>();
        usdUpdateWrapper
                .eq("SHOP_NAME", param.getShopName())
                .eq("FISCAL_PERIOD", param.getFiscalPeriod())
                .eq("CONFIRM_STATUS", 1)
                .in(CollectionUtils.isNotEmpty(param.getSites()),"SITE",param.getSites())
                .set("CONFIRM_STATUS", 0)
                .set("CONFIRM_BY", LoginContext.me().getLoginUser().getName())
                .set("CONFIRM_DATE", new Date());
        settlementDetailUsdMapper.update(null, usdUpdateWrapper);

        //站内自动分摊
        QueryWrapper<StationAutoAllocation> stationAutoAllocationUpdateWrapper = new QueryWrapper<>();
        stationAutoAllocationUpdateWrapper
            .eq("SHOP_NAME", param.getShopName())
            .eq("FISCAL_PERIOD", param.getFiscalPeriod())
            .in(CollectionUtils.isNotEmpty(param.getSites()),"SITE", param.getSites());
        stationAutoAllocationMapper.delete(stationAutoAllocationUpdateWrapper);

        //站内费用手动分摊
        QueryWrapper<StationManualAllocation> stationManualAllocationUpdateWrapper = new QueryWrapper<>();
        stationManualAllocationUpdateWrapper
            .eq("SHOP_NAME", param.getShopName())
            .eq("FISCAL_PERIOD", param.getFiscalPeriod())
            .in(CollectionUtils.isNotEmpty(param.getSites()),"SITE", param.getSites());
        stationManualAllocationMapper.delete(stationManualAllocationUpdateWrapper);

        //无需分摊站内费用
        QueryWrapper<NoStationAllocation> noStationAllocationUpdateWrapper = new QueryWrapper<>();
        noStationAllocationUpdateWrapper
            .eq("SHOP_NAME", param.getShopName())
            .eq("FISCAL_PERIOD", param.getFiscalPeriod())
            .in(CollectionUtils.isNotEmpty(param.getSites()),"SITE", param.getSites());
        noStationAllocationMapper.delete(noStationAllocationUpdateWrapper);

        //无需分摊调整表
        QueryWrapper<NoAllocationAdjust> noAllocationAdjustUpdateWrapper = new QueryWrapper<>();
        noAllocationAdjustUpdateWrapper
            .eq("SHOP_NAME", param.getShopName())
            .eq("FISCAL_PERIOD", param.getFiscalPeriod())
            .in(CollectionUtils.isNotEmpty(param.getSites()),"SITE", param.getSites());
        noAllocationAdjustMapper.delete(noAllocationAdjustUpdateWrapper);

        //手动分摊调整表
        QueryWrapper<ManualAllocationAdjust> manualAllocationAdjustUpdateWrapper = new QueryWrapper<>();
        manualAllocationAdjustUpdateWrapper
            .eq("SHOP_NAME", param.getShopName())
            .eq("FISCAL_PERIOD", param.getFiscalPeriod())
            .in(CollectionUtils.isNotEmpty(param.getSites()),"SITE", param.getSites());
        manualAllocationAdjustMapper.delete(manualAllocationAdjustUpdateWrapper);

        //补贴汇总表
        UpdateWrapper<SubsidySummary> subsidySummaryUpdateWrapper = new UpdateWrapper<>();
        subsidySummaryUpdateWrapper
            .eq("SHOP_NAME", param.getShopName())
            .eq("FISCAL_PERIOD", param.getFiscalPeriod())
            .eq("CONFIRM_STATUS", 1)
            .in(CollectionUtils.isNotEmpty(param.getSites()),"SITE", param.getSites())
            .set("CONFIRM_STATUS", 0)
            .set("CONFIRM_BY", LoginContext.me().getLoginUser().getName())
            .set("CONFIRM_DATE", new Date());
        subsidySummaryMapper.update(null, subsidySummaryUpdateWrapper);

        //站外费用分摊汇总表
        UpdateWrapper<OutStationAllocation> outStationAllocationUpdateWrapper = new UpdateWrapper<>();
        outStationAllocationUpdateWrapper
            .eq("SHOP_NAME", param.getShopName())
            .eq("FISCAL_PERIOD", param.getFiscalPeriod())
            .eq("CONFIRM_STATUS", 1)
            .in(CollectionUtils.isNotEmpty(param.getSites()),"SITE", param.getSites())
            .set("CONFIRM_STATUS", 0)
            .set("CONFIRM_BY", LoginContext.me().getLoginUser().getName())
            .set("CONFIRM_DATE", new Date());
        outStationAllocationMapper.update(null, outStationAllocationUpdateWrapper);

        //按账号站点会计期间删除结算报告
        QueryWrapper<SettlementReport> settlementReportWrapper = new QueryWrapper<>();
        settlementReportWrapper
            .eq("SHOP_NAME", param.getShopName())
            .eq("FISCAL_PERIOD", param.getFiscalPeriod())
            .in(CollectionUtils.isNotEmpty(param.getSites()),"SITE", param.getSites());
        settlementRepoertMapper.delete(settlementReportWrapper);
        return ResponseData.success();
    }


    @DataSource(name = "finance")
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseData deleteAlloc(SettlementDetailUsdParam param) throws Exception {

        //1.删除未确认结算报告
        QueryWrapper<SettlementReport> settlementReportWrapper = new QueryWrapper<>();
        settlementReportWrapper
                .eq("SHOP_NAME", param.getShopName())
                .eq("FISCAL_PERIOD", param.getFiscalPeriod())
                .eq("CONFIRM_STATUS", BigDecimal.ZERO)
                .in(CollectionUtils.isNotEmpty(param.getSites()),"SITE", param.getSites());
        settlementRepoertMapper.delete(settlementReportWrapper);



        //2.删除站内自动分摊
        QueryWrapper<StationAutoAllocation> stationAutoAllocationUpdateWrapper = new QueryWrapper<>();
        stationAutoAllocationUpdateWrapper
                .eq("SHOP_NAME", param.getShopName())
                .eq("FISCAL_PERIOD", param.getFiscalPeriod())
                .in(CollectionUtils.isNotEmpty(param.getSites()),"SITE", param.getSites());
        stationAutoAllocationMapper.delete(stationAutoAllocationUpdateWrapper);



        //3.站内费用手动分摊:删主保留明细
        QueryWrapper<StationManualAllocation> stationManualAllocationUpdateWrapper = new QueryWrapper<>();
        stationManualAllocationUpdateWrapper
                .eq("SHOP_NAME", param.getShopName())
                .eq("FISCAL_PERIOD", param.getFiscalPeriod())
                .in(CollectionUtils.isNotEmpty(param.getSites()),"SITE", param.getSites())
                .isNull("ALLOC_ID");
        stationManualAllocationMapper.delete(stationManualAllocationUpdateWrapper);


        QueryWrapper<StationManualAllocation> stationManualAllocationNonAdUpdateWrapper = new QueryWrapper<>();
        stationManualAllocationNonAdUpdateWrapper
                .eq("SHOP_NAME", param.getShopName())
                .eq("FISCAL_PERIOD", param.getFiscalPeriod())
                .in(CollectionUtils.isNotEmpty(param.getSites()),"SITE", param.getSites())
                .isNotNull("ALLOC_ID")
                .and(i->i.isNull("ADVERTISING").or().eq("ADVERTISING",0));
        stationManualAllocationMapper.delete(stationManualAllocationNonAdUpdateWrapper);


        //保留广告明细置空其他费用字段,只保留ADVERTISING费用
        UpdateWrapper<StationManualAllocation> stationManualUpdateWrapper = new UpdateWrapper<>();
        stationManualUpdateWrapper
                .eq("SHOP_NAME", param.getShopName())
                .eq("FISCAL_PERIOD", param.getFiscalPeriod())
                .isNotNull("ALLOC_ID")
                .in(CollectionUtils.isNotEmpty(param.getSites()),"SITE", param.getSites())
                .set("CONFIRM_STATUS", 0)
                .set("CONFIRM_BY", "保留广告数据")
                .set("CONFIRM_DATE", new Date())
                .set("STORAGE_FEE",0)
                .set("STORAGE_FEE_ORI",0)
                .set("STORAGE_FEE_ALLOC_RATE",0)
                .set("DISPOSE_FEE",0)
                .set("REMOVAL_DEAL",0);
        ;
        stationManualAllocationMapper.update(null, stationManualUpdateWrapper);


        //4.无需分摊站内::删除,美金表确认后会重新生成
        QueryWrapper<NoStationAllocation> noStationAllocationUpdateWrapper = new QueryWrapper<>();
        noStationAllocationUpdateWrapper
                .eq("SHOP_NAME", param.getShopName())
                .eq("FISCAL_PERIOD", param.getFiscalPeriod())
                .in(CollectionUtils.isNotEmpty(param.getSites()),"SITE", param.getSites());
        noStationAllocationMapper.delete(noStationAllocationUpdateWrapper);



        //5.无需分摊调整:删除,美金表确认后会重新生成
        QueryWrapper<NoAllocationAdjust> noAllocationAdjustUpdateWrapper = new QueryWrapper<>();
        noAllocationAdjustUpdateWrapper
                .eq("SHOP_NAME", param.getShopName())
                .eq("FISCAL_PERIOD", param.getFiscalPeriod())
                .in(CollectionUtils.isNotEmpty(param.getSites()),"SITE", param.getSites());
        noAllocationAdjustMapper.delete(noAllocationAdjustUpdateWrapper);
        return ResponseData.success();


    }


    @DataSource(name = "finance")
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseData confirmBatch(SettlementDetailUsdParam param) throws Exception {
        log.info("结算单明细（美金）批量确认开始，账号[{}]，站点[{}]，会计期间[{}]", param.getShopName(), Arrays.toString(param.getSites().toArray()), param.getFiscalPeriod());
        long start = System.currentTimeMillis();

        String name = ObjectUtil.isNotEmpty(LoginContext.me().getLoginUser())?LoginContext.me().getLoginUser().getName():param.getConfirmBy();
        String account = ObjectUtil.isNotEmpty(LoginContext.me().getLoginUser())?LoginContext.me().getLoginUser().getAccount():param.getConfirmBy();

        //redis操作需绑定key
        BoundValueOperations toList = redisTemplate.boundValueOps(this.confirmUsd);

        try{
            //从非空则为正在批量确认中
            if (toList.get() != null && StrUtil.isNotEmpty((String)toList.get())){
                return ResponseData.error("正在批量确认中!");
            }
            //设定正在批量确认
            toList.set("正在批量确认中!");

            QueryWrapper<SettlementReport> queryReport=new QueryWrapper<>();
            queryReport
                    .eq("SHOP_NAME", param.getShopName())
                    .eq("FISCAL_PERIOD", param.getFiscalPeriod())
                    .in(CollectionUtils.isNotEmpty(param.getSites()),"SITE",param.getSites());
            if (iSettlementReportService.count(queryReport) > 0) {
                return ResponseData.error("结算报告已存在，请先反审核!");
            }
            QueryWrapper<SettlementDetailUsd> queryWrapper=new QueryWrapper<>();
            queryWrapper
                    .eq("SHOP_NAME", param.getShopName())
                    .eq("FISCAL_PERIOD", param.getFiscalPeriod())
                    .eq("CONFIRM_STATUS",0)
                    .in(CollectionUtils.isNotEmpty(param.getSites()),"SITE",param.getSites());
            if (this.count(queryWrapper) == 0) {
                return ResponseData.success("无可确认的数据!");
            }

            /**
             * 1、根据条件将结算单明细（美金）批量拆分
             */
            param.setConfirmBy(name);

            param.setConfirmDate(DateUtil.date());
            //1-1、站内费用手工分摊表，规则：收入确认类型为预估或确认、SKU为0、费用为Storage fee或Advertising的数据确认之后将进入到【站内费用手工分摊表】
            this.baseMapper.insertOrUpdateManualAllocation(param);

            //1-2、站内费用自动分摊表汇总处理，规则：收入确认类型为预估或确认、SKU为0、费用非Storage fee或非Advertising的数据将确认之后将进入到【站内费用自动分摊表】
            this.baseMapper.insertOrUpdateAutoAllocationTotal(param);

            //1-3、站内费用自动分摊表明细处理
            //获取站内费用自动分摊汇总
            //新增或者更新站内费用自动分摊明细集合
            List<StationAutoAllocation> updateAutoAllocationDetailList = new ArrayList();
            //更新站内费用自动分摊汇总集合
            List<StationAutoAllocation> updateAutoAllocationTotalList = new ArrayList();

            //更新站内费用自动分摊汇总集合
            List<BigDecimal> allocIds = new ArrayList();

            QueryWrapper<StationAutoAllocation> queryAutoWrapper = new QueryWrapper<>();
            queryAutoWrapper.eq("FISCAL_PERIOD",param.getFiscalPeriod())
                    .eq("SHOP_NAME",param.getShopName())
                    .eq("CONFIRM_STATUS", 0)
                    .in(CollectionUtils.isNotEmpty(param.getSites()),"SITE",param.getSites())
                    .isNull("ALLOC_ID");
            List<StationAutoAllocation> stationAutoAllocationList =  stationAutoAllocationMapper.selectList(queryAutoWrapper);
            if(CollectionUtils.isNotEmpty(stationAutoAllocationList)){
                for (StationAutoAllocation stationAutoAllocation : stationAutoAllocationList) {
                    //根据账号分站点单个拆分明细，统计维度条件统计美金计算单明细收入
                    param.setSite(stationAutoAllocation.getSite());
                    param.setConfirmStatus(null);//批量确认的操作此时还没有更新确认状态，故确认状态不做查询条件
                    List<SettlementDetailUsdResult> detailUsdIncomeList = autoAllocationService.getExitSkuMoney(param);
                    if(CollectionUtils.isNotEmpty(detailUsdIncomeList)){
                        //汇总明细SALES_EXCLUDING_TAX为income（总收入）
                        BigDecimal incomeSum = detailUsdIncomeList.stream().map(SettlementDetailUsdResult::getSalesExcludingTax).reduce(BigDecimal.ZERO, BigDecimal::add);

                        //累加
                        BigDecimal incomeProportionSum = BigDecimal.ZERO;
                        BigDecimal salesExcludingTaxSum = BigDecimal.ZERO;
                        BigDecimal taxSum = BigDecimal.ZERO;
                        BigDecimal salesPromotionSum = BigDecimal.ZERO;
                        BigDecimal refundSum = BigDecimal.ZERO;
                        BigDecimal refundPromotionSum = BigDecimal.ZERO;
                        BigDecimal giftwarpShippingSum = BigDecimal.ZERO;
                        BigDecimal commissionSum = BigDecimal.ZERO;
                        BigDecimal refundCommissionSum = BigDecimal.ZERO;
                        BigDecimal goodwillSum = BigDecimal.ZERO;
                        BigDecimal amazonFeesSum = BigDecimal.ZERO;
                        BigDecimal reimbursementSum = BigDecimal.ZERO;
                        BigDecimal otherSum = BigDecimal.ZERO;
                        BigDecimal withheldTaxSum = BigDecimal.ZERO;
                        BigDecimal disposeFeeSum = BigDecimal.ZERO;
                        BigDecimal removalDealSum = BigDecimal.ZERO;

                        for (int i = 0; i < detailUsdIncomeList.size(); i++) {
                            StationAutoAllocation autoAllocationDetailEntity = new StationAutoAllocation();
                            autoAllocationDetailEntity.setIncome(detailUsdIncomeList.get(i).getSalesExcludingTax());
                            //明细的父类ID
                            autoAllocationDetailEntity.setAllocId(stationAutoAllocation.getId());
                            autoAllocationDetailEntity.setReportType(stationAutoAllocation.getReportType());
                            autoAllocationDetailEntity.setFiscalPeriod(detailUsdIncomeList.get(i).getFiscalPeriod());
                            if (StrUtil.isEmpty(detailUsdIncomeList.get(i).getReportType())){
                                autoAllocationDetailEntity.setReportType("0");
                            }else{
                                autoAllocationDetailEntity.setReportType(detailUsdIncomeList.get(i).getReportType());
                            };
                            autoAllocationDetailEntity.setIncomeType(stationAutoAllocation.getIncomeType());
                            autoAllocationDetailEntity.setShopName(detailUsdIncomeList.get(i).getShopName());
                            autoAllocationDetailEntity.setSite(detailUsdIncomeList.get(i).getSite());
                            autoAllocationDetailEntity.setProductType(detailUsdIncomeList.get(i).getProductType());
                            autoAllocationDetailEntity.setAccountingCurrency(detailUsdIncomeList.get(i).getAccountingCurrency());
                            autoAllocationDetailEntity.setCreateBy(account);
                            autoAllocationDetailEntity.setCreateAt(new Date());
                            autoAllocationDetailEntity.setDepartment(detailUsdIncomeList.get(i).getDepartment());
                            autoAllocationDetailEntity.setTeam(detailUsdIncomeList.get(i).getTeam());
                            autoAllocationDetailEntity.setVolumeNormal(detailUsdIncomeList.get(i).getVolumeNormal());

                            if(i == detailUsdIncomeList.size()-1){
                                //最后一条采取总数减去其他数据汇总
                                autoAllocationDetailEntity.setIncomeProportion(stationAutoAllocation.getIncomeProportion().subtract(incomeProportionSum));
                                autoAllocationDetailEntity.setSalesExcludingTax(stationAutoAllocation.getSalesExcludingTax().subtract(salesExcludingTaxSum));
                                autoAllocationDetailEntity.setTax(stationAutoAllocation.getTax().subtract(taxSum));
                                autoAllocationDetailEntity.setSalesPromotion(stationAutoAllocation.getSalesPromotion().subtract(salesPromotionSum));
                                autoAllocationDetailEntity.setRefund(stationAutoAllocation.getRefund().subtract(refundSum));
                                autoAllocationDetailEntity.setRefundPromotion(stationAutoAllocation.getRefundPromotion().subtract(refundPromotionSum));
                                autoAllocationDetailEntity.setGiftwarpShipping(stationAutoAllocation.getGiftwarpShipping().subtract(giftwarpShippingSum));
                                autoAllocationDetailEntity.setCommission(stationAutoAllocation.getCommission().subtract(commissionSum));
                                autoAllocationDetailEntity.setRefundCommission(stationAutoAllocation.getRefundCommission().subtract(refundCommissionSum));
                                autoAllocationDetailEntity.setGoodwill(stationAutoAllocation.getGoodwill().subtract(goodwillSum));
                                autoAllocationDetailEntity.setAmazonFees(stationAutoAllocation.getAmazonFees().subtract(amazonFeesSum));
                                autoAllocationDetailEntity.setReimbursement(stationAutoAllocation.getReimbursement().subtract(reimbursementSum));
                                autoAllocationDetailEntity.setOther(stationAutoAllocation.getOther().subtract(otherSum));
                                autoAllocationDetailEntity.setWithheldTax(stationAutoAllocation.getWithheldTax().subtract(withheldTaxSum));
                                autoAllocationDetailEntity.setDisposeFee(stationAutoAllocation.getDisposeFee().subtract(disposeFeeSum));
                                autoAllocationDetailEntity.setRemovalDeal(stationAutoAllocation.getRemovalDeal().subtract(removalDealSum));
                            } else {
                                //明细收入占比（四舍五入保留2位小数）
                                BigDecimal incomeProportion = (stationAutoAllocation.getIncome().equals(BigDecimal.ZERO) ? BigDecimal.ZERO : detailUsdIncomeList.get(i).getSalesExcludingTax().divide(incomeSum,4, BigDecimal.ROUND_HALF_UP));
                                autoAllocationDetailEntity.setIncomeProportion(incomeProportion);
                                BigDecimal salesExcludingTax = stationAutoAllocation.getSalesExcludingTax().multiply(incomeProportion);
                                autoAllocationDetailEntity.setSalesExcludingTax(salesExcludingTax);
                                BigDecimal tax = stationAutoAllocation.getTax().multiply(incomeProportion);
                                autoAllocationDetailEntity.setTax(tax);
                                BigDecimal salesPromotion = stationAutoAllocation.getSalesPromotion().multiply(incomeProportion);
                                autoAllocationDetailEntity.setSalesPromotion(salesPromotion);
                                BigDecimal refund = stationAutoAllocation.getRefund().multiply(incomeProportion);
                                autoAllocationDetailEntity.setRefund(refund);
                                BigDecimal refundPromotion = stationAutoAllocation.getRefundPromotion().multiply(incomeProportion);
                                autoAllocationDetailEntity.setRefundPromotion(refundPromotion);
                                BigDecimal giftwarpShipping = stationAutoAllocation.getGiftwarpShipping().multiply(incomeProportion);
                                autoAllocationDetailEntity.setGiftwarpShipping(giftwarpShipping);
                                BigDecimal commission = stationAutoAllocation.getCommission().multiply(incomeProportion);
                                autoAllocationDetailEntity.setCommission(commission);
                                BigDecimal refundCommission = stationAutoAllocation.getRefundCommission().multiply(incomeProportion);
                                autoAllocationDetailEntity.setRefundCommission(refundCommission);
                                BigDecimal goodwill = stationAutoAllocation.getGoodwill().multiply(incomeProportion);
                                autoAllocationDetailEntity.setGoodwill(goodwill);
                                BigDecimal amazonFees = stationAutoAllocation.getAmazonFees().multiply(incomeProportion);
                                autoAllocationDetailEntity.setAmazonFees(amazonFees);
                                BigDecimal reimbursement = stationAutoAllocation.getReimbursement().multiply(incomeProportion);
                                autoAllocationDetailEntity.setReimbursement(reimbursement);
                                BigDecimal other = stationAutoAllocation.getOther().multiply(incomeProportion);
                                autoAllocationDetailEntity.setOther(other);
                                BigDecimal withheldTax = stationAutoAllocation.getWithheldTax().multiply(incomeProportion);
                                autoAllocationDetailEntity.setWithheldTax(withheldTax);
                                BigDecimal disposeFee = stationAutoAllocation.getDisposeFee().multiply(incomeProportion);
                                autoAllocationDetailEntity.setDisposeFee(disposeFee);
                                BigDecimal removalDeal = stationAutoAllocation.getRemovalDeal().multiply(incomeProportion);
                                autoAllocationDetailEntity.setRemovalDeal(removalDeal);

                                //累加
                                incomeProportionSum = incomeProportionSum.add(incomeProportion.setScale(4,BigDecimal.ROUND_HALF_UP));
                                salesExcludingTaxSum = salesExcludingTaxSum.add(salesExcludingTax.setScale(2,BigDecimal.ROUND_HALF_UP));
                                taxSum = taxSum.add(tax.setScale(2,BigDecimal.ROUND_HALF_UP));
                                salesPromotionSum = salesPromotionSum.add(salesPromotion.setScale(2,BigDecimal.ROUND_HALF_UP));
                                refundSum = refundSum.add(refund.setScale(2,BigDecimal.ROUND_HALF_UP));
                                refundPromotionSum = refundPromotionSum.add(refundPromotion.setScale(2,BigDecimal.ROUND_HALF_UP));
                                giftwarpShippingSum = giftwarpShippingSum.add(giftwarpShipping.setScale(2,BigDecimal.ROUND_HALF_UP));
                                commissionSum = commissionSum.add(commission.setScale(2,BigDecimal.ROUND_HALF_UP));
                                refundCommissionSum = refundCommissionSum.add(refundCommission.setScale(2,BigDecimal.ROUND_HALF_UP));
                                goodwillSum = goodwillSum.add(goodwill.setScale(2,BigDecimal.ROUND_HALF_UP));
                                amazonFeesSum = amazonFeesSum.add(amazonFees.setScale(2,BigDecimal.ROUND_HALF_UP));
                                reimbursementSum = reimbursementSum.add(reimbursement.setScale(2,BigDecimal.ROUND_HALF_UP));
                                otherSum = otherSum.add(other.setScale(2,BigDecimal.ROUND_HALF_UP));
                                withheldTaxSum = withheldTaxSum.add(withheldTax.setScale(2,BigDecimal.ROUND_HALF_UP));
                                disposeFeeSum = disposeFeeSum.add(disposeFee.setScale(2,BigDecimal.ROUND_HALF_UP));
                                removalDealSum = removalDealSum.add(removalDeal.setScale(2,BigDecimal.ROUND_HALF_UP));
                            }
                            updateAutoAllocationDetailList.add(autoAllocationDetailEntity);
                            allocIds.add(autoAllocationDetailEntity.getAllocId());
                        }
                        stationAutoAllocation.setIncome(incomeSum);
                        updateAutoAllocationTotalList.add(stationAutoAllocation);
                    } else {
                        //没有明细时，设置IS_MANUAL=1，需要手动分摊数据
                        stationAutoAllocation.setISManual(BigDecimal.ONE);
                        updateAutoAllocationTotalList.add(stationAutoAllocation);
                    }
                }
            }

            //批量更新站内费用自动分摊汇总设置是否需要手动分摊数据集合
            if(CollectionUtils.isNotEmpty(updateAutoAllocationTotalList)){
                autoAllocationService.saveOrUpdateBatch(updateAutoAllocationTotalList, 10000);
            }

            //批量更新站内费用自动分摊明细集合
            if(CollectionUtils.isNotEmpty(updateAutoAllocationDetailList)){
                //删除已有自动分摊明细
                QueryWrapper<StationAutoAllocation> deleteWrapper = new QueryWrapper();
                deleteWrapper.in("ALLOC_ID",allocIds).eq("CONFIRM_STATUS",0);
                stationAutoAllocationMapper.delete(deleteWrapper);
                autoAllocationService.saveOrUpdateBatch(updateAutoAllocationDetailList, 200000);
            }

            //1-4、无需分摊站内费用表，规则：收入确认类型为预估或确认、SKU不为0的数据将进入到【无需分摊站内费用表】
            this.baseMapper.insertOrUpdateNoStationAllocation(param);

            //1-5、无需分摊调整表，规则：收入确认类型为调整,SKU不为0的数据将进入到【无需分摊调整表】
            this.baseMapper.insertOrUpdateNoAllocationAdjust(param);

            //1-6、手动分摊调整表，规则：收入确认类型为调整,SKU为0的数据将进入到【手动分摊调整表】
//            this.baseMapper.insertOrUpdateManualAllocationAdjust(param);

            this.baseMapper.updateConfirmDetailUsd(param);
            log.info("结算单明细（美金）批量确认结束，耗时---------->" +(System.currentTimeMillis() - start) + "ms");
            return ResponseData.success();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseData.error("生成失败!:"+e);
        } finally{
            toList.set("");
        }
    }

    @DataSource(name = "finance")
    @Override
    public SettlementDetailUsdResult getQuantity(SettlementDetailUsdParam param) {
        return this.baseMapper.getQuantity(param);
    }

    @DataSource(name = "finance")
    @Transactional(rollbackFor = Exception.class)
    @Override
    public ResponseData afreshExchangeRate(SettlementDetailUsdParam params) throws Exception {

        //写入结算单明细(美金)记录
        QueryWrapper<SettlementDetailUsd> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("ID",params.getId())
                    .eq("CONFIRM_STATUS",0);

        //插入确认头部信息
        SettlementDetailUsd detailUsd = this.baseMapper.selectOne(queryWrapper);

        //查询原币
        QueryWrapper<SettlementDetailListing> listingQueryWrapper=new QueryWrapper<>();
        listingQueryWrapper.eq("ID",detailUsd.getListingId());
        SettlementDetailListing detailListing = settlementDetailListingMapper.selectOne(listingQueryWrapper);

        //取汇率日期
        SpotExchangeRateParam rateParam=new SpotExchangeRateParam();
        rateParam.setOriginalCurrency(detailListing.getOriginalCurrency());
        rateParam.setEffectDate(DateUtil.parse(detailUsd.getFiscalPeriod()+"-01"));

        SpotExchangeRate spotrate= rateService.getRateByDateCurrency(rateParam);

        if(spotrate==null){
            return ResponseData.error("刷新失败,未设置当前会计期间即期汇率！");
        }
        //BigDecimal exchangeRate = rateService.getRateByDateCurrency(rateParam).getDirectRate();// 查询汇率

        this.baseMapper.afreshExchangeRate(params);
        return ResponseData.success();
    }

    @DataSource(name = "finance")
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void refreshExchangeRate() throws Exception {
        this.baseMapper.refreshExchangeRate();
    }

    /**
     * 写入自动分摊列表汇总记录
     * @param isSkuNull
     * @param param
     * @param detailUsd
     * @throws Exception
     */
    public void insertStationAllocation(boolean isSkuNull, SettlementDetailUsdParam param,SettlementDetailUsd detailUsd) throws Exception {
        /**
         * 判断结算单明细美元表店铺会计期是否全部审核通过，
         * 全部审核通过，则可以拆分明细；
         * 否则，新增或者汇总更新未分摊的数据到站内自动分摊表
         */
        //1、查询结算单明细美元表店铺会计期是否全部审核通过
        QueryWrapper<SettlementDetailUsd> queryDetailWrapper = new QueryWrapper<>();
        queryDetailWrapper.eq("FISCAL_PERIOD",detailUsd.getFiscalPeriod())
                .eq(StrUtil.isNotEmpty(detailUsd.getReportType()),"REPORT_TYPE",detailUsd.getReportType())
                .eq("INCOME_TYPE",detailUsd.getIncomeType())
                .eq("SHOP_NAME",detailUsd.getShopName())
                .eq("SITE",detailUsd.getSite())
                .eq("CONFIRM_STATUS",0);
        int detailUsdCount = this.count(queryDetailWrapper);

        if(detailUsdCount == 0){
            //根据维度条件统计美金计算单明细收入
            param.setFiscalPeriod(detailUsd.getFiscalPeriod());
            param.setReportType(detailUsd.getReportType());
            param.setIncomeType(detailUsd.getIncomeType());
            param.setShopName(detailUsd.getShopName());
            param.setSite(detailUsd.getSite());
            List<SettlementDetailUsdResult> detailUsdIncomeList = autoAllocationService.getExitSkuMoney(param);
            //汇总SALES_EXCLUDING_TAX为income（总收入）
            BigDecimal incomeSum = detailUsdIncomeList.stream().map(SettlementDetailUsdResult::getSalesExcludingTax).reduce(BigDecimal.ZERO, BigDecimal::add);
            if(isSkuNull){
                //2、处理站内费用自动分摊汇总
                dealAutoAllocation(detailUsd, true, incomeSum);
            }

            //更新完站内费用自动分摊汇总，获取汇总最终结果
            QueryWrapper<StationAutoAllocation> queryAutoWrapper = new QueryWrapper<>();
            queryAutoWrapper.eq("FISCAL_PERIOD",detailUsd.getFiscalPeriod())
                    .eq(StrUtil.isNotEmpty(detailUsd.getReportType()),"REPORT_TYPE",detailUsd.getReportType())
                    .eq("INCOME_TYPE",detailUsd.getIncomeType())
                    .eq("SHOP_NAME",detailUsd.getShopName())
                    .eq("SITE",detailUsd.getSite())
                    .isNull("ALLOC_ID");
            StationAutoAllocation stationAutoAllocation =  stationAutoAllocationMapper.selectOne(queryAutoWrapper);

            //3、处理明细
            if(stationAutoAllocation != null){
                //累加
                BigDecimal incomeProportionSum = BigDecimal.ZERO;
                BigDecimal salesExcludingTaxSum = BigDecimal.ZERO;
                BigDecimal taxSum = BigDecimal.ZERO;
                BigDecimal salesPromotionSum = BigDecimal.ZERO;
                BigDecimal refundSum = BigDecimal.ZERO;
                BigDecimal refundPromotionSum = BigDecimal.ZERO;
                BigDecimal giftwarpShippingSum = BigDecimal.ZERO;
                BigDecimal commissionSum = BigDecimal.ZERO;
                BigDecimal refundCommissionSum = BigDecimal.ZERO;
                BigDecimal goodwillSum = BigDecimal.ZERO;
                BigDecimal amazonFeesSum = BigDecimal.ZERO;
                BigDecimal reimbursementSum = BigDecimal.ZERO;
                BigDecimal otherSum = BigDecimal.ZERO;
                BigDecimal withheldTaxSum = BigDecimal.ZERO;
                BigDecimal disposeFeeSum = BigDecimal.ZERO;
                BigDecimal removalDealSum = BigDecimal.ZERO;

                if(CollectionUtil.isNotEmpty(detailUsdIncomeList)){
                    for (int i = 0; i < detailUsdIncomeList.size(); i++) {
                        StationAutoAllocation autoAllocationDetailEntity = new StationAutoAllocation();
                        autoAllocationDetailEntity.setIncome(detailUsdIncomeList.get(i).getSalesExcludingTax());
                        //明细的父类ID
                        autoAllocationDetailEntity.setAllocId(stationAutoAllocation.getId());
                        autoAllocationDetailEntity.setFiscalPeriod(detailUsdIncomeList.get(i).getFiscalPeriod());
                        autoAllocationDetailEntity.setReportType(detailUsdIncomeList.get(i).getReportType());
                        autoAllocationDetailEntity.setIncomeType(detailUsdIncomeList.get(i).getIncomeType());
                        autoAllocationDetailEntity.setShopName(detailUsdIncomeList.get(i).getShopName());
                        autoAllocationDetailEntity.setSite(detailUsdIncomeList.get(i).getSite());
                        autoAllocationDetailEntity.setProductType(detailUsdIncomeList.get(i).getProductType());
                        autoAllocationDetailEntity.setAccountingCurrency("USD");
                        autoAllocationDetailEntity.setCreateBy(param.getConfirmBy());
                        autoAllocationDetailEntity.setCreateAt(param.getConfirmDate());
                        autoAllocationDetailEntity.setDepartment(detailUsdIncomeList.get(i).getDepartment());
                        autoAllocationDetailEntity.setTeam(detailUsdIncomeList.get(i).getTeam());
                        autoAllocationDetailEntity.setVolumeNormal(detailUsdIncomeList.get(i).getVolumeNormal());

                        if(i == detailUsdIncomeList.size()-1){
                            //最后一条采取总数减去其他数据汇总
                            autoAllocationDetailEntity.setIncomeProportion(stationAutoAllocation.getIncomeProportion().subtract(incomeProportionSum));
                            autoAllocationDetailEntity.setSalesExcludingTax(stationAutoAllocation.getSalesExcludingTax().subtract(salesExcludingTaxSum));
                            autoAllocationDetailEntity.setTax(stationAutoAllocation.getTax().subtract(taxSum));
                            autoAllocationDetailEntity.setSalesPromotion(stationAutoAllocation.getSalesPromotion().subtract(salesPromotionSum));
                            autoAllocationDetailEntity.setRefund(stationAutoAllocation.getRefund().subtract(refundSum));
                            autoAllocationDetailEntity.setRefundPromotion(stationAutoAllocation.getRefundPromotion().subtract(refundPromotionSum));
                            autoAllocationDetailEntity.setGiftwarpShipping(stationAutoAllocation.getGiftwarpShipping().subtract(giftwarpShippingSum));
                            autoAllocationDetailEntity.setCommission(stationAutoAllocation.getCommission().subtract(commissionSum));
                            autoAllocationDetailEntity.setRefundCommission(stationAutoAllocation.getRefundCommission().subtract(refundCommissionSum));
                            autoAllocationDetailEntity.setGoodwill(stationAutoAllocation.getGoodwill().subtract(goodwillSum));
                            autoAllocationDetailEntity.setAmazonFees(stationAutoAllocation.getAmazonFees().subtract(amazonFeesSum));
                            autoAllocationDetailEntity.setReimbursement(stationAutoAllocation.getReimbursement().subtract(reimbursementSum));
                            autoAllocationDetailEntity.setOther(stationAutoAllocation.getOther().subtract(otherSum));
                            autoAllocationDetailEntity.setWithheldTax(stationAutoAllocation.getWithheldTax().subtract(withheldTaxSum));
                            autoAllocationDetailEntity.setDisposeFee(stationAutoAllocation.getDisposeFee().subtract(disposeFeeSum));
                            autoAllocationDetailEntity.setRemovalDeal(stationAutoAllocation.getRemovalDeal().subtract(removalDealSum));
                        } else {
                            //明细收入占比（四舍五入保留2位小数）
                            BigDecimal incomeProportion = (incomeSum.equals(BigDecimal.ZERO) ? BigDecimal.ZERO : detailUsdIncomeList.get(i).getSalesExcludingTax().divide(incomeSum,4, BigDecimal.ROUND_HALF_UP));
                            autoAllocationDetailEntity.setIncomeProportion(incomeProportion);
                            BigDecimal salesExcludingTax = stationAutoAllocation.getSalesExcludingTax().multiply(incomeProportion);
                            autoAllocationDetailEntity.setSalesExcludingTax(salesExcludingTax);
                            BigDecimal tax = stationAutoAllocation.getTax().multiply(incomeProportion);
                            autoAllocationDetailEntity.setTax(tax);
                            BigDecimal salesPromotion = stationAutoAllocation.getSalesPromotion().multiply(incomeProportion);
                            autoAllocationDetailEntity.setSalesPromotion(salesPromotion);
                            BigDecimal refund = stationAutoAllocation.getRefund().multiply(incomeProportion);
                            autoAllocationDetailEntity.setRefund(refund);
                            BigDecimal refundPromotion = stationAutoAllocation.getRefundPromotion().multiply(incomeProportion);
                            autoAllocationDetailEntity.setRefundPromotion(refundPromotion);
                            BigDecimal giftwarpShipping = stationAutoAllocation.getGiftwarpShipping().multiply(incomeProportion);
                            autoAllocationDetailEntity.setGiftwarpShipping(giftwarpShipping);
                            BigDecimal commission = stationAutoAllocation.getCommission().multiply(incomeProportion);
                            autoAllocationDetailEntity.setCommission(commission);
                            BigDecimal refundCommission = stationAutoAllocation.getRefundCommission().multiply(incomeProportion);
                            autoAllocationDetailEntity.setRefundCommission(refundCommission);
                            BigDecimal goodwill = stationAutoAllocation.getGoodwill().multiply(incomeProportion);
                            autoAllocationDetailEntity.setGoodwill(goodwill);
                            BigDecimal amazonFees = stationAutoAllocation.getAmazonFees().multiply(incomeProportion);
                            autoAllocationDetailEntity.setAmazonFees(amazonFees);
                            BigDecimal reimbursement = stationAutoAllocation.getReimbursement().multiply(incomeProportion);
                            autoAllocationDetailEntity.setReimbursement(reimbursement);
                            BigDecimal other = stationAutoAllocation.getOther().multiply(incomeProportion);
                            autoAllocationDetailEntity.setOther(other);
                            BigDecimal withheldTax = stationAutoAllocation.getWithheldTax().multiply(incomeProportion);
                            autoAllocationDetailEntity.setWithheldTax(withheldTax);
                            BigDecimal disposeFee = stationAutoAllocation.getDisposeFee().multiply(incomeProportion);
                            autoAllocationDetailEntity.setDisposeFee(disposeFee);
                            BigDecimal removalDeal = stationAutoAllocation.getRemovalDeal().multiply(incomeProportion);
                            autoAllocationDetailEntity.setRemovalDeal(removalDeal);

                            //累加
                            incomeProportionSum = incomeProportionSum.add(incomeProportion.setScale(4,BigDecimal.ROUND_HALF_UP));
                            salesExcludingTaxSum = salesExcludingTaxSum.add(salesExcludingTax.setScale(2,BigDecimal.ROUND_HALF_UP));
                            taxSum = taxSum.add(tax.setScale(2,BigDecimal.ROUND_HALF_UP));
                            salesPromotionSum = salesPromotionSum.add(salesPromotion.setScale(2,BigDecimal.ROUND_HALF_UP));
                            refundSum = refundSum.add(refund.setScale(2,BigDecimal.ROUND_HALF_UP));
                            refundPromotionSum = refundPromotionSum.add(refundPromotion.setScale(2,BigDecimal.ROUND_HALF_UP));
                            giftwarpShippingSum = giftwarpShippingSum.add(giftwarpShipping.setScale(2,BigDecimal.ROUND_HALF_UP));
                            commissionSum = commissionSum.add(commission.setScale(2,BigDecimal.ROUND_HALF_UP));
                            refundCommissionSum = refundCommissionSum.add(refundCommission.setScale(2,BigDecimal.ROUND_HALF_UP));
                            goodwillSum = goodwillSum.add(goodwill.setScale(2,BigDecimal.ROUND_HALF_UP));
                            amazonFeesSum = amazonFeesSum.add(amazonFees.setScale(2,BigDecimal.ROUND_HALF_UP));
                            reimbursementSum = reimbursementSum.add(reimbursement.setScale(2,BigDecimal.ROUND_HALF_UP));
                            otherSum = otherSum.add(other.setScale(2,BigDecimal.ROUND_HALF_UP));
                            withheldTaxSum = withheldTaxSum.add(withheldTax.setScale(2,BigDecimal.ROUND_HALF_UP));
                            disposeFeeSum = disposeFeeSum.add(disposeFee.setScale(2,BigDecimal.ROUND_HALF_UP));
                            removalDealSum = removalDealSum.add(removalDeal.setScale(2,BigDecimal.ROUND_HALF_UP));
                        }
                        autoAllocationService.save(autoAllocationDetailEntity);
//                        stationAutoAllocation.setIncome(incomeSum);
//                        autoAllocationService.updateById(stationAutoAllocation);
                    }
                }else{
                    //没有明细的时，需要手动分摊数据
                    UpdateWrapper<StationAutoAllocation> updateWrapper = new UpdateWrapper<>();
                    updateWrapper.eq("id", stationAutoAllocation.getId()).set("IS_MANUAL", 1);
                    stationAutoAllocationMapper.update(null, updateWrapper);
                }
            }
        } else {
            //2、处理站内费用自动分摊汇总
            if(isSkuNull) {
                dealAutoAllocation(detailUsd, false, null);
            }
        }
    }


    /**
     * 处理站内费用自动分摊汇总
     * @param detailUsd
     * @return
     */
    protected boolean dealAutoAllocation(SettlementDetailUsd detailUsd, Boolean isAllPass, BigDecimal incomeSum){
        String name = "系统生成";
        if (ObjectUtil.isNotEmpty(LoginContext.me().getLoginUser())) {
            name = LoginContext.me().getLoginUser().getName();
        }
        QueryWrapper<StationAutoAllocation> queryAutoWrapper = new QueryWrapper<>();
        queryAutoWrapper.eq("FISCAL_PERIOD",detailUsd.getFiscalPeriod())
                .eq(StrUtil.isNotEmpty(detailUsd.getReportType()),"REPORT_TYPE",detailUsd.getReportType())
                .eq("INCOME_TYPE",detailUsd.getIncomeType())
                .eq("SHOP_NAME",detailUsd.getShopName())
                .eq("SITE",detailUsd.getSite())
                .eq("CONFIRM_STATUS",0)
                .isNull("ALLOC_ID");
        StationAutoAllocation autoAllocation =  stationAutoAllocationMapper.selectOne(queryAutoWrapper);

        //费用项汇总非空
        SettlementDetailUsdResult sumAmount=this.baseMapper.autoAmountSummary(detailUsd);

        //3-2、站内费用自动分摊
        if(autoAllocation == null && !BigDecimal.ZERO.equals(sumAmount.getSumAll())){
            //新增
            StationAutoAllocation autoAllocationEntity = new StationAutoAllocation();
            BeanUtils.copyProperties(detailUsd, autoAllocationEntity);
            autoAllocationEntity.setDepartment("0");
            autoAllocationEntity.setTeam("0");
            autoAllocationEntity.setVolumeNormal(BigDecimal.ZERO);
            autoAllocationEntity.setSalesBrand("0");
            autoAllocationEntity.setId(null);
            autoAllocationEntity.setConfirmBy("");
            autoAllocationEntity.setConfirmDate(null);
            autoAllocationEntity.setConfirmStatus(BigDecimal.ZERO);
            autoAllocationEntity.setCreateAt(new Date());
            autoAllocationEntity.setCreateBy(name);
            autoAllocationEntity.setIncomeProportion(new BigDecimal(1));
            if(isAllPass){
                autoAllocationEntity.setIncome(incomeSum);
            }
            return autoAllocationService.save(autoAllocationEntity);
        }else {
            //汇总更新
            autoAllocation.setCreateAt(new Date());
            autoAllocation.setCreateBy(name);
            autoAllocation.setSalesExcludingTax(autoAllocation.getSalesExcludingTax().add(detailUsd.getSalesExcludingTax()));
            autoAllocation.setTax(autoAllocation.getTax().add(detailUsd.getTax()));
            autoAllocation.setSalesPromotion(autoAllocation.getSalesPromotion().add(detailUsd.getSalesPromotion()));
            autoAllocation.setRefund(autoAllocation.getRefund().add(detailUsd.getRefund()));
            autoAllocation.setRefundPromotion(autoAllocation.getRefundPromotion().add(detailUsd.getRefundPromotion()));
            autoAllocation.setGiftwarpShipping(autoAllocation.getGiftwarpShipping().add(detailUsd.getGiftwarpShipping()));
            autoAllocation.setCommission(autoAllocation.getCommission().add(detailUsd.getCommission()));
            autoAllocation.setRefundCommission(autoAllocation.getRefundCommission().add(detailUsd.getRefundCommission()));
            autoAllocation.setGoodwill(autoAllocation.getGoodwill().add(detailUsd.getGoodwill()));
            autoAllocation.setAmazonFees(autoAllocation.getAmazonFees().add(detailUsd.getAmazonFees()));
            autoAllocation.setReimbursement(autoAllocation.getReimbursement().add(detailUsd.getReimbursement()));
            autoAllocation.setOther(autoAllocation.getOther().add(detailUsd.getOther()));
            autoAllocation.setWithheldTax(autoAllocation.getWithheldTax().add(detailUsd.getWithheldTax()));
            autoAllocation.setDisposeFee(autoAllocation.getDisposeFee().add(detailUsd.getDisposeFee()));
            autoAllocation.setRemovalDeal(autoAllocation.getRemovalDeal().add(detailUsd.getRemovalDeal()));
            if(isAllPass){
                autoAllocation.setIncome(incomeSum);
            }
            return autoAllocationService.updateById(autoAllocation);
        }
    }

    private Page getPageContext() {
        return PageFactory.defaultPage();
    }

    //写入无需分摊调整列表
    public void insertNoAllocationAdjust(SettlementDetailUsdParam param,SettlementDetailUsd usd) throws Exception{
        noAllocationAdjustService.updateAmountOrInsert(usd);
    }

    //写入无需分摊站内费用
    public void insertNoStationAllocation(SettlementDetailUsdParam param,SettlementDetailUsd usd) throws Exception{
        noStationAllocationService.updateAmountOrInsert(usd);
    }

    /**
     * 将结算单明细（美元）数据拆分到站内手工分摊表
     */
    public void insertStationManualAllocation(SettlementDetailUsdParam param,SettlementDetailUsd usd) throws Exception{
        //1、根据维度查询手工费用分摊表
        QueryWrapper<StationManualAllocation> manualQuery = new QueryWrapper();
        manualQuery.eq("FISCAL_PERIOD", usd.getFiscalPeriod())
                .and(wq->wq
                    .eq("REPORT_TYPE",0)
                    .or()
                    .eq(StrUtil.isNotEmpty(usd.getReportType()),"REPORT_TYPE",usd.getReportType()))
                .eq("INCOME_TYPE", usd.getIncomeType())
                .eq("SHOP_NAME", usd.getShopName())
                .eq("SITE", usd.getSite())
                .eq("CONFIRM_STATUS", 0)
                .isNull("ALLOC_ID");
        StationManualAllocation stationManualAllocation = stationManualAllocationService.getOne(manualQuery);
        if(stationManualAllocation == null){
            //新增
            StationManualAllocation entity =new StationManualAllocation();
            entity.setFiscalPeriod(usd.getFiscalPeriod());
            entity.setReportType(usd.getReportType());
            if (StrUtil.isEmpty(usd.getReportType())) {
                entity.setReportType("0");
            } else {
                entity.setReportType(usd.getReportType());
            }
            entity.setIncomeType(usd.getIncomeType());
            entity.setShopName(usd.getShopName());
            entity.setSite(usd.getSite());
            entity.setAccountingCurrency(usd.getAccountingCurrency());
            entity.setSku("0");
            entity.setDepartment("0");
            entity.setTeam("0");
            entity.setMaterialCode("0");
            entity.setSalesBrand("0");
            entity.setCreateAt(new Date());
            entity.setConfirmStatus(BigDecimal.ZERO);
            entity.setCreateBy(param.getConfirmBy());
            entity.setCreateAt(param.getConfirmDate());
            entity.setStorageFee(usd.getStorageFee());
            entity.setAdvertising(usd.getAdvertising());
            stationManualAllocationService.save(entity);
        } else {
            //汇总更新
            stationManualAllocation.setCreateBy(param.getConfirmBy());
            stationManualAllocation.setCreateAt(param.getConfirmDate());
            stationManualAllocation.setStorageFee(stationManualAllocation.getStorageFee().add(usd.getStorageFee()));
            stationManualAllocation.setAdvertising(stationManualAllocation.getAdvertising().add(usd.getAdvertising()));
            stationManualAllocationService.updateById(stationManualAllocation);
        }
    }



    //手动分摊调整表   manualAllocationAdjustService
    public void insertManualAllocationAdjust(SettlementDetailUsdParam param,SettlementDetailUsd usd) throws Exception{
        manualAllocationAdjustService.updateAmountOrInsert(usd);
    }
}
