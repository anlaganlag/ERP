package com.tadpole.cloud.platformSettlement.api.finance.model.result;

import cn.stylefeng.guns.cloud.model.validator.BaseValidatingParam;
import com.alibaba.excel.annotation.ExcelProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>
 * 领星Settlement响应数据
 * </p>
 *
 * @author gal
 * @since 2022-05-06
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel
public class LxAmazonSettlementDetailResult implements Serializable, BaseValidatingParam {

    private static final long serialVersionUID = 1L;

    /** 结算ID */
    @ApiModelProperty("SETTLEMENT_ID")
    @ExcelProperty(value ="结算ID")
    private BigDecimal settlementId;

    @ApiModelProperty("TRANSACTION_TYPE")
    @ExcelProperty(value ="")
    private String transactionType;

    @ApiModelProperty("ORDER_ID")
    @ExcelProperty(value ="")
    private String orderId;

    @ApiModelProperty("MERCHANT_ORDER_ID")
    @ExcelProperty(value ="")
    private String merchantOrderId;

    @ApiModelProperty("ADJUSTMENT_ID")
    @ExcelProperty(value ="")
    private String adjustmentId;

    @ApiModelProperty("SHIPMENT_ID")
    @ExcelProperty(value ="")
    private String shipmentId;

    @ApiModelProperty("MARKETPLACE_NAME")
    @ExcelProperty(value ="")
    private String marketplaceName;

    @ApiModelProperty("AMOUNT_TYPE")
    @ExcelProperty(value ="")
    private String amountType;

    @ApiModelProperty("AMOUNT_DESCRIPTION")
    @ExcelProperty(value ="")
    private String amountDescription;

    @ApiModelProperty("AMOUNT")
    @ExcelProperty(value ="")
    private BigDecimal amount;

    @ApiModelProperty("FULFILLMENT_ID")
    @ExcelProperty(value ="")
    private String fulfillmentId;

    @ApiModelProperty("POSTED_DATE")
    @ExcelProperty(value ="")
    private Date postedDate;

    @ApiModelProperty("POSTED_DATE_TIME")
    @ExcelProperty(value ="")
    private Date postedDateTime;

    @ApiModelProperty("ORDER_ITEM_CODE")
    @ExcelProperty(value ="")
    private String orderItemCode;

    @ApiModelProperty("MERCHANT_ORDER_ITEM_ID")
    @ExcelProperty(value ="")
    private String merchantOrderItemId;

    @ApiModelProperty("MERCHANT_ADJUSTMENT_ITEM_ID")
    @ExcelProperty(value ="")
    private String merchantAdjustmentItemId;

    @ApiModelProperty("SKU")
    @ExcelProperty(value ="")
    private String sku;

    @ApiModelProperty("SHOP_NAME")
    private String shopName;

    @ApiModelProperty("SITE")
    private String site;

    @ApiModelProperty("QUANTITY_PURCHASED")
    @ExcelProperty(value ="")
    private Long quantityPurchased;

    @ApiModelProperty("CREATE_TIME")
    @ExcelProperty(value ="")
    private Date createTime;

    @ApiModelProperty("UPDATE_TIME")
    @ExcelProperty(value ="")
    private Date updateTime;

    @ApiModelProperty("ID")
    @ExcelProperty(value ="")
    private BigDecimal id;

}