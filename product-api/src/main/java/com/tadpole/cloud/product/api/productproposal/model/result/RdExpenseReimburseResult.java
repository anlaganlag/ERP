package com.tadpole.cloud.product.api.productproposal.model.result;

import java.math.BigDecimal;
import cn.stylefeng.guns.cloud.model.validator.BaseValidatingParam;
import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import java.util.List;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

/**
 * <p>
 * 提案-研发费报销 出参类
 * </p>
 *
 * @author S20190096
 * @since 2024-02-27
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ExcelIgnoreUnannotated
@TableName("RD_EXPENSE_REIMBURSE")
public class RdExpenseReimburseResult implements Serializable, BaseValidatingParam {

    private static final long serialVersionUID = 1L;



   @TableId(value = "SYS_SAEA_CODE", type = IdType.AUTO)
    private String sysSaeaCode;


    @ApiModelProperty("系统信息-创建时间")
    private Date sysCDate;


    @ApiModelProperty("系统信息-最后更新时间")
    private Date sysLDate;


    @ApiModelProperty("系统信息-部门编号")
    private String sysDeptCode;


    @ApiModelProperty("系统信息-部门名称")
    private String sysDeptName;


    @ApiModelProperty("系统信息-员工编号")
    private String sysPerCode;


    @ApiModelProperty("系统信息-员工姓名")
    private String sysPerName;


    @ApiModelProperty("系统信息-报销申请状态 值域{'待提交','待审核','待打样','待付款','已归档'}")
    private String sysSaeaStatus;


    @ApiModelProperty("申请信息-支付账户")
    private String sysSaeaAccount;


    @ApiModelProperty("申请信息-实时金额")
    private BigDecimal sysSaeaRtAmount;


    @ApiModelProperty("申请信息-报销范围(开始)")
    private Date sysSaeaStartDate;


    @ApiModelProperty("申请信息-报销范围(结束)")
    private Date sysSaeaEndDate;


    @ApiModelProperty("申请信息-报销单标题")
    private String sysSaeaTitle;


    @ApiModelProperty("申请信息-报销单摘要")
    private String sysSaeaSummary;


    @ApiModelProperty("申请信息-购样费用合计")
    private BigDecimal sysSaeaBuyAmount;


    @ApiModelProperty("申请信息-打样费用合计")
    private BigDecimal sysSaeaSampleAmount;


    @ApiModelProperty("申请信息-样品退货数量")
    private BigDecimal sysSaeaSampleRefQty;


    @ApiModelProperty("申请信息-样品退货退款合计")
    private BigDecimal sysSaeaRefAmount;


    @ApiModelProperty("申请信息-报销金额")
    private BigDecimal sysSaeaAmount;


    @ApiModelProperty("申请信息-支付凭证上传")
    private String sysSaeaIncAndExpVoucher;


    @ApiModelProperty("申请信息-账户余额截图")
    private String sysSaeaAccBalance;


    @ApiModelProperty("申请信息-收款账号")
    private String sysSaeaRecAccount;


    @ApiModelProperty("申请信息-收款人")
    private String sysSaeaRecPn;


    @ApiModelProperty("申请信息-收款开户行")
    private String sysSaeaRecAn;


    @ApiModelProperty("申请信息-报销员工姓名")
    private String sysSaeaPerName;


    @ApiModelProperty("申请信息-报销员工编号")
    private String sysSaeaPerCode;


    @ApiModelProperty("申请信息-报销申请时间")
    private Date sysSaeaAppDate;


    @ApiModelProperty("审核信息-审核结果 值域{'通过','退回'}")
    private String sysSaeaAuditResult;


    @ApiModelProperty("审核信息-退回说明")
    private String sysSaeaAuditExplain;


    @ApiModelProperty("审核信息-审核时间")
    private Date sysSaeaAuditDate;


    @ApiModelProperty("审核信息-审核员工姓名")
    private String sysSaeaAuditPn;


    @ApiModelProperty("审核信息-审核员工编号")
    private String sysSaeaAuditPc;


    @ApiModelProperty("打印信息-首次打印时间")
    private Date sysSaeaPrintDate;


    @ApiModelProperty("打印信息-首次打印员工姓名")
    private String sysSaeaPrintPn;


    @ApiModelProperty("打印信息-首次打印员工编号")
    private String sysSaeaPrintPc;


    @ApiModelProperty("打印信息-打印次数")
    private BigDecimal sysSaeaPrintQty;


    @ApiModelProperty("付款信息-实际付款日期")
    private Date sysSaeaPayDate;


    @ApiModelProperty("付款信息-实际付款金额")
    private BigDecimal sysSaeaPayAmount;


    @ApiModelProperty("付款信息-报销费用差额说明")
    private String sysSaeaBalanceExplain;


    @ApiModelProperty("付款信息-付款公司")
    private String sysSaeaPayComp;


    @ApiModelProperty("付款信息-付款账户性质 默认值{'银行卡'}")
    private String sysSaeaPayAp;


    @ApiModelProperty("付款信息-付款账户归属 值域{'公司账户','个人账户'}")
    private String sysSaeaPayAo;


    @ApiModelProperty("付款信息-付款账户类型")
    private String sysSaeaPayAt;


    @ApiModelProperty("付款信息-付款账户")
    private String sysSaeaPayAccount;


    @ApiModelProperty("付款信息-付款账户户名")
    private String sysSaeaPayAn;


    @ApiModelProperty("付款信息-付款开户行")
    private String sysSaeaPayAob;


    @ApiModelProperty("付款信息-付款登记时间")
    private Date sysSaeaPayRd;


    @ApiModelProperty("付款信息-付款登记员工姓名")
    private String sysSaeaPayRpn;


    @ApiModelProperty("付款信息-付款登记员工编号")
    private String sysSaeaPayRpc;


    @ApiModelProperty("归档信息-归档时间")
    private Date sysSaeaArchiveDate;

    @ApiModelProperty("研发费报销明细集合")
    private List<RdExpenseReimburseDetResult> rdExpenseReimburseDetResultList;
}
