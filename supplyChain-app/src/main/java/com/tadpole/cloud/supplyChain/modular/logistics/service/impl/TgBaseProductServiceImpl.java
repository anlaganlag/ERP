package com.tadpole.cloud.supplyChain.modular.logistics.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.Validator;
import cn.stylefeng.guns.cloud.libs.authority.column.annotation.DatalimitColumn;
import cn.stylefeng.guns.cloud.libs.context.auth.LoginContext;
import cn.stylefeng.guns.cloud.model.excel.listener.BaseExcelListener;
import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import cn.stylefeng.guns.cloud.system.core.dbs.annotation.DataSource;
import com.alibaba.excel.EasyExcel;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tadpole.cloud.externalSystem.api.lingxing.model.req.sell.ListingReq;
import com.tadpole.cloud.externalSystem.api.lingxing.model.resp.LingXingBaseRespData;
import com.tadpole.cloud.externalSystem.api.lingxing.model.resp.sell.ListingResp;
import com.tadpole.cloud.platformSettlement.api.finance.entity.CwLingxingShopInfo;
import com.tadpole.cloud.supplyChain.api.logistics.entity.*;
import com.tadpole.cloud.supplyChain.api.logistics.model.params.*;
import com.tadpole.cloud.supplyChain.api.logistics.model.result.*;
import com.tadpole.cloud.supplyChain.modular.logistics.consumer.LingXingSellConsumer;
import com.tadpole.cloud.supplyChain.modular.logistics.enums.*;
import com.tadpole.cloud.supplyChain.modular.logistics.mapper.TgBaseProductMapper;
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
import java.io.*;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>
 * 通关产品基础信息 服务实现类
 * </p>
 *
 * @author ty
 * @since 2023-05-22
 */
@Slf4j
@Service
public class TgBaseProductServiceImpl extends ServiceImpl<TgBaseProductMapper, TgBaseProduct> implements ITgBaseProductService {

    @Resource
    private TgBaseProductMapper mapper;
    @Autowired
    private ITgBaseProductService service;
    @Autowired
    private ITgBaseProductDetailService tgBaseProductDetailService;
    @Autowired
    private ITgCustomsTaxRateService tgCustomsTaxRateService;
    @Autowired
    private ITgLxListingInfoService tgLxListingInfoService;
    @Autowired
    private LingXingSellConsumer lingXingSellConsumer;
    @Autowired
    private ITgCountryInfoService countryInfoService;
    @Autowired
    private ITgCustomsEssentialFactorService customsEssentialFactorService;

    @Override
    @DataSource(name = "logistics")
    public ResponseData queryPage(TgBaseProductParam param) {
        return ResponseData.success(mapper.queryPage(param.getPageContext(), param));
    }

    @Override
    @DataSource(name = "logistics")
    @DatalimitColumn(clazz = List.class, name = "数据列过滤")
    public List<TgBaseProductResult> export(TgBaseProductParam param) {
        return mapper.export(param);
    }

    @Override
    @DataSource(name = "logistics")
    public List<TgBaseProductApplyResult> exportCustomsApply(TgBaseProductParam param) {
        return mapper.exportCustomsApply(param);
    }

    @Override
    @DataSource(name = "logistics")
    public List<TgBaseProductClearResult> exportCustomsClear(TgBaseProductParam param) {
        return mapper.exportCustomsClear(param);
    }

    @Override
    @DataSource(name = "logistics")
    public ResponseData importExcel(MultipartFile file) {
        String name = LoginContext.me().getLoginUser().getName();
        BufferedInputStream buffer = null;
        try {
            buffer = new BufferedInputStream(file.getInputStream());
            BaseExcelListener listener = new BaseExcelListener<TgBaseProductParam>();
            EasyExcel.read(buffer, TgBaseProductParam.class, listener).sheet().doRead();

            List<TgBaseProductParam> dataList = listener.getDataList();
            log.info("产品基本信息-导入报关信息数据开始[{}]", JSONObject.toJSON(dataList));
            long start = System.currentTimeMillis();
            if(CollectionUtil.isEmpty(dataList)){
                return ResponseData.error("导入数据为空，导入失败！");
            }
            Set<String> repeat = new HashSet<>();
            Set<String> mcCountrySet = new HashSet<>();
            for (TgBaseProductParam param : dataList) {
                String repeatStr = param.getMaterialCode();
                if(CollectionUtil.isNotEmpty(mcCountrySet) && mcCountrySet.contains(repeatStr)){
                    repeat.add(repeatStr);
                } else {
                    mcCountrySet.add(repeatStr);
                }
            }
            if(CollectionUtil.isNotEmpty(repeat)){
                return ResponseData.error("导入数据物料编码重复，" + JSONObject.toJSON(repeat) + "，导入失败！");
            }

            List<TgBaseProductParam> errorList = new ArrayList<>();
            this.validExcel(dataList, errorList);

            //正常数据处理
            if(CollectionUtil.isNotEmpty(dataList)){
                for (TgBaseProductParam successParam : dataList) {
                    //更新主表
                    TgBaseProduct baseProduct = new TgBaseProduct();
                    BeanUtils.copyProperties(successParam, baseProduct);
                    baseProduct.setUpdateUser(name);
                    baseProduct.setUpdateTime(DateUtil.date());
                    LambdaUpdateWrapper<TgBaseProduct> updateWrapper = new LambdaUpdateWrapper<>();
                    updateWrapper.eq(TgBaseProduct :: getId, baseProduct.getId());
                    this.saveOrUpdate(baseProduct, updateWrapper);
                }
            }

            //异常数据输出异常Excel文件流
            if(CollectionUtil.isNotEmpty(errorList)){
                String fileName = this.dealImportErrorList(errorList);
                log.info("产品基本信息-导入报关信息数据结束，耗时------------------->" + (System.currentTimeMillis() - start) + "ms");
                return ResponseData.error(ResponseData.DEFAULT_ERROR_CODE, "存在导入失败数据！", fileName);
            } else {
                log.info("产品基本信息-导入报关信息数据结束，耗时------------------->" + (System.currentTimeMillis() - start) + "ms");
                return ResponseData.success();
            }
        } catch (Exception e) {
            log.error("产品基本信息导入报关信息处理异常，导入失败！", e);
            return ResponseData.error("产品基本信息导入报关信息处理异常，导入失败！");
        } finally {
            if(buffer != null){
                try {
                    buffer.close();
                } catch (IOException e) {
                    log.error("产品基本信息导入报关信息关闭流异常", e);
                }
            }
        }
    }

    /**
     * 导入Excel校验
     * @param dataList
     * @param errorList
     */
    private void validExcel(List<TgBaseProductParam> dataList, List<TgBaseProductParam> errorList){
        Iterator<TgBaseProductParam> iterator = dataList.listIterator();

        while(iterator.hasNext()) {
            TgBaseProductParam param = iterator.next();
            param.setUploadRemark(null);
            //验证基础信息
            ResponseData validateResp = this.validateBaseProduct(param);
            if(ResponseData.DEFAULT_ERROR_CODE.equals(validateResp.getCode())){
                param.setUploadRemark(validateResp.getMessage());
                //添加异常数据
                errorList.add(param);
                //移除异常数据
                iterator.remove();
                continue;
            }
        }
    }

    /**
     * 校验产品基本信息入参
     * @param param
     * @return
     */
    ResponseData validateBaseProduct(TgBaseProductParam param){
        if(StringUtils.isAnyBlank(
                param.getMaterialCode(),
                param.getCustomsCode(),
                param.getEssentialFactor()
            )
                || param.getTaxRefund() == null
                || param.getGrossProfitRate() == null
        ){
            return ResponseData.error("所有都为必填项！");
        }
        if(BigDecimal.ZERO.compareTo(param.getTaxRefund()) > 0
                || BigDecimal.ZERO.compareTo(param.getGrossProfitRate()) > 0){
            return ResponseData.error("退税率和毛利率不能为负数！");
        }

        //要素规则校验
        LambdaQueryWrapper<TgCustomsEssentialFactor> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(TgCustomsEssentialFactor :: getCustomsCode, param.getCustomsCode());
        TgCustomsEssentialFactor customsEssentialFactor = customsEssentialFactorService.getOne(queryWrapper);
        if(customsEssentialFactor == null){
            return ResponseData.error("报关要素不存在！");
        }
        //要素框架名称
        List<String> nameList = new ArrayList<>();
        //判断报关要素是否包含品名
        if(!customsEssentialFactor.getEssentialFactor().contains("品名")){
            return ResponseData.error("报关要素【品名】不存在！");
        }
        if(!param.getEssentialFactor().contains("品名")){
            return ResponseData.error("产品基本信息要素【品名】不存在！");
        }
        String[] essentialFactorArr = customsEssentialFactor.getEssentialFactor().split("\\s+");
        for (String essentialFactor : essentialFactorArr) {
            String[] nameArr = essentialFactor.split("\\.");
            nameList.add(nameArr[1]);
        }
        String[] essentialFactorParamArr = param.getEssentialFactor().split("\\|");
        //报关要素在框架内的
        StringBuffer sbIn = new StringBuffer();
        //报关要素在框架之外的
        StringBuffer sbOut = new StringBuffer();
        sbOut.append("|");
        for (String essentialFactorParam : essentialFactorParamArr) {
            if(!essentialFactorParam.contains(":") && !essentialFactorParam.contains("：")){
                log.error("要素格式不正确[{}]", essentialFactorParam);
                return ResponseData.error("要素格式不正确！");
            }
            int idx = essentialFactorParam.indexOf(":");
            if(idx == -1){
                idx = essentialFactorParam.indexOf("：");
            }
            String name = essentialFactorParam.substring(0, idx);
            String val = essentialFactorParam.substring(idx + 1);
            //校验要素名称是否在框架范围内，在框架范围内按照框架排序，未在框架范围内的则添加到框架范围外，不参与排序，不生成报关单
            if(nameList.contains(name)){
                sbIn.append(name).append(":").append(val).append("|");
            }else{
                sbOut.append(name).append(":").append(val).append("|");
            }
        }

        //物料校验
        LambdaQueryWrapper<TgBaseProduct> productWrapper = new LambdaQueryWrapper<>();
        productWrapper.eq(TgBaseProduct :: getDataType, TgDataTypeEnum.NOT_MERGE.getCode())
                .eq(TgBaseProduct :: getMaterialCode, param.getMaterialCode());
        TgBaseProduct baseProduct = mapper.selectOne(productWrapper);
        if(baseProduct == null){
            return ResponseData.error("产品基本信息不存在此物料编码！");
        }
        param.setId(baseProduct.getId());
        param.setMainMaterialCode(baseProduct.getMainMaterialCode());

        param.setEssentialFactor(sbIn.substring(0, sbIn.length()-1));
        String[] essentialFactorParamValidateArr = param.getEssentialFactor().split("\\|");
        if(StringUtils.isBlank(sbOut.substring(1))){
            param.setEssentialFactorTemp("");
        }else{
            param.setEssentialFactorTemp(sbOut.substring(0, sbOut.length()-1));
        }
        //要素按照报关要素排序
        StringBuffer sb = new StringBuffer();
        for (String name : nameList) {
            for (String essentialFactorParam : essentialFactorParamValidateArr) {
                int idx = essentialFactorParam.indexOf(":");
                if(idx == -1){
                    idx = essentialFactorParam.indexOf("：");
                }
                String nameParam = essentialFactorParam.substring(0, idx);
                String valueParam = essentialFactorParam.substring(idx + 1);
                if(name.equals(nameParam)){
                    sb.append(nameParam).append(":").append(valueParam).append("|");
                }
            }
        }
        param.setEssentialFactor(sb.substring(0, sb.length()-1));
        return ResponseData.success();
    }

    /**
     * 校验错误文件流输出
     * @param errorList
     * @return
     */
    private String dealImportErrorList(List<TgBaseProductParam> errorList){
        String filePath = System.getProperty("user.dir") + "/upload/";
        String fileName =  DateUtil.format(new Date(), DatePattern.PURE_DATETIME_MS_FORMAT) + ".xlsx";
        OutputStream out = null;
        try {
            out = new FileOutputStream(filePath + fileName,false);
            EasyExcel.write(out, TgBaseProductParam.class)
                    .sheet("导入异常数据").doWrite(errorList);
        } catch (FileNotFoundException e) {
            log.error("产品基本信息导入异常数据导出异常", e);
        } finally {
            if(out != null){
                try {
                    out.close();
                } catch (IOException e) {
                    log.error("产品基本信息导入异常数据导出流关闭异常", e);
                }
            }
        }
        return fileName;
    }

    @Override
    @DataSource(name = "logistics")
    public ResponseData importDetail(MultipartFile file) {
        String name = LoginContext.me().getLoginUser().getName();
        BufferedInputStream buffer = null;
        try {
            buffer = new BufferedInputStream(file.getInputStream());
            BaseExcelListener listener = new BaseExcelListener<TgBaseProductDetailParam>();
            EasyExcel.read(buffer, TgBaseProductDetailParam.class, listener).sheet().doRead();

            List<TgBaseProductDetailParam> dataList = listener.getDataList();
            log.info("产品基本信息导入清关信息数据开始[{}]", JSONObject.toJSON(dataList));
            long start = System.currentTimeMillis();
            if(CollectionUtil.isEmpty(dataList)){
                return ResponseData.error("导入数据为空，导入失败！");
            }
            if(dataList.size() > 10000){
                return ResponseData.error("导入数据最多支持10000条数据！");
            }
            Set<String> repeat = new HashSet<>();
            Set<String> mcCountrySet = new HashSet<>();
            for (TgBaseProductDetailParam param : dataList) {
                if(param.getMaterialCode() == null){
                    return ResponseData.error("导入数据物料编码不能为空！");
                }
                String repeatStr = "物料编码：" + param.getMaterialCode();
                if(CollectionUtil.isNotEmpty(mcCountrySet) && mcCountrySet.contains(repeatStr)){
                    repeat.add(repeatStr);
                } else {
                    mcCountrySet.add(repeatStr);
                }
            }
            if(CollectionUtil.isNotEmpty(repeat)){
                return ResponseData.error("导入数据物料编码重复，" + JSONObject.toJSON(repeat) + "，导入失败！");
            }

            //国家编码查询
            List<TgCountryInfo> tgCountryInfoList = countryInfoService.list();
            Map<String, List<TgCountryInfo>> groupMap = tgCountryInfoList.stream().collect(Collectors.groupingBy(TgCountryInfo :: getCountryNameCn, LinkedHashMap::new, Collectors.toList()));

            List<TgBaseProductDetailParam> normalList = new ArrayList<>();
            List<TgBaseProductDetailParam> errorList = new ArrayList<>();
            this.validDetailExcel(dataList, errorList, normalList, groupMap);

            //正常数据处理
            if(CollectionUtil.isNotEmpty(normalList)){
                List<TgBaseProduct> saveList = new ArrayList<>();
                List<TgBaseProductDetail> saveDetailList = new ArrayList<>();
                for (TgBaseProductDetailParam successParam : normalList) {
                    //更新主表
                    TgBaseProduct baseProduct = new TgBaseProduct();
                    BeanUtils.copyProperties(successParam, baseProduct);
                    //导入清关产品信息维护带磁默认为：否
                    baseProduct.setIsMagnet("0");
                    baseProduct.setUpdateUser(name);
                    baseProduct.setUpdateTime(DateUtil.date());
                    saveList.add(baseProduct);

                    //更新明细表
                    LambdaQueryWrapper<TgBaseProductDetail> detailWrapper = new LambdaQueryWrapper<>();
                    detailWrapper.eq(TgBaseProductDetail :: getPid, successParam.getId())
                            .eq(TgBaseProductDetail :: getCountryCode, successParam.getCountryCode());
                    TgBaseProductDetail baseProductDetail = tgBaseProductDetailService.getOne(detailWrapper);
                    if(baseProductDetail == null){
                        baseProductDetail = new TgBaseProductDetail();
                        baseProductDetail.setCreateUser(name);
                        baseProductDetail.setPid(successParam.getId());
                        baseProductDetail.setMainMaterialCode(successParam.getMainMaterialCode());
                        baseProductDetail.setCountryCode(successParam.getCountryCode());
                        baseProductDetail.setCountryName(successParam.getCountryName());
                    } else {
                        baseProductDetail.setUpdateUser(name);
                        baseProductDetail.setUpdateTime(DateUtil.date());
                    }
                    baseProductDetail.setHsCode(successParam.getHsCode());
                    baseProductDetail.setMergeStatus(TgMergeStatusEnum.NOT_MERGE.getCode());
                    saveDetailList.add(baseProductDetail);
                }
                //更新主数据
                this.saveOrUpdateBatch(saveList);
                //更新明细数据
                tgBaseProductDetailService.saveOrUpdateBatch(saveDetailList);
            }

            //异常数据输出异常Excel文件流
            if(CollectionUtil.isNotEmpty(errorList)){
                String fileName = this.dealImportDetailErrorList(errorList);
                log.info("产品基本信息导入清关信息数据结束，耗时------------------->" + (System.currentTimeMillis() - start) + "ms");
                return ResponseData.error(ResponseData.DEFAULT_ERROR_CODE, "存在导入失败数据！", fileName);
            } else {
                log.info("产品基本信息导入清关信息数据结束，耗时------------------->" + (System.currentTimeMillis() - start) + "ms");
                return ResponseData.success();
            }
        } catch (Exception e) {
            log.error("产品基本信息导入清关信息处理异常，导入失败！", e);
            return ResponseData.error("产品基本信息导入清关信息处理异常，导入失败！");
        } finally {
            if(buffer != null){
                try {
                    buffer.close();
                } catch (IOException e) {
                    log.error("产品基本信息导入清关信息关闭流异常", e);
                }
            }
        }
    }

    /**
     * 导入Excel校验
     * @param dataList
     * @param errorList
     * @param normalList
     * @param groupMap
     */
    private void validDetailExcel(List<TgBaseProductDetailParam> dataList, List<TgBaseProductDetailParam> errorList, List<TgBaseProductDetailParam> normalList, Map<String, List<TgCountryInfo>> groupMap){
        Iterator<TgBaseProductDetailParam> iterator = dataList.listIterator();
        while(iterator.hasNext()) {
            TgBaseProductDetailParam param = iterator.next();
            param.setUploadRemark(null);
            //验证基础信息
            ResponseData validateResp = this.validateDetail(param, normalList, groupMap);
            if(ResponseData.DEFAULT_ERROR_CODE.equals(validateResp.getCode())){
                param.setUploadRemark(validateResp.getMessage());
                //添加异常数据
                errorList.add(param);
                continue;
            }
        }
    }

    /**
     * 校验产品基本信息明细入参
     * @param param
     * @param normalList
     * @param groupMap
     * @return
     */
    ResponseData validateDetail(TgBaseProductDetailParam param, List<TgBaseProductDetailParam> normalList, Map<String, List<TgCountryInfo>> groupMap){
        if(StringUtils.isAnyBlank(
                param.getMaterialCode(),
                param.getInvoiceProNameEn(),
                param.getClearMaterialEn()
        )){
            return ResponseData.error("物料编码、开票英文品名、报关英文材质为必填项！");
        }

        if(StringUtils.isBlank(param.getUsHsCode())
            && StringUtils.isBlank(param.getUkHsCode())
            && StringUtils.isBlank(param.getDeHsCode())
            && StringUtils.isBlank(param.getFrHsCode())
            && StringUtils.isBlank(param.getCaHsCode())
            && StringUtils.isBlank(param.getJpHsCode())
        ){
            return ResponseData.error("HSCode必须填一项！");
        }

        //物料校验
        LambdaQueryWrapper<TgBaseProduct> productWrapper = new LambdaQueryWrapper<>();
        productWrapper.eq(TgBaseProduct :: getDataType, TgDataTypeEnum.NOT_MERGE.getCode())
                .eq(TgBaseProduct :: getMaterialCode, param.getMaterialCode());
        TgBaseProduct baseProduct = mapper.selectOne(productWrapper);
        if(baseProduct == null){
            return ResponseData.error("产品基本信息不存在此物料编码！");
        }
        param.setId(baseProduct.getId());
        param.setMainMaterialCode(baseProduct.getMainMaterialCode());

        List<TgBaseProductDetailParam> tempParamList = new ArrayList<>();
        StringBuilder errorRemark = new StringBuilder();
        //美国
        if(StringUtils.isNotBlank(param.getUsHsCode())){
            List<TgCountryInfo> countryList = groupMap.get("美国");
            if(CollectionUtil.isEmpty(countryList)){
                errorRemark.append("未查询到美国国家！");
            } else {
                TgBaseProductDetailParam tempParam = new TgBaseProductDetailParam();
                BeanUtils.copyProperties(param, tempParam);
                tempParam.setCountryCode(countryList.get(0).getCountryCode());
                tempParam.setCountryName(countryList.get(0).getCountryNameCn());
                tempParam.setHsCode(param.getUsHsCode());
                tempParamList.add(tempParam);
            }
        }
        //英国
        if(StringUtils.isNotBlank(param.getUkHsCode())){
            List<TgCountryInfo> countryList = groupMap.get("英国");
            if(CollectionUtil.isEmpty(countryList)){
                errorRemark.append("未查询到英国国家！");
            } else {
                TgBaseProductDetailParam tempParam = new TgBaseProductDetailParam();
                BeanUtils.copyProperties(param, tempParam);
                tempParam.setCountryCode(countryList.get(0).getCountryCode());
                tempParam.setCountryName(countryList.get(0).getCountryNameCn());
                tempParam.setHsCode(param.getUkHsCode());
                tempParamList.add(tempParam);
            }
        }
        //德国
        if(StringUtils.isNotBlank(param.getDeHsCode())){
            List<TgCountryInfo> countryList = groupMap.get("德国");
            if(CollectionUtil.isEmpty(countryList)){
                errorRemark.append("未查询到德国国家！");
            } else {
                TgBaseProductDetailParam tempParam = new TgBaseProductDetailParam();
                BeanUtils.copyProperties(param, tempParam);
                tempParam.setCountryCode(countryList.get(0).getCountryCode());
                tempParam.setCountryName(countryList.get(0).getCountryNameCn());
                tempParam.setHsCode(param.getDeHsCode());
                tempParamList.add(tempParam);
            }
        }
        //法国
        if(StringUtils.isNotBlank(param.getFrHsCode())){
            List<TgCountryInfo> countryList = groupMap.get("法国");
            if(CollectionUtil.isEmpty(countryList)){
                errorRemark.append("未查询到法国国家！");
            } else {
                TgBaseProductDetailParam tempParam = new TgBaseProductDetailParam();
                BeanUtils.copyProperties(param, tempParam);
                tempParam.setCountryCode(countryList.get(0).getCountryCode());
                tempParam.setCountryName(countryList.get(0).getCountryNameCn());
                tempParam.setHsCode(param.getFrHsCode());
                tempParamList.add(tempParam);
            }
        }
        //加拿大
        if(StringUtils.isNotBlank(param.getCaHsCode())){
            List<TgCountryInfo> countryList = groupMap.get("加拿大");
            if(CollectionUtil.isEmpty(countryList)){
                errorRemark.append("未查询到加拿大国家！");
            } else {
                TgBaseProductDetailParam tempParam = new TgBaseProductDetailParam();
                BeanUtils.copyProperties(param, tempParam);
                tempParam.setCountryCode(countryList.get(0).getCountryCode());
                tempParam.setCountryName(countryList.get(0).getCountryNameCn());
                tempParam.setHsCode(param.getCaHsCode());
                tempParamList.add(tempParam);
            }
        }
        //日本
        if(StringUtils.isNotBlank(param.getJpHsCode())){
            List<TgCountryInfo> countryList = groupMap.get("日本");
            if(CollectionUtil.isEmpty(countryList)){
                errorRemark.append("未查询到日本国家！");
            } else {
                TgBaseProductDetailParam tempParam = new TgBaseProductDetailParam();
                BeanUtils.copyProperties(param, tempParam);
                tempParam.setCountryCode(countryList.get(0).getCountryCode());
                tempParam.setCountryName(countryList.get(0).getCountryNameCn());
                tempParam.setHsCode(param.getJpHsCode());
                tempParamList.add(tempParam);
            }
        }
        if(StringUtils.isNotBlank(errorRemark)){
            return ResponseData.error(errorRemark.toString());
        }
        normalList.addAll(tempParamList);
        return ResponseData.success();
    }

    /**
     * 校验错误文件流输出
     * @param errorList
     * @return
     */
    private String dealImportDetailErrorList(List<TgBaseProductDetailParam> errorList){
        String filePath = System.getProperty("user.dir") + "/upload/";
        String fileName =  DateUtil.format(new Date(), DatePattern.PURE_DATETIME_MS_FORMAT) + ".xlsx";
        OutputStream out = null;
        try {
            out = new FileOutputStream(filePath + fileName,false);
            EasyExcel.write(out, TgBaseProductDetailParam.class)
                    .sheet("导入异常数据").doWrite(errorList);
        } catch (FileNotFoundException e) {
            log.error("产品基本信息明细导入异常数据导出异常", e);
        } finally {
            if(out != null){
                try {
                    out.close();
                } catch (IOException e) {
                    log.error("产品基本信息明细导入异常数据导出流关闭异常", e);
                }
            }
        }
        return fileName;
    }

    @Override
    @DataSource(name = "logistics")
    public ResponseData batchAuditPass(TgBaseProductParam param) {
        String name = LoginContext.me().getLoginUser().getName();
        List<TgBaseProduct> tgBaseProductList = new ArrayList<>();
        if(CollectionUtil.isNotEmpty(param.getIdList())){
            LambdaQueryWrapper<TgBaseProduct> query = new LambdaQueryWrapper<>();
            query.in(TgBaseProduct :: getId, param.getIdList()).isNull(TgBaseProduct :: getCustomsCode);
            if(service.count(query) > 0){
                return ResponseData.error("必填项不能为空！");
            }
            for (BigDecimal id : param.getIdList()) {
                TgBaseProduct tgBaseProduct = new TgBaseProduct();
                tgBaseProduct.setId(id);
                tgBaseProduct.setAuditStatus(TgAuditStatusEnum.PASS.getCode());
                tgBaseProduct.setUpdateUser(name);
                tgBaseProduct.setUpdateTime(DateUtil.date());
                tgBaseProductList.add(tgBaseProduct);
            }
        }
        this.updateBatchById(tgBaseProductList);
        return ResponseData.success();
    }

    @Override
    @DataSource(name = "logistics")
    public ResponseData batchAuditReset(TgBaseProductParam param) {
        String name = LoginContext.me().getLoginUser().getName();
        List<TgBaseProduct> tgBaseProductList = new ArrayList<>();
        if(CollectionUtil.isNotEmpty(param.getIdList())){
            for (BigDecimal id : param.getIdList()) {
                TgBaseProduct tgBaseProduct = new TgBaseProduct();
                tgBaseProduct.setId(id);
                tgBaseProduct.setAuditStatus(TgAuditStatusEnum.RESET.getCode());
                tgBaseProduct.setUpdateUser(name);
                tgBaseProduct.setUpdateTime(DateUtil.date());
                tgBaseProductList.add(tgBaseProduct);
            }
        }
        this.updateBatchById(tgBaseProductList);
        return ResponseData.success();
    }

    @Override
    @DataSource(name = "logistics")
    @Transactional(rollbackFor = Exception.class)
    public ResponseData updateProductInfo(Integer day, List<TgBaseProductResult> resultList, List<TgBaseProductResult> changeResultList,
                                          List<TgBaseProductResult> groupResultList, List<TgBaseProductResult> groupMatList) {
        log.info("更新产品信息入参[{}]", JSONObject.toJSON(resultList));
        List<TgBaseProduct> saveOrUpdateList = new ArrayList<>();
        if(CollectionUtil.isNotEmpty(resultList)){
            for (TgBaseProductResult result : resultList) {
                String lastFBillNo = result.getFBillNo().substring(result.getFBillNo().length()-1);
                if(Validator.isNumber(lastFBillNo)){
                    result.setIncludeTax(TgIncludeTaxEnum.YES.getCode());
                    result.setIsMakeInvoice(TgIncludeTaxEnum.YES.getCode());
                } else {
                    result.setIncludeTax(TgIncludeTaxEnum.NO.getCode());
                    result.setIsMakeInvoice(TgIncludeTaxEnum.NO.getCode());
                }
                TgBaseProduct product = new TgBaseProduct();
                BeanUtils.copyProperties(result, product);
                product.setMainMaterialCode(product.getMaterialCode());
                product.setUpdateTime(DateUtil.date());
                product.setDataType(TgDataTypeEnum.NOT_MERGE.getCode());
                saveOrUpdateList.add(product);
            }
        }

        //转换物料产品信息
        if(CollectionUtil.isNotEmpty(changeResultList)){
            for (TgBaseProductResult result : changeResultList) {
                TgBaseProduct product = new TgBaseProduct();
                BeanUtils.copyProperties(result, product);
                product.setMainMaterialCode(product.getMaterialCode());
                product.setUpdateTime(DateUtil.date());
                product.setDataType(TgDataTypeEnum.NOT_MERGE.getCode());
                saveOrUpdateList.add(product);
            }
        }

        //组合物料产品信息
        if(CollectionUtil.isNotEmpty(groupResultList)){
            Map<String, List<TgBaseProductResult>> groupMap = groupMatList.stream().collect(Collectors.groupingBy(TgBaseProductResult :: getMaterialCode, LinkedHashMap::new, Collectors.toList()));
            for (TgBaseProductResult result : groupResultList) {
                TgBaseProduct product = new TgBaseProduct();
                BeanUtils.copyProperties(result, product);
                product.setMainMaterialCode(product.getMaterialCode());
                List<TgBaseProductResult> groupList = groupMap.get(result.getMaterialCode());
                if(CollectionUtil.isNotEmpty(groupList)){
                    List<String> list = new ArrayList<>();
                    for (TgBaseProductResult groupResult : groupList) {
                        list.add(groupResult.getPreMaterialCode());
                    }
                    product.setPreMaterialCode(StringUtils.join(list, ","));
                }
                product.setUpdateTime(DateUtil.date());
                product.setDataType(TgDataTypeEnum.NOT_MERGE.getCode());
                saveOrUpdateList.add(product);
            }
        }

        if(CollectionUtil.isNotEmpty(saveOrUpdateList)){
            for (TgBaseProduct product : saveOrUpdateList) {
                LambdaUpdateWrapper<TgBaseProduct> productWrapper = new LambdaUpdateWrapper<>();
                productWrapper.eq(TgBaseProduct :: getMainMaterialCode, product.getMainMaterialCode());
                service.saveOrUpdate(product, productWrapper);
            }
        }

        mapper.updateGroupMat();
        return ResponseData.success();
    }

    /**
     * 获取更新产品信息
     * @param day
     * @return
     */
    @Override
    @DataSource(name = "erpcloud")
    public List<TgBaseProductResult> getUpdateProductInfo(Integer day){
        return mapper.getUpdateProductInfo(day);
    }

    /**
     * 获取更新转换物料产品信息
     * @return
     */
    @Override
    @DataSource(name = "erpcloud")
    public List<TgBaseProductResult> getUpdateChangeProductInfo(){
        return mapper.getUpdateChangeProductInfo();
    }

    /**
     * 获取更新组合物料产品信息
     * @return
     */
    @Override
    @DataSource(name = "erpcloud")
    public List<TgBaseProductResult> getUpdateGroupProductInfo(){
        return mapper.getUpdateGroupProductInfo();
    }

    /**
     * 获取物料编码对应的组合物料
     * @return
     */
    @Override
    @DataSource(name = "erpcloud")
    public List<TgBaseProductResult> getGroupMat(){
        return mapper.getGroupMat();
    }

    @Override
    @DataSource(name = "logistics")
    @Transactional(rollbackFor = Exception.class)
    public ResponseData edit(TgBaseProductEditParam param) {
        log.info("通关产品基本信息编辑保存入参[{}]", JSONObject.toJSON(param));
        TgBaseProduct product = this.getById(param.getId());
        if(product == null){
            return ResponseData.error("产品基本信息不存在！");
        }

        //更新产品明细信息
        List<TgBaseProductDetail> detailList = param.getDetailList();
        if(CollectionUtil.isNotEmpty(detailList)){
            Set<String> repeat = new HashSet<>();
            Set<String> countrySet = new HashSet<>();
            for (TgBaseProductDetail detail : detailList) {
                if(CollectionUtil.isNotEmpty(countrySet) && countrySet.contains(detail.getCountryName())){
                    repeat.add(detail.getCountryName());
                } else {
                    countrySet.add(detail.getCountryName());
                }
            }
            if(CollectionUtil.isNotEmpty(repeat)){
                return ResponseData.error("明细数据国家重复" + JSONObject.toJSON(repeat));
            }
        }

        String userName = LoginContext.me().getLoginUser().getName();
        //校验产品基础信息
        if(param.getTaxRefund() != null && BigDecimal.ZERO.compareTo(param.getTaxRefund()) > 0){
            return ResponseData.error("退税率不能为负数！");
        }
        if(param.getGrossProfitRate() != null && BigDecimal.ZERO.compareTo(param.getGrossProfitRate()) > 0){
            return ResponseData.error("毛利率不能为负数！");
        }

        if(StringUtils.isNotBlank(param.getCustomsCode()) && StringUtils.isNotBlank(param.getEssentialFactor())){
            //要素规则校验
            LambdaQueryWrapper<TgCustomsEssentialFactor> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(TgCustomsEssentialFactor :: getCustomsCode, param.getCustomsCode());
            TgCustomsEssentialFactor customsEssentialFactor = customsEssentialFactorService.getOne(queryWrapper);
            if(customsEssentialFactor == null){
                return ResponseData.error("报关要素不存在！");
            }

            //要素框架名称
            List<String> nameList = new ArrayList<>();
            //判断报关要素是否包含品名
            if(!customsEssentialFactor.getEssentialFactor().contains("品名")){
                return ResponseData.error("报关要素【品名】不存在！");
            }
            String[] essentialFactorArr = customsEssentialFactor.getEssentialFactor().split("\\s+");
            for (String essentialFactor : essentialFactorArr) {
                String[] nameArr = essentialFactor.split("\\.");
                nameList.add(nameArr[1]);
            }
            if(!param.getEssentialFactor().contains("品名")){
                return ResponseData.error("产品基本信息要素【品名】不存在！");
            }
            String[] essentialFactorParamArr = param.getEssentialFactor().split("\\|");
            //报关要素在框架内的
            StringBuffer sbIn = new StringBuffer();
            //报关要素在框架之外的
            StringBuffer sbOut = new StringBuffer();
            sbOut.append("|");
            for (String essentialFactorParam : essentialFactorParamArr) {
                if(!essentialFactorParam.contains(":") && !essentialFactorParam.contains("：")){
                    log.error("要素格式不正确[{}]", essentialFactorParam);
                    return ResponseData.error("要素格式不正确！");
                }
                int idx = essentialFactorParam.indexOf(":");
                if(idx == -1){
                    idx = essentialFactorParam.indexOf("：");
                }
                String name = essentialFactorParam.substring(0, idx);
                String val = essentialFactorParam.substring(idx + 1);

                //校验要素名称是否在框架范围内，在框架范围内按照框架排序，未在框架范围内的则添加到框架范围外，不参与排序，不生成报关单
                if(nameList.contains(name)){
                    sbIn.append(name).append(":").append(val).append("|");
                }else{
                    sbOut.append(name).append(":").append(val).append("|");
                }
            }
            param.setEssentialFactor(sbIn.substring(0, sbIn.length()-1));
            String[] essentialFactorParamValidateArr = param.getEssentialFactor().split("\\|");
            if(StringUtils.isBlank(sbOut.substring(1))){
                param.setEssentialFactorTemp("");
            }else{
                param.setEssentialFactorTemp(sbOut.substring(0, sbOut.length()-1));
            }

            //要素按照报关要素排序
            StringBuffer sb = new StringBuffer();
            for (String name : nameList) {
                for (String essentialFactorParam : essentialFactorParamValidateArr) {
                    int idx = essentialFactorParam.indexOf(":");
                    if(idx == -1){
                        idx = essentialFactorParam.indexOf("：");
                    }
                    String nameParam = essentialFactorParam.substring(0, idx);
                    String valueParam = essentialFactorParam.substring(idx + 1);
                    if(name.equals(nameParam)){
                        sb.append(nameParam).append(":").append(valueParam).append("|");
                    }
                }
            }
            param.setEssentialFactor(sb.substring(0, sb.length()-1));
        }

        //国家校验
        List<TgCountryInfo> tgCountryInfoList = countryInfoService.list();
        Map<String, List<TgCountryInfo>> groupMap = tgCountryInfoList.stream().collect(Collectors.groupingBy(TgCountryInfo :: getCountryCode, LinkedHashMap::new, Collectors.toList()));

        //更新产品基础信息
        TgBaseProduct updateEntity = new TgBaseProduct();
        updateEntity.setId(param.getId());
        updateEntity.setInvoiceProNameEn(param.getInvoiceProNameEn());
        updateEntity.setClearMaterialCn(param.getClearMaterialCn());
        updateEntity.setClearMaterialEn(param.getClearMaterialEn());
        updateEntity.setCustomsCode(param.getCustomsCode());
        updateEntity.setIsMagnet(param.getIsMagnet());
        updateEntity.setEssentialFactor(param.getEssentialFactor());
        updateEntity.setEssentialFactorTemp(param.getEssentialFactorTemp());
        updateEntity.setTaxRefund(param.getTaxRefund());
        updateEntity.setGrossProfitRate(param.getGrossProfitRate());
        updateEntity.setUpdateTime(DateUtil.date());
        updateEntity.setUpdateUser(userName);
        updateEntity.setRemark(param.getRemark());
        LambdaUpdateWrapper<TgBaseProduct> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(TgBaseProduct :: getId, updateEntity.getId());
        this.saveOrUpdate(updateEntity, updateWrapper);

        if(CollectionUtil.isNotEmpty(detailList)){
            for (TgBaseProductDetail productDetailParam : detailList) {
                TgBaseProductDetail baseProductDetail = new TgBaseProductDetail();
                if(productDetailParam.getId() != null){
                    baseProductDetail.setId(productDetailParam.getId());
                    baseProductDetail.setUpdateTime(DateUtil.date());
                    baseProductDetail.setUpdateUser(userName);
                } else {
                    baseProductDetail.setCreateUser(userName);
                    baseProductDetail.setPid(productDetailParam.getPid());
                    baseProductDetail.setMainMaterialCode(product.getMainMaterialCode());
                }
                baseProductDetail.setCountryCode(productDetailParam.getCountryCode());
                baseProductDetail.setCountryName(productDetailParam.getCountryName());
                baseProductDetail.setHsCode(productDetailParam.getHsCode());
                baseProductDetail.setMergeStatus(TgMergeStatusEnum.NOT_MERGE.getCode());
                tgBaseProductDetailService.saveOrUpdate(baseProductDetail);
            }
        }
        return ResponseData.success();
    }

    @Override
    @DataSource(name = "logistics")
    public List<BaseSelectResult> clearMaterialCnSelect() {
        return mapper.clearMaterialCnSelect();
    }

    @Override
    @DataSource(name = "logistics")
    public List<BaseSelectResult> clearMaterialEnSelect() {
        return mapper.clearMaterialEnSelect();
    }

    @Override
    @DataSource(name = "logistics")
    public ResponseData queryMergePage(TgBaseProductParam param) {
        return ResponseData.success(mapper.queryMergePage(param.getPageContext(), param));
    }

    @Override
    @DataSource(name = "logistics")
    public List<TgProductMergeResult> exportMerge(TgBaseProductParam param) {
        Page pageContext = param.getPageContext();
        pageContext.setCurrent(1);
        pageContext.setSize(Integer.MAX_VALUE);
        return mapper.queryMergePage(pageContext, param).getRecords();
    }

    @Override
    @DataSource(name = "logistics")
    public ResponseData editMerge(TgMergeProductEditParam param){
        String name = LoginContext.me().getLoginUser().getName();

        //更新清关产品合并基础信息
        TgBaseProduct updateEntity = new TgBaseProduct();
        updateEntity.setId(param.getId());
        updateEntity.setInvoiceProNameCn(param.getInvoiceProNameCn());
        updateEntity.setInvoiceProNameEn(param.getInvoiceProNameEn());
        updateEntity.setClearMaterialCn(param.getClearMaterialCn());
        updateEntity.setClearMaterialEn(param.getClearMaterialEn());
        updateEntity.setUpdateTime(DateUtil.date());
        updateEntity.setUpdateUser(name);
        this.updateById(updateEntity);
        return ResponseData.success();
    }

    @Override
    @DataSource(name = "logistics")
    public ResponseData queryAlrMergePage(TgBaseProductParam param) {
        return ResponseData.success(mapper.queryAlrMergePage(param.getPageContext(), param));
    }

    @Override
    @DataSource(name = "logistics")
    @Transactional(rollbackFor = Exception.class)
    public ResponseData ruleMerge(List<TgMergeRuleConfirmParam> params){
        log.info("规则合并预览确认入参[{}]", JSONObject.toJSON(params));
        String name = LoginContext.me().getLoginUser().getName();
        //遍历每个合并分组
        for (TgMergeRuleConfirmParam mergeRuleConfirmParam : params) {
            List<TgProductMergeIdsParam> detailList = mergeRuleConfirmParam.getDetailList();
            if(CollectionUtil.isEmpty(detailList)){
                continue;
            }
            List<TgProductMergeIdsParam> detailSortList = detailList.stream().sorted(Comparator.comparing(TgProductMergeIdsParam :: getIsSelect, Comparator.reverseOrder())).collect(Collectors.toList());

            //每组一个合并
            BigDecimal mergeGroupId = null;
            BigDecimal mergeGroupDetailId = null;
            StringBuffer margeMaterialCode = new StringBuffer();
            for (TgProductMergeIdsParam detailParam : detailSortList) {
                TgBaseProductDetail productDetail = tgBaseProductDetailService.getById(detailParam.getDetailId());
                if(productDetail == null){
                    TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                    return ResponseData.error("清关产品合并明细数据不存在！");
                }
                TgBaseProduct product = this.getById(detailParam.getId());
                if(product == null){
                    TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                    return ResponseData.error("清关产品合并主数据不存在！");
                }
                if(!detailParam.getId().equals(productDetail.getPid())){
                    //对比主数据ID是否一致，不一致则异常
                    log.error("清关产品合并的规则合并预览的主数据ID：[{}]和明细数据ID：[{}]对应的主数据ID：[{}]不一致！", detailParam.getId(), detailParam.getDetailId(), productDetail.getPid());
                    TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                    return ResponseData.error("清关产品规则合并预览数据异常，合并失败！");
                }

                //已合并的数据，先做合并数据删除，再重新将未合并的数据合并
                if(TgMergeStatusEnum.MERGE.getCode().equals(productDetail.getMergeStatus())){
                    //根据合并的MergeId查询合并数据
                    LambdaQueryWrapper<TgBaseProductDetail> detailWrapper = new LambdaQueryWrapper<>();
                    detailWrapper.eq(TgBaseProductDetail :: getId, productDetail.getMergeId());
                    TgBaseProductDetail oldProductDetail = tgBaseProductDetailService.getOne(detailWrapper);
                    if(oldProductDetail != null){
                        //删除已合并的主数据
                        this.removeById(oldProductDetail.getPid());

                        //删除已合并的明细数据
                        tgBaseProductDetailService.removeById(oldProductDetail.getId());
                    }
                }

                //预览合并分组选择数据则为新合并的数据
                if("1".equals(detailParam.getIsSelect())){
                    TgBaseProduct mergeProduct = new TgBaseProduct();
                    BeanUtils.copyProperties(product, mergeProduct);
                    mergeProduct.setId(null);
                    mergeProduct.setDataType(TgDataTypeEnum.MERGE.getCode());
                    mergeProduct.setCreateUser(name);
                    mergeProduct.setUpdateUser(null);
                    mergeProduct.setUpdateTime(null);
                    this.save(mergeProduct);

                    mergeGroupId = mergeProduct.getId();

                    TgBaseProductDetail mergeProductDetail = new TgBaseProductDetail();
                    BeanUtils.copyProperties(productDetail, mergeProductDetail);
                    mergeProductDetail.setId(null);
                    mergeProductDetail.setPid(mergeProduct.getId());
                    mergeProductDetail.setMergeStatus(TgMergeStatusEnum.MERGE.getCode());
                    mergeProductDetail.setMergeId(null);
                    mergeProductDetail.setCreateUser(name);
                    mergeProductDetail.setUpdateUser(null);
                    mergeProductDetail.setUpdateTime(null);
                    tgBaseProductDetailService.save(mergeProductDetail);

                    mergeGroupDetailId = mergeProductDetail.getId();
                }

                productDetail.setMergeId(mergeGroupDetailId);
                productDetail.setMergeStatus(TgMergeStatusEnum.MERGE.getCode());
                productDetail.setUpdateUser(name);
                productDetail.setUpdateTime(DateUtil.date());
                tgBaseProductDetailService.updateById(productDetail);

                margeMaterialCode.append(product.getMaterialCode()).append(",");
            }

            //更新规则合并后的物料编码
            TgBaseProduct updateMaterialCode = new TgBaseProduct();
            updateMaterialCode.setId(mergeGroupId);
            updateMaterialCode.setMaterialCode(margeMaterialCode.substring(0, margeMaterialCode.length()-1));
            this.updateById(updateMaterialCode);
        }
        return ResponseData.success();
    }

    @Override
    @DataSource(name = "logistics")
    public ResponseData ruleMergePreview(TgProductMergeRuleParam param){
        log.info("规则合并预览入参[{}]", JSONObject.toJSON(param));
        if(CollectionUtil.isEmpty(param.getMergeRuleList())){
            return ResponseData.error("请选择合并规则！");
        }

        List<TgProductMergeIdsParam> productMergeIds = param.getIdList();
        if(CollectionUtil.isNotEmpty(productMergeIds) && productMergeIds.size() > 1){
            //明细数据ID集合
            List<BigDecimal> productDetailIdList = new ArrayList<>();
            for (TgProductMergeIdsParam productMergeId : productMergeIds) {
                TgBaseProduct productMerge = this.getById(productMergeId.getId());
                if(productMerge == null){
                    return ResponseData.error("清关产品合并主数据不存在！");
                }
                //合并后数据，根据合并ID：MERGE_ID查合并的源数据
                if(TgDataTypeEnum.MERGE.getCode().equals(productMerge.getDataType())){
                    TgBaseProductDetail productMergeDetail = tgBaseProductDetailService.getById(productMergeId.getDetailId());
                    if(productMergeDetail == null){
                        return ResponseData.error("清关产品合并明细数据不存在！");
                    }

                    LambdaQueryWrapper<TgBaseProductDetail> queryWrapper = new LambdaQueryWrapper<>();
                    queryWrapper.eq(TgBaseProductDetail :: getMergeId, productMergeDetail.getId());
                    List<TgBaseProductDetail> baseProductDetailList = tgBaseProductDetailService.list(queryWrapper);
                    if(CollectionUtil.isNotEmpty(baseProductDetailList)){
                        for (TgBaseProductDetail baseProductDetail : baseProductDetailList) {
                            productDetailIdList.add(baseProductDetail.getId());
                        }
                    }
                }

                //未合并的源数据
                if(TgDataTypeEnum.NOT_MERGE.getCode().equals(productMerge.getDataType())){
                    productDetailIdList.add(productMergeId.getDetailId());
                }
            }
            param.setSourceIdList(productDetailIdList);
        } else {
            return ResponseData.error("单条数据不能进行合并！");
        }

        //合并规则处理
        for (String mergeRule : param.getMergeRuleList()) {
            if(TgMergeRuleEnum.INVOICE_PRO_NAME_EN.getCode().equals(mergeRule)){
                param.setCountryCode("COUNTRY_CODE");
                param.setInvoiceProNameEn(TgMergeRuleEnum.INVOICE_PRO_NAME_EN.getCode());
            }
            if(TgMergeRuleEnum.CLEAR_MATERIAL_EN.getCode().equals(mergeRule)){
                param.setCountryCode("COUNTRY_CODE");
                param.setClearMaterialEn(TgMergeRuleEnum.CLEAR_MATERIAL_EN.getCode());
            }
            if(TgMergeRuleEnum.HSCODE.getCode().equals(mergeRule)){
                param.setCountryCode("COUNTRY_CODE");
                param.setHsCode(TgMergeRuleEnum.HSCODE.getCode());
            }
        }

        List<TgProductMergeRuleResult> ruleMergePreviewList = mapper.ruleMergePreview(param);
        if(CollectionUtil.isEmpty(ruleMergePreviewList)){
            return ResponseData.error("根据规则合并条件分析没有需要合并的数据！");
        }
        Map<String, List<TgProductMergeRuleResult>> groupMap = ruleMergePreviewList.stream().collect(Collectors.groupingBy(TgProductMergeRuleResult :: getCountryCode, LinkedHashMap::new, Collectors.toList()));
        if(groupMap.size() > 1){
            return ResponseData.error("不同国家不能进行合并操作！");
        }

        int i = 1;
        for (TgProductMergeRuleResult ruleMergePreview : ruleMergePreviewList) {
            StringBuffer sb = new StringBuffer();
            sb.append("合并").append(i).append("：");
            if(StringUtils.isNotBlank(param.getInvoiceProNameEn())){
                sb.append(ruleMergePreview.getInvoiceProNameEn()).append("|");
            }
            if(StringUtils.isNotBlank(param.getClearMaterialEn())){
                sb.append(ruleMergePreview.getClearMaterialEn()).append("|");
            }
            if(StringUtils.isNotBlank(param.getCountryCode())){
                sb.append(ruleMergePreview.getCountryName()).append("|");
            }
            if(StringUtils.isNotBlank(param.getHsCode())){
                sb.append(ruleMergePreview.getHsCode()).append("|");
            }
            String groupMergeName = sb.toString();
            ruleMergePreview.setGroupMergeName(groupMergeName.substring(0, groupMergeName.length()-1));

            i++;

            //拿到产品基本信息主表ids获取清关品名明细
            List<TgProductMergeResult> mergePreviewDetailList = new ArrayList<>();
            String[] idArr = ruleMergePreview.getIds().split(",");
            String[] detailIdArr = ruleMergePreview.getDetailIds().split(",");
            for (int j = 0; j < idArr.length; j++) {
                //查询主数据
                TgBaseProduct mergePreviewDetail = this.getById(idArr[j]);
                //默认选中第1条
                if(j == 0){
                    mergePreviewDetail.setIsSelect("1");
                } else {
                    mergePreviewDetail.setIsSelect("0");
                }
                TgProductMergeResult productMergeResult = new TgProductMergeResult();
                BeanUtils.copyProperties(mergePreviewDetail, productMergeResult);
                productMergeResult.setDetailId(new BigDecimal(detailIdArr[j]));

                //查询明细
                TgBaseProductDetail detail = tgBaseProductDetailService.getById(productMergeResult.getDetailId());
                if(detail == null){
                    return ResponseData.error("产品基本信息明细数据不存在！明细ID：" + productMergeResult.getDetailId());
                }
                productMergeResult.setCountryCode(detail.getCountryCode());
                productMergeResult.setCountryName(detail.getCountryName());
                productMergeResult.setHsCode(detail.getHsCode());
                productMergeResult.setMergeStatus(detail.getMergeStatus());
                productMergeResult.setMergeId(detail.getMergeId());

                //根据国家和HsCode获取清关税率的流转税率、关税率、附加税率
                LambdaQueryWrapper<TgCustomsTaxRate> taxRateWrapper = new LambdaQueryWrapper<>();
                taxRateWrapper.eq(TgCustomsTaxRate :: getCountryCode, detail.getCountryCode())
                        .eq(TgCustomsTaxRate :: getHsCode, detail.getHsCode());
                TgCustomsTaxRate customsTaxRate = tgCustomsTaxRateService.getOne(taxRateWrapper);
                if(customsTaxRate != null){
                    productMergeResult.setChangeTaxRate(customsTaxRate.getChangeTaxRate());
                    productMergeResult.setTaxRate(customsTaxRate.getTaxRate());
                    productMergeResult.setAddTaxRate(customsTaxRate.getAddTaxRate());
                }
                mergePreviewDetailList.add(productMergeResult);
            }
            ruleMergePreview.setInvoiceProNameCnList(mergePreviewDetailList);
        }
        return ResponseData.success(ruleMergePreviewList);
    }

    @Override
    @DataSource(name = "logistics")
    public ResponseData selectMerge(List<TgProductMergeIdsParam> param){
        log.info("自定义合并入参[{}]", JSONObject.toJSON(param));
        String name = LoginContext.me().getLoginUser().getName();
        List<TgProductMergeIdsParam> detailSortList = param.stream().sorted(Comparator.comparing(TgProductMergeIdsParam :: getIsSelect, Comparator.reverseOrder())).collect(Collectors.toList());
        //每组一个合并
        BigDecimal mergeGroupId = null;
        BigDecimal mergeGroupDetailId = null;
        StringBuffer margeMaterialCode = new StringBuffer();
        for (TgProductMergeIdsParam detailParam : detailSortList) {
            TgBaseProductDetail productDetail = tgBaseProductDetailService.getById(detailParam.getDetailId());
            if(productDetail == null){
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                return ResponseData.error("清关产品合并明细数据不存在！");
            }
            TgBaseProduct product = this.getById(detailParam.getId());
            if(product == null){
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                return ResponseData.error("清关产品合并主数据不存在！");
            }
            if(!detailParam.getId().equals(productDetail.getPid())){
                //对比主数据ID是否一致不一致则异常
                log.error("清关产品合并的自定义合并预览的主数据ID：[{}]和明细数据ID：[{}]对应的主数据ID：[{}]不一致！", detailParam.getId(), detailParam.getDetailId(), productDetail.getPid());
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                return ResponseData.error("清关产品自定义合并预览数据异常，合并失败！");
            }

            //已合并的数据，先做合并数据删除，再重新将未合并的数据合并
            if(TgMergeStatusEnum.MERGE.getCode().equals(productDetail.getMergeStatus())){
                //根据合并的MergeId查询合并数据
                LambdaQueryWrapper<TgBaseProductDetail> detailWrapper = new LambdaQueryWrapper<>();
                detailWrapper.eq(TgBaseProductDetail :: getId, productDetail.getMergeId());
                TgBaseProductDetail oldProductDetail = tgBaseProductDetailService.getOne(detailWrapper);
                if(oldProductDetail != null){
                    //删除已合并的主数据
                    this.removeById(oldProductDetail.getPid());

                    //删除已合并的明细数据
                    tgBaseProductDetailService.removeById(oldProductDetail.getId());
                }
            }

            //预览合并分组选择数据则为新合并的数据
            if("1".equals(detailParam.getIsSelect())){
                TgBaseProduct mergeProduct = new TgBaseProduct();
                BeanUtils.copyProperties(product, mergeProduct);
                mergeProduct.setId(null);
                mergeProduct.setDataType(TgDataTypeEnum.MERGE.getCode());
                mergeProduct.setCreateUser(name);
                mergeProduct.setUpdateUser(null);
                mergeProduct.setUpdateTime(null);
                this.save(mergeProduct);

                mergeGroupId = mergeProduct.getId();

                TgBaseProductDetail mergeProductDetail = new TgBaseProductDetail();
                BeanUtils.copyProperties(productDetail, mergeProductDetail);
                mergeProductDetail.setId(null);
                mergeProductDetail.setPid(mergeProduct.getId());
                mergeProductDetail.setMergeStatus(TgMergeStatusEnum.MERGE.getCode());
                mergeProductDetail.setMergeId(null);
                mergeProductDetail.setCreateUser(name);
                mergeProductDetail.setUpdateUser(null);
                mergeProductDetail.setUpdateTime(null);
                tgBaseProductDetailService.save(mergeProductDetail);

                mergeGroupDetailId = mergeProductDetail.getId();
            }

            productDetail.setMergeId(mergeGroupDetailId);
            productDetail.setMergeStatus(TgMergeStatusEnum.MERGE.getCode());
            productDetail.setUpdateUser(name);
            productDetail.setUpdateTime(DateUtil.date());
            tgBaseProductDetailService.updateById(productDetail);

            margeMaterialCode.append(product.getMaterialCode()).append(",");
        }

        //更新自定义合并后的物料编码
        TgBaseProduct updateMaterialCode = new TgBaseProduct();
        updateMaterialCode.setId(mergeGroupId);
        updateMaterialCode.setMaterialCode(margeMaterialCode.substring(0, margeMaterialCode.length()-1));
        this.updateById(updateMaterialCode);
        return ResponseData.success();
    }

    @Override
    @DataSource(name = "logistics")
    public ResponseData selectMergePreview(List<TgProductMergeIdsParam> param){
        log.info("自定义合并预览入参[{}]", JSONObject.toJSON(param));

        if(CollectionUtil.isNotEmpty(param) && param.size() > 1){
            //明细数据ID集合
            List<BigDecimal> productDetailIdList = new ArrayList<>();
            for (TgProductMergeIdsParam productMergeId : param) {
                TgBaseProduct productMerge = this.getById(productMergeId.getId());
                if(productMerge == null){
                    return ResponseData.error("清关产品合并主数据不存在！");
                }
                //合并后数据，根据合并ID：MERGE_ID查合并的源数据
                if(TgDataTypeEnum.MERGE.getCode().equals(productMerge.getDataType())){
                    TgBaseProductDetail productMergeDetail = tgBaseProductDetailService.getById(productMergeId.getDetailId());
                    if(productMergeDetail == null){
                        return ResponseData.error("清关产品合并明细数据不存在！");
                    }

                    LambdaQueryWrapper<TgBaseProductDetail> queryWrapper = new LambdaQueryWrapper<>();
                    queryWrapper.eq(TgBaseProductDetail :: getMergeId, productMergeDetail.getId());
                    List<TgBaseProductDetail> baseProductDetailList = tgBaseProductDetailService.list(queryWrapper);
                    if(CollectionUtil.isNotEmpty(baseProductDetailList)){
                        for (TgBaseProductDetail baseProductDetail : baseProductDetailList) {
                            productDetailIdList.add(baseProductDetail.getId());
                        }
                    }
                }

                //未合并的源数据
                if(TgDataTypeEnum.NOT_MERGE.getCode().equals(productMerge.getDataType())){
                    productDetailIdList.add(productMergeId.getDetailId());
                }
            }

            List<TgProductMergeResult> productMergeResult =  mapper.selectMergePreview(productDetailIdList);
            if(CollectionUtil.isEmpty(productMergeResult)){
                return ResponseData.error("所选数据分析没有需要合并的数据！");
            }
            Map<String, List<TgProductMergeResult>> groupMap = productMergeResult.stream().collect(Collectors.groupingBy(TgProductMergeResult :: getCountryCode, LinkedHashMap::new, Collectors.toList()));
            if(groupMap.size() > 1){
                return ResponseData.error("不同国家不能进行合并操作！");
            }
            for (int i = 0; i < productMergeResult.size(); i++) {
                //默认选中第1条
                if(i == 0){
                    productMergeResult.get(i).setIsSelect("1");
                } else {
                    productMergeResult.get(i).setIsSelect("0");
                }
            }
            return ResponseData.success(productMergeResult);
        } else {
            return ResponseData.error("单条数据不能进行合并！");
        }
    }

    @Override
    @DataSource(name = "logistics")
    @Transactional(rollbackFor = Exception.class)
    public ResponseData mergeSplit(TgProductMergeIdsParam param){
        try {
            String name = LoginContext.me().getLoginUser().getName();
            TgBaseProduct mergeProduct = this.getById(param.getId());
            if(mergeProduct == null){
                return ResponseData.error("清关产品合并主数据不存在！");
            }
            if(!TgDataTypeEnum.MERGE.getCode().equals(mergeProduct.getDataType())){
                return ResponseData.error("未合并数据不能拆分！");
            }
            TgBaseProductDetail mergeProductDetail = tgBaseProductDetailService.getById(param.getDetailId());
            if(mergeProductDetail == null){
                return ResponseData.error("清关产品合并明细数据不存在！");
            }
            BigDecimal mergeDetailId = mergeProductDetail.getId();
            //更新合并的明细数据的合并状态和对应的合并ID
            mapper.updateMergeProductDetailById(mergeDetailId, name);

            //删除合并的主数据
            this.removeById(param.getId());

            //删除合并的明细数据
            tgBaseProductDetailService.removeById(param.getDetailId());
            return ResponseData.success();
        } catch (Exception e){
            log.error("拆分异常，异常信息：", e);
            return ResponseData.error("拆分异常，请联系管理员！");
        }
    }

    @Override
    @DataSource(name = "EBMS")
    @Transactional(propagation = Propagation.REQUIRES_NEW )
    public List<ValidateLabelResult> selectTbComShippingLabel(ValidateLabelResult param){
        return mapper.selectTbComShippingLabel(param);
    }

    @Override
    @DataSource(name = "finance")
    @Transactional(propagation = Propagation.REQUIRES_NEW )
    public String getFinanceLxSid(String shopName, String site){
        return mapper.getFinanceLxSid(shopName, site);
    }

    @Override
    @DataSource(name = "finance")
    @Transactional(propagation = Propagation.REQUIRES_NEW )
    public List<CwLingxingShopInfo> getFinanceLxSidList(){
        return mapper.getFinanceLxSidList();
    }

    @Override
    @DataSource(name = "logistics")
    public ResponseData getLxAmazonListing(CwLingxingShopInfo param){
        //根据SID维度获取领星Listing并入库
        List<CwLingxingShopInfo> lxShopList = null;
        if(param.getSid() == null){
            lxShopList = service.getFinanceLxSidList();
        } else {
            lxShopList = new ArrayList<>();
            CwLingxingShopInfo shopInfo = new CwLingxingShopInfo();
            shopInfo.setSid(param.getSid());
            shopInfo.setShopName(param.getShopName());
            shopInfo.setSite(param.getSite());
            lxShopList.add(shopInfo);
        }
        for (CwLingxingShopInfo lxShop : lxShopList) {
            if(lxShop.getSite().contains("(")){
                lxShop.setSite(lxShop.getSite().substring(0, lxShop.getSite().indexOf("(")));
            }
            LambdaQueryWrapper<TgLxListingInfo> listingWrapper = new LambdaQueryWrapper<>();
            listingWrapper.eq(TgLxListingInfo :: getSid, lxShop.getSid());
            tgLxListingInfoService.remove(listingWrapper);

            //默认第一页
            int offset = 0;
            int length = 1000;
            Integer total = 0;
            do{
                ListingReq lxReq = new ListingReq();
                lxReq.setSid(lxShop.getSid().toString());
                lxReq.setLength(length);
                lxReq.setOffset(offset);
                try {
                    LingXingBaseRespData baseRespData = lingXingSellConsumer.listing(lxReq);
//                    log.info("定时获取领星亚马逊Listing相应数据[{}]", JSONObject.toJSON(baseRespData));
                    if(LingXingBaseRespData.DEFAULT_SUCCESS_CODE.equals(baseRespData.getCode())){
                        List<Object> objList = baseRespData.getData();
                        List<TgLxListingInfo> lxListingInfoList = new ArrayList<>();
                        for (Object obj : objList) {
                            String jsonObject = JSON.toJSONString(obj);
                            ListingResp listingResp = JSONObject.parseObject(jsonObject, ListingResp.class);
                            TgLxListingInfo lxListingInfo = new TgLxListingInfo();
                            lxListingInfo.setSid(listingResp.getSid());
                            lxListingInfo.setShopName(lxShop.getShopName());
                            lxListingInfo.setSite(lxShop.getSite());
                            lxListingInfo.setMarketplace(listingResp.getMarketplace());
                            lxListingInfo.setLocalSku(listingResp.getLocal_sku());
                            lxListingInfo.setSellerSku(listingResp.getSeller_sku());
                            lxListingInfo.setStatus(listingResp.getStatus());
                            lxListingInfo.setIsDelete(listingResp.getIs_delete());
                            lxListingInfo.setListingPrice(listingResp.getListing_price());
                            lxListingInfo.setPrice(listingResp.getPrice());
                            lxListingInfo.setLandedPrice(listingResp.getLandedPrice());
                            lxListingInfo.setSmallImageUrl(listingResp.getSmall_image_url());
                            lxListingInfo.setAsin(listingResp.getAsin());
                            lxListingInfo.setListingId(listingResp.getListingId());
                            lxListingInfo.setCurrencyCode(listingResp.getCurrencyCode());
                            lxListingInfo.setListingUpdateDate(listingResp.getListingUpdateDate());
                            lxListingInfo.setPairUpdateTime(listingResp.getPairUpdateTime());
                            lxListingInfoList.add(lxListingInfo);
                        }
                        tgLxListingInfoService.saveBatch(lxListingInfoList);
                        offset = offset + length;
                        total = baseRespData.getTotal();
                    }
                } catch (Exception e){
                    log.error("获取领星Listing数据异常，sid[{}]，当前页数[{}]，分页条数[{}]，异常信息[{}]", lxShop.getSid(), offset, length, e.getMessage());
                }
            } while (offset < total);//小于总条数时循环获取数据
        }
        return ResponseData.success();
    }

    @Override
    @DataSource(name = "logistics")
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public List<TgBaseProduct> getProductInfoList(List<String> matCodeList){
        LambdaQueryWrapper<TgBaseProduct> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.in(TgBaseProduct :: getMaterialCode, matCodeList);
        return service.list(queryWrapper);
    }
}
