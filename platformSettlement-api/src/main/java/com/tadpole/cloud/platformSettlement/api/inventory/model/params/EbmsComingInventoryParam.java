package com.tadpole.cloud.platformSettlement.api.inventory.model.params;

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
public class EbmsComingInventoryParam extends BaseRequest implements Serializable, BaseValidatingParam {

  private static final long serialVersionUID = 1L;

  @ApiModelProperty("SKU")
  private List sku;

  private List shopName;

  private List site;

  @ApiModelProperty("START_DATE")
  private String startDate;

  @ApiModelProperty("END_DATE")
  private String endDate;

  private List skus;

  @ApiModelProperty("物料编码集合")
  private List<String> materialCodeList;

  @ApiModelProperty("库存组织编码")
  private String inventoryOrganizationCode;
}
