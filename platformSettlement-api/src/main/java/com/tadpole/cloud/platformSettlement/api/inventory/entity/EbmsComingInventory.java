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
 * EBMS在途库存
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
@TableName("EBMS_COMING_INVENTORY")
@ExcelIgnoreUnannotated

public class EbmsComingInventory implements Serializable {

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
   * sku
   */
  @TableField("SKU")
  @ExcelProperty(value = "sku")
  private String sku;

  /**
   * FBA号
   */
  @TableField("FBA_NUMBER")
  @ExcelProperty(value = "FBA号")
  private String fbaNumber;

  /**
   * 物料编码
   */
  @TableField("MATERIAL_CODE")
  @ExcelProperty(value = "物料编码")
  private String materialCode;

  /**
   * 统计日期
   */
  @TableField("STATICS_DATE")
  @ExcelProperty(value = "统计日期")
  private Date staticsDate;

  /**
   * 总来货数量
   */
  @TableField("TOTAL_COMING_NUMBER")
  @ExcelProperty(value = "总来货数量")
  private BigDecimal totalComingNumber;

  /**
   * 物流待发数量
   */
  @TableField("LOGISTICS_NUMBER")
  @ExcelProperty(value = "物流待发数")
  private BigDecimal logisticsNumber;

  /**
   * 海运
   */
  @TableField("SEA_NUMBER")
  @ExcelProperty(value = "海运")
  private BigDecimal seaNumber;

  /**
   * 铁运
   */
  @TableField("TRAIN_NUMBER")
  @ExcelProperty(value = "铁运")
  private BigDecimal trainNumber;

  /**
   * 卡航
   */
  @TableField("CAR_NUMBER")
  @ExcelProperty(value = "卡运")
  private BigDecimal carNumber;

  /**
   * 空运
   */
  @TableField("AIR_NUMBER")
  @ExcelProperty(value = "空运")
  private BigDecimal airNumber;

  /**
   * 抽数日期
   */
  @TableField("ETL_TIME")
  private Date etlTime;

  /**
   * 库存组织编码
   */
  @TableField("INVENTORY_ORGANIZATION_CODE")
  private String inventoryOrganizationCode;

  /**
   * 组织仓库名称：平台_账号_站点_仓库
   */
  @TableField("ORG_WAREHOUSE_NAME")
  private String orgWarehouseName;

  /**
   * 组织仓库编码
   */
  @TableField("ORG_WAREHOUSE_CODE")
  private String orgWarehouseCode;

  /**
   * 组织编码：平台_账号_站点
   */
  @TableField("ORG")
  private String org;

  /**
   * 备用字段，用于生成期末库存列表维度：平台_账号_站点_sku
   */
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