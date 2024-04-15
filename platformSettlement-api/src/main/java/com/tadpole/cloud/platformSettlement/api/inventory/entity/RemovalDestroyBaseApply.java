package com.tadpole.cloud.platformSettlement.api.inventory.entity;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
* <p>
*
* </p>
*
* @author cyt
* @since 2022-05-25
*/
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("REMOVAL_DESTROY_BASE_APPLY")
@ExcelIgnoreUnannotated
public class RemovalDestroyBaseApply implements Serializable {

    private static final long serialVersionUID = 1L;

    /** ID */
    @TableId(value = "ID", type = IdType.AUTO)
    private BigDecimal id;

    /** 流程编号 */
    @TableField("APPLY_CODE")
    private String applyCode;

    /** 申请标题 */
    @TableField("APPLY_TITLE")
    private String applyTitle;

    /** 申请人姓名 */
    @TableField("APPLY_USER_NAME")
    private String applyUserName;

    /** 流程名称 */
    @TableField("APPLY_NAME")
    private String applyName;

    /** 申请人工号 */
    @TableField("APPLY_USER_ACCOUNT")
    private String applyUserAccount;

    /** 申请部门 */
    @TableField("APPLY_DEPARTMENT")
    private String applyDepartment;

    /** 申请时间 */
    @TableField("APPLY_DATE")
    private Date applyDate;

    /** 申请备注 */
    @TableField("INCOME_REMARK")
    private String incomeRemark;

    /** 摊销期 */
    @TableField("SHARE_NUM")
    private BigDecimal shareNum;

    /** 银行账户 */
    @TableField("BANK_ACCOUNT")
    private String bankAccount;

    /** 银行名称 */
    @TableField("BANK_NAME")
    private String bankName;

    /** 账号所属 */
    @TableField("BANK_USER")
    private String bankUser;

    /** 银行账号 */
    @TableField("BANK_USER_ACCOUNT")
    private String bankUserAccount;

    /** 确认收款金额 */
    @TableField("INCOME_AMOUMT")
    private BigDecimal incomeAmoumt;

    /** 币别 */
    @TableField("CURRENCY")
    private String currency;

    /** 审批节点状态  0：初始导入，1：申请，2：中心最高负责人审批，3：财务审批，4：总经理审批，5：归档 */
    @TableField("AUDIT_STATUS")
    private String auditStatus;

    /** 审批人工号 */
    @TableField("AUDIT_ACCOUNT")
    private String auditAccount;

    /** 审批人姓名 */
    @TableField("AUDIT_NAME")
    private String auditName;

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

    /** 驳回再提交节点 默认0：逐级审批 */
    @TableField("REJECT_SUBMIT_NODE")
    private String rejectSubmitNode;

    /** 驳回意见 */
    @TableField("REJECT_REMARK")
    private String rejectRemark;
}