package com.tadpole.cloud.platformSettlement.api.finance.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
* <p>
* 结算报告
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
@TableName("RP_SETTLEMENT_REPORT_FINAL")
public class SettlementReportFinalPre1219 implements Serializable {

    private static final long serialVersionUID = 1L;

    /** 会计期间 */
    @TableField("FISCAL_PERIOD")
    private String fiscalPeriod;

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

    @TableField("MATERIAL_CODE")
    private String materialCode;

    @TableField("PRODUCT_TYPE")
    private String productType;

    @TableField("SALES_BRAND")
    private String salesBrand;

    @TableField("DEPARTMENT")
    private String department;

    @TableField("TEAM")
    private String team;

    @TableField("COST_AUXILIARY_DESCRIPTION")
    private String costAuxiliaryDescription;

    /** 报告类型 */
    @TableField("REPORT_TYPE")
    private String reportType;

    /** 收入确认类型 */
    @TableField("INCOME_TYPE")
    private String incomeType;

    @TableField("VOLUME_NORMAL")
    private BigDecimal volumeNormal;

    @TableField("VOLUME_REISSUE")
    private BigDecimal volumeReissue;

    @TableField("VOLUME_BILL_QUANTITY")
    private BigDecimal volumeBillQuantity;

    @TableField("VOLUME_RETURN")
    private BigDecimal volumeReturn;

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

    @TableField("NET_SALES")
    private BigDecimal netSales;

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

    /** storage_fee */
    @TableField("STORAGE_FEE")
    private BigDecimal storageFee;

    /** reimbursement */
    @TableField("REIMBURSEMENT")
    private BigDecimal reimbursement;

    /** other */
    @TableField("OTHER")
    private BigDecimal other;

    /** withheld_tax */
    @TableField("WITHHELD_TAX")
    private BigDecimal withheldTax;

    @TableField("DISPOSE_FEE")
    private BigDecimal disposeFee;

    /** lightning_deal */
    @TableField("REMOVAL_DEAL")
    private BigDecimal removalDeal;

    @TableField("COLLECTION")
    private BigDecimal collection;

    /** advertising */
    @TableField("ADVERTISING")
    private BigDecimal advertising;

    @TableField("KINDLE_ADVERTISING_FEE")
    private BigDecimal kindleAdvertisingFee;

    @TableField("OTHER_ADVERTISEMENTS")
    private BigDecimal otherAdvertisements;

    @TableField("BRUSHING_VALUE")
    private BigDecimal brushingValue;

    @TableField("BRUSHING_SERVICE_CHARGE")
    private BigDecimal brushingServiceCharge;

    @TableField("LOCAL_LOGISTICS_FEE")
    private BigDecimal localLogisticsFee;

    @TableField("OVERSEAS_WAREHOUSE_FEE")
    private BigDecimal overseasWarehouseFee;

    @TableField("DISPOSE_PURCHASE_FEE")
    private BigDecimal disposePurchaseFee;

    @TableField("DISPOSE_LOGISTICS_FEE")
    private BigDecimal disposeLogisticsFee;

    @TableField("DOMESTIC_UNSALABLE_INVENTORY")
    private BigDecimal domesticUnsalableInventory;

    @TableField("COST_ACCOUNTING_FEE")
    private BigDecimal costAccountingFee;

    @TableField("ADDITIONAL_COST_AMOUNT")
    private BigDecimal additionalCostAmount;

    @TableField("FIRST_TRIP_FEE")
    private BigDecimal firstTripFee;

    @TableField("OVERSEA_TAX_FEE")
    private BigDecimal overseaTaxFee;

    @TableField("PROFIT")
    private BigDecimal profit;

    @TableField("ADVERTISING_SUBSIDY")
    private BigDecimal advertisingSubsidy;

    @TableField("INCENTIVE_FUND")
    private BigDecimal incentiveFund;

    @TableField("MOLD_OPENING_COST")
    private BigDecimal moldOpeningCost;

    @TableField("CONFIRM_DATE")
    private Date confirmDate;

    @TableField("CONFIRM_BY")
    private String confirmBy;

    @TableField("CONFIRM_STATUS")
    private BigDecimal confirmStatus;

    @TableField("ID")
    private BigDecimal id;

}