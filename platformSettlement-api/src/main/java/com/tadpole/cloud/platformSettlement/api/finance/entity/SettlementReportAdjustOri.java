//package com.tadpole.cloud.platformSettlement.api.finance.entity;
//
//import com.alibaba.excel.annotation.ExcelProperty;
//import com.baomidou.mybatisplus.annotation.TableField;
//import com.baomidou.mybatisplus.annotation.TableName;
//import lombok.*;
//
//import java.io.Serializable;
//import java.math.BigDecimal;
//import java.util.Date;
//
///**
// * <p>
// * 结算报告
// * </p>
// *
// * @author gal
// * @since 2021-12-24
// */
//@Data
//@EqualsAndHashCode(callSuper = false)
//@Builder
//@AllArgsConstructor
//@NoArgsConstructor
//@TableName("RP_SETTLEMENT_REPORT_ADJUST")
//public class SettlementReportAdjustOri implements Serializable {
//
//    private static final long serialVersionUID = 1L;
//
//    @TableField("ID")
//    private BigDecimal id;
//
//    /** 会计期间 */
//    @TableField("FISCAL_PERIOD")
//    @ExcelProperty(value= "会计期间")
//    private String fiscalPeriod;
//
//    /** 报告类型 */
//    @TableField("REPORT_TYPE")
//    @ExcelProperty(value= "报告类型")
//    private String reportType;
//
//    /** 收入确认类型 */
//    @TableField("INCOME_TYPE")
//    @ExcelProperty(value= "收入确认类型")
//    private String incomeType;
//
//    /** 账号 */
//    @TableField("SHOP_NAME")
//    @ExcelProperty(value= "账号")
//    private String shopName;
//
//    /** 站点 */
//    @TableField("SITE")
//    @ExcelProperty(value= "站点")
//    private String site;
//
//    /** 原币 */
//    @TableField("ACCOUNTING_CURRENCY")
//    @ExcelProperty(value= "核算币种")
//    private String accountingCurrency;
//
//    @TableField("SKU")
//    @ExcelProperty(value= "SKU")
//    private String sku;
//
//    @TableField("DEPARTMENT")
//    @ExcelProperty(value= "事业部")
//    private String department;
//
//    @TableField("TEAM")
//    @ExcelProperty(value= "Team")
//    private String team;
//
//    @TableField("MATERIAL_CODE")
//    @ExcelProperty(value= "物料编码")
//    private String materialCode;
//
//    @TableField("CATEGORY")
//    @ExcelProperty(value = "类目")
//    private String category;
//
//    @TableField("PRODUCT_TYPE")
//    @ExcelProperty(value = "运营大类")
//    private String productType;
//
//    @TableField("PRODUCT_NAME")
//    @ExcelProperty(value = "产品名称")
//    private String productName;
//
//    @TableField("STYLE")
//    @ExcelProperty(value = "款式")
//    private String style;
//
//    @TableField("MAIN_MATERIAL")
//    @ExcelProperty(value = "主材料")
//    private String mainMaterial;
//
//    @TableField("DESIGN")
//    @ExcelProperty(value = "图案")
//    private String design;
//
//    @TableField("FIT_BRAND")
//    @ExcelProperty(value = "适用品牌或对象")
//    private String fitBrand;
//
//    @TableField("MODEL")
//    @ExcelProperty(value = "型号")
//    private String model;
//
//    @TableField("COMPANY_BRAND")
//    @ExcelProperty(value = "公司品牌")
//    private String companyBrand;
//
//    @TableField("COLOR")
//    @ExcelProperty(value = "颜色")
//    private String color;
//
//    @TableField("SIZES")
//    @ExcelProperty(value = "尺码")
//    private String sizes;
//
//    @TableField("PACKING")
//    @ExcelProperty(value = "包装数量")
//    private String packing;
//
//    @TableField("VERSION")
//    @ExcelProperty(value = "版本")
//    private String version;
//
//    @TableField("TYPE")
//    @ExcelProperty(value = "适用机型")
//    private String type;
//
//    @TableField("STYLE_SECOND_LABEL")
//    @ExcelProperty(value = "款式二级标签")
//    private String styleSecondLabel;
//
//    @TableField("SALES_BRAND")
//    @ExcelProperty(value= "销售品牌")
//    private String salesBrand;
//
//    @TableField("BUYER")
//    @ExcelProperty(value = "采购员")
//    private String buyer;
//
//    @TableField("DEVELOPER")
//    @ExcelProperty(value = "开发人员")
//    private String developer;
//
//    @TableField("ACCOUNT_DATE")
//    @ExcelProperty(value = "核算日期")
//    private String accountDate;
//
//    @TableField("COST_AUXILIARY_DESCRIPTION")
//    @ExcelProperty(value= "费用类型辅助说明")
//    private String costAuxiliaryDescription;
//
//    @TableField("VOLUME_NORMAL")
//    @ExcelProperty(value= "Volume（正常发货）")
//    private BigDecimal volumeNormal;
//
//    @TableField("VOLUME_REISSUE")
//    @ExcelProperty(value= "Volume（补发货）")
//    private BigDecimal volumeReissue;
//
//    @TableField("VOLUME_BILL_QUANTITY")
//    @ExcelProperty(value= "Volume（测评数量）")
//    private BigDecimal volumeBillQuantity;
//
//    @TableField("VOLUME_RETURN")
//    @ExcelProperty(value= "Volume（退货）")
//    private BigDecimal volumeReturn;
//
//    /** sales_total */
//    @TableField("SALES_EXCLUDING_TAX")
//    @ExcelProperty(value= "Sales_excluding_tax")
//    private BigDecimal salesExcludingTax;
//
//    @TableField("TAX")
//    @ExcelProperty(value= "Tax")
//    private BigDecimal tax;
//
//    /** sales_promotion */
//    @TableField("SALES_PROMOTION")
//    @ExcelProperty(value= "Sales Promotion")
//    private BigDecimal salesPromotion;
//
//    /** refund_total */
//    @TableField("REFUND")
//    @ExcelProperty(value= "Refund")
//    private BigDecimal refund;
//
//    @TableField("REFUND_PROMOTION")
//    @ExcelProperty(value= "Refund Promotion")
//    private BigDecimal refundPromotion;
//
//    /** giftwarp_shipping */
//    @TableField("GIFTWARP_SHIPPING")
//    @ExcelProperty(value= "Giftwrap_Shipping")
//    private BigDecimal giftwarpShipping;
//
//    @TableField("NET_SALES")
//    @ExcelProperty(value= "Net Sales")
//    private BigDecimal netSales;
//
//    /** commission_total */
//    @TableField("COMMISSION")
//    @ExcelProperty(value= "Commission")
//    private BigDecimal commission;
//
//    @TableField("REFUND_COMMISSION")
//    @ExcelProperty(value= "Refund Commission")
//    private BigDecimal refundCommission;
//
//    /** goodwill */
//    @TableField("GOODWILL")
//    @ExcelProperty(value= "Goodwill")
//    private BigDecimal goodwill;
//
//    /** amazon_fee_total非小平台 */
//    @TableField("AMAZON_FEES")
//    @ExcelProperty(value= "Amazon Fees")
//    private BigDecimal amazonFees;
//
//    /** storage_fee */
//    @TableField("STORAGE_FEE")
//    @ExcelProperty(value= "Storage fee")
//    private BigDecimal storageFee;
//
//    /** reimbursement */
//    @TableField("REIMBURSEMENT")
//    @ExcelProperty(value= "Reimbursement")
//    private BigDecimal reimbursement;
//
//    /** other */
//    @TableField("OTHER")
//    @ExcelProperty(value= "Other")
//    private BigDecimal other;
//
//    /** withheld_tax */
//    @TableField("WITHHELD_TAX")
//    @ExcelProperty(value= "Withheld_tax")
//    private BigDecimal withheldTax;
//
//    @TableField("DISPOSE_FEE")
//    @ExcelProperty(value= "销毁费")
//    private BigDecimal disposeFee;
//
//    /** lightning_deal */
//    @TableField("REMOVAL_DEAL")
//    @ExcelProperty(value= "移除费")
//    private BigDecimal removalDeal;
//
//    @TableField("COLLECTION")
//    @ExcelProperty(value= "回款")
//    private BigDecimal collection;
//
//    /** advertising */
//    @TableField("ADVERTISING")
//    @ExcelProperty(value= "Advertising")
//    private BigDecimal advertising;
//
//    @TableField("KINDLE_ADVERTISING_FEE")
//    @ExcelProperty(value= "Kindle广告费")
//    private BigDecimal kindleAdvertisingFee;
//
//    @TableField("OTHER_ADVERTISEMENTS")
//    @ExcelProperty(value= "其他站外广告")
//    private BigDecimal otherAdvertisements;
//
//    @TableField("BRUSHING_VALUE")
//    @ExcelProperty(value= "测评货值")
//    private BigDecimal brushingValue;
//
//    @TableField("BRUSHING_SERVICE_CHARGE")
//    @ExcelProperty(value= "测评手续费")
//    private BigDecimal brushingServiceCharge;
//
//    @TableField("COST_ACCOUNTING_FEE")
//    @ExcelProperty(value= "成本核算金额")
//    private BigDecimal costAccountingFee;
//
//    @TableField("ADDITIONAL_COST_AMOUNT")
//    @ExcelProperty(value= "附加成本金额")
//    private BigDecimal additionalCostAmount;
//
//    @TableField("FIRST_TRIP_FEE")
//    @ExcelProperty(value= "头程物流费")
//    private BigDecimal firstTripFee;
//
//    @TableField("LOCAL_LOGISTICS_FEE")
//    @ExcelProperty(value= "当地物流费")
//    private BigDecimal localLogisticsFee;
//
//    @TableField("OVERSEAS_WAREHOUSE_FEE")
//    @ExcelProperty(value= "海外仓费用")
//    private BigDecimal overseasWarehouseFee;
//
//    @TableField("OVERSEA_TAX_FEE")
//    @ExcelProperty(value= "海外税费")
//    private BigDecimal overseaTaxFee;
//
//    @TableField("DISPOSE_PURCHASE_FEE")
//    @ExcelProperty(value= "销毁成本-采购成本")
//    private BigDecimal disposePurchaseFee;
//
//    @TableField("DISPOSE_LOGISTICS_FEE")
//    @ExcelProperty(value= "销毁成本-头程物流成本")
//    private BigDecimal disposeLogisticsFee;
//
//    @TableField("DOMESTIC_UNSALABLE_INVENTORY")
//    @ExcelProperty(value= "滞销库存利息费")
//    private BigDecimal domesticUnsalableInventory;
//
//    @TableField("MOLD_OPENING_COST")
//    @ExcelProperty(value= "开模费用")
//    private BigDecimal moldOpeningCost;
//
//    @TableField("PROFIT")
//    @ExcelProperty(value= "Profit")
//    private BigDecimal profit;
//
//    @TableField("ADVERTISING_SUBSIDY")
//    @ExcelProperty(value= "广告费补贴")
//    private BigDecimal advertisingSubsidy;
//
//    @TableField("INCENTIVE_FUND")
//    @ExcelProperty(value= "鼓励金")
//    private BigDecimal incentiveFund;
//
//    @TableField("Is_New")
//    @ExcelProperty(value = "是否新品")
//    private String isNew;
//
//    @TableField("CONFIRM_STATUS_TXT")
//    @ExcelProperty(value= "确认状态")
//    private String confirmStatusTxt;
//
//    @TableField("CONFIRM_BY")
//    @ExcelProperty(value= "确认人员")
//    private String confirmBy;
//
//    @TableField("CONFIRM_DATE")
//    @ExcelProperty(value= "确认时间")
//    private Date confirmDate;
//
//    @TableField("CONFIRM_STATUS")
//    private BigDecimal confirmStatus;
//
//
//
//
//
//
//
//
//
//
//}