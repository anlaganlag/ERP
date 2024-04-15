package com.tadpole.cloud.platformSettlement.api.finance.model.result;

import cn.stylefeng.guns.cloud.model.validator.BaseValidatingParam;
import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>
 * AZ结算异常出参类
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
@ExcelIgnoreUnannotated
@TableName("CW_SETTLEMENT_ABNORMAL")
public class SettlementAbnormalResult implements Serializable, BaseValidatingParam {

    private static final long serialVersionUID = 1L;

    /** ID */
    @ApiModelProperty("ID")
    private BigDecimal id;

    /** 订单日期 */
    @ApiModelProperty("订单日期")
    private String purchseDateStr;

    /** 账号 */
    @ApiModelProperty("账号")
    private String sysShopsName;

    /** 站点 */
    @ApiModelProperty("站点")
    private String sysSite;

    /** 交易ID */
    @ApiModelProperty("交易ID")
    private String amazonOrderId;

    /** 客户付款金额 */
    @ApiModelProperty("客户付款金额")
    private BigDecimal paymentAmount;

    /** 应结算金额 */
    @ApiModelProperty("应结算金额")
    private BigDecimal settlementAmount;

    /** 实际结算金额 */
    @ApiModelProperty("实际结算金额")
    private BigDecimal autualSettlementAmount;

    /** 是否结算异常 */
    @ApiModelProperty("是否结算异常")
    private String settlementAbnormal;

    /** 创建时间 */
    @ApiModelProperty("创建时间")
    private Date createTime;

    /** 创建人 */
    @ApiModelProperty("创建人")
    private String createBy;

    /** 更新时间 */
    @ApiModelProperty("更新时间")
    private Date updateTime;

    /** 更新人 */
    @ApiModelProperty("更新人")
    private String updateBy;

}