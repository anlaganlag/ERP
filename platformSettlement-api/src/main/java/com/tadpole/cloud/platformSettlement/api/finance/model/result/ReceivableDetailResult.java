package com.tadpole.cloud.platformSettlement.api.finance.model.result;

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
* 应收明细详情
* </p>
*
* @author gal
* @since 2021-10-25
*/
@Data
@ApiModel
@ExcelIgnoreUnannotated
public class ReceivableDetailResult implements Serializable {

    private static final long serialVersionUID = 1L;

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

    /** 会计期间 */
    @ApiModelProperty("FISCAL_PERIOD")
    @ExcelProperty(value = "会计期间")
    private String fiscalPeriod;

    /** 期初应收金额 */
    @ApiModelProperty("INITIAL_RECEIVE_AMOUNT")
    @ExcelProperty(value = "期初应收金额")
    private BigDecimal initialReceiveAmount;

    /** 本期应收合计 */
    @ApiModelProperty("RECEIVABLE_AMOUNT")
    @ExcelProperty(value = "本期应收合计")
    private BigDecimal receivableAmount;

    /** 本期收款合计 */
    @ApiModelProperty("RECEIVE_AMOUNT_TOTAL")
    @ExcelProperty(value = "本期收款合计")
    private BigDecimal receiveAmountTotal;

    /** 期末应收金额 */
    @ApiModelProperty("ENDTERM_RECEIVABLE_AMOUNT")
    @ExcelProperty(value = "期末应收金额")
    private BigDecimal endtermReceivableAmount;

    /** 结算单ID */
    @ApiModelProperty("SETTLEMENT_ID")
    @ExcelProperty(value = "结算单ID")
    private String settlementId;

    /** 结算单序号 */
    @ApiModelProperty("SETTLEMENT_NO")
    @ExcelProperty(value = "结算单序号")
    private String settlementNo;

    /** 收入确认类型 */
    @ApiModelProperty("INCOME_TYPE")
    @ExcelProperty(value = "收入确认类型")
    private String incomeType;

    /** 原币 */
    @ApiModelProperty("ORIGINAL_CURRENCY")
    @ExcelProperty(value = "原币")
    private String originalCurrency;

    /** 收款币种 */
    @ApiModelProperty("PROCEEDS_CURRENCY")
    @ExcelProperty(value = "收款币种")
    private String proceedsCurrency;

    /** 本期应收金额 */
    @ApiModelProperty("CURRENT_RESERVE_AMOUNT")
    @ExcelProperty(value = "本期应收金额")
    private BigDecimal currentReserveAmount;

    /** 本期收款合计 */
    @ApiModelProperty("RECEIVE_AMOUNT")
    @ExcelProperty(value = "本期信用卡充值金额（原币）")
    private BigDecimal receiveAmount;

    /** total_amount */
    @ApiModelProperty("TOTAL_AMOUNT")
    private BigDecimal totalAmount;

    /** 信用卡充值金额 */
    @ApiModelProperty("SUCCESSFUL_CHARGE")
    @ExcelProperty(value = "信用卡充值金额")
    private BigDecimal successfulCharge;

    /** 期末应收余额 */
    @ApiModelProperty("BALANCE")
    @ExcelProperty(value = "期末应收余额")
    private BigDecimal balance;

    /** 应收id */
    @ApiModelProperty("RECEIVABLE_ID")
    private BigDecimal receivableId;

    /** 创建人 */
    @ApiModelProperty("CREATE_BY")
    @ExcelProperty(value = "创建人")
    private String createBy;

    /** 创建时间 */
    @ApiModelProperty("CREATE_AT")
    @ExcelProperty(value = "创建时间")
    private Date createAt;

    /** 修改时间 */
    @ApiModelProperty("UPDATE_AT")
    private Date updateAt;

    /** 修改人 */
    @ApiModelProperty("UPDATE_BY")
    private String updateBy;

    /** 状态 */
    @ApiModelProperty("STATUS")
    private BigDecimal status;

    /** 操作 */
    @ApiModelProperty("IS_REJECT_TXT")
    @ExcelProperty(value = "操作")
    private String isRejectTxt;

}