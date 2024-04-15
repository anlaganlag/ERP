package com.tadpole.cloud.operationManagement.api.shopEntity.model.param;

import cn.stylefeng.guns.cloud.model.validator.BaseValidatingParam;
import cn.stylefeng.guns.cloud.model.web.request.BaseRequest;
import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

 /**
 * 账户流水;
 * @author : LSY
 * @date : 2023-11-10
 */
@ApiModel(value = "账户流水",description = "")
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AccountFlowParam extends BaseRequest implements Serializable,BaseValidatingParam{
 private static final long serialVersionUID = 1L;
 
    /** 数据ID */
    @ApiModelProperty(value = "数据ID")
    private String id ;
 
    /** 业务流水ID;默认等于数据ID，保证每次请求唯一 */
    @ApiModelProperty(value = "业务流水ID")
    private String flowId ;

    /** 业务流水类型(预存金额:deposit_amount,实时金额:real_time_amount) */
    @ApiModelProperty(value = "业务流水类型(预存金额:deposit_amount,实时金额:real_time_amount)")
    private String bizFlowType ;
 
    /** 业务数据来源 */
    @ApiModelProperty(value = "业务数据来源")
    private String bizDataSources ;
 
    /** 账户ID */
    @ApiModelProperty(value = "账户ID")
    private String accountId ;
 
    /** 账户（账号） */
    @ApiModelProperty(value = "账户（账号）")
    private String accountNo ;
 
    /** 收付款账号：当inOrOut=1是付款账号，inOrOut=-1是收款账号 */
    @ApiModelProperty(value = "收付款账号：当inOrOut=1是付款账号，inOrOut=-1是收款账号")
    private String paymentAccount ;
 
    /** 付款时间 */
    @ApiModelProperty(value = "付款时间")
    private Date paymentTime ;
 
    /** 金额 */
    @ApiModelProperty(value = "金额")
    private BigDecimal amount ;
 
    /** 账户余额 */
    @ApiModelProperty(value = "账户余额")
    private BigDecimal accountBalance ;
 
    /** 币别;默认 人民币CNY */
    @ApiModelProperty(value = "币别")
    private String currency ;
 
    /** 汇率;默认 1 */
    @ApiModelProperty(value = "汇率")
    private BigDecimal rate ;
 
    /** 资金流向;1：资金例如，-1：资金流出,0:待确认流水(异常情况下) */
    @ApiModelProperty(value = "资金流向")
    private Integer inOrOut ;
 
    /** 创建人所属部门 */
    @ApiModelProperty(value = "创建人所属部门")
    private String createrDepartment ;

    @ApiModelProperty(value = "备注")
    private String remark ;
 
    /** 是否删除;是否删除:正常：0，删除：1 */
    @ApiModelProperty(value = "是否删除")
    private Integer isDelete ;
 
    /** 创建人 */
    @ApiModelProperty(value = "创建人")
    private String createdBy ;
 
    /** 创建人工号 */
    @ApiModelProperty(value = "创建人工号")
    private String createdByNo ;
 
    /** 创建时间 */
    @ApiModelProperty(value = "创建时间")
    private Date createdTime ;
 
    /** 更新人 */
    @ApiModelProperty(value = "更新人")
    private String updatedBy ;
 
    /** 更新人工号 */
    @ApiModelProperty(value = "更新人工号")
    private String updatedByNo ;
 
    /** 更新时间 */
    @ApiModelProperty(value = "更新时间")
    private Date updatedTime ;


}