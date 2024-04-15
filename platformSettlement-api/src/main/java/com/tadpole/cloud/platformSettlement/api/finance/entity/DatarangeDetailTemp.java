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
* datarange明细数据中间表
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
@TableName("CW_DATARANGE_DETAIL_TEMP")
public class DatarangeDetailTemp implements Serializable {

    private static final long serialVersionUID = 1L;

    /** id */
    @TableId("ID")
    private BigDecimal id;

    /** SETTLEMENT_ID */
    @TableField("SETTLEMENT_ID")
    private String settlementId;

    /** TRANSACTION_TYPE */
    @TableField("TRANSACTION_TYPE")
    private String transactionType;

    /** AMOUNT_DESCRIPTION */
    @TableField("AMOUNT_DESCRIPTION")
    private String amountDescription;

    /** AMOUNT_TYPE */
    @TableField("AMOUNT_TYPE")
    private String amountType;

    /** POSTED_DATE */
    @TableField("POSTED_DATE")
    private LocalDateTime postedDate;

    /** DEPOSIT_DATE */
    @TableField("DEPOSIT_DATE")
    private LocalDateTime depositDate;

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

    /** CURRENCY_CODE */
    @TableField("CURRENCY_CODE")
    private String currencyCode;

    /** QUANTITY_PURCHASED */
    @TableField("QUANTITY_PURCHASED")
    private BigDecimal quantityPurchased;

    /** AMT_SOURCE */
    @TableField("AMT_SOURCE")
    private BigDecimal amtSource;

    /** TOTAL_AMOUNT */
    @TableField("TOTAL_AMOUNT")
    private String totalAmount;

    /** CREATE_TIME */
    @TableField("CREATE_TIME")
    private LocalDateTime createTime;

}