package com.tadpole.cloud.platformSettlement.api.finance.entity;

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
* settlement明细数据
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
@TableName("CW_SETTLEMENT_DETAIL")
public class SettlementDetail implements Serializable {

    private static final long serialVersionUID = 1L;

    /** id */
    @TableId(value="ID",type= IdType.AUTO)
    private BigDecimal id;

    /** 结算单号 */
    @TableField("SETTLEMENT_ID")
    private String settlementId;

    /** 交易类型 */
    @TableField("TRANSACTION_TYPE")
    private String transactionType;

    /** 费用类型 */
    @TableField("AMOUNT_TYPE")
    private String amountType;

    /** 费用描述 */
    @TableField("AMOUNT_DESCRIPTION")
    private String amountDescription;

    /** 订单编号 */
    @TableField("ORDER_ID")
    private String orderId;

    /** 订单商品编号 */
    @TableField("ORDER_ITEM_CODE")
    private String orderItemCode;

    /** 卖家订单商品编号 */
    @TableField("MERCHANT_ORDER_ITEM_ID")
    private String merchantOrderItemId;

    /** 卖家盘点商品编号 */
    @TableField("MERCHANT_ADJUSTMENT_ITEM_ID")
    private String merchantAdjustmentItemId;

    /** SKU */
    @TableField("SKU")
    private String sku;

    /** 销量 */
    @TableField("QUANTITY_PURCHASED")
    private BigDecimal quantityPurchased;

    /** 卖家订单编号 */
    @TableField("MERCHANT_ORDER_ID")
    private String merchantOrderId;

    /** 盘点编号 */
    @TableField("ADJUSTMENT_ID")
    private String adjustmentId;

    /** 货件编号 */
    @TableField("SHIPMENT_ID")
    private String shipmentId;

    /** 商城名称 */
    @TableField("MARKETPLACE_NAME")
    private String marketplaceName;

    /** 配送编号 */
    @TableField("FULFILLMENT_ID")
    private String fulfillmentId;

    /** 邮寄日期 */
    @TableField("POSTED_DATE")
    private Date postedDate;

    /** 邮寄时间 */
    @TableField("POSTED_DATE_TIME")
    private Date postedDateTime;

    /** 商品销售平台 */
    @TableField("PLATFORM")
    private String platform;

    /** 订单类型 */
    @TableField("TYPE")
    private String type;

    /** 费用金额 */
    @TableField("AMOUNT")
    private BigDecimal amount;

    /** 财务分类 */
    @TableField("FINANCE_TYPE")
    private String financeType;

    /** 站点 */
    @TableField("SYS_SITE")
    private String sysSite;

    /** 账号简称 */
    @TableField("SYS_SHOPS_NAME")
    private String sysShopsName;

    /** 创建时间 */
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


    /** Refund_Promotion */
    @TableField("REFUND_PROMOTION")
    private BigDecimal refundPromotion;

    /** Advertising */
    @TableField("ADVERTISING")
    private BigDecimal advertising;

    /** advertisingRefund */
    @TableField("ADVERTISING_REFUND")
    private BigDecimal advertisingRefund;

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

    /** OTHER_FBA_ITF */
    @TableField("OTHER_FBA_ITF")
    private BigDecimal otherFbaItf;

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

    /** 信用卡充值金额 */
    @TableField("SUCCESSFUL_CHARGE")
    private BigDecimal successfulCharge;

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

    /** PAYABLE_TO_AMAZON */
    @TableField("PAYABLE_TO_AMAZON")
    private BigDecimal payableToAmazon;

}