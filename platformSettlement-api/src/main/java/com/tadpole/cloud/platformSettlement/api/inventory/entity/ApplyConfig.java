package com.tadpole.cloud.platformSettlement.api.inventory.entity;

import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import lombok.*;

/**
* <p>
*
* </p>
*
* @author cyt
* @since 2022-05-24
*/
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("APPLY_CONFIG")
@ExcelIgnoreUnannotated
public class ApplyConfig implements Serializable {

    private static final long serialVersionUID = 1L;

    /** ID */
    @TableId(value = "ID", type = IdType.AUTO)
    private BigDecimal id;

    /** 申请流程名称 */
    @TableField("APPLY_NAME")
    private String applyName;

    /** 部门 */
    @TableField("DEPARTMENT")
    private String department;

    /** 经营分析人工号 */
    @TableField("ANALYSIS_ACCOUNT")
    private String analysisAccount;

    /** 经营分析人姓名 */
    @TableField("ANALYSIS_NAME")
    private String analysisName;

    /** 第一审批人工号 */
    @TableField("FIRST_AUDIT_ACCOUNT")
    private String firstAuditAccount;

    /** 第一审批人姓名 */
    @TableField("FIRST_AUDIT_NAME")
    private String firstAuditName;

    /** 第二审批人工号 */
    @TableField("SECOND_AUDIT_ACCOUNT")
    private String secondAuditAccount;

    /** 第二审批人姓名 */
    @TableField("SECOND_AUDIT_NAME")
    private String secondAuditName;

    /** 第一审批抄送人员工号 */
    @TableField("FIRST_SEARCH_ACCOUNT")
    private String firstSearchAccount;

    /** 第一审批抄送人员姓名 */
    @TableField("FIRST_SEARCH_NAME")
    private String firstSearchName;

    /** 归档抄送人员工号 */
    @TableField("SECOND_SEARCH_ACCOUNT")
    private String secondSearchAccount;

    /** 归档抄送人员姓名 */
    @TableField("SECOND_SEARCH_NAME")
    private String secondSearchName;

    /** 申请流程状态 0：禁用，1：启用 */
    @TableField("APPLY_STATUS")
    private String applyStatus;

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
}