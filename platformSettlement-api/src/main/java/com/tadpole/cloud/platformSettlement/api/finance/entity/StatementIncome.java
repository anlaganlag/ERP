package com.tadpole.cloud.platformSettlement.api.finance.entity;

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
* 收入记录表
* </p>
*
* @author gal
* @since 2021-10-25
*/
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("CW_STATEMENT_INCOME")
public class StatementIncome implements Serializable {

    private static final long serialVersionUID = 1L;

    /** id */
    @TableId(value = "ID",type = IdType.AUTO)
    private BigDecimal id;

    /** 平台 */
    @TableField("PLATFORM_NAME")
    private String platformName;

    /** 账号 */
    @TableField("SHOP_NAME")
    private String shopName;

    /** 站点 */
    @TableField("SITE")
    private String site;

    /** 结算id */
    @TableField("SETTLEMENT_ID")
    private String settlementId;

    /** 结算id序号 */
    @TableField("SETTLEMENT_ORDER")
    private int settlementOrder;

    /** 报告类型 */
    @TableField("REPORT_TYPE")
    private String reportType;

    /** 单据类型 */
    @TableField("BILL_TYPE")
    private String billType;

    /** 收入确认类型 */
    @TableField("INCOME_TYPE")
    private String incomeType;

    /** 开始日期 */
    @TableField("START_TIME")
    private Date startTime;

    /** 结束日期 */
    @TableField("END_TIME")
    private Date endTime;

    /** 会计期间 */
    @TableField("FISCAL_PERIOD")
    private String fiscalPeriod;

    /** 原币 */
    @TableField("ORIGINAL_CURRENCY")
    private String originalCurrency;

    /** sales_total */
    @TableField("SALES_TOTAL")
    private BigDecimal salesTotal;

    /** sales_promotion */
    @TableField("SALES_PROMOTION")
    private BigDecimal salesPromotion;

    /** refund_total */
    @TableField("REFUND_TOTAL")
    private BigDecimal refundTotal;

    /** giftwarp_shipping */
    @TableField("GIFTWARP_SHIPPING")
    private BigDecimal giftwarpShipping;

    /** advertising */
    @TableField("ADVERTISING")
    private BigDecimal advertising;

    /** advertisingRefund */
    @TableField("ADVERTISING_REFUND")
    private BigDecimal advertisingRefund;

    /** lightning_deal */
    @TableField("LIGHTNING_DEAL")
    private BigDecimal lightningDeal;

    /** commission_total */
    @TableField("COMMISSION_TOTAL")
    private BigDecimal commissionTotal;

    /** amazon_fee_total小平台 */
    @TableField("AMAZON_FEE_TOTAL_XPT")
    private BigDecimal amazonFeeTotalXpt;

    /** amazon_fee_total非小平台 */
    @TableField("AMAZON_FEE_TOTAL")
    private BigDecimal amazonFeeTotal;

    /** storage_fee */
    @TableField("STORAGE_FEE")
    private BigDecimal storageFee;

    /** reimbursement */
    @TableField("REIMBURSEMENT")
    private BigDecimal reimbursement;

    /** other */
    @TableField("OTHER")
    private BigDecimal other;

    /** OTHER_FBA_ITF */
    @TableField("OTHER_FBA_ITF")
    private BigDecimal otherFbaItf;

    /** goodwill */
    @TableField("GOODWILL")
    private BigDecimal goodwill;

    /** withheld_tax */
    @TableField("WITHHELD_TAX")
    private BigDecimal withheldTax;

    /** 本期应收金额 */
    @TableField("CURRENT_RECEIVABLE_AMOUNT")
    private BigDecimal currentReceivableAmount;

    /** 本期应收金额（实际） */
    @TableField("REAL_RECEIVABLE_AMOUNT")
    private BigDecimal realReceivableAmount;

    /** 信用卡充值金额 */
    @TableField("SUCCESSFUL_CHARGE")
    private BigDecimal successfulCharge;

    /** 信用卡账号 */
    @TableField("CHARGE_CARD")
    private String chargeCard;

    /** 信用卡科目 */
    @TableField("SUBJECT_CODE")
    private String subjectCode;

    /** 信用卡账号 */
    @TableField("SUBJECT_NAME")
    private String subjectName;

    /** 信用卡币别 */
    @TableField("CARD_CURRENCY")
    private String cardCurrency;

    /** 信用卡实际扣费金额 */
    @TableField("CARD_DEDUCTION_AMOUNT")
    private BigDecimal cardDeductionAmount;

    /** 同步状态 */
    @TableField("SYNC_STATUS")
    private int syncStatus;

    /** 刷财务分类状态 */
    @TableField("REFRESH_STATUS")
    private int refreshStatus;

    /** 凭证号 */
    @TableField("VOUCHER_NO")
    private String voucherNo;

    /** 创建时间 */
    @TableField("CREATE_AT")
    private Date createAt;

    /** 修改时间 */
    @TableField("UPDATE_AT")
    private Date updateAt;

    /** 创建人 */
    @TableField("CREATE_BY")
    private String createBy;

    /** 修改人 */
    @TableField("UPDATE_BY")
    private String updateBy;

}