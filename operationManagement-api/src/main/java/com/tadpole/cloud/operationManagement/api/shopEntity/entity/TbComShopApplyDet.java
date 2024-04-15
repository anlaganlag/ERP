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
 * @desc : 资源-店铺申请详情表-资源-店铺申请详情表
 */
@TableName("Tb_Com_Shop_Apply_Det")
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ExcelIgnoreUnannotated
public class TbComShopApplyDet implements Serializable{
 private static final long serialVersionUID = 1L;
    /** 申请详情编号 */
    @TableId(value = "sys_Apply_Det_Id", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "申请详情编号")
    private BigDecimal sysApplyDetId ;
    
    /** 申请编号 */
    @TableField("sys_Apply_Id")
    @ApiModelProperty(value = "申请编号")
    private BigDecimal sysApplyId ;
    
    /** 创建人编号 */
    @TableField("sys_Create_Per_Code")
    @ApiModelProperty(value = "创建人编号")
    private String sysCreatePerCode ;
    
    /** 创建人姓名 */
    @TableField("sys_Create_Per_Name")
    @ApiModelProperty(value = "创建人姓名")
    private String sysCreatePerName ;
    
    /** 创建时间 */
    @TableField("sys_Create_Date")
    @ApiModelProperty(value = "创建时间")
    private Date sysCreateDate ;
    
    /** 店铺名称 */
    @TableField("shop_Name")
    @ApiModelProperty(value = "店铺名称")
    private String shopName ;
    
    /** 是否额外付费店铺 */
    @TableField("shop_Is_Ext_Pay")
    @ApiModelProperty(value = "是否额外付费店铺")
    private String shopIsExtPay ;
    
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
    
    /** 远程登陆地址 */
    @TableField("shop_Rem_Log_Address")
    @ApiModelProperty(value = "远程登陆地址")
    private String shopRemLogAddress ;
    
    /** 注册邮箱 */
    @TableField("shop_Mail")
    @ApiModelProperty(value = "注册邮箱")
    private String shopMail ;
    
    /** 邮箱类型 */
    @TableField("shop_Mail_Type")
    @ApiModelProperty(value = "邮箱类型")
    private String shopMailType ;
    
    /** 邮编 */
    @TableField("shop_Com_Pos_Code")
    @ApiModelProperty(value = "邮编")
    private String shopComPosCode ;
    
    /** 受益人邮箱1 */
    @TableField("shop_Ben_Mail1")
    @ApiModelProperty(value = "受益人邮箱1")
    private String shopBenMail1 ;
    
    /** 受益人邮箱2 */
    @TableField("shop_Ben_Mail2")
    @ApiModelProperty(value = "受益人邮箱2")
    private String shopBenMail2 ;
    
    /** 是否虚拟店铺 */
    @TableField("is_Virtual_Shop")
    @ApiModelProperty(value = "是否虚拟店铺")
    private BigDecimal isVirtualShop ;
    
    /** 创建银行收款账号 */
    @TableField("shop_Bank_Colle_Acc")
    @ApiModelProperty(value = "创建银行收款账号")
    private BigDecimal shopBankColleAcc ;
    
    /** 创建税号 */
    @TableField("shop_Taxn")
    @ApiModelProperty(value = "创建税号")
    private BigDecimal shopTaxn ;

    /** 店铺数据下载任务;0：不创建店铺数据下载任务，1：创建下载任务 */
    @TableField("shop_data_down_task")
    @ApiModelProperty(value = "店铺数据下载任务")
    private BigDecimal shopDataDownTask ;
    /** 收款银行备注 */
    @TableField("shop_Col_Acc_Bank_Remark")
    @ApiModelProperty(value = "收款银行备注")
    private String shopColAccBankRemark ;


    @TableField("shop_type")
    @ApiModelProperty(value = "店铺类型")
    private String shopType ;

    @TableField("purchase_mode")
    @ApiModelProperty(value = "采购模式")
    private String purchaseMode ;

}