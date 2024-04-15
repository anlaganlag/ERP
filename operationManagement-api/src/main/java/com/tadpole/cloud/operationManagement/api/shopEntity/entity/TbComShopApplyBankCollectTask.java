package com.tadpole.cloud.operationManagement.api.shopEntity.entity;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

 /**
 * @author : LSY
 * @date : 2023-7-28
 * @desc : 资源-店铺申请银行收款任务-资源-店铺申请银行收款任务
 */
@TableName("Tb_Com_Shop_Apply_Bank_Collect_Task")
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ExcelIgnoreUnannotated
public class TbComShopApplyBankCollectTask implements Serializable{
 private static final long serialVersionUID = 1L;
    /** 收款账号任务编号 */
    @TableId(value = "sys_Apply_Det_Bct_Id", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "收款账号任务编号")
    private BigDecimal sysApplyDetBctId ;
    
    /** 申请详情编号 */
    @TableField("sys_Apply_Det_Id")
    @ApiModelProperty(value = "申请详情编号")
    private BigDecimal sysApplyDetId ;
    
    /** 最后更新人姓名 */
    @TableField("sys_Last_Upd_Per_Name")
    @ApiModelProperty(value = "最后更新人姓名")
    private String sysLastUpdPerName ;
    
    /** 最后更新人编号 */
    @TableField("sys_Last_Upd_Per_Code")
    @ApiModelProperty(value = "最后更新人编号")
    private String sysLastUpdPerCode ;
    
    /** 最后更新时间 */
    @TableField("sys_Last_Upd_Date")
    @ApiModelProperty(value = "最后更新时间")
    private Date sysLastUpdDate ;
    
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
    @TableField("shop_Bank_Code")
    @ApiModelProperty(value = "Bank Code")
    private String shopBankCode ;
    
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
    
    /** 收款账号任务状态;收款账号任务状态(未完成、完成) */
    @TableField("sys_Apply_Det_Bct_State")
    @ApiModelProperty(value = "收款账号任务状态")
    private String sysApplyDetBctState ;
    
    /** 账户持有人 */
    @TableField("shop_Account_Hold_Name")
    @ApiModelProperty(value = "账户持有人")
    private String shopAccountHoldName ;
    
    /** 店铺扣款信用卡号 */
    @TableField("shop_Account_No")
    @ApiModelProperty(value = "店铺扣款信用卡号")
    private String shopAccountNo ;
    
    /** 信用卡开户行 */
    @TableField("shop_Bank_Name")
    @ApiModelProperty(value = "信用卡开户行")
    private String shopBankName ;
    
    /** 有效期(开始) */
    @TableField("shop_Effe_Range_Start")
    @ApiModelProperty(value = "有效期(开始)")
    private Date shopEffeRangeStart ;
    
    /** 有效期(结束) */
    @TableField("shop_Effe_Range_End")
    @ApiModelProperty(value = "有效期(结束)")
    private Date shopEffeRangeEnd ;
    
    /** 店铺公司地址（中文） */
    @TableField("shop_Com_Addr_Cn")
    @ApiModelProperty(value = "店铺公司地址（中文）")
    private String shopComAddrCn ;
    
    /** 法人（中文） */
    @TableField("shop_Leg_Person_Cn")
    @ApiModelProperty(value = "法人（中文）")
    private String shopLegPersonCn ;
    
    /** 公司注册国家 */
    @TableField("shop_Com_Reg_Country")
    @ApiModelProperty(value = "公司注册国家")
    private String shopComRegCountry ;
    

}