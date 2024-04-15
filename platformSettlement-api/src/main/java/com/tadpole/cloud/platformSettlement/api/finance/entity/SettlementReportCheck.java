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
* AZ结算报告审核
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
@TableName("CW_SETTLEMENT_REPORT_CHECK")
public class SettlementReportCheck implements Serializable {

    private static final long serialVersionUID = 1L;

    /** id */
    @TableId(value="ID",type= IdType.AUTO)
    private BigDecimal id;

    /** 源仓任务名称 */
    @TableField("ORIGINAL_TASK_NAME")
    private String originalTaskName;

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

    /** 汇款日期 */
    @TableField("REMITTANCE_DATE")
    private Date remittanceDate;

    /** 汇款账号 */
    @TableField("REMITTANCE_ACCOUNT")
    private String remittanceAccount;

    /** 汇款银行 */
    @TableField("REMITTANCE_BANK")
    private String remittanceBank;

    /** 收款币种 */
    @TableField("PROCEEDS_CURRENCY")
    private String proceedsCurrency;

    /** 收款金额 */
    @TableField("REMITTANCE_MONEY")
    private BigDecimal remittanceMoney;

    /** 原币 */
    @TableField("ORIGINAL_CURRENCY")
    private String originalCurrency;

    /** 金额 */
    @TableField("MONEY")
    private BigDecimal money;

    /** 总金额 */
    @TableField("TOTAL_MONEY")
    private BigDecimal totalMoney;

    /** 审核状态 */
    @TableField("STATUS")
    private int status;

    /** 审核人员 */
    @TableField("VERIFY_BY")
    private String verifyBy;

    /** 创建时间 */
    @TableField("CREATE_AT")
    private Date createAt;

    /** 修改时间 */
    @TableField("UPDATE_AT")
    private Date updateAt;
}