package com.tadpole.cloud.operationManagement.api.shopEntity.model.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.Date;
import cn.stylefeng.guns.cloud.model.validator.BaseValidatingParam;
import cn.stylefeng.guns.cloud.model.web.request.BaseRequest;
import lombok.*;
import java.lang.*;
import java.math.BigDecimal;

 /**
 * 个人账户管理;
 * @author : LSY
 * @date : 2023-11-10
 */
@ApiModel(value = "个人账户管理",description = "")
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AccountMgtPersonalParam extends BaseRequest implements Serializable,BaseValidatingParam{
 private static final long serialVersionUID = 1L;
 
    /** ID */
    @ApiModelProperty(value = "ID")
    private String id ;
 
    /** 账户性质;值域：支付宝：微信：银行卡 */
    @ApiModelProperty(value = "账户性质")
    private String accType ;
 
    /** 账户(账号) */
    @ApiModelProperty(value = "账户(账号)")
    private String accNo ;
 
    /** 账户户名 */
    @ApiModelProperty(value = "账户户名")
    private String accName ;
 
    /** 账户开户行 */
    @ApiModelProperty(value = "账户开户行")
    private String accBank ;
 
    /** 币别 */
    @ApiModelProperty(value = "币别")
    private String accCurrency ;
 
    /** 可用状态;值域:(启用：enable，禁用：disabled，作废：cancel )*/
    @ApiModelProperty(value = "可用状态")
    private String state ;
 
    /** 预存金额 */
    @ApiModelProperty(value = "预存金额")
    private BigDecimal depositAmount ;
 
    /** 实时金额 */
    @ApiModelProperty(value = "实时金额")
    private BigDecimal amount ;
 
    /** 使用部门 */
    @ApiModelProperty(value = "使用部门")
    private String userDepartment ;
 
    /** 保管人编号 */
    @ApiModelProperty(value = "保管人编号")
    private String custodyUserNo ;
 
    /** 保管人姓名 */
    @ApiModelProperty(value = "保管人姓名")
    private String custodyUserName ;
 
    /** 维护人工号 */
    @ApiModelProperty(value = "维护人工号")
    private String maintainerNo ;
 
    /** 维护人姓名 */
    @ApiModelProperty(value = "维护人姓名")
    private String maintainerName ;
 
    /** 作废时间 */
    @ApiModelProperty(value = "作废时间")
    private Date cancelTime ;
 
    /** 创建人 */
    @ApiModelProperty(value = "创建人")
    private String createdBy ;
 
    /** 创建时间 */
    @ApiModelProperty(value = "创建时间")
    private Date createdTime ;
 
    /** 更新人 */
    @ApiModelProperty(value = "更新人")
    private String updatedBy ;
 
    /** 更新时间 */
    @ApiModelProperty(value = "更新时间")
    private Date updatedTime ;


}