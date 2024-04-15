package com.tadpole.cloud.platformSettlement.api.inventory.model.result;

import cn.stylefeng.guns.cloud.model.validator.BaseValidatingParam;
import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModelProperty;
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
* @since 2022-05-24
*/
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ExcelIgnoreUnannotated
public class ApplyConfigResult implements Serializable, BaseValidatingParam {

    private static final long serialVersionUID = 1L;

    /** ID */
    @TableId(value = "ID", type = IdType.AUTO)
    private BigDecimal id;

    /** 申请流程名称 */
    @ApiModelProperty("APPLY_NAME")
    private String applyName;

    /** 部门 */
    @ApiModelProperty("DEPARTMENT")
    private String department;

    /** 经营分析人工号 */
    @ApiModelProperty("ANALYSIS_ACCOUNT")
    private String analysisAccount;

    /** 经营分析人姓名 */
    @ApiModelProperty("ANALYSIS_NAME")
    private String analysisName;

    /** 第一审批人工号 */
    @ApiModelProperty("FIRST_AUDIT_ACCOUNT")
    private String firstAuditAccount;

    /** 第一审批人姓名 */
    @ApiModelProperty("FIRST_AUDIT_NAME")
    private String firstAuditName;

    /** 第二审批人工号 */
    @ApiModelProperty("SECOND_AUDIT_ACCOUNT")
    private String secondAuditAccount;

    /** 第二审批人姓名 */
    @ApiModelProperty("SECOND_AUDIT_NAME")
    private String secondAuditName;

    /** 第一审批抄送人员工号 */
    @ApiModelProperty("FIRST_SEARCH_ACCOUNT")
    private String firstSearchAccount;

    /** 第一审批抄送人员姓名 */
    @ApiModelProperty("FIRST_SEARCH_NAME")
    private String firstSearchName;

    /** 归档抄送人员工号 */
    @ApiModelProperty("SECOND_SEARCH_ACCOUNT")
    private String secondSearchAccount;

    /** 归档抄送人员姓名 */
    @ApiModelProperty("SECOND_SEARCH_NAME")
    private String secondSearchName;

    /** 申请流程状态 0：禁用，1：启用 */
    @ApiModelProperty("APPLY_STATUS")
    private String applyStatus;

    /** 创建时间 */
    @ApiModelProperty("CREATE_TIME")
    private Date createTime;

    /** 创建人 */
    @ApiModelProperty("CREATE_BY")
    private String createBy;

    /** 更新时间 */
    @ApiModelProperty("UPDATE_TIME")
    private Date updateTime;

    /** 更新人 */
    @ApiModelProperty("UPDATE_BY")
    private String updateBy;

}