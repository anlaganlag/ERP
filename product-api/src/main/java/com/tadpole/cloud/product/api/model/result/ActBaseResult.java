package com.tadpole.cloud.product.api.model.result;

import lombok.Data;

/**
 * 流程相关基础返回结果集，业务流程结果集若需要返回流程相关字段可继承此类
 *
 * @Author xuyuxiang
 * @Date 2019/11/15 11:30
 **/
@Data
public class ActBaseResult {
    /**
     * 实例id
     */
    private String processInstanceId;

    /**
     * 申请人id
     */
    private Long applyUserId;

    /**
     * 申请时间
     */
    private String applyDate;

    /**
     * 申请人姓名
     */
    private String applyUserName;

    /**
     * 当前审批状态(0 未开始 1审批中 2审批完成）
     */
    private Integer approveStatus;

    /**
     * 当前审批环节
     */
    private String approveProcess;

    /**
     * 当前审批人
     */
    private String approveUserName;
}
