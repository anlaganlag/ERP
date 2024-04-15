package com.tadpole.cloud.platformSettlement.api.inventory.model.result;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.Data;

/**
 * @author jobob
 * @since 2021-06-07
 */
@Data
@ApiModel
@ExcelIgnoreUnannotated
public class FbaInventoryAdjustmentsResult implements Serializable {

  private static final long serialVersionUID = 1L;

  @ApiModelProperty("ID")
  private BigDecimal id;

  @ApiModelProperty("ADJUSTED_DATE")
  private LocalDateTime adjustedDate;

  @ApiModelProperty("TRANSACTION_ITEM_ID")
  private String transactionItemId;

  @ApiModelProperty("FNSKU")
  private String fnsku;

  @ApiModelProperty("SKU")
  private String sku;

  @ApiModelProperty("FULFILLMENT_CENTER_ID")
  private String fulfillmentCenterId;

  @ApiModelProperty("QUANTITY")
  private Long quantity;

  @ApiModelProperty("REASON")
  private String reason;

  @ApiModelProperty("DISPOSITION")
  private String disposition;

  @ApiModelProperty("RECONCILED")
  private Long reconciled;

  @ApiModelProperty("UNRECONCILED")
  private Long unreconciled;

  @ApiModelProperty("SYS_SITE")
  private String sysSite;

  @ApiModelProperty("SYS_SHOPS_NAME")
  private String sysShopsName;

  @ApiModelProperty("CREATE_TIME")
  private LocalDateTime createTime;

  @ApiModelProperty("UPLOAD_DATE")
  private LocalDateTime uploadDate;

  @ApiModelProperty("ASIN")
  private String asin;

  @ApiModelProperty("SYS_SELLER_ID")
  private String sysSellerId;

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
  private String updateAt;

  @ApiModelProperty("ORG")
  private String org;
}
