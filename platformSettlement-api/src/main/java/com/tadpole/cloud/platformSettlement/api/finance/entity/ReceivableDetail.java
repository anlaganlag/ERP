package com.tadpole.cloud.platformSettlement.api.finance.entity;

import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import lombok.*;

/**
* <p>
* 应收明细
* </p>
*
* @author gal
* @since 2021-10-25
*/
@Data
@TableName("CW_RECEIVABLE_DETAIL")
public class ReceivableDetail implements Serializable {

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

    /** 会计期间 */
    @TableField("FISCAL_PERIOD")
    private String fiscalPeriod;

    /** 本期应收合计 */
    @TableField("RECEIVABLE_AMOUNT")
    private BigDecimal receivableAmount;

    /** 本期收款合计 */
    @TableField("RECEIVE_AMOUNT")
    private BigDecimal receiveAmount;

    /** total_amount */
    @TableField("TOTAL_AMOUNT")
    private BigDecimal totalAmount;

    /** 期末应收金额 */
    @TableField("ENDTERM_RECEIVABLE_AMOUNT")
    private BigDecimal endtermReceivableAmount;

    /** 期初应收金额 */
    @TableField("INITIAL_RECEIVE_AMOUNT")
    private BigDecimal initialReceiveAmount;

    /** 审核人 */
    @TableField("VERIFY_BY")
    private String verifyBy;

    /** 状态 */
    @TableField("STATUS")
    private BigDecimal status;

}