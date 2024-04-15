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
 * 库存调整报告
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
@TableName("FBA_INVENTORY_ADJUSTMENTS")
@ExcelIgnoreUnannotated
public class FbaInventoryAdjustments implements Serializable {

  private static final long serialVersionUID = 1L;

  @TableId(value = "id", type = IdType.AUTO)
  private Long id;

  @TableField("ORIGINAL_TASK_NAME")
  @ExcelProperty(value = "源仓任务名称")
  private String originalTaskName;

  @TableField("FILE_PATH")
  @ExcelProperty(value = "文件存放路径")
  private String filePath;

  @TableField("ADJUSTED_DATE")
  @ExcelProperty(value = "adjustedDate")
  private Date adjustedDate;

  @TableField("TRANSACTION_ITEM_ID")
  @ExcelProperty(value = "transactionItemId")
  private String transactionItemId;

  @TableField("FNSKU")
  @ExcelProperty(value = "fnsku")
  private String fnsku;

  @TableField("SKU")
  @ExcelProperty(value = "sku")
  private String sku;

  @TableField("FULFILLMENT_CENTER_ID")
  @ExcelProperty(value = "fulfillmentCenterId")
  private String fulfillmentCenterId;

  @TableField("QUANTITY")
  @ExcelProperty(value = "quantity")
  private Long quantity;

  @TableField("REASON")
  @ExcelProperty(value = "reason")
  private String reason;

  @TableField("DISPOSITION")
  @ExcelProperty(value = "disposition")
  private String disposition;

  @TableField("RECONCILED")
  @ExcelProperty(value = "reconciled")
  private Long reconciled;

  @TableField("UNRECONCILED")
  @ExcelProperty(value = "unreconciled")
  private Long unreconciled;

  @TableField("SYS_SHOPS_NAME")
  @ExcelProperty(value = "sysShopsName")
  private String sysShopsName;

  @TableField("SYS_SITE")
  @ExcelProperty(value = "sysSite")
  private String sysSite;

  @TableField("CREATE_TIME")
  @ExcelProperty(value = "createTime")
  private Date createTime;

  @TableField("UPLOAD_DATE")
  @ExcelProperty(value = "uploadDate")
  private Date uploadDate;

  @TableField("ASIN")
  @ExcelProperty(value = "asin")
  private String asin;

  @TableField("SYS_SELLER_ID")
  @ExcelProperty(value = "sysSellerId")
  private String sysSellerId;

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
}