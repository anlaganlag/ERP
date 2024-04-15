package com.tadpole.cloud.platformSettlement.api.inventory.entity;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * <p>
 * Amazon销毁移除主表
 * </p>
 *
 * @author gal
 * @since 2021-11-24
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("REMOVE_MAIN")
@ExcelIgnoreUnannotated
public class RemoveMainDTO implements Serializable {

  private static final long serialVersionUID = 1L;

  @TableId(value = "id", type = IdType.AUTO)
  private Long id;

  /**
   * 年份
   */
  @TableField("YEAR")
  @ExcelProperty(value = "年份")
  private String year;

  /**
   * 月份
   */
  @TableField("MONTH")
  @ExcelProperty(value = "月份")
  private String month;

  /**
   * 平台
   */
  @TableField("PLATFORM")
  @ExcelProperty(value = "平台")
  private String platform;

  /**
   * 账号
   */
  @TableField("SHOP_NAME")
  @ExcelProperty(value = "账号")
  private String shopName;

  /**
   * 站点
   */
  @TableField("SITE")
  @ExcelProperty(value = "站点")
  private String site;

  /**
   * 事业部
   */
  @TableField("DEPARTMENT")
  @ExcelProperty(value = "事业部")
  private String department;

  /**
   * Team
   */
  @TableField("TEAM")
  @ExcelProperty(value = "Team")
  private String team;

  /**
   * ASIN
   */
  @TableField("ASIN")
  @ExcelProperty(value = "ASIN")
  private String asin;

  /**
   * FNSKU
   */
  @TableField("FNSKU")
  @ExcelProperty(value = "FNSKU")
  private String fnsku;

  /**
   * SKU
   */
  @TableField("SKU")
  @ExcelProperty(value = "SKU")
  private String sku;

  /**
   * 物料编码
   */
  @TableField("MATERIAL_CODE")
  @ExcelProperty(value = "物料编码")
  private String materialCode;

  /**
   * 订单申请日期
   */
  @TableField("ORDER_APPLY_DATE")
  @ExcelProperty(value = "订单申请日期")
  private Date orderApplyDate;

  /**
   * 订单ID
   */
  @TableField("ORDER_ID")
  @ExcelProperty(value = "订单ID")
  private String orderId;

  /**
   * 订单类型
   */
  @TableField("ORDER_TYPE")
  @ExcelProperty(value = "订单类型")
  private String orderType;

  /**
   * 订单状态
   */
  @TableField("ORDER_STATUS")
  @ExcelProperty(value = "订单状态")
  private String orderStatus;

  /**
   * MAIN_ID
   */
  @TableField("MAIN_ID")
  @ExcelProperty(value = "MAIN_ID")
  private BigDecimal mainId;

  /**
   * 库存状态
   */
  @TableField("INVENTORY_STATUS")
  @ExcelProperty(value = "库存状态")
  private String inventoryStatus;

  /**
   * 申请数量
   */
  @TableField("APPLY_AMOUNT")
  @ExcelProperty(value = "申请数量")
  private String applyAmount;

  /**
   * 销毁数量
   */
  @TableField("DISPOSE_AMOUNT")
  @ExcelProperty(value = "销毁数量")
  private String disposeAmount;

  /**
   * 移除数量
   */
  @TableField("REMOVE_AMOUNT")
  @ExcelProperty(value = "移除数量")
  private String removeAmount;

  /**
   * 审核时间
   */
  @TableField("VERIFY_AT")
  private String verifyAt;

  /**
   * 审核人
   */
  @TableField("VERIFY_BY")
  private String verifyBy;

  /**
   * 审核状态
   */
  @TableField("VERIFY_STATUS")
  private String verifyStatus;

  @ExcelProperty(value = "审核状态")
  private String verifyStatusCN;

  /**
   * 同步时间
   */
  @TableField("SYNC_AT")
  private Date syncAt;

  /**
   * 同步人
   */
  @TableField("SYNC_BY")
  private String syncBy;

  /**
   * 同步状态
   */
  @TableField("SYNC_STATUS")
  private Integer syncStatus;

  /**
   * 生成状态
   */
  @TableField("GENERATE_STATUS")
  @ExcelProperty(value = "生成状态")
  private Integer generateStatus;

  @ExcelProperty(value = "生成状态")
  private Integer generateStatusCN;
}