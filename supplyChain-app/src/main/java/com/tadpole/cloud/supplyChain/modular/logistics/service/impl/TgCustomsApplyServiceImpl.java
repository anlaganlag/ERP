package com.tadpole.cloud.supplyChain.modular.logistics.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
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
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tadpole.cloud.platformSettlement.api.finance.entity.FixedExchangeRate;
import com.tadpole.cloud.platformSettlement.api.finance.model.params.FixedExchangeRateParam;
import com.tadpole.cloud.supplyChain.api.logistics.entity.*;
import com.tadpole.cloud.supplyChain.api.logistics.model.params.TgCustomsApplyImportSheet0Param;
import com.tadpole.cloud.supplyChain.api.logistics.model.params.TgCustomsApplyImportSheet1Param;
import com.tadpole.cloud.supplyChain.api.logistics.model.params.TgCustomsApplyParam;
import com.tadpole.cloud.supplyChain.api.logistics.model.params.TgCustomsApplySaveParam;
import com.tadpole.cloud.supplyChain.api.logistics.model.result.*;
import com.tadpole.cloud.supplyChain.modular.logistics.consumer.FixedExchangeRateConsumer;
import com.tadpole.cloud.supplyChain.modular.logistics.enums.TgAuditStatusEnum;
import com.tadpole.cloud.supplyChain.modular.logistics.enums.TgDataTypeEnum;
import com.tadpole.cloud.supplyChain.modular.logistics.mapper.TgCustomsApplyMapper;
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
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>
 * 报关单 服务实现类
 * </p>
 *
 * @author ty
 * @since 2023-06-19
 */
@Slf4j
@Service
public class TgCustomsApplyServiceImpl extends ServiceImpl<TgCustomsApplyMapper, TgCustomsApply> implements ITgCustomsApplyService {

    @Resource
    private TgCustomsApplyMapper mapper;
    @Autowired
    private ITgCustomsApplyDetailService applyDetailService;
    @Autowired
    private ITgBaseProductService productService;
    @Autowired
    private ITgBoxInfoService tgBoxInfoService;
    @Autowired
    private ITgCustomBoxInfoService tgCustomBoxInfoService;
    @Autowired
    private FixedExchangeRateConsumer fixedExchangeRateConsumer;

    @Override
    @DataSource(name = "logistics")
    public ResponseData queryPage(TgCustomsApplyParam param) {
        if(StringUtils.isNotEmpty(param.getApplyDateEnd())){
            param.setApplyDateEnd(param.getApplyDateEnd() + " 23:59:59");
        }
        if(StringUtils.isNotEmpty(param.getExportDateEnd())){
            param.setExportDateEnd(param.getExportDateEnd() + " 23:59:59");
        }
        return ResponseData.success(mapper.queryPage(param.getPageContext(), param));
    }

    @Override
    @DataSource(name = "logistics")
    @Transactional(rollbackFor = Exception.class)
    public ResponseData delete(TgCustomsApplyParam param) {
        //删除主数据
        this.removeById(param.getId());

        //删除明细数据
        LambdaQueryWrapper<TgCustomsApplyDetail> detailWrapper = new LambdaQueryWrapper<>();
        detailWrapper.eq(TgCustomsApplyDetail :: getPid, param.getId());
        applyDetailService.remove(detailWrapper);
        return ResponseData.success();
    }

    @Override
    @Transactional
    @DataSource(name = "EBMS")
    public ResponseData selectEbmsLogisticsPacking(TgLogisticsPackingResult param, List<TgBoxInfo> tgBoxInfoList) {
        if(CollectionUtil.isEmpty(param.getPackCodeList())){
            return ResponseData.error("请输入出货清单号！");
        }

        List<String> boxTypeList = tgBoxInfoList.stream().map(i -> i.getBoxType()).collect(Collectors.toList());

        //获取EBMS出货订单数据
        List<TgLogisticsPackingResult> packingList = mapper.selectLogisticsPacking(param.getPackCodeList());
        if(CollectionUtil.isNotEmpty(packingList)){
            for (TgLogisticsPackingResult packing : packingList) {
                List<TgCustomsApplyDetail> packingDetailList = mapper.selectLogisticsPackingDetail(packing.getPackCode());
                if(CollectionUtil.isNotEmpty(packingDetailList)){
                    Map<String, List<TgCustomsApplyDetail>> detailListMap = packingDetailList.stream().collect(Collectors.groupingBy(TgCustomsApplyDetail :: getBoxNo, LinkedHashMap::new, Collectors.toList()));
                    packing.setQuantity(detailListMap.size());

                    Map<String, List<TgCustomsApplyDetail>> packingDetailListMap = packingDetailList.stream().collect(Collectors.groupingBy(packingDetail -> packingDetail.getMaterialCode(), LinkedHashMap::new, Collectors.toList()));
                    List<String> matCodeList = packingDetailListMap.keySet().stream().collect(Collectors.toList());
                    //根据物料编码查询通关产品信息的规格型号、开票规格型号、公司品牌、开票品名
                    List<TgBaseProduct> productList = productService.getProductInfoList(matCodeList);
                    if(CollectionUtil.isEmpty(productList)){
                        return ResponseData.error("未查询到产品基本信息数据，物料编码：" + JSONObject.toJSON(matCodeList));
                    }
                    Map<String, List<TgBaseProduct>> productListMap = productList.stream().collect(Collectors.groupingBy(product -> product.getMaterialCode(), LinkedHashMap::new, Collectors.toList()));

                    Boolean mainIsSelect = Boolean.TRUE;
                    for (TgCustomsApplyDetail applyDetail : packingDetailList) {
                        if(StringUtils.isBlank(applyDetail.getPackDirectCode())){
                            return ResponseData.error(applyDetail.getPackCode() + "的调拨单号为空！");
                        }
                        if(StringUtils.isBlank(applyDetail.getBoxType())){
                            return ResponseData.error(applyDetail.getPackCode() + "的箱型为空！");
                        }

                        List<TgBaseProduct> mBaseProductList = productListMap.get(applyDetail.getMaterialCode());
                        if(CollectionUtil.isEmpty(mBaseProductList)){
                            return ResponseData.error("未查询到产品基本信息数据，物料编码：" + applyDetail.getMaterialCode());
                        }
                        applyDetail.setType(mBaseProductList.get(0).getFitBrand());
                        applyDetail.setStyle(mBaseProductList.get(0).getStyle());
                        applyDetail.setCompanyBrand(mBaseProductList.get(0).getCompanyBrand());
                        applyDetail.setInvoiceProNameCn(mBaseProductList.get(0).getInvoiceProNameCn());

                        applyDetail.setBoxNumAccount(detailListMap.get(applyDetail.getBoxNo()).size());//箱子对应数据的数据条数，用于判断箱子的数据是否全部选中

                        //根据出货清单、箱号、SKU、调拨单号判断是否关联
                        LambdaQueryWrapper<TgCustomsApplyDetail> detailWrapper = new LambdaQueryWrapper<>();
                        detailWrapper.eq(TgCustomsApplyDetail :: getPackCode, applyDetail.getPackCode())
                                .eq(TgCustomsApplyDetail :: getBoxNo, applyDetail.getBoxNo())
                                .eq(TgCustomsApplyDetail :: getSku, applyDetail.getSku())
                                .eq(TgCustomsApplyDetail :: getPackDirectCode, applyDetail.getPackDirectCode())
                                .eq(TgCustomsApplyDetail :: getDataType, TgDataTypeEnum.MERGE.getCode());
                        TgCustomsApplyDetail tgCustomsApplyDetail = applyDetailService.getOne(detailWrapper);
                        //MC是否存在此箱型
                        Boolean hasBoxType = Boolean.FALSE;
                        if(tgCustomsApplyDetail != null){
                            if(boxTypeList.contains(tgCustomsApplyDetail.getBoxType())){
                                hasBoxType = Boolean.TRUE;
                            }
                            packing.setIsDeal("是");
                            applyDetail.setBoxType(tgCustomsApplyDetail.getBoxType());
                            applyDetail.setCurrency(tgCustomsApplyDetail.getCurrency());
                            applyDetail.setIsDeal("是");
                            applyDetail.setIsSelect(Boolean.TRUE);//明细数据是否选中
                            applyDetail.setId(tgCustomsApplyDetail.getId());
                        } else {
                            if(boxTypeList.contains(applyDetail.getBoxType())){
                                hasBoxType = Boolean.TRUE;
                            }
                            mainIsSelect = Boolean.FALSE;
                        }
                        applyDetail.setHasBoxType(hasBoxType);
                    }
                    packing.setIsSelect(mainIsSelect);//主数据是否选中
                    packing.setApplyPackDetailList(packingDetailList);
                }
            }
        }
        return ResponseData.success(packingList);
    }

    @Override
    @DataSource(name = "EBMS")
    public ResponseData packCodeSelect() {
        return ResponseData.success(mapper.packCodeSelect());
    }

    @Override
    @DataSource(name = "EBMS")
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public List<TgLogisticsPackingResult> selectLogisticsPackingShipment(List<String> packCodeList){
        return mapper.selectLogisticsPackingShipment(packCodeList);
    }

    @Override
    @DataSource(name = "logistics")
    @Transactional(rollbackFor = Exception.class)
    public ResponseData selectSave(TgCustomsApplySaveParam param) {
        log.info("报关单关联新增保存入参[{}]", JSONObject.toJSON(param));
        String name = LoginContext.me().getLoginUser().getName();
        TgCustomsApply apply = new TgCustomsApply();
        BeanUtils.copyProperties(param, apply);
        apply.setSigningAddr("深圳");
        apply.setApplyDate(DateUtil.date());
        apply.setExportDate(DateUtil.date());
        apply.setTradeMode("一般贸易");
        apply.setExemptionNature("一般征税");
        apply.setSettlementType("电汇");
        apply.setPackageType("纸箱");
        apply.setSourceAddr("深圳特区");
//        apply.setDirectPort(apply.getArrivalCountryName());
        apply.setDataType(TgDataTypeEnum.MERGE.getCode());
        apply.setCreateUser(name);

        if(CollectionUtil.isEmpty(param.getApplyDetails())){
            return ResponseData.error("报关单明细数据为空！");
        }

        //获取MC报关外箱信息
        List<TgBoxInfo> tgBoxInfoList = tgBoxInfoService.list();
        List<String> boxTypeList = tgBoxInfoList.stream().map(i -> i.getBoxType()).collect(Collectors.toList());

        //获取MC自定义报关外箱信息
        List<TgCustomBoxInfo> boxTypeSelect = tgCustomBoxInfoService.list();
        if(CollectionUtil.isNotEmpty(boxTypeSelect)){
            boxTypeList.add(boxTypeSelect.get(0).getBoxType());
        }

        List<TgCustomsApplyDetail> applyDetailList = new ArrayList<>();
        for (TgCustomsApplyDetail applyDetail : param.getApplyDetails()) {
            //MC是否存在此箱型
            if(!boxTypeList.contains(applyDetail.getBoxType())){
                return ResponseData.error("报关外箱不存在此箱型：" + applyDetail.getBoxType());
            }

            if("否".equals(applyDetail.getIsDeal()) && applyDetail.getIsSelect()){
                //根据物料编码取产品基本信息的海关编码和要素
                if(StringUtils.isBlank(applyDetail.getMaterialCode())){
                    return ResponseData.error(applyDetail.getMaterialCode() + "物料编码为空！");
                }
                LambdaQueryWrapper<TgBaseProduct> productWrapper = new LambdaQueryWrapper<>();
                productWrapper.eq(TgBaseProduct :: getDataType, TgDataTypeEnum.NOT_MERGE.getCode())
                        .eq(TgBaseProduct :: getMaterialCode, applyDetail.getMaterialCode())
                        .eq(TgBaseProduct :: getAuditStatus, TgAuditStatusEnum.PASS.getCode());
                TgBaseProduct baseProduct = productService.getOne(productWrapper);
                if(baseProduct == null){
                    return ResponseData.error("未查询到" + applyDetail.getMaterialCode() + "对应的审核通过的产品基本信息！");
                }
                applyDetail.setCustomsNum(baseProduct.getCustomsCode());
                applyDetail.setDataType(apply.getDataType());
                applyDetail.setEssentialFactor(baseProduct.getEssentialFactor());
                applyDetail.setCreateUser(name);
                applyDetailList.add(applyDetail);
            }
        }

        if(CollectionUtil.isNotEmpty(applyDetailList)){
            Map<String, List<TgCustomsApplyDetail>> detailListMap = applyDetailList.stream().collect(Collectors.groupingBy(detail -> detail.getPackCode() + detail.getBoxNo(), LinkedHashMap::new, Collectors.toList()));
            BigDecimal packageWeight = BigDecimal.ZERO;
            for (String key : detailListMap.keySet()) {
                List<TgCustomsApplyDetail> detailList = detailListMap.get(key);
                if(detailList.size() > 0){
                    packageWeight = packageWeight.add(detailList.get(0).getWeight());
                }
            }
            apply.setBoxNum(new BigDecimal(detailListMap.size()));
            apply.setPackageWeight(packageWeight);

            //生成报关单合同协议号
            apply.setSigningNo(this.getNowBgdOrder());
            mapper.insert(apply);

            for (TgCustomsApplyDetail applyDetail : applyDetailList) {
                applyDetail.setPid(apply.getId());
                applyDetailService.save(applyDetail);
            }
        }
        return ResponseData.success();
    }

    @Override
    @DataSource(name = "logistics")
    @Transactional(rollbackFor = Exception.class)
    public ResponseData selectEdit(TgCustomsApplySaveParam param) {
        log.info("报关单关联编辑保存入参[{}]", JSONObject.toJSON(param));
        if(StringUtils.isBlank(String.valueOf(param.getId()))){
            return ResponseData.error("编辑ID为空！");
        }
        String name = LoginContext.me().getLoginUser().getName();
        TgCustomsApply apply = new TgCustomsApply();
        BeanUtils.copyProperties(param, apply);
        apply.setDataType(TgDataTypeEnum.MERGE.getCode());
        if(CollectionUtil.isEmpty(param.getApplyDetails())){
            apply.setUpdateTime(DateUtil.date());
            apply.setUpdateUser(name);
            mapper.updateById(apply);
        } else {
            //获取MC报关外箱信息
            List<TgBoxInfo> tgBoxInfoList = tgBoxInfoService.list();
            List<String> boxTypeList = tgBoxInfoList.stream().map(i -> i.getBoxType()).collect(Collectors.toList());

            //获取MC自定义报关外箱信息
            List<TgCustomBoxInfo> boxTypeSelect = tgCustomBoxInfoService.list();
            if(CollectionUtil.isNotEmpty(boxTypeSelect)){
                boxTypeList.add(boxTypeSelect.get(0).getBoxType());
            }

            List<TgCustomsApplyDetail> saveOrUpdateDetailList = new ArrayList<>();
            List<BigDecimal> deleteList = new ArrayList<>();
            for (TgCustomsApplyDetail applyDetail : param.getApplyDetails()) {
                /**
                 * 已关联，勾选 --> 更新
                 * 已关联，未勾选 --> 删除
                 * 未关联，勾选 --> 新增
                 * 未关联，未勾选 --> 不做处理
                 */
                if("是".equals(applyDetail.getIsDeal())){
                    if(applyDetail.getIsSelect()){
                        //已关联，勾选 --> 更新
                        //MC是否存在此箱型
                        if(!boxTypeList.contains(applyDetail.getBoxType())){
                            return ResponseData.error("报关外箱不存在此箱型：" + applyDetail.getBoxType());
                        }
                        //根据物料编码取产品基本信息的海关编码和要素
                        if(StringUtils.isBlank(applyDetail.getMaterialCode())){
                            return ResponseData.error(applyDetail.getMaterialCode() + "物料编码为空！");
                        }
                        LambdaQueryWrapper<TgBaseProduct> productWrapper = new LambdaQueryWrapper<>();
                        productWrapper.eq(TgBaseProduct :: getDataType, TgDataTypeEnum.NOT_MERGE.getCode())
                                .eq(TgBaseProduct :: getMaterialCode, applyDetail.getMaterialCode())
                                .eq(TgBaseProduct :: getAuditStatus, TgAuditStatusEnum.PASS.getCode());
                        TgBaseProduct baseProduct = productService.getOne(productWrapper);
                        if(baseProduct == null){
                            return ResponseData.error("未查询到" + applyDetail.getMaterialCode() + "对应的产品基本信息！");
                        }
                        applyDetail.setCustomsNum(baseProduct.getCustomsCode());
                        applyDetail.setEssentialFactor(baseProduct.getEssentialFactor());
                        applyDetail.setUpdateUser(name);
                        applyDetail.setUpdateTime(DateUtil.date());
                        saveOrUpdateDetailList.add(applyDetail);
                    } else {
                        //已关联，未勾选 --> 删除
                        deleteList.add(applyDetail.getId());
                    }
                }

                if("否".equals(applyDetail.getIsDeal()) && applyDetail.getIsSelect()){
                    //未关联，勾选 --> 新增
                    //MC是否存在此箱型
                    if(!boxTypeList.contains(applyDetail.getBoxType())){
                        return ResponseData.error("报关外箱不存在此箱型：" + applyDetail.getBoxType());
                    }
                    //根据物料编码取产品基本信息的海关编码和要素
                    if(StringUtils.isBlank(applyDetail.getMaterialCode())){
                        return ResponseData.error(applyDetail.getPackCode() + "物料编码为空！");
                    }
                    LambdaQueryWrapper<TgBaseProduct> productWrapper = new LambdaQueryWrapper<>();
                    productWrapper.eq(TgBaseProduct :: getDataType, TgDataTypeEnum.NOT_MERGE.getCode())
                            .eq(TgBaseProduct :: getMaterialCode, applyDetail.getMaterialCode())
                            .eq(TgBaseProduct :: getAuditStatus, TgAuditStatusEnum.PASS.getCode());
                    TgBaseProduct baseProduct = productService.getOne(productWrapper);
                    if(baseProduct == null){
                        return ResponseData.error("未查询到" + applyDetail.getMaterialCode() + "对应的产品基本信息！");
                    }
                    applyDetail.setCustomsNum(baseProduct.getCustomsCode());
                    applyDetail.setDataType(apply.getDataType());
                    applyDetail.setEssentialFactor(baseProduct.getEssentialFactor());
                    applyDetail.setCreateUser(name);
                    saveOrUpdateDetailList.add(applyDetail);
                }
            }

            if(CollectionUtil.isNotEmpty(saveOrUpdateDetailList)){
                Map<String, List<TgCustomsApplyDetail>> detailListMap = saveOrUpdateDetailList.stream().collect(Collectors.groupingBy(detail -> detail.getPackCode() + detail.getBoxNo(), LinkedHashMap::new, Collectors.toList()));
                BigDecimal packageWeight = BigDecimal.ZERO;
                for (String key : detailListMap.keySet()) {
                    List<TgCustomsApplyDetail> detailList = detailListMap.get(key);
                    if(detailList.size() > 0){
                        packageWeight = packageWeight.add(detailList.get(0).getWeight());
                    }
                }
                apply.setBoxNum(new BigDecimal(detailListMap.size()));
                apply.setPackageWeight(packageWeight);
                apply.setUpdateTime(DateUtil.date());
                apply.setUpdateUser(name);
                mapper.updateById(apply);

                for (TgCustomsApplyDetail detail : saveOrUpdateDetailList) {
                    if(detail.getPid() == null){
                        detail.setPid(apply.getId());
                    }
                    applyDetailService.saveOrUpdate(detail);
                }

                if(CollectionUtil.isNotEmpty(deleteList)){
                    //删除已关联未勾选数据
                    applyDetailService.removeByIds(deleteList);
                }
            }

            //全量更新数据，取当前最新数据
            LambdaQueryWrapper<TgCustomsApplyDetail> adQueryWrapper = new LambdaQueryWrapper<>();
            adQueryWrapper.eq(TgCustomsApplyDetail :: getPid, apply.getId());
            List<TgCustomsApplyDetail> adList = applyDetailService.list(adQueryWrapper);
            if(CollectionUtil.isNotEmpty(adList)){
                List<TgCustomsApplyDetail> applyDetailList = new ArrayList<>();
                for (TgCustomsApplyDetail applyDetail : adList) {
                    //MC是否存在此箱型
                    if (!boxTypeList.contains(applyDetail.getBoxType())) {
                        TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                        return ResponseData.error("报关外箱不存在此箱型：" + applyDetail.getBoxType());
                    }

                    LambdaQueryWrapper<TgBaseProduct> productWrapper = new LambdaQueryWrapper<>();
                    productWrapper.eq(TgBaseProduct :: getDataType, TgDataTypeEnum.NOT_MERGE.getCode())
                            .eq(TgBaseProduct :: getMaterialCode, applyDetail.getMaterialCode())
                            .eq(TgBaseProduct :: getAuditStatus, TgAuditStatusEnum.PASS.getCode());
                    TgBaseProduct baseProduct = productService.getOne(productWrapper);
                    if(baseProduct == null){
                        TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                        return ResponseData.error("未查询到" + applyDetail.getMaterialCode() + "对应的审核通过的产品基本信息！");
                    }
                    applyDetail.setCustomsNum(baseProduct.getCustomsCode());
                    applyDetail.setEssentialFactor(baseProduct.getEssentialFactor());
                    applyDetail.setUpdateUser(name);
                    applyDetail.setUpdateTime(DateUtil.date());
                    applyDetailList.add(applyDetail);
                }

                Map<String, List<TgCustomsApplyDetail>> detailListMap = adList.stream().collect(Collectors.groupingBy(detail -> detail.getPackCode() + detail.getBoxNo(), LinkedHashMap::new, Collectors.toList()));
                BigDecimal packageWeight = BigDecimal.ZERO;
                for (String key : detailListMap.keySet()) {
                    List<TgCustomsApplyDetail> detailList = detailListMap.get(key);
                    if(detailList.size() > 0){
                        packageWeight = packageWeight.add(detailList.get(0).getWeight());
                    }
                }

                //更新主表箱子个数和毛重
                apply.setBoxNum(new BigDecimal(detailListMap.size()));
                apply.setPackageWeight(packageWeight);
                apply.setUpdateTime(DateUtil.date());
                apply.setUpdateUser(name);
                mapper.updateById(apply);

                //更新明细表海关编码和要素
                applyDetailService.updateBatchById(applyDetailList);
            }
        }
        return ResponseData.success();
    }

    @Override
    @DataSource(name = "logistics")
    public ResponseData importExcel(MultipartFile[] files) {
        int fileNo = 1;
        List<TgCustomsApplyDetail> applyPackDetailList = new ArrayList<>();
        List<TgCustomsApplyImportSheet0Param> sheet0ParamErrorList = new ArrayList<>();
        //判断不同文件是否有相同的出货清单号
        Set<String> allPackCodeSet = new HashSet<>();
        if(files.length < 1){
            return ResponseData.error("报关单导入文件为空，导入失败！");
        }
        for (MultipartFile file : files) {
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

                Set<String> packCodeSet = new HashSet<>();
                List<TgCustomsApplyImportSheet0Param> dataList0 = listener0.getDataList();
                log.info("报关单导入文件[{}]sheet0数据[{}]", fileNo, JSONObject.toJSON(dataList0));
                if(CollectionUtil.isEmpty(dataList0)){
                    return ResponseData.error("报关单导入文件[" + fileNo + "]sheet0导入数据为空，导入失败！");
                }
                for (TgCustomsApplyImportSheet0Param sheet0Param: dataList0) {
                    if(StringUtils.isAnyBlank(
                            sheet0Param.getPackCode(),
                            sheet0Param.getMaterialCode(),
                            sheet0Param.getBoxNo(),
                            sheet0Param.getUnit(),
                            sheet0Param.getCurrency())
                            || sheet0Param.getQuantity() == null){
                        return ResponseData.error("报关单导入文件[" + fileNo + "]sheet0出货清单号、物料编码、数量、数量单位、币制为必填项！");
                    }
                    if(!packCodeSet.contains(sheet0Param.getPackCode())){
                        packCodeSet.add(sheet0Param.getPackCode());
                    }
                }

                List<TgCustomsApplyImportSheet1Param> dataList1 = listener1.getDataList();
                log.info("报关单导入文件[{}]sheet1数据[{}]", fileNo, JSONObject.toJSON(dataList1));
                if(CollectionUtil.isEmpty(dataList1)){
                    return ResponseData.error("报关单导入文件[" + fileNo + "]sheet1导入数据为空，导入失败！");
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
                        return ResponseData.error("报关单导入文件[" + fileNo + "]sheet1出货清单号、箱号、箱型、重量、长、宽、高为必填项！");
                    }
                    if(!packCodeSet.contains(sheet1Param.getPackCode())){
                        packCodeSet.add(sheet1Param.getPackCode());
                    }
                }
                if(allPackCodeSet.contains(packCodeSet)){
                    return ResponseData.error("不同的文件存在相同的出货清单号");
                }
                allPackCodeSet.addAll(packCodeSet);

                ResponseData rd = this.mergeSheetData(dataList0, dataList1, applyPackDetailList, sheet0ParamErrorList);
                if(ResponseData.DEFAULT_ERROR_CODE.equals(rd.getCode())){
                    return rd;
                }
            } catch (Exception e) {
                log.error("报关单导入Excel文件[{}]处理异常，导入失败！", fileNo, e);
                return ResponseData.error("报关单导入Excel文件[" + fileNo + "]处理异常，导入失败！");
            } finally {
                fileNo ++;
                if(buffer != null){
                    try {
                        buffer.close();
                    } catch (IOException e) {
                        log.error("报关单导入Excel文件[{}]关闭流异常", fileNo, e);
                    }
                }
            }
        }

        if(CollectionUtil.isNotEmpty(sheet0ParamErrorList)){
            String fileName = dealImportErrorList(sheet0ParamErrorList);
            return ResponseData.success(ResponseData.DEFAULT_SUCCESS_CODE, fileName, applyPackDetailList);
        }
        return ResponseData.success(applyPackDetailList);
    }

    /**
     * 合并sheet数据
     * @param dataList0 sheet0数据
     * @param dataList1 sheet1数据
     * @param sheet0ParamErrorList 正常数据
     * @param sheet0ParamErrorList 异常数据
     * @return
     */
    private ResponseData mergeSheetData(List<TgCustomsApplyImportSheet0Param> dataList0, List<TgCustomsApplyImportSheet1Param> dataList1,
                                        List<TgCustomsApplyDetail> applyPackDetailList, List<TgCustomsApplyImportSheet0Param> sheet0ParamErrorList){
        //获取MC报关外箱信息
        List<TgBoxInfo> tgBoxInfoList = tgBoxInfoService.list();
        List<String> boxTypeList = tgBoxInfoList.stream().map(i -> i.getBoxType()).collect(Collectors.toList());

        //获取MC自定义报关外箱信息
        List<TgCustomBoxInfo> boxTypeSelect = tgCustomBoxInfoService.list();
        if(CollectionUtil.isNotEmpty(boxTypeSelect)){
            boxTypeList.add(boxTypeSelect.get(0).getBoxType());
        }

        //币别
        List<FixedExchangeRate> originalCurrencyList = fixedExchangeRateConsumer.originalCurrencyList();
        List<String> currencyList = originalCurrencyList.stream().map(i -> i.getOriginalCurrency()).collect(Collectors.toList());

        //出货清单号、箱号维度分组
        Map<String, List<TgCustomsApplyImportSheet1Param>> sheet1ListMap = dataList1.stream().collect(Collectors.groupingBy(sheet1Param -> sheet1Param.getPackCode() + sheet1Param.getBoxNo(), LinkedHashMap::new, Collectors.toList()));
        for (String key : sheet1ListMap.keySet()) {
            BigDecimal packageWeight = BigDecimal.ZERO;
            List<TgCustomsApplyImportSheet1Param> detailList = sheet1ListMap.get(key);
            for (TgCustomsApplyImportSheet1Param sheet1Param : detailList) {
                packageWeight = packageWeight.add(sheet1Param.getWeight());
            }

            //出货清单号、箱号相同，同箱号的重量相加，箱子个数累加，并取第一个箱子数据
            detailList.get(0).setPackageWeight(packageWeight);
            detailList.get(0).setBoxNum(new BigDecimal(detailList.size()));
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

        //预查询到审核通过的产品基本信息数据，没有的导出文件提示
        LambdaQueryWrapper<TgBaseProduct> passQueryWrapper = new LambdaQueryWrapper<>();
        passQueryWrapper.eq(TgBaseProduct :: getDataType, TgDataTypeEnum.NOT_MERGE.getCode())
                .in(TgBaseProduct :: getMaterialCode, matCodeList)
                .eq(TgBaseProduct :: getAuditStatus, TgAuditStatusEnum.PASS.getCode());
        List<TgBaseProduct> passProductList = productService.list(passQueryWrapper);
        if(CollectionUtil.isEmpty(passProductList)){
            return ResponseData.error("未查询到审核通过的产品基本信息数据，物料编码：" + JSONObject.toJSON(matCodeList));
        }
        Map<String, List<TgBaseProduct>> passProductListMap = passProductList.stream().collect(Collectors.groupingBy(product -> product.getMaterialCode(), LinkedHashMap::new, Collectors.toList()));


        for (TgCustomsApplyImportSheet0Param sheet0 : dataList0) {
            if(!currencyList.contains(sheet0.getCurrency())){
                return ResponseData.error("系统不存在此导入币制：" + sheet0.getCurrency());
            }

            TgCustomsApplyDetail detail = new TgCustomsApplyDetail();
            BeanUtils.copyProperties(sheet0, detail);
            List<TgBaseProduct> mBaseProductList = productListMap.get(sheet0.getMaterialCode());
            if(CollectionUtil.isEmpty(mBaseProductList)){
                return ResponseData.error("未查询到产品基本信息数据，物料编码：" + sheet0.getMaterialCode());
            }
            detail.setType(mBaseProductList.get(0).getFitBrand());
            detail.setStyle(mBaseProductList.get(0).getStyle());
            detail.setCompanyBrand(mBaseProductList.get(0).getCompanyBrand());
            detail.setInvoiceProNameCn(mBaseProductList.get(0).getInvoiceProNameCn());
            List<TgCustomsApplyImportSheet1Param> detailList = sheet1ListMap.get(detail.getPackCode() + detail.getBoxNo());
            if(CollectionUtil.isEmpty(detailList)){
                return ResponseData.error("未查询到sheet0出货清单号和箱号对应sheet1数据，出货清单号：" + detail.getPackCode() + "，箱号：" + detail.getBoxNo());
            }
            TgCustomsApplyImportSheet1Param sheet1ParamInfo = detailList.get(0);
            detail.setBoxNoName(sheet1ParamInfo.getBoxNoName());
            detail.setBoxType(sheet1ParamInfo.getBoxType());

            List<TgBaseProduct> mPassBaseProductList = passProductListMap.get(sheet0.getMaterialCode());
            if(CollectionUtil.isEmpty(mPassBaseProductList)){
                sheet0.setUploadRemark("未查询到审核通过的产品基本信息数据");
                sheet0ParamErrorList.add(sheet0);
            }
            if(StringUtils.isBlank(mBaseProductList.get(0).getCustomsCode())
                    || StringUtils.isBlank(mBaseProductList.get(0).getEssentialFactor())
                    || mBaseProductList.get(0).getTaxRefund() == null
                    || mBaseProductList.get(0).getGrossProfitRate() == null){
                sheet0.setUploadRemark("请先补充产品基本信息海关编码、要素、退税率、毛利率信息");
                sheet0ParamErrorList.add(sheet0);
            }

            //MC是否存在此箱型
            Boolean hasBoxType = Boolean.FALSE;
            if(boxTypeList.contains(detail.getBoxType())){
                hasBoxType = Boolean.TRUE;
            }
            detail.setHasBoxType(hasBoxType);
            detail.setWeight(sheet1ParamInfo.getPackageWeight());
            detail.setGoodsLong(sheet1ParamInfo.getGoodsLong());
            detail.setWide(sheet1ParamInfo.getWide());
            detail.setHigh(sheet1ParamInfo.getHigh());
            detail.setBoxNum(sheet1ParamInfo.getBoxNum());
            applyPackDetailList.add(detail);
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
            log.error("报关单导入异常数据导出异常", e);
        } finally {
            if(out != null){
                try {
                    out.close();
                } catch (IOException e) {
                    log.error("报关单导入异常数据导出流关闭异常", e);
                }
            }
        }
        return fileName;
    }

    @Override
    @DataSource(name = "logistics")
    @Transactional(rollbackFor = Exception.class)
    public ResponseData importSave(TgCustomsApplySaveParam param) {
        log.info("报关单导入新增保存入参[{}]", JSONObject.toJSON(param));
        String name = LoginContext.me().getLoginUser().getName();
        TgCustomsApply apply = new TgCustomsApply();
        BeanUtils.copyProperties(param, apply);
        apply.setSigningAddr("深圳");
        apply.setApplyDate(DateUtil.date());
        apply.setExportDate(DateUtil.date());
        apply.setTradeMode("一般贸易");
        apply.setExemptionNature("一般征税");
        apply.setSettlementType("电汇");
        apply.setPackageType("纸箱");
        apply.setSourceAddr("深圳特区");
//        apply.setDirectPort(apply.getArrivalCountryName());
        apply.setDataType(TgDataTypeEnum.NOT_MERGE.getCode());
        apply.setCreateUser(name);

        if(CollectionUtil.isEmpty(param.getApplyDetails())){
            return ResponseData.error("报关单明细数据为空！");
        }

        //获取MC报关外箱信息
        List<TgBoxInfo> tgBoxInfoList = tgBoxInfoService.list();
        List<String> boxTypeList = tgBoxInfoList.stream().map(i -> i.getBoxType()).collect(Collectors.toList());

        //获取MC自定义报关外箱信息
        List<TgCustomBoxInfo> boxTypeSelect = tgCustomBoxInfoService.list();
        if(CollectionUtil.isNotEmpty(boxTypeSelect)){
            boxTypeList.add(boxTypeSelect.get(0).getBoxType());
        }

        List<TgCustomsApplyDetail> applyDetailList = new ArrayList<>();
        for (TgCustomsApplyDetail applyDetail : param.getApplyDetails()) {
            //MC是否存在此箱型
            if(!boxTypeList.contains(applyDetail.getBoxType())){
                return ResponseData.error("报关外箱不存在此箱型：" + applyDetail.getBoxType());
            }
            //根据物料编码取产品基本信息的海关编码和要素
            if(StringUtils.isBlank(applyDetail.getMaterialCode())){
                return ResponseData.error(applyDetail.getMaterialCode() + "物料编码为空！");
            }
            LambdaQueryWrapper<TgBaseProduct> productWrapper = new LambdaQueryWrapper<>();
            productWrapper.eq(TgBaseProduct :: getDataType, TgDataTypeEnum.NOT_MERGE.getCode())
                    .eq(TgBaseProduct :: getMaterialCode, applyDetail.getMaterialCode())
                    .eq(TgBaseProduct :: getAuditStatus, TgAuditStatusEnum.PASS.getCode());
            TgBaseProduct baseProduct = productService.getOne(productWrapper);
            if(baseProduct == null){
                return ResponseData.error("未查询到" + applyDetail.getMaterialCode() + "对应的审核通过的产品基本信息！");
            }
            applyDetail.setCustomsNum(baseProduct.getCustomsCode());
            applyDetail.setDataType(apply.getDataType());
            applyDetail.setEssentialFactor(baseProduct.getEssentialFactor());
            applyDetail.setCreateUser(name);
            applyDetailList.add(applyDetail);
        }

        if(CollectionUtil.isNotEmpty(applyDetailList)){
            Map<String, List<TgCustomsApplyDetail>> detailListMap = applyDetailList.stream().collect(Collectors.groupingBy(detail -> detail.getPackCode()+detail.getBoxNo(), LinkedHashMap::new, Collectors.toList()));
            BigDecimal packageWeight = BigDecimal.ZERO;
            for (String key : detailListMap.keySet()) {
                List<TgCustomsApplyDetail> detailList = detailListMap.get(key);
                if(detailList.size() > 0){
                    packageWeight = packageWeight.add(detailList.get(0).getWeight());
                }
            }
            apply.setBoxNum(new BigDecimal(detailListMap.size()));
            apply.setPackageWeight(packageWeight);

            //生成报关单合同协议号
            apply.setSigningNo(this.getNowBgdOrder());
            mapper.insert(apply);

            for (TgCustomsApplyDetail applyDetail : applyDetailList) {
                applyDetail.setPid(apply.getId());
                applyDetailService.save(applyDetail);
            }
        }
        return ResponseData.success();
    }

    @Override
    @DataSource(name = "logistics")
    @Transactional(rollbackFor = Exception.class)
    public ResponseData importEdit(TgCustomsApplySaveParam param) {
        log.info("报关单导入编辑保存入参[{}]", JSONObject.toJSON(param));
        if(StringUtils.isBlank(String.valueOf(param.getId()))){
            return ResponseData.error("编辑ID为空！");
        }
        String name = LoginContext.me().getLoginUser().getName();
        TgCustomsApply apply = new TgCustomsApply();
        BeanUtils.copyProperties(param, apply);
        apply.setDataType(TgDataTypeEnum.NOT_MERGE.getCode());
//        apply.setDirectPort(apply.getArrivalCountryName());

        if(CollectionUtil.isEmpty(param.getApplyDetails())){
            return ResponseData.error("报关单明细数据为空！");
        }

        //获取MC报关外箱信息
        List<TgBoxInfo> tgBoxInfoList = tgBoxInfoService.list();
        List<String> boxTypeList = tgBoxInfoList.stream().map(i -> i.getBoxType()).collect(Collectors.toList());

        //获取MC自定义报关外箱信息
        List<TgCustomBoxInfo> boxTypeSelect = tgCustomBoxInfoService.list();
        if(CollectionUtil.isNotEmpty(boxTypeSelect)){
            boxTypeList.add(boxTypeSelect.get(0).getBoxType());
        }

        //报关单编辑导入明细保存是否为新导入数据 0：否，1：是，新数据则删除旧的数据，再保存新的数据
        if("1".equals(param.getImportNew())){
            List<TgCustomsApplyDetail> applyDetailList = new ArrayList<>();
            for (TgCustomsApplyDetail applyDetail : param.getApplyDetails()) {
                //MC是否存在此箱型
                if(!boxTypeList.contains(applyDetail.getBoxType())){
                    return ResponseData.error("报关外箱不存在此箱型：" + applyDetail.getBoxType());
                }
                //根据物料编码取产品基本信息的海关编码和要素
                if(StringUtils.isBlank(applyDetail.getMaterialCode())){
                    return ResponseData.error(applyDetail.getMaterialCode() + "物料编码为空！");
                }
                LambdaQueryWrapper<TgBaseProduct> productWrapper = new LambdaQueryWrapper<>();
                productWrapper.eq(TgBaseProduct :: getDataType, TgDataTypeEnum.NOT_MERGE.getCode())
                        .eq(TgBaseProduct :: getMaterialCode, applyDetail.getMaterialCode())
                        .eq(TgBaseProduct :: getAuditStatus, TgAuditStatusEnum.PASS.getCode());
                TgBaseProduct baseProduct = productService.getOne(productWrapper);
                if(baseProduct == null){
                    return ResponseData.error("未查询到" + applyDetail.getMaterialCode() + "对应的审核通过的产品基本信息！");
                }
                applyDetail.setCustomsNum(baseProduct.getCustomsCode());
                applyDetail.setDataType(apply.getDataType());
                applyDetail.setEssentialFactor(baseProduct.getEssentialFactor());
                applyDetail.setUpdateUser(name);
                applyDetail.setUpdateTime(DateUtil.date());
                applyDetailList.add(applyDetail);
            }

            if(CollectionUtil.isNotEmpty(applyDetailList)){
                Map<String, List<TgCustomsApplyDetail>> detailListMap = applyDetailList.stream().collect(Collectors.groupingBy(detail -> detail.getPackCode()+detail.getBoxNo(), LinkedHashMap::new, Collectors.toList()));
                BigDecimal packageWeight = BigDecimal.ZERO;
                for (String key : detailListMap.keySet()) {
                    List<TgCustomsApplyDetail> detailList = detailListMap.get(key);
                    if(detailList.size() > 0){
                        packageWeight = packageWeight.add(detailList.get(0).getWeight());
                    }
                }
                apply.setBoxNum(new BigDecimal(detailListMap.size()));
                apply.setPackageWeight(packageWeight);
                apply.setCreateUser(name);
                mapper.updateById(apply);

                LambdaQueryWrapper<TgCustomsApplyDetail> detailWrapper = new LambdaQueryWrapper<>();
                detailWrapper.eq(TgCustomsApplyDetail :: getDataType, TgDataTypeEnum.NOT_MERGE.getCode())
                        .eq(TgCustomsApplyDetail :: getPid, apply.getId());
                applyDetailService.remove(detailWrapper);

                for (TgCustomsApplyDetail applyDetail : applyDetailList) {
                    applyDetail.setPid(apply.getId());
                    applyDetailService.save(applyDetail);
                }
            }
        } else if("0".equals(param.getImportNew())){
            apply.setUpdateUser(name);
            apply.setUpdateTime(DateUtil.date());
            mapper.updateById(apply);

            List<TgCustomsApplyDetail> applyDetailList = new ArrayList<>();
            for (TgCustomsApplyDetail applyDetail : param.getApplyDetails()) {
                //根据物料编码取产品基本信息的海关编码和要素
                if(StringUtils.isBlank(applyDetail.getMaterialCode())){
                    return ResponseData.error(applyDetail.getMaterialCode() + "物料编码为空！");
                }
                TgCustomsApplyDetail detail = applyDetailService.getById(applyDetail);
                if(!applyDetail.getBoxType().equals(detail.getBoxType())){
                    //MC是否存在此箱型
                    if(!boxTypeList.contains(applyDetail.getBoxType())){
                        return ResponseData.error("报关外箱不存在此箱型：" + applyDetail.getBoxType());
                    }

                    applyDetail.setBoxType(applyDetail.getBoxType());
                    applyDetail.setUpdateUser(name);
                    applyDetail.setUpdateTime(DateUtil.date());
                    applyDetailList.add(applyDetail);
                }
            }
            if(CollectionUtil.isNotEmpty(applyDetailList)){
                applyDetailService.updateBatchById(applyDetailList);
            }
        }
        return ResponseData.success();
    }

    @Override
    @DataSource(name = "logistics")
    @Transactional
    public void generateCustomsApply(TgCustomsApplyParam param, HttpServletResponse response) throws IOException {
        log.info("生成国内报关单开始[{}]", JSONObject.toJSON(param));
        long start = System.currentTimeMillis();
        DecimalFormat oneDf = new DecimalFormat("0.0");
        DecimalFormat twoDf = new DecimalFormat("0.00");
        DecimalFormat fourDf = new DecimalFormat("0.0000");
        ExcelWriter writer = null;
        try {
            TgCustomsApply apply = mapper.selectById(param.getId());
            if(apply == null){
                response.setHeader("code", "500");
                response.setHeader("message", URLEncoder.encode("国内报关单数据不存在！", "UTF-8"));
                return;
            }
            apply.setApplyDateStr(DateUtil.format(apply.getApplyDate(), DatePattern.NORM_DATE_PATTERN));
            apply.setExportDateStr(DateUtil.format(apply.getExportDate(), DatePattern.NORM_DATE_PATTERN));
            apply.setExport1PackageDate(DateUtil.format(DateUtil.offsetDay(apply.getApplyDate(), +20), DatePattern.NORM_DATE_PATTERN));

            QueryWrapper<TgCustomsApplyDetail> detailWrapper = new QueryWrapper<>();
            detailWrapper.select("PACK_CODE,BOX_TYPE,BOX_NO,MAX(GOODS_LONG) AS GOODS_LONG, MAX(WIDE) AS WIDE, MAX(HIGH) AS HIGH");
            detailWrapper.eq("PID", param.getId())
                    .groupBy("PACK_CODE,BOX_TYPE,BOX_NO");
            List<TgCustomsApplyDetail> boxTypeGroupList = applyDetailService.list(detailWrapper);
            if(CollectionUtil.isEmpty(boxTypeGroupList)){
                response.setHeader("code", "500");
                response.setHeader("message", URLEncoder.encode("国内报关单明细数据不存在！", "UTF-8"));
                return;
            }

            //获取报关单信息缺失明细数据，存在则提示，不能生成国内报关单
            TgCustomsApplyDetail detailParam = new TgCustomsApplyDetail();
            detailParam.setPid(param.getId());
            List<TgCustomsApplyDetail> incompleteDetailList = applyDetailService.getIncompleteDetail(detailParam);
            if(CollectionUtil.isNotEmpty(incompleteDetailList)){
                Set<String> materialCodeSet = incompleteDetailList.stream().map(i -> i.getMaterialCode()).collect(Collectors.toSet());
                response.setHeader("code", "500");
                response.setHeader("message", URLEncoder.encode("国内报关单明细数据的物料编码：" + StringUtils.join(materialCodeSet, ',') + "开票品名、开票规格型号信息不完善！", "UTF-8"));
                return;
            }

            //获取MC报关外箱信息
            List<TgBoxInfo> tgBoxInfoList = tgBoxInfoService.listTgBoxInfo();
            Map<String, List<TgBoxInfo>> boxInfoMap = tgBoxInfoList.stream().collect(Collectors.groupingBy(TgBoxInfo :: getBoxType, LinkedHashMap::new, Collectors.toList()));
            //获取MC自定义报关外箱信息
            List<TgCustomBoxInfo> tgCustomBoxInfoList = tgCustomBoxInfoService.listTgBoxInfo();

            //sheet0
            //所有箱子重
            BigDecimal totalBoxWeight = BigDecimal.ZERO;
            for (TgCustomsApplyDetail applyDetail : boxTypeGroupList) {
                if("自定义箱型".equals(applyDetail.getBoxType())){
                    //自定义箱型
                    if(CollectionUtil.isEmpty(tgCustomBoxInfoList)){
                        response.setHeader("code", "500");
                        response.setHeader("message", URLEncoder.encode("国内报关单自定义箱型（箱型：" + applyDetail.getBoxType() + "）数据不存在！", "UTF-8"));
                        return;
                    }
                    BigDecimal goodsVolume = applyDetail.getGoodsLong().multiply(applyDetail.getWide()).multiply(applyDetail.getHigh()).divide(new BigDecimal(1000000), 3, BigDecimal.ROUND_HALF_UP);
                    Boolean isContain = Boolean.FALSE;
                    for (TgCustomBoxInfo customBoxInfo : tgCustomBoxInfoList) {
                        BigDecimal minVolume = customBoxInfo.getMinVolume();
                        String minEq = customBoxInfo.getMinEq();
                        BigDecimal maxVolume = customBoxInfo.getMaxVolume();
                        String maxEq = customBoxInfo.getMaxEq();
                        BigDecimal boxWeight = customBoxInfo.getBoxWeight();
                        if("0".equals(minEq) && "0".equals(maxEq) && goodsVolume.compareTo(minVolume) > 0 && goodsVolume.compareTo(maxVolume) < 0){
                            isContain = Boolean.TRUE;
                            totalBoxWeight = totalBoxWeight.add(boxWeight);
                            break;
                        }
                        if("1".equals(minEq) && "1".equals(maxEq) && goodsVolume.compareTo(minVolume) >= 0 && goodsVolume.compareTo(maxVolume) <= 0){
                            isContain = Boolean.TRUE;
                            totalBoxWeight = totalBoxWeight.add(boxWeight);
                            break;
                        }
                        if("0".equals(minEq) && "1".equals(maxEq) && goodsVolume.compareTo(minVolume) > 0 && goodsVolume.compareTo(maxVolume) <= 0){
                            isContain = Boolean.TRUE;
                            totalBoxWeight = totalBoxWeight.add(boxWeight);
                            break;
                        }
                        if("1".equals(minEq) && "0".equals(maxEq) && goodsVolume.compareTo(minVolume) >= 0 && goodsVolume.compareTo(maxVolume) < 0){
                            isContain = Boolean.TRUE;
                            totalBoxWeight = totalBoxWeight.add(boxWeight);
                            break;
                        }
                    }
                    if(!isContain){
                        response.setHeader("code", "500");
                        response.setHeader("message", URLEncoder.encode("国内报关单体积（" + goodsVolume + "）对应的自定义箱型（箱型：" + applyDetail.getBoxType() + "）数据不存在！", "UTF-8"));
                        return;
                    }
                } else {
                    //非自定义箱型
                    List<TgBoxInfo> boxInfoList = boxInfoMap.get(applyDetail.getBoxType());
                    if(CollectionUtil.isEmpty(boxInfoList)){
                        response.setHeader("code", "500");
                        response.setHeader("message", URLEncoder.encode("国内报关单报关外箱（箱型：" + applyDetail.getBoxType() + "）数据不存在！", "UTF-8"));
                        return;
                    }
                    totalBoxWeight = totalBoxWeight.add(boxInfoList.get(0).getBoxWeight());
                }
            }

            //查询ERP固定汇率表
            FixedExchangeRateParam rateParam  = new FixedExchangeRateParam();
            rateParam.setEffectDate(apply.getApplyDate());
            List<FixedExchangeRate> fixedExchangeRateList = fixedExchangeRateConsumer.getFixedExchangeRateList(rateParam);
            if(CollectionUtil.isEmpty(fixedExchangeRateList)){
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(apply.getApplyDate());
                calendar.set(Calendar.DAY_OF_MONTH, -15);
                Date selectDate = calendar.getTime();
                rateParam.setEffectDate(selectDate);
                fixedExchangeRateList = fixedExchangeRateConsumer.getFixedExchangeRateList(rateParam);
            }
            Map<String, List<FixedExchangeRate>> fixedExchangeRateMap = fixedExchangeRateList.stream().collect(Collectors.groupingBy(FixedExchangeRate :: getOriginalCurrency, LinkedHashMap::new, Collectors.toList()));

            apply.setTotalNetWeightStr(apply.getPackageWeight().subtract(totalBoxWeight).setScale(0, BigDecimal.ROUND_HALF_UP));
            apply.setPackageWeightStr(apply.getPackageWeight().setScale(0, BigDecimal.ROUND_HALF_UP));

            List<TgCustomsApplyExport0ListResult> export0List = new ArrayList<>();
            List<TgCustomsApplyExport0ListResult> export0Result = mapper.generateExport0(param);
            if(CollectionUtil.isEmpty(export0Result)){
                response.setHeader("code", "500");
                response.setHeader("message", URLEncoder.encode("国内报关单报关单数据不存在！", "UTF-8"));
                return;
            }
            //报关单总价合计
            BigDecimal export0TotalAmount = BigDecimal.ZERO;
            //报关单sheet0总的数量
            BigDecimal sheet0TotalQuantity = export0Result.stream().map(TgCustomsApplyExport0ListResult :: getTotalQuantity).reduce(BigDecimal.ZERO, BigDecimal :: add);
            //关联获取产品基本信息的K3价格、是否含税和重量单位
            List<TgCustomsApplyExport0ListResult> productInfoList = mapper.getBaseProductInfo(param);
            Map<String, List<TgCustomsApplyExport0ListResult>> productInfoMap = productInfoList.stream().collect(Collectors.groupingBy(TgCustomsApplyExport0ListResult :: getGroupStr, LinkedHashMap::new, Collectors.toList()));
            for (TgCustomsApplyExport0ListResult result : export0Result) {
                List<TgCustomsApplyExport0ListResult> subProductInfoList = productInfoMap.get(result.getGroupStr());
                if(CollectionUtil.isEmpty(subProductInfoList)){
                    response.setHeader("code", "500");
                    response.setHeader("message", URLEncoder.encode("国内报关单报关单物料编码对应产品基本信息不存在！", "UTF-8"));
                    return;
                }

                //总价：开票品名+海关编码+要素+数量单位+套装属性一致的产品，每个含税的物料编码的K3价格/1.13/(1-毛利率)/间接汇率*数量（每个不含税的物料编码的K3价格/(1-毛利率)*间接汇率*数量），多个物料编码的情况把算出来的价格汇总后，转换为报关币制，最多2位小数，四舍五入
                BigDecimal totalAmount = BigDecimal.ZERO;
                for (TgCustomsApplyExport0ListResult subProductInfo : subProductInfoList) {
                    if(subProductInfo.getK3Price() == null){
                        response.setHeader("code", "500");
                        response.setHeader("message", URLEncoder.encode("国内报关单报关单物料编码" + subProductInfo.getMaterialCode() + "对应产品基本信息采购价不存在！", "UTF-8"));
                        return;
                    }
                    if(subProductInfo.getGrossProfitRate() == null){
                        response.setHeader("code", "500");
                        response.setHeader("message", URLEncoder.encode("国内报关单报关单物料编码" + subProductInfo.getMaterialCode() + "对应产品基本信息毛利率不存在！", "UTF-8"));
                        return;
                    }

                    List<FixedExchangeRate> rateList = fixedExchangeRateMap.get(subProductInfo.getCurrency());
                    if(CollectionUtil.isEmpty(rateList)){
                        response.setHeader("code", "500");
                        response.setHeader("message", URLEncoder.encode("国内报关单报关单" + subProductInfo.getCurrency() + "汇率信息不存在！", "UTF-8"));
                        return;
                    }
                    //固定汇率的间接汇率
                    BigDecimal indirectRate = rateList.get(0).getIndirectRate();
                    BigDecimal subTotalAmount;
                    if("1".equals(subProductInfo.getIncludeTax())){
                        //含税
                        subTotalAmount = subProductInfo.getK3Price()
                                .divide(new BigDecimal("1.13"), 4, BigDecimal.ROUND_HALF_UP)
                                .divide((BigDecimal.ONE.subtract(subProductInfo.getGrossProfitRate())), 4, BigDecimal.ROUND_HALF_UP)
                                .multiply(indirectRate)
                                .multiply(subProductInfo.getTotalQuantity()).setScale(2, BigDecimal.ROUND_HALF_UP);
                    } else {
                        //不含税
                        subTotalAmount = subProductInfo.getK3Price()
                                .divide((BigDecimal.ONE.subtract(subProductInfo.getGrossProfitRate())), 4, BigDecimal.ROUND_HALF_UP)
                                .multiply(indirectRate)
                                .multiply(subProductInfo.getTotalQuantity()).setScale(2, BigDecimal.ROUND_HALF_UP);
                    }
                    totalAmount = totalAmount.add(subTotalAmount);
                }
                //单价：总价/数量
                BigDecimal unitPrice = totalAmount.divide(result.getTotalQuantity(), 4, BigDecimal.ROUND_HALF_UP);
                result.setTotalAmountStr(twoDf.format(totalAmount));
                export0TotalAmount = export0TotalAmount.add(totalAmount);
                result.setUnitPriceStr(fourDf.format(unitPrice));
                result.setCurrencyCn(fixedExchangeRateMap.get(result.getCurrency()).get(0).getOriginalCurrencyName());

                //净重：总毛重（毛重KG/总数量的总和*总数量）-所有箱子重（每个箱子的箱型刷报关外箱包装，取箱重，并合计）/总数量的总和*总数量，保留1位小数，第2位小数进1位到第一位小数
                BigDecimal subGrossWeight = apply.getPackageWeight().divide(sheet0TotalQuantity, 4, BigDecimal.ROUND_HALF_UP).multiply(result.getTotalQuantity());
                BigDecimal subBoxWeight = totalBoxWeight.divide(sheet0TotalQuantity, 4, BigDecimal.ROUND_HALF_UP).multiply(result.getTotalQuantity());
                //小数第二位非0向上取入，保留1位小数，小数点后0.1~0.4进位成0.5，0.5不变，0.6~0.9向前进位1
                result.setNetWeight(subGrossWeight.subtract(subBoxWeight).setScale(1, BigDecimal.ROUND_UP));
                result.setSourceCountry("中国");
                if(result.getGoodsName().contains(":") || result.getGoodsName().contains("：")){
                    int idx = result.getGoodsName().indexOf(":");
                    if(idx == -1){
                        idx = result.getGoodsName().indexOf("：");
                    }

                    result.setGoodsName(result.getGoodsName().substring(idx + 1));
                    result.setGoodsNameTemp(result.getGoodsName());
                }
                result.setTotalQuantityStr(result.getTotalQuantity().toString());
                export0List.add(result);
                TgCustomsApplyExport0ListResult essentialFactor = new TgCustomsApplyExport0ListResult();
                String subEssentialFactor = result.getEssentialFactor().substring(result.getEssentialFactor().indexOf("|") + 1);
                String[] subEfArr = subEssentialFactor.split("\\|");
                StringBuffer subEfValBf = new StringBuffer();
                for (String subEf : subEfArr) {
                    int idx = subEf.indexOf(":");
                    if(idx == -1){
                        idx = subEf.indexOf("：");
                    }
                    subEfValBf.append(subEf.substring(idx + 1)).append("|");
                }
                if(StringUtils.isNotBlank(result.getAttribute())){
                    essentialFactor.setGoodsName(subEfValBf.substring(0, subEfValBf.length()-1) + "|" + result.getAttribute());
                    essentialFactor.setGoodsNameTemp(essentialFactor.getGoodsName());
                } else {
                    essentialFactor.setGoodsName(subEfValBf.substring(0, subEfValBf.length()-1));
                    essentialFactor.setGoodsNameTemp(essentialFactor.getGoodsName());
                }
                //格式化
                String netWeightStr = oneDf.format(result.getNetWeight());
                String[] nwArr = netWeightStr.split("\\.");
                String nwDeciPreStr = nwArr[0];
                BigDecimal nwDeciPre = new BigDecimal(nwDeciPreStr);
                String nwDeciStr = nwArr[1];
                BigDecimal nwDeci = new BigDecimal(nwDeciStr);
                if(nwDeci.compareTo(BigDecimal.ZERO) > 0 && nwDeci.compareTo(new BigDecimal(5)) < 0){
                    result.setNetWeight(new BigDecimal(nwDeciPreStr + "." + "5"));
                } else if(nwDeci.compareTo(new BigDecimal(5)) > 0 && nwDeci.compareTo(new BigDecimal(10)) < 0){
                    result.setNetWeight(new BigDecimal(nwDeciPre.add(BigDecimal.ONE) + "." + "0"));
                }
                essentialFactor.setTotalQuantityStr(oneDf.format(result.getNetWeight()));
                essentialFactor.setUnit("千克");
                essentialFactor.setIsMergeUpCell("0");
                essentialFactor.setSourceCountry("（CHN）");
                export0List.add(essentialFactor);
            }
            //设置sheet0总价合计
            apply.setExport0TotalAmountStr(twoDf.format(export0TotalAmount));

            //处理最后一行且最后一单元格的数据，需删除合并标识单元格
            TgCustomsApplyExport0ListResult export0LastData = export0List.get(export0List.size() - 1);
            export0LastData.setIsMergeUpCell("-1");

            //sheet1
            List<TgCustomsApplyExport3ListResult> export1List = new ArrayList<>();
            List<TgCustomsApplyExport3ListResult> export1Result = mapper.generateExport3(param);
            if(CollectionUtil.isEmpty(export1Result)){
                response.setHeader("code", "500");
                response.setHeader("message", URLEncoder.encode("国内报关单合同数据不存在！", "UTF-8"));
                return;
            }

            //sheet1总值
            BigDecimal export1TotalAmount = BigDecimal.ZERO;
            for (TgCustomsApplyExport3ListResult result : export1Result) {
                List<TgCustomsApplyExport0ListResult> subProductInfoList = productInfoMap.get(result.getGroupStr());
                if(CollectionUtil.isEmpty(subProductInfoList)){
                    response.setHeader("code", "500");
                    response.setHeader("message", URLEncoder.encode("国内报关单合同物料编码对应产品基本信息不存在！", "UTF-8"));
                    return;
                }

                //总价：开票品名+海关编码+要素+数量单位+套装属性一致的产品，每个含税的物料编码的K3价格/1.13/(1-毛利率)/间接汇率*数量（每个不含税的物料编码的K3价格/(1-毛利率)*间接汇率*数量），多个物料编码的情况把算出来的价格汇总后，转换为报关币制，最多2位小数，四舍五入
                BigDecimal totalAmount = BigDecimal.ZERO;
                for (TgCustomsApplyExport0ListResult subProductInfo : subProductInfoList) {
                    List<FixedExchangeRate> rateList = fixedExchangeRateMap.get(subProductInfo.getCurrency());
                    if(CollectionUtil.isEmpty(rateList)){
                        response.setHeader("code", "500");
                        response.setHeader("message", URLEncoder.encode("国内报关单合同" + subProductInfo.getCurrency() + "汇率信息不存在！", "UTF-8"));
                        return;
                    }
                    //固定汇率的间接汇率
                    BigDecimal indirectRate = rateList.get(0).getIndirectRate();
                    BigDecimal subTotalAmount;
                    if("1".equals(subProductInfo.getIncludeTax())){
                        //含税
                        subTotalAmount = subProductInfo.getK3Price()
                                .divide(new BigDecimal("1.13"), 4, BigDecimal.ROUND_HALF_UP)
                                .divide((BigDecimal.ONE.subtract(subProductInfo.getGrossProfitRate())), 4, BigDecimal.ROUND_HALF_UP)
                                .multiply(indirectRate)
                                .multiply(subProductInfo.getTotalQuantity()).setScale(2, BigDecimal.ROUND_HALF_UP);
                    } else {
                        //不含税
                        subTotalAmount = subProductInfo.getK3Price()
                                .divide((BigDecimal.ONE.subtract(subProductInfo.getGrossProfitRate())), 4, BigDecimal.ROUND_HALF_UP)
                                .multiply(indirectRate)
                                .multiply(subProductInfo.getTotalQuantity()).setScale(2, BigDecimal.ROUND_HALF_UP);
                    }
                    totalAmount = totalAmount.add(subTotalAmount);
                }
                //单价：总价/数量
                BigDecimal unitPrice = totalAmount.divide(result.getTotalQuantity(), 4, BigDecimal.ROUND_HALF_UP);
                result.setTotalAmountStr(twoDf.format(totalAmount));
                export1TotalAmount = export1TotalAmount.add(totalAmount);
                result.setUnitPriceStr(fourDf.format(unitPrice));
                result.setCurrencyCn(fixedExchangeRateMap.get(result.getCurrency()).get(0).getOriginalCurrencyName());
                result.setIsMergeUpCell("1");
                if(result.getGoodsName().contains(":") || result.getGoodsName().contains("：")){
                    int idx = result.getGoodsName().indexOf(":");
                    if(idx == -1){
                        idx = result.getGoodsName().indexOf("：");
                    }
                    result.setGoodsName(result.getGoodsName().substring(idx + 1));
                }
                export1List.add(result);
                TgCustomsApplyExport3ListResult essentialFactor = new TgCustomsApplyExport3ListResult();
                String subEssentialFactor = result.getEssentialFactor().substring(result.getEssentialFactor().indexOf("|") + 1);
                String[] subEfArr = subEssentialFactor.split("\\|");
                StringBuffer subEfValBf = new StringBuffer();
                for (String subEf : subEfArr) {
                    int idx = subEf.indexOf(":");
                    if(idx == -1){
                        idx = subEf.indexOf("：");
                    }
                    subEfValBf.append(subEf.substring(idx + 1)).append("|");
                }
                if(StringUtils.isNotBlank(result.getAttribute())){
                    essentialFactor.setGoodsName(subEfValBf.substring(0, subEfValBf.length()-1) + "|" + result.getAttribute());
                } else {
                    essentialFactor.setGoodsName(subEfValBf.substring(0, subEfValBf.length()-1));
                }
                essentialFactor.setIsMergeUpCell("1");
                export1List.add(essentialFactor);
            }
            //处理最后一行且最后一单元格的数据，需删除合并标识单元格
            TgCustomsApplyExport3ListResult export1LastData = export1List.get(export1List.size() - 1);
            export1LastData.setIsMergeUpCell("-1");
            //sheet1汇总
            apply.setExport1TotalAmount0(twoDf.format(export1TotalAmount));
            apply.setExport1TotalAmount1(Convert.digitToChinese(export1TotalAmount));

            //sheet2
            List<TgCustomsApplyExport2ListResult> export2List = new ArrayList<>();
            List<TgCustomsApplyExport2ListResult> export2Result = mapper.generateExport2(param);
            if(CollectionUtil.isEmpty(export2Result)){
                response.setHeader("code", "500");
                response.setHeader("message", URLEncoder.encode("国内报关单装箱单数据不存在！", "UTF-8"));
                return;
            }

            //报关单sheet2总的数量
            BigDecimal sheet2TotalQuantity = export2Result.stream().map(TgCustomsApplyExport2ListResult :: getTotalQuantity).reduce(BigDecimal.ZERO, BigDecimal :: add);
            //平均毛重：毛重KG/总数量的总和
            BigDecimal sheet2GrossWeightAvg = apply.getPackageWeight().divide(sheet2TotalQuantity, 4, BigDecimal.ROUND_HALF_UP);
            //汇总行
            BigDecimal totalNetWeight = BigDecimal.ZERO;
            BigDecimal totalGrossWeight = BigDecimal.ZERO;
            BigDecimal totalQuantity = BigDecimal.ZERO;
            for (TgCustomsApplyExport2ListResult result : export2Result) {
                //净重：总毛重（毛重KG/总数量的总和*总数量）-所有箱子重（每个箱子的箱型刷报关外箱包装，取箱重，并合计）/总数量的总和*总数量，保留1位小数，第2位小数进1位到第一位小数
                BigDecimal subGrossWeight = apply.getPackageWeight().divide(sheet2TotalQuantity, 4, BigDecimal.ROUND_HALF_UP).multiply(result.getTotalQuantity());
                BigDecimal subBoxWeight = totalBoxWeight.divide(sheet2TotalQuantity, 4, BigDecimal.ROUND_HALF_UP).multiply(result.getTotalQuantity());
                ////小数第二位非0向上取入，保留1位小数，小数点后0.1~0.4进位成0.5，0.5不变，0.6~0.9向前进位1
                result.setTotalNetWeight(subGrossWeight.subtract(subBoxWeight).setScale(1, BigDecimal.ROUND_UP));
                //毛重：毛重KG/总数量的总和*总数量，//小数第二位非0向上取入，保留1位小数，小数点后0.1~0.4进位成0.5，0.5不变，0.6~0.9向前进位1
                result.setTotalGrossWeight(sheet2GrossWeightAvg.multiply(result.getTotalQuantity()).setScale(1, BigDecimal.ROUND_UP));

                //格式化
                String tnwStr = oneDf.format(result.getTotalNetWeight());
                String[] tnwArr = tnwStr.split("\\.");
                String tnwDeciPreStr = tnwArr[0];
                BigDecimal tnwDeciPre = new BigDecimal(tnwDeciPreStr);
                String tnwDeciStr = tnwArr[1];
                BigDecimal tnwDeci = new BigDecimal(tnwDeciStr);
                if(tnwDeci.compareTo(BigDecimal.ZERO) > 0 && tnwDeci.compareTo(new BigDecimal(5)) < 0){
                    result.setTotalNetWeightStr(tnwDeciPreStr + "." + "5");
                } else if(tnwDeci.compareTo(new BigDecimal(5)) > 0 && tnwDeci.compareTo(new BigDecimal(10)) < 0){
                    result.setTotalNetWeightStr(tnwDeciPre.add(BigDecimal.ONE) + "." + "0");
                } else {
                    result.setTotalNetWeightStr(tnwStr);
                }

                String tgwStr = oneDf.format(result.getTotalGrossWeight());
                String[] tgwArr = tgwStr.split("\\.");
                String tgwDeciPreStr = tgwArr[0];
                BigDecimal tgwDeciPre = new BigDecimal(tgwDeciPreStr);
                String tgwDeciStr = tgwArr[1];
                BigDecimal tgwDeci = new BigDecimal(tgwDeciStr);
                if(tgwDeci.compareTo(BigDecimal.ZERO) > 0 && tgwDeci.compareTo(new BigDecimal(5)) < 0){
                    result.setTotalGrossWeightStr(tgwDeciPreStr + "." + "5");
                } else if(tgwDeci.compareTo(new BigDecimal(5)) > 0 && tgwDeci.compareTo(new BigDecimal(10)) < 0){
                    result.setTotalGrossWeightStr(tgwDeciPre.add(BigDecimal.ONE) + "." + "0");
                } else {
                    result.setTotalGrossWeightStr(tgwStr);
                }

                totalNetWeight = totalNetWeight.add(result.getTotalNetWeight());
                totalGrossWeight = totalGrossWeight.add(result.getTotalGrossWeight());
                totalQuantity = totalQuantity.add(result.getTotalQuantity());
                if(result.getGoodsName().contains(":") || result.getGoodsName().contains("：")){
                    int idx = result.getGoodsName().indexOf(":");
                    if(idx == -1){
                        idx = result.getGoodsName().indexOf("：");
                    }
                    result.setGoodsName(result.getGoodsName().substring(idx + 1));
                }
                export2List.add(result);
                TgCustomsApplyExport2ListResult essentialFactor = new TgCustomsApplyExport2ListResult();
                String subEssentialFactor = result.getEssentialFactor().substring(result.getEssentialFactor().indexOf("|") + 1);
                String[] subEfArr = subEssentialFactor.split("\\|");
                StringBuffer subEfValBf = new StringBuffer();
                for (String subEf : subEfArr) {
                    int idx = subEf.indexOf(":");
                    if(idx == -1){
                        idx = subEf.indexOf("：");
                    }
                    subEfValBf.append(subEf.substring(idx + 1)).append("|");
                }
                if(StringUtils.isNotBlank(result.getAttribute())){
                    essentialFactor.setGoodsName(subEfValBf.substring(0, subEfValBf.length()-1) + "|" + result.getAttribute());
                } else {
                    essentialFactor.setGoodsName(subEfValBf.substring(0, subEfValBf.length()-1));
                }
                essentialFactor.setIsMergeUpCell("0");
                export2List.add(essentialFactor);
            }
            //处理最后一行且最后一单元格的数据，需删除合并标识单元格
            TgCustomsApplyExport2ListResult export2LastData = export2List.get(export1List.size() - 1);
            export2LastData.setIsMergeUpCell("-1");

            apply.setExport2TotalNetWeight(totalNetWeight.setScale(0, BigDecimal.ROUND_UP).toString());
            apply.setExport2TotalGrossWeight(totalGrossWeight.setScale(0, BigDecimal.ROUND_UP).toString());
            apply.setExport2TotalQuantity(totalQuantity.toString());

            //sheet3
            List<TgCustomsApplyExport3ListResult> export3List = new ArrayList<>();
            List<TgCustomsApplyExport3ListResult> export3Result = mapper.generateExport3(param);
            if(CollectionUtil.isEmpty(export3Result)){
                response.setHeader("code", "500");
                response.setHeader("message", URLEncoder.encode("国内报关单发票数据不存在！", "UTF-8"));
                return;
            }
            //sheet3总金额
            BigDecimal export3TotalAmount = BigDecimal.ZERO;
            for (TgCustomsApplyExport3ListResult result : export3Result) {
                List<TgCustomsApplyExport0ListResult> subProductInfoList = productInfoMap.get(result.getGroupStr());
                if(CollectionUtil.isEmpty(subProductInfoList)){
                    response.setHeader("code", "500");
                    response.setHeader("message", URLEncoder.encode("国内报关单发票物料编码对应产品基本信息不存在！", "UTF-8"));
                    return;
                }

                //总价：开票品名+海关编码+要素+数量单位+套装属性一致的产品，每个含税的物料编码的K3价格/1.13/(1-毛利率)/间接汇率*数量（每个不含税的物料编码的K3价格/(1-毛利率)*间接汇率*数量），多个物料编码的情况把算出来的价格汇总后，转换为报关币制，最多2位小数，四舍五入
                BigDecimal totalAmount = BigDecimal.ZERO;
                for (TgCustomsApplyExport0ListResult subProductInfo : subProductInfoList) {
                    List<FixedExchangeRate> rateList = fixedExchangeRateMap.get(subProductInfo.getCurrency());
                    if(CollectionUtil.isEmpty(rateList)){
                        response.setHeader("code", "500");
                        response.setHeader("message", URLEncoder.encode("国内报关单发票" + subProductInfo.getCurrency() + "汇率信息不存在！", "UTF-8"));
                        return;
                    }
                    //固定汇率的间接汇率
                    BigDecimal indirectRate = rateList.get(0).getIndirectRate();
                    BigDecimal subTotalAmount;
                    if("1".equals(subProductInfo.getIncludeTax())){
                        //含税
                        subTotalAmount = subProductInfo.getK3Price()
                                .divide(new BigDecimal("1.13"), 4, BigDecimal.ROUND_HALF_UP)
                                .divide((BigDecimal.ONE.subtract(subProductInfo.getGrossProfitRate())), 4, BigDecimal.ROUND_HALF_UP)
                                .multiply(indirectRate)
                                .multiply(subProductInfo.getTotalQuantity()).setScale(2, BigDecimal.ROUND_HALF_UP);
                    } else {
                        //不含税
                        subTotalAmount = subProductInfo.getK3Price()
                                .divide((BigDecimal.ONE.subtract(subProductInfo.getGrossProfitRate())), 4, BigDecimal.ROUND_HALF_UP)
                                .multiply(indirectRate)
                                .multiply(subProductInfo.getTotalQuantity()).setScale(2, BigDecimal.ROUND_HALF_UP);
                    }
                    totalAmount = totalAmount.add(subTotalAmount);
                }
                //单价：总价/数量
                BigDecimal unitPrice = totalAmount.divide(result.getTotalQuantity(), 4, BigDecimal.ROUND_HALF_UP);
                result.setTotalAmountStr(twoDf.format(totalAmount));
                export3TotalAmount = export3TotalAmount.add(totalAmount);
                result.setUnitPriceStr(fourDf.format(unitPrice));
                result.setCurrencyCn(fixedExchangeRateMap.get(result.getCurrency()).get(0).getOriginalCurrencyName());
                if(result.getGoodsName().contains(":") || result.getGoodsName().contains("：")){
                    int idx = result.getGoodsName().indexOf(":");
                    if(idx == -1){
                        idx = result.getGoodsName().indexOf("：");
                    }
                    result.setGoodsName(result.getGoodsName().substring(idx + 1));
                }
                export3List.add(result);
                TgCustomsApplyExport3ListResult essentialFactor = new TgCustomsApplyExport3ListResult();
                String subEssentialFactor = result.getEssentialFactor().substring(result.getEssentialFactor().indexOf("|") + 1);
                String[] subEfArr = subEssentialFactor.split("\\|");
                StringBuffer subEfValBf = new StringBuffer();
                for (String subEf : subEfArr) {
                    int idx = subEf.indexOf(":");
                    if(idx == -1){
                        idx = subEf.indexOf("：");
                    }
                    subEfValBf.append(subEf.substring(idx + 1)).append("|");
                }
                if(StringUtils.isNotBlank(result.getAttribute())){
                    essentialFactor.setGoodsName(subEfValBf.substring(0, subEfValBf.length()-1) + "|" + result.getAttribute());
                } else {
                    essentialFactor.setGoodsName(subEfValBf.substring(0, subEfValBf.length()-1));
                }
                essentialFactor.setIsMergeUpCell("0");
                export3List.add(essentialFactor);
            }
            //处理最后一行且最后一单元格的数据，需删除合并标识单元格
            TgCustomsApplyExport3ListResult export3LastData = export3List.get(export3List.size() - 1);
            export3LastData.setIsMergeUpCell("-1");
            //sheet3汇总
            apply.setExport3TotalAmount(twoDf.format(export3TotalAmount));

            //sheet4
            List<TgCustomsApplyExport4ListResult> export4Result = mapper.generateExport4(param);
            if(CollectionUtil.isEmpty(export4Result)){
                response.setHeader("code", "500");
                response.setHeader("message", URLEncoder.encode("国内报关单清单数据不存在！", "UTF-8"));
                return;
            }
            //报关单sheet4总的数量
            BigDecimal sheet4TotalQuantity = export4Result.stream().map(TgCustomsApplyExport4ListResult :: getTotalQuantity).reduce(BigDecimal.ZERO, BigDecimal :: add);
            for (TgCustomsApplyExport4ListResult result : export4Result) {
                List<TgCustomsApplyExport0ListResult> subProductInfoList = productInfoMap.get(result.getGroupStr());
                if(CollectionUtil.isEmpty(subProductInfoList)){
                    response.setHeader("code", "500");
                    response.setHeader("message", URLEncoder.encode("国内报关单清单物料编码对应产品基本信息不存在！", "UTF-8"));
                    return;
                }

                Set<String> weightUnitSet = new HashSet<>();
                //总价（报关销售额）：开票品名+海关编码+要素+数量单位+套装属性一致的产品，每个含税的物料编码的K3价格/1.13/(1-毛利率)/间接汇率*数量（每个不含税的物料编码的K3价格/(1-毛利率)*间接汇率*数量），多个物料编码的情况把算出来的价格汇总后，转换为报关币制，最多2位小数，四舍五入
                BigDecimal totalAmount = BigDecimal.ZERO;
                for (TgCustomsApplyExport0ListResult subProductInfo : subProductInfoList) {
                    if(!weightUnitSet.contains(subProductInfo.getWeightUnitOrg())){
                        weightUnitSet.add(subProductInfo.getWeightUnitOrg());
                    }
                    List<FixedExchangeRate> rateList = fixedExchangeRateMap.get(subProductInfo.getCurrency());
                    if(CollectionUtil.isEmpty(rateList)){
                        response.setHeader("code", "500");
                        response.setHeader("message", URLEncoder.encode("国内报关单清单" + subProductInfo.getCurrency() + "汇率信息不存在！", "UTF-8"));
                        return;
                    }
                    //固定汇率的间接汇率
                    BigDecimal indirectRate = rateList.get(0).getIndirectRate();
                    BigDecimal subTotalAmount;
                    if("1".equals(subProductInfo.getIncludeTax())){
                        //含税
                        subTotalAmount = subProductInfo.getK3Price()
                                .divide(new BigDecimal("1.13"), 4, BigDecimal.ROUND_HALF_UP)
                                .divide((BigDecimal.ONE.subtract(subProductInfo.getGrossProfitRate())), 4, BigDecimal.ROUND_HALF_UP)
                                .multiply(indirectRate)
                                .multiply(subProductInfo.getTotalQuantity()).setScale(2, BigDecimal.ROUND_HALF_UP);
                    } else {
                        //不含税
                        subTotalAmount = subProductInfo.getK3Price()
                                .divide((BigDecimal.ONE.subtract(subProductInfo.getGrossProfitRate())), 4, BigDecimal.ROUND_HALF_UP)
                                .multiply(indirectRate)
                                .multiply(subProductInfo.getTotalQuantity()).setScale(2, BigDecimal.ROUND_HALF_UP);
                    }
                    subProductInfo.setApplyPrice(subTotalAmount.divide(subProductInfo.getTotalQuantity(), 4, BigDecimal.ROUND_HALF_UP));
                    subProductInfo.setApplyAmount(subTotalAmount);
                    totalAmount = totalAmount.add(subTotalAmount);
                }
                //单价：总价/数量
                BigDecimal unitPrice = totalAmount.divide(result.getTotalQuantity(), 4, BigDecimal.ROUND_HALF_UP);
                result.setTotalSales(totalAmount);
                result.setTotalSalesStr(twoDf.format(result.getTotalSales()));
                result.setUnitPrice(unitPrice);
                result.setUnitPriceStr(fourDf.format(result.getUnitPrice()));
                result.setCurrencyCn(fixedExchangeRateMap.get(result.getCurrency()).get(0).getOriginalCurrencyName());
                //净重：总毛重（毛重KG/总数量的总和*总数量）-所有箱子重（每个箱子的箱型刷报关外箱包装，取箱重，并合计）/总数量的总和*总数量，保留1位小数，第2位小数进1位到第一位小数
                BigDecimal subGrossWeight = apply.getPackageWeight().divide(sheet4TotalQuantity, 4, BigDecimal.ROUND_HALF_UP).multiply(result.getTotalQuantity());
                BigDecimal subBoxWeight = totalBoxWeight.divide(sheet4TotalQuantity, 4, BigDecimal.ROUND_HALF_UP).multiply(result.getTotalQuantity());

                result.setTotalWeight(subGrossWeight.subtract(subBoxWeight).setScale(1, BigDecimal.ROUND_UP));//小数第二位非0向上取入
                String twStr = oneDf.format(result.getTotalWeight());
                String[] twArr = twStr.split("\\.");
                String twDeciPreStr = twArr[0];
                BigDecimal twDeciPre = new BigDecimal(twDeciPreStr);
                String twDeciStr = twArr[1];
                BigDecimal twDeci = new BigDecimal(twDeciStr);
                if(twDeci.compareTo(BigDecimal.ZERO) > 0 && twDeci.compareTo(new BigDecimal(5)) < 0){
                    result.setTotalWeightStr(twDeciPreStr + "." + "5");
                } else if(twDeci.compareTo(new BigDecimal(5)) > 0 && twDeci.compareTo(new BigDecimal(10)) < 0){
                    result.setTotalWeightStr(twDeciPre.add(BigDecimal.ONE) + "." + "0");
                } else {
                    result.setTotalWeightStr(twStr);
                }
                result.setUnitPrice(unitPrice);
                if(result.getGoodsName().contains(":") || result.getGoodsName().contains("：")){
                    int idx = result.getGoodsName().indexOf(":");
                    if(idx == -1){
                        idx = result.getGoodsName().indexOf("：");
                    }
                    result.setGoodsName(result.getGoodsName().substring(idx + 1));
                }

                String[] subEfArr = result.getEssentialFactor().split("\\|");
                StringBuffer subEfValBf = new StringBuffer();
                for (String subEf : subEfArr) {
                    int idx = subEf.indexOf(":");
                    if(idx == -1){
                        idx = subEf.indexOf("：");
                    }
                    subEfValBf.append(subEf.substring(idx + 1)).append("|");
                }
                if(StringUtils.isNotBlank(result.getAttribute())){
                    result.setEssentialFactor(subEfValBf.substring(0, subEfValBf.length()-1) + "|" + result.getAttribute());
                } else {
                    result.setEssentialFactor(subEfValBf.substring(0, subEfValBf.length()-1));
                }
            }

            List<TgCustomsApplyExport4ListResult> export4List = new ArrayList<>();
            for (TgCustomsApplyExport4ListResult result : export4Result) {
                List<TgCustomsApplyExport0ListResult> subProductInfoList = productInfoMap.get(result.getGroupStr());
                int listSize = subProductInfoList.size();
                for (TgCustomsApplyExport0ListResult subProductInfo : subProductInfoList) {
                    TgCustomsApplyExport4ListResult export4 = new TgCustomsApplyExport4ListResult();
                    BeanUtils.copyProperties(result, export4);
                    export4.setInvoiceProNameCn(subProductInfo.getSubInvoiceProNameCn());
                    export4.setStyle(subProductInfo.getStyle());
                    export4.setQuantity(subProductInfo.getTotalQuantity());
                    //维度总重量/维度总数量*数量
                    BigDecimal subWeight = result.getTotalWeight().divide(result.getTotalQuantity(), 4, BigDecimal.ROUND_HALF_UP).multiply(subProductInfo.getTotalQuantity());
                    //权重：物料编码维度的物料K3毛重/(维度:开票品名+海关编码+要素+数量单位+套装属性一致)的产品的物料编码K3毛重合计*物料编码条数)
                    if(BigDecimal.ZERO.equals(subProductInfo.getGrossWeightOrg())){
                        response.setHeader("code", "500");
                        response.setHeader("message", URLEncoder.encode("产品基本信息的物料编码：" + subProductInfo.getMaterialCode() + "重量不能为0，请先维护！", "UTF-8"));
                        return;
                    }
                    BigDecimal proportion = subProductInfo.getGrossWeightOrg().divide(export4.getSumGrossWeightOrg(), 4, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(subProductInfoList.size()));

                    export4.setWeight(subWeight.multiply(proportion).setScale(1, BigDecimal.ROUND_HALF_UP));
                    String wStr = oneDf.format(export4.getWeight());
                    String[] wArr = wStr.split("\\.");
                    String wDeciPreStr = wArr[0];
                    BigDecimal wDeciPre = new BigDecimal(wDeciPreStr);
                    String wDeciStr = wArr[1];
                    BigDecimal wDeci = new BigDecimal(wDeciStr);
                    if(wDeci.compareTo(BigDecimal.ZERO) > 0 && wDeci.compareTo(new BigDecimal(5)) < 0){
                        export4.setWeightStr(wDeciPreStr + "." + "5");
                    } else if(wDeci.compareTo(new BigDecimal(5)) > 0 && wDeci.compareTo(new BigDecimal(10)) < 0){
                        export4.setWeightStr(wDeciPre.add(BigDecimal.ONE) + "." + "0");
                    } else {
                        export4.setWeightStr(wStr);
                    }
                    export4.setK3Price(subProductInfo.getK3Price());
                    export4.setK3PriceStr(fourDf.format(export4.getK3Price()));
                    export4.setApplyPrice(subProductInfo.getApplyPrice());
                    export4.setApplyPriceStr(fourDf.format(export4.getApplyPrice()));
                    export4.setApplyAmount(subProductInfo.getApplyAmount());
                    export4.setApplyAmountStr(fourDf.format(export4.getApplyAmount()));
                    export4.setAdviceSupplier(subProductInfo.getAdviceSupplier());
                    export4.setMaterialCode(subProductInfo.getMaterialCode());
                    export4.setIsMergeUpCell(listSize > 1 ? "1" : "0");
                    export4List.add(export4);
                    listSize --;
                }
            }

            //处理最后一行且最后一单元格的数据，需删除合并标识单元格
            TgCustomsApplyExport4ListResult export4LastData = export4List.get(export4List.size() - 1);
            export4LastData.setIsMergeUpCell("-1");

            /** 读取服务器端模板文件 */
            String modelPath = "/template/国内报关单模板.xlsx";
            InputStream inputStream = this.getClass().getResourceAsStream(modelPath);
//            String excelName = URLEncoder.encode("国内报关单" + DateUtil.format(apply.getApplyDate(), DatePattern.PURE_DATETIME_PATTERN) + ".xlsx", "UTF-8");
            String excelName = URLEncoder.encode("国内报关单.xlsx", "UTF-8");
            response.setContentType("application/vnd.ms-excel");
            response.addHeader("Content-Disposition", "attachment;filename=" +  new String((excelName).getBytes(), "iso-8859-1"));
            response.setHeader("code", "200");
            writer = EasyExcel.write(response.getOutputStream()).withTemplate(inputStream).excelType(ExcelTypeEnum.XLSX).build();
            //填充列表开启自动换行,自动换行表示每次写入一条list数据是都会重新生成一行空行,此选项默认是关闭的,需要提前设置为true
            FillConfig fillConfig = FillConfig.builder().forceNewRow(Boolean.TRUE).build();

            //合并类型 0：单元格合并行（向上合并）
            int merge0Type = 2;
            //模板列表【单元格合并行】标识的列数
            short signColumn0Index = 17;
            //需要合并的列
            int[] merge0ColumnIndex = {0};
            //从第X行后开始合并
            int merge0RowIndex = 15;
            BaseExcelFillCellMergeStrategy excelFillCellMerge0Strategy = new BaseExcelFillCellMergeStrategy(merge0Type, signColumn0Index, merge0ColumnIndex, merge0RowIndex, null);
            WriteSheet sheet0 = EasyExcel.writerSheet(0).registerWriteHandler(excelFillCellMerge0Strategy).build();
            writer.fill(export0List, fillConfig, sheet0);
            writer.fill(apply, sheet0);

            //合并类型 1：单元格合并列（向左合并）
            int merge1Type = 1;
            //模板列表【单元格合并行】标识的列数
            short signColumn1Index = 8;
            //从第X行后开始合并
            int merge1RowIndex = 18;
            //向左合并单元格的信息：Map<合并单元格的列下标, 向左合并单元格数>
            List<Map<Integer, Integer>> mergeColumnList = new ArrayList<>();
            Map<Integer, Integer> oneMap = new HashMap<>();
            oneMap.put(1, 1);
            mergeColumnList.add(oneMap);
            BaseExcelFillCellMergeStrategy excelFillCellMerge1Strategy = new BaseExcelFillCellMergeStrategy(merge1Type, signColumn1Index, null, merge1RowIndex, mergeColumnList);
            WriteSheet sheet1 = EasyExcel.writerSheet(1).registerWriteHandler(excelFillCellMerge1Strategy).build();
            writer.fill(apply, sheet1);
            writer.fill(export1List, fillConfig, sheet1);

            //合并类型 0：单元格合并行（向上合并）
            int merge2Type = 0;
            //模板列表【单元格合并行】标识的列数
            short signColumn2Index = 7;
            //需要合并的列
            int[] merge2ColumnIndex = {0};
            //从第X行后开始合并
            int merge2RowIndex = 8;
            BaseExcelFillCellMergeStrategy excelFillCellMerge2Strategy = new BaseExcelFillCellMergeStrategy(merge2Type, signColumn2Index, merge2ColumnIndex, merge2RowIndex, null);
            WriteSheet sheet2 = EasyExcel.writerSheet(2).registerWriteHandler(excelFillCellMerge2Strategy).build();
            writer.fill(apply, sheet2);
            writer.fill(export2List, fillConfig, sheet2);

            //合并类型 0：单元格合并行（向上合并）
            int merge3Type = 0;
            //模板列表【单元格合并行】标识的列数
            short signColumn3Index = 8;
            //需要合并的列
            int[] merge3ColumnIndex = {0};
            //从第X行后开始合并
            int merge3RowIndex = 5;
            BaseExcelFillCellMergeStrategy excelFillCellMerge3Strategy = new BaseExcelFillCellMergeStrategy(merge3Type, signColumn3Index, merge3ColumnIndex, merge3RowIndex, null);
            WriteSheet sheet3 = EasyExcel.writerSheet(3).registerWriteHandler(excelFillCellMerge3Strategy).build();
            writer.fill(apply, sheet3);
            writer.fill(export3List, fillConfig, sheet3);

            //合并类型 0：单元格合并行（向上合并）
            int merge4Type = 0;
            //模板列表【单元格合并行】标识的列数
            short signColumn4Index = 18;
            //需要合并的列
            int[] merge4ColumnIndex = {0, 1, 2, 3, 4, 5, 6, 16};
            //从第X行后开始合并
            int merge4RowIndex = 1;
            BaseExcelFillCellMergeStrategy excelFillCellMerge4Strategy = new BaseExcelFillCellMergeStrategy(merge4Type, signColumn4Index, merge4ColumnIndex, merge4RowIndex, null);
            WriteSheet sheet4 = EasyExcel.writerSheet(4).registerWriteHandler(excelFillCellMerge4Strategy).build();
            writer.fill(export4List, fillConfig, sheet4);
            writer.finish();
            log.info("生成国内报关单结束，耗时------------------->" + (System.currentTimeMillis() - start) + "ms");
        } catch (Exception e){
            response.setHeader("code", "500");
            response.setHeader("message", URLEncoder.encode("生成国内报关单异常，" + e, "UTF-8"));
            log.error("生成国内报关单异常，", e);
            return;
        } finally {
            if (writer != null) {
                writer.finish();
            }
        }
    }

    @Override
    @DataSource(name = "logistics")
    public String getNowBgdOrder() {
        Integer noLength = 4;
        String pureDate = DateUtil.format(new Date(), DatePattern.PURE_DATE_PATTERN);
        String nowBgdOrder = mapper.getNowBgdOrder(pureDate);//获取数据库最新的合同协议号
        String newBgdOrder;
        if(StringUtils.isNotBlank(nowBgdOrder)){
            Integer newNum = Integer.parseInt(nowBgdOrder.substring(nowBgdOrder.length() - noLength)) + 1;
            newBgdOrder = pureDate + StringUtils.leftPad(newNum.toString(), noLength, "0");
        }else{
            newBgdOrder = pureDate + StringUtils.leftPad("1", noLength, "0");
        }
        return newBgdOrder;
    }

}
