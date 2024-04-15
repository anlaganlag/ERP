package com.tadpole.cloud.platformSettlement.api.finance.model.result;

import cn.stylefeng.guns.cloud.model.validator.BaseValidatingParam;
import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.Date;
import lombok.Data;
import java.io.Serializable;
import java.math.BigDecimal;

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
@ExcelIgnoreUnannotated
public class PaymentConfirmHandlingResult implements Serializable, BaseValidatingParam {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @ApiModelProperty("ID")
    private BigDecimal id;

    /**
     * 平台
     */
    @ApiModelProperty("PLATFORM_NAME")
    @ExcelProperty(value = "平台")
    private String platformName;

    /**
     * 账号
     */
    @ApiModelProperty("SHOP_NAME")
    @ExcelProperty(value = "账号")
    private String shopName;

    /**
     * 站点
     */
    @ApiModelProperty("SITE")
    @ExcelProperty(value = "站点")
    private String site;

    /**
     * 报告类型
     */
    @ApiModelProperty("REPORT_TYPE")
    @ExcelProperty(value = "报告类型")
    private String reportType;

    /**
     * 单据类型
     */
    @ApiModelProperty("BILL_TYPE")
    @ExcelProperty(value = "单据类型")
    private String billType;

    /**
     * 结算id
     */
    @ApiModelProperty("SETTLEMENT_ID")
    @ExcelProperty(value = "结算ID")
    private String settlementId;

    /**
     * 开始时间
     */
    @ApiModelProperty("START_TIME")
    @ExcelProperty(value = "开始日期")
    private Date startTime;

    /**
     * 结束时间
     */
    @ApiModelProperty("END_TIME")
    @ExcelProperty(value = "结束日期")
    private Date endTime;

    /**
     * 原币
     */
    @ApiModelProperty("ORIGINAL_CURRENCY")
    @ExcelProperty(value = "原币")
    private String originalCurrency;

    /**
     * 汇款日期
     */
    @ApiModelProperty("DEPOSIT_DATE")
    @ExcelProperty(value = "汇款日期")
    private Date depositDate;

    /**
     * 汇款银行
     */
    @ApiModelProperty("DEPOSIT_BANK")
    @ExcelProperty(value = "汇款银行")
    private String depositBank;

    /**
     * 汇款账号
     */
    @ApiModelProperty("DEPOSIT_ACCOUNT")
    @ExcelProperty(value = "汇款账号")
    private String depositAccount;

    /**
     * amount
     */
    @ApiModelProperty("AMOUNT")
    @ExcelProperty(value = "amount")
    private BigDecimal amount;

    /**
     * total_amount
     */
    @ApiModelProperty("TOTAL_AMOUNT")
    @ExcelProperty(value = "total-amount")
    private BigDecimal totalAmount;

    /**
     * 会计审核人
     */
    @ApiModelProperty("VERIFY_BY")
    @ExcelProperty(value = "审核人员")
    private String verifyBy;

    /**
     * 收款币种
     */
    @ApiModelProperty("PROCEEDS_CURRENCY")
    @ExcelProperty(value = "收款币种")
    private String proceedsCurrency;

    /**
     * 结算汇率
     */
    @ApiModelProperty("SETTLEMENT_RATE")
    @ExcelProperty(value = "结算汇率")
    private BigDecimal settlementRate;

    /**
     * 实际收款原币金额
     */
    @ApiModelProperty("RECEIVE_ORIGINAL_AMOUNT")
    @ExcelProperty(value = "实际收款原币金额")
    private BigDecimal receiveOriginalAmount;

    /**
     * 实际收款金额
     */
    @ApiModelProperty("RECEIVE_AMOUNT")
    @ExcelProperty(value = "实际收款金额")
    private BigDecimal receiveAmount;

    /**
     * 回款银行
     */
    @ApiModelProperty("COLLECTION_BANK")
    @ExcelProperty(value = "回款银行")
    private String collectionBank;

    /**
     * 回款账号
     */
    @ApiModelProperty("COLLECTION_ACCOUNT")
    @ExcelProperty(value = "回款账号")
    private String collectionAccount;

    /**
     * 是否已收到款项
     */
    @ApiModelProperty("IS_RECEIVE_AMOUNT")
    @ExcelProperty(value = "是否已收到款项")
    private String isReceiveAmount;

    /**
     * 回款日期
     */
    @ApiModelProperty("RECEIVE_DATE")
    @ExcelProperty(value = "回款日期")
    private Date receiveDate;

    /**
     * 会计期间
     */
    @ApiModelProperty("FISCAL_PERIOD")
    @ExcelProperty(value = "会计期间")
    private String fiscalPeriod;

    /**
     * 同步状态
     */
    @ApiModelProperty("SYNC_STATUS")
    @ExcelProperty(value = "同步状态")
    private String syncStatus;

    /**
     * 凭证号
     */
    @ApiModelProperty("VOUCHER_NO")
    @ExcelProperty(value = "凭证号")
    private String voucherNo;

    /**
     * 出纳确认人
     */
    @ApiModelProperty("COMFIRM_BY")
    private String comfirmBy;

    /**
     * 会计审核人
     */
    @ApiModelProperty("VERIFY_STATUS")
    @ExcelProperty(value = "会计审核状态")
    private String verifyStatus;

    /**
     * 创建时间
     */
    @ApiModelProperty("CREATE_AT")
    private Date createAt;

    /**
     * 修改时间
     */
    @ApiModelProperty("UPDATE_AT")
    private Date updateAt;

    /**
     * 创建人
     */
    @ApiModelProperty("CREATE_BY")
    private String createBy;

    /**
     * 修改人
     */
    @ApiModelProperty("UPDATE_BY")
    private String updateBy;

    /** 修改人 */
    @ApiModelProperty("PAYMENT_TYPE")
    @ExcelProperty(value = "回款方式")
    private String paymentType;

}