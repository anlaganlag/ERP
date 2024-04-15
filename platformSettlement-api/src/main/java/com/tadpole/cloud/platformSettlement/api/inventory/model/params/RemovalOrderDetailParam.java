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
public class RemovalOrderDetailParam extends BaseRequest implements Serializable, BaseValidatingParam {

  private static final long serialVersionUID = 1L;

  @ApiModelProperty("ID")
  private Long id;

  /**
   * 发货日期
   */
  @ApiModelProperty("REQUEST_DATE")
  private String requestDate;

  /**
   * 买家SKU
   */
  @ApiModelProperty("SKU")
  private String sku;

  /**
   * 账号
   */
  @ApiModelProperty("SHOP_NAME")
  private String shopName;

  @ApiModelProperty("CURRENCY")
  private String currency;

  /**
   * 站点
   */
  @ApiModelProperty("site")
  private String site;


  @ApiModelProperty("START_DATE")
  private String startDate;

  @ApiModelProperty("END_DATE")
  private String endDate;

  @ApiModelProperty("INVENTORY_ORGANIZATION")
  private String inventoryOrganization;

  @ApiModelProperty("SALES_ORGANIZATION")
  private String salesOrganization;

  @ApiModelProperty("SALES_ORGANIZATION_CODE")
  private String salesOrganizationCode;

  @ApiModelProperty("INVENTORY_ORGANIZATION_CODE")
  private String inventoryOrganizationCode;

  @ApiModelProperty("WAREHOUSE_NAME")
  private String warehouseName;

  @ApiModelProperty("WAREHOUSE_CODE")
  private String warehouseCode;

  @ApiModelProperty("VERIFY_STATUS")
  private String verifyStatus;

  @ApiModelProperty("UPDATE_By")
  private String updateBy;

  @ApiModelProperty("GENERATE_STATUS")
  private Integer generateStatus;

  @ApiModelProperty("ORDER_ID")
  private String orderId;

  private List skus;

  private List sysShopsNames;

  private List sysSites;

  private String mat;

  private String uploadMark;
}
