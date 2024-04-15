package com.tadpole.cloud.platformSettlement.api.finance.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;
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
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("CW_RECEIVABLE_DETAIL_DETAIL")
public class ReceivableDetailDetail implements Serializable {

    private static final long serialVersionUID = 1L;

    /** id */
    @TableId(value = "ID",type = IdType.AUTO)
    private BigDecimal id;

    /** 应收id */
    @TableField("RECEIVABLE_ID")
    private BigDecimal receivableId;

    /** 结算单ID */
    @TableField("SETTLEMENT_ID")
    private String settlementId;

    /** 结算单序号 */
    @TableField("SETTLEMENT_NO")
    private BigDecimal settlementNo;

    /** 收入确认类型 */
    @TableField("INCOME_TYPE")
    private String incomeType;

    /** 原币 */
    @TableField("ORIGINAL_CURRENCY")
    private String originalCurrency;

    /** 收款币种 */
    @TableField("PROCEEDS_CURRENCY")
    private String proceedsCurrency;

    /** 本期应收金额 */
    @TableField("CURRENT_RESERVE_AMOUNT")
    private BigDecimal currentReserveAmount;

    /** 本期收款金额(原币) */
    @TableField("RECEIVE_AMOUNT")
    private BigDecimal receiveAmount;

    /** 信用卡充值金额 */
    @TableField("SUCCESSFUL_CHARGE")
    private BigDecimal successfulCharge;

    /** 期末应收余额 */
    @TableField("BALANCE")
    private BigDecimal balance;

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

    /** 是否反审核 */
    @TableField("IS_REJECT")
    private int isReject;

}