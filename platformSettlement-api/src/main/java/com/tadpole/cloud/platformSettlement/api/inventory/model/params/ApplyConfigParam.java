package com.tadpole.cloud.platformSettlement.api.inventory.model.params;

import cn.stylefeng.guns.cloud.model.web.request.BaseRequest;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

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
public class ApplyConfigParam extends BaseRequest implements Serializable {

    private static final long serialVersionUID = 1L;

    /** ID */
    @TableId(value = "ID", type = IdType.AUTO)
    private BigDecimal id;

    /** 申请流程名称 */
    @ApiModelProperty("申请流程名称APPLY_NAME")
    private String applyName;

    /** 部门 */
    @ApiModelProperty("部门DEPARTMENT")
    private String department;

    /** 经营分析人工号 */
    @ApiModelProperty("经营分析人工号ANALYSIS_ACCOUNT")
    private List analysisAccount;

    /** 经营分析人姓名 */
    @ApiModelProperty("经营分析人姓名ANALYSIS_NAME")
    private List analysisName;

    /** 第一审批人工号 */
    @ApiModelProperty("第一审批人工号FIRST_AUDIT_ACCOUNT")
    private String firstAuditAccount;

    /** 第一审批人姓名 */
    @ApiModelProperty("第一审批人姓名FIRST_AUDIT_NAME")
    private String firstAuditName;

    /** 第二审批人工号 */
    @ApiModelProperty("第二审批人工号SECOND_AUDIT_ACCOUNT")
    private String secondAuditAccount;

    /** 第二审批人姓名 */
    @ApiModelProperty("第二审批人姓名SECOND_AUDIT_NAME")
    private String secondAuditName;

    /** 第一审批抄送人员工号 */
    @ApiModelProperty("第一审批抄送人员工号FIRST_SEARCH_ACCOUNT")
    private List firstSearchAccount;

    /** 第一审批抄送人员姓名 */
    @ApiModelProperty("第一审批抄送人员姓名FIRST_SEARCH_NAME")
    private List firstSearchName;

    /** 归档抄送人员工号 */
    @ApiModelProperty("归档抄送人员工号SECOND_SEARCH_ACCOUNT")
    private List secondSearchAccount;

    /** 归档抄送人员姓名 */
    @ApiModelProperty("归档抄送人员姓名SECOND_SEARCH_NAME")
    private List secondSearchName;

    /** 申请流程状态 0：禁用，1：启用 */
    @ApiModelProperty("申请流程状态APPLY_STATUS")
    private String applyStatus;

    /** 创建时间 */
    @ApiModelProperty("创建时间CREATE_TIME")
    private Date createTime;

    /** 创建人 */
    @ApiModelProperty("创建人CREATE_BY")
    private String createBy;

    /** 更新时间 */
    @ApiModelProperty("更新时间UPDATE_TIME")
    private Date updateTime;

    /** 更新人 */
    @ApiModelProperty("更新人UPDATE_BY")
    private String updateBy;

}