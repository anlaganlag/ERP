package com.tadpole.cloud.platformSettlement.api.inventory.entity;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

import java.io.Serializable;

/**
 * <p>
 * erp物料分配表
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
@TableName("Z_Z_DISTRIBUTE_MCMS")
@ExcelIgnoreUnannotated

public class ErpMaterialDistribute implements Serializable {

  private static final long serialVersionUID = 1L;

  /**
   * 物料编码
   */
  @TableField("material_code")
  private String materialCode;

  /**
   * 店铺组织
   */
  @TableField("shop_code")
  private String shopCode;
}