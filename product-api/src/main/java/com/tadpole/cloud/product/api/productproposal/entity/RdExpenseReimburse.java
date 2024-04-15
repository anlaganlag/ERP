package com.tadpole.cloud.product.api.productproposal.entity;

import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import lombok.*;

/**
 * <p>
 * 提案-研发费报销 实体类
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
@TableName("RD_EXPENSE_REIMBURSE")
@ExcelIgnoreUnannotated
public class RdExpenseReimburse implements Serializable {

    private static final long serialVersionUID = 1L;


    /** 系统信息-报销申请编号 */
   @TableId(value = "SYS_SAEA_CODE", type = IdType.ASSIGN_ID)
    private String sysSaeaCode;

    /** 系统信息-创建时间 */
    @TableField("SYS_C_DATE")
    private Date sysCDate;

    /** 系统信息-最后更新时间 */
    @TableField("SYS_L_DATE")
    private Date sysLDate;

    /** 系统信息-部门编号 */
    @TableField("SYS_DEPT_CODE")
    private String sysDeptCode;

    /** 系统信息-部门名称 */
    @TableField("SYS_DEPT_NAME")
    private String sysDeptName;

    /** 系统信息-员工编号 */
    @TableField("SYS_PER_CODE")
    private String sysPerCode;

    /** 系统信息-员工姓名 */
    @TableField("SYS_PER_NAME")
    private String sysPerName;

    /** 系统信息-报销申请状态 值域{"待提交","待审核","待打样","待付款","已归档"} */
    @TableField("SYS_SAEA_STATUS")
    private String sysSaeaStatus;

    /** 申请信息-支付账户 */
    @TableField("SYS_SAEA_ACCOUNT")
    private String sysSaeaAccount;

    /** 申请信息-实时金额 */
    @TableField("SYS_SAEA_RT_AMOUNT")
    private BigDecimal sysSaeaRtAmount;

    /** 申请信息-报销范围(开始) */
    @TableField("SYS_SAEA_START_DATE")
    private Date sysSaeaStartDate;

    /** 申请信息-报销范围(结束) */
    @TableField("SYS_SAEA_END_DATE")
    private Date sysSaeaEndDate;

    /** 申请信息-报销单标题 */
    @TableField("SYS_SAEA_TITLE")
    private String sysSaeaTitle;

    /** 申请信息-报销单摘要 */
    @TableField("SYS_SAEA_SUMMARY")
    private String sysSaeaSummary;

    /** 申请信息-购样费用合计 */
    @TableField("SYS_SAEA_BUY_AMOUNT")
    private BigDecimal sysSaeaBuyAmount;

    /** 申请信息-打样费用合计 */
    @TableField("SYS_SAEA_SAMPLE_AMOUNT")
    private BigDecimal sysSaeaSampleAmount;

    /** 申请信息-样品退货数量 */
    @TableField("SYS_SAEA_SAMPLE_REF_QTY")
    private BigDecimal sysSaeaSampleRefQty;

    /** 申请信息-样品退货退款合计 */
    @TableField("SYS_SAEA_REF_AMOUNT")
    private BigDecimal sysSaeaRefAmount;

    /** 申请信息-报销金额 */
    @TableField("SYS_SAEA_AMOUNT")
    private BigDecimal sysSaeaAmount;

    /** 申请信息-支付凭证上传 */
    @TableField("SYS_SAEA_INC_AND_EXP_VOUCHER")
    private String sysSaeaIncAndExpVoucher;

    /** 申请信息-账户余额截图 */
    @TableField("SYS_SAEA_ACC_BALANCE")
    private String sysSaeaAccBalance;

    /** 申请信息-收款账号 */
    @TableField("SYS_SAEA_REC_ACCOUNT")
    private String sysSaeaRecAccount;

    /** 申请信息-收款人 */
    @TableField("SYS_SAEA_REC_PN")
    private String sysSaeaRecPn;

    /** 申请信息-收款开户行 */
    @TableField("SYS_SAEA_REC_AN")
    private String sysSaeaRecAn;

    /** 申请信息-报销员工姓名 */
    @TableField("SYS_SAEA_PER_NAME")
    private String sysSaeaPerName;

    /** 申请信息-报销员工编号 */
    @TableField("SYS_SAEA_PER_CODE")
    private String sysSaeaPerCode;

    /** 申请信息-报销申请时间 */
    @TableField("SYS_SAEA_APP_DATE")
    private Date sysSaeaAppDate;

    /** 审核信息-审核结果 值域{"通过","退回"} */
    @TableField("SYS_SAEA_AUDIT_RESULT")
    private String sysSaeaAuditResult;

    /** 审核信息-退回说明 */
    @TableField("SYS_SAEA_AUDIT_EXPLAIN")
    private String sysSaeaAuditExplain;

    /** 审核信息-审核时间 */
    @TableField("SYS_SAEA_AUDIT_DATE")
    private Date sysSaeaAuditDate;

    /** 审核信息-审核员工姓名 */
    @TableField("SYS_SAEA_AUDIT_PN")
    private String sysSaeaAuditPn;

    /** 审核信息-审核员工编号 */
    @TableField("SYS_SAEA_AUDIT_PC")
    private String sysSaeaAuditPc;

    /** 打印信息-首次打印时间 */
    @TableField("SYS_SAEA_PRINT_DATE")
    private Date sysSaeaPrintDate;

    /** 打印信息-首次打印员工姓名 */
    @TableField("SYS_SAEA_PRINT_PN")
    private String sysSaeaPrintPn;

    /** 打印信息-首次打印员工编号 */
    @TableField("SYS_SAEA_PRINT_PC")
    private String sysSaeaPrintPc;

    /** 打印信息-打印次数 */
    @TableField("SYS_SAEA_PRINT_QTY")
    private BigDecimal sysSaeaPrintQty;

    /** 付款信息-实际付款日期 */
    @TableField("SYS_SAEA_PAY_DATE")
    private Date sysSaeaPayDate;

    /** 付款信息-实际付款金额 */
    @TableField("SYS_SAEA_PAY_AMOUNT")
    private BigDecimal sysSaeaPayAmount;

    /** 付款信息-报销费用差额说明 */
    @TableField("SYS_SAEA_BALANCE_EXPLAIN")
    private String sysSaeaBalanceExplain;

    /** 付款信息-付款公司 */
    @TableField("SYS_SAEA_PAY_COMP")
    private String sysSaeaPayComp;

    /** 付款信息-付款账户性质 默认值{"银行卡"} */
    @TableField("SYS_SAEA_PAY_AP")
    private String sysSaeaPayAp;

    /** 付款信息-付款账户归属 值域{"公司账户","个人账户"} */
    @TableField("SYS_SAEA_PAY_AO")
    private String sysSaeaPayAo;

    /** 付款信息-付款账户类型 */
    @TableField("SYS_SAEA_PAY_AT")
    private String sysSaeaPayAt;

    /** 付款信息-付款账户 */
    @TableField("SYS_SAEA_PAY_ACCOUNT")
    private String sysSaeaPayAccount;

    /** 付款信息-付款账户户名 */
    @TableField("SYS_SAEA_PAY_AN")
    private String sysSaeaPayAn;

    /** 付款信息-付款开户行 */
    @TableField("SYS_SAEA_PAY_AOB")
    private String sysSaeaPayAob;

    /** 付款信息-付款登记时间 */
    @TableField("SYS_SAEA_PAY_RD")
    private Date sysSaeaPayRd;

    /** 付款信息-付款登记员工姓名 */
    @TableField("SYS_SAEA_PAY_RPN")
    private String sysSaeaPayRpn;

    /** 付款信息-付款登记员工编号 */
    @TableField("SYS_SAEA_PAY_RPC")
    private String sysSaeaPayRpc;

    /** 归档信息-归档时间 */
    @TableField("SYS_SAEA_ARCHIVE_DATE")
    private Date sysSaeaArchiveDate;

}