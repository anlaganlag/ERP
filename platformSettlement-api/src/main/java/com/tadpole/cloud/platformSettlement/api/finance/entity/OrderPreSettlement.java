package com.tadpole.cloud.platformSettlement.api.finance.entity;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
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
*
* </p>
*
* @author cyt
* @since 2022-06-08
*/
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("CW_ORDER_PRE_SETTLEMENT")
@ExcelIgnoreUnannotated
public class OrderPreSettlement implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "ID", type = IdType.AUTO)
    private BigDecimal id;

    @TableField("PURCHASE_DATE")
    private String purchaseDate;

    @TableField("AMAZON_ORDER_ID")
    private String amazonOrderId;

    @TableField("SYS_SHOPS_NAME")
    private String sysShopsName;

    @TableField("SYS_SITE")
    private String sysSite;

    @TableField("SKU")
    private String sku;

    @TableField("MATERIAL_CODE")
    private String materialCode;

    @TableField("SALES_BRAND")
    private String salesBrand;

    @TableField("DEPARTMENT")
    private String department;

    @TableField("TEAM")
    private String team;

    @TableField("CURRENCY")
    private String currency;

    @TableField("VOLUME_NORMAL")
    private BigDecimal volumeNormal;

    @TableField("VOLUME_REISSUE")
    private BigDecimal volumeReissue;

    @TableField("VOLUME_BILL_QUANTITY")
    private BigDecimal volumeBillQuantity;

    @TableField("VOLUME_RETURN")
    private BigDecimal volumeReturn;

    @TableField("SALES_EXCLUDING_TAX")
    private BigDecimal salesExcludingTax;

    @TableField("TAX")
    private BigDecimal tax;

    @TableField("SALES_PROMOTION")
    private BigDecimal salesPromotion;

    @TableField("REFUND")
    private BigDecimal refund;

    @TableField("REFUND_PROMOTION")
    private BigDecimal refundPromotion;

    @TableField("GIFTWARP_SHIPPING")
    private BigDecimal giftwarpShipping;

    @TableField("NET_SALES")
    private BigDecimal netSales;

    @TableField("COMMISSION")
    private BigDecimal commission;

    @TableField("REFUND_COMMISSION")
    private BigDecimal refundCommission;

    @TableField("GOODWILL")
    private BigDecimal goodwill;

    @TableField("AMAZON_FEES")
    private BigDecimal amazonFees;

    @TableField("STORAGE_FEE")
    private BigDecimal storageFee;

    @TableField("REIMBURSEMENT")
    private BigDecimal reimbursement;

    @TableField("OTHER")
    private BigDecimal other;

    @TableField("WITHHELD_TAX")
    private BigDecimal withheldTax;

    @TableField("DISPOSE_FEE")
    private BigDecimal disposeFee;

    @TableField("REMOVAL_DEAL")
    private BigDecimal removalDeal;

    @TableField("COLLECTION")
    private BigDecimal collection;

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

    @TableField("COST_ACCOUNTING_FEE")
    private BigDecimal costAccountingFee;

    @TableField("ADDITIONAL_COST_AMOUNT")
    private BigDecimal additionalCostAmount;

    @TableField("FIRST_TRIP_FEE")
    private BigDecimal firstTripFee;

    @TableField("LOCAL_LOGISTICS_FEE")
    private BigDecimal localLogisticsFee;

    @TableField("OVERSEAS_WAREHOUSE_FEE")
    private BigDecimal overseasWarehouseFee;

    @TableField("OVERSEA_TAX_FEE")
    private BigDecimal overseaTaxFee;

    @TableField("DISPOSE_PURCHASE_FEE")
    private BigDecimal disposePurchaseFee;

    @TableField("DISPOSE_LOGISTICS_FEE")
    private BigDecimal disposeLogisticsFee;

    @TableField("DOMESTIC_UNSALABLE_INVENTORY")
    private BigDecimal domesticUnsalableInventory;

    @TableField("PRE_PROFIT")
    private BigDecimal preProfit;

    @TableField("ADVERTISING_SUBSIDY")
    private BigDecimal advertisingSubsidy;

    @TableField("INCENTIVE_FUND")
    private BigDecimal incentiveFund;

    @TableField("PRE_PROFIT_RATE")
    private BigDecimal preProfitRate;

    @TableField("IS_ERROR")
    private String isError;

    @TableField("CREATE_TIME")
    private Date createTime;

    @TableField("CREATE_BY")
    private String createBy;

    @TableField("UPDATE_TIME")
    private Date updateTime;

    @TableField("UPDATE_BY")
    private String updateBy;

}