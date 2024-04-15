package com.tadpole.cloud.platformSettlement.api.finance.model.result;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * <p>
 * ERP固定汇率
 * </p>
 *
 * @author gal
 * @since 2021-10-25
 */
@Data
@ApiModel
@ExcelIgnoreUnannotated
public class NoAllocationAdjustResult implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("ID")
    private BigDecimal id;

    /** 会计期间 */
    @ApiModelProperty("FISCAL_PERIOD")
    @ExcelProperty(value = "会计期间")
    private String fiscalPeriod;

    /** 报告类型 */
    @ApiModelProperty("REPORT_TYPE")
    @ExcelProperty(value = "报告类型")
    private String reportType;

    /** 收入确认类型 */
    @ApiModelProperty("INCOME_TYPE")
    @ExcelProperty(value = "收入确认类型")
    private String incomeType;

    /** 账号 */
    @ApiModelProperty("SHOP_NAME")
    @ExcelProperty(value = "账号")
    private String shopName;

    /** 站点 */
    @ApiModelProperty("SITE")
    @ExcelProperty(value = "站点")
    private String site;

    /** 原币 */
    @ApiModelProperty("ACCOUNTING_CURRENCY")
    @ExcelProperty(value = "核算币别")
    private String accountingCurrency;

    @ApiModelProperty("SKU")
    @ExcelProperty(value = "SKU")
    private String sku;

    @ApiModelProperty("DEPARTMENT")
    @ExcelProperty(value = "事业部")
    private String department;

    @ApiModelProperty("TEAM")
    @ExcelProperty(value = "Team")
    private String team;

    @ApiModelProperty("MATERIAL_CODE")
    @ExcelProperty(value = "物料编码")
    private String materialCode;

    @ApiModelProperty("PRODUCT_TYPE")
    @ExcelProperty(value = "运营大类")
    private String productType;

    @ApiModelProperty("CATEGORY")
    @ExcelProperty(value = "类目")
    private String category;

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

    @ApiModelProperty("VOLUME_NORMAL")
    @ExcelProperty(value = "Volume(正常发货)")
    private String volumeNormal;

    @ApiModelProperty("VOLUME_REISSUE")
    @ExcelProperty(value = "Volume(补发货)")
    private String volumeReissue;

    /** sales_total */
    @ApiModelProperty("SALES_EXCLUDING_TAX")
    @ExcelProperty(value = "Sales_excluding_tax")
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
    @ExcelProperty(value = "Refund")
    private BigDecimal refund;

    @ApiModelProperty("REFUND_PROMOTION")
    @ExcelProperty(value= "Refund Promotion")
    private BigDecimal refundPromotion;

    /** giftwarp_shipping */
    @ApiModelProperty("GIFTWARP_SHIPPING")
    @ExcelProperty(value= "Giftwrap_Shipping")
    private BigDecimal giftwarpShipping;

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
    @ExcelProperty(value = "销毁费")
    private BigDecimal disposeFee;

    @ApiModelProperty("REMOVAL_DEAL")
    @ExcelProperty(value = "移除费")
    private BigDecimal removalDeal;

    @ApiModelProperty("COST_ACCOUNTING_FEE")
    @ExcelProperty(value = "成本核算金额")
    private BigDecimal costAccountingFee;

    @ApiModelProperty("ADDITIONAL_COST_AMOUNT")
    @ExcelProperty(value = "附加成本金额")
    private BigDecimal additionalCostAmount;

    @ApiModelProperty("FIRST_TRIP_FEE")
    @ExcelProperty(value = "头程物流费")
    private BigDecimal firstTripFee;

    @ApiModelProperty("UNIT_PURCHASE_COST")
    @ExcelProperty(value = "单位采购成本CNY")
    private BigDecimal unitPurchaseCost;

    @ApiModelProperty("UNIT_PURCHASE_COST_ADDITONAL")
    @ExcelProperty(value = "单位采购成本附加CNY")
    private BigDecimal unitPurchaseCostAdditonal;

    @ApiModelProperty("UNIT_LOGISTICS_COST")
    @ExcelProperty(value = "单位物流成本CNY")
    private BigDecimal unitLogisticsCost;

    @ApiModelProperty("CONFIRM_STATUS_TXT")
    @ExcelProperty(value = "确认状态")
    private String confirmStatusTxt;

    @ApiModelProperty("CONFIRM_BY")
    @ExcelProperty(value = "确认人员")
    private String confirmBy;

    @ApiModelProperty("CONFIRM_DATE")
    @ExcelProperty(value = "确认时间")
    private String confirmDate;

    @ApiModelProperty("CONFIRM_STATUS")
    private BigDecimal confirmStatus;

    /** 创建人 */
    @ApiModelProperty("CREATE_BY")
    @ExcelProperty(value = "创建人")
    private String createBy;

    /** 创建时间 */
    @ApiModelProperty("CREATE_AT")
    @ExcelProperty(value = "创建时间")
    private String createAt;

}