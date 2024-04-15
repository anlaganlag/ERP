package com.tadpole.cloud.supplyChain.api.logistics.model.result;

import cn.stylefeng.guns.cloud.model.validator.BaseValidatingParam;
import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
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
 * 物流费K3付款申请单明细 出参类
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
@ExcelIgnoreUnannotated
@TableName("LS_K3_PAYMENT_APPLY_DETAIL")
public class LsK3PaymentApplyDetailResult implements Serializable, BaseValidatingParam {

    private static final long serialVersionUID = 1L;

    @TableId(value = "ID", type = IdType.AUTO)
    private BigDecimal id;

    @ApiModelProperty("单据编号")
    private String paymentApplyNo;

    @ApiModelProperty("付费事项")
    private String paymentRemark;

    @ApiModelProperty("结算方式编码")
    private String settlementTypeCode;

    @ApiModelProperty("结算方式")
    private String settlementType;

    @ApiModelProperty("应付比例（%）")
    private String paymentPercent;

    @ApiModelProperty("付款用途编码")
    private String paymentUseCode;

    @ApiModelProperty("付款用途")
    private String paymentUse;

    @ApiModelProperty("对账金额")
    private BigDecimal settlementAmt;

    @ApiModelProperty("申请付款金额")
    private BigDecimal applyPaymentAmt;

    @ApiModelProperty("到期日")
    private Date expireDate;

    @ApiModelProperty("期望付款日期")
    private Date wishPaymentDate;

    @ApiModelProperty("开票日期")
    private Date billDate;

    @ApiModelProperty("发票代码")
    private String billCode;

    @ApiModelProperty("发票号码")
    private String billNo;

    @ApiModelProperty("备注")
    private String remark;

    @ApiModelProperty("未付款金额")
    private BigDecimal notPayAmt;

    @ApiModelProperty("对方银行账号")
    private String bankAccNo;

    @ApiModelProperty("对方银行账户名称")
    private String bankAccName;

    @ApiModelProperty("对方银行开户行")
    private String bankName;

    @ApiModelProperty("创建时间")
    private Date createTime;

    @ApiModelProperty("创建人")
    private String createUser;

    @ApiModelProperty("更新时间")
    private Date updateTime;

    @ApiModelProperty("更新人")
    private String updateUser;

}
