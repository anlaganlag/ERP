package com.tadpole.cloud.operationManagement.api.shopEntity.entity;

import java.io.Serializable;
import java.util.Date;
import java.lang.*;
import lombok.*;
import java.math.BigDecimal;
import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;

 /**
 * @author : LSY
 * @date : 2023-7-28
 * @desc : 资源-店铺收款银行账号变更-资源-店铺收款银行账号变更
 */
@TableName("Tb_Com_Shop_Rec_Brank_Change_Acc")
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ExcelIgnoreUnannotated
public class TbComShopRecBrankChangeAcc implements Serializable{
 private static final long serialVersionUID = 1L;
    /** 收款银行账号变更编号 */
    @TableId(value = "sys_Id", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "收款银行账号变更编号")
    private BigDecimal sysId ;
    
    /** 店铺名称 */
    @TableField("shop_Name")
    @ApiModelProperty(value = "店铺名称")
    private String shopName ;
    
    /** 店铺收款银行 */
    @TableField("shop_Col_Acc_Bank")
    @ApiModelProperty(value = "店铺收款银行")
    private String shopColAccBank ;
    
    /** 店铺收款银行主体 */
    @TableField("shop_Col_Acc_Bank_Main_Body")
    @ApiModelProperty(value = "店铺收款银行主体")
    private String shopColAccBankMainBody ;
    
    /** 店铺收款账号 */
    @TableField("shop_Col_Acc_No")
    @ApiModelProperty(value = "店铺收款账号")
    private String shopColAccNo ;
    
    /** 店铺收款币种 */
    @TableField("shop_Col_Currency")
    @ApiModelProperty(value = "店铺收款币种")
    private String shopColCurrency ;
    
    /** 收款账号邮箱 */
    @TableField("shop_Col_Acc_Email")
    @ApiModelProperty(value = "收款账号邮箱")
    private String shopColAccEmail ;
    
    /** SWIFT Code(BIC) */
    @TableField("shop_BIC")
    @ApiModelProperty(value = "SWIFT Code(BIC)")
    private String shopBic ;
    
    /** Routing number */
    @TableField("shop_Routing_Number")
    @ApiModelProperty(value = "Routing number")
    private String shopRoutingNumber ;
    
    /** Bank State Branch(BSB) */
    @TableField("shop_BSB")
    @ApiModelProperty(value = "Bank State Branch(BSB)")
    private String shopBsb ;
    
    /** Bank Code */
    @TableField("shop_Ban_K_Code")
    @ApiModelProperty(value = "Bank Code")
    private String shopBanKCode ;
    
    /** Branch Code */
    @TableField("shop_Branch_Code")
    @ApiModelProperty(value = "Branch Code")
    private String shopBranchCode ;
    
    /** Account Type */
    @TableField("shop_Account_Type")
    @ApiModelProperty(value = "Account Type")
    private String shopAccountType ;
    
    /** Sort code */
    @TableField("shop_Sort_Code")
    @ApiModelProperty(value = "Sort code")
    private String shopSortCode ;
    
    /** IBAN */
    @TableField("shop_IBAN")
    @ApiModelProperty(value = "IBAN")
    private String shopIban ;
    
    /** 原店铺收款银行 */
    @TableField("shop_Col_Acc_Bank_Orig")
    @ApiModelProperty(value = "原店铺收款银行")
    private String shopColAccBankOrig ;
    
    /** 原店铺收款银行主体 */
    @TableField("shop_Col_Acc_Bank_Main_Body_Orig")
    @ApiModelProperty(value = "原店铺收款银行主体")
    private String shopColAccBankMainBodyOrig ;
    
    /** 原店铺收款账号 */
    @TableField("shop_Col_Acc_No_Orig")
    @ApiModelProperty(value = "原店铺收款账号")
    private String shopColAccNoOrig ;
    
    /** 原店铺收款币种 */
    @TableField("shop_Col_Currency_Orig")
    @ApiModelProperty(value = "原店铺收款币种")
    private String shopColCurrencyOrig ;
    
    /** 原收款账号邮箱 */
    @TableField("shop_Col_Acc_Email_Orig")
    @ApiModelProperty(value = "原收款账号邮箱")
    private String shopColAccEmailOrig ;
    
    /** 原SWIFT Code(BIC) */
    @TableField("shop_BIC_Orig")
    @ApiModelProperty(value = "原SWIFT Code(BIC)")
    private String shopBicOrig ;
    
    /** 原Routing number */
    @TableField("shop_Routing_Number_Orig")
    @ApiModelProperty(value = "原Routing number")
    private String shopRoutingNumberOrig ;
    
    /** 原Bank State Branch(BSB) */
    @TableField("shop_BSB_Orig")
    @ApiModelProperty(value = "原Bank State Branch(BSB)")
    private String shopBsbOrig ;
    
    /** 原Bank Code */
    @TableField("shop_Ban_K_Code_Orig")
    @ApiModelProperty(value = "原Bank Code")
    private String shopBanKCodeOrig ;
    
    /** 原Branch Code */
    @TableField("shop_Branch_Code_Orig")
    @ApiModelProperty(value = "原Branch Code")
    private String shopBranchCodeOrig ;
    
    /** 原Account Type */
    @TableField("shop_Account_Type_Orig")
    @ApiModelProperty(value = "原Account Type")
    private String shopAccountTypeOrig ;
    
    /** 原Sort code */
    @TableField("shop_Sort_Code_Orig")
    @ApiModelProperty(value = "原Sort code")
    private String shopSortCodeOrig ;
    
    /** 原IBAN */
    @TableField("shop_IBAN_Orig")
    @ApiModelProperty(value = "原IBAN")
    private String shopIbanOrig ;
    
    /** 完成人姓名 */
    @TableField("sys_Finish_Per_Name")
    @ApiModelProperty(value = "完成人姓名")
    private String sysFinishPerName ;
    
    /** 完成人编号 */
    @TableField("sys_Finish_Per_Code")
    @ApiModelProperty(value = "完成人编号")
    private String sysFinishPerCode ;
    
    /** 完成时间 */
    @TableField("sys_Finish_Date")
    @ApiModelProperty(value = "完成时间")
    private Date sysFinishDate ;
    
    /** 申请人姓名 */
    @TableField("sy_Apply_Per_Name2")
    @ApiModelProperty(value = "申请人姓名")
    private String syApplyPerName2 ;
    
    /** 申请人编号 */
    @TableField("sys_Apply_Per_Code2")
    @ApiModelProperty(value = "申请人编号")
    private String sysApplyPerCode2 ;
    
    /** 申请时间 */
    @TableField("sys_Apply_Date")
    @ApiModelProperty(value = "申请时间")
    private Date sysApplyDate ;
    
    /** 接收人姓名 */
    @TableField("sys_Rec_Per_Name")
    @ApiModelProperty(value = "接收人姓名")
    private String sysRecPerName ;
    
    /** 接收人编号 */
    @TableField("sys_Rec_Per_Code")
    @ApiModelProperty(value = "接收人编号")
    private String sysRecPerCode ;
    
    /** 接收人时间 */
    @TableField("sys_Rec_Date")
    @ApiModelProperty(value = "接收人时间")
    private Date sysRecDate ;
    
    /** 申请变更状态;申请变更状态(1:变更申请、2:接收确认、3:完成确认) */
    @TableField("sys_App_Change_Status")
    @ApiModelProperty(value = "申请变更状态")
    private BigDecimal sysAppChangeStatus ;
    
    /** 变更状态;变更状态(已申请、已变更) */
    @TableField("sys_Change_Status")
    @ApiModelProperty(value = "变更状态")
    private String sysChangeStatus ;
    

}