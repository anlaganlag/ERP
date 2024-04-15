package com.tadpole.cloud.supplyChain.modular.logistics.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjUtil;
import cn.stylefeng.guns.cloud.libs.context.auth.LoginContext;
import cn.stylefeng.guns.cloud.model.excel.listener.BaseExcelListener;
import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import cn.stylefeng.guns.cloud.system.core.dbs.annotation.DataSource;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelReader;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.read.metadata.ReadSheet;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.alibaba.excel.write.metadata.fill.FillConfig;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tadpole.cloud.externalSystem.api.saihu.entity.SaihuShop;
import com.tadpole.cloud.externalSystem.api.saihu.model.params.SaiHuProductParam;
import com.tadpole.cloud.externalSystem.api.saihu.model.result.SaiHuBaseResult;
import com.tadpole.cloud.externalSystem.api.saihu.model.result.SaiHuPageDataResult;
import com.tadpole.cloud.supplyChain.api.logistics.entity.*;
import com.tadpole.cloud.supplyChain.api.logistics.model.params.*;
import com.tadpole.cloud.supplyChain.api.logistics.model.result.*;
import com.tadpole.cloud.supplyChain.modular.logistics.consumer.FixedExchangeRateConsumer;
import com.tadpole.cloud.supplyChain.modular.logistics.consumer.SaiHuSellConsumer;
import com.tadpole.cloud.supplyChain.modular.logistics.enums.TgDataTypeEnum;
import com.tadpole.cloud.supplyChain.modular.logistics.enums.TgPriceTypeEnum;
import com.tadpole.cloud.supplyChain.modular.logistics.mapper.TgCustomsApplyMapper;
import com.tadpole.cloud.supplyChain.modular.logistics.mapper.TgCustomsClearanceMapper;
import com.tadpole.cloud.supplyChain.modular.logistics.service.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.util.*;
import java.util.stream.Collectors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * <p>
 * 清关单 服务实现类
 * </p>
 *
 * @author ty
 * @since 2023-06-19
 */
@Slf4j
@Service
public class TgCustomsClearanceServiceImpl extends ServiceImpl<TgCustomsClearanceMapper, TgCustomsClearance> implements ITgCustomsClearanceService {

    @Resource
    private TgCustomsClearanceMapper mapper;
    @Autowired
    private ITgCustomsClearanceService clearanceService;
    @Autowired
    private ITgCustomsClearanceDetailService clearanceDetailService;
    @Autowired
    private TgCustomsApplyMapper applyMapper;
    @Autowired
    private ITgCustomsApplyService applyService;
    @Autowired
    private ITgBaseProductService productService;
    @Autowired
    private ITgBaseProductDetailService productDetailService;
    @Autowired
    private ITgCustomsPriceCoeffRuleService customsPriceCoeffRuleService;
    @Autowired
    private ITgCustomsPriceMinRuleService customsPriceMinRuleService;
    @Autowired
    private ITgCustomsAgainCoeffRuleService customsAgainCoeffRuleService;
//    @Autowired
//    private ITgLxListingInfoService lxListingInfoService;
    @Autowired
    private ITgCountryInfoService countryInfoService;
    @Autowired
    private ITgReceiveCompanyService receiveCompanyService;
    @Autowired
    private ITgCustomsTaxRateService tgCustomsTaxRateService;
    @Autowired
    private ITgSaihuProductService saihuProductService;
    @Autowired
    private FixedExchangeRateConsumer fixedExchangeRateConsumer;
    @Autowired
    private SaiHuSellConsumer saiHuSellConsumer;

    @Override
    @DataSource(name = "logistics")
    public ResponseData queryPage(TgCustomsClearanceParam param) {
        if(StringUtils.isNotEmpty(param.getPostDateEnd())){
            param.setPostDateEnd(param.getPostDateEnd() + " 23:59:59");
        }
        return ResponseData.success(mapper.queryPage(param.getPageContext(), param));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @DataSource(name = "logistics")
    public ResponseData delete(TgCustomsClearanceParam param) {
        //删除主数据
        this.removeById(param.getId());

        //删除明细数据
        LambdaQueryWrapper<TgCustomsClearanceDetail> detailWrapper = new LambdaQueryWrapper<>();
        detailWrapper.eq(TgCustomsClearanceDetail :: getPid, param.getId());
        clearanceDetailService.remove(detailWrapper);
        return ResponseData.success();
    }

    @Override
    @Transactional
    @DataSource(name = "EBMS")
    public ResponseData selectEbmsLogisticsPacking(TgLogisticsPackingResult param) {
        if(CollectionUtil.isEmpty(param.getPackCodeList())){
            return ResponseData.error("请输入出货清单号！");
        }
        //获取EBMS出货订单数据
        List<TgLogisticsPackingResult> packingList = applyMapper.selectLogisticsPacking(param.getPackCodeList());
        if(CollectionUtil.isNotEmpty(packingList)){
            for (TgLogisticsPackingResult packing : packingList) {
                List<TgCustomsClearanceDetail> packingDetailList = mapper.selectLogisticsPackingDetail(packing.getPackCode());
                if(CollectionUtil.isNotEmpty(packingDetailList)){
                    Map<String, List<TgCustomsClearanceDetail>> detailListMap = packingDetailList.stream().collect(Collectors.groupingBy(TgCustomsClearanceDetail :: getBoxNo, LinkedHashMap::new, Collectors.toList()));
                    packing.setQuantity(detailListMap.size());

                    Map<String, List<TgCustomsClearanceDetail>> packingDetailListMap = packingDetailList.stream().collect(Collectors.groupingBy(packingDetail -> packingDetail.getMaterialCode(), LinkedHashMap::new, Collectors.toList()));
                    List<String> matCodeList = packingDetailListMap.keySet().stream().collect(Collectors.toList());
                    //根据物料编码查询通关产品信息的规格型号、开票规格型号、公司品牌、开票品名
                    List<TgBaseProduct> productList = productService.getProductInfoList(matCodeList);
                    if(CollectionUtil.isEmpty(productList)){
                        return ResponseData.error("未查询到产品基本信息数据，物料编码：" + JSONObject.toJSON(matCodeList));
                    }
                    Map<String, List<TgBaseProduct>> productListMap = productList.stream().collect(Collectors.groupingBy(product -> product.getMaterialCode(), LinkedHashMap::new, Collectors.toList()));

                    Boolean mainIsSelect = Boolean.TRUE;
                    for (TgCustomsClearanceDetail clearanceDetail : packingDetailList) {
                        if(StringUtils.isBlank(clearanceDetail.getPackDirectCode())){
                            return ResponseData.error(clearanceDetail.getPackCode() + "调拨单号为空！");
                        }

                        List<TgBaseProduct> mBaseProductList = productListMap.get(clearanceDetail.getMaterialCode());
                        if(CollectionUtil.isEmpty(mBaseProductList)){
                            return ResponseData.error("未查询到产品基本信息数据，物料编码：" + clearanceDetail.getMaterialCode());
                        }
                        clearanceDetail.setType(mBaseProductList.get(0).getFitBrand());
                        clearanceDetail.setStyle(mBaseProductList.get(0).getStyle());
                        clearanceDetail.setCompanyBrand(mBaseProductList.get(0).getCompanyBrand());
                        clearanceDetail.setInvoiceProNameCn(mBaseProductList.get(0).getInvoiceProNameCn());

                        //根据出货清单、箱号、SKU、调拨单号判断是否关联
                        LambdaQueryWrapper<TgCustomsClearanceDetail> detailWrapper = new LambdaQueryWrapper<>();
                        detailWrapper.eq(TgCustomsClearanceDetail :: getPackCode, clearanceDetail.getPackCode())
                                .eq(TgCustomsClearanceDetail :: getBoxNo, clearanceDetail.getBoxNo())
                                .eq(TgCustomsClearanceDetail :: getSku, clearanceDetail.getSku())
                                .eq(TgCustomsClearanceDetail :: getPackDirectCode, clearanceDetail.getPackDirectCode())
                                .eq(TgCustomsClearanceDetail :: getDataType, "1");
                        TgCustomsClearanceDetail detail = clearanceDetailService.getOne(detailWrapper);
                        if(detail != null){
                            packing.setIsDeal("是");
                            clearanceDetail.setIsDeal("是");
                            clearanceDetail.setIsSelect(Boolean.TRUE);//明细数据是否选中
                            clearanceDetail.setId(detail.getId());
                        } else {
                            mainIsSelect = Boolean.FALSE;
                        }
                    }
                    packing.setIsSelect(mainIsSelect);//主数据是否选中
                    packing.setClearancePackDetailList(packingDetailList);
                }
            }
        }
        return ResponseData.success(packingList);
    }

    @Override
    @DataSource(name = "logistics")
    @Transactional(rollbackFor = Exception.class)
    public ResponseData selectSave(TgCustomsClearanceSaveParam param) {
        log.info("清关单关联新增保存入参[{}]", JSONObject.toJSON(param));
        String name = LoginContext.me().getLoginUser().getName();
        param.setPostDate(DateUtil.date());

        //查询ERP固定汇率表
        /*FixedExchangeRateParam rateParam  = new FixedExchangeRateParam();
        rateParam.setOriginalCurrency(param.getCurrency());
        rateParam.setEffectDate(DateUtil.beginOfDay(param.getPostDate()));
        FixedExchangeRate fixedExchangeRate = fixedExchangeRateConsumer.getRateByDateCurrency(rateParam);
        if(fixedExchangeRate == null){
            return ResponseData.error("ERP固定汇率不存在！币别：" + param.getCurrency() + "，发货日期：" + param.getPostDate());
        }*/

        TgCustomsClearance clearance = new TgCustomsClearance();
        BeanUtils.copyProperties(param, clearance);
        clearance.setCountryOfExport("CHINA");
        clearance.setDeliveryTerm("CIF");
        clearance.setPaymentMethord("T/T");
        clearance.setDataType(TgDataTypeEnum.MERGE.getCode());
        clearance.setCreateUser(name);

        //根据国家获取对应站点，用于通过站点和SKU查询对应的销售价格，Asin和主图链接
        LambdaQueryWrapper<TgCountryInfo> countryWrapper = new LambdaQueryWrapper<>();
        countryWrapper.eq(TgCountryInfo :: getCountryCode, clearance.getArrivalCountryCode());
        TgCountryInfo country = countryInfoService.getOne(countryWrapper);
        if(country == null){
            return ResponseData.error("未查询到运抵国：" + clearance.getArrivalCountryName() + "对应的国家信息！");
        }
        if(StringUtils.isBlank(country.getSite())){
            return ResponseData.error("未查询到运抵国：" + clearance.getArrivalCountryName() + "对应的站点信息！");
        }

        if(CollectionUtil.isEmpty(param.getClearanceDetails())){
            return ResponseData.error("清关单明细数据为空！");
        }
        List<TgCustomsClearanceDetail> clearanceDetailList = new ArrayList<>();
        for (TgCustomsClearanceDetail clearanceDetail : param.getClearanceDetails()) {
            if("否".equals(clearanceDetail.getIsDeal()) && clearanceDetail.getIsSelect()){
                //根据物料编码和运抵国取清关产品合并：清关品名、开票英文品名、报关英文材质、用途（适用品牌或对象）、带磁、带电、K3价格
                if(StringUtils.isBlank(clearanceDetail.getMaterialCode())){
                    return ResponseData.error(clearanceDetail.getPackCode() + "物料编码为空！");
                }
                //查询清关产品合并
                LambdaQueryWrapper<TgBaseProduct> mergeWrapper = new LambdaQueryWrapper<>();
                mergeWrapper.eq(TgBaseProduct :: getDataType, TgDataTypeEnum.MERGE.getCode())
                        .like(TgBaseProduct :: getMaterialCode, clearanceDetail.getMaterialCode());
                TgBaseProduct mergeProduct = productService.getOne(mergeWrapper);

                //查询产品基本信息
                LambdaQueryWrapper<TgBaseProduct> productWrapper = new LambdaQueryWrapper<>();
                productWrapper.eq(TgBaseProduct :: getDataType, TgDataTypeEnum.NOT_MERGE.getCode())
                        .eq(TgBaseProduct :: getMaterialCode, clearanceDetail.getMaterialCode());
                TgBaseProduct baseProduct = productService.getOne(productWrapper);

                if(baseProduct == null){
                    return ResponseData.error("未查询到" + clearanceDetail.getMaterialCode() + "对应的产品基本信息！");
                }

                //如果没有合并的信息则取未合并的
                if(mergeProduct == null){
                    mergeProduct = baseProduct;
                }

                clearanceDetail.setClearNameCn(mergeProduct.getInvoiceProNameCn());
                clearanceDetail.setInvoiceProNameEn(mergeProduct.getInvoiceProNameEn());
                clearanceDetail.setClearMaterialEn(mergeProduct.getClearMaterialEn());
                clearanceDetail.setFitBrand(mergeProduct.getFitBrand());
                if("0".equals(baseProduct.getIsCharged())){
                    clearanceDetail.setIsCharged("否");
                }
                if ("1".equals(baseProduct.getIsCharged())){
                    clearanceDetail.setIsCharged("是");
                }
                if("0".equals(baseProduct.getIsMagnet())){
                    clearanceDetail.setIsMagnet("否");
                }
                if ("1".equals(baseProduct.getIsMagnet())){
                    clearanceDetail.setIsMagnet("是");
                }

                //清关产品合并明细：HSCode
                LambdaQueryWrapper<TgBaseProductDetail> mergeDetailWrapper = new LambdaQueryWrapper<>();
                mergeDetailWrapper.eq(TgBaseProductDetail :: getPid, mergeProduct.getId())
                        .eq(TgBaseProductDetail :: getCountryCode, param.getArrivalCountryCode());
                TgBaseProductDetail mergeDetail = productDetailService.getOne(mergeDetailWrapper);
                if(mergeDetail == null){
                    return ResponseData.error("未查询到物料：" + clearanceDetail.getMaterialCode() + "，国家：" + param.getArrivalCountryName() + "对应的清关产品明细信息！");
                }
                clearanceDetail.setHscode(mergeDetail.getHsCode());
                clearanceDetail.setDataType(clearance.getDataType());
                clearanceDetail.setCreateUser(name);

                //根据站点和SKU获取领星Listing销售价、销售链接、主图链接数据
                /*LambdaQueryWrapper<TgLxListingInfo> lxListingWrapper = new LambdaQueryWrapper<>();
                lxListingWrapper.eq(TgLxListingInfo :: getSellerSku, clearanceDetail.getSku())
                        .eq(TgLxListingInfo :: getSite, country.getSite())
                        .eq(TgLxListingInfo :: getIsDelete, "0");
                TgLxListingInfo lxListing = lxListingInfoService.getOne(lxListingWrapper);
                if(lxListing != null){
                    clearanceDetail.setAmazonSalePrice(lxListing.getListingPrice() == null ? lxListing.getPrice() : lxListing.getListingPrice());
                    clearanceDetail.setAmazonSaleLink(country.getAmazonUrl() + "dp/" + lxListing.getAsin());
                    clearanceDetail.setAmazonPictureLink(lxListing.getSmallImageUrl());
                }*/
                /*LambdaQueryWrapper<TgSaihuProduct> saihuProductWrapper = new LambdaQueryWrapper<>();
                saihuProductWrapper.eq(TgSaihuProduct :: getSku, clearanceDetail.getSku())
                        .eq(TgSaihuProduct :: getSite, country.getSite())
                        .isNull(TgSaihuProduct :: getDxmPublishState);
                TgSaihuProduct saihuProduct = saihuProductService.getOne(saihuProductWrapper);
                if(saihuProduct != null){
                    clearanceDetail.setAmazonSalePrice(saihuProduct.getListingPrice() == null ? saihuProduct.getStandardPrice() : saihuProduct.getListingPrice());
                    clearanceDetail.setAmazonSaleLink(country.getAmazonUrl() + "dp/" + saihuProduct.getAsin());
                    clearanceDetail.setAmazonPictureLink(saihuProduct.getMainImage());
                }*/

                //计算单价：将人民币的K3价格换成目标币，K3价格 * 间接汇率
                if(baseProduct.getK3Price() == null){
                    return ResponseData.error(clearanceDetail.getMaterialCode() + "对应的产品基本信息采购价为空！");
                }
//                clearanceDetail.setUnitPrice(baseProduct.getK3Price().multiply(fixedExchangeRate.getIndirectRate()).setScale(2, BigDecimal.ROUND_HALF_UP));

                //计算清关单价
                /*ResponseData rd = calculateCustomsUnitPrice(clearance, clearanceDetail, mergeDetail);
                if(ResponseData.DEFAULT_ERROR_CODE.equals(rd.getCode())){
                    return rd;
                }*/
                clearanceDetailList.add(clearanceDetail);
            }
        }
        if(CollectionUtil.isNotEmpty(clearanceDetailList)){
            //生成清关单发票号
            clearance.setInvoiceNo(this.getNowQgdOrder());
            //总重量
            BigDecimal totalWeight = BigDecimal.ZERO;
            Map<String, List<TgCustomsClearanceDetail>> boxListMap = param.getClearanceDetails().stream().collect(Collectors.groupingBy(detail -> detail.getPackCode()+detail.getBoxNo(), LinkedHashMap::new, Collectors.toList()));
            for (List<TgCustomsClearanceDetail> boxList : boxListMap.values()) {
                totalWeight = totalWeight.add(boxList.get(0).getWeight());
            }
            //保费：总重量*运费系数*0.003
            clearance.setInsureCost(totalWeight.multiply(clearance.getTransportCost()).multiply(new BigDecimal(0.003)).setScale(2, BigDecimal.ROUND_HALF_UP));
            mapper.insert(clearance);

            for (TgCustomsClearanceDetail clearanceDetail : clearanceDetailList) {
                clearanceDetail.setPid(clearance.getId());
                clearanceDetailService.save(clearanceDetail);
            }
        }
        return ResponseData.success();
    }

    /**
     * 计算清关单价
     * @param clearance
     * @param clearanceDetail
     * @param mergeDetail
     * @return
     */
    private ResponseData calculateCustomsUnitPrice(TgCustomsClearance clearance, TgCustomsClearanceDetail clearanceDetail, TgBaseProductDetail mergeDetail){
        if(StringUtils.isNotBlank(clearance.getPriceType()) && clearance.getCustomsCoeff() != null){
            //取主表价格类型和清关折算率
            //价格
            BigDecimal price = null;
            if(TgPriceTypeEnum.BUY.getName().equals(clearance.getPriceType())){
                price = clearanceDetail.getUnitPrice();
            }
            if(TgPriceTypeEnum.SALE.getName().equals(clearance.getPriceType())){
                price = clearanceDetail.getAmazonSalePrice();
            }
            if(price == null){
                clearanceDetail.setCustomsUnitPrice(null);
            }else{
                clearanceDetail.setCustomsUnitPrice(price.multiply(clearance.getCustomsCoeff()).setScale(2, BigDecimal.ROUND_HALF_UP));
            }
        }else{
            //根据国家和HsCode获取清关税率的流转税率、关税率、附加税率
            LambdaQueryWrapper<TgCustomsTaxRate> taxRateWrapper = new LambdaQueryWrapper<>();
            taxRateWrapper.eq(TgCustomsTaxRate :: getCountryCode, mergeDetail.getCountryCode())
                    .eq(TgCustomsTaxRate :: getHsCode, mergeDetail.getHsCode());
            TgCustomsTaxRate customsTaxRate = tgCustomsTaxRateService.getOne(taxRateWrapper);
            if(customsTaxRate == null){
                return ResponseData.error("未查询到国家：" + mergeDetail.getCountryCode() + "，HSCode：" + mergeDetail.getHsCode() + "对应的清关税率！");
            }

            //根据运抵国和HSCode获取清关价格折算规则，没有则用运抵国查询
            TgCustomsPriceCoeffRuleParam priceCoeffParam = new TgCustomsPriceCoeffRuleParam();
            priceCoeffParam.setCountryCode(clearance.getArrivalCountryCode());
            priceCoeffParam.setHsCode(clearanceDetail.getHscode());
            priceCoeffParam.setTaxRate(customsTaxRate.getTaxRate());
            TgCustomsPriceCoeffRule priceCoeff = customsPriceCoeffRuleService.queryPriceCoeff(priceCoeffParam);
            if(priceCoeff == null){
                //根据运抵国获取清关价格折算规则
                priceCoeffParam.setHsCode(null);
                priceCoeff = customsPriceCoeffRuleService.queryPriceCoeff(priceCoeffParam);
                if(priceCoeff == null){
                    return ResponseData.error("未查询到国家：" + clearance.getArrivalCountryName() + "，HSCode：" + clearanceDetail.getHscode() + "对应的清关价格折算信息！");
                }
            }

            //价格
            if(TgPriceTypeEnum.BUY.getName().equals(priceCoeff.getPriceType())){
                clearanceDetail.setCustomsUnitPrice(clearanceDetail.getUnitPrice());
            }
            if(TgPriceTypeEnum.SALE.getName().equals(priceCoeff.getPriceType())){
                clearanceDetail.setCustomsUnitPrice(clearanceDetail.getAmazonSalePrice());
            }

            //折算
            if(clearanceDetail.getCustomsUnitPrice() == null){
                clearanceDetail.setCustomsUnitPrice(null);
            }else{
                clearanceDetail.setCustomsUnitPrice(clearanceDetail.getCustomsUnitPrice().multiply(priceCoeff.getCustomsCoeff()).setScale(2, BigDecimal.ROUND_HALF_UP));

                //根据运抵国和币制查询清关二次折算规则
                TgCustomsAgainCoeffRuleParam againCoeffParam = new TgCustomsAgainCoeffRuleParam();
                againCoeffParam.setCountryCode(clearance.getArrivalCountryCode());
                againCoeffParam.setCustomsCurrency(clearance.getCurrency());
                againCoeffParam.setCustomsUnitPrice(clearanceDetail.getCustomsUnitPrice());
                TgCustomsAgainCoeffRule againCoeff = customsAgainCoeffRuleService.queryAgainCoeff(againCoeffParam);
                if(againCoeff != null){
                    //二次折算
                    clearanceDetail.setCustomsUnitPrice(clearanceDetail.getCustomsUnitPrice().multiply(againCoeff.getAgainCoeff()).setScale(2, BigDecimal.ROUND_HALF_UP));
                }

                //判断是否小于最低清关价格规则，如果小于则取最低清关价格规则
                LambdaQueryWrapper<TgCustomsPriceMinRule> priceMinWrapper = new LambdaQueryWrapper<>();
                priceMinWrapper.eq(TgCustomsPriceMinRule :: getCountryCode, clearance.getArrivalCountryCode())
                        .eq(TgCustomsPriceMinRule :: getCustomsCurrency, clearance.getCurrency());
                TgCustomsPriceMinRule priceMin = customsPriceMinRuleService.getOne(priceMinWrapper);
                if(priceMin != null){
                    if(clearanceDetail.getCustomsUnitPrice().compareTo(priceMin.getMinCustomsPrice()) == -1){
                        clearanceDetail.setCustomsUnitPrice(priceMin.getMinCustomsPrice());
                    }
                }
            }
        }
        return ResponseData.success();
    }

    @Override
    @DataSource(name = "logistics")
    @Transactional(rollbackFor = Exception.class)
    public ResponseData selectEdit(TgCustomsClearanceSaveParam param) {
        log.info("清关单关联编辑保存入参[{}]", JSONObject.toJSON(param));
        if(StringUtils.isBlank(String.valueOf(param.getId()))){
            return ResponseData.error("编辑ID为空！");
        }
        TgCustomsClearance oldClearance = mapper.selectById(param.getId());
        if(oldClearance == null){
            return ResponseData.error("未查询到此清关发票！");
        }
        String name = LoginContext.me().getLoginUser().getName();

        //查询ERP固定汇率表
        /*FixedExchangeRateParam rateParam  = new FixedExchangeRateParam();
        rateParam.setOriginalCurrency(param.getCurrency());
        rateParam.setEffectDate(DateUtil.beginOfDay(oldClearance.getPostDate()));
        FixedExchangeRate fixedExchangeRate = fixedExchangeRateConsumer.getRateByDateCurrency(rateParam);
        if(fixedExchangeRate == null){
            return ResponseData.error("ERP固定汇率不存在！币别：" + param.getCurrency() + "，发货日期：" + param.getPostDate());
        }*/

        TgCustomsClearance clearance = new TgCustomsClearance();
        BeanUtils.copyProperties(param, clearance);
        clearance.setDataType(TgDataTypeEnum.MERGE.getCode());
        clearance.setUpdateUser(name);
        clearance.setUpdateTime(DateUtil.date());

        //根据国家获取对应站点，用于通过站点和SKU查询对应的销售价格，Asin和主图链接
        LambdaQueryWrapper<TgCountryInfo> countryWrapper = new LambdaQueryWrapper<>();
        countryWrapper.eq(TgCountryInfo :: getCountryCode, clearance.getArrivalCountryCode());
        TgCountryInfo country = countryInfoService.getOne(countryWrapper);
        if(country == null){
            return ResponseData.error("未查询到运抵国：" + clearance.getArrivalCountryName() + "对应的国家信息！");
        }
        if(StringUtils.isBlank(country.getSite())){
            return ResponseData.error("未查询到运抵国：" + clearance.getArrivalCountryName() + "对应的站点信息！");
        }

        //运费系数是否有变更，变更则需要重新计算保费
        Boolean isTransportCostDiff = Boolean.FALSE;
        if(oldClearance.getTransportCost().compareTo(clearance.getTransportCost()) != 0){
            isTransportCostDiff = Boolean.TRUE;
        }
        //币别是否有变更，变更则需要重新计算
        Boolean isCurrencyDiff = Boolean.FALSE;
        //价格类型和清关折算率是否有变更，变更则需要重新计算
        Boolean isDiff = Boolean.FALSE;
        //如果有更新或者新增明细数据，则需要重新计算
        Boolean isAddOrUpdate = Boolean.FALSE;
        if(!oldClearance.getCurrency().equals(clearance.getCurrency())){
            isCurrencyDiff = Boolean.TRUE;
        }
        if(StringUtils.isBlank(oldClearance.getPriceType()) || oldClearance.getCustomsCoeff() == null){
            if(StringUtils.isNotBlank(clearance.getPriceType()) && clearance.getCustomsCoeff() != null){
                isDiff = Boolean.TRUE;
            }
        }
        if(StringUtils.isNotBlank(oldClearance.getPriceType()) && oldClearance.getCustomsCoeff() != null){
            if(StringUtils.isBlank(clearance.getPriceType()) || clearance.getCustomsCoeff() == null){
                isDiff = Boolean.TRUE;
            }
            if(StringUtils.isNotBlank(clearance.getPriceType()) && clearance.getCustomsCoeff() != null
                    && (!oldClearance.getPriceType().equals(clearance.getPriceType()) || oldClearance.getCustomsCoeff() != clearance.getCustomsCoeff())){
                isDiff = Boolean.TRUE;
            }
        }
        if(CollectionUtil.isEmpty(param.getClearanceDetails())){
            mapper.updateById(clearance);
        }else{
            //新增
            List<TgCustomsClearanceDetail> saveDetailList = new ArrayList<>();
            //更新
            List<TgCustomsClearanceDetail> updateDetailList = new ArrayList<>();
            //删除
            List<BigDecimal> deleteList = new ArrayList<>();
            for (TgCustomsClearanceDetail clearanceDetail : param.getClearanceDetails()) {
                /**
                 * 已关联，勾选 --> 更新
                 * 已关联，未勾选 --> 删除
                 * 未关联，勾选 --> 新增
                 * 未关联，未勾选 --> 不做处理
                 */
                if("是".equals(clearanceDetail.getIsDeal())){
                    if(clearanceDetail.getIsSelect()){
                        //已关联，勾选 --> 更新
                        isAddOrUpdate = Boolean.TRUE;

                        //根据物料编码和运抵国取清关产品合并：清关品名、开票英文品名、报关英文材质、用途（适用品牌或对象）、带磁、带电、K3价格
                        if(StringUtils.isBlank(clearanceDetail.getMaterialCode())){
                            return ResponseData.error(clearanceDetail.getPackCode() + "物料编码为空！");
                        }
                        //查询清关产品合并
                        LambdaQueryWrapper<TgBaseProduct> mergeWrapper = new LambdaQueryWrapper<>();
                        mergeWrapper.eq(TgBaseProduct :: getDataType, TgDataTypeEnum.MERGE.getCode())
                                .like(TgBaseProduct :: getMaterialCode, clearanceDetail.getMaterialCode());
                        TgBaseProduct mergeProduct = productService.getOne(mergeWrapper);

                        //查询产品基本信息
                        LambdaQueryWrapper<TgBaseProduct> productWrapper = new LambdaQueryWrapper<>();
                        productWrapper.eq(TgBaseProduct :: getDataType, TgDataTypeEnum.NOT_MERGE.getCode())
                                .eq(TgBaseProduct :: getMaterialCode, clearanceDetail.getMaterialCode());
                        TgBaseProduct baseProduct = productService.getOne(productWrapper);

                        if(baseProduct == null){
                            return ResponseData.error("未查询到" + clearanceDetail.getMaterialCode() + "对应的产品基本信息！");
                        }

                        //如果没有合并的信息则取未合并的
                        if(mergeProduct == null){
                            mergeProduct = baseProduct;
                        }

                        clearanceDetail.setClearNameCn(mergeProduct.getInvoiceProNameCn());
                        clearanceDetail.setInvoiceProNameEn(mergeProduct.getInvoiceProNameEn());
                        clearanceDetail.setClearMaterialEn(mergeProduct.getClearMaterialEn());
                        clearanceDetail.setFitBrand(mergeProduct.getFitBrand());
                        if("0".equals(baseProduct.getIsCharged())){
                            clearanceDetail.setIsCharged("否");
                        }
                        if ("1".equals(baseProduct.getIsCharged())){
                            clearanceDetail.setIsCharged("是");
                        }
                        if("0".equals(baseProduct.getIsMagnet())){
                            clearanceDetail.setIsMagnet("否");
                        }
                        if ("1".equals(baseProduct.getIsMagnet())){
                            clearanceDetail.setIsMagnet("是");
                        }

                        //清关产品合并明细：HSCode
                        LambdaQueryWrapper<TgBaseProductDetail> mergeDetailWrapper = new LambdaQueryWrapper<>();
                        mergeDetailWrapper.eq(TgBaseProductDetail :: getPid, mergeProduct.getId())
                                .eq(TgBaseProductDetail :: getCountryCode, param.getArrivalCountryCode());
                        TgBaseProductDetail mergeDetail = productDetailService.getOne(mergeDetailWrapper);
                        if(mergeDetail == null){
                            return ResponseData.error("未查询到物料：" + clearanceDetail.getMaterialCode() + "，国家：" + param.getArrivalCountryName() + "对应的清关产品合并信息！");
                        }
                        clearanceDetail.setHscode(mergeDetail.getHsCode());
                        clearanceDetail.setDataType(clearance.getDataType());
                        clearanceDetail.setUpdateUser(name);
                        clearanceDetail.setUpdateTime(DateUtil.date());

                        //根据站点和SKU获取领星Listing销售价、销售链接、主图链接数据
                        /*LambdaQueryWrapper<TgLxListingInfo> lxListingWrapper = new LambdaQueryWrapper<>();
                        lxListingWrapper.eq(TgLxListingInfo :: getSellerSku, clearanceDetail.getSku())
                                .eq(TgLxListingInfo :: getSite, country.getSite())
                                .eq(TgLxListingInfo :: getIsDelete, "0");
                        TgLxListingInfo lxListing = lxListingInfoService.getOne(lxListingWrapper);
                        if(lxListing != null){
                            clearanceDetail.setAmazonSalePrice(lxListing.getListingPrice() == null ? lxListing.getPrice() : lxListing.getListingPrice());
                            //再次编辑导入，如果系统有销售价则取系统的
                            if(clearanceDetail.getAmazonSalePrice() != null){
                                clearanceDetail.setEditStatus("");
                            }
                            clearanceDetail.setAmazonSaleLink(country.getAmazonUrl() + "dp/" + lxListing.getAsin());
                            clearanceDetail.setAmazonPictureLink(lxListing.getSmallImageUrl());
                        }*/
                        /*LambdaQueryWrapper<TgSaihuProduct> saihuProductWrapper = new LambdaQueryWrapper<>();
                        saihuProductWrapper.eq(TgSaihuProduct :: getSku, clearanceDetail.getSku())
                                .eq(TgSaihuProduct :: getSite, country.getSite())
                                .isNull(TgSaihuProduct :: getDxmPublishState);
                        TgSaihuProduct saihuProduct = saihuProductService.getOne(saihuProductWrapper);
                        if(saihuProduct != null){
                            clearanceDetail.setAmazonSalePrice(saihuProduct.getListingPrice() == null ? saihuProduct.getStandardPrice() : saihuProduct.getListingPrice());
                            //再次编辑导入，如果系统有销售价则取系统的
                            if(clearanceDetail.getAmazonSalePrice() != null){
                                clearanceDetail.setEditStatus("");
                            }
                            clearanceDetail.setAmazonSaleLink(country.getAmazonUrl() + "dp/" + saihuProduct.getAsin());
                            clearanceDetail.setAmazonPictureLink(saihuProduct.getMainImage());
                        }*/

                        //计算单价：将人民币的K3价格换成目标币，K3价格 * 间接汇率
                        if(baseProduct.getK3Price() == null){
                            return ResponseData.error(clearanceDetail.getMaterialCode() + "对应的产品基本信息采购价为空！");
                        }
//                        clearanceDetail.setUnitPrice(baseProduct.getK3Price().multiply(fixedExchangeRate.getIndirectRate()).setScale(2, BigDecimal.ROUND_HALF_UP));

                        //计算清关单价
                        /*ResponseData rd = calculateCustomsUnitPrice(clearance, clearanceDetail, mergeDetail);
                        if(ResponseData.DEFAULT_ERROR_CODE.equals(rd.getCode())){
                            return rd;
                        }*/
                        updateDetailList.add(clearanceDetail);
                    } else {
                        //已关联，未勾选 --> 删除
                        deleteList.add(clearanceDetail.getId());
                    }
                }

                if("否".equals(clearanceDetail.getIsDeal()) && clearanceDetail.getIsSelect()){
                    //未关联，勾选 --> 新增
                    isAddOrUpdate = Boolean.TRUE;

                    //根据物料编码和运抵国取清关产品合并：清关品名、开票英文品名、报关英文材质、用途（适用品牌或对象）、带磁、带电、K3价格
                    if(StringUtils.isBlank(clearanceDetail.getMaterialCode())){
                        return ResponseData.error(clearanceDetail.getPackCode() + "物料编码为空！");
                    }

                    //查询清关产品合并
                    LambdaQueryWrapper<TgBaseProduct> mergeWrapper = new LambdaQueryWrapper<>();
                    mergeWrapper.eq(TgBaseProduct :: getDataType, TgDataTypeEnum.MERGE.getCode())
                            .like(TgBaseProduct :: getMaterialCode, clearanceDetail.getMaterialCode());
                    TgBaseProduct mergeProduct = productService.getOne(mergeWrapper);

                    //查询产品基本信息
                    LambdaQueryWrapper<TgBaseProduct> productWrapper = new LambdaQueryWrapper<>();
                    productWrapper.eq(TgBaseProduct :: getDataType, TgDataTypeEnum.NOT_MERGE.getCode())
                            .eq(TgBaseProduct :: getMaterialCode, clearanceDetail.getMaterialCode());
                    TgBaseProduct baseProduct = productService.getOne(productWrapper);

                    if(baseProduct == null){
                        return ResponseData.error("未查询到" + clearanceDetail.getMaterialCode() + "对应的产品基本信息！");
                    }

                    //如果没有合并的信息则取未合并的
                    if(mergeProduct == null){
                        mergeProduct = baseProduct;
                    }

                    clearanceDetail.setClearNameCn(mergeProduct.getInvoiceProNameCn());
                    clearanceDetail.setInvoiceProNameEn(mergeProduct.getInvoiceProNameEn());
                    clearanceDetail.setClearMaterialEn(mergeProduct.getClearMaterialEn());
                    clearanceDetail.setFitBrand(mergeProduct.getFitBrand());
                    if("0".equals(baseProduct.getIsCharged())){
                        clearanceDetail.setIsCharged("否");
                    }
                    if ("1".equals(baseProduct.getIsCharged())){
                        clearanceDetail.setIsCharged("是");
                    }
                    if("0".equals(baseProduct.getIsMagnet())){
                        clearanceDetail.setIsMagnet("否");
                    }
                    if ("1".equals(baseProduct.getIsMagnet())){
                        clearanceDetail.setIsMagnet("是");
                    }

                    //清关产品合并明细：HSCode
                    LambdaQueryWrapper<TgBaseProductDetail> mergeDetailWrapper = new LambdaQueryWrapper<>();
                    mergeDetailWrapper.eq(TgBaseProductDetail :: getPid, mergeProduct.getId())
                            .eq(TgBaseProductDetail :: getCountryCode, param.getArrivalCountryCode());
                    TgBaseProductDetail mergeDetail = productDetailService.getOne(mergeDetailWrapper);
                    if(mergeDetail == null){
                        return ResponseData.error("未查询到物料：" + clearanceDetail.getMaterialCode() + "，国家：" + param.getArrivalCountryName() + "对应的清关产品明细信息！");
                    }
                    clearanceDetail.setHscode(mergeDetail.getHsCode());
                    clearanceDetail.setDataType(clearance.getDataType());
                    clearanceDetail.setCreateUser(name);

                    //根据站点和SKU获取领星Listing销售价、销售链接、主图链接数据
                    /*LambdaQueryWrapper<TgLxListingInfo> lxListingWrapper = new LambdaQueryWrapper<>();
                    lxListingWrapper.eq(TgLxListingInfo :: getSellerSku, clearanceDetail.getSku())
                            .eq(TgLxListingInfo :: getSite, country.getSite())
                            .eq(TgLxListingInfo :: getIsDelete, "0");
                    TgLxListingInfo lxListing = lxListingInfoService.getOne(lxListingWrapper);
                    if(lxListing != null){
                        clearanceDetail.setAmazonSalePrice(lxListing.getListingPrice() == null ? lxListing.getPrice() : lxListing.getListingPrice());
                        clearanceDetail.setAmazonSaleLink(country.getAmazonUrl() + "dp/" + lxListing.getAsin());
                        clearanceDetail.setAmazonPictureLink(lxListing.getSmallImageUrl());
                    }*/
                    /*LambdaQueryWrapper<TgSaihuProduct> saihuProductWrapper = new LambdaQueryWrapper<>();
                    saihuProductWrapper.eq(TgSaihuProduct :: getSku, clearanceDetail.getSku())
                            .eq(TgSaihuProduct :: getSite, country.getSite())
                            .isNull(TgSaihuProduct :: getDxmPublishState);
                    TgSaihuProduct saihuProduct = saihuProductService.getOne(saihuProductWrapper);
                    if(saihuProduct != null){
                        clearanceDetail.setAmazonSalePrice(saihuProduct.getListingPrice() == null ? saihuProduct.getStandardPrice() : saihuProduct.getListingPrice());
                        clearanceDetail.setAmazonSaleLink(country.getAmazonUrl() + "dp/" + saihuProduct.getAsin());
                        clearanceDetail.setAmazonPictureLink(saihuProduct.getMainImage());
                    }*/

                    //计算单价：将人民币的K3价格换成目标币，K3价格 * 间接汇率
                    if(baseProduct.getK3Price() == null){
                        return ResponseData.error(clearanceDetail.getMaterialCode() + "对应的产品基本信息采购价为空！");
                    }
//                    clearanceDetail.setUnitPrice(baseProduct.getK3Price().multiply(fixedExchangeRate.getIndirectRate()).setScale(2, BigDecimal.ROUND_HALF_UP));

                    //计算清关单价
                    /*ResponseData rd = calculateCustomsUnitPrice(clearance, clearanceDetail, mergeDetail);
                    if(ResponseData.DEFAULT_ERROR_CODE.equals(rd.getCode())){
                        return rd;
                    }*/
                    saveDetailList.add(clearanceDetail);
                }
            }

            if(CollectionUtil.isNotEmpty(updateDetailList) || CollectionUtil.isNotEmpty(saveDetailList)){
                mapper.updateById(clearance);

                for (TgCustomsClearanceDetail detail : saveDetailList) {
                    if(detail.getPid() == null){
                        detail.setPid(clearance.getId());
                    }
                    clearanceDetailService.save(detail);
                }

                for (TgCustomsClearanceDetail update : updateDetailList) {
                    LambdaUpdateWrapper<TgCustomsClearanceDetail> uw = new LambdaUpdateWrapper();
                    uw.eq(TgCustomsClearanceDetail :: getId, update.getId())
                            .setSql(update.getCustomsUnitPrice() == null, "CUSTOMS_UNIT_PRICE = NULL");
                    clearanceDetailService.update(update, uw);
                }

                if(CollectionUtil.isNotEmpty(deleteList)){
                    //删除已关联未勾选数据
                    clearanceDetailService.removeByIds(deleteList);
                }
            }
        }

        if(isCurrencyDiff || isDiff || isAddOrUpdate) {
            //全量更新数据，取当前最新数据
            LambdaQueryWrapper<TgCustomsClearanceDetail> cdQueryWrapper = new LambdaQueryWrapper<>();
            cdQueryWrapper.eq(TgCustomsClearanceDetail::getPid, clearance.getId());
            List<TgCustomsClearanceDetail> cdList = clearanceDetailService.list(cdQueryWrapper);
            if (CollectionUtil.isNotEmpty(cdList)) {
                for (TgCustomsClearanceDetail clearanceDetail : cdList) {
                    //根据物料编码和运抵国取清关产品合并：清关品名、开票英文品名、报关英文材质、用途（适用品牌或对象）、带磁、带电、K3价格
                    if(StringUtils.isBlank(clearanceDetail.getMaterialCode())){
                        TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                        return ResponseData.error(clearanceDetail.getPackCode() + "物料编码为空！");
                    }
                    //查询清关产品合并
                    LambdaQueryWrapper<TgBaseProduct> mergeWrapper = new LambdaQueryWrapper<>();
                    mergeWrapper.eq(TgBaseProduct :: getDataType, TgDataTypeEnum.MERGE.getCode())
                            .like(TgBaseProduct :: getMaterialCode, clearanceDetail.getMaterialCode());
                    TgBaseProduct mergeProduct = productService.getOne(mergeWrapper);

                    //查询产品基本信息
                    LambdaQueryWrapper<TgBaseProduct> productWrapper = new LambdaQueryWrapper<>();
                    productWrapper.eq(TgBaseProduct :: getDataType, TgDataTypeEnum.NOT_MERGE.getCode())
                            .eq(TgBaseProduct :: getMaterialCode, clearanceDetail.getMaterialCode());
                    TgBaseProduct baseProduct = productService.getOne(productWrapper);

                    if(baseProduct == null){
                        return ResponseData.error("未查询到" + clearanceDetail.getMaterialCode() + "对应的产品基本信息！");
                    }

                    //如果没有合并的信息则取未合并的
                    if(mergeProduct == null){
                        mergeProduct = baseProduct;
                    }

                    clearanceDetail.setClearNameCn(mergeProduct.getInvoiceProNameCn());
                    clearanceDetail.setInvoiceProNameEn(mergeProduct.getInvoiceProNameEn());
                    clearanceDetail.setClearMaterialEn(mergeProduct.getClearMaterialEn());
                    clearanceDetail.setFitBrand(mergeProduct.getFitBrand());
                    if("0".equals(baseProduct.getIsCharged())){
                        clearanceDetail.setIsCharged("否");
                    }
                    if ("1".equals(baseProduct.getIsCharged())){
                        clearanceDetail.setIsCharged("是");
                    }
                    if("0".equals(baseProduct.getIsMagnet())){
                        clearanceDetail.setIsMagnet("否");
                    }
                    if ("1".equals(baseProduct.getIsMagnet())){
                        clearanceDetail.setIsMagnet("是");
                    }

                    //清关产品合并明细：HSCode
                    LambdaQueryWrapper<TgBaseProductDetail> mergeDetailWrapper = new LambdaQueryWrapper<>();
                    mergeDetailWrapper.eq(TgBaseProductDetail :: getPid, mergeProduct.getId())
                            .eq(TgBaseProductDetail :: getCountryCode, param.getArrivalCountryCode());
                    TgBaseProductDetail mergeDetail = productDetailService.getOne(mergeDetailWrapper);
                    if(mergeDetail == null){
                        TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                        return ResponseData.error("未查询到物料：" + clearanceDetail.getMaterialCode() + "，国家：" + param.getArrivalCountryName() + "对应的清关产品明细信息！");
                    }
                    clearanceDetail.setHscode(mergeDetail.getHsCode());
                    clearanceDetail.setDataType(clearance.getDataType());
                    clearanceDetail.setUpdateUser(name);
                    clearanceDetail.setUpdateTime(DateUtil.date());

                    //根据站点和SKU获取领星Listing销售价、销售链接、主图链接数据
                    /*LambdaQueryWrapper<TgLxListingInfo> lxListingWrapper = new LambdaQueryWrapper<>();
                    lxListingWrapper.eq(TgLxListingInfo :: getSellerSku, clearanceDetail.getSku())
                            .eq(TgLxListingInfo :: getSite, country.getSite())
                            .eq(TgLxListingInfo :: getIsDelete, "0");
                    TgLxListingInfo lxListing = lxListingInfoService.getOne(lxListingWrapper);
                    if(lxListing != null){
                        clearanceDetail.setAmazonSalePrice(lxListing.getListingPrice() == null ? lxListing.getPrice() : lxListing.getListingPrice());
                        clearanceDetail.setAmazonSaleLink(country.getAmazonUrl() + "dp/" + lxListing.getAsin());
                        clearanceDetail.setAmazonPictureLink(lxListing.getSmallImageUrl());
                    }*/
                    /*LambdaQueryWrapper<TgSaihuProduct> saihuProductWrapper = new LambdaQueryWrapper<>();
                    saihuProductWrapper.eq(TgSaihuProduct :: getSku, clearanceDetail.getSku())
                            .eq(TgSaihuProduct :: getSite, country.getSite())
                            .isNull(TgSaihuProduct :: getDxmPublishState);
                    TgSaihuProduct saihuProduct = saihuProductService.getOne(saihuProductWrapper);
                    if(saihuProduct != null){
                        clearanceDetail.setAmazonSalePrice(saihuProduct.getListingPrice() == null ? saihuProduct.getStandardPrice() : saihuProduct.getListingPrice());
                        clearanceDetail.setAmazonSaleLink(country.getAmazonUrl() + "dp/" + saihuProduct.getAsin());
                        clearanceDetail.setAmazonPictureLink(saihuProduct.getMainImage());
                    }*/

                    //计算单价：将人民币的K3价格换成目标币，K3价格 * 间接汇率
                    if(baseProduct.getK3Price() == null){
                        return ResponseData.error(clearanceDetail.getMaterialCode() + "对应的产品基本信息采购价为空！");
                    }
                    //clearanceDetail.setUnitPrice(baseProduct.getK3Price().multiply(fixedExchangeRate.getIndirectRate()).setScale(2, BigDecimal.ROUND_HALF_UP));

                    //计算清关单价
                    /*ResponseData rd = calculateCustomsUnitPrice(clearance, clearanceDetail, mergeDetail);
                    if(ResponseData.DEFAULT_ERROR_CODE.equals(rd.getCode())){
                        TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                        return rd;
                    }*/
                }

                for (TgCustomsClearanceDetail update : cdList) {
                    LambdaUpdateWrapper<TgCustomsClearanceDetail> uw = new LambdaUpdateWrapper();
                    uw.eq(TgCustomsClearanceDetail :: getId, update.getId())
                            .setSql(update.getCustomsUnitPrice() == null, "CUSTOMS_UNIT_PRICE = NULL");
                    clearanceDetailService.update(update, uw);
                }
            }
        }

        if(isTransportCostDiff){
            LambdaQueryWrapper<TgCustomsClearanceDetail> cdQueryWrapper = new LambdaQueryWrapper<>();
            cdQueryWrapper.eq(TgCustomsClearanceDetail::getPid, clearance.getId());
            List<TgCustomsClearanceDetail> cdList = clearanceDetailService.list(cdQueryWrapper);
            if (CollectionUtil.isNotEmpty(cdList)) {
                //总重量
                BigDecimal totalWeight = BigDecimal.ZERO;
                Map<String, List<TgCustomsClearanceDetail>> boxListMap = cdList.stream().collect(Collectors.groupingBy(detail -> detail.getPackCode()+detail.getBoxNo(), LinkedHashMap::new, Collectors.toList()));
                for (List<TgCustomsClearanceDetail> boxList : boxListMap.values()) {
                    totalWeight = totalWeight.add(boxList.get(0).getWeight());
                }
                //保费：总重量*运费系数*0.003
                clearance.setInsureCost(totalWeight.multiply(clearance.getTransportCost()).multiply(new BigDecimal(0.003)).setScale(2, BigDecimal.ROUND_HALF_UP));
                mapper.updateById(clearance);
            }
        }
        return ResponseData.success();
    }

    @Override
    @DataSource(name = "logistics")
    public String getNowQgdOrder() {
        Integer noLength = 4;
        String pureDate = DateUtil.format(new Date(), DatePattern.PURE_DATE_PATTERN);
        String nowQgdOrder = mapper.getNowQgdOrder(pureDate);//获取数据库最新的清关单发票号
        String newQgdOrder;
        if(StringUtils.isNotBlank(nowQgdOrder)){
            Integer newNum = Integer.parseInt(nowQgdOrder.substring(nowQgdOrder.length() - noLength)) + 1;
            newQgdOrder = pureDate + StringUtils.leftPad(newNum.toString(), noLength, "0");
        }else{
            newQgdOrder = pureDate + StringUtils.leftPad("1", noLength, "0");
        }
        return newQgdOrder;
    }

    @Override
    @DataSource(name = "logistics")
    public ResponseData importExcel(MultipartFile file, String arrivalCountryCode, String arrivalCountryName) {
        BufferedInputStream buffer = null;
        try {
            buffer = new BufferedInputStream(file.getInputStream());
            ExcelReader excelReader = EasyExcel.read(buffer).build();
            BaseExcelListener listener0 = new BaseExcelListener<TgCustomsApplyImportSheet0Param>();
            ReadSheet readSheet0 = EasyExcel.readSheet(0).head(TgCustomsApplyImportSheet0Param.class).registerReadListener(listener0).build();
            BaseExcelListener listener1 = new BaseExcelListener<TgCustomsApplyImportSheet1Param>();
            ReadSheet readSheet1 = EasyExcel.readSheet(1).head(TgCustomsApplyImportSheet1Param.class).registerReadListener(listener1).build();
            excelReader.read(readSheet0, readSheet1);
            excelReader.finish();

            List<TgCustomsApplyImportSheet0Param> dataList0 = listener0.getDataList();
            log.info("清关单导入sheet0数据[{}]", JSONObject.toJSON(dataList0));
            if(CollectionUtil.isEmpty(dataList0)){
                return ResponseData.error("清关单sheet0导入数据为空，导入失败！");
            }
            for (TgCustomsApplyImportSheet0Param sheet0Param: dataList0) {
                if(StringUtils.isAnyBlank(
                        sheet0Param.getPackCode(),
                        sheet0Param.getMaterialCode(),
                        sheet0Param.getBoxNo(),
                        sheet0Param.getCurrency())
                        || sheet0Param.getQuantity() == null){
                    return ResponseData.error("出货清单号、物料编码、箱号、数量、币制为必填项！");
                }
            }

            List<TgCustomsApplyImportSheet1Param> dataList1 = listener1.getDataList();
            log.info("清关单导入sheet1数据[{}]", JSONObject.toJSON(dataList1));
            if(CollectionUtil.isEmpty(dataList1)){
                return ResponseData.error("清关单sheet1导入数据为空，导入失败！");
            }
            for (TgCustomsApplyImportSheet1Param sheet1Param: dataList1) {
                if(StringUtils.isAnyBlank(
                        sheet1Param.getBoxNo(),
                        sheet1Param.getBoxType(),
                        sheet1Param.getPackCode())
                        || sheet1Param.getWeight() == null
                        || sheet1Param.getGoodsLong() == null
                        || sheet1Param.getWide() == null
                        || sheet1Param.getHigh() == null){
                    return ResponseData.error("出货清单号、箱号、箱型、重量、长、宽、高为必填项！");
                }
            }

            List<TgCustomsClearanceDetail> clearancePackDetailList = new ArrayList<>();
            List<TgCustomsApplyImportSheet0Param> sheet0ParamErrorList = new ArrayList<>();
            ResponseData rd = this.mergeSheetData(dataList0, dataList1, arrivalCountryCode, arrivalCountryName, clearancePackDetailList, sheet0ParamErrorList);
            if(ResponseData.DEFAULT_ERROR_CODE.equals(rd.getCode())){
                return rd;
            }

            if(CollectionUtil.isNotEmpty(sheet0ParamErrorList)){
                String fileName = dealImportErrorList(sheet0ParamErrorList);
                return ResponseData.success(ResponseData.DEFAULT_SUCCESS_CODE, fileName, clearancePackDetailList);
            }
            return ResponseData.success(clearancePackDetailList);
        } catch (Exception e) {
            log.error("清关单导入Excel处理异常，导入失败！", e);
            return ResponseData.error("清关单导入Excel处理异常，导入失败！");
        } finally {
            if(buffer != null){
                try {
                    buffer.close();
                } catch (IOException e) {
                    log.error("清关单导入Excel关闭流异常", e);
                }
            }
        }
    }

    /**
     * 合并sheet数据
     * @param dataList0 sheet0数据
     * @param dataList1 sheet1数据
     * @param arrivalCountryCode 国家编码
     * @param arrivalCountryName 国家名称
     * @param sheet0ParamErrorList 异常数据
     * @param sheet0ParamErrorList 异常数据
     * @return
     */
    private ResponseData mergeSheetData(List<TgCustomsApplyImportSheet0Param> dataList0, List<TgCustomsApplyImportSheet1Param> dataList1,
                                        String arrivalCountryCode, String arrivalCountryName,
                                        List<TgCustomsClearanceDetail> clearancePackDetailList, List<TgCustomsApplyImportSheet0Param> sheet0ParamErrorList){
        //箱号维度分组
        Map<String, List<TgCustomsApplyImportSheet1Param>> sheet1ListMap = dataList1.stream().collect(Collectors.groupingBy(detail -> detail.getPackCode()+detail.getBoxNo(), LinkedHashMap::new, Collectors.toList()));
        for (String key : sheet1ListMap.keySet()) {
            if(sheet1ListMap.get(key).size() > 1){
                return ResponseData.error("重量信息存在重复出货清单号、箱号，导入失败！");
            }
        }

        Map<String, List<TgCustomsApplyImportSheet0Param>> sheet0ListMap = dataList0.stream().collect(Collectors.groupingBy(sheet0Param -> sheet0Param.getMaterialCode(), LinkedHashMap::new, Collectors.toList()));
        List<String> matCodeList = sheet0ListMap.keySet().stream().collect(Collectors.toList());
        //根据物料编码查询通关产品信息的规格型号、开票规格型号、公司品牌、开票品名
        LambdaQueryWrapper<TgBaseProduct> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.in(TgBaseProduct :: getMaterialCode, matCodeList);
        List<TgBaseProduct> productList = productService.list(queryWrapper);
        if(CollectionUtil.isEmpty(productList)){
            return ResponseData.error("未查询到产品基本信息数据，物料编码：" + JSONObject.toJSON(matCodeList));
        }
        Map<String, List<TgBaseProduct>> productListMap = productList.stream().collect(Collectors.groupingBy(product -> product.getMaterialCode(), LinkedHashMap::new, Collectors.toList()));

        for (TgCustomsApplyImportSheet0Param sheet0 : dataList0) {
            List<TgBaseProduct> mBaseProductList = productListMap.get(sheet0.getMaterialCode());
            if(CollectionUtil.isEmpty(mBaseProductList)){
                return ResponseData.error("未查询到产品基本信息数据，物料编码：" + sheet0.getMaterialCode());
            }

            //查询清关产品合并
            LambdaQueryWrapper<TgBaseProduct> mergeWrapper = new LambdaQueryWrapper<>();
            mergeWrapper.eq(TgBaseProduct :: getDataType, TgDataTypeEnum.MERGE.getCode())
                    .like(TgBaseProduct :: getMaterialCode, sheet0.getMaterialCode());
            TgBaseProduct mergeProduct = productService.getOne(mergeWrapper);

            //查询产品基本信息
            LambdaQueryWrapper<TgBaseProduct> productWrapper = new LambdaQueryWrapper<>();
            productWrapper.eq(TgBaseProduct :: getDataType, TgDataTypeEnum.NOT_MERGE.getCode())
                    .eq(TgBaseProduct :: getMaterialCode, sheet0.getMaterialCode());
            TgBaseProduct baseProduct = productService.getOne(productWrapper);

            if(baseProduct == null){
                sheet0.setUploadRemark("未查询到" + sheet0.getMaterialCode() + "对应的产品基本信息！");
                sheet0ParamErrorList.add(sheet0);
            }

            //如果没有合并的信息则取未合并的
            if(mergeProduct == null){
                mergeProduct = baseProduct;
            }

            //如果传参有运抵国则校验
            // 清关产品合并明细：HSCode
            if(StringUtils.isNotBlank(arrivalCountryCode) && StringUtils.isNotBlank(arrivalCountryName) && mergeProduct != null){
                LambdaQueryWrapper<TgBaseProductDetail> mergeDetailWrapper = new LambdaQueryWrapper<>();
                mergeDetailWrapper.eq(TgBaseProductDetail :: getPid, mergeProduct.getId())
                        .eq(TgBaseProductDetail :: getCountryCode, arrivalCountryCode);
                TgBaseProductDetail mergeDetail = productDetailService.getOne(mergeDetailWrapper);
                if(mergeDetail == null){
                    sheet0.setUploadRemark("未查询到物料：" + sheet0.getMaterialCode() + "，国家：" + arrivalCountryName + "对应的清关产品合并信息！");
                    sheet0ParamErrorList.add(sheet0);
                }
            }

            TgCustomsClearanceDetail detail = new TgCustomsClearanceDetail();
            BeanUtils.copyProperties(sheet0, detail);
            detail.setType(mBaseProductList.get(0).getFitBrand());
            detail.setStyle(mBaseProductList.get(0).getStyle());
            detail.setCompanyBrand(mBaseProductList.get(0).getCompanyBrand());
            detail.setInvoiceProNameCn(mBaseProductList.get(0).getInvoiceProNameCn());
            List<TgCustomsApplyImportSheet1Param> detailList = sheet1ListMap.get(detail.getPackCode()+detail.getBoxNo());
            if(CollectionUtil.isEmpty(detailList)){
                sheet0.setUploadRemark("出货清单号：" + detail.getPackCode() + "，箱号：" + detail.getBoxNo() + "，导入箱子的重量信息缺失！");
                sheet0ParamErrorList.add(sheet0);
            } else {
                detail.setBoxNoName(detailList.get(0).getBoxNoName());
                detail.setBoxType(detailList.get(0).getBoxType());
                detail.setWeight(detailList.get(0).getWeight());
                detail.setGoodsLong(detailList.get(0).getGoodsLong());
                detail.setWide(detailList.get(0).getWide());
                detail.setHigh(detailList.get(0).getHigh());
            }
            clearancePackDetailList.add(detail);
        }
        return ResponseData.success();
    }

    /**
     * 校验错误文件流输出
     * @param errorList
     * @return
     */
    private String dealImportErrorList(List<TgCustomsApplyImportSheet0Param> errorList){
        String filePath = System.getProperty("user.dir") + "/upload/";
        String fileName =  DateUtil.format(new Date(), DatePattern.PURE_DATETIME_MS_FORMAT) + ".xlsx";
        OutputStream out = null;
        try {
            out = new FileOutputStream(filePath + fileName,false);
            EasyExcel.write(out, TgCustomsApplyImportSheet0Param.class)
                    .sheet("导入异常数据").doWrite(errorList);
        } catch (FileNotFoundException e) {
            log.error("清关发票导入异常数据导出异常", e);
        } finally {
            if(out != null){
                try {
                    out.close();
                } catch (IOException e) {
                    log.error("清关发票导入异常数据导出流关闭异常", e);
                }
            }
        }
        return fileName;
    }

    @Override
    @DataSource(name = "logistics")
    @Transactional(rollbackFor = Exception.class)
    public ResponseData importSave(TgCustomsClearanceSaveParam param) {
        log.info("清关单导入新增保存入参[{}]", JSONObject.toJSON(param));
        String name = LoginContext.me().getLoginUser().getName();
        param.setPostDate(DateUtil.date());

        //查询ERP固定汇率表
        /*FixedExchangeRateParam rateParam  = new FixedExchangeRateParam();
        rateParam.setOriginalCurrency(param.getCurrency());
        rateParam.setEffectDate(DateUtil.beginOfDay(param.getPostDate()));
        FixedExchangeRate fixedExchangeRate = fixedExchangeRateConsumer.getRateByDateCurrency(rateParam);
        if(fixedExchangeRate == null){
            return ResponseData.error("ERP固定汇率不存在！币别：" + param.getCurrency() + "，发货日期：" + param.getPostDate());
        }*/

        TgCustomsClearance clearance = new TgCustomsClearance();
        BeanUtils.copyProperties(param, clearance);
        clearance.setCountryOfExport("CHINA");
        clearance.setDeliveryTerm("CIF");
        clearance.setPaymentMethord("T/T");
        clearance.setDataType(TgDataTypeEnum.NOT_MERGE.getCode());
        clearance.setCreateUser(name);

        //根据国家获取对应站点，用于通过站点和SKU查询对应的销售价格，Asin和主图链接
        LambdaQueryWrapper<TgCountryInfo> countryWrapper = new LambdaQueryWrapper<>();
        countryWrapper.eq(TgCountryInfo :: getCountryCode, clearance.getArrivalCountryCode());
        TgCountryInfo country = countryInfoService.getOne(countryWrapper);
        if(country == null){
            return ResponseData.error("未查询到运抵国：" + clearance.getArrivalCountryName() + "对应的国家信息！");
        }
        if(StringUtils.isBlank(country.getSite())){
            return ResponseData.error("未查询到运抵国：" + clearance.getArrivalCountryName() + "对应的站点信息！");
        }

        if(CollectionUtil.isEmpty(param.getClearanceDetails())){
            return ResponseData.error("清关单明细数据为空！");
        }
        List<TgCustomsClearanceDetail> clearanceDetailList = new ArrayList<>();
        for (TgCustomsClearanceDetail clearanceDetail : param.getClearanceDetails()) {
            //根据物料编码和运抵国取清关产品合并：清关品名、开票英文品名、报关英文材质、用途（适用品牌或对象）、带磁、带电、K3价格
            if(StringUtils.isBlank(clearanceDetail.getMaterialCode())){
                return ResponseData.error(clearanceDetail.getPackCode() + "物料编码为空！");
            }
            //查询清关产品合并
            LambdaQueryWrapper<TgBaseProduct> mergeWrapper = new LambdaQueryWrapper<>();
            mergeWrapper.eq(TgBaseProduct :: getDataType, TgDataTypeEnum.MERGE.getCode())
                    .like(TgBaseProduct :: getMaterialCode, clearanceDetail.getMaterialCode());
            TgBaseProduct mergeProduct = productService.getOne(mergeWrapper);

            //查询产品基本信息
            LambdaQueryWrapper<TgBaseProduct> productWrapper = new LambdaQueryWrapper<>();
            productWrapper.eq(TgBaseProduct :: getDataType, TgDataTypeEnum.NOT_MERGE.getCode())
                    .eq(TgBaseProduct :: getMaterialCode, clearanceDetail.getMaterialCode());
            TgBaseProduct baseProduct = productService.getOne(productWrapper);

            if(baseProduct == null){
                return ResponseData.error("未查询到" + clearanceDetail.getMaterialCode() + "对应的产品基本信息！");
            }

            //如果没有合并的信息则取未合并的
            if(mergeProduct == null){
                mergeProduct = baseProduct;
            }

            clearanceDetail.setClearNameCn(mergeProduct.getInvoiceProNameCn());
            clearanceDetail.setInvoiceProNameEn(mergeProduct.getInvoiceProNameEn());
            clearanceDetail.setClearMaterialEn(mergeProduct.getClearMaterialEn());
            clearanceDetail.setFitBrand(mergeProduct.getFitBrand());
            if("0".equals(baseProduct.getIsCharged())){
                clearanceDetail.setIsCharged("否");
            }
            if ("1".equals(baseProduct.getIsCharged())){
                clearanceDetail.setIsCharged("是");
            }
            if("0".equals(baseProduct.getIsMagnet())){
                clearanceDetail.setIsMagnet("否");
            }
            if ("1".equals(baseProduct.getIsMagnet())){
                clearanceDetail.setIsMagnet("是");
            }

            //清关产品合并明细：HSCode
            LambdaQueryWrapper<TgBaseProductDetail> mergeDetailWrapper = new LambdaQueryWrapper<>();
            mergeDetailWrapper.eq(TgBaseProductDetail :: getPid, mergeProduct.getId())
                    .eq(TgBaseProductDetail :: getCountryCode, param.getArrivalCountryCode());
            TgBaseProductDetail mergeDetail = productDetailService.getOne(mergeDetailWrapper);
            if(mergeDetail == null){
                return ResponseData.error("未查询到物料：" + clearanceDetail.getMaterialCode() + "，国家：" + param.getArrivalCountryName() + "对应的清关产品合并信息！");
            }
            clearanceDetail.setHscode(mergeDetail.getHsCode());
            clearanceDetail.setDataType(clearance.getDataType());
            clearanceDetail.setCreateUser(name);

            //根据站点和SKU获取领星Listing销售价、销售链接、主图链接数据
            /*LambdaQueryWrapper<TgLxListingInfo> lxListingWrapper = new LambdaQueryWrapper<>();
            lxListingWrapper.eq(TgLxListingInfo :: getSellerSku, clearanceDetail.getSku())
                    .eq(TgLxListingInfo :: getSite, country.getSite())
                    .eq(TgLxListingInfo :: getIsDelete, "0");
            TgLxListingInfo lxListing = lxListingInfoService.getOne(lxListingWrapper);
            if(lxListing != null){
                clearanceDetail.setAmazonSalePrice(lxListing.getListingPrice() == null ? lxListing.getPrice() : lxListing.getListingPrice());
                clearanceDetail.setAmazonSaleLink(country.getAmazonUrl() + "dp/" + lxListing.getAsin());
                clearanceDetail.setAmazonPictureLink(lxListing.getSmallImageUrl());
            }*/
            /*LambdaQueryWrapper<TgSaihuProduct> saihuProductWrapper = new LambdaQueryWrapper<>();
            saihuProductWrapper.eq(TgSaihuProduct :: getSku, clearanceDetail.getSku())
                    .eq(TgSaihuProduct :: getSite, country.getSite())
                    .isNull(TgSaihuProduct :: getDxmPublishState);
            TgSaihuProduct saihuProduct = saihuProductService.getOne(saihuProductWrapper);
            if(saihuProduct != null){
                clearanceDetail.setAmazonSalePrice(saihuProduct.getListingPrice() == null ? saihuProduct.getStandardPrice() : saihuProduct.getListingPrice());
                clearanceDetail.setAmazonSaleLink(country.getAmazonUrl() + "dp/" + saihuProduct.getAsin());
                clearanceDetail.setAmazonPictureLink(saihuProduct.getMainImage());
            }*/

            //计算单价：将人民币的K3价格换成目标币，K3价格 * 间接汇率
            if(baseProduct.getK3Price() == null){
                return ResponseData.error(clearanceDetail.getMaterialCode() + "对应的产品基本信息采购价为空！");
            }
            //clearanceDetail.setUnitPrice(baseProduct.getK3Price().multiply(fixedExchangeRate.getIndirectRate()).setScale(2, BigDecimal.ROUND_HALF_UP));

            //计算清关单价
            /*ResponseData rd = calculateCustomsUnitPrice(clearance, clearanceDetail, mergeDetail);
            if(ResponseData.DEFAULT_ERROR_CODE.equals(rd.getCode())){
                return rd;
            }*/
            clearanceDetailList.add(clearanceDetail);
        }
        if(CollectionUtil.isNotEmpty(clearanceDetailList)){
            //生成清关单发票号
            clearance.setInvoiceNo(this.getNowQgdOrder());
            //总重量
            BigDecimal totalWeight = BigDecimal.ZERO;
            Map<String, List<TgCustomsClearanceDetail>> boxListMap = param.getClearanceDetails().stream().collect(Collectors.groupingBy(detail -> detail.getPackCode()+detail.getBoxNo(), LinkedHashMap::new, Collectors.toList()));
            for (List<TgCustomsClearanceDetail> boxList : boxListMap.values()) {
                totalWeight = totalWeight.add(boxList.get(0).getWeight());
            }
            //保费：总重量*运费系数*0.003
            clearance.setInsureCost(totalWeight.multiply(clearance.getTransportCost()).multiply(new BigDecimal(0.003)).setScale(2, BigDecimal.ROUND_HALF_UP));
            mapper.insert(clearance);

            for (TgCustomsClearanceDetail clearanceDetail : clearanceDetailList) {
                clearanceDetail.setPid(clearance.getId());
                clearanceDetailService.save(clearanceDetail);
            }
        }
        return ResponseData.success();
    }

    @Override
    @DataSource(name = "logistics")
    @Transactional(rollbackFor = Exception.class)
    public ResponseData importEdit(TgCustomsClearanceSaveParam param) {
        log.info("清关单导入编辑保存入参[{}]", JSONObject.toJSON(param));
        if(StringUtils.isBlank(String.valueOf(param.getId()))){
            return ResponseData.error("编辑ID为空！");
        }
        TgCustomsClearance oldClearance = mapper.selectById(param.getId());
        if(oldClearance == null){
            return ResponseData.error("未查询到此清关发票！");
        }

        String name = LoginContext.me().getLoginUser().getName();
        param.setPostDate(DateUtil.date());

        //查询ERP固定汇率表
        /*FixedExchangeRateParam rateParam  = new FixedExchangeRateParam();
        rateParam.setOriginalCurrency(param.getCurrency());
        rateParam.setEffectDate(DateUtil.beginOfDay(oldClearance.getPostDate()));
        FixedExchangeRate fixedExchangeRate = fixedExchangeRateConsumer.getRateByDateCurrency(rateParam);
        if(fixedExchangeRate == null){
            return ResponseData.error("ERP固定汇率不存在！币别：" + param.getCurrency() + "，发货日期：" + param.getPostDate());
        }*/

        TgCustomsClearance clearance = new TgCustomsClearance();
        BeanUtils.copyProperties(param, clearance);
        clearance.setDataType(TgDataTypeEnum.NOT_MERGE.getCode());
        clearance.setUpdateUser(name);
        clearance.setUpdateTime(DateUtil.date());

        //根据国家获取对应站点，用于通过站点和SKU查询对应的销售价格，Asin和主图链接
        LambdaQueryWrapper<TgCountryInfo> countryWrapper = new LambdaQueryWrapper<>();
        countryWrapper.eq(TgCountryInfo :: getCountryCode, clearance.getArrivalCountryCode());
        TgCountryInfo country = countryInfoService.getOne(countryWrapper);
        if(country == null){
            return ResponseData.error("未查询到运抵国：" + clearance.getArrivalCountryName() + "对应的国家信息！");
        }
        if(StringUtils.isBlank(country.getSite())){
            return ResponseData.error("未查询到运抵国：" + clearance.getArrivalCountryName() + "对应的站点信息！");
        }

        //运费系数是否有变更，变更则需要重新计算保费
        Boolean isTransportCostDiff = Boolean.FALSE;
        if(oldClearance.getTransportCost().compareTo(clearance.getTransportCost()) != 0){
            isTransportCostDiff = Boolean.TRUE;
        }

        //币别是否有变更，变更则需要重新计算
        Boolean isCurrencyDiff = Boolean.FALSE;
        //价格类型和清关折算率是否有变更，变更则需要重新计算
        Boolean isDiff = Boolean.FALSE;
        if(!oldClearance.getCurrency().equals(clearance.getCurrency())){
            isCurrencyDiff = Boolean.TRUE;
        }
        if(StringUtils.isBlank(oldClearance.getPriceType()) || oldClearance.getCustomsCoeff() == null){
            if(StringUtils.isNotBlank(clearance.getPriceType()) && clearance.getCustomsCoeff() != null){
                isDiff = Boolean.TRUE;
            }
        }
        if(StringUtils.isNotBlank(oldClearance.getPriceType()) && oldClearance.getCustomsCoeff() != null){
            if(StringUtils.isBlank(clearance.getPriceType()) || clearance.getCustomsCoeff() == null){
                isDiff = Boolean.TRUE;
            }
            if(StringUtils.isNotBlank(clearance.getPriceType()) && clearance.getCustomsCoeff() != null
                    && (!oldClearance.getPriceType().equals(clearance.getPriceType()) || oldClearance.getCustomsCoeff() != clearance.getCustomsCoeff())){
                isDiff = Boolean.TRUE;
            }
        }

        if(CollectionUtil.isEmpty(param.getClearanceDetails())){
            return ResponseData.error("清关单明细数据为空！");
        }

        //报关单编辑导入明细保存是否为新导入数据 0：否，1：是，新数据则删除旧的数据，再保存新的数据
        if("1".equals(param.getImportNew())){
            List<TgCustomsClearanceDetail> clearanceDetailList = new ArrayList<>();
            for (TgCustomsClearanceDetail clearanceDetail : param.getClearanceDetails()) {
                if("否".equals(clearanceDetail.getIsDeal()) && clearanceDetail.getIsSelect()){
                    //根据物料编码和运抵国取清关产品合并：清关品名、开票英文品名、报关英文材质、用途（适用品牌或对象）、带磁、带电、K3价格
                    if(StringUtils.isBlank(clearanceDetail.getMaterialCode())){
                        return ResponseData.error(clearanceDetail.getPackCode() + "物料编码为空！");
                    }
                    //查询清关产品合并
                    LambdaQueryWrapper<TgBaseProduct> mergeWrapper = new LambdaQueryWrapper<>();
                    mergeWrapper.eq(TgBaseProduct :: getDataType, TgDataTypeEnum.MERGE.getCode())
                            .like(TgBaseProduct :: getMaterialCode, clearanceDetail.getMaterialCode());
                    TgBaseProduct mergeProduct = productService.getOne(mergeWrapper);

                    //查询产品基本信息
                    LambdaQueryWrapper<TgBaseProduct> productWrapper = new LambdaQueryWrapper<>();
                    productWrapper.eq(TgBaseProduct :: getDataType, TgDataTypeEnum.NOT_MERGE.getCode())
                            .eq(TgBaseProduct :: getMaterialCode, clearanceDetail.getMaterialCode());
                    TgBaseProduct baseProduct = productService.getOne(productWrapper);

                    if(baseProduct == null){
                        return ResponseData.error("未查询到" + clearanceDetail.getMaterialCode() + "对应的产品基本信息！");
                    }

                    //如果没有合并的信息则取未合并的
                    if(mergeProduct == null){
                        mergeProduct = baseProduct;
                    }

                    clearanceDetail.setClearNameCn(mergeProduct.getInvoiceProNameCn());
                    clearanceDetail.setInvoiceProNameEn(mergeProduct.getInvoiceProNameEn());
                    clearanceDetail.setClearMaterialEn(mergeProduct.getClearMaterialEn());
                    clearanceDetail.setFitBrand(mergeProduct.getFitBrand());
                    if("0".equals(baseProduct.getIsCharged())){
                        clearanceDetail.setIsCharged("否");
                    }
                    if ("1".equals(baseProduct.getIsCharged())){
                        clearanceDetail.setIsCharged("是");
                    }
                    if("0".equals(baseProduct.getIsMagnet())){
                        clearanceDetail.setIsMagnet("否");
                    }
                    if ("1".equals(baseProduct.getIsMagnet())){
                        clearanceDetail.setIsMagnet("是");
                    }

                    //清关产品合并明细：HSCode
                    LambdaQueryWrapper<TgBaseProductDetail> mergeDetailWrapper = new LambdaQueryWrapper<>();
                    mergeDetailWrapper.eq(TgBaseProductDetail :: getPid, mergeProduct.getId())
                            .eq(TgBaseProductDetail :: getCountryCode, param.getArrivalCountryCode());
                    TgBaseProductDetail mergeDetail = productDetailService.getOne(mergeDetailWrapper);
                    if(mergeDetail == null){
                        return ResponseData.error("未查询到物料：" + clearanceDetail.getMaterialCode() + "，国家：" + param.getArrivalCountryName() + "对应的清关产品明细信息！");
                    }
                    clearanceDetail.setHscode(mergeDetail.getHsCode());
                    clearanceDetail.setDataType(clearance.getDataType());
                    clearanceDetail.setCreateUser(name);

                    //根据站点和SKU获取领星Listing销售价、销售链接、主图链接数据
                    /*LambdaQueryWrapper<TgLxListingInfo> lxListingWrapper = new LambdaQueryWrapper<>();
                    lxListingWrapper.eq(TgLxListingInfo :: getSellerSku, clearanceDetail.getSku())
                            .eq(TgLxListingInfo :: getSite, country.getSite())
                            .eq(TgLxListingInfo :: getIsDelete, "0");
                    TgLxListingInfo lxListing = lxListingInfoService.getOne(lxListingWrapper);
                    if(lxListing != null){
                        clearanceDetail.setAmazonSalePrice(lxListing.getListingPrice() == null ? lxListing.getPrice() : lxListing.getListingPrice());
                        //再次编辑导入，如果系统有销售价则取系统的
                        if(clearanceDetail.getAmazonSalePrice() != null){
                            clearanceDetail.setEditStatus("");
                        }
                        clearanceDetail.setAmazonSaleLink(country.getAmazonUrl() + "dp/" + lxListing.getAsin());
                        clearanceDetail.setAmazonPictureLink(lxListing.getSmallImageUrl());
                    }*/
                    /*LambdaQueryWrapper<TgSaihuProduct> saihuProductWrapper = new LambdaQueryWrapper<>();
                    saihuProductWrapper.eq(TgSaihuProduct :: getSku, clearanceDetail.getSku())
                            .eq(TgSaihuProduct :: getSite, country.getSite())
                            .isNull(TgSaihuProduct :: getDxmPublishState);
                    TgSaihuProduct saihuProduct = saihuProductService.getOne(saihuProductWrapper);
                    if(saihuProduct != null){
                        clearanceDetail.setAmazonSalePrice(saihuProduct.getListingPrice() == null ? saihuProduct.getStandardPrice() : saihuProduct.getListingPrice());
                        //再次编辑导入，如果系统有销售价则取系统的
                        if(clearanceDetail.getAmazonSalePrice() != null){
                            clearanceDetail.setEditStatus("");
                        }
                        clearanceDetail.setAmazonSaleLink(country.getAmazonUrl() + "dp/" + saihuProduct.getAsin());
                        clearanceDetail.setAmazonPictureLink(saihuProduct.getMainImage());
                    }*/

                    //计算单价：将人民币的K3价格换成目标币，K3价格 * 间接汇率
                    if(baseProduct.getK3Price() == null){
                        return ResponseData.error(clearanceDetail.getMaterialCode() + "对应的产品基本信息采购价为空！");
                    }
                    //clearanceDetail.setUnitPrice(baseProduct.getK3Price().multiply(fixedExchangeRate.getIndirectRate()).setScale(2, BigDecimal.ROUND_HALF_UP));

                    //计算清关单价
                    /*ResponseData rd = calculateCustomsUnitPrice(clearance, clearanceDetail, mergeDetail);
                    if(ResponseData.DEFAULT_ERROR_CODE.equals(rd.getCode())){
                        return rd;
                    }*/
                    clearanceDetailList.add(clearanceDetail);
                }
            }
            if(CollectionUtil.isNotEmpty(clearanceDetailList)){
                //总重量
                BigDecimal totalWeight = BigDecimal.ZERO;
                Map<String, List<TgCustomsClearanceDetail>> boxListMap = param.getClearanceDetails().stream().collect(Collectors.groupingBy(detail -> detail.getPackCode()+detail.getBoxNo(), LinkedHashMap::new, Collectors.toList()));
                for (List<TgCustomsClearanceDetail> boxList : boxListMap.values()) {
                    totalWeight = totalWeight.add(boxList.get(0).getWeight());
                }
                //保费：总重量*运费系数*0.003
                clearance.setInsureCost(totalWeight.multiply(clearance.getTransportCost()).multiply(new BigDecimal(0.003)).setScale(2, BigDecimal.ROUND_HALF_UP));
                mapper.updateById(clearance);

                LambdaQueryWrapper<TgCustomsClearanceDetail> detailWrapper = new LambdaQueryWrapper<>();
                detailWrapper.eq(TgCustomsClearanceDetail :: getDataType, TgDataTypeEnum.NOT_MERGE.getCode())
                        .eq(TgCustomsClearanceDetail :: getPid, clearance.getId());
                clearanceDetailService.remove(detailWrapper);

                for (TgCustomsClearanceDetail clearanceDetail : clearanceDetailList) {
                    clearanceDetail.setPid(clearance.getId());
                    clearanceDetailService.save(clearanceDetail);
                }
            }
        } else if ("0".equals(param.getImportNew())){
            //更新币制，则需要重新计算所有数据单价、清关单价
            List<TgCustomsClearanceDetail> clearanceDetailList = new ArrayList<>();
            if(isCurrencyDiff || isDiff){
                //全量更新数据，取当前最新数据
                LambdaQueryWrapper<TgCustomsClearanceDetail> cdQueryWrapper = new LambdaQueryWrapper<>();
                cdQueryWrapper.eq(TgCustomsClearanceDetail :: getPid, clearance.getId());
                List<TgCustomsClearanceDetail> cdList = clearanceDetailService.list(cdQueryWrapper);
                if(CollectionUtil.isNotEmpty(cdList)){
                    for (TgCustomsClearanceDetail clearanceDetail : cdList) {
                        clearanceDetail.setUpdateUser(name);
                        clearanceDetail.setUpdateTime(DateUtil.date());
                        //根据物料编码和运抵国取清关产品合并：清关品名、开票英文品名、报关英文材质、用途（适用品牌或对象）、带磁、带电、K3价格
                        if(StringUtils.isBlank(clearanceDetail.getMaterialCode())){
                            return ResponseData.error(clearanceDetail.getPackCode() + "物料编码为空！");
                        }
                        //查询清关产品合并
                        LambdaQueryWrapper<TgBaseProduct> mergeWrapper = new LambdaQueryWrapper<>();
                        mergeWrapper.eq(TgBaseProduct :: getDataType, TgDataTypeEnum.MERGE.getCode())
                                .like(TgBaseProduct :: getMaterialCode, clearanceDetail.getMaterialCode());
                        TgBaseProduct mergeProduct = productService.getOne(mergeWrapper);

                        //查询产品基本信息
                        LambdaQueryWrapper<TgBaseProduct> productWrapper = new LambdaQueryWrapper<>();
                        productWrapper.eq(TgBaseProduct :: getDataType, TgDataTypeEnum.NOT_MERGE.getCode())
                                .eq(TgBaseProduct :: getMaterialCode, clearanceDetail.getMaterialCode());
                        TgBaseProduct baseProduct = productService.getOne(productWrapper);

                        if(baseProduct == null){
                            return ResponseData.error("未查询到" + clearanceDetail.getMaterialCode() + "对应的产品基本信息！");
                        }

                        //如果没有合并的信息则取未合并的
                        if(mergeProduct == null){
                            mergeProduct = baseProduct;
                        }

                        //清关产品合并明细：HSCode
                        LambdaQueryWrapper<TgBaseProductDetail> mergeDetailWrapper = new LambdaQueryWrapper<>();
                        mergeDetailWrapper.eq(TgBaseProductDetail :: getPid, mergeProduct.getId())
                                .eq(TgBaseProductDetail :: getCountryCode, param.getArrivalCountryCode());
                        TgBaseProductDetail mergeDetail = productDetailService.getOne(mergeDetailWrapper);
                        if(mergeDetail == null){
                            return ResponseData.error("未查询到物料：" + clearanceDetail.getMaterialCode() + "，国家：" + param.getArrivalCountryName() + "对应的清关产品明细信息！");
                        }

                        //根据站点和SKU获取领星Listing销售价、销售链接、主图链接数据
                        /*LambdaQueryWrapper<TgLxListingInfo> lxListingWrapper = new LambdaQueryWrapper<>();
                        lxListingWrapper.eq(TgLxListingInfo :: getSellerSku, clearanceDetail.getSku())
                                .eq(TgLxListingInfo :: getSite, country.getSite())
                                .eq(TgLxListingInfo :: getIsDelete, "0");
                        TgLxListingInfo lxListing = lxListingInfoService.getOne(lxListingWrapper);
                        if(lxListing != null){
                            clearanceDetail.setAmazonSalePrice(lxListing.getListingPrice() == null ? lxListing.getPrice() : lxListing.getListingPrice());
                            //再次编辑导入，如果系统有销售价则取系统的
                            if(clearanceDetail.getAmazonSalePrice() != null){
                                clearanceDetail.setEditStatus("");
                            }
                            clearanceDetail.setAmazonSaleLink(country.getAmazonUrl() + "dp/" + lxListing.getAsin());
                            clearanceDetail.setAmazonPictureLink(lxListing.getSmallImageUrl());
                        }*/
                        /*LambdaQueryWrapper<TgSaihuProduct> saihuProductWrapper = new LambdaQueryWrapper<>();
                        saihuProductWrapper.eq(TgSaihuProduct :: getSku, clearanceDetail.getSku())
                                .eq(TgSaihuProduct :: getSite, country.getSite())
                                .isNull(TgSaihuProduct :: getDxmPublishState);
                        TgSaihuProduct saihuProduct = saihuProductService.getOne(saihuProductWrapper);
                        if(saihuProduct != null){
                            clearanceDetail.setAmazonSalePrice(saihuProduct.getListingPrice() == null ? saihuProduct.getStandardPrice() : saihuProduct.getListingPrice());
                            //再次编辑导入，如果系统有销售价则取系统的
                            if(clearanceDetail.getAmazonSalePrice() != null){
                                clearanceDetail.setEditStatus("");
                            }
                            clearanceDetail.setAmazonSaleLink(country.getAmazonUrl() + "dp/" + saihuProduct.getAsin());
                            clearanceDetail.setAmazonPictureLink(saihuProduct.getMainImage());
                        }*/

                        //计算单价：将人民币的K3价格换成目标币，K3价格 * 间接汇率
                        if(baseProduct.getK3Price() == null){
                            return ResponseData.error(clearanceDetail.getMaterialCode() + "对应的产品基本信息采购价为空！");
                        }
                        //clearanceDetail.setUnitPrice(baseProduct.getK3Price().multiply(fixedExchangeRate.getIndirectRate()).setScale(2, BigDecimal.ROUND_HALF_UP));

                        //更换币制则需要重新计算单价
                        /*if(isCurrencyDiff){
                            //计算单价：将人民币的K3价格换成目标币，K3价格 * 间接汇率
                            clearanceDetail.setUnitPrice(baseProduct.getK3Price().multiply(fixedExchangeRate.getIndirectRate()).setScale(2, BigDecimal.ROUND_HALF_UP));
                        }*/

                        //计算清关单价
                        /*ResponseData rd = calculateCustomsUnitPrice(clearance, clearanceDetail, mergeDetail);
                        if(ResponseData.DEFAULT_ERROR_CODE.equals(rd.getCode())){
                            return rd;
                        }*/
                        clearanceDetailList.add(clearanceDetail);
                    }
                }
            }
            if(isTransportCostDiff){
                //总重量
                BigDecimal totalWeight = BigDecimal.ZERO;
                Map<String, List<TgCustomsClearanceDetail>> boxListMap = param.getClearanceDetails().stream().collect(Collectors.groupingBy(detail -> detail.getPackCode()+detail.getBoxNo(), LinkedHashMap::new, Collectors.toList()));
                for (List<TgCustomsClearanceDetail> boxList : boxListMap.values()) {
                    totalWeight = totalWeight.add(boxList.get(0).getWeight());
                }
                //保费：总重量*运费系数*0.003
                clearance.setInsureCost(totalWeight.multiply(clearance.getTransportCost()).multiply(new BigDecimal(0.003)).setScale(2, BigDecimal.ROUND_HALF_UP));
            }
            mapper.updateById(clearance);

            if(CollectionUtil.isNotEmpty(clearanceDetailList)){
                for (TgCustomsClearanceDetail update : clearanceDetailList) {
                    LambdaUpdateWrapper<TgCustomsClearanceDetail> uw = new LambdaUpdateWrapper();
                            uw.eq(TgCustomsClearanceDetail :: getId, update.getId())
                            .setSql(update.getCustomsUnitPrice() == null, "CUSTOMS_UNIT_PRICE = NULL");
                    clearanceDetailService.update(update, uw);
                }
            }
        }
        return ResponseData.success();
    }

    @Override
    @DataSource(name = "logistics")
    @Transactional(rollbackFor = Exception.class)
    public void generateCustomsClearance(TgCustomsClearanceParam param, HttpServletResponse response) throws IOException {
        log.info("生成清关发票（通用）开始[{}]", JSONObject.toJSON(param));
        long start = System.currentTimeMillis();
        TgCustomsClearance clearance = mapper.selectById(param.getId());
        if(clearance == null){
            response.setHeader("code", "500");
            response.setHeader("message", URLEncoder.encode("清关发票数据不存在！", "UTF-8"));
            return;
        }

        String orderType = param.getOrderType();
        if(StringUtils.isBlank(orderType)){
            response.setHeader("code", "500");
            response.setHeader("message", URLEncoder.encode("清关发票类型入参不存在 ！", "UTF-8"));
            return;
        }

        LambdaQueryWrapper<TgReceiveCompany> queryRCWrapper = new LambdaQueryWrapper<>();
        queryRCWrapper.eq(TgReceiveCompany :: getCompanyNameCn, clearance.getReceiveCompanyNameCn());
        TgReceiveCompany receiveCompany = receiveCompanyService.getOne(queryRCWrapper);
        if(receiveCompany != null){
            StringBuffer addrCnBuffer = new StringBuffer();
            addrCnBuffer.append(receiveCompany.getCompanyNameCn());
            if(StringUtils.isNotBlank(receiveCompany.getAddrCn())){
                addrCnBuffer.append(",");
                addrCnBuffer.append(receiveCompany.getAddrCn());
            }
            if(StringUtils.isNotBlank(receiveCompany.getCity())){
                addrCnBuffer.append(",");
                addrCnBuffer.append(receiveCompany.getCity());
            }
            if(StringUtils.isNotBlank(receiveCompany.getState())){
                addrCnBuffer.append(",");
                addrCnBuffer.append(receiveCompany.getState());
            }
            if(StringUtils.isNotBlank(receiveCompany.getLogRecZip())){
                addrCnBuffer.append(",");
                addrCnBuffer.append(receiveCompany.getLogRecZip());
            }
            if(StringUtils.isNotBlank(receiveCompany.getCountryCode())){
                addrCnBuffer.append(",");
                addrCnBuffer.append(receiveCompany.getCountryCode());
            }
            clearance.setReceiveAddrCn(addrCnBuffer.toString());
        }

        QueryWrapper<TgCustomsClearanceDetail> cdPackCodeQuery = new QueryWrapper<>();
        cdPackCodeQuery.select("PACK_CODE").eq("PID", param.getId()).groupBy("PACK_CODE");
        List<TgCustomsClearanceDetail> cdPackCodeList = clearanceDetailService.list(cdPackCodeQuery);
        if(CollectionUtil.isNotEmpty(cdPackCodeList)){
            //明细的出货清单号集合
            List<String> packCodeList = cdPackCodeList.stream().map(TgCustomsClearanceDetail :: getPackCode).collect(Collectors.toList());
            //根据出货清单号获取EBMS出货订单ShipmentID数据
            List<TgLogisticsPackingResult> packingShipmentList = applyService.selectLogisticsPackingShipment(packCodeList);
            if(CollectionUtil.isNotEmpty(packingShipmentList)){
                Set<String> shipmentIDSet = new HashSet<>();
                LambdaUpdateWrapper<TgCustomsClearance> updateWrapper = new LambdaUpdateWrapper<>();
                Boolean isUpdate = Boolean.FALSE;
                updateWrapper.eq(TgCustomsClearance :: getId, clearance.getId());
                if(CollectionUtil.isNotEmpty(shipmentIDSet)){
                    String shipmentIDStr = shipmentIDSet.stream().collect(Collectors.joining(";"));
                    if(shipmentIDStr != null && !shipmentIDStr.equals(clearance.getShipmentID())){
                        clearance.setShipmentID(shipmentIDStr);
                        updateWrapper.set(TgCustomsClearance :: getShipmentID, clearance.getShipmentID());
                        isUpdate = Boolean.TRUE;
                    }
                }
                if(isUpdate){
                    updateWrapper.set(TgCustomsClearance :: getUpdateTime, DateUtil.date());
                    this.update(null, updateWrapper);
                }
            }
        }

        //sheet0表头数据
        TgCustomsClearanceExport0Result export0 = new TgCustomsClearanceExport0Result();
        BeanUtils.copyProperties(clearance, export0);
        export0.setPostDateStr(DateUtil.format(clearance.getPostDate(), "yyyy/MM/dd"));

        //查询清关单是否有分单号
        QueryWrapper<TgCustomsClearanceDetail> cdQuery = new QueryWrapper<>();
        cdQuery.select("NVL(SPLIT_ORDER, '-1') AS SPLIT_ORDER").eq("PID", param.getId()).groupBy("SPLIT_ORDER");
        List<TgCustomsClearanceDetail> splitOrderList = clearanceDetailService.list(cdQuery);
        if(splitOrderList.size() == 1){
            //sheet0列表数据
            List<TgCustomsClearanceExport0ListResult> export0List = new ArrayList<>();
            //sheet1列表数据
            List<TgCustomsClearanceExport1Result> export1List = new ArrayList<>();
            ResponseData resp = this.dealClearanceExport(param, export0, orderType, export0List, export1List);
            if (!ResponseData.DEFAULT_SUCCESS_CODE.equals(resp.getCode())){
                response.setHeader("code", "500");
                response.setHeader("message", URLEncoder.encode(resp.getMessage(), "UTF-8"));
                return;
            }

            ExcelWriter writer = null;
            try {
                /** 读取服务器端模板文件 */
                String modelPath = "/template/清关发票模板.xlsx";
                InputStream inputStream = this.getClass().getResourceAsStream(modelPath);
                String excelName = URLEncoder.encode("清关发票" + DateUtil.format(clearance.getPostDate(), DatePattern.PURE_DATETIME_PATTERN) + ".xlsx", "UTF-8");
                response.setContentType("application/vnd.ms-excel");
                response.addHeader("Content-Disposition", "attachment;filename=" + new String((excelName).getBytes(), "iso-8859-1"));
                response.setHeader("code", "200");
                writer = EasyExcel.write(response.getOutputStream()).withTemplate(inputStream).excelType(ExcelTypeEnum.XLSX).build();
                //填充列表开启自动换行,自动换行表示每次写入一条list数据是都会重新生成一行空行,此选项默认是关闭的,需要提前设置为true
                FillConfig fillConfig = FillConfig.builder().forceNewRow(Boolean.TRUE).build();

                //填充列表
                //合并类型 0：单元格合并行（向上合并）
                int merge0Type = 0;
                //模板列表【单元格合并行】标识的列数
                short signColumn0Index = 24;
                //需要合并的列
                int[] merge0ColumnIndex = {0, 9, 10, 11, 12, 13};
                //从第X行后开始合并
                int merge0RowIndex = 10;
                BaseExcelFillCellMergeStrategy excelFillCellMerge0Strategy = new BaseExcelFillCellMergeStrategy(merge0Type, signColumn0Index, merge0ColumnIndex, merge0RowIndex, null);
                WriteSheet sheet0 = EasyExcel.writerSheet(0).registerWriteHandler(excelFillCellMerge0Strategy).build();
                writer.fill(export0, sheet0);
                writer.fill(export0List, fillConfig, sheet0);

                //填充列表
                //合并类型 0：单元格合并行（向上合并）
                int merge1Type = 0;
                //模板列表【单元格合并行】标识的列数
                short signColumn1Index = 20;
                //需要合并的列
                int[] merge1ColumnIndex = {0, 1, 2, 3, 4, 5, 6, 7, 18};
                //从第X行后开始合并
                int merge1RowIndex = 1;
                BaseExcelFillCellMergeStrategy excelFillCellMerge1Strategy = new BaseExcelFillCellMergeStrategy(merge1Type, signColumn1Index, merge1ColumnIndex, merge1RowIndex, null);
                WriteSheet sheet1 = EasyExcel.writerSheet(1).registerWriteHandler(excelFillCellMerge1Strategy).build();
                writer.fill(export1List, fillConfig, sheet1);
                writer.finish();
            } catch (Exception e){
                response.setHeader("code", "500");
                response.setHeader("message", URLEncoder.encode("生成清关发票（通用）异常，" + e, "UTF-8"));
                log.error("生成清关发票（通用）异常，", e);
                return;
            } finally {
                if (writer != null) {
                    writer.finish();
                }
            }
        } else {
            response.setContentType("application/zip");
            response.setCharacterEncoding("utf-8");
            String zipName = "清关发票" + DateUtil.format(clearance.getPostDate(), DatePattern.PURE_DATETIME_PATTERN) + ".zip";
            response.setHeader("Content-disposition", "attachment;filename=" + zipName);
            response.setHeader("code", "200");
            ServletOutputStream outputStream = response.getOutputStream();
            ZipOutputStream zipOut = new ZipOutputStream(outputStream);
            //运费系数
            BigDecimal transportCost = export0.getTransportCost();
            for (TgCustomsClearanceDetail detail : splitOrderList) {
                //sheet0列表数据
                //分单号
                param.setSplitOrder(detail.getSplitOrder());
                //sheet0列表数据
                List<TgCustomsClearanceExport0ListResult> export0List = new ArrayList<>();
                //sheet1列表数据
                List<TgCustomsClearanceExport1Result> export1List = new ArrayList<>();
                export0.setTransportCost(transportCost);
                ResponseData resp = this.dealClearanceExport(param, export0, orderType, export0List, export1List);
                if (!ResponseData.DEFAULT_SUCCESS_CODE.equals(resp.getCode())){
                    response.setHeader("code", "500");
                    response.setHeader("message", URLEncoder.encode(resp.getMessage(), "UTF-8"));
                    return;
                }

                ExcelWriter writer = null;
                try {
                    /** 读取服务器端模板文件 */
                    String modelPath = "/template/清关发票模板.xlsx";
                    InputStream inputStream = this.getClass().getResourceAsStream(modelPath);
                    String excelName = "清关发票" + DateUtil.format(clearance.getPostDate(), DatePattern.PURE_DATETIME_PATTERN) + "_" +detail.getSplitOrder() + ".xlsx";

                    // 创建一个临时的ByteArrayOutputStream用于存储生成的Excel文件数据
                    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                    writer = EasyExcel.write(byteArrayOutputStream).withTemplate(inputStream).excelType(ExcelTypeEnum.XLSX).build();
                    //填充列表开启自动换行,自动换行表示每次写入一条list数据是都会重新生成一行空行,此选项默认是关闭的,需要提前设置为true
                    FillConfig fillConfig = FillConfig.builder().forceNewRow(Boolean.TRUE).build();

                    //填充列表
                    //合并类型 0：单元格合并行（向上合并）
                    int merge0Type = 0;
                    //模板列表【单元格合并行】标识的列数
                    short signColumn0Index = 24;
                    //需要合并的列
                    int[] merge0ColumnIndex = {0, 9, 10, 11, 12, 13};
                    //从第X行后开始合并
                    int merge0RowIndex = 10;
                    BaseExcelFillCellMergeStrategy excelFillCellMerge0Strategy = new BaseExcelFillCellMergeStrategy(merge0Type, signColumn0Index, merge0ColumnIndex, merge0RowIndex, null);
                    WriteSheet sheet0 = EasyExcel.writerSheet(0).registerWriteHandler(excelFillCellMerge0Strategy).build();
                    writer.fill(export0, sheet0);
                    writer.fill(export0List, fillConfig, sheet0);

                    //填充列表
                    //合并类型 0：单元格合并行（向上合并）
                    int merge1Type = 0;
                    //模板列表【单元格合并行】标识的列数
                    short signColumn1Index = 20;
                    //需要合并的列
                    int[] merge1ColumnIndex = {0, 1, 2, 3, 4, 5, 6, 7, 18};
                    //从第X行后开始合并
                    int merge1RowIndex = 1;
                    BaseExcelFillCellMergeStrategy excelFillCellMerge1Strategy = new BaseExcelFillCellMergeStrategy(merge1Type, signColumn1Index, merge1ColumnIndex, merge1RowIndex, null);
                    WriteSheet sheet1 = EasyExcel.writerSheet(1).registerWriteHandler(excelFillCellMerge1Strategy).build();
                    writer.fill(export1List, fillConfig, sheet1);
                    writer.finish();
                    // 创建文件
                    zipOut.putNextEntry(new ZipEntry(excelName));
                    zipOut.write(byteArrayOutputStream.toByteArray());
                } catch (Exception e){
                    response.setHeader("code", "500");
                    response.setHeader("message", URLEncoder.encode("生成清关发票（通用）异常，" + e, "UTF-8"));
                    log.error("生成清关发票（通用）异常，", e);
                    return;
                } finally {
                    if (writer != null) {
                        writer.finish();
                    }
                    if (outputStream != null) {
                        outputStream.flush();
                    }
                }
            }
            zipOut.closeEntry();
            zipOut.finish();
            zipOut.close();
            outputStream.flush();
            outputStream.close();
        }
        log.info("生成清关发票（通用）结束，耗时------------------->" + (System.currentTimeMillis() - start) + "ms");
    }

    /**
     * 处理清关发票通用模板数据
     * @param param
     * @param export0
     * @param orderType
     * @param export0List
     * @param export1List
     * @return
     * @throws MalformedURLException
     */
    private ResponseData dealClearanceExport(TgCustomsClearanceParam param, TgCustomsClearanceExport0Result export0, String orderType,
                                              List<TgCustomsClearanceExport0ListResult> export0List, List<TgCustomsClearanceExport1Result> export1List) throws MalformedURLException {
        DecimalFormat oneDf = new DecimalFormat("0.0");
        DecimalFormat twoDf = new DecimalFormat("0.00");

        //获取清关发票sheet0箱子列表
        List<TgCustomsClearanceExport0ListResult> exportBox0List = mapper.generateExportBox0(param);

        //获取清关发票基本信息
        List<TgCustomsClearanceExport0ListResult> clearanceInfoList = mapper.getClearanceInfo(param);
        List<TgCustomsClearanceExport0ListResult> clearanceInfoListTemp = BeanUtil.copyToList(clearanceInfoList, TgCustomsClearanceExport0ListResult.class);
        List<String> customsTaxRateEmptyList = new ArrayList();
        List<String> indirectRateEmptyList = new ArrayList();
        clearanceInfoList.stream().forEach(i -> {
            if(ObjUtil.isNull(i.getTaxRate()) || ObjUtil.isNull(i.getChangeTaxRate()) || ObjUtil.isNull(i.getAddTaxRate())){
                customsTaxRateEmptyList.add(i.getMainMaterialCode());
            }
            if(ObjUtil.isNull(i.getIndirectRate())){
                indirectRateEmptyList.add(i.getMainMaterialCode());
            }
        });
        if(CollectionUtil.isNotEmpty(customsTaxRateEmptyList)){
            return ResponseData.error(StringUtils.join(customsTaxRateEmptyList, ',') + "清关税率为空，请先维护清关税率");
        }
        if(CollectionUtil.isNotEmpty(indirectRateEmptyList)){
            return ResponseData.error("ERP固定汇率不存在！");
        }
        Boolean customsUnitPriceEmpty = clearanceInfoList.stream().anyMatch(i -> ObjUtil.isNull(i.getCustomsUnitPrice()));
        if(customsUnitPriceEmpty){
            return ResponseData.error("清关单价为空，请先维护清关单价");
        }
        for (TgCustomsClearanceExport0ListResult export0ListResult : clearanceInfoList) {
            //计算单个品名毛重：取值优先级：人工合并 > 系统合并 > 未合并
            if("人工已合并".equals(export0ListResult.getClearMergeStatus())){
                //人工已合并，取值：（人工合并之前的产品基本信息的重量 * 数量）总和 / 数量总和
                param.setMergeId(export0ListResult.getMergeId());
                TgCustomsClearanceExport0ListResult handAvgWeight = mapper.getHandAvgWeight(param);
                if(handAvgWeight != null){
                    export0ListResult.setAvgWeight(handAvgWeight.getAvgWeight());
                }
            }
            if("系统已合并".equals(export0ListResult.getClearMergeStatus())){
                //系统已合并，取值：（系统已合并之前的产品基本信息的重量 * 数量）总和 / 数量总和
                param.setMergeId(export0ListResult.getMergeId());
                TgCustomsClearanceExport0ListResult sysAvgWeight = mapper.getSysAvgWeight(param);
                if(sysAvgWeight != null){
                    export0ListResult.setAvgWeight(sysAvgWeight.getAvgWeight());
                }
            }
            if("系统未合并".equals(export0ListResult.getClearMergeStatus()) || "人工未合并".equals(export0ListResult.getClearMergeStatus())){
                //未合并，取值产品基本信息的重量
                LambdaQueryWrapper<TgBaseProduct> productQw = new LambdaQueryWrapper<>();
                productQw.eq(TgBaseProduct :: getMainMaterialCode, export0ListResult.getMainMaterialCode());
                TgBaseProduct product = productService.getOne(productQw);
                if(product == null){
                    return ResponseData.error("未查询到产品基本信息" + export0ListResult.getMainMaterialCode() + "主物料编码");
                }
                export0ListResult.setAvgWeight(product.getGrossWeightOrg());
            }
        }

        //动态维度合并清关发票（出货清单号+箱号+动态根据手动清关产品合并、系统清关产品合并）
        Map<String, List<TgCustomsClearanceExport0ListResult>> clearanceInfoListMap = clearanceInfoList.stream().collect(Collectors.groupingBy(item ->{
            item.setGroupStr(item.getMergeId() == null ? item.getId() + "_" : item.getMergeId() + "_" + item.getClearMergeStatus());
            return item.getPackCode() + item.getBoxNo() + item.getGroupStr();
        }, LinkedHashMap::new, Collectors.toList()));
        List<TgCustomsClearanceExport0ListResult> groupList = new ArrayList<>();
        for (Map.Entry<String, List<TgCustomsClearanceExport0ListResult>> entry : clearanceInfoListMap.entrySet()) {
            List<TgCustomsClearanceExport0ListResult> groupDetailList = entry.getValue();
            BigDecimal totalQuantity = groupDetailList.stream().map(TgCustomsClearanceExport0ListResult :: getQuantity).reduce(BigDecimal.ZERO, BigDecimal :: add);
            TgCustomsClearanceExport0ListResult group = groupDetailList.get(0);//已经分组了，获取第1条数据就好了
            group.setTotalQuantity(totalQuantity);//维度分组总数量
            groupList.add(group);
        }

        //计算最低、平均清关单价、并取清关单价最低的记录报关（动态根据手动清关产品合并、系统清关产品合并）
        Map<String, List<TgCustomsClearanceExport0ListResult>> clearancePriceListMap = clearanceInfoList.stream().collect(
                Collectors.groupingBy(TgCustomsClearanceExport0ListResult :: getGroupStr, LinkedHashMap::new, Collectors.toList()));
        //sheet0最低价、平均价数据
        List<TgCustomsClearanceExport0ListResult> groupPriceList = new ArrayList<>();
        //sheet1数据
        List<TgCustomsClearanceExport1Result> groupSheet1List = new ArrayList<>();
        for (Map.Entry<String, List<TgCustomsClearanceExport0ListResult>> entry : clearancePriceListMap.entrySet()) {
            List<TgCustomsClearanceExport0ListResult> groupDetailList = entry.getValue();
            BigDecimal minPrice = groupDetailList.stream().map(TgCustomsClearanceExport0ListResult :: getCustomsUnitPrice).reduce(groupDetailList.get(0).getCustomsUnitPrice(), BigDecimal :: min);
            BigDecimal totalPrice = groupDetailList.stream().map(TgCustomsClearanceExport0ListResult :: getCustomsUnitPrice).reduce(BigDecimal.ZERO, BigDecimal :: add);
            BigDecimal totalQuantity = groupDetailList.stream().map(TgCustomsClearanceExport0ListResult :: getQuantity).reduce(BigDecimal.ZERO, BigDecimal :: add);
            BigDecimal avgPrice = totalPrice.divide(new BigDecimal(groupDetailList.size())).setScale(2, BigDecimal.ROUND_HALF_UP);
            //sheet1清关合并明细ID集合
            List<BigDecimal> detailIdList = groupDetailList.stream()
                    .sorted(Comparator.comparing(TgCustomsClearanceExport0ListResult :: getId))
                    .map(TgCustomsClearanceExport0ListResult :: getId).collect(Collectors.toList());
            TgCustomsClearanceExport0ListResult minGroup = groupDetailList.stream().min(Comparator.comparing(TgCustomsClearanceExport0ListResult :: getCustomsUnitPrice)).get();//已经分组了，取价格最低的数据报关
            minGroup.setUnitPriceAvg(avgPrice);//维度分组平均价
            minGroup.setUnitPriceMin(minPrice);//维度分组最低价
            groupPriceList.add(minGroup);

            TgCustomsClearanceExport1Result sheet1 = new TgCustomsClearanceExport1Result();
            sheet1.setClearNameCn(minGroup.getClearNameCn());
            sheet1.setInvoiceProNameEn(minGroup.getInvoiceProNameEn());
            sheet1.setClearMaterialEn(minGroup.getClearMaterialEn());
            sheet1.setCurrency(minGroup.getCurrency());
            sheet1.setImportTaxNum(minGroup.getImportTaxNum());
            sheet1.setSortCondition(minGroup.getClearNameCn() + minGroup.getInvoiceProNameEn() + minGroup.getClearMaterialEn());
            sheet1.setGroupStr(minGroup.getGroupStr());
            sheet1.setTotalQuantity(totalQuantity);
            sheet1.setDetailIdList(detailIdList);
            if("0".equals(orderType)){
                sheet1.setUnitPrice(minPrice);
                sheet1.setTotalAmount(totalQuantity.multiply(minPrice));
            }
            if("1".equals(orderType)){
                sheet1.setUnitPrice(avgPrice);
                sheet1.setTotalAmount(totalQuantity.multiply(avgPrice));
            }
            groupSheet1List.add(sheet1);
        }
        //sheet0最低价、平均价数据
        Map<String, List<TgCustomsClearanceExport0ListResult>> priceListMap = groupPriceList.stream().collect(
                Collectors.groupingBy(TgCustomsClearanceExport0ListResult :: getGroupStr, LinkedHashMap::new, Collectors.toList()));
        //sheet1数据
        Map<String, List<TgCustomsClearanceExport1Result>> groupSheet1ListMap = groupSheet1List.stream().collect(
                Collectors.groupingBy(TgCustomsClearanceExport1Result :: getGroupStr, LinkedHashMap::new, Collectors.toList()));

        //组合sheet0价格和销售链接数据
        for (TgCustomsClearanceExport0ListResult boxDate : groupList) {
            List<TgCustomsClearanceExport0ListResult> priceList = priceListMap.get(boxDate.getGroupStr());
            if(CollectionUtil.isNotEmpty(priceList)){
                TgCustomsClearanceExport0ListResult price = priceList.get(0);
                boxDate.setUnitPriceMin(price.getUnitPriceMin());
                boxDate.setUnitPriceAvg(price.getUnitPriceAvg());
                boxDate.setAmazonSaleLink(price.getAmazonSaleLink());
                boxDate.setAmazonPictureLink(price.getAmazonPictureLink());
                boxDate.setTaxRate(price.getTaxRate());
            }
        }
        Map<String, List<TgCustomsClearanceExport0ListResult>> boxDetailListMap = groupList.stream()
                .collect(Collectors.groupingBy(item -> item.getPackCode() + item.getBoxNo(), LinkedHashMap::new, Collectors.toList()));

        //组合sheet0箱子数据
        for (TgCustomsClearanceExport0ListResult date : exportBox0List) {
            List<TgCustomsClearanceExport0ListResult> boxDetailList = boxDetailListMap.get(date.getPackCode()+date.getBoxNo());
            for (int i = 0; i < boxDetailList.size(); i++) {
                TgCustomsClearanceExport0ListResult boxDetail = boxDetailList.get(i);
                if(i+1 == boxDetailList.size()){
                    boxDetail.setIsMergeUpCell("0");
                }else{
                    boxDetail.setIsMergeUpCell("1");
                }
                boxDetail.setBoxNo(date.getBoxNo());
                boxDetail.setWeight(date.getWeight());
                boxDetail.setGoodsLong(date.getGoodsLong());
                boxDetail.setWide(date.getWide());
                boxDetail.setHigh(date.getHigh());
                boxDetail.setTotalVolume(date.getTotalVolume());
                export0List.add(boxDetail);
            }
        }

        Set<String> boxNoSet = new HashSet<>();
        BigDecimal totalQuantity = BigDecimal.ZERO;
        BigDecimal totalAmount = BigDecimal.ZERO;
        BigDecimal weight = BigDecimal.ZERO;
        BigDecimal totalVolume = BigDecimal.ZERO;
        for (TgCustomsClearanceExport0ListResult result : export0List) {
            if(!boxNoSet.contains(result.getPackCode()+result.getBoxNo())){
                boxNoSet.add(result.getPackCode()+result.getBoxNo());
                weight = weight.add(result.getWeight());
                totalVolume = totalVolume.add(result.getTotalVolume());
            }
            totalQuantity = totalQuantity.add(result.getTotalQuantity());
            BigDecimal avgWeight = result.getAvgWeight().divide(new BigDecimal(1000), 2, BigDecimal.ROUND_HALF_UP);
            result.setGrossWeightStr(twoDf.format(avgWeight));
            result.setNetWeightStr(twoDf.format(avgWeight));
            result.setWeightStr(oneDf.format(result.getWeight()));
            result.setTotalVolumeStr(twoDf.format(result.getTotalVolume()));
            if("0".equals(orderType)){
                result.setUnitPrice(result.getUnitPriceMin());
                result.setAmazonPictureUrl(result.getAmazonPictureLink() == null ? null : new URL(result.getAmazonPictureLink()));
            }
            if("1".equals(orderType)){
                result.setUnitPrice(result.getUnitPriceAvg());
                result.setAmazonPictureUrl(null);
                result.setAmazonSaleLink(null);
            }
            result.setUnitPriceStr(twoDf.format(result.getUnitPrice()));
            result.setTotalAmount(result.getUnitPrice().multiply(result.getTotalQuantity()));
            result.setTotalAmountStr(twoDf.format(result.getTotalAmount()));

            totalAmount = totalAmount.add(result.getTotalAmount());
        }

        //最后一列合计
        TgCustomsClearanceExport0ListResult endExport0List = new TgCustomsClearanceExport0ListResult();
        endExport0List.setBoxNo("总计");
        endExport0List.setClearNameCn(String.valueOf(boxNoSet.size()));
        endExport0List.setTotalQuantity(totalQuantity);
        endExport0List.setTotalAmountStr(twoDf.format(totalAmount));
        endExport0List.setWeightStr(oneDf.format(weight));
        endExport0List.setTotalVolumeStr(twoDf.format(totalVolume));
        endExport0List.setIsMergeUpCell("-1");
        export0List.add(endExport0List);
        export0.setTransportCost(export0.getTransportCost().multiply(weight).setScale(2, BigDecimal.ROUND_HALF_UP));

        //组合sheet1数据
        Map<BigDecimal, List<TgCustomsClearanceExport0ListResult>> clearanceMap = clearanceInfoListTemp.stream().collect(
                Collectors.groupingBy(TgCustomsClearanceExport0ListResult :: getId, LinkedHashMap::new, Collectors.toList()));
        for (Map.Entry<String, List<TgCustomsClearanceExport1Result>> entry : groupSheet1ListMap.entrySet()) {
            List<TgCustomsClearanceExport1Result> groupDetailList = entry.getValue();
            TgCustomsClearanceExport1Result groupDetail = groupDetailList.get(0);
            List<BigDecimal> detailIdList = groupDetail.getDetailIdList();
            for (int i = 0; i < detailIdList.size(); i++) {
                TgCustomsClearanceExport1Result export1 = new TgCustomsClearanceExport1Result();
                BeanUtils.copyProperties(groupDetail, export1);
                export1.setUnitPriceStr(twoDf.format(export1.getUnitPrice()));
                export1.setTotalAmountStr(twoDf.format(export1.getTotalAmount()));

                if(i+1 == detailIdList.size()){
                    export1.setIsMergeUpCell("0");
                }else{
                    export1.setIsMergeUpCell("1");
                }
                TgCustomsClearanceExport0ListResult result = clearanceMap.get(detailIdList.get(i)).get(0);
                export1.setK3Code(result.getSku());
                export1.setAmazonSaleLink(result.getAmazonSaleLink());
                export1.setAmazonPictureLink(result.getAmazonPictureLink());
                export1.setAmazonPictureUrl(export1.getAmazonPictureLink() == null ? null : new URL(export1.getAmazonPictureLink()));
                export1.setAmazonSalePrice(result.getAmazonSalePrice());
                if(export1.getAmazonSalePrice() != null){
                    export1.setAmazonSalePriceStr(twoDf.format(export1.getAmazonSalePrice()));
                }else{
                    export1.setAmazonSalePriceStr("");
                }
                export1.setTotalClearanceQuantity(result.getQuantity());
                export1.setClearanceUnitPrice(result.getCustomsUnitPrice());
                export1.setClearanceUnitPriceStr(twoDf.format(export1.getClearanceUnitPrice()));
                export1.setTotalClearanceAmount(result.getQuantity().multiply(result.getCustomsUnitPrice()));
                export1.setTotalClearanceAmountStr(twoDf.format(export1.getTotalClearanceAmount()));
                export1.setChangeTaxRate(export1.getTotalClearanceAmount().multiply(result.getChangeTaxRate()));
                export1.setChangeTaxRateStr(twoDf.format(export1.getChangeTaxRate()));
                export1.setTaxRate(export1.getTotalClearanceAmount().multiply(result.getTaxRate()));
                export1.setTaxRateStr(twoDf.format(export1.getTaxRate()));
                export1.setAddTaxRate(export1.getTotalClearanceAmount().multiply(result.getAddTaxRate()));
                export1.setAddTaxRateStr(twoDf.format(export1.getAddTaxRate()));
                export1List.add(export1);
            }
        }
        //处理最后一行且最后一单元格的数据，需删除合并标识单元格
        TgCustomsClearanceExport1Result lastData = export1List.get(export1List.size() - 1);
        lastData.setIsMergeUpCell("-1");
        return ResponseData.success();
    }

    @Override
    @DataSource(name = "logistics")
    @Transactional(rollbackFor = Exception.class)
    public void generateCustomsClearanceKD(TgCustomsClearanceParam param, HttpServletResponse response) throws IOException {
        log.info("生成清关发票（快递）开始[{}]", JSONObject.toJSON(param));
        long start = System.currentTimeMillis();
        DecimalFormat twoDf = new DecimalFormat("0.00");
        TgCustomsClearance clearance = mapper.selectById(param.getId());
        if(clearance == null){
            response.setHeader("code", "500");
            response.setHeader("message", URLEncoder.encode("清关发票数据不存在！", "UTF-8"));
            return;
        }

        String orderType = param.getOrderType();
        if(StringUtils.isBlank(orderType)){
            response.setHeader("code", "500");
            response.setHeader("message", URLEncoder.encode("清关发票类型入参不存在 ！", "UTF-8"));
            return;
        }

        LambdaQueryWrapper<TgReceiveCompany> queryRCWrapper = new LambdaQueryWrapper<>();
        queryRCWrapper.eq(TgReceiveCompany :: getCompanyNameCn, clearance.getReceiveCompanyNameCn());
        TgReceiveCompany receiveCompany = receiveCompanyService.getOne(queryRCWrapper);
        if(receiveCompany != null){
            StringBuffer addrCnBuffer = new StringBuffer();
            addrCnBuffer.append(receiveCompany.getCompanyNameCn());
            if(StringUtils.isNotBlank(receiveCompany.getAddrCn())){
                addrCnBuffer.append(",");
                addrCnBuffer.append(receiveCompany.getAddrCn());
            }
            if(StringUtils.isNotBlank(receiveCompany.getCity())){
                addrCnBuffer.append(",");
                addrCnBuffer.append(receiveCompany.getCity());
            }
            if(StringUtils.isNotBlank(receiveCompany.getState())){
                addrCnBuffer.append(",");
                addrCnBuffer.append(receiveCompany.getState());
            }
            if(StringUtils.isNotBlank(receiveCompany.getLogRecZip())){
                addrCnBuffer.append(",");
                addrCnBuffer.append(receiveCompany.getLogRecZip());
            }
            if(StringUtils.isNotBlank(receiveCompany.getCountryCode())){
                addrCnBuffer.append(",");
                addrCnBuffer.append(receiveCompany.getCountryCode());
            }
            clearance.setReceiveAddrCn(addrCnBuffer.toString());
        }

        QueryWrapper<TgCustomsClearanceDetail> cdPackCodeQuery = new QueryWrapper<>();
        cdPackCodeQuery.select("PACK_CODE").eq("PID", param.getId()).groupBy("PACK_CODE");
        List<TgCustomsClearanceDetail> cdPackCodeList = clearanceDetailService.list(cdPackCodeQuery);
        if(CollectionUtil.isNotEmpty(cdPackCodeList)){
            //明细的出货清单号集合
            List<String> packCodeList = cdPackCodeList.stream().map(TgCustomsClearanceDetail :: getPackCode).collect(Collectors.toList());
            //根据出货清单号获取EBMS出货订单ShipmentID数据
            List<TgLogisticsPackingResult> packingShipmentList = applyService.selectLogisticsPackingShipment(packCodeList);
            if(CollectionUtil.isNotEmpty(packingShipmentList)){
                Set<String> shipmentIDSet = new HashSet<>();
                LambdaUpdateWrapper<TgCustomsClearance> updateWrapper = new LambdaUpdateWrapper<>();
                Boolean isUpdate = Boolean.FALSE;
                updateWrapper.eq(TgCustomsClearance :: getId, clearance.getId());
                if(CollectionUtil.isNotEmpty(shipmentIDSet)){
                    String shipmentIDStr = shipmentIDSet.stream().collect(Collectors.joining(";"));
                    if(shipmentIDStr != null && !shipmentIDStr.equals(clearance.getShipmentID())){
                        clearance.setShipmentID(shipmentIDStr);
                        updateWrapper.set(TgCustomsClearance :: getShipmentID, clearance.getShipmentID());
                        isUpdate = Boolean.TRUE;
                    }
                }

                if(isUpdate){
                    updateWrapper.set(TgCustomsClearance :: getUpdateTime, DateUtil.date());
                    this.update(null, updateWrapper);
                }
            }
        }

        //sheet0表头数据
        TgCustomsClearanceExport0Result export0 = new TgCustomsClearanceExport0Result();
        BeanUtils.copyProperties(clearance, export0);
        export0.setPostDateStr(DateUtil.format(clearance.getPostDate(), "yyyy/MM/dd"));

        //查询清关单是否有分单号
        QueryWrapper<TgCustomsClearanceDetail> cdQuery = new QueryWrapper<>();
        cdQuery.select("NVL(SPLIT_ORDER, '-1') AS SPLIT_ORDER").eq("PID", param.getId()).groupBy("SPLIT_ORDER");
        List<TgCustomsClearanceDetail> splitOrderList = clearanceDetailService.list(cdQuery);

        response.setContentType("application/zip");
        response.setCharacterEncoding("utf-8");
        String zipName = URLEncoder.encode("清关发票" + DateUtil.format(clearance.getPostDate(), DatePattern.PURE_DATETIME_PATTERN) + ".zip", "UTF-8");
        response.setHeader("Content-disposition", "attachment;filename=" + zipName);
        response.setHeader("code", "200");
        ServletOutputStream outputStream = response.getOutputStream();
        ZipOutputStream zipOut = new ZipOutputStream(outputStream);
        if(splitOrderList.size() == 1){
            //判断是否存在1个箱子有多个重量的情况
            Integer counts = mapper.haveMoreWeight(param);
            if(counts != null){
                response.setHeader("code", "500");
                response.setHeader("message", URLEncoder.encode("存在1个箱子有多个重量的情况，生成清关发票失败！", "UTF-8"));
                return;
            }

            //根据单号和分单号获取清关发票总重量和货箱总数
            TgCustomsClearanceExport0Result totalResult = mapper.getClearanceSumWeight(param);
            export0.setSumWeightStr(twoDf.format(totalResult.getSumWeight()));
            export0.setTransportCost(export0.getTransportCost().multiply(totalResult.getSumWeight()).setScale(2, BigDecimal.ROUND_HALF_UP));
            export0.setTotalBoxNum(totalResult.getTotalBoxNum());

            //sheet0列表数据
            List<TgCustomsClearanceExport0ListResult> export0List = new ArrayList<>();
            //sheet1列表数据
            List<TgCustomsClearanceExport1Result> export1List = new ArrayList<>();
            //invoice列表数据
            List<TgCustomsClearanceExport1Result> invoiceList = new ArrayList<>();
            ResponseData resp = this.dealClearanceExportKD(param, export0, orderType, export0List, export1List, invoiceList);
            if (!ResponseData.DEFAULT_SUCCESS_CODE.equals(resp.getCode())){
                response.setHeader("code", "500");
                response.setHeader("message", URLEncoder.encode(resp.getMessage(), "UTF-8"));
                return;
            }
            ExcelWriter writer = null;
            try {
                /** 读取服务器端模板文件 */
                String modelPath = "/template/清关发票快递模板.xlsx";
                InputStream inputStream = this.getClass().getResourceAsStream(modelPath);
                String excelName = "清关发票" + DateUtil.format(clearance.getPostDate(), DatePattern.PURE_DATETIME_PATTERN) + ".xlsx";
                // 创建一个临时的ByteArrayOutputStream用于存储生成的Excel文件数据
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                writer = EasyExcel.write(byteArrayOutputStream).withTemplate(inputStream).excelType(ExcelTypeEnum.XLSX).build();
                //填充列表开启自动换行,自动换行表示每次写入一条list数据是都会重新生成一行空行,此选项默认是关闭的,需要提前设置为true
                FillConfig fillConfig = FillConfig.builder().forceNewRow(Boolean.TRUE).build();

                //填充列表
                //合并类型 1：单元格合并列（向左合并）
                int merge0Type = 1;
                //模板列表【单元格合并行】标识的列数
                short signColumn0Index = 13;
                //从第X行后开始合并
                int merge0RowIndex = 21;
                //向左合并单元格的信息：Map<合并单元格的列下标, 向左合并单元格数>
                List<Map<Integer, Integer>> mergeColumnList = new ArrayList<>();
                Map<Integer, Integer> oneMap = new HashMap<>();
                oneMap.put(2, 1);
                mergeColumnList.add(oneMap);
                Map<Integer, Integer> twoMap = new HashMap<>();
                twoMap.put(5, 1);
                mergeColumnList.add(twoMap);
                BaseExcelFillCellMergeStrategy excelFillCellMerge0Strategy = new BaseExcelFillCellMergeStrategy(merge0Type, signColumn0Index, null, merge0RowIndex, mergeColumnList);
                WriteSheet sheet0 = EasyExcel.writerSheet(0).registerWriteHandler(excelFillCellMerge0Strategy).build();
                writer.fill(export0, sheet0);
                writer.fill(export0List, fillConfig, sheet0);

                //填充列表
                //合并类型 0：单元格合并行（向上合并）
                int merge1Type = 0;
                //模板列表【单元格合并行】标识的列数
                short signColumn1Index = 20;
                //需要合并的列
                int[] merge1ColumnIndex = {0, 1, 2, 3, 4, 5, 6, 7, 18};
                //从第X行后开始合并
                int merge1RowIndex = 1;
                BaseExcelFillCellMergeStrategy excelFillCellMerge1Strategy = new BaseExcelFillCellMergeStrategy(merge1Type, signColumn1Index, merge1ColumnIndex, merge1RowIndex, null);
                WriteSheet sheet1 = EasyExcel.writerSheet(1).registerWriteHandler(excelFillCellMerge1Strategy).build();
                writer.fill(export1List, fillConfig, sheet1);
                writer.finish();

                // 创建文件
                zipOut.putNextEntry(new ZipEntry(excelName));
                zipOut.write(byteArrayOutputStream.toByteArray());
            } catch (Exception e){
                response.setHeader("code", "500");
                response.setHeader("message", URLEncoder.encode("生成清关发票（快递）异常，" + e, "UTF-8"));
                log.error("生成清关发票（快递）异常，", e);
                return;
            } finally {
                if (writer != null) {
                    writer.finish();
                }
                if (outputStream != null) {
                    outputStream.flush();
                }
            }

            // 创建invoice文件
            // 创建一个临时的ByteArrayOutputStream用于存储生成的Excel文件数据
            ByteArrayOutputStream byteArrayOutputStream1 = new ByteArrayOutputStream();
            EasyExcel.write(byteArrayOutputStream1, TgCustomsClearanceExport1Result.class).sheet("invoice").doWrite(invoiceList);
            String excelName = "invoice.csv";
            zipOut.putNextEntry(new ZipEntry(excelName));
            zipOut.write(byteArrayOutputStream1.toByteArray());
        } else {
            for (TgCustomsClearanceDetail detail : splitOrderList) {
                //判断是否存在1个箱子有多个重量的情况
                Integer counts = mapper.haveMoreWeight(param);
                if(counts != null){
                    response.setHeader("code", "500");
                    response.setHeader("message", URLEncoder.encode("存在1个箱子有多个重量的情况，生成清关发票失败！", "UTF-8"));
                    return;
                }

                //根据单号和分单号获取清关发票总重量和货箱总数
                param.setSplitOrder(detail.getSplitOrder());//分单号
                TgCustomsClearanceExport0Result totalResult = mapper.getClearanceSumWeight(param);
                export0.setSumWeightStr(twoDf.format(totalResult.getSumWeight()));
                export0.setTransportCost(export0.getTransportCost().multiply(totalResult.getSumWeight()).setScale(2, BigDecimal.ROUND_HALF_UP));
                export0.setTotalBoxNum(totalResult.getTotalBoxNum());

                //sheet0列表数据
                List<TgCustomsClearanceExport0ListResult> export0List = new ArrayList<>();
                //sheet1列表数据
                List<TgCustomsClearanceExport1Result> export1List = new ArrayList<>();
                //invoice列表数据
                List<TgCustomsClearanceExport1Result> invoiceList = new ArrayList<>();
                ResponseData resp = this.dealClearanceExportKD(param, export0, orderType, export0List, export1List, invoiceList);
                if (!ResponseData.DEFAULT_SUCCESS_CODE.equals(resp.getCode())){
                    response.setHeader("code", "500");
                    response.setHeader("message", URLEncoder.encode(resp.getMessage(), "UTF-8"));
                    return;
                }
                ExcelWriter writer = null;
                try {
                    /** 读取服务器端模板文件 */
                    String modelPath = "/template/清关发票快递模板.xlsx";
                    InputStream inputStream = this.getClass().getResourceAsStream(modelPath);
                    String excelName = "清关发票" + DateUtil.format(clearance.getPostDate(), DatePattern.PURE_DATETIME_PATTERN) + "_" +detail.getSplitOrder() + ".xlsx";

                    // 创建一个临时的ByteArrayOutputStream用于存储生成的Excel文件数据
                    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                    writer = EasyExcel.write(byteArrayOutputStream).withTemplate(inputStream).excelType(ExcelTypeEnum.XLSX).build();
                    //填充列表开启自动换行,自动换行表示每次写入一条list数据是都会重新生成一行空行,此选项默认是关闭的,需要提前设置为true
                    FillConfig fillConfig = FillConfig.builder().forceNewRow(Boolean.TRUE).build();

                    //填充列表
                    //合并类型 1：单元格合并列（向左合并）
                    int merge0Type = 1;
                    //模板列表【单元格合并行】标识的列数
                    short signColumn0Index = 13;
                    //从第X行后开始合并
                    int merge0RowIndex = 21;
                    //向左合并单元格的信息：Map<合并单元格的列下标, 向左合并单元格数>
                    List<Map<Integer, Integer>> mergeColumnList = new ArrayList<>();
                    Map<Integer, Integer> oneMap = new HashMap<>();
                    oneMap.put(2, 1);
                    mergeColumnList.add(oneMap);
                    Map<Integer, Integer> twoMap = new HashMap<>();
                    twoMap.put(5, 1);
                    mergeColumnList.add(twoMap);
                    BaseExcelFillCellMergeStrategy excelFillCellMerge0Strategy = new BaseExcelFillCellMergeStrategy(merge0Type, signColumn0Index, null, merge0RowIndex, mergeColumnList);
                    WriteSheet sheet0 = EasyExcel.writerSheet(0).registerWriteHandler(excelFillCellMerge0Strategy).build();
                    writer.fill(export0, sheet0);
                    writer.fill(export0List, fillConfig, sheet0);

                    //填充列表
                    //合并类型 0：单元格合并行（向上合并）
                    int merge1Type = 0;
                    //模板列表【单元格合并行】标识的列数
                    short signColumn1Index = 20;
                    //需要合并的列
                    int[] merge1ColumnIndex = {0, 1, 2, 3, 4, 5, 6, 7, 18};
                    //从第X行后开始合并
                    int merge1RowIndex = 1;
                    BaseExcelFillCellMergeStrategy excelFillCellMerge1Strategy = new BaseExcelFillCellMergeStrategy(merge1Type, signColumn1Index, merge1ColumnIndex, merge1RowIndex, null);
                    WriteSheet sheet1 = EasyExcel.writerSheet(1).registerWriteHandler(excelFillCellMerge1Strategy).build();
                    writer.fill(export1List, fillConfig, sheet1);
                    writer.finish();
                    // 创建文件
                    zipOut.putNextEntry(new ZipEntry(excelName));
                    zipOut.write(byteArrayOutputStream.toByteArray());
                } catch (Exception e){
                    response.setHeader("code", "500");
                    response.setHeader("message", URLEncoder.encode("生成清关发票（快递）异常，" + e, "UTF-8"));
                    log.error("生成清关发票（快递）异常，", e);
                    return;
                } finally {
                    if (writer != null) {
                        writer.finish();
                    }
                    if (outputStream != null) {
                        outputStream.flush();
                    }
                }

                // 创建invoice文件
                // 创建一个临时的ByteArrayOutputStream用于存储生成的Excel文件数据
                ByteArrayOutputStream byteArrayOutputStream1 = new ByteArrayOutputStream();
                EasyExcel.write(byteArrayOutputStream1, TgCustomsClearanceExport1Result.class).sheet("invoice").doWrite(invoiceList);
                String excelName = "invoice" + "_" +detail.getSplitOrder() + ".csv";
                zipOut.putNextEntry(new ZipEntry(excelName));
                zipOut.write(byteArrayOutputStream1.toByteArray());

            }
        }
        zipOut.closeEntry();
        zipOut.finish();
        zipOut.close();
        outputStream.flush();
        outputStream.close();
        log.info("生成清关发票（快递）结束，耗时------------------->" + (System.currentTimeMillis() - start) + "ms");
    }

    /**
     * 处理清关发票快递模板数据
     * @param param
     * @param export0
     * @param orderType
     * @param export0List
     * @param export1List
     * @param invoiceList
     * @return
     * @throws MalformedURLException
     */
    private ResponseData dealClearanceExportKD(TgCustomsClearanceParam param, TgCustomsClearanceExport0Result export0, String orderType,
                                             List<TgCustomsClearanceExport0ListResult> export0List,
                                               List<TgCustomsClearanceExport1Result> export1List,
                                               List<TgCustomsClearanceExport1Result> invoiceList) throws MalformedURLException {
        DecimalFormat twoDf = new DecimalFormat("0.00");

        //获取清关发票基本信息
        List<TgCustomsClearanceExport0ListResult> clearanceInfoList = mapper.getClearanceInfo(param);
        List<String> customsTaxRateEmptyList = new ArrayList();
        List<String> indirectRateEmptyList = new ArrayList();
        clearanceInfoList.stream().forEach(i -> {
            if(ObjUtil.isNull(i.getTaxRate()) || ObjUtil.isNull(i.getChangeTaxRate()) || ObjUtil.isNull(i.getAddTaxRate())){
                customsTaxRateEmptyList.add(i.getMainMaterialCode());
            }
            if(ObjUtil.isNull(i.getIndirectRate())){
                indirectRateEmptyList.add(i.getMainMaterialCode());
            }
        });
        if(CollectionUtil.isNotEmpty(customsTaxRateEmptyList)){
            return ResponseData.error(StringUtils.join(customsTaxRateEmptyList, ',') + "清关税率为空，请先维护清关税率");
        }
        if(CollectionUtil.isNotEmpty(indirectRateEmptyList)){
            return ResponseData.error("ERP固定汇率不存在！");
        }
        Boolean customsUnitPriceEmpty = clearanceInfoList.stream().anyMatch(i -> ObjUtil.isNull(i.getCustomsUnitPrice()));
        if(customsUnitPriceEmpty){
            return ResponseData.error("清关单价为空，请先维护清关单价");
        }

        //动态维度合并清关发票（出货清单号+箱号+动态根据手动清关产品合并、系统清关产品合并）
        Map<String, List<TgCustomsClearanceExport0ListResult>> clearanceInfoListMap = clearanceInfoList.stream().collect(Collectors.groupingBy(item ->{
            item.setGroupStr(item.getMergeId() == null ? item.getId() + "_" : item.getMergeId() + "_" + item.getClearMergeStatus());
            return item.getPackCode() + item.getBoxNo() + item.getGroupStr();
        }, LinkedHashMap::new, Collectors.toList()));
        List<TgCustomsClearanceExport0ListResult> clearanceGroupList = new ArrayList<>();
        for (Map.Entry<String, List<TgCustomsClearanceExport0ListResult>> entry : clearanceInfoListMap.entrySet()) {
            List<TgCustomsClearanceExport0ListResult> groupDetailList = entry.getValue();
            BigDecimal totalQuantity = groupDetailList.stream().map(TgCustomsClearanceExport0ListResult :: getQuantity).reduce(BigDecimal.ZERO, BigDecimal :: add);
            BigDecimal minPrice = groupDetailList.stream().map(TgCustomsClearanceExport0ListResult :: getCustomsUnitPrice).reduce(groupDetailList.get(0).getCustomsUnitPrice(), BigDecimal :: min);
            BigDecimal totalPrice = groupDetailList.stream().map(TgCustomsClearanceExport0ListResult :: getCustomsUnitPrice).reduce(BigDecimal.ZERO, BigDecimal :: add);
            TgCustomsClearanceExport0ListResult group = groupDetailList.get(0);//已经分组了，获取第1条数据就好了
            group.setTotalQuantity(totalQuantity);//维度分组总数量
            group.setTotalCount(new BigDecimal(groupDetailList.size()));//记录条数
            group.setTotalPrice(totalPrice);//维度分组总价
            group.setUnitPriceMin(minPrice);//维度分组最低价
            clearanceGroupList.add(group);
        }

        //二次维度合并清关发票（箱子维度+报关英文品名+报关英文材质+HSCODE合并）
        List<TgCustomsClearanceExport0ListResult> groupList = new ArrayList<>();
        Map<String, List<TgCustomsClearanceExport0ListResult>> boxDetailListMap = clearanceGroupList.stream()
                .collect(Collectors.groupingBy(item -> item.getPackCode() + item.getBoxNo() + item.getInvoiceProNameEn() + item.getClearMaterialEn() + item.getHscode(), LinkedHashMap::new, Collectors.toList()));
        for (Map.Entry<String, List<TgCustomsClearanceExport0ListResult>> entry : boxDetailListMap.entrySet()) {
            List<TgCustomsClearanceExport0ListResult> groupDetailList = entry.getValue();
            BigDecimal totalQuantity = groupDetailList.stream().map(TgCustomsClearanceExport0ListResult :: getTotalQuantity).reduce(BigDecimal.ZERO, BigDecimal :: add);
            BigDecimal minPrice = groupDetailList.stream().map(TgCustomsClearanceExport0ListResult :: getUnitPriceMin).reduce(groupDetailList.get(0).getUnitPriceMin(), BigDecimal :: min);
            BigDecimal totalPrice = groupDetailList.stream().map(TgCustomsClearanceExport0ListResult :: getTotalPrice).reduce(BigDecimal.ZERO, BigDecimal :: add);
            BigDecimal totalCount = groupDetailList.stream().map(TgCustomsClearanceExport0ListResult :: getTotalCount).reduce(BigDecimal.ZERO, BigDecimal :: add);
            TgCustomsClearanceExport0ListResult group = groupDetailList.get(0);//已经分组了，获取第1条数据就好了
            group.setTotalQuantity(totalQuantity);//维度分组总数量
            group.setTotalCount(totalCount);//维度分组记录条数
            group.setTotalPrice(totalPrice);//维度分组总价
            group.setUnitPriceMin(minPrice);//维度分组最低价
            groupList.add(group);
        }

        //最终维度合并清关发票（报关英文品名+报关英文材质+HSCODE合并）
        Map<String, List<TgCustomsClearanceExport0ListResult>> detailListMap = groupList.stream()
                .collect(Collectors.groupingBy(item -> item.getInvoiceProNameEn() + item.getClearMaterialEn() + item.getHscode(), LinkedHashMap::new, Collectors.toList()));
        Integer no = 1;
        //总价合计
        BigDecimal sumTotalAmount = BigDecimal.ZERO;
        for (Map.Entry<String, List<TgCustomsClearanceExport0ListResult>> entry : detailListMap.entrySet()) {
            List<TgCustomsClearanceExport0ListResult> groupDetailList = entry.getValue();
            BigDecimal totalQuantity = groupDetailList.stream().map(TgCustomsClearanceExport0ListResult :: getTotalQuantity).reduce(BigDecimal.ZERO, BigDecimal :: add);
            BigDecimal minPrice = groupDetailList.stream().map(TgCustomsClearanceExport0ListResult :: getUnitPriceMin).reduce(groupDetailList.get(0).getUnitPriceMin(), BigDecimal :: min);
            BigDecimal totalPrice = groupDetailList.stream().map(TgCustomsClearanceExport0ListResult :: getTotalPrice).reduce(BigDecimal.ZERO, BigDecimal :: add);
            BigDecimal totalCount = groupDetailList.stream().map(TgCustomsClearanceExport0ListResult :: getTotalCount).reduce(BigDecimal.ZERO, BigDecimal :: add);
            TgCustomsClearanceExport0ListResult group = groupDetailList.get(0);//已经分组了，获取第1条数据就好了
            group.setTotalQuantity(totalQuantity);//维度分组总数量
            group.setTotalCount(totalCount);//维度分组记录条数
            group.setTotalPrice(totalPrice);//维度分组总价
            group.setUnitPriceMin(minPrice);//维度分组最低价
            if("0".equals(orderType)){
                group.setUnitPrice(minPrice);
                group.setTotalAmount(totalQuantity.multiply(minPrice));
            }
            if("1".equals(orderType)){
                BigDecimal avgPrice = totalPrice.divide(totalCount, 2, BigDecimal.ROUND_HALF_UP);
                group.setUnitPrice(avgPrice);
                group.setTotalAmount(totalQuantity.multiply(avgPrice));
            }
            group.setUnitPriceStr(twoDf.format(group.getUnitPrice()));
            group.setTotalAmountStr(twoDf.format(group.getTotalAmount()));
            sumTotalAmount = sumTotalAmount.add(group.getTotalAmount());
            group.setIsMergeUpCell("1");//向左合并单元格
            group.setBoxNum(new BigDecimal(groupDetailList.size()));
            group.setNo(no.toString());
            no ++;
            export0List.add(group);
        }
        export0.setSumTotalAmountStr(twoDf.format(sumTotalAmount));
        //处理最后一行且最后一单元格的数据，需删除合并标识单元格
        TgCustomsClearanceExport0ListResult lastData0 = export0List.get(export0List.size() - 1);
        lastData0.setIsMergeUpCell("-1");

        //sheet1数据
        //维度合并清单（动态根据手动清关产品合并、系统清关产品合并）
        Map<String, List<TgCustomsClearanceExport0ListResult>> clearancePriceListMap = clearanceInfoList.stream().collect(
                Collectors.groupingBy(TgCustomsClearanceExport0ListResult :: getGroupStr, LinkedHashMap::new, Collectors.toList()));
        List<TgCustomsClearanceExport1Result> groupSheet1List = new ArrayList<>();
        for (Map.Entry<String, List<TgCustomsClearanceExport0ListResult>> entry : clearancePriceListMap.entrySet()) {
            List<TgCustomsClearanceExport0ListResult> groupDetailList = entry.getValue();
            BigDecimal minPrice = groupDetailList.stream().map(TgCustomsClearanceExport0ListResult :: getCustomsUnitPrice).reduce(groupDetailList.get(0).getCustomsUnitPrice(), BigDecimal :: min);
            BigDecimal totalPrice = groupDetailList.stream().map(TgCustomsClearanceExport0ListResult :: getCustomsUnitPrice).reduce(BigDecimal.ZERO, BigDecimal :: add);
            BigDecimal totalQuantity = groupDetailList.stream().map(TgCustomsClearanceExport0ListResult :: getQuantity).reduce(BigDecimal.ZERO, BigDecimal :: add);
            //sheet1清关合并明细ID集合
            List<BigDecimal> detailIdList = groupDetailList.stream()
                    .sorted(Comparator.comparing(TgCustomsClearanceExport0ListResult :: getId))
                    .map(TgCustomsClearanceExport0ListResult :: getId).collect(Collectors.toList());
            TgCustomsClearanceExport0ListResult minGroup = groupDetailList.stream().min(Comparator.comparing(TgCustomsClearanceExport0ListResult :: getCustomsUnitPrice)).get();//已经分组了，取价格最低的数据报关
            TgCustomsClearanceExport1Result sheet1 = new TgCustomsClearanceExport1Result();
            sheet1.setClearNameCn(minGroup.getClearNameCn());
            sheet1.setInvoiceProNameEn(minGroup.getInvoiceProNameEn());
            sheet1.setClearMaterialEn(minGroup.getClearMaterialEn());
            sheet1.setHscode(minGroup.getHscode());
            sheet1.setMainMaterialCode(minGroup.getMainMaterialCode());//invoice需要用到
            sheet1.setUnit(minGroup.getUnit());//invoice需要用到
            sheet1.setCurrency(minGroup.getCurrency());
            sheet1.setImportTaxNum(minGroup.getImportTaxNum());
            sheet1.setSortCondition(minGroup.getClearNameCn() + minGroup.getInvoiceProNameEn() + minGroup.getClearMaterialEn());
            sheet1.setTotalQuantity(totalQuantity);
            sheet1.setUnitPriceMin(minPrice);
            sheet1.setTotalPrice(totalPrice);
            sheet1.setTotalCount(new BigDecimal(groupDetailList.size()));
            sheet1.setDetailIdList(detailIdList);
            groupSheet1List.add(sheet1);
        }

        //二次维度合并清单（清关品名+报关英文品名+报关英文材质+HSCODE合并）
        Map<String, List<TgCustomsClearanceExport1Result>> secondGroupListMap = groupSheet1List.stream().collect(
                Collectors.groupingBy(item -> item.getClearNameCn() + item.getInvoiceProNameEn() + item.getClearMaterialEn() + item.getHscode(), LinkedHashMap::new, Collectors.toList()));
        List<TgCustomsClearanceExport1Result> secondGroupList = new ArrayList<>();
        for (Map.Entry<String, List<TgCustomsClearanceExport1Result>> entry : secondGroupListMap.entrySet()) {
            List<TgCustomsClearanceExport1Result> groupDetailList = entry.getValue();
            BigDecimal unitPriceMin = groupDetailList.stream().map(TgCustomsClearanceExport1Result :: getUnitPriceMin).reduce(groupDetailList.get(0).getUnitPriceMin(), BigDecimal :: min);
            BigDecimal totalPrice = groupDetailList.stream().map(TgCustomsClearanceExport1Result :: getTotalPrice).reduce(BigDecimal.ZERO, BigDecimal :: add);
            BigDecimal totalQuantity = groupDetailList.stream().map(TgCustomsClearanceExport1Result :: getTotalQuantity).reduce(BigDecimal.ZERO, BigDecimal :: add);
            BigDecimal totalCount = groupDetailList.stream().map(TgCustomsClearanceExport1Result :: getTotalCount).reduce(BigDecimal.ZERO, BigDecimal :: add);
            //sheet1清关合并明细ID集合
            List<BigDecimal> detailIdList = new ArrayList<>();
            groupDetailList.stream().forEach(i ->{
                detailIdList.addAll(i.getDetailIdList());
            });
            TgCustomsClearanceExport1Result sheet1 = groupDetailList.get(0);
            sheet1.setTotalQuantity(totalQuantity);
            sheet1.setUnitPriceMin(unitPriceMin);
            sheet1.setTotalPrice(totalPrice);
            sheet1.setTotalCount(totalCount);
            if("0".equals(orderType)){
                sheet1.setUnitPrice(unitPriceMin);
                sheet1.setTotalAmount(totalQuantity.multiply(unitPriceMin));
            }
            if("1".equals(orderType)){
                BigDecimal avgPrice = totalPrice.divide(totalCount, 2, BigDecimal.ROUND_HALF_UP);
                sheet1.setUnitPrice(avgPrice);
                sheet1.setTotalAmount(totalQuantity.multiply(avgPrice));
            }
            sheet1.setDetailIdList(detailIdList);
            secondGroupList.add(sheet1);
        }

        //处理invoice
        invoiceList.addAll(secondGroupList);
        for (TgCustomsClearanceExport1Result invoice : invoiceList) {
            invoice.setUnitPriceStr(twoDf.format(invoice.getUnitPrice()));
            invoice.setOriginCountry("CN");
            invoice.setIntlCurrency("USD");
            invoice.setDefaultCurrency("USD");
            invoice.setFreight(twoDf.format(export0.getTransportCost()));
            invoice.setCreateInvoice("Y");
            invoice.setPaperlessInvoice("Y");
            invoice.setPrintCopyPI("Y");
        }

        //组合sheet1数据
        Map<BigDecimal, List<TgCustomsClearanceExport0ListResult>> clearanceMap = clearanceInfoList.stream().collect(
                Collectors.groupingBy(TgCustomsClearanceExport0ListResult :: getId, LinkedHashMap::new, Collectors.toList()));
        for (TgCustomsClearanceExport1Result groupDetail : secondGroupList) {
            List<BigDecimal> detailIdList = groupDetail.getDetailIdList();
            for (int i = 0; i < detailIdList.size(); i++) {
                TgCustomsClearanceExport1Result export1 = new TgCustomsClearanceExport1Result();
                BeanUtils.copyProperties(groupDetail, export1);
                if(i+1 == detailIdList.size()){
                    export1.setIsMergeUpCell("0");
                }else{
                    export1.setIsMergeUpCell("1");
                }
                TgCustomsClearanceExport0ListResult result = clearanceMap.get(detailIdList.get(i)).get(0);
                export1.setK3Code(result.getSku());
                export1.setAmazonSaleLink(result.getAmazonSaleLink());
                export1.setAmazonPictureLink(result.getAmazonPictureLink());
                export1.setAmazonPictureUrl(export1.getAmazonPictureLink() == null ? null : new URL(export1.getAmazonPictureLink()));
                export1.setAmazonSalePrice(result.getAmazonSalePrice());
                if(export1.getAmazonSalePrice() != null){
                    export1.setAmazonSalePriceStr(twoDf.format(export1.getAmazonSalePrice()));
                }else{
                    export1.setAmazonSalePriceStr("");
                }
                export1.setTotalClearanceQuantity(result.getQuantity());
                export1.setClearanceUnitPrice(result.getCustomsUnitPrice());
                export1.setClearanceUnitPriceStr(twoDf.format(export1.getClearanceUnitPrice()));
                export1.setTotalClearanceAmount(result.getQuantity().multiply(result.getCustomsUnitPrice()));
                export1.setTotalClearanceAmountStr(twoDf.format(export1.getTotalClearanceAmount()));
                export1.setChangeTaxRate(export1.getTotalClearanceAmount().multiply(result.getChangeTaxRate()));
                export1.setChangeTaxRateStr(twoDf.format(export1.getChangeTaxRate()));
                export1.setTaxRate(export1.getTotalClearanceAmount().multiply(result.getTaxRate()));
                export1.setTaxRateStr(twoDf.format(export1.getTaxRate()));
                export1.setAddTaxRate(export1.getTotalClearanceAmount().multiply(result.getAddTaxRate()));
                export1.setAddTaxRateStr(twoDf.format(export1.getAddTaxRate()));
                export1.setUnitPriceStr(twoDf.format(export1.getUnitPrice()));
                export1.setTotalAmountStr(twoDf.format(export1.getTotalAmount()));
                export1List.add(export1);
            }
        }
        //处理最后一行且最后一单元格的数据，需删除合并标识单元格
        TgCustomsClearanceExport1Result lastData = export1List.get(export1List.size() - 1);
        lastData.setIsMergeUpCell("-1");
        return ResponseData.success();
    }

    @Override
    @DataSource(name = "logistics")
    @Transactional(rollbackFor = Exception.class)
    public ResponseData salePriceEdit(TgCustomsClearanceDetailParam param) {
        log.info("清关单销售价编辑保存入参[{}]", JSONObject.toJSON(param));
        if(StringUtils.isBlank(String.valueOf(param.getId()))){
            return ResponseData.error("编辑ID为空！");
        }
        TgCustomsClearanceDetail clearanceDetail = clearanceDetailService.getById(param.getId());
        if(clearanceDetail == null){
            return ResponseData.error("未查询到此清关发票明细信息！");
        }
        if(param.getAmazonSalePrice() == null){
            return ResponseData.error("清关发票明细销售价格不能为空！");
        }
        clearanceDetail.setAmazonSalePrice(param.getAmazonSalePrice());

        TgCustomsClearance clearance = mapper.selectById(clearanceDetail.getPid());
        if(clearance == null){
            return ResponseData.error("未查询到此清关发票信息！");
        }

        if(StringUtils.isNotBlank(clearance.getPriceType()) || clearance.getCustomsCoeff() != null){
            //取主表清关折算率且价格类型为销售价，计算清关单价
            if(TgPriceTypeEnum.SALE.getName().equals(clearance.getPriceType())){
                ResponseData rd = calculateCustomsUnitPrice(clearance, clearanceDetail, null);
                if(ResponseData.DEFAULT_ERROR_CODE.equals(rd.getCode())){
                    return rd;
                }
            }
        } else {
            //查询清关产品合并
            LambdaQueryWrapper<TgBaseProduct> mergeWrapper = new LambdaQueryWrapper<>();
            mergeWrapper.eq(TgBaseProduct :: getDataType, TgDataTypeEnum.MERGE.getCode())
                    .like(TgBaseProduct :: getMaterialCode, clearanceDetail.getMaterialCode());
            TgBaseProduct mergeProduct = productService.getOne(mergeWrapper);

            //查询产品基本信息
            LambdaQueryWrapper<TgBaseProduct> productWrapper = new LambdaQueryWrapper<>();
            productWrapper.eq(TgBaseProduct :: getDataType, TgDataTypeEnum.NOT_MERGE.getCode())
                    .eq(TgBaseProduct :: getMaterialCode, clearanceDetail.getMaterialCode());
            TgBaseProduct baseProduct = productService.getOne(productWrapper);

            if(baseProduct == null){
                return ResponseData.error("未查询到" + clearanceDetail.getMaterialCode() + "对应的产品基本信息！");
            }

            //如果没有合并的信息则取未合并的
            if(mergeProduct == null){
                mergeProduct = baseProduct;
            }

            //清关产品合并明细：HSCode
            LambdaQueryWrapper<TgBaseProductDetail> mergeDetailWrapper = new LambdaQueryWrapper<>();
            mergeDetailWrapper.eq(TgBaseProductDetail :: getPid, mergeProduct.getId())
                    .eq(TgBaseProductDetail :: getCountryCode, clearance.getArrivalCountryCode());
            TgBaseProductDetail mergeDetail = productDetailService.getOne(mergeDetailWrapper);
            if(mergeDetail == null){
                return ResponseData.error("未查询到物料：" + clearanceDetail.getMaterialCode() + "，国家：" + clearance.getArrivalCountryName() + "对应的清关产品明细信息！");
            }

            //根据国家和HsCode获取清关税率的流转税率、关税率、附加税率
            LambdaQueryWrapper<TgCustomsTaxRate> taxRateWrapper = new LambdaQueryWrapper<>();
            taxRateWrapper.eq(TgCustomsTaxRate :: getCountryCode, mergeDetail.getCountryCode())
                    .eq(TgCustomsTaxRate :: getHsCode, mergeDetail.getHsCode());
            TgCustomsTaxRate customsTaxRate = tgCustomsTaxRateService.getOne(taxRateWrapper);
            if(customsTaxRate == null){
                return ResponseData.error("未查询到国家：" + mergeDetail.getCountryCode() + "，HSCode：" + mergeDetail.getHsCode() + "对应的清关税率！");
            }

            //根据运抵国和HSCode获取清关价格折算规则，没有则用运抵国查询
            TgCustomsPriceCoeffRuleParam priceCoeffParam = new TgCustomsPriceCoeffRuleParam();
            priceCoeffParam.setCountryCode(clearance.getArrivalCountryCode());
            priceCoeffParam.setHsCode(clearanceDetail.getHscode());
            priceCoeffParam.setTaxRate(customsTaxRate.getTaxRate());
            TgCustomsPriceCoeffRule priceCoeff = customsPriceCoeffRuleService.queryPriceCoeff(priceCoeffParam);
            if(priceCoeff == null){
                //根据运抵国获取清关价格折算规则
                priceCoeffParam.setTaxRate(null);
                priceCoeff = customsPriceCoeffRuleService.queryPriceCoeff(priceCoeffParam);
                if(priceCoeff == null){
                    return ResponseData.error("未查询到国家：" + clearance.getArrivalCountryName() + "，HSCode：" + clearanceDetail.getHscode() + "对应的清关价格折算信息！");
                }
            }

            if(TgPriceTypeEnum.SALE.getName().equals(priceCoeff.getPriceType())){
                //计算清关单价
                ResponseData rd = calculateCustomsUnitPrice(clearance, clearanceDetail, mergeDetail);
                if(ResponseData.DEFAULT_ERROR_CODE.equals(rd.getCode())){
                    return rd;
                }
            }
        }

        TgCustomsClearanceDetail updateDetail = new TgCustomsClearanceDetail();
        updateDetail.setId(clearanceDetail.getId());
        updateDetail.setAmazonSalePrice(clearanceDetail.getAmazonSalePrice());
        updateDetail.setCustomsUnitPrice(clearanceDetail.getCustomsUnitPrice());
        updateDetail.setEditStatus("人工编辑");
        updateDetail.setUpdateTime(DateUtil.date());
        updateDetail.setUpdateUser(LoginContext.me().getLoginUser().getName());
        clearanceDetailService.updateById(updateDetail);
        return ResponseData.success();
    }

    @Override
    @DataSource(name = "logistics")
    public ResponseData clearMergePreview(TgCustomsClearanceDetailParam param) {
        return ResponseData.success(mapper.clearMergePreview(param));
    }

    @Override
    @DataSource(name = "logistics")
    public ResponseData clearMergeSplit(TgCustomsClearanceDetailParam param) {
        if(param.getPid() == null){
            return ResponseData.error("清关发票ID入参为空！");
        }
        if(StringUtils.isBlank(param.getDetailIds())){
            return ResponseData.error("清关发票明细ID入参为空！");
        }
        String[] detailIdArr = param.getDetailIds().split(",");
        param.setDetailIdList(Arrays.asList(detailIdArr));

        LambdaUpdateWrapper<TgCustomsClearanceDetail> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(TgCustomsClearanceDetail :: getPid, param.getPid())
                .in(TgCustomsClearanceDetail :: getId, param.getDetailIdList())
                .set(TgCustomsClearanceDetail :: getSpecialMergeSign, "特殊合并")
                .set(TgCustomsClearanceDetail :: getSpecialMaterialCode, "")
                .set(TgCustomsClearanceDetail :: getSpecialMaterialId, "")
                .set(TgCustomsClearanceDetail :: getUpdateUser, LoginContext.me().getLoginUser().getName())
                .set(TgCustomsClearanceDetail :: getUpdateTime, DateUtil.date());
        clearanceDetailService.update(null, updateWrapper);
        return ResponseData.success();
    }

    @Override
    @DataSource(name = "logistics")
    public ResponseData clearMerge(List<TgCustomsClearanceDetailResult> param) {
        if(CollectionUtil.isEmpty(param)){
            return ResponseData.error("入参集合为空！");
        }
        List<BigDecimal> detailIdList = new ArrayList<>();
        String mainMaterialCode = null;
        BigDecimal specialMaterialId = null;
        for (TgCustomsClearanceDetailResult mergeDetail : param) {
            //勾选为合并主数据
            if(mergeDetail.getIsSelect()){
                TgCustomsClearanceDetail mainDetail = clearanceDetailService.getById(mergeDetail.getId());
                if(mainDetail == null){
                    return ResponseData.error("未查询到" + mergeDetail.getId() + "清关发票明细信息！");
                }
                mainMaterialCode = mainDetail.getMaterialCode();
                specialMaterialId = mainDetail.getId();
            }
            detailIdList.add(mergeDetail.getId());
        }

        LambdaUpdateWrapper<TgCustomsClearanceDetail> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.in(TgCustomsClearanceDetail :: getId, detailIdList)
                .set(TgCustomsClearanceDetail :: getSpecialMergeSign, "特殊合并")
                .set(TgCustomsClearanceDetail :: getSpecialMaterialCode, mainMaterialCode)
                .set(TgCustomsClearanceDetail :: getSpecialMaterialId, specialMaterialId)
                .set(TgCustomsClearanceDetail :: getUpdateUser, LoginContext.me().getLoginUser().getName())
                .set(TgCustomsClearanceDetail :: getUpdateTime, DateUtil.date());
        clearanceDetailService.update(null, updateWrapper);
        return ResponseData.success();
    }

    @Override
    @DataSource(name = "logistics")
    public ResponseData selectClearMerge(TgCustomsClearanceDetailParam param) {
        if(param.getPid() == null){
            return ResponseData.error("清关发票ID入参为空！");
        }
        if(StringUtils.isBlank(param.getDetailIds())){
            return ResponseData.error("清关发票明细ID入参为空！");
        }
        String[] detailIdArr = param.getDetailIds().split(",");
        param.setDetailIdList(Arrays.asList(detailIdArr));
        return ResponseData.success(mapper.selectClearMerge(param));
    }

    @Override
    @DataSource(name = "logistics")
    public ResponseData specialClearMergeList(List<TgCustomsClearanceDetailResult> param) {
        if(CollectionUtil.isEmpty(param)){
            return ResponseData.error("入参集合为空！");
        }
        BigDecimal pid = null;
        List<String> detailIdList = new ArrayList<>();
        for (TgCustomsClearanceDetailResult mergeDetail : param) {
            pid = mergeDetail.getPid();
            String[] detailIdArr = mergeDetail.getDetailIds().split(",");
            detailIdList.addAll(Arrays.asList(detailIdArr));
        }
        if(pid == null){
            return ResponseData.error("清关发票ID入参为空！");
        }
        if(CollectionUtil.isEmpty(detailIdList)){
            return ResponseData.error("清关发票明细ID入参为空！");
        }
        TgCustomsClearanceDetailParam selectParam = new TgCustomsClearanceDetailParam();
        selectParam.setPid(pid);
        selectParam.setDetailIdList(detailIdList);
        return ResponseData.success(mapper.selectClearMerge(selectParam));
    }

    @Override
    @Transactional
    @DataSource(name = "logistics")
    public ResponseData getSaihuProduct(SaihuShop param){
        //根据shopId维度获取赛狐在线产品并入库
        List<SaihuShop> saihuShopList;
        if(param.getShopId() == null){
            saihuShopList = clearanceService.getSaihuShop();
        } else {
            saihuShopList = new ArrayList<>();
            SaihuShop saihuShop = new SaihuShop();
            saihuShop.setShopId(param.getShopId());
            saihuShop.setName(param.getName());
            saihuShop.setShopName(param.getShopName());
            saihuShop.setSite(param.getSite());
            saihuShopList.add(saihuShop);
        }
        for (SaihuShop shShop : saihuShopList) {
            if(shShop.getSite().contains("(")){
                shShop.setSite(shShop.getSite().substring(0, shShop.getSite().indexOf("(")));
            }

            LambdaQueryWrapper<TgSaihuProduct> qw = new LambdaQueryWrapper<>();
            qw.eq(TgSaihuProduct :: getShopId, shShop.getShopId());
            saihuProductService.remove(qw);

            //默认第一页
            int pageNo = 1;
            int pageSize = 200;
            Integer totalPage = 0;
            do{
                SaiHuProductParam productParam = new SaiHuProductParam();
                productParam.setPageNo(String.valueOf(pageNo));
                productParam.setPageSize(String.valueOf(pageSize));
                productParam.setShopIdList(Arrays.asList(shShop.getShopId()));
                Date curDate = DateUtil.date();
                //增量查询增加修改时间入参，默认取前3天数据
                /*Date beforeDate = DateUtil.offsetDay(curDate, -3);
                productParam.setModifiedTimeStart(DateUtil.format(DateUtil.beginOfDay(beforeDate), DatePattern.NORM_DATETIME_PATTERN));
                productParam.setModifiedTimeEnd(DateUtil.format(curDate, DatePattern.NORM_DATETIME_PATTERN));*/
                try {
                    SaiHuBaseResult result = saiHuSellConsumer.productList(productParam);
                    if(SaiHuBaseResult.DEFAULT_SUCCESS_CODE.equals(result.getCode())){
                        String jsonDateObject = JSON.toJSONString(result.getData());
                        SaiHuPageDataResult pageData = JSONObject.parseObject(jsonDateObject, SaiHuPageDataResult.class);
                        totalPage = Integer.parseInt(pageData.getTotalPage());
                        List<Object> rows = pageData.getRows();

                        for (Object obj : rows) {
                            String jsonObject = JSON.toJSONString(obj);
                            TgSaihuProduct saihuProduct = JSONObject.parseObject(jsonObject, TgSaihuProduct.class);
                            saihuProduct.setUpdateTime(curDate);
                            saihuProduct.setName(shShop.getName());
                            saihuProduct.setShopName(shShop.getShopName());
                            saihuProduct.setSite(shShop.getSite());
                            saihuProductService.save(saihuProduct);
                            /*LambdaUpdateWrapper<TgSaihuProduct> uw = new LambdaUpdateWrapper<>();
                            uw.eq(TgSaihuProduct :: getShopName, saihuProduct.getShopName())
                                    .eq(TgSaihuProduct :: getSite, saihuProduct.getSite())
                                    .eq(TgSaihuProduct :: getSku, saihuProduct.getSku());
                            saihuProductService.saveOrUpdate(saihuProduct, uw);*/
                        }
                        pageNo ++;
                    }else if(result.getCode() == 40019){
                        //调用超过限制，阻塞5S
                        Thread.sleep(5000);
                    }
                } catch (Exception e){
                    pageNo ++;
                    log.error("获取赛狐在线产品异常，shopId[{}]，当前页数[{}]，分页条数[{}]，异常信息[{}]", shShop.getShopId(), pageNo, pageSize, e.getMessage());
                }
            } while (pageNo <= totalPage);//小于等于总页数时循环获取数据
        }
        return ResponseData.success();
    }

    @Override
    @DataSource(name = "external")
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public List<SaihuShop> getSaihuShop(){
        return mapper.getSaihuShop();
    }

}
