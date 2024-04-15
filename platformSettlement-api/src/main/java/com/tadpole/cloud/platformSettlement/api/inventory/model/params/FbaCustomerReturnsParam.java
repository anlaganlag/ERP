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
public class FbaCustomerReturnsParam extends BaseRequest implements Serializable, BaseValidatingParam {

  private static final long serialVersionUID = 1L;

  @ApiModelProperty("ID")
  private Long id;

  /**
   * 发货日期
   */
  @ApiModelProperty("RETURN_DATE")
  private String returnDate;

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

  @ApiModelProperty("START_DATE")
  private String startDate;

  @ApiModelProperty("END_DATE")
  private String endDate;

  @ApiModelProperty("VERIFY_STATUS")
  private String verifyStatus;

  @ApiModelProperty("UPDATE_By")
  private String updateBy;

  @ApiModelProperty("VERIFY_BY")
  private String verifyBy;

  @ApiModelProperty("GENERATE_STATUS")
  private Integer generateStatus;

  private List skus;

  private List sysShopsNames;

  private List sysSites;

  private List verifyStatuss;

  private List generateStatuss;

  @ApiModelProperty("ORDER_ID")
  private String orderId;

  private String salesCode;

  @ApiModelProperty("AMAZON_ORDER_ID")
  private String amazonOrderId;

  @ApiModelProperty("SALES_ORGANIZATION")
  private String salesOrganization;

  @ApiModelProperty("SALES_ORGANIZATION_CODE")
  private String salesOrganizationCode;

  @ApiModelProperty("INVENTORY_ORGANIZATION")
  private String inventoryOrganization;

  @ApiModelProperty("WAREHOUSE_NAME")
  private String warehouseName;

  @ApiModelProperty("WAREHOUSE_CODE")
  private String warehouseCode;

  @ApiModelProperty("SALES_PLATFORM")
  private String platform;

  private String salesPlatform;

  @ApiModelProperty("INVENTORY_ORGANIZATION_CODE")
  private String inventoryOrganizationCode;

  private String mat;

  private String uploadMark;

  private List<String> idList;
}
