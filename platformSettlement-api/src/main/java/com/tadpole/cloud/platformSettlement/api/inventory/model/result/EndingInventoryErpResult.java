package com.tadpole.cloud.platformSettlement.api.inventory.model.result;

import cn.stylefeng.guns.cloud.model.validator.BaseValidatingParam;
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
public class EndingInventoryErpResult implements Serializable, BaseValidatingParam {

  private static final long serialVersionUID = 1L;

  /**
   * 单据编号
   */
  @ApiModelProperty(value = "FCountQty")
  private String FCountQty;

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
  @ApiModelProperty(value = "F_REQUIRETEAM")
  private String F_REQUIRETEAM;
}
