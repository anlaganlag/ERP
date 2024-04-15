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
 * 提案-开模费付款申请 实体类
 * </p>
 *
 * @author S20190096
 * @since 2023-12-22
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("RD_MOLD_OPEN_PFA")
@ExcelIgnoreUnannotated
public class RdMoldOpenPfa implements Serializable {

    private static final long serialVersionUID = 1L;


    /** 系统信息-开模付款申请编号 */
   @TableId(value = "SYS_MOF_CODE", type = IdType.ASSIGN_ID)
    private String sysMofCode;

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

    /** 系统信息-申请状态 值域{"待提交","待审核","待打印","待付款","待上传票据","待复核","已归档"} */
    @TableField("SYS_MOF_STATUS")
    private String sysMofStatus;

    /** 单据联系-产品线编号 */
    @TableField("SYS_PL_CODE")
    private String sysPlCode;

    /** 单据联系-SPU */
    @TableField("SYS_SPU")
    private String sysSpu;

    /** 单据联系-提案编号 */
    @TableField("SYS_TA_CODE")
    private String sysTaCode;

    /** 单据联系-拿样任务编号 */
    @TableField("SYS_TS_TASK_CODE")
    private String sysTsTaskCode;

    /** 单据联系-定制反馈编号 */
    @TableField("SYS_CUST_FEBK_CODE")
    private String sysCustFebkCode;

    /** 系统信息-费用申请编号 */
    @TableField("SYS_FEE_APP_CODE")
    private String sysFeeAppCode;

    /** 单据联系-开模付款申请编号(首款) */
    @TableField("SYS_FEE_APP_CODE_FIRST")
    private String sysFeeAppCodeFirst;

    /** 付款申请信息-产品经理编号 */
    @TableField("SYS_PM_PER_CODE")
    private String sysPmPerCode;

    /** 付款申请信息-产品经理姓名 */
    @TableField("SYS_PM_PER_NAME")
    private String sysPmPerName;

    /** 付款申请信息-供应商编码 */
    @TableField("SYS_CF_SUPPLIER_NUM")
    private String sysCfSupplierNum;

    /** 付款申请信息-供应商名称 */
    @TableField("SYS_CF_SUPPLIER_NAME")
    private String sysCfSupplierName;

    /** 付款申请信息-采购负责人编号 */
    @TableField("SYS_CF_PUR_PER_CODE")
    private String sysCfPurPerCode;

    /** 付款申请信息-采购负责人姓名 */
    @TableField("SYS_CF_PUR_PER_NAME")
    private String sysCfPurPerName;

    /** 付款申请信息-提供票据类型 */
    @TableField("SYS_CF_TICKET_TYPE")
    private String sysCfTicketType;

    /** 付款申请信息-供应商-账户类型 */
    @TableField("SYS_CF_ACCOUNT_TYPE")
    private String sysCfAccountType;

    /** 付款申请信息-供应商-收款方式 */
    @TableField("SYS_CF_PAY_METHOD")
    private String sysCfPayMethod;

    /** 付款申请信息-供应商-账号户名 */
    @TableField("SYS_CF_ACCOUNT_NAME")
    private String sysCfAccountName;

    /** 付款申请信息-供应商-银行账号 */
    @TableField("SYS_CF_BANK_ACCOUNT")
    private String sysCfBankAccount;

    /** 付款申请信息-供应商-开户行 */
    @TableField("SYS_CF_BANK_NAME")
    private String sysCfBankName;

    /** 付款申请信息-供应商-支付宝账号 */
    @TableField("SYS_CF_ALIPAY_ACCOUNT")
    private String sysCfAlipayAccount;

    /** 付款申请信息-合同金额 */
    @TableField("SYS_SA_CONTRACT_AMOUNT")
    private BigDecimal sysSaContractAmount;

    /** 付款申请信息-盖章合同文件 */
    @TableField("SYS_SA_STA_CONTRACT_DOC")
    private String sysSaStaContractDoc;

    /** 付款申请信息-付款申请-标题 ="CW28-付款单-【采购负责人】-【年】-【月】-【日】" */
    @TableField("SYS_MOF_TITLE")
    private String sysMofTitle;

    /** 付款申请信息-付款申请-摘要 ="【提案编号】>【产品名称】 开模费用-【付款金额】 【付款方式】" */
    @TableField("SYS_MOF_SUMMARY")
    private String sysMofSummary;

    /** 付款申请信息-提交时间 */
    @TableField("SYS_MOF_SUB_DATE")
    private Date sysMofSubDate;

    /** 付款申请信息-提交人编号 */
    @TableField("SYS_MOF_SUB_PC")
    private String sysMofSubPc;

    /** 付款申请信息-提交人姓名 */
    @TableField("SYS_MOF_SUB_PN")
    private String sysMofSubPn;

    /** 付款信息-付款账号类型 */
    @TableField("SYS_MOF_PAY_AT")
    private String sysMofPayAt;

    /** 付款信息-付款账户 */
    @TableField("SYS_MOF_PAY_ACCOUNT")
    private String sysMofPayAccount;

    /** 付款信息-付款账户户名 */
    @TableField("SYS_MOF_PAY_AN")
    private String sysMofPayAn;

    /** 付款信息-付款账户开户行 */
    @TableField("SYS_MOF_PAY_AOB")
    private String sysMofPayAob;

    /** 审核信息-审核时间 */
    @TableField("SYS_MOF_AUDIT_DATE")
    private Date sysMofAuditDate;

    /** 审核信息-审核结果 */
    @TableField("SYS_MOF_AUDIT_RESULT")
    private String sysMofAuditResult;

    /** 审核信息-审核说明 */
    @TableField("SYS_MOF_AUDIT_EXPLAIN")
    private String sysMofAuditExplain;

    /** 审核信息-审核员工姓名 */
    @TableField("SYS_MOF_AUDIT_PER_NAME")
    private String sysMofAuditPerName;

    /** 审核信息-审核员工编号 */
    @TableField("SYS_MOF_AUDIT_PER_CODE")
    private String sysMofAuditPerCode;

    /** 打印信息-打印时间 */
    @TableField("SYS_MOF_PRINT_DATE")
    private Date sysMofPrintDate;

    /** 打印信息-打印次数 */
    @TableField("SYS_MOF_PRINT_COUNT")
    private BigDecimal sysMofPrintCount;

    /** 打印信息-打印员工姓名 */
    @TableField("SYS_MOF_PRINT_PER_NAME")
    private String sysMofPrintPerName;

    /** 打印信息-打印员工编号 */
    @TableField("SYS_MOF_PRINT_PER_CODE")
    private String sysMofPrintPerCode;

    /** 付款信息-付款日期 */
    @TableField("SYS_MOF_PAY_DATE")
    private Date sysMofPayDate;

    /** 付款申请信息-付款申请-付款方式 */
    @TableField("SYS_MOF_PAY_METHOD")
    private String sysMofPayMethod;

    /** 付款申请信息-付款申请-实际付款金额 */
    @TableField("SYS_MOF_PAY_AMOUNT")
    private BigDecimal sysMofPayAmount;

    /** 付款申请信息-付款申请-付款说明 */
    @TableField("SYS_MOF_PAY_EXPLAIN")
    private String sysMofPayExplain;

    /** 付款申请信息-付款申请-付款公司 */
    @TableField("SYS_MOF_PAY_COMP")
    private String sysMofPayComp;

    /** 付款信息-付款账号性质 */
    @TableField("SYS_MOF_PAY_AP")
    private String sysMofPayAp;

    /** 付款信息-付款登记时间 */
    @TableField("SYS_MOF_PAY_RD")
    private Date sysMofPayRd;

    /** 付款信息-付款登记员工姓名 */
    @TableField("SYS_MOF_PAY_R_PER_NAME")
    private String sysMofPayRPerName;

    /** 付款信息-付款登记员工编号 */
    @TableField("SYS_MOF_PAY_R_PER_CODE")
    private String sysMofPayRPerCode;

    /** 票据信息-发票号码 */
    @TableField("SYS_MOF_INVOICE_NUM")
    private String sysMofInvoiceNum;

    /** 票据信息-票据文件 */
    @TableField("SYS_MOF_TICKET_FILE")
    private String sysMofTicketFile;

    /** 票据信息-票据上传时间 */
    @TableField("SYS_MOF_TICKET_UD")
    private Date sysMofTicketUd;

    /** 票据信息-票据上传员工姓名 */
    @TableField("SYS_MOF_TICKET_UPN")
    private String sysMofTicketUpn;

    /** 票据信息-票据上传员工编号 */
    @TableField("SYS_MOF_TICKET_UPC")
    private String sysMofTicketUpc;

    /** 复核信息-复核时间 */
    @TableField("SYS_MOF_REVIEW_DATE")
    private Date sysMofReviewDate;

    /** 复核信息-复核结果 */
    @TableField("SYS_MOF_REVIEW_RESULT")
    private String sysMofReviewResult;

    /** 复核信息-复核说明 */
    @TableField("SYS_MOF_REVIEW_EXPLAIN")
    private String sysMofReviewExplain;

    /** 复核信息-复核员工姓名 */
    @TableField("SYS_MOF_REVIEW_PER_NAME")
    private String sysMofReviewPerName;

    /** 复核信息-复核员工编号 */
    @TableField("SYS_MOF_REVIEW_PER_CODE")
    private String sysMofReviewPerCode;

    /** 归档信息-归档时间 */
    @TableField("SYS_MOF_ARCHIVE_DATE")
    private Date sysMofArchiveDate;

}