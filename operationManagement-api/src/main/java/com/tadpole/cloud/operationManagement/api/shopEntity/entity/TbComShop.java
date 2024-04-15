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
 * @desc : 资源-店铺-资源-店铺
 */
@TableName("Tb_Com_Shop")
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ExcelIgnoreUnannotated
public class TbComShop implements Serializable{
 private static final long serialVersionUID = 1L;
    /** 店铺名称 */
    @TableId(value = "shop_Name", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "店铺名称")
    private String shopName ;
    
    /** 是否额外付费店铺 */
    @TableField("shop_Is_Ext_Pay")
    @ApiModelProperty(value = "是否额外付费店铺")
    private String shopIsExtPay ;
    
    /** 电商平台 */
    @TableField("ele_Platform_Name")
    @ApiModelProperty(value = "电商平台")
    private String elePlatformName ;
    
    /** 账号 */
    @TableField("shop_Name_Simple")
    @ApiModelProperty(value = "账号")
    private String shopNameSimple ;
    
    /** 站点 */
    @TableField("country_Code")
    @ApiModelProperty(value = "站点")
    private String countryCode ;
    
    /** 后台店铺名称 */
    @TableField("shop_Name_Plat")
    @ApiModelProperty(value = "后台店铺名称")
    private String shopNamePlat ;
    
    /** SellerID */
    @TableField("Seller_ID")
    @ApiModelProperty(value = "SellerID")
    private String sellerId ;

    /** vc店铺编码 */
    @TableField("VC_SHOP_CODE")
    @ApiModelProperty(value = "vc店铺编码")
    private String vcShopCode ;
    
    /** MarketplaceID */
    @TableField("Marketplace_ID")
    @ApiModelProperty(value = "MarketplaceID")
    private String marketplaceId ;
    
    /** AWSAccessKeyID */
    @TableField("AWS_Access_Key_ID")
    @ApiModelProperty(value = "AWSAccessKeyID")
    private String awsAccessKeyId ;
    
    /** CID */
    @TableField("C_ID")
    @ApiModelProperty(value = "CID")
    private String cId ;
    
    /** 注册邮箱 */
    @TableField("shop_Mail")
    @ApiModelProperty(value = "注册邮箱")
    private String shopMail ;
    
    /** 邮箱类型 */
    @TableField("shop_Mail_Type")
    @ApiModelProperty(value = "邮箱类型")
    private String shopMailType ;
    
    /** 绑定电话 */
    @TableField("shop_Telephone")
    @ApiModelProperty(value = "绑定电话")
    private String shopTelephone ;
    
    /** 店铺公司名称（英文） */
    @TableField("shop_Com_Name_En")
    @ApiModelProperty(value = "店铺公司名称（英文）")
    private String shopComNameEn ;
    
    /** 店铺公司地址1（英文） */
    @TableField("shop_Com_Addr_En1")
    @ApiModelProperty(value = "店铺公司地址1（英文）")
    private String shopComAddrEn1 ;
    
    /** 店铺公司地址2（英文） */
    @TableField("shop_Com_Addr_En2")
    @ApiModelProperty(value = "店铺公司地址2（英文）")
    private String shopComAddrEn2 ;
    
    /** 城市（英文） */
    @TableField("shop_Com_City")
    @ApiModelProperty(value = "城市（英文）")
    private String shopComCity ;
    
    /** 国家（英文） */
    @TableField("shop_Com_Country")
    @ApiModelProperty(value = "国家（英文）")
    private String shopComCountry ;
    
    /** 省份（英文） */
    @TableField("shop_Com_State")
    @ApiModelProperty(value = "省份（英文）")
    private String shopComState ;
    
    /** 地区（英文） */
    @TableField("shop_Com_District")
    @ApiModelProperty(value = "地区（英文）")
    private String shopComDistrict ;
    
    /** 邮编 */
    @TableField("shop_Com_Pos_Code")
    @ApiModelProperty(value = "邮编")
    private String shopComPosCode ;
    
    /** 法人（英文） */
    @TableField("shop_Leg_Person_En")
    @ApiModelProperty(value = "法人（英文）")
    private String shopLegPersonEn ;
    
    /** 受益人邮箱1 */
    @TableField("shop_Ben_Mail1")
    @ApiModelProperty(value = "受益人邮箱1")
    private String shopBenMail1 ;
    
    /** 受益人邮箱2 */
    @TableField("shop_Ben_Mail2")
    @ApiModelProperty(value = "受益人邮箱2")
    private String shopBenMail2 ;
    
    /** 远程登录地址 */
    @TableField("shop_Rem_Log_Address")
    @ApiModelProperty(value = "远程登录地址")
    private String shopRemLogAddress ;
    
    /** 店铺状态;关闭 开通中 已关闭 暂未开通 正常 */
    @TableField("shop_State")
    @ApiModelProperty(value = "店铺状态")
    private String shopState ;
    
    /** 店铺主体 */
    @TableField("shop_Main_Body")
    @ApiModelProperty(value = "店铺主体")
    private String shopMainBody ;
    
    /** 店铺公司名称（中文） */
    @TableField("shop_Com_Name_Cn")
    @ApiModelProperty(value = "店铺公司名称（中文）")
    private String shopComNameCn ;
    
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
    
    /** 店铺收款银行 */
    @TableField("shop_Col_Acc_Bank")
    @ApiModelProperty(value = "店铺收款银行")
    private String shopColAccBank ;
    
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
    
    /** 店铺发货地址1（英文） */
    @TableField("shop_Ship_Addr_En1")
    @ApiModelProperty(value = "店铺发货地址1（英文）")
    private String shopShipAddrEn1 ;
    
    /** 店铺发货地址2（英文） */
    @TableField("shop_Ship_Addr_En2")
    @ApiModelProperty(value = "店铺发货地址2（英文）")
    private String shopShipAddrEn2 ;
    
    /** 店铺收款银行主体 */
    @TableField("shop_Col_Acc_Bank_Main_Body")
    @ApiModelProperty(value = "店铺收款银行主体")
    private String shopColAccBankMainBody ;
    
    /** 收款银行备注 */
    @TableField("shop_Col_Acc_Bank_Remark")
    @ApiModelProperty(value = "收款银行备注")
    private String shopColAccBankRemark ;
    
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
    
    /** 标识 */
    @TableField("identification")
    @ApiModelProperty(value = "标识")
    private String identification ;
    
    /** 是否异常数据 */
    @TableField("shop_Data_State")
    @ApiModelProperty(value = "是否异常数据")
    private String shopDataState ;
    
    /** 组织编码 */
    @TableField("shop_Org_Code")
    @ApiModelProperty(value = "组织编码")
    private String shopOrgCode ;
    
    /** 账号同步状态 */
    @TableField("shop_Sync_State")
    @ApiModelProperty(value = "账号同步状态")
    private String shopSyncState ;
    
    /** 账号同步时间 */
    @TableField("shop_Sync_Time")
    @ApiModelProperty(value = "账号同步时间")
    private Date shopSyncTime ;
    
    /** 店铺创建时间 */
    @TableField("shop_Create_Time")
    @ApiModelProperty(value = "店铺创建时间")
    private Date shopCreateTime ;
    
    /** 店铺状态操作人编号 */
    @TableField("shop_State_Update_Person_Code")
    @ApiModelProperty(value = "店铺状态操作人编号")
    private String shopStateUpdatePersonCode ;
    
    /** 店铺状态操作人 */
    @TableField("shop_State_Update_Person_Name")
    @ApiModelProperty(value = "店铺状态操作人")
    private String shopStateUpdatePersonName ;
    
    /** 店铺状态更新时间 */
    @TableField("shop_State_Update_Time")
    @ApiModelProperty(value = "店铺状态更新时间")
    private Date shopStateUpdateTime ;
    
    /** 店铺API 0 未授权 1 授权正常 2 授权失效 */
    @TableField("auth_Status")
    @ApiModelProperty(value = "店铺API 0 未授权 1 授权正常 2 授权失效")
    private BigDecimal authStatus ;

    /** 店铺数据下载任务;0：不创建店铺数据下载任务，1：创建下载任务 */
    @TableField("shop_data_down_task")
    @ApiModelProperty(value = "店铺数据下载任务")
    private BigDecimal shopDataDownTask ;
    
    /** 备注 */
    @TableField("remark")
    @ApiModelProperty(value = "备注")
    private String remark ;


    @TableField("shop_type")
    @ApiModelProperty(value = "店铺类型")
    private String shopType ;

    @TableField("PURCHASE_MODE")
    @ApiModelProperty(value = "采购模式")
    private String purchaseMode ;
    

}