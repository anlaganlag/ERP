package com.tadpole.cloud.platformSettlement.modular.finance.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.stylefeng.guns.cloud.libs.context.auth.LoginContext;
import cn.stylefeng.guns.cloud.libs.mp.page.PageFactory;
import cn.stylefeng.guns.cloud.model.page.PageResult;
import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import cn.stylefeng.guns.cloud.system.core.dbs.annotation.DataSource;
import com.tadpole.cloud.platformSettlement.api.finance.entity.NewAveragePrice;
import com.tadpole.cloud.platformSettlement.api.finance.entity.NoAllocationAdjust;
import com.tadpole.cloud.platformSettlement.api.finance.entity.SettlementDetailUsd;
import com.tadpole.cloud.platformSettlement.api.finance.entity.SpotExchangeRate;
import com.tadpole.cloud.platformSettlement.modular.finance.mapper.NoAllocationAdjustMapper;
import com.tadpole.cloud.platformSettlement.api.finance.model.params.NoAllocationAdjustParam;
import com.tadpole.cloud.platformSettlement.api.finance.model.params.SettlementDetailUsdParam;
import com.tadpole.cloud.platformSettlement.api.finance.model.params.SpotExchangeRateParam;
import com.tadpole.cloud.platformSettlement.api.finance.model.result.NoAllocationAdjustResult;
import com.tadpole.cloud.platformSettlement.api.finance.model.result.SettlementDetailUsdResult;
import com.tadpole.cloud.platformSettlement.modular.finance.service.INewAveragePriceService;
import com.tadpole.cloud.platformSettlement.modular.finance.service.INoAllocationAdjustService;
import com.tadpole.cloud.platformSettlement.modular.finance.service.ISpotExchangeRateService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.BoundValueOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Date;
import java.util.List;

/**
* <p>
* 无需分摊调整表 服务实现类
* </p>
*
* @author gal
* @since 2021-12-24
*/
@Slf4j
@Service
public class NoAllocationAdjustServiceImpl extends ServiceImpl<NoAllocationAdjustMapper, NoAllocationAdjust> implements INoAllocationAdjustService {

    @Autowired
    INewAveragePriceService newAveragePriceService;

    @Autowired
    ISpotExchangeRateService spotExchangeRateService;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    /**
     * 批量确认无需分摊调整标识
     */
    @Value("${rediskey.confirmNoAdjust}")
    public String confirmNoAdjust;

    @DataSource(name = "finance")
    @Override
    public PageResult<NoAllocationAdjustResult> findPageBySpec(NoAllocationAdjustParam param) {
        Page pageContext = getPageContext();

        IPage<NoAllocationAdjustResult> page = this.baseMapper.findPageBySpec(pageContext, param);
        return new PageResult<>(page);
    }

    @DataSource(name = "finance")
    @Override
    public List<NoAllocationAdjustResult> export(NoAllocationAdjustParam param) {

        Page pageContext = PageFactory.defaultPage();
        pageContext.setSize(Integer.MAX_VALUE);
        IPage<NoAllocationAdjustResult> page = this.baseMapper.findPageBySpec(pageContext, param);
        return page.getRecords();
    }

    @DataSource(name = "finance")
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void confirm(NoAllocationAdjustParam param) {
        String name = "系统生成";
        if (ObjectUtil.isNotEmpty(LoginContext.me().getLoginUser())) {
            name = LoginContext.me().getLoginUser().getName();
        }

        QueryWrapper<NoAllocationAdjust> qs=new QueryWrapper<>();
        qs.eq("id",param.getId());
        NoAllocationAdjust check=this.baseMapper.selectOne(qs);
        if(check.getConfirmStatus().equals(BigDecimal.ZERO)){
            NoAllocationAdjust ss=new NoAllocationAdjust();

            ss.setId(param.getId());
            ss.setConfirmStatus(new BigDecimal(1));
            ss.setConfirmBy(name);
            ss.setConfirmDate(new Date());

            //todo 更新结算报告
            this.baseMapper.updateToReport(param);

            this.baseMapper.updateById(ss);

        }

    }

    @DataSource(name = "finance")
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseData confirmBatch(NoAllocationAdjustParam param) {

        log.info("无需分摊调整表审核开始");
        long start = System.currentTimeMillis();

        String name = "系统生成";
        if (ObjectUtil.isNotEmpty(LoginContext.me().getLoginUser())) {
            name = LoginContext.me().getLoginUser().getName();
        }

        //redis操作需绑定key
        BoundValueOperations toList = redisTemplate.boundValueOps(this.confirmNoAdjust);

        try{
            //从非空则为正在批量确认中
            if (toList.get() != null && StrUtil.isNotEmpty((String)toList.get())){
                return ResponseData.error("正在批量确认中!");
            }
            //设定正在批量确认
            toList.set("正在批量确认中!");

            QueryWrapper<NoAllocationAdjust> queryWrapper=new QueryWrapper<>();
            queryWrapper
                    .eq("SHOP_NAME", param.getShopName())
                    .eq("FISCAL_PERIOD", param.getFiscalPeriod())
                    .eq("CONFIRM_STATUS",0)
                    .in(CollectionUtils.isNotEmpty(param.getSites()),"SITE",param.getSites());

            List<NoAllocationAdjust> detailNoAllocation=this.list(queryWrapper);
            queryWrapper.clear();

            if (CollUtil.isEmpty(detailNoAllocation)) {
                return ResponseData.success("无可确认的数据!");
            }

            //批量刷结算报告
            this.baseMapper.updateToReportBatch(param);

            //修改状态
            UpdateWrapper<NoAllocationAdjust> updateWrapper = new UpdateWrapper<>();
            updateWrapper
                    .eq("SHOP_NAME", param.getShopName())
                    .eq("FISCAL_PERIOD", param.getFiscalPeriod())
                    .eq("CONFIRM_STATUS",0)
                    .in(CollectionUtils.isNotEmpty(param.getSites()),"SITE",param.getSites())
                    .set("CONFIRM_STATUS",1)
                    .set("CONFIRM_BY",name)
                    .set("CONFIRM_DATE",new Date());

            this.baseMapper.update(null,updateWrapper);


            return ResponseData.success(detailNoAllocation);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseData.error("生成失败!:"+e);
        } finally{
            toList.set("");
            log.info("无需分摊调整表审核结束，耗时---------->" +(System.currentTimeMillis() - start) + "ms");
        }

    }

    @DataSource(name = "finance")
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseData afreshCost(NoAllocationAdjustParam param) {

        //查询核算平均单价
        QueryWrapper<NewAveragePrice> queryWrapper=new QueryWrapper<>();
        queryWrapper
                .eq("FISCAL_PERIOD",param.getFiscalPeriod())
                .eq("SHOP_NAME",param.getShopName())
                .eq("SITE",param.getSite())
                .eq("MATERIAL_CODE",param.getMaterialCode())
                .eq("CONFIRM_STATUS",1);

        // 汇率
        NewAveragePrice avgPrice=newAveragePriceService.getOne(queryWrapper);
        if(avgPrice==null){
            return ResponseData.error("未维护核算库存单价！");
        }

        SpotExchangeRateParam rateParam = new SpotExchangeRateParam();
        rateParam.setOriginalCurrency("CNY");
        String d = param.getFiscalPeriod()+"-02";
        rateParam.setEffectDate(DateUtil.parse(d));

        //查询汇率
        BigDecimal exchangeRate;
        SpotExchangeRate spotExchangeRate  = spotExchangeRateService.getRateByDateCurrency(rateParam);

        //USD汇率值
        if(spotExchangeRate==null){
            return ResponseData.error("Erp无法查询到汇率");
        }
        exchangeRate=spotExchangeRate.getDirectRate();


        //成本核算金额、附加成本金额、头程物流费
        BigDecimal costAccountingFee = avgPrice.getPurchaseUnitPrice().multiply(param.getVolumeNormal()==null?BigDecimal.ZERO:param.getVolumeNormal().add(param.getVolumeReissue()==null?BigDecimal.ZERO:param.getVolumeReissue())).multiply(exchangeRate);
        BigDecimal additionalCostAmount = avgPrice.getAdditionalUnitPrice().multiply(param.getVolumeNormal()==null?BigDecimal.ZERO:param.getVolumeNormal().add(param.getVolumeReissue()==null?BigDecimal.ZERO:param.getVolumeReissue())).multiply(exchangeRate);
        BigDecimal firstTripFee = avgPrice.getLogisticsUnitPrice().multiply(param.getVolumeNormal()==null?BigDecimal.ZERO:param.getVolumeNormal().add(param.getVolumeReissue()==null?BigDecimal.ZERO:param.getVolumeReissue())).multiply(exchangeRate);

        //刷新成本值
        UpdateWrapper<NoAllocationAdjust> updateWrapper = new UpdateWrapper<>();
        updateWrapper
                .eq("id", param.getId())
                .eq("CONFIRM_STATUS",0)
                .set("COST_ACCOUNTING_FEE", costAccountingFee)
                .set("ADDITIONAL_COST_AMOUNT", additionalCostAmount)
                .set("FIRST_TRIP_FEE", firstTripFee)
                .set("UNIT_PURCHASE_COST", avgPrice.getPurchaseUnitPrice())
                .set("UNIT_PURCHASE_COST_ADDITONAL", avgPrice.getAdditionalUnitPrice())
                .set("UNIT_LOGISTICS_COST", avgPrice.getLogisticsUnitPrice());

        this.baseMapper.update(null, updateWrapper);
        return ResponseData.success();
    }


    @DataSource(name = "finance")
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseData refreshCost() throws ParseException {

        //查询未确认单批量重刷成本
        this.baseMapper.updateCostBatch();
        return  ResponseData.success();
    }

    @DataSource(name = "finance")
    @Override
    public NoAllocationAdjustResult getQuantity(NoAllocationAdjustParam param) {
        return this.baseMapper.getQuantity(param);
    }

    @DataSource(name = "finance")
    @Override
    public List<SettlementDetailUsdResult> getExitSkuAdjust(SettlementDetailUsdParam param) {
        return this.baseMapper.getExitSkuAdjust(param);
    }

    @DataSource(name = "finance")
    @Override
    public void updateAmountOrInsert(SettlementDetailUsd usd) {
         this.baseMapper.updateAmountOrInsert(usd);
    }

    private Page getPageContext() {
        return PageFactory.defaultPage();
    }

}
