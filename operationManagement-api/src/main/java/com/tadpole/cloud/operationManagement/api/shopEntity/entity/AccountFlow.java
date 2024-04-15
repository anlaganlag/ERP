package com.tadpole.cloud.operationManagement.api.shopEntity.entity;

import java.io.Serializable;
import java.lang.*;
import lombok.*;
import java.math.BigDecimal;
import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import java.util.Date;

 /**
 * 账户流水;
 * @author : LSY
 * @date : 2023-11-10
 */
@TableName("ACCOUNT_FLOW")
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ExcelIgnoreUnannotated
public class AccountFlow implements Serializable{
 private static final long serialVersionUID = 1L;
 
    /** 数据ID */
    @ApiModelProperty(value = "数据ID")
    @TableId (value = "ID", type = IdType.ASSIGN_ID)
    @TableField("ID")
    private String id ;
 
    /** 业务流水ID;默认等于数据ID，保证每次请求唯一 */
    @ApiModelProperty(value = "业务流水ID")
    @TableField("Flow_id")
    private String flowId ;
 
    /** 业务流水类型(预存金额:deposit_amount,实时金额:real_time_amount) */
    @ApiModelProperty(value = "业务流水类型(预存金额:deposit_amount,实时金额:real_time_amount)")
    @TableField("Biz_flow_type")
    private String bizFlowType ;
 
    /** 业务数据来源 */
    @ApiModelProperty(value = "业务数据来源")
    @TableField("Biz_data_sources")
    private String bizDataSources ;
 
    /** 账户ID */
    @ApiModelProperty(value = "账户ID")
    @TableField("Account_Id")
    private String accountId ;
 
    /** 账户（账号） */
    @ApiModelProperty(value = "账户（账号）")
    @TableField("Account_No")
    private String accountNo ;
 
    /** 收付款账号：当inOrOut=1是付款账号，inOrOut=-1是收款账号 */
    @ApiModelProperty(value = "收付款账号：当inOrOut=1是付款账号，inOrOut=-1是收款账号")
    @TableField("Payment_Account")
    private String paymentAccount ;
 
    /** 付款时间 */
    @ApiModelProperty(value = "付款时间")
    @TableField("Payment_Time")
    private Date paymentTime ;
 
    /** 金额 */
    @ApiModelProperty(value = "金额")
    @TableField("Amount")
    private BigDecimal amount ;
 
    /** 账户余额 */
    @ApiModelProperty(value = "账户余额")
    @TableField("Account_balance")
    private BigDecimal accountBalance ;
 
    /** 币别;默认 人民币CNY */
    @ApiModelProperty(value = "币别")
    @TableField("Currency")
    private String currency ;
 
    /** 汇率;默认 1 */
    @ApiModelProperty(value = "汇率")
    @TableField("Rate")
    private BigDecimal rate ;
 
    /** 资金流向;1：资金例如，-1：资金流出,0:待确认流水(异常情况下) */
    @ApiModelProperty(value = "资金流向")
    @TableField("In_or_out")
    private Integer inOrOut ;
 
    /** 创建人所属部门 */
    @ApiModelProperty(value = "创建人所属部门")
    @TableField("Creater_department")
    private String createrDepartment ;


    @ApiModelProperty(value = "备注")
    @TableField("remark")
    private String remark ;


 
    /** 是否删除;是否删除:正常：0，删除：1 */
    @ApiModelProperty(value = "是否删除")
    @TableField("Is_Delete")
    private Integer isDelete ;
 
    /** 创建人 */
    @ApiModelProperty(value = "创建人")
    @TableField("CREATED_BY")
    private String createdBy ;
 
    /** 创建人工号 */
    @ApiModelProperty(value = "创建人工号")
    @TableField("CREATED_BY_NO")
    private String createdByNo ;
 
    /** 创建时间 */
    @ApiModelProperty(value = "创建时间")
    @TableField("CREATED_TIME")
    private Date createdTime ;
 
    /** 更新人 */
    @ApiModelProperty(value = "更新人")
    @TableField("UPDATED_BY")
    private String updatedBy ;
 
    /** 更新人工号 */
    @ApiModelProperty(value = "更新人工号")
    @TableField("UPDATED_BY_NO")
    private String updatedByNo ;
 
    /** 更新时间 */
    @ApiModelProperty(value = "更新时间")
    @TableField("UPDATED_TIME")
    private Date updatedTime ;


}