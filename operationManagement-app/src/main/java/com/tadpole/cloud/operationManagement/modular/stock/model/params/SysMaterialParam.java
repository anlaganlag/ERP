package com.tadpole.cloud.operationManagement.modular.stock.model.params;

import cn.stylefeng.guns.cloud.model.validator.BaseValidatingParam;
import cn.stylefeng.guns.cloud.model.web.request.BaseRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author jobob
 * @since 2021-06-07
 */
@Data
@ApiModel
public class SysMaterialParam extends BaseRequest implements  Serializable, BaseValidatingParam {

  private static final long serialVersionUID = 1L;

  @ApiModelProperty("spu")
  private String spu;

  @ApiModelProperty("logistics_mode")
  private String logisticsMode;

  @ApiModelProperty("material_code")
  private String materialCode;

  @ApiModelProperty("category")
  private String category;

  @ApiModelProperty("product_type")
  private String productType;

  @ApiModelProperty("product_name")
  private String productName;

  @ApiModelProperty("style")
  private String style;

  @ApiModelProperty("type")
  private String type;

  @ApiModelProperty("main_material")
  private String mainMaterial;

  @ApiModelProperty("pattern")
  private String pattern;

  @ApiModelProperty("company_brand")
  private String companyBrand;

  @ApiModelProperty("brand")
  private String brand;

  @ApiModelProperty("model")
  private String model;

  @ApiModelProperty("forbidstatus")
  private String forbidstatus;

  @ApiModelProperty("matEditState")
  private String matEditState;

  @ApiModelProperty("operator")
  private String operator;


  @ApiModelProperty("team")
  private String team;


  @ApiModelProperty("department")
  private String department;


  @ApiModelProperty("platform")
  private String platform;

  @ApiModelProperty("area")
  private String area;


  @ApiModelProperty("spu列表")
  private List<String> spuList;

  @ApiModelProperty("物料编码列表")
  private List<String> materialCodeList;

  public static long getSerialVersionUID() {
    return serialVersionUID;
  }

  public String getSpu() {
    return spu;
  }

  public void setSpu(String spu) {
    this.spu = spu;
  }


  public void getSpuList(List<String> spuList) {
    this.spuList = spuList;
  }
  public void setSpuList(List<String> spuList) {
    this.spuList = spuList;
  }

  public String getLogisticsMode() {
    return logisticsMode;
  }

  public void setLogisticsMode(String logisticsMode) {
    this.logisticsMode = logisticsMode;
  }

  public String getMaterialCode() {
    return materialCode;
  }
  public List<String> getMaterialCodeList() {
    return materialCodeList;
  }

  public void setMaterialCode(String materialCode) {
    this.materialCode = materialCode;
  }
  public void setMaterialCodeList(List<String> materialCodeList) {
    this.materialCodeList = materialCodeList;
  }

  public String getCategory() {
    return category;
  }

  public void setCategory(String category) {
    this.category = category;
  }

  public String getProductType() {
    return productType;
  }

  public void setProductType(String productType) {
    this.productType = productType;
  }

  public String getProductName() {
    return productName;
  }

  public void setProductName(String productName) {
    this.productName = productName;
  }

  public String getStyle() {
    return style;
  }

  public void setStyle(String style) {
    this.style = style;
  }

  public String getMainMaterial() {
    return mainMaterial;
  }

  public void setMainMaterial(String mainMaterial) {
    this.mainMaterial = mainMaterial;
  }

  public String getPattern() {
    return pattern;
  }

  public void setPattern(String pattern) {
    this.pattern = pattern;
  }

  public String getCompanyBrand() {
    return companyBrand;
  }

  public void setCompanyBrand(String companyBrand) {
    this.companyBrand = companyBrand;
  }

  public String getBrand() {
    return brand;
  }

  public void setBrand(String brand) {
    this.brand = brand;
  }

  public String getModel() {
    return model;
  }

  public void setModel(String model) {
    this.model = model;
  }

  public String getForbidstatus() {
    return forbidstatus;
  }

  public void setForbidstatus(String forbidstatus) {
    this.forbidstatus = forbidstatus;
  }

  public String getMatEditState() {
    return matEditState;
  }

  public void setMatEditState(String matEditState) {
    this.matEditState = matEditState;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public String getOperator() {
    return operator;
  }

  public void setOperator(String operator) {
    this.operator = operator;
  }
}
