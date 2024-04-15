package com.tadpole.cloud.product.api.entity;

import com.baomidou.mybatisplus.annotation.TableField;

import java.io.Serializable;

/**
 * 流程相关基础实体，业务流程实体需集成此类
 *
 * @author xuyuxiang
 * @Date 2019/8/22 21:59
 */
public class ActBaseEntity implements Serializable {
    /**
     * 实例id
     */
    @TableField("process_instance_id")
    private String processInstanceId;

    /**
     * 申请人id
     */
    @TableField("apply_user_id")
    private Long applyUserId;

    /**
     * 申请时间
     */
    @TableField("apply_date")
    private String applyDate;

    /**
     * 申请人姓名
     */
    @TableField(exist = false)
    private String applyUserName;

    /**
     * 当前审批状态(0 未开始 1审批中 2审批完成）
     */
    @TableField(exist = false)
    private Integer approveStatus;

    /**
     * 当前审批环节
     */
    @TableField(exist = false)
    private String approveProcess;

    /**
     * 当前审批人
     */
    @TableField(exist = false)
    private String approveUserName;

    public String getProcessInstanceId() {
        return processInstanceId;
    }

    public void setProcessInstanceId(String processInstanceId) {
        this.processInstanceId = processInstanceId;
    }

    public Long getApplyUserId() {
        return applyUserId;
    }

    public void setApplyUserId(Long applyUserId) {
        this.applyUserId = applyUserId;
    }

    public String getApplyDate() {
        return applyDate;
    }

    public void setApplyDate(String applyDate) {
        this.applyDate = applyDate;
    }

    public String getApplyUserName() {
        return applyUserName;
    }

    public void setApplyUserName(String applyUserName) {
        this.applyUserName = applyUserName;
    }

    public Integer getApproveStatus() {
        return approveStatus;
    }

    public void setApproveStatus(Integer approveStatus) {
        this.approveStatus = approveStatus;
    }

    public String getApproveProcess() {
        return approveProcess;
    }

    public void setApproveProcess(String approveProcess) {
        this.approveProcess = approveProcess;
    }

    public String getApproveUserName() {
        return approveUserName;
    }

    public void setApproveUserName(String approveUserName) {
        this.approveUserName = approveUserName;
    }
}
