package com.tadpole.cloud.platformSettlement.api.inventory.model.result;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.io.Serializable;

/**
 * @author jobob
 * @since 2021-06-07
 */
@Data
@ApiModel
@ExcelIgnoreUnannotated
public class SalesOutErpResult implements Serializable {

  /**
   * 单据编号
   */
  @ApiModelProperty(value = "FCustMatID")
  private String FCustMatID;

  /**
   * 年份
   */
  @ApiModelProperty(value = "F_Customer_SKU")
  private String F_Customer_SKU;

  /**
   * 月份
   */
  @ApiModelProperty(value = "FMaterialID")
  private String FMaterialID;

  /**
   * 平台
   */
  @ApiModelProperty(value = "FUnitID")
  private String FUnitID;

  /**
   * 账号
   */
  @ApiModelProperty(value = "FRealQty ")
  private String FRealQty;

  /**
   * 站点
   */
  @ApiModelProperty(value = "FOwnerTypeID")
  private String FOwnerTypeID;

  /**
   * 库存组织编码
   */
  @ApiModelProperty(value = "FStockID")
  private String FStockID;

  /**
   * 库存状态
   */
  @ApiModelProperty(value = "FSALBASEQTY")
  private String FSALBASEQTY;

  /**
   * WAREHOUSE_NAME
   */
  @ApiModelProperty(value = "FPRICEBASEQTY")
  private String FPRICEBASEQTY;

  /**
   * 仓库编码
   */
  @ApiModelProperty(value = "FBASEUNITQTY")
  private String FBASEUNITQTY;

  /**
   * 仓库编码
   */
  @ApiModelProperty(value = "WAREHOUSE_NAME")
  private String WAREHOUSE_NAME;

  /**
   * 仓库编码
   */
  @ApiModelProperty(value = "F_BSC_Team")
  private String F_BSC_Team;

  /**
   * 备注1：（马帮erp传的是订单编号）
   */
  @ApiModelProperty(value = "F_BSC_SubRemark1")
  private String F_BSC_SubRemark1;
}
