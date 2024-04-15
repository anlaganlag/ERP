package com.tadpole.cloud.platformSettlement.api.finance.entity;

import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

/**
* <p>
* 回款确认办理凭证
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
@TableName("CW_PAYMENT_CONFIRM_VOUCHER")
public class PaymentConfirmVoucher implements Serializable {

    private static final long serialVersionUID = 1L;

    /** voucher_id */
    @TableId(value="VOUCHER_ID",type= IdType.AUTO)
    private BigDecimal voucherId;

    /** confirmId */
    @TableField("CONFIRM_ID")
    private BigDecimal confirmId;

    /** 账簿 */
    @TableField("ACCOUNT_BOOK")
    private String accountBook;

    /** 日期 */
    @TableField("VOUCHER_DATE")
    private String voucherDate;

    /** 凭证字 */
    @TableField("PROOF_WORDS")
    private String proofWords;

    /** 核算组织 */
    @TableField("ACCOUNTING_ORGANIZATION")
    private String accountingOrganization;

    /** 会计期间 */
    @TableField("FISCAL_PERIOD")
    private String fiscalPeriod;

    /** 凭证号 */
    @TableField("VOUCHER_NO")
    private String voucherNo;

    /** 附件数 */
    @TableField("ATTACHMENT_NUMBER")
    private BigDecimal attachmentNumber;

    /** 同步状态 */
    @ApiModelProperty("SYNC_STATUS")
    private int syncStatus;

    /** 采购中心、销售中心 */
    @ApiModelProperty("VOUCHER_TYPE")
    private String voucherType;

}