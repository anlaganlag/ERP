package com.tadpole.cloud.platformSettlement.api.inventory.entity;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
* <p>
* 移除货件详情报告
* </p>
*
* @author gal
* @since 2022-04-15
*/
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("REMOVAL_SHIPMENT_DETAIL")
@ExcelIgnoreUnannotated
public class RemovalShipmentDetail implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableField("ID")
    @ExcelProperty(value ="")
    private BigDecimal id;

    @TableField("REQUEST_DATE")
    @ExcelProperty(value ="")
    private Date requestDate;

    @TableField("ORDER_ID")
    @ExcelProperty(value ="")
    private String orderId;

    @TableField("SHIPMENT_DATE")
    @ExcelProperty(value ="")
    private Date shipmentDate;

    @TableField("SKU")
    @ExcelProperty(value ="")
    private String sku;

    @TableField("FNSKU")
    @ExcelProperty(value ="")
    private String fnsku;

    @TableField("DISPOSITION")
    @ExcelProperty(value ="")
    private String disposition;

    @TableField("SHIPPED_QUANTITY")
    @ExcelProperty(value ="")
    private BigDecimal shippedQuantity;

    @TableField("CARRIER")
    @ExcelProperty(value ="")
    private String carrier;

    @TableField("TRACKING_NUMBER")
    @ExcelProperty(value ="")
    private String trackingNumber;

    @TableField("REMOVAL_ORDER_TYPE")
    @ExcelProperty(value ="")
    private String removalOrderType;

    @TableField("SYS_SITE")
    @ExcelProperty(value ="")
    private String sysSite;

    /** 账号简称 */
    @TableField("SYS_SHOPS_NAME")
    @ExcelProperty(value ="账号简称")
    private String sysShopsName;

    @TableField("CREATE_TIME")
    @ExcelProperty(value ="")
    private Date createTime;

    @TableField("USER_NAME")
    @ExcelProperty(value ="")
    private String userName;

    @TableField("SYS_SELLER_ID")
    @ExcelProperty(value ="")
    private String sysSellerId;

    /** 报告文件上传日期(生成上传报告任务的日期) */
    @TableField("UPLOAD_DATE")
    @ExcelProperty(value ="报告文件上传日期(生成上传报告任务的日期)")
    private Date uploadDate;

    @TableField("VERIFY_AT")
    @ExcelProperty(value ="")
    private Date verifyAt;

    @TableField("VERIFY_BY")
    @ExcelProperty(value ="")
    private String verifyBy;

    @TableField("VERIFY_STATUS")
    @ExcelProperty(value ="")
    private BigDecimal verifyStatus;

    @TableField("CREATE_AT")
    @ExcelProperty(value ="")
    private Date createAt;

    @TableField("UPDATE_BY")
    @ExcelProperty(value ="")
    private String updateBy;

    @TableField("GENERATE_STATUS")
    @ExcelProperty(value ="")
    private BigDecimal generateStatus;

    @TableField("BILL_CODE")
    @ExcelProperty(value ="")
    private String billCode;

    @TableField("ORG")
    @ExcelProperty(value ="")
    private String org;

    @TableField("INVENTORY_ORGANIZATION_CODE")
    @ExcelProperty(value ="")
    private String inventoryOrganizationCode;

    @TableField("WAREHOUSE_NAME")
    @ExcelProperty(value ="")
    private String warehouseName;

    @TableField("WAREHOUSE_CODE")
    @ExcelProperty(value ="")
    private String warehouseCode;

    @TableField("SALES_ORGANIZATION")
    @ExcelProperty(value ="")
    private String salesOrganization;

    @TableField("SALES_ORGANIZATION_CODE")
    @ExcelProperty(value ="")
    private String salesOrganizationCode;

    @TableField("UPDATE_TIME")
    @ExcelProperty(value ="")
    private Date updateTime;

    @TableField("UPLOAD_MARK")
    @ExcelProperty(value ="")
    private String uploadMark;

    @TableField("ORIGINAL_TASK_NAME")
    @ExcelProperty(value ="")
    private String originalTaskName;

    @TableField("FILE_PATH")
    @ExcelProperty(value ="")
    private String filePath;

    /**
     * 生成海外仓入库数据状态 0：未生成，1：已生成
     */
    @TableField("GENERATE_HWC")
    private String generateHwc;

    @TableField("LAST_SYS_SITE")
    @ExcelProperty(value ="")
    private String lastSysSite;
}