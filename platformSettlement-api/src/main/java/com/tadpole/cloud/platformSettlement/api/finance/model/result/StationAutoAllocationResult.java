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
public class StationAutoAllocationResult implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("ALLOC_ID")
    private BigDecimal allocId;

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
    @ExcelProperty(value = "核算币种")
    private String accountingCurrency;

    @ApiModelProperty("DEPARTMENT")
    @ExcelProperty(value = "事业部")
    private String department;

    @ApiModelProperty("TEAM")
    @ExcelProperty(value = "Team")
    private String team;

    /** 运营大类 */
    @ApiModelProperty("PRODUCT_TYPE")
    @ExcelProperty(value = "运营大类")
    private String productType;

    @ApiModelProperty("SALES_BRAND")
    @ExcelProperty(value = "销售品牌")
    private String salesBrand;

    @ApiModelProperty("VOLUME_NORMAL")
    private String volumeNormal;

    @ApiModelProperty("INCOME")
    @ExcelProperty(value = "收入")
    private BigDecimal income;

    @ApiModelProperty("INCOME_PROPORTION")
    @ExcelProperty(value = "收入占比")
    private BigDecimal incomeProportion;

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

//    @ApiModelProperty("DISPOSE_FEE")
//    @ExcelProperty(value = "销毁费")
//    private BigDecimal disposeFee;
//
//    /** lightning_deal */
//    @ApiModelProperty("REMOVAL_DEAL")
//    @ExcelProperty(value = "移除费")
//    private BigDecimal removalDeal;

    @ApiModelProperty("CONFIRM_BY")
    @ExcelProperty(value = "确认人员")
    private String confirmBy;

    @ApiModelProperty("CONFIRM_DATE")
    @ExcelProperty(value = "确认时间")
    private String confirmDate;

    @ApiModelProperty("CONFIRM_STATUS")
    @ExcelProperty(value = "确认状态")
    private String confirmStatus;

    /** 创建人 */
    @ApiModelProperty("CREATE_BY")
    @ExcelProperty(value = "创建人")
    private String createBy;

    /** 创建时间 */
    @ApiModelProperty("CREATE_AT")
    @ExcelProperty(value = "创建时间")
    private String createAt;

    @ApiModelProperty("ID")
    @ExcelProperty(value = "ID")
    private BigDecimal id;

    /**
     * 是否手动分摊
     */
    @ApiModelProperty("IS_MANUAL")
    private String iSManual;

}