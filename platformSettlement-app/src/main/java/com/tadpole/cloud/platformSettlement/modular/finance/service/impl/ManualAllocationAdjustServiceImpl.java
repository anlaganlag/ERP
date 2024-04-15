package com.tadpole.cloud.platformSettlement.modular.finance.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DatePattern;
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
import com.tadpole.cloud.platformSettlement.api.finance.entity.ManualAllocationAdjust;
import com.tadpole.cloud.platformSettlement.api.finance.entity.SettlementDetailUsd;
import com.tadpole.cloud.platformSettlement.api.finance.model.params.ManualAllocationAdjustParam;
import com.tadpole.cloud.platformSettlement.api.finance.model.params.SettlementDetailUsdParam;
import com.tadpole.cloud.platformSettlement.api.finance.model.result.ManualAllocationAdjustResult;
import com.tadpole.cloud.platformSettlement.api.finance.model.result.SettlementDetailUsdResult;
import com.tadpole.cloud.platformSettlement.modular.finance.mapper.ManualAllocationAdjustMapper;
import com.tadpole.cloud.platformSettlement.modular.finance.service.IManualAllocationAdjustService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.BoundValueOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

/**
* <p>
* 手动分摊调整表 服务实现类
* </p>
*
* @author gal
* @since 2021-12-24
*/
@Service
@Slf4j
public class ManualAllocationAdjustServiceImpl extends ServiceImpl<ManualAllocationAdjustMapper, ManualAllocationAdjust> implements IManualAllocationAdjustService {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    /**
     * 批量确认无需分摊调整标识
     */
    @Value("${rediskey.confirmManualAdjust}")
    public String confirmManualAdjust;

    @DataSource(name = "finance")
    @Override
    public PageResult<ManualAllocationAdjustResult> findPageBySpec(ManualAllocationAdjustParam param) {
        Page pageContext = getPageContext();

        IPage<ManualAllocationAdjustResult> page = this.baseMapper.findPageBySpec(pageContext, param);
        return new PageResult<>(page);
    }

    @DataSource(name = "finance")
    @Override
    public List<ManualAllocationAdjustResult> queryList(ManualAllocationAdjustParam param) {
        return this.baseMapper.queryList(param);
    }

    @DataSource(name = "finance")
    @Override
    public ResponseData importExcel(MultipartFile file,List<String> departmentList,List<String> teamList) {
        log.info("导入Excel处理开始");
        String account = LoginContext.me().getLoginUser().getName();
        BufferedInputStream buffer = null;
        try {
            buffer = new BufferedInputStream(file.getInputStream());
            BaseExcelListener listener = new BaseExcelListener<ManualAllocationAdjust>();
            EasyExcel.read(buffer, ManualAllocationAdjust.class, listener).sheet()
                    .doRead();

            List<ManualAllocationAdjust> dataList = listener.getDataList();
            if(CollectionUtil.isEmpty(dataList)){
                return ResponseData.error("导入数据为空，导入失败！");
            }

            //异常数据集合 
            List<ManualAllocationAdjust> errorList = new ArrayList<>();
            this.validation(dataList,errorList,account,departmentList,teamList);

            //批量保存更新表数据
            if(CollectionUtil.isNotEmpty(dataList)){
                this.saveBatch(dataList);
                if(CollectionUtil.isNotEmpty(errorList)){
                    String fileName = this.dealImportErrorList(errorList);
                    //部分导入成功
                    return ResponseData.error(ResponseData.DEFAULT_ERROR_CODE, "部分导入成功，存在异常数据数据", fileName);
                }
                return ResponseData.success("导入成功！");
            }
            if(CollectionUtil.isNotEmpty(errorList)){
                String fileName = this.dealImportErrorList(errorList);
                //导入失败
                return ResponseData.error(ResponseData.DEFAULT_ERROR_CODE, "导入失败，存在异常数据数据", fileName);
            }
            return ResponseData.success("导入失败！导入数据为空！");
        } catch (Exception e) {
            log.error("导入Excel处理异常，导入失败！", e);
            return ResponseData.error("导入Excel处理异常，导入失败！");
        } finally {
            if(buffer != null){
                try {
                    buffer.close();
                } catch (IOException e) {
                    log.error("导入Excel关闭流异常", e);
                }
            }
        }
    }

    @DataSource(name = "finance")
    @Override
    public void edit(ManualAllocationAdjustParam param) {
        ManualAllocationAdjust ss = new ManualAllocationAdjust();
        ss.setId(param.getId());
        BeanUtils.copyProperties(param, ss);
        this.baseMapper.updateById(ss);
    }

    @DataSource(name = "finance")
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseData confirm(ManualAllocationAdjustParam param) {

        String name = "系统生成";
        if (ObjectUtil.isNotEmpty(LoginContext.me().getLoginUser())) {
            name = LoginContext.me().getLoginUser().getName();
        }

        ManualAllocationAdjustParam pa = new ManualAllocationAdjustParam();

        //被分摊金额记录
        ManualAllocationAdjust total=this.baseMapper.selectById(param.getId());

        //父ID审核明细数据
        QueryWrapper<ManualAllocationAdjust> qs=new QueryWrapper<>();
        qs.eq("ALLOC_ID",param.getId());

        List<ManualAllocationAdjust> checks=this.baseMapper.selectList(qs);

        BigDecimal SalesExcludingTaxFeeSum = checks.stream().map(ManualAllocationAdjust::getSalesExcludingTax).reduce(BigDecimal.ZERO, BigDecimal::add);
        BigDecimal TaxSum = checks.stream().map(ManualAllocationAdjust::getTax).reduce(BigDecimal.ZERO, BigDecimal::add);
        BigDecimal SalesPromotionSum = checks.stream().map(ManualAllocationAdjust::getSalesPromotion).reduce(BigDecimal.ZERO, BigDecimal::add);
        BigDecimal RefundSum = checks.stream().map(ManualAllocationAdjust::getRefund).reduce(BigDecimal.ZERO, BigDecimal::add);
        BigDecimal RefundPromotionSum = checks.stream().map(ManualAllocationAdjust::getRefundPromotion).reduce(BigDecimal.ZERO, BigDecimal::add);
        BigDecimal GiftwarpShippingSum = checks.stream().map(ManualAllocationAdjust::getGiftwarpShipping).reduce(BigDecimal.ZERO, BigDecimal::add);
        BigDecimal CommissionSum = checks.stream().map(ManualAllocationAdjust::getCommission).reduce(BigDecimal.ZERO, BigDecimal::add);
        BigDecimal RefundCommissionSum = checks.stream().map(ManualAllocationAdjust::getRefundCommission).reduce(BigDecimal.ZERO, BigDecimal::add);
        BigDecimal GoodwillSum = checks.stream().map(ManualAllocationAdjust::getGoodwill).reduce(BigDecimal.ZERO, BigDecimal::add);
        BigDecimal AmazonFeesSum = checks.stream().map(ManualAllocationAdjust::getAmazonFees).reduce(BigDecimal.ZERO, BigDecimal::add);
        BigDecimal StorageFeeSum = checks.stream().map(ManualAllocationAdjust::getStorageFee).reduce(BigDecimal.ZERO, BigDecimal::add);
        BigDecimal ReimbursementSum = checks.stream().map(ManualAllocationAdjust::getReimbursement).reduce(BigDecimal.ZERO, BigDecimal::add);
        BigDecimal OtherSum = checks.stream().map(ManualAllocationAdjust::getOther).reduce(BigDecimal.ZERO, BigDecimal::add);
        BigDecimal WithheldTaxSum = checks.stream().map(ManualAllocationAdjust::getWithheldTax).reduce(BigDecimal.ZERO, BigDecimal::add);
        BigDecimal DisposeFeeSum = checks.stream().map(ManualAllocationAdjust::getDisposeFee).reduce(BigDecimal.ZERO, BigDecimal::add);
        BigDecimal RemovalDealSum = checks.stream().map(ManualAllocationAdjust::getRemovalDeal).reduce(BigDecimal.ZERO, BigDecimal::add);
        BigDecimal AdvertisingSum = checks.stream().map(ManualAllocationAdjust::getAdvertising).reduce(BigDecimal.ZERO, BigDecimal::add);

        //总记录金额汇总
        BigDecimal totalSum=total.getSalesExcludingTax().add(total.getTax()).add(total.getSalesPromotion()).add(total.getRefund()).add(total.getRefundPromotion())
                .add(total.getGiftwarpShipping()).add(total.getCommission()).add(total.getRefundCommission()).add(total.getGoodwill()).add(total.getAmazonFees())
                .add(total.getStorageFee()).add(total.getReimbursement()).add(total.getOther()).add(total.getWithheldTax()).add(total.getDisposeFee())
                .add(total.getRemovalDeal()).add(total.getAdvertising());
        //明细金额汇总
        BigDecimal detailSum=SalesExcludingTaxFeeSum.add(TaxSum).add(SalesPromotionSum).add(RefundSum).add(RefundPromotionSum).add(GiftwarpShippingSum).add(CommissionSum).add(RefundCommissionSum)
                .add(GoodwillSum).add(AmazonFeesSum).add(StorageFeeSum).add(ReimbursementSum).add(OtherSum).add(WithheldTaxSum).add(DisposeFeeSum).add(RemovalDealSum).add(AdvertisingSum);

        //审核校验->汇总手工分摊明细金额
        if(totalSum.compareTo(detailSum)==0 )
        {
            this.baseMapper.updateToReport(param);

            UpdateWrapper<ManualAllocationAdjust> updateWrapper = new UpdateWrapper();
            updateWrapper.eq("ALLOC_ID",param.getId()).set("CONFIRM_STATUS","1")
                    .set("CONFIRM_BY",name)
                    .set("CONFIRM_DATE",new Date());

            this.baseMapper.update(null,updateWrapper);

            ManualAllocationAdjust parent =new ManualAllocationAdjust();

            parent.setId(param.getId());
            parent.setConfirmStatus(new BigDecimal(1));
            parent.setConfirmBy(name);
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
    public void delete(ManualAllocationAdjustParam param) {
        QueryWrapper<ManualAllocationAdjust> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("ID",param.getId());
        this.baseMapper.delete(queryWrapper);
    }

    @DataSource(name = "finance")
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseData confirmBatch(ManualAllocationAdjustParam param) {

        //redis操作需绑定key
        BoundValueOperations toList = redisTemplate.boundValueOps(this.confirmManualAdjust);

        try{
            //从非空则为正在批量确认中
            if (toList.get() != null && StrUtil.isNotEmpty((String)toList.get())){
                return ResponseData.error("正在批量确认中!");
            }
            //设定正在批量确认
            toList.set("正在批量确认中!");
            //确认主数据
            List<ManualAllocationAdjustParam> params = this.baseMapper.queryConfirm(param);

            if (CollUtil.isEmpty(params)) {
                return ResponseData.success("无可确认的数据!");
            }

            for (ManualAllocationAdjustParam p : params) {
                this.confirm(p);
            }
            return ResponseData.success(params);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseData.error("生成失败!:"+e);
        } finally{
            toList.set("");
        }

    }

    @DataSource(name = "finance")
    @Override
    public void deleteBatch(ManualAllocationAdjustParam param) {

         QueryWrapper<ManualAllocationAdjust> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("FISCAL_PERIOD",param.getFiscalPeriod())
                        .eq("SHOP_NAME",param.getShopName())
                        .in("SITE",param.getFiscalPeriod()).isNotNull("ALLOC_ID")
                        .in(CollectionUtils.isNotEmpty(param.getSites()),"SITE",param.getSites());
            this.baseMapper.delete(queryWrapper);
    }

    @DataSource(name = "finance")
    @Override
    public ManualAllocationAdjustResult getQuantity(ManualAllocationAdjustParam param) {
        return this.baseMapper.getQuantity(param);
    }

    @DataSource(name = "finance")
    @Override
    public List<SettlementDetailUsdResult> getManualAllocationAdjust(SettlementDetailUsdParam param) {
        return this.baseMapper.getManualAllocationAdjust(param);
    }

    @DataSource(name = "finance")
    @Override
    public void updateAmountOrInsert(SettlementDetailUsd usd) {
        this.baseMapper.updateAmountOrInsert(usd);
    }

    private void validation(List<ManualAllocationAdjust> dataList, List<ManualAllocationAdjust> errorList, String account,List<String> departmentList
            ,List<String> teamList) {
        Iterator<ManualAllocationAdjust> iterator = dataList.listIterator();
        while(iterator.hasNext()) {
            ManualAllocationAdjust manualAllocationAdjust = iterator.next();
            manualAllocationAdjust.setCreateBy(account);
            manualAllocationAdjust.setCreateAt(new Date());

            if(manualAllocationAdjust.getAllocId() == null){
                //只导入明细，过滤掉父类ID为null的数据
                iterator.remove();
            } else if(StringUtils.isEmpty(manualAllocationAdjust.getFiscalPeriod())
                    || StringUtils.isEmpty(manualAllocationAdjust.getReportType())
                    || StringUtils.isEmpty(manualAllocationAdjust.getIncomeType())
                    || StringUtils.isEmpty(manualAllocationAdjust.getShopName())
                    || StringUtils.isEmpty(manualAllocationAdjust.getSite())
                    || StringUtils.isEmpty(manualAllocationAdjust.getAccountingCurrency())
                    || StringUtils.isEmpty(manualAllocationAdjust.getDepartment())
                    || StringUtils.isEmpty(manualAllocationAdjust.getTeam())
                    || StringUtils.isEmpty(manualAllocationAdjust.getProductType())
            ){
                //不为空校验
                manualAllocationAdjust.setUploadRemark("会计期间、报告类型、收入确认类型、账号、站点、核算币种、事业部、team、运营大类均不能为空");
                errorList.add(manualAllocationAdjust);
                iterator.remove();
            } else {
                //验证事业部，Team信息
                StringBuffer validInfo = new StringBuffer();
                if (!departmentList.contains(manualAllocationAdjust.getDepartment())) {
                    validInfo.append("事业部有误!");
                }
                if (!teamList.contains(manualAllocationAdjust.getTeam())) {
                    validInfo.append("Team有误!");
                }
                if (validInfo.length() > 0) {
                    manualAllocationAdjust.setUploadRemark(validInfo.toString());
                    errorList.add(manualAllocationAdjust);
                    iterator.remove();
                }
                else {
                    QueryWrapper<ManualAllocationAdjust> queryWrapper = new QueryWrapper<>();
                    queryWrapper.eq("ALLOC_ID", manualAllocationAdjust.getAllocId())
                            .eq("FISCAL_PERIOD", manualAllocationAdjust.getFiscalPeriod())
                            .eq("REPORT_TYPE", manualAllocationAdjust.getReportType())
                            .eq("INCOME_TYPE", manualAllocationAdjust.getIncomeType())
                            .eq("SHOP_NAME", manualAllocationAdjust.getShopName())
                            .eq("SITE", manualAllocationAdjust.getSite())
                            .eq("DEPARTMENT", manualAllocationAdjust.getDepartment())
                            .eq("TEAM", manualAllocationAdjust.getTeam())
                            .eq("PRODUCT_TYPE", manualAllocationAdjust.getProductType());
                    if (this.baseMapper.selectCount(queryWrapper) > 0) {
                        manualAllocationAdjust.setUploadRemark("数据重复，请排查重复数据！");
                        errorList.add(manualAllocationAdjust);
                        iterator.remove();
                    }
                }
            }
        }
    }

    private String dealImportErrorList(List<ManualAllocationAdjust> errorList){
        String filePath = System.getProperty("user.dir") + "/upload/";
        String fileName =  DateUtil.format(new Date(), DatePattern.PURE_DATETIME_MS_FORMAT) + ".xlsx";
        OutputStream out = null;
        try {
            out = new FileOutputStream(filePath + fileName,false);
            EasyExcel.write(out, ManualAllocationAdjust.class)
                    .sheet("导入结果").doWrite(errorList);
        } catch (FileNotFoundException e) {
            log.error("导入Excel异常数据导出异常", e);
        } finally {
            if(out != null){
                try {
                    out.close();
                } catch (IOException e) {
                    log.error("导入Excel异常数据导出流关闭异常", e);
                }
            }
        }
        return fileName;
    }
    
    private Page getPageContext() {
        return PageFactory.defaultPage();
    }
}
