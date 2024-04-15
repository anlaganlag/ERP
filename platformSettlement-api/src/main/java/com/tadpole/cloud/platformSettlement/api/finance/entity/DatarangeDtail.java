package com.tadpole.cloud.platformSettlement.api.finance.entity;

import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import lombok.*;

/**
* <p>
* datarange源数据
* </p>
*
* @author gal
* @since 2021-10-25
*/
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("CW_DATARANGE_DTAIL")
public class DatarangeDtail implements Serializable {

    private static final long serialVersionUID = 1L;

    /** id */
    @TableId("ID")
    private BigDecimal id;

    /** SETTLEMENT_ID */
    @TableField("SETTLEMENT_ID")
    private String settlementId;

    /** DATE_TIME */
    @TableField("DATE_TIME")
    private LocalDateTime dateTime;

    /** TYPE */
    @TableField("TYPE")
    private String type;

    /** ORDER_ID */
    @TableField("ORDER_ID")
    private String orderId;

    /** SKU */
    @TableField("SKU")
    private String sku;

    /** DESCRIPTION */
    @TableField("DESCRIPTION")
    private String description;

    /** QUANTITY */
    @TableField("QUANTITY")
    private BigDecimal quantity;

    /** MARKETPLACE */
    @TableField("MARKETPLACE")
    private String marketplace;

    /** ACCOUNT_TYPE */
    @TableField("ACCOUNT_TYPE")
    private String accountType;

    /** FULFILLMENT */
    @TableField("FULFILLMENT")
    private String fulfillment;

    /** ORDER_CITY */
    @TableField("ORDER_CITY")
    private String orderCity;

    /** ORDER_STATE */
    @TableField("ORDER_STATE")
    private String orderState;

    /** ORDER_POST_CODE */
    @TableField("ORDER_POST_CODE")
    private String orderPostCode;

    /** TAX_COLLECTION_MODEL */
    @TableField("TAX_COLLECTION_MODEL")
    private String taxCollectionModel;

    /** PRODUCT_SALES */
    @TableField("PRODUCT_SALES")
    private BigDecimal productSales;

    /** PRODUCT_SALES_TAX */
    @TableField("PRODUCT_SALES_TAX")
    private BigDecimal productSalesTax;

    /** SHIPPING_CREDITS */
    @TableField("SHIPPING_CREDITS")
    private BigDecimal shippingCredits;

    /** SHIPPING_CREDITS_TAX */
    @TableField("SHIPPING_CREDITS_TAX")
    private BigDecimal shippingCreditsTax;

    /** GIFT_WRAP_CREDITS */
    @TableField("GIFT_WRAP_CREDITS")
    private BigDecimal giftWrapCredits;

    /** GIFT_WRAP_CREDITS_TAX */
    @TableField("GIFT_WRAP_CREDITS_TAX")
    private BigDecimal giftWrapCreditsTax;

    /** AMAZON_POINT_FEE */
    @TableField("AMAZON_POINT_FEE")
    private BigDecimal amazonPointFee;

    /** PROMOTIONAL_REBATE */
    @TableField("PROMOTIONAL_REBATE")
    private BigDecimal promotionalRebate;

    /** PROMOTIONAL_REBATE_TAX */
    @TableField("PROMOTIONAL_REBATE_TAX")
    private BigDecimal promotionalRebateTax;

    /** MARKETPLACE_WITHHELD_TAX */
    @TableField("MARKETPLACE_WITHHELD_TAX")
    private BigDecimal marketplaceWithheldTax;

    /** SELLING_FEES */
    @TableField("SELLING_FEES")
    private BigDecimal sellingFees;

    /** FBA_FEES */
    @TableField("FBA_FEES")
    private BigDecimal fbaFees;

    /** OTHER_TRANSACTION_FEES */
    @TableField("OTHER_TRANSACTION_FEES")
    private BigDecimal otherTransactionFees;

    /** OTHER */
    @TableField("OTHER")
    private BigDecimal other;

    /** DIFFERENCE */
    @TableField("DIFFERENCE")
    private BigDecimal difference;

    /** TOTAL */
    @TableField("TOTAL")
    private BigDecimal total;

    /** CURRENCY */
    @TableField("CURRENCY")
    private String currency;

    /** PLATFORM */
    @TableField("PLATFORM")
    private String platform;

    /** SYS_SHOPS_NAME */
    @TableField("SYS_SHOPS_NAME")
    private String sysShopsName;

    /** SYS_SITE */
    @TableField("SYS_SITE")
    private String sysSite;

    /** CREATE_TIME */
    @TableField("CREATE_TIME")
    private LocalDateTime createTime;

    /** UPLOAD_DATE */
    @TableField("UPLOAD_DATE")
    private LocalDateTime uploadDate;

}