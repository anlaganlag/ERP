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
 * 销毁移除列表
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
@TableName("DISPOSE_REMOVE")
@ExcelIgnoreUnannotated
public class DisposeRemoveDTO implements Serializable {

  private static final long serialVersionUID = 1L;

  @TableId(value = "id", type = IdType.AUTO)
  private Long id;

  /**
   * 单据编号
   */
  @TableField("BILL_CODE")
  @ExcelProperty(value = "单据编号")
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

  /**
   * 库存组织编码
   */
  @TableField("INVENTORY_ORGANIZATION_CODE")
  @ExcelProperty(value = "库存组织编码")
  private String inventoryOrganizationCode;

  /**
   * 仓库名称
   */
  @TableField("WAREHOUSE_NAME")
  @ExcelProperty(value = "仓库名称")
  private String warehouseName;

  /**
   * 仓库编码
   */
  @TableField("WAREHOUSE_CODE")
  @ExcelProperty(value = "仓库编码")
  private String warehouseCode;

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
   * 是否作废
   */
  @TableField("IS_CANCEL")
  @ExcelProperty(value = "是否作废")
  private String isCancel;

  /**
   * 同步状态
   */
  @TableField("SYNC_STATUS")
  @ExcelProperty(value = "同步状态")
  private String syncStatus;

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

  @TableField("ORG")
  @ExcelProperty(value = "组织")
  private String org;

  @TableField("OUT_ID")
  private Long outId;
}