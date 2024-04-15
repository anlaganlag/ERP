package com.tadpole.cloud.platformSettlement.api.inventory.entity;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ContentFontStyle;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 亚马逊物流买家退货
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
@TableName("FBA_CUSTOMER_RETURNS")
@ExcelIgnoreUnannotated

public class FbaCustomerReturns implements Serializable {

  private static final long serialVersionUID = 1L;

  @TableId(value = "id", type = IdType.AUTO)
  private Long id;

  @TableField("ORIGINAL_TASK_NAME")
  @ExcelProperty(value = "源仓任务名称")
  private String originalTaskName;

  @TableField("FILE_PATH")
  @ExcelProperty(value = "文件存放路径")
  private String filePath;

  /**
   * 退货日期
   */
  @TableField("RETURN_DATE")
  @ExcelProperty(value = "returnDate")
  private Date returnDate;

  /**
   * 订单编号
   */
  @TableField("ORDER_ID")
  @ExcelProperty(value = "amazonOrderId")
  private String orderId;

  /**
   * SKU
   */
  @TableField("SKU")
  @ExcelProperty(value = "sku")
  private String sku;

  /**
   * ASIN
   */
  @TableField("ASIN")
  @ExcelProperty(value = "asin")
  private String asin;

  /**
   * FNSKU
   */
  @TableField("FNSKU")
  @ExcelProperty(value = "fnsku")
  private String fnsku;

  /**
   * 标题
   */
  @TableField("PRODUCT_NAME")
  @ExcelProperty(value = "productName")
  private String productName;

  /**
   * 数量
   */
  @TableField("QUANTITY")
  @ExcelProperty(value = "quantity")
  private Long quantity;

  /**
   * FC
   */
  @TableField("FULFILLMENT_CENTER_ID")
  @ExcelProperty(value = "fulfillmentCenterId")
  private String fulfillmentCenterId;

  /**
   * 库存属性
   */
  @TableField("DETAILED_DISPOSITION")
  @ExcelProperty(value = "detailedDisposition")
  private String detailedDisposition;

  /**
   * 原因
   */
  @TableField("REASON")
  @ExcelProperty(value = "reason")
  private String reason;

  /**
   * 状态
   */
  @TableField("STATUS")
  @ExcelProperty(value = "status")
  private String status;

  /**
   * LPN
   */
  @TableField("LICENSE_PLATE_NUMBER")
  @ExcelProperty(value = "licensePlateNumber")
  private String licensePlateNumber;

  /**
   * 客户评价
   */
  @TableField("CUSTOMER_COMMENTS")
  @ExcelProperty(value = "customerComments")
  private String customerComments;

  @TableField("SYS_SELLER_ID")
  @ExcelProperty(value = "sysSellerId")
  private String sysSellerId;

  /**
   * 账号简称
   */
  @TableField("SYS_SHOPS_NAME")
  @ExcelProperty(value = "sysShopsName")
  private String sysShopsName;

  /**
   * 站点
   */
  @TableField("SYS_SITE")
  @ExcelProperty(value = "sysSite")
  private String sysSite;

  @TableField("CREATE_TIME")
  @ExcelProperty(value = "createTime")
  private Date createTime;

  @TableField("USER_NAME")
  @ExcelProperty(value = "userName")
  private String userName;

  /**
   * 报告文件上传日期(生成上传报告任务的日期)
   */
  @TableField("UPLOAD_DATE")
  @ExcelProperty(value = "uploadDate")
  private Date uploadDate;

  @TableField("SALES_PLATFORM")
  @ExcelProperty(value = "platform")
  private String platform;

  @TableField(exist = false)
  @ExcelProperty(value = "审核状态")
  private String verifyStatusCN;

  @TableField(exist = false)
  @ExcelProperty(value = "生成状态")
  private String generateStatusCN;

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
  private String generateStatus;

  @TableField("UPLOAD_MARK")
  private String uploadMark;

  @TableField("SALES_ORGANIZATION")
  @ExcelProperty(value = "销售组织")
  private String salesOrganization;

  @TableField("SALES_ORGANIZATION_CODE")
  @ExcelProperty(value = "销售组织编码")
  private String salesOrganizationCode;

  @TableField(exist = false)
  @ContentFontStyle(color = 10, fontName = "宋体", fontHeightInPoints = 12)
  @ExcelProperty(value = "导入错误备注")
  private String uploadRemark;

  @TableField(exist = false)
  @ExcelProperty(value = "库存组织编码")
  private String inventoryOrganizationCode;
}