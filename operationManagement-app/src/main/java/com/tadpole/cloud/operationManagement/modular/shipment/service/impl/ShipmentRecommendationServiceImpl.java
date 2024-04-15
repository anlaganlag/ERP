package com.tadpole.cloud.operationManagement.modular.shipment.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import cn.stylefeng.guns.cloud.auth.api.model.LoginUser;
import cn.stylefeng.guns.cloud.libs.context.auth.LoginContext;
import cn.stylefeng.guns.cloud.libs.mp.page.PageFactory;
import cn.stylefeng.guns.cloud.model.page.PageResult;
import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import cn.stylefeng.guns.cloud.system.core.dbs.annotation.DataSource;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.alibaba.excel.write.metadata.fill.FillConfig;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tadpole.cloud.operationManagement.modular.shipment.entity.ShipmentApplyItem;
import com.tadpole.cloud.operationManagement.modular.shipment.entity.ShipmentRecommendation;
import com.tadpole.cloud.operationManagement.modular.shipment.mapper.ShipmentRecommendationMapper;
import com.tadpole.cloud.operationManagement.modular.shipment.model.params.ShipmentRecomD6Param;
import com.tadpole.cloud.operationManagement.modular.shipment.model.params.ShipmentRecommendationParam;
import com.tadpole.cloud.operationManagement.modular.shipment.model.params.SkuCheckParam;
import com.tadpole.cloud.operationManagement.modular.shipment.model.result.ShipmentApplyItemResult;
import com.tadpole.cloud.operationManagement.modular.shipment.model.result.ShipmentRecommendationShopSkuResult;
import com.tadpole.cloud.operationManagement.modular.shipment.model.result.ShipmentRecommendationTemplateResult;
import com.tadpole.cloud.operationManagement.modular.shipment.model.result.SkuCheckResult;
import com.tadpole.cloud.operationManagement.modular.shipment.service.IOtherModularService;
import com.tadpole.cloud.operationManagement.modular.shipment.service.IShipmentApplyItemService;
import com.tadpole.cloud.operationManagement.modular.shipment.service.IShipmentRecomSnapshotService;
import com.tadpole.cloud.operationManagement.modular.shipment.service.IShipmentRecommendationService;
import com.tadpole.cloud.operationManagement.modular.shipment.utils.GeneratorShipmentNoUtil;
import com.tadpole.cloud.operationManagement.modular.stock.service.RpMaterialService;
import com.tadpole.cloud.platformSettlement.api.finance.entity.Material;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>
 * 发货推荐 服务实现类
 * </p>
 *
 * @author lsy
 * @since 2023-02-02
 */
@Service
@Slf4j
public class ShipmentRecommendationServiceImpl extends ServiceImpl<ShipmentRecommendationMapper, ShipmentRecommendation> implements IShipmentRecommendationService {

    @Autowired
    ResourceLoader resourceLoader;


    @Resource
    private IShipmentRecommendationService service;


    @Resource
    IShipmentApplyItemService shipmentApplyItemService;


    @Resource
    IShipmentRecomSnapshotService shipmentRecomSnapshotService;


    @Resource
    private RpMaterialService materialService;


    @Resource
    IOtherModularService otherModularService;


    @Resource
    GeneratorShipmentNoUtil generatorShipmentNoUtil;


    @Resource
    private ShipmentRecommendationMapper mapper;


    @Override
    @DataSource(name = "stocking")
    @Transactional(rollbackFor = Exception.class)
    public PageResult<ShipmentRecommendation> shipmentRecommendationList(ShipmentRecommendationParam param) {
        LambdaQueryWrapper<ShipmentRecommendation> queryWrapper = new LambdaQueryWrapper<>();
        Page pageContext = param.getPageContext();
        //Amazon平台
        if (ObjectUtil.isNotEmpty(param.getPlatform())) {
            queryWrapper.eq(ShipmentRecommendation::getPlatform, param.getPlatform());
        } else {
            queryWrapper.eq(ShipmentRecommendation::getPlatform, "Amazon");
        }

        //Team
        queryWrapper.eq(ObjectUtil.isNotEmpty(param.getTeam()), ShipmentRecommendation::getTeam, param.getTeam());
        queryWrapper.in(ObjectUtil.isNotEmpty(param.getTeamList()), ShipmentRecommendation::getTeam, param.getTeamList());

        //部门
        queryWrapper.eq(ObjectUtil.isNotEmpty(param.getDepartment()), ShipmentRecommendation::getDepartment, param.getDepartment());
        queryWrapper.in(ObjectUtil.isNotEmpty(param.getDepartmentList()), ShipmentRecommendation::getDepartment, param.getDepartmentList());

        //区域
        queryWrapper.eq(ObjectUtil.isNotEmpty(param.getPreArea()), ShipmentRecommendation::getPreArea, param.getPreArea());
        queryWrapper.in(ObjectUtil.isNotEmpty(param.getPreAreaList()), ShipmentRecommendation::getPreArea, param.getPreAreaList());

        //ASIN
        queryWrapper.eq(ObjectUtil.isNotEmpty(param.getAsin()), ShipmentRecommendation::getAsin, param.getAsin());
        queryWrapper.in(ObjectUtil.isNotEmpty(param.getAsinList()), ShipmentRecommendation::getAsin, param.getAsinList());

        //物料十四级属性 : materialCode,category,productType,productName,style,mainMaterial,pattern,companyBrand,brand,model,color,sizes,packing,versionDes;

        //物料编码
        queryWrapper.like(ObjectUtil.isNotEmpty(param.getMaterialCode()), ShipmentRecommendation::getMaterialCode, param.getMaterialCode());
        queryWrapper.in(ObjectUtil.isNotEmpty(param.getMaterialCodeList()), ShipmentRecommendation::getMaterialCode, param.getMaterialCodeList());

        //类目
        queryWrapper.like(ObjectUtil.isNotEmpty(param.getCategory()), ShipmentRecommendation::getCategory, param.getCategory());
        queryWrapper.in(ObjectUtil.isNotEmpty(param.getCategoryList()), ShipmentRecommendation::getCategory, param.getCategoryList());

        //运营大类
        queryWrapper.like(ObjectUtil.isNotEmpty(param.getProductType()) && ! "[]".equals(param.getProductType()), ShipmentRecommendation::getProductType, param.getProductType());
        queryWrapper.in(ObjectUtil.isNotEmpty(param.getProductTypeList()), ShipmentRecommendation::getProductType, param.getProductTypeList());

        //产品名称
        queryWrapper.like(ObjectUtil.isNotEmpty(param.getProductName()) && ! "[]".equals(param.getProductName()), ShipmentRecommendation::getProductName, param.getProductName());
        queryWrapper.in(ObjectUtil.isNotEmpty(param.getProductNameList()), ShipmentRecommendation::getProductName, param.getProductNameList());

        //款式
        queryWrapper.like(ObjectUtil.isNotEmpty(param.getStyle()), ShipmentRecommendation::getStyle, param.getStyle());
        queryWrapper.in(ObjectUtil.isNotEmpty(param.getStyleList()), ShipmentRecommendation::getStyle, param.getStyleList());

        //主材料
        queryWrapper.like(ObjectUtil.isNotEmpty(param.getMainMaterial()), ShipmentRecommendation::getMainMaterial, param.getMainMaterial());
        queryWrapper.in(ObjectUtil.isNotEmpty(param.getMainMaterialList()), ShipmentRecommendation::getMainMaterial, param.getMainMaterialList());

        //图案
        queryWrapper.like(ObjectUtil.isNotEmpty(param.getPattern()), ShipmentRecommendation::getPattern, param.getPattern());
        queryWrapper.in(ObjectUtil.isNotEmpty(param.getPatternList()), ShipmentRecommendation::getPattern, param.getPatternList());

        //公司品牌
        queryWrapper.like(ObjectUtil.isNotEmpty(param.getCompanyBrand()), ShipmentRecommendation::getCompanyBrand, param.getCompanyBrand());
        queryWrapper.in(ObjectUtil.isNotEmpty(param.getCompanyBrandList()), ShipmentRecommendation::getCompanyBrand, param.getCompanyBrandList());

        //品牌
        queryWrapper.like(ObjectUtil.isNotEmpty(param.getBrand()), ShipmentRecommendation::getBrand, param.getBrand());
        queryWrapper.in(ObjectUtil.isNotEmpty(param.getBrandList()), ShipmentRecommendation::getBrand, param.getBrandList());

        //型号
        queryWrapper.like(ObjectUtil.isNotEmpty(param.getModel()), ShipmentRecommendation::getModel, param.getModel());
        queryWrapper.in(ObjectUtil.isNotEmpty(param.getModelList()), ShipmentRecommendation::getModel, param.getModelList());

        //颜色
        queryWrapper.like(ObjectUtil.isNotEmpty(param.getColor()), ShipmentRecommendation::getColor, param.getColor());
        queryWrapper.in(ObjectUtil.isNotEmpty(param.getColorList()), ShipmentRecommendation::getColor, param.getColorList());

        //尺寸
        queryWrapper.like(ObjectUtil.isNotEmpty(param.getSizes()), ShipmentRecommendation::getSizes, param.getSizes());
        queryWrapper.in(ObjectUtil.isNotEmpty(param.getSizesList()), ShipmentRecommendation::getSizes, param.getSizesList());

        //包装数量
        queryWrapper.like(ObjectUtil.isNotEmpty(param.getPacking()), ShipmentRecommendation::getPacking, param.getPacking());
        queryWrapper.in(ObjectUtil.isNotEmpty(param.getPackingList()), ShipmentRecommendation::getPacking, param.getPackingList());


        //适用机型
        queryWrapper.like(ObjectUtil.isNotEmpty(param.getType()), ShipmentRecommendation::getType, param.getType());
        queryWrapper.in(ObjectUtil.isNotEmpty(param.getTypeList()), ShipmentRecommendation::getType, param.getTypeList());

        //版本描述
        queryWrapper.like(ObjectUtil.isNotEmpty(param.getVersionDes()), ShipmentRecommendation::getVersionDes, param.getVersionDes());
        queryWrapper.in(ObjectUtil.isNotEmpty(param.getVersionDesList()), ShipmentRecommendation::getVersionDes, param.getVersionDesList());

        //申请状态:"只显示无申请记录"  status != 1
        queryWrapper.ne(param.getIsNoApply() != null && param.getIsNoApply(), ShipmentRecommendation::getStatus, 1);
        queryWrapper.orderByAsc(ShipmentRecommendation::getPlatform, ShipmentRecommendation::getDepartment, ShipmentRecommendation::getTeam, ShipmentRecommendation::getPreArea, ShipmentRecommendation::getMaterialCode, ShipmentRecommendation::getAsin);

        return new PageResult<>(baseMapper.selectPage(pageContext, queryWrapper));
    }



    @Override
    @DataSource(name = "stocking")
    @Transactional(rollbackFor = Exception.class)
    public PageResult<ShipmentRecommendation> shipmentRecommendationListDatalimit(ShipmentRecommendationParam param) {
        LambdaQueryWrapper<ShipmentRecommendation> queryWrapper = new LambdaQueryWrapper<>();
        Page pageContext = param.getPageContext();

        //Amazon平台
        if (ObjectUtil.isEmpty(param.getPlatform())) {
            param.setPlatform("Amazon");
        }

        return new PageResult<>(mapper.selectPageDatalimit(pageContext, param));
    }

    @Override
    @DataSource(name = "stocking")
    @Transactional(rollbackFor = Exception.class)
    public ResponseData recommendationApplyShipmentSave(List<ShipmentApplyItem> applyItemList) {
        log.info("推荐申请发货保存>>recommendationApplyShipmentSave 传入参数===================>: [{}] ", JSONUtil.toJsonStr(applyItemList));
        if (this.getUserAccount() == null) {
            return ResponseData.error("当前登陆人为空");
        }
        if (CollUtil.isEmpty(applyItemList)) {
            return ResponseData.error("SKU数据为空");
        }
        String pk = applyItemList.get(0).getPk();
        List<ShipmentRecommendation> shipmentRecommendationList = this.getListByPK(Arrays.asList(pk));
        if (shipmentRecommendationList.size() == 0) {
            return ResponseData.error(StrUtil.format("推荐申请发货保存>>推荐申请发货保存recommendationApplyShipmentSave>> D6:[{}]无对应推荐数据", pk));
        }
        ShipmentRecommendation shipmentRecommendation = shipmentRecommendationList.get(0);

        try {
            String shipmentBatchNo = generatorShipmentNoUtil.getBatchNo("FHSQ");
            //校验applyItemList deliverypoint 一致性
            Map<String, List<ShipmentApplyItem>> deliverypoint = applyItemList.stream().collect(Collectors.groupingBy(ShipmentApplyItem::getDeliverypoint));
            if (deliverypoint.keySet().size()>1) {
                return ResponseData.error("明细数据发货点不一致");
            }
            HashSet<String> billCodeSet = new HashSet<>();
            for (ShipmentApplyItem applyItem : applyItemList) {
                //明细关键信息校验
                if (applyItem.getSendQty() == null || applyItem.getTransportationType() == null) {
                    return ResponseData.error(StrUtil.format("SKU: [{}] 申请发货数量和发货方式为空", applyItem.getSku()));
                }
//                //店铺和运营大类校验
//                if (applyItem.getShopName() == null ) {
//                    return ResponseData.error(StrUtil.format("SKU: [{}] shopName为空",applyItem.getSku()));
//                }
//                if (applyItem.getProductType() == null ) {
//                    return ResponseData.error(StrUtil.format("SKU: [{}] 运营大类为空",applyItem.getSku()));
//                }
                //物料信息json
                Material materialInfo = materialService.queryMaterialInfo(applyItem.getMaterialCode());
                if (materialInfo == null) {
                    return ResponseData.error(StrUtil.format("推荐申请发货保存recommendationApplyShipmentSave>> SKU:[{}]无对应物料信息", applyItem.getSku()));
                }
                applyItem.setMaterialInfo(JSONUtil.toJsonStr(materialInfo));

                applyItem.setProductName(materialInfo.getProductName());
                applyItem.setDataSourceType("BI推荐");
                applyItem.setApplyDate(new Date());
                applyItem.setApplyStatus(0);
                if (this.getUserAccount() == null) {
                    return ResponseData.error("当前登陆人为空");
                }
                //登录人信息
                applyItem.setApllyPersonNo(this.getUserAccount());
                applyItem.setApllyPerson(this.getUserName());
                applyItem.setCreatedBy(this.getUserName());
                //亚马逊仓库 Amazon_CC_US_仓库
                String warehouse = "Amazon_" + applyItem.getSysShopsName() + "_" + applyItem.getSysSite() + "_仓库";

                HashSet<String> warehouseSet = new HashSet<>();
                warehouseSet.add(warehouse);
                Map<String, String> shopWarehouseCodeMap = otherModularService.getWarehouseCode(warehouseSet);
                if (ObjectUtil.isEmpty(shopWarehouseCodeMap)) {
                    return ResponseData.error(StrUtil.format("推荐申请发货保存>>未获取到仓库[{}]店铺调入仓code!", warehouse));
                }
                String warehouseCode = shopWarehouseCodeMap.get(warehouse);
                if (ObjectUtil.isEmpty(warehouseCode)) {
                    return ResponseData.error(StrUtil.format("推荐申请发货保存>>仓库编码为空！{}", warehouseCode));
                }

                String unwType = ObjectUtil.isEmpty(applyItem.getUnwType()) ? "亚马逊出货" : applyItem.getUnwType();
                applyItem.setUnwType(unwType);
                applyItem.setOrgCode(warehouseCode);
                String billNo = generatorShipmentNoUtil.getBillNo(unwType, warehouseCode);
                String receiveWarehouse = applyItem.getReceiveWarehouse();
                //调入仓库为空取默认仓和编码
                if (ObjectUtil.isEmpty(receiveWarehouse)) {
                    applyItem.setReceiveWarehouse(warehouse);
                    applyItem.setReceiveWarehouseCode(warehouseCode);
                    //店铺取对应仓库编码,海外仓编码取海外仓
                } else {
                    applyItem.setReceiveWarehouseCode(receiveWarehouse.startsWith("Amazon") ? warehouseCode : receiveWarehouse);
                }
                applyItem.setApplyBatchNo(shipmentBatchNo);
                applyItem.setBatchNo(shipmentBatchNo);
                applyItem.setBillNo(billNo);
                //保存申请明细
                shipmentApplyItemService.save(applyItem);
                //调拨单据号重复校验
                String saveBillNo = applyItem.getBillNo();
                log.info("BI推荐发货申请保存数据ID{}---批次号{}-----调拨单号BILL_NO:{}----时间：{}",applyItem.getId(),shipmentBatchNo,billNo, LocalDateTime.now());
                if (billCodeSet.contains(saveBillNo)) {
                    log.info("BI推荐发货申请保存数据有重复BILL_NO---重复数据ID{}---批次号{}-----调拨单号BILL_NO:{}----时间：{}",applyItem.getId(),shipmentBatchNo,billNo, LocalDateTime.now());
                }
                billCodeSet.add(applyItem.getBillNo());
            }

            //合理性分析
            List<ShipmentApplyItemResult> analyzeResultList = new ArrayList<>();

            //分析结果需要更新到数据库
            List<ShipmentApplyItem> analyzeResultUpdateList = new ArrayList<>();

            Map<String, List<ShipmentApplyItem>> singleApplyMap = new HashMap<>();


            singleApplyMap.put(pk, applyItemList);

            analyzeResultList.addAll(shipmentApplyItemService.batchSkuAnalyze(applyItemList, shipmentRecommendation, false, analyzeResultUpdateList));
            shipmentApplyItemService.saveOrUpdateBatch(analyzeResultUpdateList);

            //更新当前推荐状态为1
            shipmentRecommendation.setStatus(1);
            this.saveOrUpdate(shipmentRecommendation);

            return ResponseData.success(shipmentBatchNo);
        } catch (Exception e) {
            log.error("推荐申请发货保存异常>>recommendationApplyShipmentSave \n 系统异常原因：[{}] \n 堆栈信息:[{}] \n 入参:[{}]", e.getMessage(), e.getStackTrace(), JSONUtil.toJsonStr(shipmentRecommendation));
            return ResponseData.error("推荐申请发货保存异常");
        }
    }

    @Override
    @DataSource(name = "stocking")
    @Transactional(rollbackFor = Exception.class)
    public ResponseData recommendationApplyShipmentSubmit(List<ShipmentApplyItem> applyItemList) {
        ResponseData saveResponse = this.recommendationApplyShipmentSave(applyItemList);
        if (saveResponse.getSuccess()) {
            List<String> applyBatchList = new ArrayList<>();
            applyBatchList.add(String.valueOf(saveResponse.getData()));
            ResponseData comitResponseData = shipmentApplyItemService.comitByBatchNo(applyBatchList);
            if (!comitResponseData.getSuccess()) {
                shipmentApplyItemService.deleteByBatchNo(applyBatchList);
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            }
            return comitResponseData;
        }
        return saveResponse;
    }


    @Override
    @DataSource(name = "stocking")
    @Transactional(rollbackFor = Exception.class)
    public ResponseData recommendationApplyShipmentSubmitDeprecated(List<ShipmentApplyItem> applyItemList) {
        if (this.getUserAccount() == null) {
            return ResponseData.error("当前登陆人为空");
        }
        log.info("推荐申请发货提交>>recommendationApplyShipmentSubmit 传入参数===================>: [{}] ", JSONUtil.toJsonStr(applyItemList));
        try {
            String shipmentBatchNo = generatorShipmentNoUtil.getBatchNo("FHSQ");
            for (ShipmentApplyItem applyItem : applyItemList) {
                if (applyItem.getSendQty() == null || applyItem.getTransportationType() == null) {
                    return ResponseData.error(StrUtil.format("SKU: [{}] 申请发货数量和发货方式为空", applyItem.getSku()));
                }
                //物料信息json
                Material materialInfo = materialService.queryMaterialInfo(applyItem.getMaterialCode());
                if (materialInfo == null) {
                    return ResponseData.error(StrUtil.format("推荐申请发货提交>>recommendationApplyShipmentSubmit>> SKU:[{}]无对应物料信息", applyItem.getSku()));
                }
                applyItem.setMaterialInfo(JSONUtil.toJsonStr(materialInfo));
                List<String> pkList = Arrays.asList(applyItem.getPk());
                List<ShipmentRecommendation> shipmentRecommendationList = this.getListByPK(pkList);
                if (shipmentRecommendationList.size() == 0) {
                    return ResponseData.error(StrUtil.format("推荐申请发货提交>>recommendationApplyShipmentSubmit>> SKU:[{}]无对应推荐数据", applyItem.getSku()));
                }
                ShipmentRecommendation shipmentRecommendation = shipmentRecommendationList.get(0);
                //更新当前推荐状态为1
                shipmentRecommendation.setStatus(1);
                this.saveOrUpdate(shipmentRecommendation);
                //推荐快照
                applyItem.setProductName(materialInfo.getProductName());
                applyItem.setDataSourceType("BI推荐");
                applyItem.setApplyDate(new Date());
                applyItem.setApplyStatus(0);
                if (this.getUserAccount() == null) {
                    return ResponseData.error("当前登陆人为空");
                }
                //登录人信息
                applyItem.setApllyPersonNo(this.getUserAccount());
                applyItem.setApllyPerson(this.getUserName());
                applyItem.setCreatedBy(this.getUserName());
                //写入批次号
                applyItem.setApplyBatchNo(shipmentBatchNo);
                //保存申请明细
                shipmentApplyItemService.save(applyItem);
            }
            return ResponseData.success();
        } catch (Exception e) {
            log.error("推荐申请发货提交异常>>recommendationApplyShipmentSubmit 系统异常原因：[{}]", e);
            return ResponseData.error("推荐申请发货提交异常");
        }
    }


    @Override
    @DataSource(name = "stocking")
    public List<ShipmentRecommendation> getListByPK(List<String> pKList) {
        return mapper.getListByPK(pKList);
    }


    @Override
    @DataSource(name = "stocking")
    public List<ShipmentRecommendationShopSkuResult> getShopSkuListByPK(String pk) {
        return mapper.getShopSkuListByPK(pk);
    }

    @Override
    @DataSource(name = "stocking")
    public List<ShipmentRecommendationShopSkuResult> getShopSkuListBySpec(ShipmentRecommendationParam param) {
        return mapper.getShopSkuListBySpec(param);
    }


    @Override
    @DataSource(name = "stocking")
    public ShipmentRecommendation getByApplyItem(ShipmentRecomD6Param d6Param) {
        //AND 'Amazon'||PRE_AREA || department || team || material_code || ASIN  IN
        String pk = d6Param.getPlatform() + d6Param.getArea() + d6Param.getDepartment() + d6Param.getTeam() + d6Param.getMaterialCode() + d6Param.getAsin();
        List<String> pkList = new ArrayList<>();
        pkList.add(pk);
        List<ShipmentRecommendation> recommendationList = mapper.getListByPK(pkList);
        if (ObjectUtil.isNotEmpty(recommendationList)) {
            return recommendationList.get(0);
        }
        return null;
    }


    @Override
    @DataSource(name = "stocking")
    public List<ShipmentRecommendationTemplateResult> exportTemplateList(ShipmentRecommendationParam param) {
        return mapper.exportTemplateList(param);
    }


    @Override
    @DataSource(name = "EBMS")
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW)
    public ResponseData skuCheck(SkuCheckParam param) {
        if (ObjectUtil.isEmpty(param.getSku())) {
            return ResponseData.error("sku不能为空");
        }
        if (ObjectUtil.isEmpty(param.getSite())) {
            return ResponseData.error("站点不能为空");
        }
        if (ObjectUtil.isEmpty(param.getShopName())) {
            return ResponseData.error("店铺不能为空");
        }
        List<SkuCheckResult> skuCheckResultList = mapper.skuCheck(param);
        if (ObjectUtil.isEmpty(skuCheckResultList)) {
            return ResponseData.error("sku无效");
        }
        SkuCheckResult skuCheckResult = skuCheckResultList.get(0);

        if (!skuCheckResult.getLabState().equals("正常")) {
            return ResponseData.error(StrUtil.format("sku标签状态[{}]", skuCheckResult.getLabState()));
        }
        if (!skuCheckResult.getLabSyncState().equals("已同步")) {
            return ResponseData.error(StrUtil.format("sku标签同步状态[{}]", skuCheckResult.getLabSyncState()));
        }
        if (!skuCheckResult.getLabUseState().equals("启用")) {
            return ResponseData.error(StrUtil.format("sku标签启用[{}]", skuCheckResult.getLabUseState()));
        }
        return ResponseData.success();
    }



    @Override
    @DataSource(name = "EBMS")
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW)
    public List<String> skuBatchCheck(List<SkuCheckParam> params) {
        params.stream().filter(param -> ObjectUtil.isEmpty(param.getSku())&&ObjectUtil.isEmpty(param.getSite())&&ObjectUtil.isEmpty(param.getShopName())).collect(Collectors.toList());
        if (ObjectUtil.isEmpty(params)) {
            return new ArrayList<>();
        }
        List<String> failSku = mapper.failSku(params);
        return failSku;
    }




    @Override
    @DataSource(name = "stocking")
    public ResponseData exportTemplate(HttpServletResponse response, ShipmentRecommendationParam param) throws Exception {
        int exportTemplateCount = this.baseMapper.exportTemplateCount(param);
        if (exportTemplateCount == 0) {
            log.error("未找到该对应的数据");
            return ResponseData.error("未找到该对应的数据");
        }
//        List<ShipmentRecommendationTemplateResult> resultList = this.exportTemplateList(param);

        List<ShipmentRecommendationShopSkuResult> shopSkuList = this.getShopSkuListBySpec(param);

        String filename = "导出源模板";
        String modelPath = "classpath:template/shipmentRecommendation.xlsx";//模板所在路径
        org.springframework.core.io.Resource resource = resourceLoader.getResource(modelPath);
        InputStream inputStream = resource.getInputStream();

        ExcelWriter excelWriter = null;
        try {
            OutputStream outputStream = response.getOutputStream();
            String encoderFileName = URLEncoder.encode(filename, "UTF-8");
            response.setContentType("application/vnd.ms-excel");
            response.setCharacterEncoding("utf-8");
            response.setHeader("Access-Control-Expose-Headers", "Content-Disposition");
            response.setHeader("Content-disposition", "attachment;filename=" + encoderFileName + ".xlsx");

            excelWriter = EasyExcel.write(outputStream).withTemplate(inputStream).build();

            param.setPageSize((long) Integer.MAX_VALUE);
            List<ShipmentRecommendation> results = service.shipmentRecommendationList(param).getRows();
            WriteSheet writeSheet0 = EasyExcel.writerSheet(0, "发货推荐").build();
//            WriteSheet writeSheet1 = EasyExcel.writerSheet(1,"发货推荐组合SKU").build();
            WriteSheet writeSheet2 = EasyExcel.writerSheet(1, "店铺SKU明细").build();
            FillConfig fillConfig = FillConfig.builder().build();
            if (ObjectUtil.isNotEmpty(results)) {
                excelWriter.fill(results, fillConfig, writeSheet0);
            }
//            if (ObjectUtil.isNotEmpty(resultList)) {
//                excelWriter.fill(resultList, fillConfig, writeSheet1);
//            }
            if (ObjectUtil.isNotEmpty(shopSkuList)) {
                excelWriter.fill(shopSkuList, fillConfig, writeSheet2);
            }
            Workbook workbook = excelWriter.writeContext().writeWorkbookHolder().getWorkbook();
            workbook.setForceFormulaRecalculation(true);
            return ResponseData.success();

        } catch (Exception e) {
            log.error("发货推荐导出源模板:{}", e);
            return ResponseData.error(StrUtil.format("发货推荐导出源模板:{}", e));

        } finally {
            if (excelWriter != null) {
                excelWriter.finish();
            }
            try {
                inputStream.close();
            } catch (IOException e) {
                log.error(String.valueOf(e));
            }
        }
    }

    @Override
    @DataSource(name = "stocking")
    public ResponseData exportTemplateSku(HttpServletResponse response, ShipmentRecommendationParam param) throws Exception {
        int exportTemplateCount = this.baseMapper.exportTemplateCount(param);
        if (exportTemplateCount == 0) {
            log.error("未找到该对应的数据");
            return ResponseData.error("Current user query condition cannot find combined SKU recommendation data!");
//            throw new Exception("未找到该对应的数据");
        }
        List<ShipmentRecommendationTemplateResult> resultList = this.exportTemplateList(param);

        if (ObjectUtil.isEmpty(resultList)) {
            return ResponseData.error("Current user query condition cannot find combined SKU recommendation data!");
//            throw new Exception("未找到该对应的数据");
        }
        String filename = "组合SKU推荐";
        String modelPath = "classpath:template/shipmentRecommendationSku.xlsx";//模板所在路径
        org.springframework.core.io.Resource resource = resourceLoader.getResource(modelPath);
        InputStream inputStream = resource.getInputStream();

        ExcelWriter excelWriter = null;
        try {
            OutputStream outputStream = response.getOutputStream();
            String encoderFileName = URLEncoder.encode(filename, "UTF-8");
            response.setContentType("application/vnd.ms-excel");
            response.setCharacterEncoding("utf-8");
            response.setHeader("Access-Control-Expose-Headers", "Content-Disposition");
            response.setHeader("Content-disposition", "attachment;filename=" + encoderFileName + ".xlsx");

            excelWriter = EasyExcel.write(outputStream).withTemplate(inputStream).build();

            WriteSheet writeSheet1 = EasyExcel.writerSheet(0, "组合SKU推荐").build();
            FillConfig fillConfig = FillConfig.builder().build();
            excelWriter.fill(resultList, fillConfig, writeSheet1);
            Workbook workbook = excelWriter.writeContext().writeWorkbookHolder().getWorkbook();
            workbook.setForceFormulaRecalculation(true);
            return ResponseData.success();

        } catch (Exception e) {
            log.error("发货推荐导出源模板:{}", e);
            return ResponseData.error(StrUtil.format("Shipment combination SKU recommendation export exception{}", JSONUtil.toJsonStr(e)));

        } finally {
            if (excelWriter != null) {
                excelWriter.finish();
            }
            try {
                inputStream.close();
            } catch (IOException e) {
                log.error(String.valueOf(e));
            }
        }
    }




    private Page getPageContext() {
        return PageFactory.defaultPage();
    }

    private String getUserName() {
        LoginUser loginUser = LoginContext.me().getLoginUser();
        return loginUser.getName();
    }

    private String getUserAccount() {
        LoginUser loginUser = LoginContext.me().getLoginUser();
        return loginUser.getAccount();
    }
}
