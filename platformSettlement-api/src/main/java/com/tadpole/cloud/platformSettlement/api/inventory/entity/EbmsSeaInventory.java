package com.tadpole.cloud.platformSettlement.api.inventory.entity;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>
 * 海外库存
 * </p>
 *
 * @author gal
 * @since 2021-12-09
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("EBMS_SEA_INVENTORY")
@ExcelIgnoreUnannotated

public class EbmsSeaInventory implements Serializable {

  private static final long serialVersionUID = 1L;

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
   * 仓库名称
   */
  @TableField("WAREHOUSE_NAME")
  @ExcelProperty(value = "仓库名称")
  private String warehouseName;

  /**
   * sku
   */
  @TableField("SKU")
  @ExcelProperty(value = "sku")
  private String sku;

  /**
   * FNSKU
   */
  @TableField("FNSKU")
  @ExcelProperty(value = "Fnsku")
  private String fnsku;

  /**
   * 物料编码
   */
  @TableField("MATERIAL_CODE")
  @ExcelProperty(value = "物料编码")
  private String materialCode;

  /**
   * 账存数量
   */
  @TableField("ACCOUNT_NUMBER")
  @ExcelProperty(value = "帐存数量")
  private BigDecimal accountNumber;

  /**
   * 来货数量
   */
  @TableField("COMING_NUMBER")
  @ExcelProperty(value = "来货数量")
  private BigDecimal comingNumber;

  /**
   * 结存计算日期
   */
  @TableField("BALANCE_DATE")
  @ExcelProperty(value = "结存计算日期")
  private Date balanceDate;

  /**
   * 抽数日期
   */
  @TableField("ETL_TIME")
  private Date etlTime;

  /** 库存组织编码 */
  @TableField("INVENTORY_ORGANIZATION_CODE")
  private String inventoryOrganizationCode;

  /** 组织仓库名称：平台_账号_站点_仓库 */
  @TableField("ORG_WAREHOUSE_NAME")
  private String orgWarehouseName;

  /** 组织仓库编码 */
  @TableField("ORG_WAREHOUSE_CODE")
  private String orgWarehouseCode;

  /** 组织编码：平台_账号_站点 */
  @TableField("ORG")
  private String org;

  /** 备用字段，用于生成期末库存列表维度：平台_账号_站点_sku */
  @TableField("UNIQUE_ORG")
  private String uniqueOrg;

  /**
   * 是否使用 0：未使用 1：已使用
   */
  @TableField("IS_USE")
  private String isUse;

  /**
   * 源数据ID
   */
  @TableField("PARENT_ID")
  private BigDecimal parentId;

  @TableId(value = "ID", type = IdType.AUTO)
  private BigDecimal id;
}