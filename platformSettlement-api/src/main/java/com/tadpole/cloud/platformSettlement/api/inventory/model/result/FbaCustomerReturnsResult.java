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
public class FbaCustomerReturnsResult implements Serializable {

  private static final long serialVersionUID = 1L;

  @ApiModelProperty("ID")
  private BigDecimal id;

  /**
   * 退货日期
   */
  @ApiModelProperty("RETURN_DATE")
  private Date returnDate;

  /**
   * 订单编号
   */
  @ApiModelProperty("ORDER_ID")
  private String orderId;

  /**
   * SKU
   */
  @ApiModelProperty("SKU")
  private String sku;

  /**
   * ASIN
   */
  @ApiModelProperty("ASIN")
  private String asin;

  /**
   * FNSKU
   */
  @ApiModelProperty("FNSKU")
  private String fnsku;

  /**
   * 标题
   */
  @ApiModelProperty("PRODUCT_NAME")
  private String productName;

  /**
   * 数量
   */
  @ApiModelProperty("QUANTITY")
  private Long quantity;

  /**
   * FC
   */
  @ApiModelProperty("FULFILLMENT_CENTER_ID")
  private String fulfillmentCenterId;

  /**
   * 库存属性
   */
  @ApiModelProperty("DETAILED_DISPOSITION")
  private String detailedDisposition;

  /**
   * 原因
   */
  @ApiModelProperty("REASON")
  private String reason;

  /**
   * 状态
   */
  @ApiModelProperty("STATUS")
  private String status;

  /**
   * LPN
   */
  @ApiModelProperty("LICENSE_PLATE_NUMBER")
  private String licensePlateNumber;

  /**
   * 客户评价
   */
  @ApiModelProperty("CUSTOMER_COMMENTS")
  private String customerComments;

  @ApiModelProperty("SYS_SELLER_ID")
  private String sysSellerId;

  /**
   * 站点
   */
  @ApiModelProperty("SYS_SITE")
  private String sysSite;

  /**
   * 账号简称
   */
  @ApiModelProperty("SYS_SHOPS_NAME")
  private String sysShopsName;

  @ApiModelProperty("CREATE_TIME")
  private Date createTime;

  @ApiModelProperty("USER_NAME")
  private String userName;

  /**
   * 报告文件上传日期(生成上传报告任务的日期)
   */
  @ApiModelProperty("UPLOAD_DATE")
  private Date uploadDate;

  /**
   * 单据编号
   */
  @ApiModelProperty("BILL_CODE")
  private String billCode;

  /**
   * 年份
   */
  @ApiModelProperty("YEAR")
  private String year;

  /**
   * 月份
   */
  @ApiModelProperty("MONTH")
  private String month;

  /**
   * 平台
   */
  @ApiModelProperty("PLATFORM")
  private String platform;

  /**
   * 账号
   */
  @ApiModelProperty("SHOP_NAME")
  private String shopName;

  /**
   * 站点
   */
  @ApiModelProperty("SITE")
  private String site;

  /**
   * 库存组织编码
   */
  @ApiModelProperty("INVENTORY_ORGANIZATION_CODE")
  private String inventoryOrganizationCode;

  /**
   * 库存状态
   */
  @ApiModelProperty("INVENTORY_STATUS")
  private String inventoryStatus;

  /**
   * WAREHOUSE_NAME
   */
  @ApiModelProperty("WAREHOUSE_NAME")
  private String warehouseName;

  /**
   * 仓库编码
   */
  @ApiModelProperty("WAREHOUSE_CODE")
  private String warehouseCode;

  /**
   * 事业部
   */
  @ApiModelProperty("DEPARTMENT")
  private String department;

  /**
   * Team
   */
  @ApiModelProperty("Team")
  private String team;

  /**
   * 物料编码
   */
  @ApiModelProperty("MATERIAL_CODE")
  private String materialCode;

  /**
   * 销售组织
   */
  @ApiModelProperty("SALES_ORGANIZATION")
  private String salesOrganization;

  /**
   * 销售组织编码
   */
  @ApiModelProperty("SALES_ORGANIZATION_CODE")
  private String salesOrganizationCode;

  /**
   * 销售出库数量
   */
  @ApiModelProperty("SALES_STOCKOUT_AMOUNT")
  private String salesStockoutAmount;

  /**
   * 是否作废
   */
  @ApiModelProperty("IS_CANCEL")
  private String isCancel;

  /**
   * 同步状态
   */
  @ApiModelProperty("SYNC_STATUS")
  private String syncStatus;

  /**
   * 最后操作时间
   */
  @ApiModelProperty("UPDATE_AT")
  private Date updateAt;

  /**
   * 最后操作人
   */
  @ApiModelProperty("UPDATE_BY")
  private String updateBy;

  @ApiModelProperty("VERIFY_AT")
  private String verifyAt;

  @ApiModelProperty("VERIFY_BY")
  private String verifyBy;

  @ApiModelProperty("VERIFY_STATUS")
  private String verifyStatus;

  @ApiModelProperty("GENERATE_STATUS")
  private String generateStatus;

  @ApiModelProperty("ORG")
  private String org;

  @ApiModelProperty("OUT_ID")
  private Long outId;
}
