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
public class MonthlyInventoryHistoryResult implements Serializable {

  private static final long serialVersionUID = 1L;

  @ApiModelProperty("ID")
  private Double id;

  @ApiModelProperty("DATE_MONTHLY")
  private String dateMonthly;

  @ApiModelProperty("FNSKU")
  private String fnsku;

  @ApiModelProperty("SKU")
  private String sku;

  @ApiModelProperty("PRODUCT_NAME")
  private String productName;

  @ApiModelProperty("AVERAGE_QUANTITY")
  private BigDecimal averageQuantity;

  @ApiModelProperty("END_QUANTITY")
  private BigDecimal endQuantity;

  @ApiModelProperty("FULFILLMENT_CENTER_ID")
  private String fulfillmentCenterId;

  @ApiModelProperty("DETAILED_DISPOSITION")
  private String detailedDisposition;

  @ApiModelProperty("SYS_SITE")
  private String sysSite;

  @ApiModelProperty("SYS_SELLER_ID")
  private String sysSellerId;

  @ApiModelProperty("SYS_SHOPS_NAME")
  private String sysShopsName;

  @ApiModelProperty("CREATE_TIME")
  private Date createTime;

  @ApiModelProperty("UPLOAD_DATE")
  private Date uploadDate;

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

  @ApiModelProperty("OUT_ID")
  private Long outId;

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

  @ApiModelProperty("ORG")
  private String org;
}
