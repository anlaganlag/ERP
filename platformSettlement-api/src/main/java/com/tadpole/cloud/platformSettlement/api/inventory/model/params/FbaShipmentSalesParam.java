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
public class FbaShipmentSalesParam extends BaseRequest implements Serializable, BaseValidatingParam {

  private static final long serialVersionUID = 1L;

  @ApiModelProperty("ID")
  private Long id;

  /**
   * 发货日期
   */
  @ApiModelProperty("SHIPMENT_DATE")
  private String shipmentDate;

  /**
   * 买家SKU
   */
  @ApiModelProperty("SKU")
  private String sku;

  /**
   * 账号
   */
  @ApiModelProperty("SYS_SHOPS_NAME")
  private String sysShopsName;

  /**
   * 站点
   */
  @ApiModelProperty("SYS_SITE")
  private String sysSite;

  /**
   * 亚马逊订单编号
   */
  @ApiModelProperty("AMAZON_ORDER_ID")
  private String amazonOrderId;

  @ApiModelProperty("VERIFY_STATUS")
  private String verifyStatus;

  @ApiModelProperty("UPDATE_BY")
  private String updateBy;

  @ApiModelProperty("VERIFY_BY")
  private String verifyBy;

  @ApiModelProperty("GENERATE_STATUS")
  private Integer generateStatus;

  @ApiModelProperty("START_DATE")
  private String startDate;

  @ApiModelProperty("END_DATE")
  private String endDate;

  @ApiModelProperty("SALES_ORGANIZATION")
  private String salesOrganization;

  @ApiModelProperty("SALES_ORGANIZATION_CODE")
  private String salesOrganizationCode;

  @ApiModelProperty("INVENTORY_ORGANIZATION")
  private String inventoryOrganization;

  @ApiModelProperty("INVENTORY_ORGANIZATION_CODE")
  private String inventoryOrganizationCode;

  @ApiModelProperty("WAREHOUSE_NAME")
  private String warehouseName;

  @ApiModelProperty("WAREHOUSE_CODE")
  private String warehouseCode;

  @ApiModelProperty("platform")
  private String platform;

  private String salesCode;

  private String orderId;

  private List skus;

  private List sysShopsNames;

  private List sysSites;

  private List verifyStatuss;

  private List generateStatuss;

  private String mat;

  private String currency;

  private String uploadMark;

  private String verifyAt;

  private List<String> idList;
}
