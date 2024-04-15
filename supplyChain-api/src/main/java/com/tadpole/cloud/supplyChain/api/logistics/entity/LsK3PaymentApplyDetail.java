package com.tadpole.cloud.supplyChain.api.logistics.entity;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>
 * 物流费K3付款申请单明细 实体类
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
@ExcelIgnoreUnannotated
public class LsK3PaymentApplyDetail implements Serializable {

    private static final long serialVersionUID = 1L;

    /** ID */
    @TableId(value = "ID", type = IdType.AUTO)
    private BigDecimal id;

    /** 单据编号 */
    @ApiModelProperty("单据编号")
    @TableField("PAYMENT_APPLY_NO")
    private String paymentApplyNo;

    /** 付费事项 */
    @ApiModelProperty("付费事项")
    @TableField("PAYMENT_REMARK")
    private String paymentRemark;

    /** 结算方式编码 */
    @ApiModelProperty("结算方式编码")
    @TableField("SETTLEMENT_TYPE_CODE")
    private String settlementTypeCode;

    /** 结算方式 */
    @ApiModelProperty("结算方式")
    @TableField("SETTLEMENT_TYPE")
    private String settlementType;

    /** 应付比例（%） */
    @ApiModelProperty("应付比例（%）")
    @TableField("PAYMENT_PERCENT")
    private String paymentPercent;

    /** 付款用途编码 */
    @ApiModelProperty("付款用途编码")
    @TableField("PAYMENT_USE_CODE")
    private String paymentUseCode;

    /** 付款用途 */
    @ApiModelProperty("付款用途")
    @TableField("PAYMENT_USE")
    private String paymentUse;

    /** 对账金额 */
    @ApiModelProperty("对账金额")
    @TableField("SETTLEMENT_AMT")
    private BigDecimal settlementAmt;

    /** 申请付款金额 */
    @ApiModelProperty("申请付款金额")
    @TableField("APPLY_PAYMENT_AMT")
    private BigDecimal applyPaymentAmt;

    /** 到期日 */
    @ApiModelProperty("到期日")
    @TableField("EXPIRE_DATE")
    private Date expireDate;

    /** 期望付款日期 */
    @ApiModelProperty("期望付款日期")
    @TableField("WISH_PAYMENT_DATE")
    private Date wishPaymentDate;

    /** 开票日期 */
    @ApiModelProperty("开票日期")
    @TableField("BILL_DATE")
    private Date billDate;

    /** 发票代码 */
    @ApiModelProperty("发票代码")
    @TableField("BILL_CODE")
    private String billCode;

    /** 发票号码 */
    @ApiModelProperty("发票号码")
    @TableField("BILL_NO")
    private String billNo;

    /** 备注 */
    @ApiModelProperty("备注")
    @TableField("REMARK")
    private String remark;

    /** 未付款金额 */
    @ApiModelProperty("未付款金额")
    @TableField("NOT_PAY_AMT")
    private BigDecimal notPayAmt;

    /** 对方银行账号 */
    @ApiModelProperty("对方银行账号")
    @TableField("BANK_ACC_NO")
    private String bankAccNo;

    /** 对方银行账户名称 */
    @ApiModelProperty("对方银行账户名称")
    @TableField("BANK_ACC_NAME")
    private String bankAccName;

    /** 对方银行开户行 */
    @ApiModelProperty("对方银行开户行")
    @TableField("BANK_NAME")
    private String bankName;

    /** 创建时间 */
    @ApiModelProperty("创建时间")
    @TableField("CREATE_TIME")
    private Date createTime;

    /** 创建人 */
    @ApiModelProperty("创建人")
    @TableField("CREATE_USER")
    private String createUser;

    /** 更新时间 */
    @ApiModelProperty("更新时间")
    @TableField("UPDATE_TIME")
    private Date updateTime;

    /** 更新人 */
    @ApiModelProperty("更新人")
    @TableField("UPDATE_USER")
    private String updateUser;

}