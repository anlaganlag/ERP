package com.tadpole.cloud.platformSettlement.api.inventory.model.params;

import cn.stylefeng.guns.cloud.model.validator.BaseValidatingParam;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import lombok.Data;

/**
 * @author jobob
 * @since 2021-06-07
 */
@Data
@ApiModel
public class ErpWarehouseCodeParam implements Serializable, BaseValidatingParam {

  private static final long serialVersionUID = 1L;

  @ApiModelProperty("amazon_order_id")
  private String amazonOrderId;

  @ApiModelProperty("sku")
  private String sku;

  @ApiModelProperty("ORGANIZATION_TYPE")
  private String organizationType;
}
