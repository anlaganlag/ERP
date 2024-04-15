package com.tadpole.cloud.platformSettlement.api.finance.model.params;

import cn.stylefeng.guns.cloud.model.validator.BaseValidatingParam;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

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
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StatementIncomeParam implements Serializable, BaseValidatingParam {

    private static final long serialVersionUID = 1L;

    /** id */
    @ApiModelProperty("ID")
    private BigDecimal id;

    /** 平台 */
    @ApiModelProperty("PLATFORM_NAME")
    private String platformName;

    /** 账号 */
    @ApiModelProperty("SHOP_NAME")
    private String shopName;

    /** 站点 */
    @ApiModelProperty("SITE")
    private String site;

    /** 结算id */
    @ApiModelProperty("SETTLEMENT_ID")
    private String settlementId;

    /** 结算id序号 */
    @ApiModelProperty("SETTLEMENT_ORDER")
    private String settlementOrder;

    /** 报告类型 */
    @ApiModelProperty("REPORT_TYPE")
    private String reportType;

    /** 单据类型 */
    @ApiModelProperty("BILL_TYPE")
    private String billType;

    /** 收入确认类型 */
    @ApiModelProperty("INCOME_TYPE")
    private String incomeType;

    /** 开始日期 */
    @ApiModelProperty("START_TIME")
    private String startTime;

    /** 结束日期 */
    @ApiModelProperty("END_TIME")
    private String endTime;

    /** 会计期间 */
    @ApiModelProperty("FISCAL_PERIOD")
    private String fiscalPeriod;

    /** 原币 */
    @ApiModelProperty("ORIGINAL_CURRENCY")
    private String originalCurrency;

    /** sales_total */
    @ApiModelProperty("SALES_TOTAL")
    private BigDecimal salesTotal;

    /** sales_promotion */
    @ApiModelProperty("SALES_PROMOTION")
    private BigDecimal salesPromotion;

    /** refund_total */
    @ApiModelProperty("REFUND_TOTAL")
    private BigDecimal refundTotal;

    /** giftwarp_shipping */
    @ApiModelProperty("GIFTWARP_SHIPPING")
    private BigDecimal giftwarpShipping;

    /** advertising */
    @ApiModelProperty("ADVERTISING")
    private BigDecimal advertising;

    /** lightning_deal */
    @ApiModelProperty("LIGHTNING_DEAL")
    private BigDecimal lightningDeal;

    /** commission_total */
    @ApiModelProperty("COMMISSION_TOTAL")
    private BigDecimal commissionTotal;

    /** amazon_fee_total小平台 */
    @ApiModelProperty("AMAZON_FEE_TOTAL_XPT")
    private BigDecimal amazonFeeTotalXpt;

    /** amazon_fee_total非小平台 */
    @ApiModelProperty("AMAZON_FEE_TOTAL")
    private BigDecimal amazonFeeTotal;

    /** storage_fee */
    @ApiModelProperty("STORAGE_FEE")
    private BigDecimal storageFee;

    /** reimbursement */
    @ApiModelProperty("REIMBURSEMENT")
    private BigDecimal reimbursement;

    /** other */
    @ApiModelProperty("OTHER")
    private BigDecimal other;

    /** goodwill */
    @ApiModelProperty("GOODWILL")
    private BigDecimal goodwill;

    /** withheld_tax */
    @ApiModelProperty("WITHHELD_TAX")
    private BigDecimal withheldTax;

    /** 本期应收金额 */
    @ApiModelProperty("CURRENT_RECEIVABLE_AMOUNT")
    private BigDecimal currentReceivableAmount;

    /** 本期应收金额（实际） */
    @ApiModelProperty("REAL_RECEIVABLE_AMOUNT")
    private BigDecimal realReceivableAmount;

    /** 同步状态 */
    @ApiModelProperty("SYNC_STATUS")
    private String syncStatus;

    /** 凭证号 */
    @ApiModelProperty("VOUCHER_NO")
    private String voucherNo;

    /** 信用卡账号 */
    @ApiModelProperty("CHARGE_CARD")
    private String chargeCard;

    /** 信用卡实际扣费金额 */
    @ApiModelProperty("CARD_DEDUCTION_AMOUNT")
    private BigDecimal cardDeductionAmount;

    /** 信用卡账号 */
    @ApiModelProperty("SUBJECT_CODE")
    private String subjectCode;

    /** 信用卡账号 */
    @ApiModelProperty("SUBJECT_NAME")
    private String subjectName;

    /** 信用卡币别 */
    @ApiModelProperty("CURRENCY")
    private String currency;

    /** 创建时间 */
    @ApiModelProperty("CREATE_AT")
    private LocalDateTime createAt;

    /** 修改时间 */
    @ApiModelProperty("UPDATE_AT")
    private LocalDateTime updateAt;

    /** 创建人 */
    @ApiModelProperty("CREATE_BY")
    private String createBy;

    /** 修改人 */
    @ApiModelProperty("UPDATE_BY")
    private String updateBy;

    /** 是否有信用卡充值 */
    private Boolean isSuccessfulCharge;

    @ApiModelProperty("站点列表")
    private List<String> sites;

}