package com.tadpole.cloud.operationManagement.modular.stock.controller;

import cn.stylefeng.guns.cloud.libs.scanner.annotation.GetResource;
import cn.stylefeng.guns.cloud.libs.scanner.stereotype.ApiResource;
import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import com.tadpole.cloud.operationManagement.modular.stock.model.params.SysMaterialParam;
import com.tadpole.cloud.operationManagement.modular.stock.model.result.SysMaterialResult;
import com.tadpole.cloud.operationManagement.modular.stock.service.RpMaterialService;
import com.tadpole.cloud.platformSettlement.api.finance.entity.Material;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 物料属性基础数据 前端控制器
 *
 * @author cyt
 * @since 2022-06-21
 */
@RestController
@ApiResource(name = "物料属性基础数据", path = "/RpMaterial")
@Api(tags = "物料属性基础数据")
public class RpMaterialController {

  @Autowired
  private RpMaterialService service;

  @GetResource(name = "物流方式", path = "/logisticsMode", requiredPermission = false)
  @ApiOperation(value = "物流方式", response = SysMaterialResult.class)
  public ResponseData getLogisticsModeSelect() {
    return ResponseData.success(service.getLogisticsModeSelect());
  }

  @GetResource(name = "物料编码", path = "/materialCode", requiredPermission = false)
  @ApiOperation(value = "物料编码", response = SysMaterialResult.class)
  public ResponseData getMaterialCode() {
    return ResponseData.success(service.getMaterialCode());
  }

  @GetResource(name = "类目", path = "/Category", requiredPermission = false)
  @ApiOperation(value = "类目", response = SysMaterialResult.class)
  public ResponseData getCategorySelect() {
    return ResponseData.success(service.getCategorySelect());
  }

  @GetResource(name = "运营大类", path = "/ProductType", requiredPermission = false)
  @ApiOperation(value = "运营大类", response = SysMaterialResult.class)
  public ResponseData getProductTypeSelect(
      @RequestParam(value = "category", required = false, defaultValue = "") String category) {

    return ResponseData.success(service.getProductTypeSelect(category));
  }

  @GetResource(name = "物料产品名称", path = "/ProductName", requiredPermission = false)
  @ApiOperation(value = "物料产品名称", response = SysMaterialResult.class)
  public ResponseData getProductSelect(
      @RequestParam(value = "productType", required = false, defaultValue = "")
          String productType) {
    return ResponseData.success(service.getProductSelect(productType));
  }

  @GetResource(name = "物料款式", path = "/style", requiredPermission = false)
  @ApiOperation(value = "物料款式", response = SysMaterialResult.class)
  public ResponseData getStyleSelect(@RequestParam( value = "product",required = false, defaultValue = "") String product) {
    return ResponseData.success(service.getStyleSelect(product));
  }

  @GetResource(name = "物料主材料", path = "/MainMaterial", requiredPermission = false)
  @ApiOperation(value = "物料主材料", response = SysMaterialResult.class)
  public ResponseData getMainMaterialSelect(
      @RequestParam(value = "style", required = false, defaultValue = "") String style) {
    return ResponseData.success(service.getMainMaterialSelect(style));
  }

  @GetResource(name = "物料图案", path = "/pattern", requiredPermission = false)
  @ApiOperation(value = "物料图案", response = SysMaterialResult.class)
  public ResponseData getPatternSelect(
          @RequestParam(value = "material", required = false, defaultValue = "") String material)  {
    return ResponseData.success(service.getPatternSelect(material));
  }

  @GetResource(name = "公司品牌", path = "/brand", requiredPermission = false)
  @ApiOperation(value = "公司品牌", response = SysMaterialResult.class)
  public ResponseData getBrandSelect() {
    return ResponseData.success(service.getBrandSelect());
  }

  @GetResource(name = "适用品牌", path = "/fitBrand", requiredPermission = false)
  @ApiOperation(value = "适用品牌", response = SysMaterialResult.class)
  public ResponseData getFitBrandSelect() {
    return ResponseData.success(service.getFitBrandSelect());
  }

  @GetResource(name = "型号", path = "/model", requiredPermission = false)
  @ApiOperation(value = "型号", response = SysMaterialResult.class)
  public ResponseData getModelSelect(
          @RequestParam(value = "productName", required = false, defaultValue = "") String productName) {
    return ResponseData.success(service.getModelSelect(productName));
  }

  @GetResource(name = "颜色", path = "/color", requiredPermission = false)
  @ApiOperation(value = "颜色", response = SysMaterialResult.class)
  public ResponseData getColorSelect(
          @RequestParam(value = "model", required = false, defaultValue = "") String model) {
    return ResponseData.success(service.getColorSelect(model));
  }

  @GetResource(name = "尺码", path = "/size", requiredPermission = false)
  @ApiOperation(value = "尺码", response = SysMaterialResult.class)
  public ResponseData getSizeSelect() {
    return ResponseData.success(service.getSizeSelect());
  }

  @GetResource(name = "包装数量", path = "/packing", requiredPermission = false)
  @ApiOperation(value = "包装数量", response = SysMaterialResult.class)
  public ResponseData getPackSelect() {
    return ResponseData.success(service.getPackSelect());
  }

  @GetResource(name = "适用机型", path = "/type", requiredPermission = false)
  @ApiOperation(value = "适用机型", response = SysMaterialResult.class)
  public ResponseData getTypeSelect(      @RequestParam(value = "productName", required = false, defaultValue = "")
      String productName) {
    return ResponseData.success(service.getTypeSelect(productName));
  }

  @GetResource(name = "节日标签", path = "/festivalLabel", requiredPermission = false)
  @ApiOperation(value = "节日标签", response = SysMaterialResult.class)
  public ResponseData getFestivalLabel(
          @RequestParam(value = "model", required = false, defaultValue = "") String model) {
    return ResponseData.success(service.getFestivalLabel(model));
  }

  @GetResource(name = "季节标签", path = "/seasonLabel", requiredPermission = false)
  @ApiOperation(value = "季节标签", response = SysMaterialResult.class)
  public ResponseData getSeasonLabel(
          @RequestParam(value = "model", required = false, defaultValue = "") String model) {
    return ResponseData.success(service.getSeasonLabel(model));
  }

  @GetResource(name = "产品名称", path = "/productName", requiredPermission = false)
  @ApiOperation(value = "产品名称", response = SysMaterialResult.class)
  public ResponseData getProductName(
          @RequestParam(value = "productName", required = false, defaultValue = "") String productName) {
    return ResponseData.success(service.getProductName(productName));
  }



  @GetResource(name = "版本", path = "/version", requiredPermission = false)
  @ApiOperation(value = "版本", response = SysMaterialResult.class)
  public ResponseData getVersionSelect() {
    return ResponseData.success(service.getVersionSelect());
  }


  @GetResource(name = "款式二级标签下拉", path = "/StyleSecondLabel", requiredPermission = false)
  @ApiOperation(value = "款式二级标签下拉", response = SysMaterialResult.class)
  public ResponseData getStyleSecondLabel() {
    return ResponseData.success(service.StyleSecondLabel());
  }






  //产品线权限下拉


  @GetResource(name = "物料列表-产品线权限", path = "/codeByPerson", requiredPermission = false)
  @ApiOperation(value = "物料列表-产品线权限权限", response = SysMaterialResult.class)
  public ResponseData codeByPerson(SysMaterialParam param) {
    return ResponseData.success(service.selectCodeByPerson(param));
  }

  @GetResource(name = "运营大类-产品线权限", path = "/productTypeByPerson", requiredPermission = false)
  @ApiOperation(value = "运营大类-产品线权限权限", response = SysMaterialResult.class)
  public ResponseData productTypeByPerson(SysMaterialParam param) {
    return ResponseData.success(service.selectProductTypeByPerson(param));
  }

  @GetResource(name = "产品名称-产品线权限", path = "/productNameByPerson", requiredPermission = false)
  @ApiOperation(value = "产品名称-产品线权限权限", response = SysMaterialResult.class)
  public ResponseData productNameByPerson(SysMaterialParam param) {
    return ResponseData.success(service.selectProductNameByPerson(param));
  }

  @GetResource(name = "款式-产品线权限", path = "/styleByPerson", requiredPermission = false)
  @ApiOperation(value = "款式-产品线权限权限", response = SysMaterialResult.class)
  public ResponseData styleByPerson(SysMaterialParam param) {
    return ResponseData.success(service.selectStyleByPerson(param));
  }

  @GetResource(name = "主材料-产品线权限", path = "/materialByPerson", requiredPermission = false)
  @ApiOperation(value = "主材料-产品线权限权限", response = SysMaterialResult.class)
  public ResponseData materialByPerson(SysMaterialParam param) {
    return ResponseData.success(service.selectMaterialByPerson(param));
  }

  @GetResource(name = "图案-产品线权限", path = "/patternByPerson", requiredPermission = false)
  @ApiOperation(value = "图案-产品线权限权限", response = SysMaterialResult.class)
  public ResponseData patternByPerson(SysMaterialParam param) {
    return ResponseData.success(service.selectPatternByPerson(param));
  }

  @GetResource(name = "公司品牌-产品线权限", path = "/companyBrandByPerson", requiredPermission = false)
  @ApiOperation(value = "公司品牌-产品线权限权限", response = SysMaterialResult.class)
  public ResponseData companyBrandByPerson(SysMaterialParam param) {
    return ResponseData.success(service.selectCompanyBrandByPerson(param));
  }


  @GetResource(name = "品牌-产品线权限", path = "/brandByPerson", requiredPermission = false)
  @ApiOperation(value = "品牌-产品线权限权限", response = SysMaterialResult.class)
  public ResponseData brandByPerson(SysMaterialParam param) {
    return ResponseData.success(service.selectBrandByPerson(param));
  }

  @GetResource(name = "型号-产品线权限", path = "/modelByPerson", requiredPermission = false)
  @ApiOperation(value = "型号-产品线权限权限", response = SysMaterialResult.class)
  public ResponseData modelByPerson(SysMaterialParam param) {
    return ResponseData.success(service.selectModelByPerson(param));
  }

  @GetResource(name = "颜色-产品线权限", path = "/colorByPerson", requiredPermission = false)
  @ApiOperation(value = "颜色-产品线权限权限", response = SysMaterialResult.class)
  public ResponseData colorByPerson(SysMaterialParam param) {
    return ResponseData.success(service.selectColorByPerson(param));
  }

  @GetResource(name = "尺码-产品线权限", path = "/sizeByPerson", requiredPermission = false)
  @ApiOperation(value = "尺码-产品线权限权限", response = SysMaterialResult.class)
  public ResponseData sizeByPerson(SysMaterialParam param) {
    return ResponseData.success(service.selectSizeByPerson(param));
  }

  @GetResource(name = "包装数量-产品线权限", path = "/packingByPerson", requiredPermission = false)
  @ApiOperation(value = "包装数量-产品线权限权限", response = SysMaterialResult.class)
  public ResponseData packingByPerson(SysMaterialParam param) {
    return ResponseData.success(service.selectPackingByPerson(param));
  }

  @GetResource(name = "版本-产品线权限", path = "/versionByPerson", requiredPermission = false)
  @ApiOperation(value = "版本-产品线权限权限", response = SysMaterialResult.class)
  public ResponseData versionByPerson(SysMaterialParam param) {
    return ResponseData.success(service.selectVersionByPerson(param));
  }

  @GetResource(name = "款式二级标签-产品线权限", path = "/styleSecondLabelByPerson", requiredPermission = false)
  @ApiOperation(value = "款式二级标签-产品线权限权限", response = SysMaterialResult.class)
  public ResponseData styleSecondLabelByPerson(SysMaterialParam param) {
    return ResponseData.success(service.selectStyleSecondLabelByPerson(param));
  }

  @GetResource(name = "根据物料编码获取物料信息", path = "/materialInfo", requiredPermission = false)
  @ApiOperation(value = "根据物料编码获取物料信息", response = Material.class)
  public ResponseData getMaterialInfo(@RequestParam String materialCode) {
    return ResponseData.success(service.getMaterialInfo(materialCode));
  }
}
