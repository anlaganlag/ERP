package com.tadpole.cloud.platformSettlement.api.inventory.entity;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 期末库存列表
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
@TableName("ENDING_INVENTORY")
@ExcelIgnoreUnannotated
public class EndingInventory implements Serializable {

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

  @TableField("ORG")
  private String org;
  /**
   * 库存组织编码
   */
  @TableField("INVENTORY_ORGANIZATION_CODE")
  @ExcelProperty(value = "库存组织编码")
  private String inventoryOrganizationCode;

  /**
   * WAREHOUSE_NAME
   */
  @TableField("WAREHOUSE_NAME")
  @ExcelProperty(value = "仓库名称")
  private String warehouseName;

  /**
   * 仓库编码
   */
  @TableField("WAREHOUSE_CODE")
  @ExcelProperty(value = "仓库组织编码")
  private String warehouseCode;

  /**
   * 仓库编码
   */
  @TableField("INVENTORY_STATUS")
  @ExcelProperty(value = "库存状态")
  private String inventoryStatus;

  /**
   * 仓库编码
   */
  @TableField("DEPARTMENT")
  @ExcelProperty(value = "事业部")
  private String department;
  /**
   * 仓库编码
   */
  @TableField("TEAM")
  @ExcelProperty(value = "Team")
  private String team;

  /**
   * 仓库编码
   */
  @TableField("SKU")
  @ExcelProperty(value = "sku")
  private String sku;

  /**
   * 仓库编码
   */
  @TableField("MATERIAL_CODE")
  @ExcelProperty(value = "物料编码")
  private String materialCode;

  /**
   * 在途数
   */
  @TableField(value = "TRANSIT_NUM")
  @ExcelProperty(value = "在途数量")
  private String transitNum;

  /**
   * 物流代发数
   */
  @TableField(value = "LOGISTICS_NUM")
  @ExcelProperty(value = "物流待发数")
  private String logisticsNum;

  /**
   * 在库数
   */
  @TableField(value = "STOCK_NUM")
  @ExcelProperty(value = "在库数")
  private String stockNum;

  /**
   * 期末库存数量
   */
  @TableField(value = "ENDING_NUM")
  @ExcelProperty(value = "期末库存数量")
  private String endingNum;

  @TableField(exist = false)
  @ExcelProperty(value = "是否作废")
  private  Integer isCancel;

  @TableField(exist = false)
  @ExcelProperty(value = "同步状态")
  private Integer syncStatus;

  /**
   * 更新时间
   */
  @TableField(value = "UPDATE_TIME")
  private Date updateTime;

  /**
   * 更新人
   */
  @TableField(value = "UPDATE_USER")
  private String updateUser;

  /**
   * 创建时间
   */
  @TableField(value = "CREATE_TIME")
  private Date createTime;

  /**
   * 创建人
   */
  @TableField(value = "CREATE_USER")
  private String createUser;

  @TableField("OUT_ID")
  private Long outId;

  private Integer canSync;

  /**
   * 数据月份
   */
  @TableField(exist = false)
  private String dateMonthly;
}