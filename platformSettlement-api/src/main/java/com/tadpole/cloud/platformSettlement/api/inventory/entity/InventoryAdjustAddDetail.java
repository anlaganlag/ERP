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
 * AZ库存调整列表(增加)
 * </p>
 *
 * @author gal
 * @since 2021-11-22
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("INVENTORY_ADJUST_ADD_DETAIL")
@ExcelIgnoreUnannotated
public class InventoryAdjustAddDetail implements Serializable {

  private static final long serialVersionUID = 1L;

  @TableId(value = "ID", type = IdType.AUTO)
  private Long id;

  /**
   * 单据编号
   */
  @TableField("ADD_ID")
  private String addId;

  /**
   * 库存状态
   */
  @TableField("INVENTORY_STATUS")
  @ExcelProperty(value = "库存状态")
  private String inventoryStatus;

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
   * 调整增加数量
   */
  @TableField("ADJUST_NUM")
  @ExcelProperty(value = "调整增加数量")
  private BigDecimal adjustNum;

  /**
   * 是否作废
   */
  @TableField("IS_CANCEL")
  @ExcelProperty(value = "是否作废")
  private Integer isCancel;

  /**
   * 同步状态
   */
  @TableField("SYNC_STATUS")
  @ExcelProperty(value = "同步状态")
  private Integer syncStatus;

  /**
   * 最后操作时间
   */
  @TableField("UPDATE_AT")
  @ExcelProperty(value = "最后操作时间")
  private Date updateAt;

  /**
   * 最后操作人
   */
  @TableField("UPDATE_BY")
  @ExcelProperty(value = "最后操作人")
  private String updateBy;

  @TableField("OUT_ID")
  private Long outId;

  @TableField("SHOP_NAME")
  private String shopName;

  @TableField("site")
  private String site;
}