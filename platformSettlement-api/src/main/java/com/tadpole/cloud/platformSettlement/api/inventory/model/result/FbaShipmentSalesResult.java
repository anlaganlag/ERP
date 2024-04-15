package com.tadpole.cloud.platformSettlement.api.inventory.model.result;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import lombok.Data;

/**
 * @author jobob
 * @since 2021-06-07
 */
@Data
@ApiModel
@ExcelIgnoreUnannotated
public class FbaShipmentSalesResult implements Serializable {

  private static final long serialVersionUID = 1L;

  @ApiModelProperty("ID")
  private Long id;

  @ApiModelProperty("OUT_ID")
  private Long outId;

  /**
   * 发货日期
   */
  @ApiModelProperty("SHIPMENT_DATE")
  private Date shipmentDate;

  /**
   * 买家SKU
   */
  @ApiModelProperty("SKU")
  private String sku;

  @ApiModelProperty("FNSKU")
  private String fnsku;

  @ApiModelProperty("ASIN")
  private String asin;

  /**
   * 运营中心编号
   */
  @ApiModelProperty("FULFILLMENT_CENTER_ID")
  private String fulfillmentCenterId;

  /**
   * 数量
   */
  @ApiModelProperty("QUANTITY")
  private BigDecimal quantity;

  /**
   * 亚马逊订单编号
   */
  @ApiModelProperty("AMAZON_ORDER_ID")
  private String amazonOrderId;

  /**
   * 货币
   */
  @ApiModelProperty("CURRENCY")
  private String currency;

  /**
   * 每件商品价格
   */
  @ApiModelProperty("ITEM_PRICE_PER_UNIT")
  private BigDecimal itemPricePerUnit;

  /**
   * 配送价格
   */
  @ApiModelProperty("SHIPPING_PRICE")
  private BigDecimal shippingPrice;

  /**
   * 买家为礼品包装支付的金额
   */
  @ApiModelProperty("GIFT_WRAP_PRICE")
  private BigDecimal giftWrapPrice;

  /**
   * 配送城市
   */
  @ApiModelProperty("SHIP_CITY")
  private String shipCity;

  /**
   * 配送州
   */
  @ApiModelProperty("SHIP_STATE")
  private String shipState;

  /**
   * 配送邮政编码
   */
  @ApiModelProperty("SHIP_POSTAL_CODE")
  private String shipPostalCode;

  /**
   * 创建日期
   */
  @ApiModelProperty("CREATE_TIME")
  private Date createTime;

  /**
   * 上传日期
   */
  @ApiModelProperty("UPLOAD_DATE")
  private Date uploadDate;

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

  @ApiModelProperty("SYS_SELLER_ID")
  private String sysSellerId;

  /**
   * 平台
   */
  @ApiModelProperty("PLATFORM")
  private String platform;

  @ApiModelProperty("VERIFY_AT")
  private String verifyAt;

  @ApiModelProperty("VERIFY_BY")
  private String verifyBy;

  @ApiModelProperty("VERIFY_STATUS")
  private String verifyStatus;

  @ApiModelProperty("UPDATE_BY")
  private String updateBy;

  @ApiModelProperty("BILL_CODE")
  private String billCode;

  @ApiModelProperty("GENERATE_STATUS")
  private String generateStatus;

  @ApiModelProperty("SALES_ORGANIZATION")
  private String salesOrganization;

  @ApiModelProperty("SALES_ORGANIZATION_CODE")
  private String salesOrganizationCode;

  @ApiModelProperty("ORG")
  private String org;

  @ApiModelProperty("INVENTORY_ORGANIZATION_CODE")
  private String inventoryOrganizationCode;

  @ApiModelProperty("WAREHOUSE_NAME")
  private String warehouseName;

  @ApiModelProperty("WAREHOUSE_CODE")
  private String warehouseCode;

  @ApiModelProperty("year")
  private String year;

  @ApiModelProperty("month")
  private String month;
}
