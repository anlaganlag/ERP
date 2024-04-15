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
 * Monthly inventory history报告明细
 * </p>
 *
 * @author gal
 * @since 2021-11-24
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("MONTHLY_INVENTORY_HISTORY")
@ExcelIgnoreUnannotated
public class MonthlyInventoryHistory implements Serializable {

  private static final long serialVersionUID = 1L;

  @TableId(value = "id", type = IdType.AUTO)
  private Long id;

  @TableField("ORIGINAL_TASK_NAME")
  @ExcelProperty(value = "源仓任务名称")
  private String originalTaskName;

  @TableField("FILE_PATH")
  @ExcelProperty(value = "文件存放路径")
  private String filePath;

  @TableField("DATE_MONTHLY")
  @ExcelProperty(value = "dateMonthly")
  private String dateMonthly;

  @TableField("FNSKU")
  @ExcelProperty(value = "fnsku")
  private String fnsku;

  @TableField("SKU")
  @ExcelProperty(value = "sku")
  private String sku;

  @TableField("PRODUCT_NAME")
  @ExcelProperty(value = "productName")
  private String productName;

  @TableField("AVERAGE_QUANTITY")
  @ExcelProperty(value = "averageQuantity")
  private BigDecimal averageQuantity;

  @TableField("END_QUANTITY")
  @ExcelProperty(value = "endQuantity")
  private BigDecimal endQuantity;

  @TableField("IN_TRANSIT_BETWEEN_WAREHOUSES")
  @ExcelProperty(value = "亚马逊移库在途数量")
  private BigDecimal inTransitBetweenWarehouses;

  @TableField("FULFILLMENT_CENTER_ID")
  @ExcelProperty(value = "fulfillmentCenterId")
  private String fulfillmentCenterId;

  @TableField("DETAILED_DISPOSITION")
  @ExcelProperty(value = "detailedDisposition")
  private String detailedDisposition;

  @ExcelProperty(value = "country")
  @TableField("COUNTRY")
  private String country;

  @TableField("SYS_SHOPS_NAME")
  @ExcelProperty(value = "sysShopsName")
  private String sysShopsName;

  @TableField("SYS_SITE")
  @ExcelProperty(value = "sysSite")
  private String sysSite;

  @TableField("SYS_SELLER_ID")
  @ExcelProperty(value = "sysSellerId")
  private String sysSellerId;

  @TableField("CREATE_TIME")
  @ExcelProperty(value = "createTime")
  private Date createTime;

  @TableField("UPLOAD_DATE")
  @ExcelProperty(value = "uploadDate")
  private Date uploadDate;

  @TableField("INVENTORY_ORGANIZATION_CODE")
  @ExcelProperty(value = "库存组织编码")
  private String inventoryOrganizationCode;

  @TableField("VERIFY_AT")
  private String verifyAt;

  @TableField("VERIFY_BY")
  private String verifyBy;

  @TableField("VERIFY_STATUS")
  private String verifyStatus;

  @TableField("UPDATE_BY")
  private String updateBy;

  @TableField("BILL_CODE")
  private String billCode;

  @TableField("GENERATE_STATUS")
  private Integer generateStatus;

  @TableField("OUT_ID")
  private Long outId;

  @ExcelProperty(value = "审核状态")
  private String verifyStatusCN;

  @ExcelProperty(value = "生成状态")
  private String generateStatusCN;

  @TableField("UPLOAD_MARK")
  private String uploadMark;

  @TableField("ASIN")
  private String asin;

  @TableField("UNKNOWN_EVENTS")
  private BigDecimal unknownEvents;

  @TableField("PARENT_ID")
  private BigDecimal parentId;
}