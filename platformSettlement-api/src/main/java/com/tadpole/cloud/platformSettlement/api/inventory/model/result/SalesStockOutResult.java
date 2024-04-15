package com.tadpole.cloud.platformSettlement.api.inventory.model.result;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author jobob
 * @since 2021-06-07
 */
@Data
@ApiModel
@ExcelIgnoreUnannotated
public class SalesStockOutResult implements Serializable {

  private static final long serialVersionUID = 1L;

  @ApiModelProperty("ID")
  private BigDecimal id;

  @ApiModelProperty("OUT_ID")
  private BigDecimal outId;
  /**
   * 单据编号
   */
  @ApiModelProperty(value = "BILL_CODE")
  @ExcelProperty(value = "单据编号")
  private String billCode;

  /**
   * 年份
   */
  @ApiModelProperty(value = "YEAR")
  @ExcelProperty(value = "年份")
  private String year;

  /**
   * 月份
   */
  @ApiModelProperty(value = "MONTH")
  @ExcelProperty(value = "月份")
  private String month;

  /**
   * 平台
   */
  @ApiModelProperty(value = "PLATFORM")
  @ExcelProperty(value = "平台")
  private String platform;

  /**
   * 账号
   */
  @ApiModelProperty(value = "SHOP_NAME")
  @ExcelProperty(value = "账号")
  private String shopName;

  /**
   * 站点
   */
  @ApiModelProperty(value = "SITE")
  @ExcelProperty(value = "站点")
  private String site;

  /**
   * 库存组织编码
   */
  @ApiModelProperty(value = "INVENTORY_ORGANIZATION_CODE")
  @ExcelProperty(value = "库存组织编码")
  private String inventoryOrganizationCode;

  /**
   * WAREHOUSE_NAME
   */
  @ApiModelProperty(value = "WAREHOUSE_NAME")
  @ExcelProperty(value = "仓库名称")
  private String warehouseName;

  /**
   * 仓库编码
   */
  @ApiModelProperty(value = "WAREHOUSE_CODE")
  @ExcelProperty(value = "仓库编码")
  private String warehouseCode;

  /**
   * 库存状态
   */
  @ApiModelProperty(value = "INVENTORY_STATUS")
  @ExcelProperty(value = "库存状态")
  private String inventoryStatus;

  /**
   * 事业部
   */
  @ApiModelProperty(value = "DEPARTMENT")
  @ExcelProperty(value = "事业部")
  private String department;

  /**
   * Team
   */
  @ApiModelProperty(value = "Team")
  @ExcelProperty(value = "Team")
  private String team;

  /**
   * SKU
   */
  @ApiModelProperty(value = "SKU")
  @ExcelProperty(value = "sku")
  private String sku;

  /**
   * 物料编码
   */
  @ApiModelProperty(value = "MATERIAL_CODE")
  @ExcelProperty(value = "物料编码")
  private String materialCode;

  /**
   * 销售组织
   */
  @ApiModelProperty(value = "SALES_ORGANIZATION")
  @ExcelProperty(value = "销售组织")
  private String salesOrganization;

  /**
   * 销售组织编码
   */
  @ApiModelProperty(value = "SALES_ORGANIZATION_CODE")
  @ExcelProperty(value = "销售组织编码")
  private String salesOrganizationCode;

  /**
   * 销售出库数量
   */
  @ApiModelProperty(value = "SALES_STOCKOUT_AMOUNT")
  @ExcelProperty(value = "销售出库数量")
  private String salesStockoutAmount;

  /**
   * 审核状态：未作废、已作废、已审核、无需处理
   */
  @ApiModelProperty(value = "IS_CANCEL")
  @ExcelProperty(value = "审核状态")
  private String isCancel;

  /**
   * 同步状态
   */
  @ApiModelProperty(value = "SYNC_STATUS")
  @ExcelProperty(value = "同步状态")
  private String syncStatus;

  /**
   * 最后操作时间
   */
  @ApiModelProperty(value = "UPDATE_AT")
  private Date updateAt;

  /**
   * 最后操作人
   */
  @ApiModelProperty(value = "UPDATE_BY")
  private String updateBy;
}
