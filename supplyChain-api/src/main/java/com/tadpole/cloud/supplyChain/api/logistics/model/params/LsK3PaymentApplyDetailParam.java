package com.tadpole.cloud.supplyChain.api.logistics.model.params;

import cn.stylefeng.guns.cloud.model.validator.BaseValidatingParam;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>
 * 物流费K3付款申请单明细 入参类
 * </p>
 *
 * @author ty
 * @since 2023-12-05
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("LS_K3_PAYMENT_APPLY_DETAIL")
public class LsK3PaymentApplyDetailParam implements Serializable, BaseValidatingParam {

    private static final long serialVersionUID = 1L;

    /** ID */
    @TableId(value = "ID", type = IdType.AUTO)
    private BigDecimal id;

    /** 单据编号 */
    @ApiModelProperty("单据编号")
    private String paymentApplyNo;

    /** 付费事项 */
    @ApiModelProperty("付费事项")
    private String paymentRemark;

    @ApiModelProperty("结算方式编码")
    private String settlementTypeCode;

    /** 结算方式 */
    @ApiModelProperty("结算方式")
    private String settlementType;

    /** 应付比例（%） */
    @ApiModelProperty("应付比例（%）")
    private String paymentPercent;

    /** 付款用途 */
    @ApiModelProperty("付款用途")
    private String paymentUse;

    @ApiModelProperty("付款用途编码")
    private String paymentUseCode;

    /** 对账金额 */
    @ApiModelProperty("对账金额")
    private BigDecimal settlementAmt;

    /** 申请付款金额 */
    @ApiModelProperty("申请付款金额")
    private BigDecimal applyPaymentAmt;

    /** 到期日 */
    @ApiModelProperty("到期日")
    private Date expireDate;

    /** 期望付款日期 */
    @ApiModelProperty("期望付款日期")
    private Date wishPaymentDate;

    /** 开票日期 */
    @ApiModelProperty("开票日期")
    private Date billDate;

    /** 发票代码 */
    @ApiModelProperty("发票代码")
    private String billCode;

    /** 发票号码 */
    @ApiModelProperty("发票号码")
    private String billNo;

    /** 备注 */
    @ApiModelProperty("备注")
    private String remark;

    /** 未付款金额 */
    @ApiModelProperty("未付款金额")
    private BigDecimal notPayAmt;

    /** 对方银行账号 */
    @ApiModelProperty("对方银行账号")
    private String bankAccNo;

    /** 对方银行账户名称 */
    @ApiModelProperty("对方银行账户名称")
    private String bankAccName;

    /** 对方银行开户行 */
    @ApiModelProperty("对方银行开户行")
    private String bankName;

    /** 创建时间 */
    @ApiModelProperty("创建时间")
    private Date createTime;

    /** 创建人 */
    @ApiModelProperty("创建人")
    private String createUser;

    /** 更新时间 */
    @ApiModelProperty("更新时间")
    private Date updateTime;

    /** 更新人 */
    @ApiModelProperty("更新人")
    private String updateUser;

}
