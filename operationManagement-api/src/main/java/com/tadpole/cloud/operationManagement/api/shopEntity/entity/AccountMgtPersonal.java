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
 * 个人账户管理;
 * @author : LSY
 * @date : 2023-11-10
 */
@TableName("ACCOUNT_MGT_PERSONAL")
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ExcelIgnoreUnannotated
public class AccountMgtPersonal implements Serializable{
 private static final long serialVersionUID = 1L;
 
    /** ID */
    @ApiModelProperty(value = "ID")
    @TableId (value = "ID", type = IdType.ASSIGN_ID)
    @TableField("ID")
    private String id ;
 
    /** 账户性质;值域：支付宝：微信：银行卡 */
    @ApiModelProperty(value = "账户性质")
    @TableField("Acc_Type")
    private String accType ;
 
    /** 账户(账号) */
    @ApiModelProperty(value = "账户(账号)")
    @TableField("Acc_No")
    private String accNo ;
 
    /** 账户户名 */
    @ApiModelProperty(value = "账户户名")
    @TableField("Acc_Name")
    private String accName ;
 
    /** 账户开户行 */
    @ApiModelProperty(value = "账户开户行")
    @TableField("Acc_Bank")
    private String accBank ;
 
    /** 币别 */
    @ApiModelProperty(value = "币别")
    @TableField("Acc_Currency")
    private String accCurrency ;
 
    /** 可用状态;值域:(启用：enable，禁用：disabled，作废：cancel )*/
    @ApiModelProperty(value = "可用状态")
    @TableField("State")
    private String state ;
 
    /** 预存金额 */
    @ApiModelProperty(value = "预存金额")
    @TableField("Deposit_amount")
    private BigDecimal depositAmount ;
 
    /** 实时金额 */
    @ApiModelProperty(value = "实时金额")
    @TableField("amount")
    private BigDecimal amount ;
 
    /** 使用部门 */
    @ApiModelProperty(value = "使用部门")
    @TableField("User_department")
    private String userDepartment ;
 
    /** 保管人编号 */
    @ApiModelProperty(value = "保管人编号")
    @TableField("Custody_User_No")
    private String custodyUserNo ;
 
    /** 保管人姓名 */
    @ApiModelProperty(value = "保管人姓名")
    @TableField("Custody_User_Name")
    private String custodyUserName ;
 
    /** 维护人工号 */
    @ApiModelProperty(value = "维护人工号")
    @TableField("Maintainer_No")
    private String maintainerNo ;
 
    /** 维护人姓名 */
    @ApiModelProperty(value = "维护人姓名")
    @TableField("Maintainer_Name")
    private String maintainerName ;
 
    /** 作废时间 */
    @ApiModelProperty(value = "作废时间")
    @TableField("cancel_Time")
    private Date cancelTime ;
 
    /** 创建人 */
    @ApiModelProperty(value = "创建人")
    @TableField("CREATED_BY")
    private String createdBy ;
 
    /** 创建时间 */
    @ApiModelProperty(value = "创建时间")
    @TableField("CREATED_TIME")
    private Date createdTime ;
 
    /** 更新人 */
    @ApiModelProperty(value = "更新人")
    @TableField("UPDATED_BY")
    private String updatedBy ;
 
    /** 更新时间 */
    @ApiModelProperty(value = "更新时间")
    @TableField("UPDATED_TIME")
    private Date updatedTime ;


}