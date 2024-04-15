package com.tadpole.cloud.platformSettlement.api.inventory.entity;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * <p>
 * 销售出库列表
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
@TableName("SALES_STOCK_OUT")
@ExcelIgnoreUnannotated
public class SalesStockOut implements Serializable {

  private static final long serialVersionUID = 1L;

  @TableId(value = "id", type = IdType.AUTO)
  private Long id;

  /**
   * 单据编号
   */
  @TableField("BILL_CODE")
  private String billCode;

  /**
   * 年份
   */
  @TableField("YEAR")
  private String year;

  /**
   * 月份
   */
  @TableField("MONTH")
  private String month;

  /**
   * 平台
   */
  @TableField("PLATFORM")
  private String platform;

  /**
   * 账号
   */
  @TableField("SHOP_NAME")
  private String shopName;

  /**
   * 站点
   */
  @TableField("SITE")
  private String site;

  @TableField("ORG")
  private String org;
  /**
   * 库存组织编码
   */
  @TableField("INVENTORY_ORGANIZATION_CODE")
  private String inventoryOrganizationCode;

  /**
   * WAREHOUSE_NAME
   */
  @TableField("WAREHOUSE_NAME")
  private String warehouseName;

  /**
   * 仓库编码
   */
  @TableField("WAREHOUSE_CODE")
  private String warehouseCode;

  /**
   * 销售组织
   */
  @TableField("SALES_ORGANIZATION")
  private String salesOrganization;

  /**
   * 销售组织编码
   */
  @TableField("SALES_ORGANIZATION_CODE")
  private String salesOrganizationCode;

  private Integer canSync;
}