package com.tadpole.cloud.platformSettlement.modular.finance.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.stylefeng.guns.cloud.libs.context.auth.LoginContext;
import cn.stylefeng.guns.cloud.libs.mp.page.PageFactory;
import cn.stylefeng.guns.cloud.model.page.PageResult;
import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import cn.stylefeng.guns.cloud.system.core.dbs.annotation.DataSource;
import com.tadpole.cloud.platformSettlement.api.finance.entity.StationAutoAllocation;
import com.tadpole.cloud.platformSettlement.modular.finance.mapper.StationAutoAllocationMapper;
import com.tadpole.cloud.platformSettlement.api.finance.model.params.SettlementDetailUsdParam;
import com.tadpole.cloud.platformSettlement.api.finance.model.params.StationAutoAllocationParam;
import com.tadpole.cloud.platformSettlement.api.finance.model.result.SettlementDetailUsdResult;
import com.tadpole.cloud.platformSettlement.api.finance.model.result.StationAutoAllocationResult;
import com.tadpole.cloud.platformSettlement.modular.finance.service.IStationAutoAllocationService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.BoundValueOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
* <p>
* 站内自动分摊表 服务实现类
* </p>
*
* @author gal
* @since 2021-12-24
*/
@Service
public class StationAutoAllocationServiceImpl extends ServiceImpl<StationAutoAllocationMapper, StationAutoAllocation> implements IStationAutoAllocationService {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    /**
     * 批量确认站内费用自动分摊标识
     */
    @Value("${rediskey.confirmStationAuto}")
    public String confirmStationAuto;

    @DataSource(name = "finance")
    @Override
    public PageResult<StationAutoAllocationResult> findPageBySpec(StationAutoAllocationParam param) {
        Page pageContext = param.getPageContext();

        IPage<StationAutoAllocationResult> page = this.baseMapper.findPageBySpec(pageContext, param);
        return new PageResult<>(page);
    }

    @DataSource(name = "finance")
    @Override
    public List<StationAutoAllocationResult> export(StationAutoAllocationParam param) {

        Page pageContext = PageFactory.defaultPage();
        pageContext.setSize(Integer.MAX_VALUE);
        IPage<StationAutoAllocationResult> page = this.baseMapper.findPageBySpec(pageContext, param);
        return page.getRecords();
    }

    @DataSource(name = "finance")
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseData confirm(StationAutoAllocationParam param) {

        StationAutoAllocationParam pa = new StationAutoAllocationParam();
        String confirmBy = ObjectUtil.isNotEmpty(LoginContext.me().getLoginUser())?LoginContext.me().getLoginUser().getName():param.getConfirmBy();

        //被分摊金额记录
        StationAutoAllocation total=this.baseMapper.selectById(param.getId());

        //父ID审核明细数据
        QueryWrapper<StationAutoAllocation> qs=new QueryWrapper<>();
        qs.eq("ALLOC_ID",param.getId());

        List<StationAutoAllocation> checks=this.baseMapper.selectList(qs);

        BigDecimal SalesExcludingTaxFeeSum = checks.stream().map(StationAutoAllocation::getSalesExcludingTax).reduce(BigDecimal.ZERO, BigDecimal::add);
        BigDecimal TaxSum = checks.stream().map(StationAutoAllocation::getTax).reduce(BigDecimal.ZERO, BigDecimal::add);
        BigDecimal SalesPromotionSum = checks.stream().map(StationAutoAllocation::getSalesPromotion).reduce(BigDecimal.ZERO, BigDecimal::add);
        BigDecimal RefundSum = checks.stream().map(StationAutoAllocation::getRefund).reduce(BigDecimal.ZERO, BigDecimal::add);
        BigDecimal RefundPromotionSum = checks.stream().map(StationAutoAllocation::getRefundPromotion).reduce(BigDecimal.ZERO, BigDecimal::add);
        BigDecimal GiftwarpShippingSum = checks.stream().map(StationAutoAllocation::getGiftwarpShipping).reduce(BigDecimal.ZERO, BigDecimal::add);
        BigDecimal CommissionSum = checks.stream().map(StationAutoAllocation::getCommission).reduce(BigDecimal.ZERO, BigDecimal::add);
        BigDecimal RefundCommissionSum = checks.stream().map(StationAutoAllocation::getRefundCommission).reduce(BigDecimal.ZERO, BigDecimal::add);
        BigDecimal GoodwillSum = checks.stream().map(StationAutoAllocation::getGoodwill).reduce(BigDecimal.ZERO, BigDecimal::add);
        BigDecimal AmazonFeesSum = checks.stream().map(StationAutoAllocation::getAmazonFees).reduce(BigDecimal.ZERO, BigDecimal::add);
        BigDecimal ReimbursementSum = checks.stream().map(StationAutoAllocation::getReimbursement).reduce(BigDecimal.ZERO, BigDecimal::add);
        BigDecimal OtherSum = checks.stream().map(StationAutoAllocation::getOther).reduce(BigDecimal.ZERO, BigDecimal::add);
        BigDecimal WithheldTaxSum = checks.stream().map(StationAutoAllocation::getWithheldTax).reduce(BigDecimal.ZERO, BigDecimal::add);
        BigDecimal DisposeFeeSum = checks.stream().map(StationAutoAllocation::getDisposeFee).reduce(BigDecimal.ZERO, BigDecimal::add);
        BigDecimal RemovalDealSum = checks.stream().map(StationAutoAllocation::getRemovalDeal).reduce(BigDecimal.ZERO, BigDecimal::add);

        //总记录金额汇总
        BigDecimal totalSum=total.getSalesExcludingTax().add(total.getTax()).add(total.getSalesPromotion()).add(total.getRefund()).add(total.getRefundPromotion())
                .add(total.getGiftwarpShipping()).add(total.getCommission()).add(total.getRefundCommission()).add(total.getGoodwill()).add(total.getAmazonFees())
                .add(total.getReimbursement()).add(total.getOther()).add(total.getWithheldTax()).add(total.getDisposeFee())
                .add(total.getRemovalDeal());
        //明细金额汇总
        BigDecimal detailSum=SalesExcludingTaxFeeSum.add(TaxSum).add(SalesPromotionSum).add(RefundSum).add(RefundPromotionSum).add(GiftwarpShippingSum).add(CommissionSum).add(RefundCommissionSum)
                .add(GoodwillSum).add(AmazonFeesSum).add(ReimbursementSum).add(OtherSum).add(WithheldTaxSum).add(DisposeFeeSum).add(RemovalDealSum);

        //审核校验->汇总手工分摊明细金额
        if(totalSum.compareTo(detailSum)==0) {

            this.baseMapper.updateToReport(param);

            UpdateWrapper<StationAutoAllocation> updateWrapper = new UpdateWrapper();
            updateWrapper.eq("ALLOC_ID",param.getId()).set("CONFIRM_STATUS","1")
                    .set("CONFIRM_BY", confirmBy)
                    .set("CONFIRM_DATE",new Date());

            this.baseMapper.update(null,updateWrapper);

            StationAutoAllocation parent = new StationAutoAllocation();

            parent.setId(param.getId());
            parent.setConfirmStatus(new BigDecimal(1));
            parent.setConfirmBy(confirmBy);
            parent.setConfirmDate(new Date());

            this.baseMapper.updateById(parent);
            return ResponseData.success();
        }
        else
        {
            return ResponseData.error("会计期间【"+total.getFiscalPeriod()+"】账号【"+total.getShopName()+
                    "】站点【"+total.getSite()+"】,被分摊总记录和明细汇总金额不相等");
        }

    }


    @DataSource(name = "finance")
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void confirmManual(List<StationAutoAllocation> param) {

        //todo 更新结算报告
        StationAutoAllocationParam params=new StationAutoAllocationParam();
        params.setId(param.get(0).getId());

        for(int i=1;i<param.size();i++){

            param.get(i).setAllocId(param.get(0).getId());

            //保存手动分摊明细
            if(param.get(i).getId()!=null){
                this.updateById(param.get(i));
            }else {
                this.save(param.get(i));
            }
        }

        this.baseMapper.updateToReport(params);

        //更新确认状态
        UpdateWrapper<StationAutoAllocation> updateWrapper = new UpdateWrapper<>();
        updateWrapper
                .eq("id", param.get(0).getId()).or().eq("ALLOC_ID",param.get(0).getId())
                .set("CONFIRM_DATE", new Date())
                .set("CONFIRM_BY", LoginContext.me().getLoginUser().getName())
                .set("CONFIRM_STATUS", 1);

        this.baseMapper.update(null, updateWrapper);

    }

    @DataSource(name = "finance")
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseData confirmBatch(StationAutoAllocationParam param) {

        //redis操作需绑定key
        BoundValueOperations toList = redisTemplate.boundValueOps(this.confirmStationAuto);

        try{
            //从非空则为正在批量确认中
            if (toList.get() != null && StrUtil.isNotEmpty((String)toList.get())){
                return ResponseData.error("正在批量确认中!");
            }
            //设定正在批量确认
            toList.set("正在批量确认中!");

            QueryWrapper<StationAutoAllocation> queryWrapper = new QueryWrapper<>();

            queryWrapper
                    .eq("SHOP_NAME", param.getShopName())
                    .eq("FISCAL_PERIOD", param.getFiscalPeriod())
                    .eq("CONFIRM_STATUS",0)
                    .eq("IS_MANUAL",0)
                    .in(CollectionUtils.isNotEmpty(param.getSites()),"SITE",param.getSites())
                    .isNull("ALLOC_ID");

            List<StationAutoAllocation> detailStation=this.list(queryWrapper);
            queryWrapper.clear();



            if (CollUtil.isEmpty(detailStation)) {
                return ResponseData.success("无可确认的数据!");
            }
            //批量保存
            for (StationAutoAllocation pa : detailStation) {
                param.setId(pa.getId());
                this.confirm(param);
            }
        return ResponseData.success(detailStation);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseData.error("生成失败!:"+e);
        } finally{
            toList.set("");
        }
    }

    @DataSource(name = "finance")
    @Override
    public StationAutoAllocationResult getQuantity(StationAutoAllocationParam param) {
        return this.baseMapper.getQuantity(param);
    }

    @DataSource(name = "finance")
    @Override
    public List<SettlementDetailUsdResult> getExitSkuMoney(SettlementDetailUsdParam param) {
         return this.baseMapper.getExitSkuMoney(param);
    }

}
