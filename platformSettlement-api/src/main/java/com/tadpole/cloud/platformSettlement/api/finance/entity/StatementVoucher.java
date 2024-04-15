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
* 收入记录表凭证
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
@TableName("CW_STATEMENT_VOUCHER")
public class StatementVoucher implements Serializable {

    private static final long serialVersionUID = 1L;

    /** voucher_id */
    @TableId(value="VOUCHER_ID",type= IdType.AUTO)
    private BigDecimal voucherId;

    /** 收入记录id */
    @TableField("RECORD_ID")
    private BigDecimal recordId;

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
    @TableField("VOUCHER_NUMBER")
    private String voucherNumber;

    /** 附件数 */
    @TableField("ATTACHMENT_NUMBER")
    private BigDecimal attachmentNumber;

    /** 差异金额 */
    @TableField("DIFFERENCE_AMOUNT")
    private BigDecimal differenceAmount;

    /** 同步状态 */
    @ApiModelProperty("SYNC_STATUS")
    private int syncStatus;

}