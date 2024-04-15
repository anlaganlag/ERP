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
 * @since 2021-12-13
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("DISPOSE_REMOVE_DETAIL")
public class DisposeRemoveDetail implements Serializable {

  private static final long serialVersionUID = 1L;

  @TableField("OUT_ID")
  private Long outId;

  /**
   * 库存状态
   */
  @TableField("INVENTORY_STATUS")
  private String inventoryStatus;

  /**
   * 事业部
   */
  @TableField("DEPARTMENT")
  private String department;

  /**
   * Team
   */
  @TableField("TEAM")
  private String team;

  /**
   * SKU
   */
  @TableField("SKU")
  private String sku;

  /**
   * 物料编码
   */
  @TableField("MATERIAL_CODE")
  private String materialCode;

  /**
   * 销毁数量
   */
  @TableField("DISPOSE_AMOUNT")
  private String disposeAmount;

  /**
   * 移除数量
   */
  @TableField("REMOVE_AMOUNT")
  private int removeAmount;

  /**
   * 是否作废
   */
  @TableField("IS_CANCEL")
  private int isCancel;

  /**
   * 同步状态
   */
  @TableField("SYNC_STATUS")
  private String syncStatus;

  /**
   * 最后操作人
   */
  @TableField("UPDATE_BY")
  private String updateBy;

  /**
   * 更新时间
   */
  @TableField("UPDATE_AT")
  private LocalDateTime updateAt;

  /**
   * 平台_店铺_站点
   */
  @TableId(value = "ID", type = IdType.AUTO)
  private Long id;

  @TableField("SHOP_NAME")
  private String shopName;

  @TableField("site")
  private String site;
}