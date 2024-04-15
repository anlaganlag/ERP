package com.tadpole.cloud.platformSettlement.api.finance.entity;

import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import java.util.Date;
import lombok.*;

/**
* <p>
* 回款确认办理
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
@TableName("CW_PAYMENT_CONFIRM_HANDLING")
public class PaymentConfirmHandling implements Serializable {

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

    /** 报告类型 */
    @TableField("REPORT_TYPE")
    private String reportType;

    /** 单据类型 */
    @TableField("BILL_TYPE")
    private String billType;

    /** 结算id */
    @TableField("SETTLEMENT_ID")
    private String settlementId;

    /** 开始时间 */
    @TableField("START_TIME")
    private Date startTime;

    /** 结束时间 */
    @TableField("END_TIME")
    private Date endTime;

    /** 原币 */
    @TableField("ORIGINAL_CURRENCY")
    private String originalCurrency;

    /** 汇款日期 */
    @TableField("DEPOSIT_DATE")
    private Date depositDate;

    /** 汇款银行 */
    @TableField("DEPOSIT_BANK")
    private String depositBank;

    /** 汇款账号 */
    @TableField("DEPOSIT_ACCOUNT")
    private String depositAccount;

    /** amount */
    @TableField("AMOUNT")
    private BigDecimal amount;

    /** total_amount */
    @TableField("TOTAL_AMOUNT")
    private BigDecimal totalAmount;

    /** 收款币种 */
    @TableField("PROCEEDS_CURRENCY")
    private String proceedsCurrency;

    /** 结算汇率 */
    @TableField("SETTLEMENT_RATE")
    private BigDecimal settlementRate;

    /** 实际收款原币金额 */
    @TableField("RECEIVE_ORIGINAL_AMOUNT")
    private BigDecimal receiveOriginalAmount;

    /** 实际收款金额 */
    @TableField("RECEIVE_AMOUNT")
    private BigDecimal receiveAmount;

    /** 回款银行 */
    @TableField("COLLECTION_BANK")
    private String collectionBank;

    /** 回款账号 */
    @TableField("COLLECTION_ACCOUNT")
    private String collectionAccount;

    /** 是否已收到款项 */
    @TableField("IS_RECEIVE_AMOUNT")
    private String isReceiveAmount;

    /** 回款日期 */
    @TableField("RECEIVE_DATE")
    private Date receiveDate;

    /** 会计期间 */
    @TableField("FISCAL_PERIOD")
    private String fiscalPeriod;

    /** 同步状态 */
    @TableField("SYNC_STATUS")
    private int syncStatus;

    /** 同步状态 */
    @TableField("VERIFY_STATUS")
    private int verifyStatus;

    /** 凭证号 */
    @TableField("VOUCHER_NO")
    private String voucherNo;

    /** 出纳确认人 */
    @TableField("COMFIRM_BY")
    private String comfirmBy;

    /** 科目编码 */
    @TableField("SUBJECT_CODE")
    private String subjectCode;

    /** 科目名称 */
    @TableField("SUBJECT_NAME")
    private String subjectName;

    /** 会计审核人 */
    @TableField("VERIFY_BY")
    private String verifyBy;

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

    /** 修改人 */
    @TableField("PAYMENT_TYPE")
    private String paymentType;

}