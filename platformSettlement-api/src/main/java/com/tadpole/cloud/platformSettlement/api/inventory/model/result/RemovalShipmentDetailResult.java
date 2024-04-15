package com.tadpole.cloud.platformSettlement.api.inventory.model.result;

import cn.stylefeng.guns.cloud.model.validator.BaseValidatingParam;
import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

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
@ExcelIgnoreUnannotated
@TableName("REMOVAL_SHIPMENT_DETAIL")
public class RemovalShipmentDetailResult implements Serializable, BaseValidatingParam {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("ORIGINAL_TASK_NAME")
    private String originalTaskName;

    @ApiModelProperty("FILE_PATH")
    private String filePath;

    @ApiModelProperty("ID")
    @ExcelProperty(value ="id")
    private BigDecimal id;

    @ApiModelProperty("UPLOAD_MARK")
    private String uploadMark;

    @ApiModelProperty("REQUEST_DATE")
    @ExcelProperty(value ="requestDate")
    private Date requestDate;

    @ApiModelProperty("ORDER_ID")
    @ExcelProperty(value ="orderId")
    private String orderId;

    @ApiModelProperty("REMOVAL_ORDER_TYPE")
    @ExcelProperty(value ="removalOrderType")
    private String removalOrderType;

    @ApiModelProperty("SHIPMENT_DATE")
    @ExcelProperty(value ="shipmentDate")
    private Date shipmentDate;

    @ApiModelProperty("SHIPPED_QUANTITY")
    @ExcelProperty(value ="shippedQuantity")
    private BigDecimal shippedQuantity;

    @ApiModelProperty("SKU")
    @ExcelProperty(value ="sku")
    private String sku;

    @ApiModelProperty("FNSKU")
    @ExcelProperty(value ="fnsku")
    private String fnsku;

    @ApiModelProperty("DISPOSITION")
    @ExcelProperty(value ="disposition")
    private String disposition;


    @ApiModelProperty("CARRIER")
    @ExcelProperty(value ="carrier")
    private String carrier;

    @ApiModelProperty("TRACKING_NUMBER")
    @ExcelProperty(value ="trackingNumber")
    private String trackingNumber;

    /** 账号简称 */
    @ApiModelProperty("SYS_SHOPS_NAME")
    @ExcelProperty(value ="sysShopsName")
    private String sysShopsName;

    @ApiModelProperty("SYS_SITE")
    @ExcelProperty(value ="sysSite")
    private String sysSite;

    @ApiModelProperty("最新站点")
    @ExcelProperty(value ="最新站点")
    private String lastSysSite;

    @ApiModelProperty("CREATE_TIME")
    @ExcelProperty(value ="createTime")
    private Date createTime;

    @ApiModelProperty("USER_NAME")
    private String userName;

    @ApiModelProperty("SYS_SELLER_ID")
    @ExcelProperty(value ="sysSellerId")
    private String sysSellerId;

    /** 报告文件上传日期(生成上传报告任务的日期) */
    @ApiModelProperty("UPLOAD_DATE")
    @ExcelProperty(value ="UPLOAD_DATE")
    private Date uploadDate;

    @ApiModelProperty("BILL_CODE")
    private String billCode;

    @ApiModelProperty("ORG")
    private String org;

    @ApiModelProperty("INVENTORY_ORGANIZATION_CODE")
    @ExcelProperty(value ="库存组织编码")
    private String inventoryOrganizationCode;

    @ApiModelProperty("WAREHOUSE_NAME")
    private String warehouseName;

    @ApiModelProperty("WAREHOUSE_CODE")
    private String warehouseCode;

    @ApiModelProperty("CREATE_AT")
    private Date createAt;

    @ApiModelProperty("UPDATE_BY")
    private String updateBy;

    @ApiModelProperty("VERIFY_AT")
    @ExcelProperty(value ="审核时间")
    private Date verifyAt;

    @ApiModelProperty("VERIFY_BY")
    @ExcelProperty(value ="审核人")
    private String verifyBy;

    @ApiModelProperty("VERIFY_STATUS")
    private BigDecimal verifyStatus;

    @ApiModelProperty("GENERATE_STATUS")
    private BigDecimal generateStatus;

    @TableField(exist = false)
    @ExcelProperty(value ="审核状态")
    private String verifyStatusTxt;

    @TableField(exist = false)
    @ExcelProperty(value ="生成状态")
    private String generateStatusTxt;

    @ApiModelProperty("UPDATE_TIME")
    private Date updateTime;

    private List sysSites;

    private List skus;

    private List sysShopsNames;

    private List verifyStatuss;

    private List generateStatuss;

    private List<String> idList;

    private String year;

    private String month;

    @ApiModelProperty("开始时间（YYYYMM）")
    private String shipmentStartDate;

    @ApiModelProperty("结束时间（YYYYMM）")
    private String shipmentEndDate;
}