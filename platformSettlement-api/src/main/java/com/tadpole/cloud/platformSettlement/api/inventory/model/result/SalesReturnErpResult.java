package com.tadpole.cloud.platformSettlement.api.inventory.model.result;

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
public class SalesReturnErpResult implements Serializable {

  /**
   * 单据编号
   */
  @ApiModelProperty(value = "FMaterialId")
  private String FMaterialId;

  /**
   * 年份
   */
  @ApiModelProperty(value = "FStockId")
  private String FStockId;

  /**
   * 月份
   */
  @ApiModelProperty(value = "FDeliveryDate")
  private String FDeliveryDate;

  /**
   * 平台
   */
  @ApiModelProperty(value = "FRealQty")
  private String FRealQty;

  @ApiModelProperty(value = "warehouseName")
  private String warehouseName;

  @ApiModelProperty(value = "F_REQUIRETEAM")
  private String F_REQUIRETEAM;
}
