package com.tadpole.cloud.platformSettlement.api.inventory.model.result;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
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
public class RemoveMainResult implements Serializable {

  private static final long serialVersionUID = 1L;

  @ApiModelProperty("ID")
  private BigDecimal id;

  /**
   * 年份
   */
  @ApiModelProperty("YEAR")
  @ExcelProperty(value = "年份")
  private String year;

  /**
   * 月份
   */
  @ApiModelProperty("MONTH")
  @ExcelProperty(value = "月份")
  private String month;

  /**
   * 平台
   */
  @ApiModelProperty("PLATFORM")
  @ExcelProperty(value = "平台")
  private String platform;

  /**
   * 账号
   */
  @ApiModelProperty("SHOP_NAME")
  @ExcelProperty(value = "账号")
  private String shopName;

  /**
   * 站点
   */
  @ApiModelProperty("SITE")
  @ExcelProperty(value = "站点")
  private String site;

  /**
   * 事业部
   */
  @ApiModelProperty("DEPARTMENT")
  @ExcelProperty(value = "事业部")
  private String department;

  /**
   * Team
   */
  @ApiModelProperty("TEAM")
  @ExcelProperty(value = "Team")
  private String team;

  /**
   * ASIN
   */
  @ApiModelProperty("ASIN")
  @ExcelProperty(value = "Asin")
  private String asin;

  /**
   * FNSKU
   */
  @ApiModelProperty("FNSKU")
  @ExcelProperty(value = "Fnsku")
  private String fnsku;

  /**
   * SKU
   */
  @ApiModelProperty("SKU")
  @ExcelProperty(value = "sku")
  private String sku;

  /**
   * 物料编码
   */
  @ApiModelProperty("MATERIAL_CODE")
  @ExcelProperty(value = "物料编码")
  private String materialCode;

  /**
   * 订单申请日期
   */
  @ApiModelProperty("ORDER_APPLY_DATE")
  @ExcelProperty(value = "订单申请日期")
  private Date orderApplyDate;

  /**
   * 订单ID
   */
  @ApiModelProperty("ORDER_ID")
  @ExcelProperty(value = "订单ID")
  private String orderId;

  /**
   * 订单类型
   */
  @ApiModelProperty("ORDER_TYPE")
  @ExcelProperty(value = "订单类型")
  private String orderType;

  /**
   * 订单状态
   */
  @ApiModelProperty("ORDER_STATUS")
  @ExcelProperty(value = "订单状态")
  private String orderStatus;

  /**
   * MAIN_ID
   */
  @ApiModelProperty("MAIN_ID")
  private BigDecimal mainId;

  /**
   * 库存状态
   */
  @ApiModelProperty("INVENTORY_STATUS")
  @ExcelProperty(value = "库存状态")
  private String inventoryStatus;

  /**
   * 申请数量
   */
  @ApiModelProperty("APPLY_AMOUNT")
  @ExcelProperty(value = "申请数量")
  private String applyAmount;

  /**
   * 销毁数量
   */
  @ApiModelProperty("DISPOSE_AMOUNT")
  @ExcelProperty(value = "销毁数量")
  private String disposeAmount;

  /**
   * 移除数量
   */
  @ApiModelProperty("REMOVE_AMOUNT")
  @ExcelProperty(value = "移除数量")
  private String removeAmount;

  /**
   * 审核时间
   */
  @ApiModelProperty("VERIFY_AT")
  private Date verifyAt;

  /**
   * 审核人
   */
  @ApiModelProperty("VERIFY_BY")
  private String verifyBy;

  /**
   * 审核状态
   */
  @ApiModelProperty("VERIFY_STATUS")
  private String verifyStatus;

  /**
   * 同步状态
   */
  @ApiModelProperty("GENERATE_STATUS")
  private String generateStatus;

  /**
   * 站点
   */
  @ApiModelProperty("SYS_SITE")
  private String sysSite;

  /**
   * 账号简称
   */
  @ApiModelProperty("SHOP_NAME")
  private String shopsName;

  @ApiModelProperty("ORG")
  private String org;

  @ApiModelProperty("INVENTORY_ORGANIZATION_CODE")
  private String inventoryOrganizationCode;

  @ApiModelProperty("WAREHOUSE_NAME")
  private String warehouseName;

  /**
   * 仓库编码
   */
  @ApiModelProperty("WAREHOUSE_CODE")
  private String warehouseCode;

  @ApiModelProperty("OUT_ID")
  private String outId;

  @ApiModelProperty("UPDATE_BY")
  private String updateBy;

  @ApiModelProperty("BILL_CODE")
  private String billCode;

  @ApiModelProperty("SYS_SHOPS_NAME")
  private String sysShopsName;

  @ExcelProperty(value = "审核状态")
  private String verifyStatusCN;

  @ExcelProperty(value = "生成状态")
  private String generateStatusCN;
}
