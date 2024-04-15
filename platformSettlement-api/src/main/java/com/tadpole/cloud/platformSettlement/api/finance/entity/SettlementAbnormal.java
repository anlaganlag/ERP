package com.tadpole.cloud.platformSettlement.api.finance.entity;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>
 *  AZ结算异常实体类
 * </p>
 *
 * @author ty
 * @since 2022-07-28
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("CW_SETTLEMENT_ABNORMAL")
@ExcelIgnoreUnannotated
public class SettlementAbnormal implements Serializable {

    private static final long serialVersionUID = 1L;

    /** ID */
    @TableField("ID")
    private BigDecimal id;

    /** 订单日期 */
    @TableField("PURCHSE_DATE_STR")
    private String purchseDateStr;

    /** 账号 */
    @TableField("SYS_SHOPS_NAME")
    private String sysShopsName;

    /** 站点 */
    @TableField("SYS_SITE")
    private String sysSite;

    /** 交易ID */
    @TableField("AMAZON_ORDER_ID")
    private String amazonOrderId;

    /** 客户付款金额 */
    @TableField("PAYMENT_AMOUNT")
    private BigDecimal paymentAmount;

    /** 应结算金额 */
    @TableField("SETTLEMENT_AMOUNT")
    private BigDecimal settlementAmount;

    /** 实际结算金额 */
    @TableField("AUTUAL_SETTLEMENT_AMOUNT")
    private BigDecimal autualSettlementAmount;

    /** 是否结算异常 */
    @TableField("SETTLEMENT_ABNORMAL")
    private String settlementAbnormal;

    /** 创建时间 */
    @TableField("CREATE_TIME")
    private Date createTime;

    /** 创建人 */
    @TableField("CREATE_BY")
    private String createBy;

    /** 更新时间 */
    @TableField("UPDATE_TIME")
    private Date updateTime;

    /** 更新人 */
    @TableField("UPDATE_BY")
    private String updateBy;

}