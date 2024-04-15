package com.tadpole.cloud.platformSettlement.api.finance.model.params;

import cn.stylefeng.guns.cloud.model.validator.BaseValidatingParam;
import cn.stylefeng.guns.cloud.model.web.request.BaseRequest;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

/**
* <p>
* 结算报告
* </p>
*
* @author gal
* @since 2021-12-24
*/
@Data
@ApiModel
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SettlementReportAdjustParam extends BaseRequest implements Serializable, BaseValidatingParam {

    private static final long serialVersionUID = 1L;

    /** 会计期间 */
    @ApiModelProperty("FISCAL_PERIOD")
    private String fiscalPeriod;

    /** 账号 */
    @ApiModelProperty("SHOP_NAME")
    private String shopName;
    private List shopNames;

    /** 站点 */
    @ApiModelProperty("SITE")
    private String site;

    private List sites;


    /** 原币 */
    @ApiModelProperty("ACCOUNTING_CURRENCY")
    private String accountingCurrency;

    @ApiModelProperty("SKU")
    private String sku;

    @ApiModelProperty("MATERIAL_CODE")
    private String materialCode;

    @ApiModelProperty("SALES_BRAND")
    private String salesBrand;

    @ApiModelProperty("DEPARTMENT")
    private String department;

    @ApiModelProperty("TEAM")
    private String team;

    @ApiModelProperty("COST_AUXILIARY_DESCRIPTION")
    private String costAuxiliaryDescription;

    /** 报告类型 */
    @ApiModelProperty("REPORT_TYPE")
    private String reportType;

    /** 收入确认类型 */
    @ApiModelProperty("INCOME_TYPE")
    private String incomeType;

    @ApiModelProperty("VOLUME_NORMAL")
    private BigDecimal volumeNormal;

    @ApiModelProperty("VOLUME_REISSUE")
    private BigDecimal volumeReissue;

    @ApiModelProperty("VOLUME_BILL_QUANTITY")
    private BigDecimal volumeBillQuantity;

    @ApiModelProperty("VOLUME_RETURN")
    private BigDecimal volumeReturn;

    /** sales_total */
    @ApiModelProperty("SALES_EXCLUDING_TAX")
    private BigDecimal salesExcludingTax;

    @ApiModelProperty("TAX")
    private BigDecimal tax;

    /** sales_promotion */
    @ApiModelProperty("SALES_PROMOTION")
    private BigDecimal salesPromotion;

    /** refund_total */
    @ApiModelProperty("REFUND")
    private BigDecimal refund;

    @ApiModelProperty("REFUND_PROMOTION")
    private BigDecimal refundPromotion;

    /** giftwarp_shipping */
    @ApiModelProperty("GIFTWARP_SHIPPING")
    private BigDecimal giftwarpShipping;

    @ApiModelProperty("NET_SALES")
    private BigDecimal netSales;

    /** commission_total */
    @ApiModelProperty("COMMISSION")
    private BigDecimal commission;

    @ApiModelProperty("REFUND_COMMISSION")
    private BigDecimal refundCommission;

    /** goodwill */
    @ApiModelProperty("GOODWILL")
    private BigDecimal goodwill;

    /** amazon_fee_total非小平台 */
    @ApiModelProperty("AMAZON_FEES")
    private BigDecimal amazonFees;

    /** storage_fee */
    @ApiModelProperty("STORAGE_FEE")
    private BigDecimal storageFee;

    /** reimbursement */
    @ApiModelProperty("REIMBURSEMENT")
    private BigDecimal reimbursement;

    /** other */
    @ApiModelProperty("OTHER")
    private BigDecimal other;

    /** withheld_tax */
    @ApiModelProperty("WITHHELD_TAX")
    private BigDecimal withheldTax;

    @ApiModelProperty("DISPOSE_FEE")
    private BigDecimal disposeFee;

    /** lightning_deal */
    @ApiModelProperty("REMOVAL_DEAL")
    private BigDecimal removalDeal;

    @ApiModelProperty("COLLECTION")
    private BigDecimal collection;

    /** advertising */
    @ApiModelProperty("ADVERTISING")
    private BigDecimal advertising;

    @ApiModelProperty("KINDLE_ADVERTISING_FEE")
    private BigDecimal kindleAdvertisingFee;

    @ApiModelProperty("OTHER_ADVERTISEMENTS")
    private BigDecimal otherAdvertisements;

    @ApiModelProperty("BRUSHING_VALUE")
    private BigDecimal brushingValue;

    @ApiModelProperty("BRUSHING_SERVICE_CHARGE")
    private BigDecimal brushingServiceCharge;

    @ApiModelProperty("LOCAL_LOGISTICS_FEE")
    private BigDecimal localLogisticsFee;

    @ApiModelProperty("OVERSEAS_WAREHOUSE_FEE")
    private BigDecimal overseasWarehouseFee;

    @ApiModelProperty("DISPOSE_PURCHASE_FEE")
    private BigDecimal disposePurchaseFee;

    @ApiModelProperty("DISPOSE_LOGISTICS_FEE")
    private BigDecimal disposeLogisticsFee;

    @ApiModelProperty("DOMESTIC_UNSALABLE_INVENTORY")
    private BigDecimal domesticUnsalableInventory;

    @ApiModelProperty("COST_ACCOUNTING_FEE")
    private BigDecimal costAccountingFee;

    @ApiModelProperty("ADDITIONAL_COST_AMOUNT")
    private BigDecimal additionalCostAmount;

    @ApiModelProperty("FIRST_TRIP_FEE")
    private BigDecimal firstTripFee;

    @ApiModelProperty("OVERSEA_TAX_FEE")
    private BigDecimal overseaTaxFee;

    @ApiModelProperty("PROFIT")
    private BigDecimal profit;

    @ApiModelProperty("ADVERTISING_SUBSIDY")
    private BigDecimal advertisingSubsidy;

    @ApiModelProperty("INCENTIVE_FUND")
    private BigDecimal incentiveFund;

    @ApiModelProperty("MOLD_OPENING_COST")
    private BigDecimal moldOpeningCost;

    @ApiModelProperty("CONFIRM_DATE")
    private LocalDateTime confirmDate;

    @ApiModelProperty("CONFIRM_BY")
    private String confirmBy;

    @ApiModelProperty("CONFIRM_STATUS")
    private String confirmStatus;

    @ApiModelProperty("ID")
    private String id;

    private Boolean isDefect;

    private List skus;



    private BigDecimal directRate;

    @ApiModelProperty("")
    private Date createTime ;

    /**  */
    @ApiModelProperty("")
    private String createBy ;

    @ApiModelProperty("")
    private Date updateTime ;

    /**  */
    @ApiModelProperty("")
    private String updateBy ;




}