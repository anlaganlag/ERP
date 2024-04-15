package com.tadpole.cloud.platformSettlement.api.finance.model.result;

import cn.stylefeng.guns.cloud.model.validator.BaseValidatingParam;
import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

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
@ApiModel
@ExcelIgnoreUnannotated
public class SettlementReportResult implements Serializable, BaseValidatingParam {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("ID")
    private BigDecimal id;

    /** 会计期间 */
    @ApiModelProperty("FISCAL_PERIOD")
    @ExcelProperty(value= "会计期间")
    private String fiscalPeriod;

    /** 报告类型 */
    @ApiModelProperty("REPORT_TYPE")
    @ExcelProperty(value= "报告类型")
    private String reportType;

    /** 收入确认类型 */
    @ApiModelProperty("INCOME_TYPE")
    @ExcelProperty(value= "收入确认类型")
    private String incomeType;

    /** 账号 */
    @ApiModelProperty("SHOP_NAME")
    @ExcelProperty(value= "账号")
    private String shopName;

    /** 站点 */
    @ApiModelProperty("SITE")
    @ExcelProperty(value= "站点")
    private String site;

    /** 原币 */
    @ApiModelProperty("ACCOUNTING_CURRENCY")
    @ExcelProperty(value= "核算币种")
    private String accountingCurrency;

    @ApiModelProperty("SKU")
    @ExcelProperty(value= "SKU")
    private String sku;

    @ApiModelProperty("DEPARTMENT")
    @ExcelProperty(value= "事业部")
    private String department;

    @ApiModelProperty("TEAM")
    @ExcelProperty(value= "Team")
    private String team;

    @ApiModelProperty("MATERIAL_CODE")
    @ExcelProperty(value= "物料编码")
    private String materialCode;

    @ApiModelProperty("CATEGORY")
    @ExcelProperty(value = "类目")
    private String category;

    @ApiModelProperty("PRODUCT_TYPE")
    @ExcelProperty(value = "运营大类")
    private String productType;

    @ApiModelProperty("PRODUCT_NAME")
    @ExcelProperty(value = "产品名称")
    private String productName;

    @ApiModelProperty("STYLE")
    @ExcelProperty(value = "款式")
    private String style;

    @ApiModelProperty("MAIN_MATERIAL")
    @ExcelProperty(value = "主材料")
    private String mainMaterial;

    @ApiModelProperty("DESIGN")
    @ExcelProperty(value = "图案")
    private String design;

    @ApiModelProperty("FIT_BRAND")
    @ExcelProperty(value = "适用品牌或对象")
    private String fitBrand;

    @ApiModelProperty("MODEL")
    @ExcelProperty(value = "型号")
    private String model;

    @ApiModelProperty("COMPANY_BRAND")
    @ExcelProperty(value = "公司品牌")
    private String companyBrand;

    @ApiModelProperty("COLOR")
    @ExcelProperty(value = "颜色")
    private String color;

    @ApiModelProperty("SIZES")
    @ExcelProperty(value = "尺码")
    private String sizes;

    @ApiModelProperty("PACKING")
    @ExcelProperty(value = "包装数量")
    private String packing;

    @ApiModelProperty("VERSION")
    @ExcelProperty(value = "版本")
    private String version;

    @ApiModelProperty("TYPE")
    @ExcelProperty(value = "适用机型")
    private String type;

    @ApiModelProperty("STYLE_SECOND_LABEL")
    @ExcelProperty(value = "款式二级标签")
    private String styleSecondLabel;

    @ApiModelProperty("SALES_BRAND")
    @ExcelProperty(value= "销售品牌")
    private String salesBrand;

    @ApiModelProperty("BUYER")
    @ExcelProperty(value = "采购员")
    private String buyer;

    @ApiModelProperty("DEVELOPER")
    @ExcelProperty(value = "开发人员")
    private String developer;

    @ApiModelProperty("ACCOUNT_DATE")
    @ExcelProperty(value = "核算日期")
    private String accountDate;

    @ApiModelProperty("COST_AUXILIARY_DESCRIPTION")
    @ExcelProperty(value= "费用类型辅助说明")
    private String costAuxiliaryDescription;

    @ApiModelProperty("VOLUME_NORMAL")
    @ExcelProperty(value= "Volume（正常发货）")
    private BigDecimal volumeNormal;

    @ApiModelProperty("VOLUME_REISSUE")
    @ExcelProperty(value= "Volume（补发货）")
    private BigDecimal volumeReissue;

    @ApiModelProperty("VOLUME_BILL_QUANTITY")
    @ExcelProperty(value= "Volume（测评数量）")
    private BigDecimal volumeBillQuantity;

    @ApiModelProperty("VOLUME_RETURN")
    @ExcelProperty(value= "Volume（退货）")
    private BigDecimal volumeReturn;

    /** sales_total */
    @ApiModelProperty("SALES_EXCLUDING_TAX")
    @ExcelProperty(value= "Sales_excluding_tax")
    private BigDecimal salesExcludingTax;

    @ApiModelProperty("TAX")
    @ExcelProperty(value= "Tax")
    private BigDecimal tax;

    /** sales_promotion */
    @ApiModelProperty("SALES_PROMOTION")
    @ExcelProperty(value= "Sales Promotion")
    private BigDecimal salesPromotion;

    /** refund_total */
    @ApiModelProperty("REFUND")
    @ExcelProperty(value= "Refund")
    private BigDecimal refund;

    @ApiModelProperty("REFUND_PROMOTION")
    @ExcelProperty(value= "Refund Promotion")
    private BigDecimal refundPromotion;

    /** giftwarp_shipping */
    @ApiModelProperty("GIFTWARP_SHIPPING")
    @ExcelProperty(value= "Giftwrap_Shipping")
    private BigDecimal giftwarpShipping;

    @ApiModelProperty("NET_SALES")
    @ExcelProperty(value= "Net Sales")
    private BigDecimal netSales;

    /** commission_total */
    @ApiModelProperty("COMMISSION")
    @ExcelProperty(value= "Commission")
    private BigDecimal commission;

    @ApiModelProperty("REFUND_COMMISSION")
    @ExcelProperty(value= "Refund Commission")
    private BigDecimal refundCommission;

    /** goodwill */
    @ApiModelProperty("GOODWILL")
    @ExcelProperty(value= "Goodwill")
    private BigDecimal goodwill;

    /** amazon_fee_total非小平台 */
    @ApiModelProperty("AMAZON_FEES")
    @ExcelProperty(value= "Amazon Fees")
    private BigDecimal amazonFees;

    /** storage_fee */
    @ApiModelProperty("STORAGE_FEE")
    @ExcelProperty(value= "Storage fee")
    private BigDecimal storageFee;

    /** reimbursement */
    @ApiModelProperty("REIMBURSEMENT")
    @ExcelProperty(value= "Reimbursement")
    private BigDecimal reimbursement;

    /** other */
    @ApiModelProperty("OTHER")
    @ExcelProperty(value= "Other")
    private BigDecimal other;

    /** withheld_tax */
    @ApiModelProperty("WITHHELD_TAX")
    @ExcelProperty(value= "Withheld_tax")
    private BigDecimal withheldTax;

    @ApiModelProperty("DISPOSE_FEE")
    @ExcelProperty(value= "销毁费")
    private BigDecimal disposeFee;

    /** lightning_deal */
    @ApiModelProperty("REMOVAL_DEAL")
    @ExcelProperty(value= "移除费")
    private BigDecimal removalDeal;

    @ApiModelProperty("COLLECTION")
    @ExcelProperty(value= "回款")
    private BigDecimal collection;

    /** advertising */
    @ApiModelProperty("ADVERTISING")
    @ExcelProperty(value= "Advertising")
    private BigDecimal advertising;

    @ApiModelProperty("KINDLE_ADVERTISING_FEE")
    @ExcelProperty(value= "Kindle广告费")
    private BigDecimal kindleAdvertisingFee;

    @ApiModelProperty("OTHER_ADVERTISEMENTS")
    @ExcelProperty(value= "其他站外广告")
    private BigDecimal otherAdvertisements;

    @ApiModelProperty("BRUSHING_VALUE")
    @ExcelProperty(value= "测评货值")
    private BigDecimal brushingValue;

    @ApiModelProperty("BRUSHING_SERVICE_CHARGE")
    @ExcelProperty(value= "测评手续费")
    private BigDecimal brushingServiceCharge;

    @ApiModelProperty("COST_ACCOUNTING_FEE")
    @ExcelProperty(value= "成本核算金额")
    private BigDecimal costAccountingFee;

    @ApiModelProperty("ADDITIONAL_COST_AMOUNT")
    @ExcelProperty(value= "附加成本金额")
    private BigDecimal additionalCostAmount;

    @ApiModelProperty("FIRST_TRIP_FEE")
    @ExcelProperty(value= "头程物流费")
    private BigDecimal firstTripFee;

    @ApiModelProperty("LOCAL_LOGISTICS_FEE")
    @ExcelProperty(value= "当地物流费")
    private BigDecimal localLogisticsFee;

    @ApiModelProperty("OVERSEAS_WAREHOUSE_FEE")
    @ExcelProperty(value= "海外仓费用")
    private BigDecimal overseasWarehouseFee;

    @ApiModelProperty("OVERSEA_TAX_FEE")
    @ExcelProperty(value= "海外税费")
    private BigDecimal overseaTaxFee;

    @ApiModelProperty("DISPOSE_PURCHASE_FEE")
    @ExcelProperty(value= "销毁成本-采购成本")
    private BigDecimal disposePurchaseFee;

    @ApiModelProperty("DISPOSE_LOGISTICS_FEE")
    @ExcelProperty(value= "销毁成本-头程物流成本")
    private BigDecimal disposeLogisticsFee;

    @ApiModelProperty("DOMESTIC_UNSALABLE_INVENTORY")
    @ExcelProperty(value= "滞销库存利息费")
    private BigDecimal domesticUnsalableInventory;

    @ApiModelProperty("MOLD_OPENING_COST")
    @ExcelProperty(value= "开模费用")
    private BigDecimal moldOpeningCost;

    @ApiModelProperty("PROFIT")
    @ExcelProperty(value= "Profit")
    private BigDecimal profit;

    @ApiModelProperty("ADVERTISING_SUBSIDY")
    @ExcelProperty(value= "广告费补贴")
    private BigDecimal advertisingSubsidy;

    @ApiModelProperty("INCENTIVE_FUND")
    @ExcelProperty(value= "鼓励金")
    private BigDecimal incentiveFund;

    @ApiModelProperty("Is_New")
    @ExcelProperty(value = "是否新品")
    private String isNew;

    @ApiModelProperty("CONFIRM_STATUS_TXT")
    @ExcelProperty(value= "确认状态")
    private String confirmStatusTxt;

    @ApiModelProperty("CONFIRM_BY")
    @ExcelProperty(value= "确认人员")
    private String confirmBy;

    @ApiModelProperty("CONFIRM_DATE")
    @ExcelProperty(value= "确认时间")
    private Date confirmDate;

    @ApiModelProperty("CONFIRM_STATUS")
    private BigDecimal confirmStatus;










}