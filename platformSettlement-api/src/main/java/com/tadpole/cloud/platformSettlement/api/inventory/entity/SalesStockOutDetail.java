package com.tadpole.cloud.platformSettlement.api.inventory.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * <p>
 *
 * </p>
 *
 * @author gal
 * @since 2021-12-08
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("SALES_STOCK_OUT_DETAIL")
public class SalesStockOutDetail implements Serializable {

  private static final long serialVersionUID = 1L;

  @TableField("INVENTORY_STATUS")
  private String inventoryStatus;

  @TableField("DEPARTMENT")
  private String department;

  @TableField("SKU")
  private String sku;

  @TableField("MATERIAL_CODE")
  private String materialCode;

  @TableField("SALES_STOCKOUT_AMOUNT")
  private String salesStockoutAmount;

  @TableField("IS_CANCEL")
  private Integer isCancel;

  @TableField("SYNC_STATUS")
  private Integer syncStatus;

  @TableField("UPDATE_AT")
  private LocalDateTime updateAt;

  @TableField("UPDATE_BY")
  private String updateBy;

  @TableField("TEAM")
  private String team;

  @TableId(value = "id", type = IdType.AUTO)
  private Long id;

  @TableField("OUT_ID")
  private Long outId;

  @TableField("SHOP_NAME")
  private String shopName;

  @TableField("site")
  private String site;
}