package com.tadpole.cloud.platformSettlement.api.finance.model.result;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.tadpole.cloud.platformSettlement.api.finance.entity.PaymentVoucherDetail;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

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
public class PaymentConfirmVoucherResult implements Serializable {

    private static final long serialVersionUID = 1L;

    /** voucher_id */
    @TableId(value="VOUCHER_ID",type= IdType.AUTO)
    private BigDecimal voucherId;

    /** confirmId */
    @ApiModelProperty("CONFIRM_ID")
    private BigDecimal confirmId;

    /** 账簿 */
    @ApiModelProperty("ACCOUNT_BOOK")
    private String accountBook;

    /** 日期 */
    @ApiModelProperty("VOUCHER_DATE")
    private String voucherDate;

    /** 凭证字 */
    @ApiModelProperty("PROOF_WORDS")
    private String proofWords;

    /** 核算组织 */
    @ApiModelProperty("ACCOUNTING_ORGANIZATION")
    private String accountingOrganization;

    /** 会计期间 */
    @ApiModelProperty("FISCAL_PERIOD")
    private String fiscalPeriod;

    /** 凭证号 */
    @ApiModelProperty("VOUCHER_NO")
    private String voucherNo;

    /** 附件数 */
    @ApiModelProperty("ATTACHMENT_NUMBER")
    private BigDecimal attachmentNumber;

    /** 同步状态 */
    @ApiModelProperty("SYNC_STATUS")
    private int syncStatus;


    /** 凭证明细list */
    @ApiModelProperty("凭证明细list")
    private List<PaymentVoucherDetail> paymentVoucherDetailList;

}