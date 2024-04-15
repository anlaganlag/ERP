package com.tadpole.cloud.platformSettlement.api.finance.model.params;

import cn.stylefeng.guns.cloud.model.validator.BaseValidatingParam;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author: ty
 * @description: 领星Settlement请求参数
 * @date: 2022/5/6
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel
public class LxAmazonSettlementDetailParam implements Serializable, BaseValidatingParam {

    private static final long serialVersionUID = 1L;

    /** 结算ID */
    @ApiModelProperty("SETTLEMENT_ID")
    private BigDecimal settlementId;

    @ApiModelProperty("TRANSACTION_TYPE")
    private String transactionType;

    @ApiModelProperty("ORDER_ID")
    private String orderId;

    @ApiModelProperty("MERCHANT_ORDER_ID")
    private String merchantOrderId;

    @ApiModelProperty("ADJUSTMENT_ID")
    private String adjustmentId;

    @ApiModelProperty("SHIPMENT_ID")
    private String shipmentId;

    @ApiModelProperty("MARKETPLACE_NAME")
    private String marketplaceName;

    @ApiModelProperty("AMOUNT_TYPE")
    private String amountType;

    @ApiModelProperty("AMOUNT_DESCRIPTION")
    private String amountDescription;

    @ApiModelProperty("AMOUNT")
    private BigDecimal amount;

    @ApiModelProperty("FULFILLMENT_ID")
    private String fulfillmentId;

    @ApiModelProperty("POSTED_DATE")
    private Date postedDate;

    @ApiModelProperty("POSTED_DATE_TIME")
    private Date postedDateTime;

    @ApiModelProperty("ORDER_ITEM_CODE")
    private String orderItemCode;

    @ApiModelProperty("MERCHANT_ORDER_ITEM_ID")
    private String merchantOrderItemId;

    @ApiModelProperty("MERCHANT_ADJUSTMENT_ITEM_ID")
    private String merchantAdjustmentItemId;

    @ApiModelProperty("SKU")
    private String sku;

    @ApiModelProperty("QUANTITY_PURCHASED")
    private Long quantityPurchased;

    @ApiModelProperty("CREATE_TIME")
    private Date createTime;

    @ApiModelProperty("UPDATE_TIME")
    private Date updateTime;

    @ApiModelProperty("ID")
    private BigDecimal id;

}