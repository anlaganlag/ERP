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
* 站内费用手动分摊
* </p>
*
* @author gal
* @since 2021-12-24
*/
@Data
@ApiModel
@ExcelIgnoreUnannotated
public class ManualAllocationAdjustResult implements Serializable, BaseValidatingParam {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("ID")
    @ExcelProperty(value= "被分摊id")
    private BigDecimal id;

    @ApiModelProperty("ALLOC_ID")
    @ExcelProperty(value= "父级id")
    private BigDecimal allocId;

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

    @ApiModelProperty("DEPARTMENT")
    @ExcelProperty(value= "事业部")
    private String department;

    @ApiModelProperty("TEAM")
    @ExcelProperty(value= "Team")
    private String team;

    @ApiModelProperty("PRODUCT_TYPE")
    @ExcelProperty(value= "运营大类")
    private String productType;

    @ApiModelProperty("VOLUME_NORMAL")
    @ExcelProperty(value= "Volume(正常发货)")
    private String volumeNormal;

    @ApiModelProperty("VOLUME_REISSUE")
    @ExcelProperty(value= "Volume(补发货)")
    private String volumeReissue;

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

    /** advertising */
    @ApiModelProperty("ADVERTISING")
    @ExcelProperty(value= "Advertising")
    private BigDecimal advertising;

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

    /** 创建人 */
    @ApiModelProperty("CREATE_BY")
    @ExcelProperty(value= "创建人")
    private String createBy;

    /** 创建时间 */
    @ApiModelProperty("CREATE_AT")
    @ExcelProperty(value= "创建时间")
    private Date createAt;


}