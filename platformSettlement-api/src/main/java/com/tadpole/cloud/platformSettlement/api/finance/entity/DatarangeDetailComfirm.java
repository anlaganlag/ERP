package com.tadpole.cloud.platformSettlement.api.finance.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
* <p>
* datarange明细刷完财务分类后的
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
@TableName("CW_DATARANGE_DETAIL_COMFIRM")
public class DatarangeDetailComfirm implements Serializable {

    private static final long serialVersionUID = 1L;

    /** id */
    @TableId("ID")
    private BigDecimal id;

    /** SETTLEMENT_ID */
    @TableField("SETTLEMENT_ID")
    private String settlementId;

    /** POSTED_DATE */
    @TableField("POSTED_DATE")
    private Date postedDate;

    /** DEPOSIT_DATE */
    @TableField("DEPOSIT_DATE")
    private String depositDate;

    /** ORDER_ID */
    @TableField("ORDER_ID")
    private String orderId;

    /** SKU */
    @TableField("SKU")
    private String sku;

    /** MARKETPLACE_NAME */
    @TableField("MARKETPLACE_NAME")
    private String marketplaceName;

    /** PLATFORM */
    @TableField("PLATFORM")
    private String platform;

    /** SYS_SITE */
    @TableField("SYS_SITE")
    private String sysSite;

    /** SYS_SHOPS_NAME */
    @TableField("SYS_SHOPS_NAME")
    private String sysShopsName;

    /** TRANSACTION_TYPE */
    @TableField("TRANSACTION_TYPE")
    private String transactionType;

    /** AMOUNT_DESCRIPTION */
    @TableField("AMOUNT_DESCRIPTION")
    private String amountDescription;

    /** CURRENCY_CODE */
    @TableField("CURRENCY_CODE")
    private String currencyCode;

    /** QUANTITY_PURCHASED */
    @TableField("QUANTITY_PURCHASED")
    private String quantityPurchased;

    /** AMOUNT_TYPE */
    @TableField("AMOUNT_TYPE")
    private String amountType;

    /** AMT_SOURCE */
    @TableField("AMT_SOURCE")
    private BigDecimal amtSource;

    /** 信用卡充值金额 */
    @TableField("SUCCESSFUL_CHARGE")
    private BigDecimal successfulCharge;

    /** TOTAL_AMOUNT */
    @TableField("TOTAL_AMOUNT")
    private String totalAmount;

    /** CREATE_TIME */
    @TableField("CREATE_TIME")
    private Date createTime;

    /** Sales_Promotion */
    @TableField("SALES_PROMOTION")
    private BigDecimal salesPromotion;

    /** Commission */
    @TableField("COMMISSION")
    private BigDecimal commission;

    /** Previous_Reserve_Amount */
    @TableField("PREVIOUS_RESERVE_AMOUNT")
    private BigDecimal previousReserveAmount;

    /** Refund_Commission */
    @TableField("REFUND_COMMISSION")
    private BigDecimal refundCommission;

    /** Disposal_Fee */
    @TableField("DISPOSAL_FEE")
    private BigDecimal disposalFee;

    /** Long_term_storage_fee */
    @TableField("LONG_TERM_STORAGE_FEE")
    private BigDecimal longTermStorageFee;

    /** Good_will */
    @TableField("GOOD_WILL")
    private BigDecimal goodWill;

    /** Money_Transfer */
    @TableField("MONEY_TRANSFER")
    private BigDecimal moneyTransfer;

    /** Refund_Promotion */
    @TableField("REFUND_PROMOTION")
    private BigDecimal refundPromotion;

    /** Advertising */
    @TableField("ADVERTISING")
    private BigDecimal advertising;

    /** Amazon_Fee */
    @TableField("AMAZON_FEE")
    private BigDecimal amazonFee;

    /** Sales_Tax */
    @TableField("SALES_TAX")
    private BigDecimal salesTax;

    /** Sales_other */
    @TableField("SALES_OTHER")
    private BigDecimal salesOther;

    /** Reimbursement */
    @TableField("REIMBURSEMENT")
    private BigDecimal reimbursement;

    /** Other */
    @TableField("OTHER")
    private BigDecimal other;

    /** Giftwarp_Shipping */
    @TableField("GIFTWARP_SHIPPING")
    private BigDecimal giftwarpShipping;

    /** Current_Reserve_Amount */
    @TableField("CURRENT_RESERVE_AMOUNT")
    private BigDecimal currentReserveAmount;

    /** Storage_Fee */
    @TableField("STORAGE_FEE")
    private BigDecimal storageFee;

    /** Refund */
    @TableField("REFUND")
    private BigDecimal refund;

    /** Withheld_tax */
    @TableField("WITHHELD_TAX")
    private BigDecimal withheldTax;

    /** Unsuccessful_Transfer */
    @TableField("UNSUCCESSFUL_TRANSFER")
    private BigDecimal unsuccessfulTransfer;

    /** Removal_Fee */
    @TableField("REMOVAL_FEE")
    private BigDecimal removalFee;

    /** Sales_Principal */
    @TableField("SALES_PRINCIPAL")
    private BigDecimal salesPrincipal;

    /** LIGHTNING_DEAL_FEE */
    @TableField("LIGHTNING_DEAL_FEE")
    private BigDecimal lightningDealFee;

    /** BATCHNO */
    @TableField("BATCHNO")
    private BigDecimal batchno;

    /** 分组总数量 */
    @TableField("GROUP_TOTAL_QUANTITY")
    private BigDecimal groupTotalQuantity;

    /** 源数据父类ID */
    @TableField("PARENT_ID")
    private BigDecimal parentId;
}