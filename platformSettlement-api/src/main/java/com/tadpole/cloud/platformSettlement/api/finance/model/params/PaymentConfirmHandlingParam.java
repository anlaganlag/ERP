package com.tadpole.cloud.platformSettlement.api.finance.model.params;

import cn.stylefeng.guns.cloud.model.validator.BaseValidatingParam;
import cn.stylefeng.guns.cloud.model.web.request.BaseRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

/**
* <p>
* 回款确认办理
* </p>
*
* @author gal
* @since 2021-10-25
*/
@Data
@ApiModel
public class PaymentConfirmHandlingParam extends BaseRequest implements Serializable, BaseValidatingParam {

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

    /** 报告类型 */
    @ApiModelProperty("REPORT_TYPE")
    private String reportType;

    /** 单据类型 */
    @ApiModelProperty("BILL_TYPE")
    private String billType;

    /** 结算id */
    @ApiModelProperty("SETTLEMENT_ID")
    private String settlementId;

    /** 开始时间 */
    @ApiModelProperty("START_TIME")
    private LocalDateTime startTime;

    /** 结束时间 */
    @ApiModelProperty("END_TIME")
    private LocalDateTime endTime;

    /** 原币 */
    @ApiModelProperty("ORIGINAL_CURRENCY")
    private String originalCurrency;

    /** 汇款日期 */
    @ApiModelProperty("DEPOSIT_DATE")
    private LocalDateTime depositDate;

    /** 汇款银行 */
    @ApiModelProperty("DEPOSIT_BANK")
    private String depositBank;

    /** 汇款账号 */
    @ApiModelProperty("DEPOSIT_ACCOUNT")
    private String depositAccount;

    /** amount */
    @ApiModelProperty("AMOUNT")
    private BigDecimal amount;

    /** total_amount */
    @ApiModelProperty("TOTAL_AMOUNT")
    private BigDecimal totalAmount;

    /** 收款币种 */
    @ApiModelProperty("PROCEEDS_CURRENCY")
    private String proceedsCurrency;

    /** 结算汇率 */
    @ApiModelProperty("SETTLEMENT_RATE")
    private BigDecimal settlementRate;

    /** 实际收款原币金额 */
    @ApiModelProperty("RECEIVE_ORIGINAL_AMOUNT")
    private BigDecimal receiveOriginalAmount;

    /** 实际收款金额 */
    @ApiModelProperty("RECEIVE_AMOUNT")
    private BigDecimal receiveAmount;

    /** 回款银行 */
    @ApiModelProperty("COLLECTION_BANK")
    private String collectionBank;

    /** 回款账号 */
    @ApiModelProperty("COLLECTION_ACCOUNT")
    private String collectionAccount;

    /** 是否已收到款项 */
    @ApiModelProperty("IS_RECEIVE_AMOUNT")
    private List<String> isReceiveAmount;

    /** 回款日期 */
    @ApiModelProperty("RECEIVE_DATE")
    private String receiveDate;

    /** 会计期间 */
    @ApiModelProperty("FISCAL_PERIOD")
    private String fiscalPeriod;

    /** 同步状态 */
    @ApiModelProperty("SYNC_STATUS")
    private String syncStatus;

    /** 凭证号 */
    @ApiModelProperty("VOUCHER_NO")
    private String voucherNo;

    /** 出纳确认人 */
    @ApiModelProperty("COMFIRM_BY")
    private String comfirmBy;

    /** 会计审核人 */
    @ApiModelProperty("VERIFY_BY")
    private String verifyBy;

    /** 审核状态 */
    @ApiModelProperty("VERIFY_STATUS")
    private String verifyStatus;

    /** 科目编码 */
    @ApiModelProperty("SUBJECT_CODE")
    private String subjectCode;

    /** 科目名称 */
    @ApiModelProperty("SUBJECT_NAME")
    private String subjectName;

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

    /** 修改人 */
    @ApiModelProperty("PAYMENT_TYPE")
    private String paymentType;

    /** 修改人 */
    @ApiModelProperty("VOUCHER_TYPE")
    private String voucherType;

}