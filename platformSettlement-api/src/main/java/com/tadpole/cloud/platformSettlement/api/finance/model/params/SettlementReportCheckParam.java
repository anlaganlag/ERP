package com.tadpole.cloud.platformSettlement.api.finance.model.params;

import cn.stylefeng.guns.cloud.model.validator.BaseValidatingParam;
import cn.stylefeng.guns.cloud.model.web.request.BaseRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.io.Serializable;
import java.math.BigDecimal;

/**
* <p>
* AZ结算报告审核
* </p>
*
* @author gal
* @since 2021-10-25
*/
@Data
@ApiModel
public class SettlementReportCheckParam extends BaseRequest implements Serializable, BaseValidatingParam {

    private static final long serialVersionUID = 1L;

    /** id */
    @ApiModelProperty("ID")
    private BigDecimal id;

    /** 源仓任务名称 */
    @ApiModelProperty("ORIGINAL_TASK_NAME")
    private String originalTaskName;

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
    private String startTime;

    /** 结束时间 */
    @ApiModelProperty("END_TIME")
    private String endTime;

    /** 汇款日期 */
    @ApiModelProperty("REMITTANCE_DATE")
    private String remittanceDate;

    /** 汇款账号 */
    @ApiModelProperty("REMITTANCE_ACCOUNT")
    private String remittanceAccount;

    /** 汇款银行 */
    @ApiModelProperty("REMITTANCE_BANK")
    private String remittanceBank;

    /** 收款币种 */
    @ApiModelProperty("PROCEEDS_CURRENCY")
    private String proceedsCurrency;

    /** 收款金额 */
    @ApiModelProperty("REMITTANCE_MONEY")
    private String remittanceMoney;

    /** 原币 */
    @ApiModelProperty("ORIGINAL_CURRENCY")
    private String originalCurrency;

    /** 金额 */
    @ApiModelProperty("MONEY")
    private String money;

    /** 总金额 */
    @ApiModelProperty("TOTAL_MONEY")
    private String totalMoney;

    /** 审核状态 */
    @ApiModelProperty("STATUS")
    private String status;

    /** 审核人员 */
    @ApiModelProperty("VERIFY_BY")
    private String verifyBy;

    /** 创建时间 */
    @ApiModelProperty("CREATE_AT")
    private String createAt;

    /** 修改时间 */
    @ApiModelProperty("UPDATE_AT")
    private String updateAt;

}