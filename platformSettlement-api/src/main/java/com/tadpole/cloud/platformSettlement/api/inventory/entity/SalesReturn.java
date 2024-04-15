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
 * 销售退货列表
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
@TableName("SALES_RETURN")
@ExcelIgnoreUnannotated
public class SalesReturn implements Serializable {

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


  @TableField("ORG")
  @ExcelProperty(value = "站点")
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
  @ExcelProperty(value = "仓库编码")
  private String warehouseCode;

  /**
   * 销售组织
   */
  @TableField("SALES_ORGANIZATION")
  @ExcelProperty(value = "销售组织")
  private String salesOrganization;

  /**
   * 销售组织编码
   */
  @TableField("SALES_ORGANIZATION_CODE")
  @ExcelProperty(value = "销售组织编码")
  private String salesOrganizationCode;

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

  private Integer canSync;
}