package com.tadpole.cloud.platformSettlement.api.finance.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;

/**
* <p>
* 无需分摊调整表
* </p>
*
* @author gal
* @since 2021-12-24
*/
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("RP_NO_ALLOCATION_ADJUST")
public class NoAllocationAdjust implements Serializable {

    private static final long serialVersionUID = 1L;

    /** 会计期间 */
    @TableField("FISCAL_PERIOD")
    private String fiscalPeriod;

    /** 报告类型 */
    @TableField("REPORT_TYPE")
    private String reportType;

    /** 收入确认类型 */
    @TableField("INCOME_TYPE")
    private String incomeType;

    /** 账号 */
    @TableField("SHOP_NAME")
    private String shopName;

    /** 站点 */
    @TableField("SITE")
    private String site;

    /** 原币 */
    @TableField("ACCOUNTING_CURRENCY")
    private String accountingCurrency;

    @TableField("SKU")
    private String sku;

    @TableField("DEPARTMENT")
    private String department;

    @TableField("TEAM")
    private String team;

    @TableField("SALES_BRAND")
    private String salesBrand;

    @TableField("MATERIAL_CODE")
    private String materialCode;

    @TableField("VOLUME_NORMAL")
    private BigDecimal volumeNormal;

    @TableField("VOLUME_REISSUE")
    private BigDecimal volumeReissue;

    /** sales_total */
    @TableField("SALES_EXCLUDING_TAX")
    private BigDecimal salesExcludingTax;

    @TableField("TAX")
    private BigDecimal tax;

    /** sales_promotion */
    @TableField("SALES_PROMOTION")
    private BigDecimal salesPromotion;

    /** refund_total */
    @TableField("REFUND")
    private BigDecimal refund;

    @TableField("REFUND_PROMOTION")
    private BigDecimal refundPromotion;

    /** giftwarp_shipping */
    @TableField("GIFTWARP_SHIPPING")
    private BigDecimal giftwarpShipping;

    /** commission_total */
    @TableField("COMMISSION")
    private BigDecimal commission;

    @TableField("REFUND_COMMISSION")
    private BigDecimal refundCommission;

    /** goodwill */
    @TableField("GOODWILL")
    private BigDecimal goodwill;

    /** amazon_fee_total非小平台 */
    @TableField("AMAZON_FEES")
    private BigDecimal amazonFees;

    /** reimbursement */
    @TableField("REIMBURSEMENT")
    private BigDecimal reimbursement;

    /** other */
    @TableField("OTHER")
    private BigDecimal other;

    /** withheld_tax */
    @TableField("WITHHELD_TAX")
    private BigDecimal withheldTax;

    /** lightning_deal */
    @TableField("REMOVAL_DEAL")
    private BigDecimal removalDeal;

    @TableField("DISPOSE_FEE")
    private BigDecimal disposeFee;

    @TableField("COST_ACCOUNTING_FEE")
    private BigDecimal costAccountingFee;

    @TableField("ADDITIONAL_COST_AMOUNT")
    private BigDecimal additionalCostAmount;

    @TableField("FIRST_TRIP_FEE")
    private BigDecimal firstTripFee;

    @TableField("UNIT_PURCHASE_COST")
    private BigDecimal unitPurchaseCost;

    @TableField("UNIT_PURCHASE_COST_ADDITONAL")
    private BigDecimal unitPurchaseCostAdditonal;

    @TableField("UNIT_LOGISTICS_COST")
    private BigDecimal unitLogisticsCost;

    @TableField("CONFIRM_DATE")
    private Date confirmDate;

    @TableField("CONFIRM_BY")
    private String confirmBy;

    @TableField("CONFIRM_STATUS")
    private BigDecimal confirmStatus;

    /** 创建人 */
    @TableField("CREATE_BY")
    private String createBy;

    /** 创建时间 */
    @TableField("CREATE_AT")
    private LocalDateTime createAt;

    @TableId(value="ID",type = IdType.AUTO)
    private BigDecimal id;

}