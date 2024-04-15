package com.tadpole.cloud.platformSettlement.api.inventory.model.params;

import cn.stylefeng.guns.cloud.model.web.request.BaseRequest;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.tadpole.cloud.platformSettlement.api.inventory.entity.RemovalDestroyApply;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

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
public class RemovalDestroyBaseApplyParam extends BaseRequest implements Serializable {

    private static final long serialVersionUID = 1L;

    /** ID */
    @TableId(value = "ID", type = IdType.AUTO)
    private BigDecimal id;

    /** 申请标题 */
    @ApiModelProperty("APPLY_TITLE")
    private String applyTitle;

    /** 申请人姓名 */
    @ApiModelProperty("APPLY_USER_NAME")
    private String applyUserName;

    /** 流程名称 */
    @ApiModelProperty("APPLY_NAME")
    private String applyName;

    /** 申请人工号 */
    @ApiModelProperty("APPLY_USER_ACCOUNT")
    private String applyUserAccount;

    /** 申请部门 */
    @ApiModelProperty("APPLY_DEPARTMENT")
    private String applyDepartment;

    /** 申请时间 */
    @ApiModelProperty("APPLY_DATE")
    private String applyDate;

    /** 申请备注 */
    @ApiModelProperty("INCOME_REMARK")
    private String incomeRemark;

    /** 摊销期 */
    @ApiModelProperty("SHARE_NUM")
    private BigDecimal shareNum;

    /** 银行账户 */
    @ApiModelProperty("BANK_ACCOUNT")
    private String bankAccount;

    /** 银行名称 */
    @ApiModelProperty("BANK_NAME")
    private String bankName;

    /** 账号所属 */
    @ApiModelProperty("BANK_USER")
    private String bankUser;

    /** 银行账号 */
    @ApiModelProperty("BANK_USER_ACCOUNT")
    private String bankUserAccount;

    /** 确认收款金额 */
    @ApiModelProperty("INCOME_AMOUMT")
    private BigDecimal incomeAmoumt;

    /** 币别 */
    @ApiModelProperty("CURRENCY")
    private String currency;

    /** 审批节点状态  1：申请，2：中心最高负责人审批，3：财务审批，4：总经理审批，5：归档 */
    @ApiModelProperty("AUDIT_STATUS")
    private String auditStatus;

    /** 流程编号 */
    @ApiModelProperty("APPLY_CODE")
    private String applyCode;

    /** 驳回再提交节点 默认0：逐级审批 */
    @ApiModelProperty("REJECT_SUBMIT_NODE")
    private String rejectSubmitNode;

    /** 驳回意见 */
    @ApiModelProperty("REJECT_REMARK")
    private String rejectRemark;

    /** 保存或提交 */
    @ApiModelProperty("保存或提交标识 saveMark")
    private String saveMark;

    /** 申请流程类型 */
    @ApiModelProperty("申请流程类型 applyType")
    private String applyType;

    /** 库存明细集合 */
    @ApiModelProperty("集合明细 detailList")
    List<RemovalDestroyApply> detailList;
}