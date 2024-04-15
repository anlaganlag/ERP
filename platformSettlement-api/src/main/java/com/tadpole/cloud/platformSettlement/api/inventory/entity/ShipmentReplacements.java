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
    *
    * </p>
*
* @author S20190161
* @since 2023-06-08
*/
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("SHIPMENT_REPLACEMENTS")
@ExcelIgnoreUnannotated
public class ShipmentReplacements implements Serializable {

    private static final long serialVersionUID = 1L;


    @TableId(value = "ID", type = IdType.AUTO)
    private Long id;

    @ExcelProperty(value = "会计期")
    @TableField("SHIPMENT_DATE")
    private Date shipmentDate;
    @ExcelProperty(value = "sku")
    @TableField("SKU")
    private String sku;
    @ExcelProperty(value = "asin")
    @TableField("ASIN")
    private String asin;
    @ExcelProperty(value = "换货仓库")
    @TableField("FULFILLMENT_CENTER_ID")
    private String fulfillmentCenterId;
    @ExcelProperty(value = "原始仓库")
    @TableField("ORIGINAL_FULFILLMENT_CENTER_ID")
    private String originalFulfillmentCenterId;
    @ExcelProperty(value = "换货数量")
    @TableField("QUANTITY")
    private Integer quantity;

    @TableField("REPLACEMENT_REASON_CODE")
    private Integer replacementReasonCode;

    @ExcelProperty(value = "换货原因")
    @TableField(exist = false)
    private String replacementReasonName;

    @ExcelProperty(value = "换货订单号")
    @TableField("REPLACEMENT_AMAZON_ORDER_ID")
    private String replacementAmazonOrderId;
    @ExcelProperty(value = "原始订单号")
    @TableField("ORIGINAL_AMAZON_ORDER_ID")
    private String originalAmazonOrderId;
    @ExcelProperty(value = "originalTaskName")
    @TableField("ORIGINAL_TASK_NAME")
    private String originalTaskName;
    @ExcelProperty(value = "filePath")
    @TableField("FILE_PATH")
    private String filePath;

    @TableField("SYS_SELLER_ID")
    private String sysSellerId;

    @TableField("CREATE_TIME")
    private Date createTime;
    @ExcelProperty(value = "站点")
    @TableField("SYS_SITE")
    private String sysSite;
    @ExcelProperty(value = "账号")
    @TableField("SYS_SHOPS_NAME")
    private String sysShopsName;



}

