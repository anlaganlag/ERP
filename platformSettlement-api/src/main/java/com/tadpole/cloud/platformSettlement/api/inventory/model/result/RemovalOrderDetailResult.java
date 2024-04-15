package com.tadpole.cloud.platformSettlement.api.inventory.model.result;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * @author jobob
 * @since 2021-06-07
 */
@Data
@ApiModel
@ExcelIgnoreUnannotated
public class RemovalOrderDetailResult implements Serializable {

  private static final long serialVersionUID = 1L;

  @ApiModelProperty("ID")
  private Double id;

  /**
   * 请求时间
   */
  @ApiModelProperty("REQUEST_DATE")
  private Date requestDate;

  /**
   * 订单编号
   */
  @ApiModelProperty("ORDER_ID")
  private String orderId;

  /**
   * 订单类型
   */
  @ApiModelProperty("ORDER_TYPE")
  private String orderType;

  /**
   * 订单状态
   */
  @ApiModelProperty("ORDER_STATUS")
  private String orderStatus;

  /**
   * Sku
   */
  @ApiModelProperty("SKU")
  private String sku;

  /**
   * Fnsku
   */
  @ApiModelProperty("FNSKU")
  private String fnsku;

  /**
   * 状态
   */
  @ApiModelProperty("DISPOSITION")
  private String disposition;

  /**
   * 报废数量
   */
  @ApiModelProperty("DISPOSED_QUANTITY")
  private Double disposedQuantity;

  /**
   * Shipped-quantity
   */
  @ApiModelProperty("SHIPPED_QUANTITY")
  private Double shippedQuantity;

  /**
   * 移除费用
   */
  @ApiModelProperty("REMOVAL_FEE")
  private Double removalFee;

  /**
   * 币种
   */
  @ApiModelProperty("CURRENCY")
  private String currency;

  @ApiModelProperty("SYS_SITE")
  private String sysSite;

  /**
   * 账号简称
   */
  @ApiModelProperty("SYS_SHOPS_NAME")
  private String sysShopsName;

  /**
   * 平台
   */
  @ApiModelProperty("PLATFORM")
  private String platform;

  @ApiModelProperty("CREATE_TIME")
  private Date createTime;

  @ApiModelProperty("USER_NAME")
  private String userName;

  @ApiModelProperty("SYS_SELLER_ID")
  private String sysSellerId;

  /**
   * 报告文件上传日期(生成上传报告任务的日期)
   */
  @ApiModelProperty("UPLOAD_DATE")
  private Date uploadDate;

  /**
   * 申请数量
   */
  @ApiModelProperty("REQUESTED_QUANTITY")
  private Long requestedQuantity;

  @ApiModelProperty("CANCELLED_QUANTITY")
  private Long cancelledQuantity;

  @ApiModelProperty("IN_PROCESS_QUANTITY")
  private Long inProcessQuantity;

  @ApiModelProperty("year")
  private String year;

  @ApiModelProperty("month")
  private String month;

  @ApiModelProperty("SALES_ORGANIZATION")
  private String salesOrganization;

  @ApiModelProperty("SALES_ORGANIZATION_CODE")
  private String salesOrganizationCode;

  @ApiModelProperty("ORG")
  private String org;

  @ApiModelProperty("INVENTORY_ORGANIZATION_CODE")
  private String inventoryOrganizationCode;

  @ApiModelProperty("WAREHOUSE_NAME")
  private String warehouseName;

  @ApiModelProperty("WAREHOUSE_CODE")
  private String warehouseCode;

  @ApiModelProperty("OUT_ID")
  private Long outId;

  @ApiModelProperty("UPDATE_BY")
  private String updateBy;
}
