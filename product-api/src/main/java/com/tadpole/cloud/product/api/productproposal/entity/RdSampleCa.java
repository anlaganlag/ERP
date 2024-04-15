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
 * 提案-定制申请 实体类
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
@TableName("RD_SAMPLE_CA")
@ExcelIgnoreUnannotated
public class RdSampleCa implements Serializable {

    private static final long serialVersionUID = 1L;


    /** 系统信息-费用申请编号 */
   @TableId(value = "SYS_FEE_APP_CODE", type = IdType.ASSIGN_ID)
    private String sysFeeAppCode;

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

    /** 系统信息-申请状态 值域{"待上传合同","合同待审核","待更新合同","待审批","待支付","已归档","已支付"} */
    @TableField("SYS_SA_AP_STATUS")
    private String sysSaApStatus;

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

    /** 申请信息-申请说明 */
    @TableField("SYS_SA_AP_EXPLAIN")
    private String sysSaApExplain;

    /** 申请信息-申请时间 */
    @TableField("SYS_SA_AP_DATE")
    private Date sysSaApDate;

    /** 申请信息-申请员工姓名 */
    @TableField("SYS_SA_AP_PER_NAME")
    private String sysSaApPerName;

    /** 申请信息-申请员工编号 */
    @TableField("SYS_SA_AP_PER_CODE")
    private String sysSaApPerCode;

    /** 合同信息-合同金额 */
    @TableField("SYS_SA_CONTRACT_AMOUNT")
    private BigDecimal sysSaContractAmount;

    /** 合同信息-合同文件(协议) */
    @TableField("SYS_SA_CONTRACT_DOC")
    private String sysSaContractDoc;

    /** 合同信息-合同备注(协议) */
    @TableField("SYS_SA_CONTRACT_REMARKS")
    private String sysSaContractRemarks;

    /** 合同信息-上传时间(协议) */
    @TableField("SYS_SA_CONTRACT_UD")
    private Date sysSaContractUd;

    /** 合同信息-上传员工姓名(协议) */
    @TableField("SYS_SA_CONTRACT_UPN")
    private String sysSaContractUpn;

    /** 合同信息-上传员工编号(协议) */
    @TableField("SYS_SA_CONTRACT_UPC")
    private String sysSaContractUpc;

    /** 合同审核信息-审核时间 */
    @TableField("SYS_SA_AUDIT_DATE")
    private Date sysSaAuditDate;

    /** 合同审核信息-审核结果 值域{"同意","不同意"} */
    @TableField("SYS_SA_AUDIT_RESULT")
    private String sysSaAuditResult;

    /** 合同审核信息-审核说明 */
    @TableField("SYS_SA_AUDIT_EXPLAIN")
    private String sysSaAuditExplain;

    /** 合同审核信息-审核员工姓名 */
    @TableField("SYS_SA_AUDIT_PER_NAME")
    private String sysSaAuditPerName;

    /** 合同审核信息-审核员工编号 */
    @TableField("SYS_SA_AUDIT_PER_CODE")
    private String sysSaAuditPerCode;

    /** 审批信息-审批结果 值域{"修订合同","同意","不同意"} */
    @TableField("SYS_SA_APP_RESULT")
    private String sysSaAppResult;

    /** 审批信息-审批备注 */
    @TableField("SYS_SA_APP_REMARKS")
    private String sysSaAppRemarks;

    /** 审批信息-审批时间 */
    @TableField("SYS_SA_APP_DATE")
    private Date sysSaAppDate;

    /** 审批信息-审批员工编号 */
    @TableField("SYS_SA_APP_PER_NAME")
    private String sysSaAppPerName;

    /** 审批信息-审批员工姓名 */
    @TableField("SYS_SA_APP_PER_CODE")
    private String sysSaAppPerCode;

    /** 支付信息-支付日期 */
    @TableField("SYS_SA_PAY_DATE")
    private Date sysSaPayDate;

    /** 支付信息-实际支付金额 */
    @TableField("SYS_SA_PAY_AMOUNT")
    private BigDecimal sysSaPayAmount;

    /** 支付信息-差额说明 */
    @TableField("SYS_SA_PAY_VD")
    private String sysSaPayVd;

    /** 支付信息-支付宝账户 */
    @TableField("SYS_SA_ALIPAY_ACCOUNT")
    private String sysSaAlipayAccount;

    /** 支付信息-支付宝账户户名 */
    @TableField("SYS_SA_ALIPAY_AN")
    private String sysSaAlipayAn;

    /** 支付信息-支付登记时间 */
    @TableField("SYS_SA_PAY_RD")
    private Date sysSaPayRd;

    /** 支付信息-支付登记员工姓名 */
    @TableField("SYS_SA_PAY_RPN")
    private String sysSaPayRpn;

    /** 支付信息-支付登记员工编号 */
    @TableField("SYS_SA_PAY_RPC")
    private String sysSaPayRpc;

    /** 合同审核信息-是否采用模板协议 值域{"是","否"} */
    @TableField("SYS_SA_AUDIT_IS_USE_TEMP")
    private String sysSaAuditIsUseTemp;
}