package com.tadpole.cloud.platformSettlement.modular.finance.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.stylefeng.guns.cloud.libs.context.auth.LoginContext;
import cn.stylefeng.guns.cloud.libs.mp.page.PageFactory;
import cn.stylefeng.guns.cloud.model.excel.listener.BaseExcelListener;
import cn.stylefeng.guns.cloud.model.page.PageResult;
import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import cn.stylefeng.guns.cloud.system.core.dbs.annotation.DataSource;
import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tadpole.cloud.platformSettlement.api.finance.entity.*;
import com.tadpole.cloud.platformSettlement.api.finance.model.params.SettlementDetailListingParam;
import com.tadpole.cloud.platformSettlement.api.finance.model.params.SpotExchangeRateParam;
import com.tadpole.cloud.platformSettlement.api.finance.model.result.SettlementDetailListingResult;
import com.tadpole.cloud.platformSettlement.api.finance.model.result.SettlementDetailResult;
import com.tadpole.cloud.platformSettlement.modular.finance.mapper.MaterialMapper;
import com.tadpole.cloud.platformSettlement.modular.finance.mapper.SettlementDetailListingMapper;
import com.tadpole.cloud.platformSettlement.modular.finance.service.ISettlementDetailListingService;
import com.tadpole.cloud.platformSettlement.modular.finance.service.ISettlementDetailUsdService;
import com.tadpole.cloud.platformSettlement.modular.finance.service.ISpotExchangeRateService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.BoundValueOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
* <p>
* 收入记录表 服务实现类
* </p>
*
* @author gal
* @since 2021-12-24
*/
@Slf4j
@Service
public class SettlementDetailListingServiceImpl extends ServiceImpl<SettlementDetailListingMapper, SettlementDetailListing> implements ISettlementDetailListingService {

    @Autowired
    ISettlementDetailUsdService detailUsdService;

    @Autowired
    ISpotExchangeRateService rateService;

    @Resource
    MaterialMapper materialMapper;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    /**
     * 批量确认结算单原币标识
     */

    @Value("${rediskey.confirmListing}")
    public String confirmListing;

    @DataSource(name = "finance")
    @Override
    public PageResult<SettlementDetailListingResult> findPageBySpec(SettlementDetailListingParam param) {
        Page pageContext = getPageContext();

        IPage<SettlementDetailListingResult> page = this.baseMapper.findPageBySpec(pageContext, param);
        return new PageResult<>(page);
    }

    @DataSource(name = "finance")
    @Override
    public List<SettlementDetailListingResult> export(SettlementDetailListingParam param) {
        Page pageContext = PageFactory.defaultPage();
        pageContext.setSize(Integer.MAX_VALUE);
        IPage<SettlementDetailListingResult> page = this.baseMapper.findPageBySpec(pageContext, param);
        return page.getRecords();

    }


    /**
     * 导入修改sku
     */
    @DataSource(name = "finance")
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseData uploadSku(MultipartFile file) {
        log.info("导入修改SKU开始");
        BufferedInputStream buffer = null;
        try{
            buffer = new BufferedInputStream(file.getInputStream());
            BaseExcelListener listener = new BaseExcelListener<SettlementDetailListing>();
            EasyExcel.read(buffer, SettlementDetailListing.class, listener).sheet().doRead();
            List<SettlementDetailListing> dataList = listener.getDataList();
            for (SettlementDetailListing  data: dataList) {
                String sku =  data.getSku() ;
                String id  =  data.getId().toString() ;
                if (StrUtil.isNotEmpty(sku)){
                    sku = sku.trim();
                }
                if (StrUtil.isNotEmpty(id) && StrUtil.isNotEmpty(sku)){
                    UpdateWrapper<SettlementDetailListing> updateWrapper = new UpdateWrapper<>();
                    updateWrapper
                        .eq("ID", id)
                        .set("SKU", sku);
                    this.baseMapper.update(null, updateWrapper);
                    updateWrapper.clear();
                }
            }
            if (CollectionUtil.isEmpty(dataList)) {
                return ResponseData.error("数据为空，无法导入！");
            }
            return ResponseData.success();
        }catch (Exception e) {
            log.error("导入修改SKU异常，导入失败！", e);
            return ResponseData.error("导入修改SKU异常，导入失败！");
        } finally {
            if (buffer != null) {
                try {
                    buffer.close();
                } catch (IOException e) {
                    log.error("导入修改SKU关闭流异常", e);
                }
            }
        }


    };






    @DataSource(name = "finance")
    @Override
    public List<SettlementDetail> getSettlementMoney(SettlementDetail param) {
        return this.baseMapper.getSettlementMoney(param);
    }

    @DataSource(name = "finance")
    @Override
    public List<DatarangeDetailComfirm> getDataRangeMoney(DatarangeDetailComfirm param) {
        return this.baseMapper.getDataRangeMoney(param);
    }

    @DataSource(name = "finance")
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseData confirm(SettlementDetailListingParam param) throws ParseException {


        //审核记录写入结算单明细(美金)表
        QueryWrapper<SettlementDetailListing> queryWrapper = new QueryWrapper<>();
        if (param.getId()!=null){
            queryWrapper.eq("ID",param.getId());
        }
        //插入确认头部信息
        SettlementDetailListing detailListing = this.baseMapper.selectOne(queryWrapper);


        String skuStr=detailListing.getSku();
        //按确认类型批量审核
        // 正常
        if(param.getConfirmType()==0 && (!detailListing.getDepartment().equals("0") || detailListing.getSku().equals("0")))
        {
            confirmTypeAudit(param,detailListing,skuStr);
        }
        //sku规定期限没有listing信息，将sku置为0
        //异常且超时
        UpdateWrapper<SettlementDetailListing> updateskuWrapper = new UpdateWrapper<>();
        if(param.getConfirmType()==1 && detailListing.getDepartment().equals("0") && LocalDateTime.now().isAfter(detailListing.getLatestDate()))
        {
            updateskuWrapper.eq("id", param.getId())
                    .set("sku","0");
            this.baseMapper.update(null, updateskuWrapper);
            skuStr="0";
            confirmTypeAudit(param,detailListing,skuStr);
        }
        //异常未超时
        if(param.getConfirmType()==2 && detailListing.getDepartment().equals("0") && LocalDateTime.now().isBefore(detailListing.getLatestDate()))
        {
            updateskuWrapper.eq("id", param.getId())
                    .set("sku","0");
            this.baseMapper.update(null, updateskuWrapper);
            skuStr="0";
            confirmTypeAudit(param,detailListing,skuStr);
        }
        return ResponseData.success();
    }

    /**
     * 按确认类型批量审核
     * @param
     * @return
     */
    protected ResponseData confirmTypeAudit(SettlementDetailListingParam param,SettlementDetailListing detailListing,String skuStr){

        //审核结算单原币记录
        UpdateWrapper<SettlementDetailListing> updateWrapper = new UpdateWrapper<>();
        updateWrapper
                .eq("id", param.getId())
                .set("CONFIRM_DATE", new Date())
                .set("CONFIRM_BY", LoginContext.me().getLoginUser().getName())
                .set("CONFIRM_STATUS", 1);
        this.baseMapper.update(null, updateWrapper);


        //取汇率日期
        SpotExchangeRateParam rateParam=new SpotExchangeRateParam();
        rateParam.setOriginalCurrency(detailListing.getOriginalCurrency());
        rateParam.setEffectDate(DateUtil.parse(detailListing.getFiscalPeriod()+"-01"));

        //查询汇率
        BigDecimal exchangeRate;
        SpotExchangeRate ss=rateService.getRateByDateCurrency(rateParam);

        //原币USD汇率值为1
        if(detailListing.getOriginalCurrency().equals("USD"))
        {
            exchangeRate=BigDecimal.ONE;
        }
        else
        {
            if(ss==null){
                return ResponseData.error("Erp无法查询到汇率");
            }
            exchangeRate=ss.getDirectRate();
        }

        SettlementDetailUsd detailUsd=new SettlementDetailUsd();
        detailUsd.setListingId(detailListing.getId());
        detailUsd.setFiscalPeriod(detailListing.getFiscalPeriod());
        detailUsd.setSettlementId(detailListing.getSettlementId());
        detailUsd.setReportType(detailListing.getReportType());
        detailUsd.setIncomeType(detailListing.getIncomeType());
        detailUsd.setShopName(detailListing.getShopName());
        detailUsd.setSite(detailListing.getSite());
        detailUsd.setSku(skuStr);
        detailUsd.setDepartment(detailListing.getDepartment());
        detailUsd.setTeam(detailListing.getTeam());
        detailUsd.setMaterialCode(detailListing.getMaterialCode());
        if(detailUsd.getMaterialCode().equals("0")){
            detailUsd.setProductType("0");
        }else{
            detailUsd.setProductType(materialMapper.getMaterial(detailUsd.getMaterialCode())
                    .getProductType());
        }
        detailUsd.setSalesBrand(detailListing.getSalesBrand());
        detailUsd.setAccountingCurrency("USD");
        detailUsd.setCreateBy(LoginContext.me().getLoginUser().getName());
        detailUsd.setCreateAt(new Date());

        detailUsd.setVolumeNormal(detailListing.getVolumeNormal());
        detailUsd.setVolumeReissue(detailListing.getVolumeReissue());
        detailUsd.setSalesExcludingTax(detailListing.getSalesExcludingTax().multiply(exchangeRate).setScale(2,BigDecimal.ROUND_HALF_UP));
        detailUsd.setTax(detailListing.getTax().multiply(exchangeRate).setScale(2,BigDecimal.ROUND_HALF_UP));
        detailUsd.setSalesPromotion(detailListing.getSalesPromotion().multiply(exchangeRate).setScale(2,BigDecimal.ROUND_HALF_UP));
        detailUsd.setRefund(detailListing.getRefund().multiply(exchangeRate).setScale(2,BigDecimal.ROUND_HALF_UP));
        detailUsd.setRefundPromotion(detailListing.getRefundPromotion().multiply(exchangeRate).setScale(2,BigDecimal.ROUND_HALF_UP));
        detailUsd.setRefundCommission(detailListing.getRefundCommission().multiply(exchangeRate).setScale(2,BigDecimal.ROUND_HALF_UP));
        detailUsd.setGiftwarpShipping(detailListing.getGiftwarpShipping().multiply(exchangeRate).setScale(2,BigDecimal.ROUND_HALF_UP));
        detailUsd.setCommission(detailListing.getCommission().multiply(exchangeRate).setScale(2,BigDecimal.ROUND_HALF_UP));
        detailUsd.setGoodwill(detailListing.getGoodwill().multiply(exchangeRate).setScale(2,BigDecimal.ROUND_HALF_UP));
        detailUsd.setAmazonFees(detailListing.getAmazonFees().multiply(exchangeRate).setScale(2,BigDecimal.ROUND_HALF_UP));
        detailUsd.setStorageFee(detailListing.getStorageFee().multiply(exchangeRate).setScale(2,BigDecimal.ROUND_HALF_UP));
        detailUsd.setReimbursement(detailListing.getReimbursement().multiply(exchangeRate).setScale(2,BigDecimal.ROUND_HALF_UP));
        detailUsd.setOther(detailListing.getOther().multiply(exchangeRate).setScale(2,BigDecimal.ROUND_HALF_UP));
        detailUsd.setWithheldTax(detailListing.getWithheldTax().multiply(exchangeRate).setScale(2,BigDecimal.ROUND_HALF_UP));
        detailUsd.setRemovalDeal(detailListing.getRemovalDeal().multiply(exchangeRate).setScale(2,BigDecimal.ROUND_HALF_UP));
        detailUsd.setDisposeFee(detailListing.getDisposeFee().multiply(exchangeRate).setScale(2,BigDecimal.ROUND_HALF_UP));
        detailUsd.setAdvertising(detailListing.getAdvertising().multiply(exchangeRate).setScale(2,BigDecimal.ROUND_HALF_UP));
        detailUsdService.save(detailUsd);
        return ResponseData.success();
    }

    @DataSource(name = "finance")
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseData confirmBatch(SettlementDetailListingParam param) throws ParseException {
        //自动调接口时获取不到操作人,使用传入值
        String name = ObjectUtil.isNotEmpty(LoginContext.me().getLoginUser())?LoginContext.me().getLoginUser().getName():param.getConfirmBy();


        log.info("结算单明细（原币）批量确认开始，账号[{}]，站点[{}]，会计期间[{}]，确认类型[{}]，操作人[{}]",
                param.getShopName(),
                Arrays.toString(param.getSites().toArray()),
                param.getFiscalPeriod(),
                param.getConfirmType(),
                name);
        long start = System.currentTimeMillis();
        //redis操作需绑定key
        BoundValueOperations toList = redisTemplate.boundValueOps(this.confirmListing);

        try{
            //从非空则为正在批量确认中
            if (toList.get() != null && StrUtil.isNotEmpty((String)toList.get())){
                return ResponseData.error("正在批量确认中!");
            }
            //设定正在批量确认
            toList.set("正在批量确认中!");

            //待确认记录
            QueryWrapper<SettlementDetailListing> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("SHOP_NAME", param.getShopName())
                    .eq("FISCAL_PERIOD", param.getFiscalPeriod())
                    .eq("CONFIRM_STATUS",0)
                    .in(CollectionUtils.isNotEmpty(param.getSites()),"SITE",param.getSites());
            if (this.count(queryWrapper) == 0) {
                return ResponseData.success("无可确认的数据!");
            }

            //1、批量确认前需要批量刷listing
            this.updateDetailList(param);

            //2、批量将原币结算单明细表插入到美金结算单明细表
            param.setConfirmBy(name);
            param.setConfirmDate(new Date());
            this.baseMapper.insertBatchSettlementUsd(param);

            //3、批量确认更新原币结算单明细表
            param.setConfirmStatus("1");
            this.baseMapper.updateConfirmDetailListing(param);
            log.info("结算单明细（原币）批量确认结束，耗时---------->" +(System.currentTimeMillis() - start) + "ms");
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
    public SettlementDetailListingResult getQuantity(SettlementDetailListingParam param) {
        return this.baseMapper.getQuantity(param);
    }

    @DataSource(name = "finance")
    @Override
    public void afreshListing(SettlementDetailListingParam param) throws ParseException {
        //FNSKU为空的将SKU赋值于FNSKU
        this.baseMapper.afreshListingFnsku(param);
        //根据FNSKU匹配物料表刷SKU
        this.baseMapper.afreshListingSku(param);
        //根据FNSKU匹配物料表刷SKU（存档）
        this.baseMapper.afreshListingSkuFile(param);
        //根据SKU刷物料
        this.baseMapper.afreshListing(param);
        //根据SKU刷物料（存档）
        this.baseMapper.afreshListingFile(param);
    }

    @DataSource(name = "finance")
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void updateDetailList(SettlementDetailListingParam param) throws ParseException {
        this.afreshListing(param);
    }

    @DataSource(name = "finance")
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void updateLatestDate(SettlementDetailListingParam param) throws ParseException {

        List<SettlementDetailListingResult> resultList = emailList();
        //刷listing最晚时间保存
        for(int i=0;i<resultList.size();i++) {
            UpdateWrapper<SettlementDetailListing> updateWrapper=new UpdateWrapper<>();
            updateWrapper.eq("id",resultList.get(i).getId())
                    .set("LATEST_DATE",param.getLatestDate())
                    .set("CREATE_AT",new Date());

            this.update(updateWrapper);
        }
    }

    @DataSource(name = "finance")
    @Override
    public List<DatarangeDetailComfirm> getDataRangeNumber(DatarangeDetailComfirm param) {
        return this.baseMapper.getDataRangeNumber(param);
    }

    @DataSource(name = "finance")
    @Override
    public List<SettlementDetail> getSettlementNumber(SettlementDetail detailSettlement) {
        return this.baseMapper.getSettlementNumber(detailSettlement);
    }

    @DataSource(name = "finance")
    @Override
    public List<SettlementDetailListingResult> emailList() {
        List<SettlementDetailListingResult> list = this.baseMapper.emailList();
        return list;
    }

    @DataSource(name = "finance")
    @Override
    public List<SettlementDetailResult> getSettlementDataRangeNumber(SettlementDetail detailSettlement, DatarangeDetailComfirm detailDataRange) {
        return this.baseMapper.getSettlementDataRangeNumber(detailSettlement,detailDataRange);
    }

    @DataSource(name = "finance")
    @Override
    public List<DatarangeDetailComfirm> getDataRangeDiffrencelist(SettlementDetail detailSettlement, DatarangeDetailComfirm detailDataRange) {
        return this.baseMapper.getDataRangeDiffrencelist(detailSettlement,detailDataRange);
    }

    @DataSource(name = "finance")
    @Override
    public void updateSettlementDetailSkuNullToZero() {
         this.baseMapper.updateSettlementDetailSkuNullToZero();
    }

    @DataSource(name = "finance")
    @Override
    public void updateRangeDetailSkuNullToZero() {
         this.baseMapper.updateRangeDetailSkuNullToZero();
    }

    private Page getPageContext() {
        return PageFactory.defaultPage();
    }

}
