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
 * 提案-定品 实体类
 * </p>
 *
 * @author S20190096
 * @since 2024-03-13
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("RD_CUST_PRODUCT")
@ExcelIgnoreUnannotated
public class RdCustProduct implements Serializable {

    private static final long serialVersionUID = 1L;


    /** 系统信息-定品申请编号 */
   @TableId(value = "SYS_CP_CODE", type = IdType.ASSIGN_ID)
    private String sysCpCode;

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

    /** 系统信息-申请状态 值域{"待提交","待审批","已审批"} */
    @TableField("SYS_CP_STATUS")
    private String sysCpStatus;

    /** 单据联系-提案编号 */
    @TableField("SYS_TA_CODE")
    private String sysTaCode;

    /** 申请信息-定品申请名称 */
    @TableField("SYS_CP_NAME")
    private String sysCpName;

    /** 申请信息-提案已用时 */
    @TableField("SYS_CP_PU_TIMES")
    private BigDecimal sysCpPuTimes;

    /** 申请信息-购样任务次数 */
    @TableField("SYS_CP_ST_TIMES")
    private BigDecimal sysCpStTimes;

    /** 申请信息-定制任务次数 */
    @TableField("SYS_CP_CT_TIMES")
    private BigDecimal sysCpCtTimes;

    /** 申请信息-任务合计次数 */
    @TableField("SYS_CP_T_TOTAL_TIMES")
    private BigDecimal sysCpTTotalTimes;

    /** 申请信息-购样费用合计 */
    @TableField("SYS_CP_ST_FEE_AMOUNT")
    private BigDecimal sysCpStFeeAmount;

    /** 申请信息-定制费用合计 */
    @TableField("SYS_CP_CT_FEE_AMOUNT")
    private BigDecimal sysCpCtFeeAmount;

    /** 申请信息-已投入研发费合计 */
    @TableField("SYS_CP_TOTAL_FEE_AMOUNT")
    private BigDecimal sysCpTotalFeeAmount;

    /** 申请信息-购样数量 */
    @TableField("SYS_CP_ST_QTY")
    private BigDecimal sysCpStQty;

    /** 申请信息-定制数量 */
    @TableField("SYS_CP_CT_QTY")
    private BigDecimal sysCpCtQty;

    /** 申请信息-样品数量合计 */
    @TableField("SYS_CP_S_TOTAL_QTY")
    private BigDecimal sysCpSTotalQty;

    /** 申请信息-无效样品数量合计 */
    @TableField("SYS_CP_IS_QTY")
    private BigDecimal sysCpIsQty;

    /** 申请信息-有效样品数量合计 */
    @TableField("SYS_CP_VS_QTY")
    private BigDecimal sysCpVsQty;

    /** 申请信息-定品审批通过数量合计 */
    @TableField("SYS_CP_APPR_QTY")
    private BigDecimal sysCpApprQty;

    /** 申请信息-定品申请数量合计 */
    @TableField("SYS_CP_APP_QTY")
    private BigDecimal sysCpAppQty;

    /** 申请信息-销售规划文档 */
    @TableField("SYS_CP_SP_DOC")
    private String sysCpSpDoc;

    /** 申请信息-申请时间 */
    @TableField("SYS_CP_APP_DATE")
    private Date sysCpAppDate;

    /** 申请信息-申请员工姓名 */
    @TableField("SYS_CP_APP_PER_NAME")
    private String sysCpAppPerName;

    /** 申请信息-申请员工编号 */
    @TableField("SYS_CP_APP_PER_CODE")
    private String sysCpAppPerCode;

    /** 审批信息-审批结果 值域{"继续选品","同意落地","研发撤销"} */
    @TableField("SYS_CP_APPR_RESULT")
    private String sysCpApprResult;

    /** 审批信息-审批说明 */
    @TableField("SYS_CP_APPR_EXPLAIN")
    private String sysCpApprExplain;

    /** 审批信息-审批时间 */
    @TableField("SYS_CP_APPR_DATE")
    private Date sysCpApprDate;

    /** 审批信息-审批员工姓名 */
    @TableField("SYS_CP_APPR_PER_NAME")
    private String sysCpApprPerName;

    /** 审批信息-审批员工编号 */
    @TableField("SYS_CP_APPR_PER_CODE")
    private String sysCpApprPerCode;


    /** 审批信息-审批结果 值域{"继续选品","同意落地","研发撤销"} */
    @TableField("SYS_CP_APPR_RESULT2")
    private String sysCpApprResult2;

    /** 审批信息-审批说明 */
    @TableField("SYS_CP_APPR_EXPLAIN2")
    private String sysCpApprExplain2;

    /** 审批信息-审批时间 */
    @TableField("SYS_CP_APPR_DATE2")
    private Date sysCpApprDate2;

    /** 审批信息-审批员工姓名 */
    @TableField("SYS_CP_APPR_PER_NAME2")
    private String sysCpApprPerName2;

    /** 审批信息-审批员工编号 */
    @TableField("SYS_CP_APPR_PER_CODE2")
    private String sysCpApprPerCode2;

}