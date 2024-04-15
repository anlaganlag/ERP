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
* 收入记录表
* </p>
*
* @author gal
* @since 2021-10-25
*/
@Data
@ApiModel
@ExcelIgnoreUnannotated
public class StatementIncomeResult implements Serializable, BaseValidatingParam {

    private static final long serialVersionUID = 1L;

    /** id */
    @ApiModelProperty("ID")
    private BigDecimal id;

    /** 平台 */
    @ApiModelProperty("PLATFORM_NAME")
    @ExcelProperty(value = "平台")
    private String platformName;

    /** 账号 */
    @ApiModelProperty("SHOP_NAME")
    @ExcelProperty(value = "账号")
    private String shopName;

    /** 站点 */
    @ApiModelProperty("SITE")
    @ExcelProperty(value = "站点")
    private String site;

    /** 结算id */
    @ApiModelProperty("SETTLEMENT_ID")
    @ExcelProperty(value = "结算ID")
    private String settlementId;

    /** 结算id序号 */
    @ApiModelProperty("SETTLEMENT_ORDER")
    @ExcelProperty(value = "结算ID序号")
    private String settlementOrder;

    /** 报告类型 */
    @ApiModelProperty("REPORT_TYPE")
    @ExcelProperty(value = "报告类型")
    private String reportType;

    /** 单据类型 */
    @ApiModelProperty("BILL_TYPE")
    @ExcelProperty(value = "单据类型")
    private String billType;

    /** 收入确认类型 */
    @ApiModelProperty("INCOME_TYPE")
    @ExcelProperty(value = "收入确认类型")
    private String incomeType;

    /** 开始日期 */
    @ApiModelProperty("START_TIME")
    @ExcelProperty(value = "开始日期")
    private Date startTime;

    /** 结束日期 */
    @ApiModelProperty("END_TIME")
    @ExcelProperty(value = "结束日期")
    private Date endTime;

    /** 会计期间 */
    @ApiModelProperty("FISCAL_PERIOD")
    @ExcelProperty(value = "会计期间")
    private String fiscalPeriod;

    /** 原币 */
    @ApiModelProperty("ORIGINAL_CURRENCY")
    @ExcelProperty(value = "原币")
    private String originalCurrency;

    /** sales_total */
    @ApiModelProperty("SALES_TOTAL")
    @ExcelProperty(value = "Sales-Total")
    private BigDecimal salesTotal;

    /** sales_promotion */
    @ApiModelProperty("SALES_PROMOTION")
    @ExcelProperty(value = "Sales-Promotion")
    private BigDecimal salesPromotion;

    /** refund_total */
    @ApiModelProperty("REFUND_TOTAL")
    @ExcelProperty(value = "Refund-Total")
    private BigDecimal refundTotal;

    /** giftwarp_shipping */
    @ApiModelProperty("GIFTWARP_SHIPPING")
    @ExcelProperty(value = "Giftwarp_Shipping")
    private BigDecimal giftwarpShipping;

    /** advertising */
    @ApiModelProperty("ADVERTISING")
    @ExcelProperty(value = "Advertising")
    private BigDecimal advertising;

    /** advertisingRefund */
    @ApiModelProperty("ADVERTISING_REFUND")
    @ExcelProperty(value = "advertisingRefund")
    private BigDecimal advertisingRefund;

    /** lightning_deal */
    @ApiModelProperty("LIGHTNING_DEAL")
    @ExcelProperty(value = "Lightning deal")
    private BigDecimal lightningDeal;

    /** commission_total */
    @ApiModelProperty("COMMISSION_TOTAL")
    @ExcelProperty(value = "Commission-Total")
    private BigDecimal commissionTotal;

     /** amazon_fee_total非小平台 */
     @ApiModelProperty("AMAZON_FEE_TOTAL")
     @ExcelProperty(value = "Amazon Fee-Total非小平台")
     private BigDecimal amazonFeeTotal;

    /** amazon_fee_total小平台 */
    @ApiModelProperty("AMAZON_FEE_TOTAL_XPT")
    @ExcelProperty(value = "Amazon Fee-Total小平台")
    private BigDecimal amazonFeeTotalXpt;

    /** storage_fee */
    @ApiModelProperty("STORAGE_FEE")
    @ExcelProperty(value = "Storage Fee")
    private BigDecimal storageFee;

    /** reimbursement */
    @ApiModelProperty("REIMBURSEMENT")
    @ExcelProperty(value = "Reimbursement")
    private BigDecimal reimbursement;

    /** other */
    @ApiModelProperty("OTHER")
    @ExcelProperty(value = "Other")
    private BigDecimal other;

    /** otherFbaItf */
    @ApiModelProperty("Other-FBA ITF")
    @ExcelProperty(value = "Other-FBA ITF")
    private BigDecimal otherFbaItf;

    /** goodwill */
    @ApiModelProperty("GOODWILL")
    @ExcelProperty(value = "Goodwill")
    private BigDecimal goodwill;

    /** withheld_tax */
    @ApiModelProperty("WITHHELD_TAX")
    @ExcelProperty(value = "withheldTax")
    private BigDecimal withheldTax;

   /** 本期应收金额(财务分类合计) */
   @ApiModelProperty("CURRENT_RECEIVABLE_AMOUNT")
   @ExcelProperty(value = "本期应收金额(财务分类合计)")
   private BigDecimal currentReceivableAmount;

    /** 本期应收金额（实际） */
    @ApiModelProperty("REAL_RECEIVABLE_AMOUNT")
    @ExcelProperty(value = "本期应收金额(实际)")
    private BigDecimal realReceivableAmount;

    /** 信用卡充值金额 */
    @ApiModelProperty("SUCCESSFUL_CHARGE")
    @ExcelProperty(value = "信用卡充值金额")
    private BigDecimal successfulCharge;

    /** 信用卡账号 */
    @ApiModelProperty("CHARGE_CARD")
    @ExcelProperty(value = "信用卡账号")
    private String chargeCard;

    /** 信用卡币别 */
    @ApiModelProperty("CARD_CURRENCY")
    @ExcelProperty(value = "信用卡币别")
    private String cardCurrency;

    /** 信用卡实际扣费金额 */
    @ApiModelProperty("CARD_DEDUCTION_AMOUNT")
    @ExcelProperty(value = "信用卡实际扣费金额")
    private BigDecimal cardDeductionAmount;

    /** 同步状态 */
    @ApiModelProperty("SYNC_STATUS")
    @ExcelProperty(value = "同步状态")
    private String syncStatus;

    /** 凭证号 */
    @ApiModelProperty("VOUCHER_NO")
    @ExcelProperty(value = "凭证号")
    private String voucherNo;

    /** 创建时间 */
    @ApiModelProperty("CREATE_AT")
    private Date createAt;

    /** 修改时间 */
    @ApiModelProperty("UPDATE_AT")
    private Date updateAt;

    /** 创建人 */
    @ApiModelProperty("CREATE_BY")
    private String createBy;

    /** 修改人 */
    @ApiModelProperty("UPDATE_BY")
    private String updateBy;

    /** 刷财务分类状态 */
    @ApiModelProperty("REFRESH_STATUS")
    private String refreshStatus;

}