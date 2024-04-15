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
public class ErpAdjustResult implements Serializable, BaseValidatingParam {

  /**
   * 单据编号
   */
  @ApiModelProperty(value = "FMATERIALID")
  private String FMATERIALID;

  @ApiModelProperty(value = "FUnitID")
  private String FUnitID;

  @ApiModelProperty(value = "FSTOCKID")
  private String FSTOCKID;

  @ApiModelProperty(value = "FSTOCKSTATUSID")
  private String FSTOCKSTATUSID;

  @ApiModelProperty(value = "FQty")
  private String FQty;

  @ApiModelProperty(value = "FOWNERTYPEID")
  private String FOWNERTYPEID;

  @ApiModelProperty(value = "FOWNERID")
  private String FOWNERID;

  @ApiModelProperty(value = "FOwnerIdHead")
  private String FOwnerIdHead;

  @ApiModelProperty(value = "FKEEPERTYPEID")
  private String FKEEPERTYPEID;

  @ApiModelProperty(value = "FKEEPERID")
  private String FKEEPERID;

  @ApiModelProperty(value = "F_REQUIRETEAM")
  private String F_REQUIRETEAM;

  @ApiModelProperty(value = "warehouse_name")
  private String warehouse_name;

  @ApiModelProperty(value = "org")
  private String org;
}
