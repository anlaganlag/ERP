package com.tadpole.cloud.platformSettlement.api.inventory.entity;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * <p>
 * Amazon销毁移除子表
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
@TableName("REMOVE_MAIN_DETIAL")
@ExcelIgnoreUnannotated
public class RemoveMainDetial implements Serializable {

  private static final long serialVersionUID = 1L;

  /**
   * id
   */
  @TableId(value = "id", type = IdType.AUTO)
  private Long id;

  /**
   * MAIN_ID
   */
  @TableField("MAIN_ID")
  @ExcelProperty(value = "MAIN_ID")
  private BigDecimal mainId;

  /**
   * 单据编码
   */
  @TableField("BILL_CODE")
  @ExcelProperty(value = "单据编码")
  private String billCode;

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
  @ExcelProperty(value = "审核时间")
  private String verifyAt;

  /**
   * 审核人
   */
  @TableField("VERIFY_BY")
  @ExcelProperty(value = "审核人")
  private String verifyBy;

  /**
   * 审核状态
   */
  @TableField("VERIFY_STATUS")
  @ExcelProperty(value = "审核状态")
  private String verifyStatus;

  @TableField("UPDATE_TIME")
  @ExcelProperty(value = "更新时间")
  private String updateTime;

  @TableField("IS_CANCEL")
  @ExcelProperty(value = "是否作废")
  private String isCancel;
}