package com.tadpole.cloud.platformSettlement.api.finance.entity;

import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import lombok.*;

/**
* <p>
* 收入记录表凭证明细
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
@TableName("CW_STATEMENT_VOUCHER_DETAIL")
public class StatementVoucherDetail implements Serializable {

    private static final long serialVersionUID = 1L;

    /** id */
    @TableId(value="ID",type= IdType.AUTO)
    private BigDecimal id;

    /** voucher_id */
    @TableField("VOUCHER_ID")
    private BigDecimal voucherId;

    /** 摘要 */
    @TableField("DIGEST")
    private String digest;

    /** 科目编码 */
    @TableField("SUBJECT_CODE")
    private String subjectCode;

    /** 科目全名 */
    @TableField("SUBJECT_NAME")
    private String subjectName;

    /** 币别 */
    @TableField("CURRENCY")
    private String currency;

    /** 核算维度 */
    @TableField("ACCOUNTING_DIMENSIONS")
    private String accountingDimensions;

    /** 汇率类型 */
    @TableField("EXCHANGE_RATE_TYPE")
    private String exchangeRateType;

    /** 汇率 */
    @TableField("EXCHANGE_RATE")
    private BigDecimal exchangeRate;

    /** 原币金额 */
    @TableField("ORIGINAL_AMOUNT")
    private BigDecimal originalAmount;

    /** 借方金额 */
    @TableField("DEBIT_AMOUNT")
    private BigDecimal debitAmount;

    /** 贷方金额 */
    @TableField("CREDIT_AMOUNT")
    private BigDecimal creditAmount;

}