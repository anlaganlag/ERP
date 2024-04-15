package com.tadpole.cloud.platformSettlement.api.inventory.entity;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
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
public class RemoveMain implements Serializable {

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

  @TableField("VERIFY_AT")
  @ExcelProperty(value = "审核时间")
  private String verifyAt;

  @TableField("VERIFY_BY")
  @ExcelProperty(value = "审核人")
  private String verifyBy;

  @TableField("VERIFY_STATUS")
  @ExcelProperty(value = "审核状态")
  private String verifyStatus;

  @TableField("UPDATE_BY")
  @ExcelProperty(value = "操作人")
  private String updateBy;

  @TableField("BILL_CODE")
  @ExcelProperty(value = "单据编码")
  private String billCode;

  @TableField("GENERATE_STATUS")
  @ExcelProperty(value = "生成状态")
  private Integer generateStatus;

  @TableField("OUT_ID")
  private Long outId;

  @TableField("ORIGINAL_TASK_NAME")
  @ExcelProperty(value = "源仓任务名称")
  private String originalTaskName;

  @TableField("FILE_PATH")
  @ExcelProperty(value = "文件存放路径")
  private String filePath;
}