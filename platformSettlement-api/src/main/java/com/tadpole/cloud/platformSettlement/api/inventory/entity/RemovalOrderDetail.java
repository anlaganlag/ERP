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
 * 移除订单详情报告
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
@TableName("REMOVAL_ORDER_DETAIL")
@ExcelIgnoreUnannotated
public class RemovalOrderDetail implements Serializable {

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
   * 请求时间
   */
  @TableField("REQUEST_DATE")
  @ExcelProperty(value = "requestDate")
  private Date requestDate;

  /**
   * 订单编号
   */
  @TableField("ORDER_ID")
  @ExcelProperty(value = "orderId")
  private String orderId;

  /**
   * 订单类型
   */
  @TableField("ORDER_TYPE")
  @ExcelProperty(value = "orderType")
  private String orderType;

  /**
   * 订单状态
   */
  @TableField("ORDER_STATUS")
  @ExcelProperty(value = "orderStatus")
  private String orderStatus;

  /**
   * Sku
   */
  @TableField("SKU")
  @ExcelProperty(value = "sku")
  private String sku;

  /**
   * Fnsku
   */
  @TableField("FNSKU")
  @ExcelProperty(value = "fnsku")
  private String fnsku;

  /**
   * 状态
   */
  @TableField("DISPOSITION")
  @ExcelProperty(value = "disposition")
  private String disposition;

  /**
   * 报废数量
   */
  @TableField("DISPOSED_QUANTITY")
  @ExcelProperty(value = "disposedQuantity")
  private BigDecimal disposedQuantity;

  /**
   * Shipped-quantity
   */
  @TableField("SHIPPED_QUANTITY")
  @ExcelProperty(value = "shippedQuantity")
  private BigDecimal shippedQuantity;

  /**
   * 移除费用
   */
  @TableField("REMOVAL_FEE")
  @ExcelProperty(value = "removalFee")
  private BigDecimal removalFee;

  /**
   * 币种
   */
  @TableField("CURRENCY")
  @ExcelProperty(value = "currency")
  private String currency;

  /**
   * 账号简称
   */
  @TableField("SYS_SHOPS_NAME")
  @ExcelProperty(value = "sysShopsName")
  private String sysShopsName;

  @TableField("SYS_SITE")
  @ExcelProperty(value = "sysSite")
  private String sysSite;

  @TableField("CREATE_TIME")
  @ExcelProperty(value = "createTime")
  private Date createTime;

  @TableField("USER_NAME")
  @ExcelProperty(value = "userName")
  private String userName;

  @TableField("SYS_SELLER_ID")
  @ExcelProperty(value = "sysSellerId")
  private String sysSellerId;

  /**
   * 报告文件上传日期(生成上传报告任务的日期)
   */
  @TableField("UPLOAD_DATE")
  @ExcelProperty(value = "uploadDate")
  private Date uploadDate;

  /**
   * 申请数量
   */
  @TableField("REQUESTED_QUANTITY")
  @ExcelProperty(value = "requestedQuantity")
  private Long requestedQuantity;

  @TableField("CANCELLED_QUANTITY")
  @ExcelProperty(value = "cancelledQuantity")
  private Long cancelledQuantity;

  @TableField("IN_PROCESS_QUANTITY")
  @ExcelProperty(value = "inProcessQuantity")
  private Long inProcessQuantity;

  @TableField("UPLOAD_MARK")
  private String uploadMark;

  /**
   * 生成销毁移除跟踪表状态 0：未生成，1：已生成
   */
  @TableField("GENERATE_XHYCGZB")
  private String generateXhycgzb;

  /**
   * 请求时间UTC
   */
  @TableField("REQUEST_DATE_UTC")
  private Date requestDateUtc;

  /**
   * 最后更新时间UTC
   */
  @TableField("LAST_UPDATED_DATE_UTC")
  private Date lastUpdatedDateUtc;

  /**
   * 最后更新时间
   */
  @TableField("LAST_UPDATED_DATE")
  private Date lastUpdatedDate;
}