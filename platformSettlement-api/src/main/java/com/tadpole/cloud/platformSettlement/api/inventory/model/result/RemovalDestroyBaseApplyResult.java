package com.tadpole.cloud.platformSettlement.api.inventory.model.result;

import cn.stylefeng.guns.cloud.model.validator.BaseValidatingParam;
import com.tadpole.cloud.platformSettlement.api.inventory.entity.InventoryRejectVO;
import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
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
* @since 2022-05-25
*/
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ExcelIgnoreUnannotated
public class RemovalDestroyBaseApplyResult implements Serializable, BaseValidatingParam {

    private static final long serialVersionUID = 1L;

    /** ID */
    @TableId(value = "ID", type = IdType.AUTO)
    private BigDecimal id;

    /** 流程编号 */
    @ApiModelProperty("APPLY_CODE")
    private String applyCode;

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
    private Date applyDate;

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

    /** 审批节点状态  0：初始导入，1：申请，2：中心最高负责人审批，3：财务审批，4：总经理审批，5：归档 */
    @ApiModelProperty("AUDIT_STATUS")
    private String auditStatus;

    /** 审批人工号 */
    @ApiModelProperty("AUDIT_ACCOUNT")
    private String auditAccount;

    /** 审批人姓名 */
    @ApiModelProperty("AUDIT_NAME")
    private String auditName;

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

    @ApiModelProperty("驳回意见")
    private List<InventoryRejectVO> rejectNode;
}