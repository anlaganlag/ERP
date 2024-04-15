package com.tadpole.cloud.operationManagement.api.shopEntity.model.result;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
* 资源-店铺;资源-店铺
* @author : LSY
* @date : 2023-7-28
*/
@ApiModel(value = "资源-店铺",description = "资源-店铺")
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ExcelIgnoreUnannotated
public class TbComShopLogisticsReportResult implements Serializable{
private static final long serialVersionUID = 1L;
   /** 店铺名称 */
   @ApiModelProperty(value = "店铺名称")
   @ExcelProperty(value ="店铺名称")
   private String shopName ;

   /** 电商平台 */
   @ApiModelProperty(value = "电商平台")
   @ExcelProperty(value ="电商平台")
   private String elePlatformName ;

   /** 店铺简称 */
   @ApiModelProperty(value = "店铺简称")
   @ExcelProperty(value ="店铺简称")
   private String shopNameSimple ;

   /** 站点 */
   @ApiModelProperty(value = "站点")
   @ExcelProperty(value ="站点")
   private String countryCode ;

   /** 后台店铺名称 */
   @ApiModelProperty(value = "后台店铺名称")
   @ExcelProperty(value ="后台店铺名称")
   private String shopNamePlat ;

   /** SellerID */
   @ApiModelProperty(value = "SellerID")
   @ExcelProperty(value ="SellerID")
   private String sellerId ;

   /** vc店铺编码 */
   @ApiModelProperty(value = "vc店铺编码")
   @ExcelProperty(value ="vc店铺编码")
   private String vcShopCode ;



   /** 注册邮箱 */
   @ApiModelProperty(value = "注册邮箱")
   @ExcelProperty(value ="注册邮箱")
   private String shopMail ;

   /** 邮箱类型 */
   @ApiModelProperty(value = "邮箱类型")
   @ExcelProperty(value ="邮箱类型")
   private String shopMailType ;

   /** 绑定电话 */
   @ApiModelProperty(value = "绑定电话")
   @ExcelProperty(value ="绑定电话")
   private String shopTelephone ;

   /** 店铺公司名称（英文） */
   @ApiModelProperty(value = "店铺公司名称（英文）")
   @ExcelProperty(value ="店铺公司名称（英文）")
   private String shopComNameEn ;

   /** 店铺公司地址1（英文） */
   @ApiModelProperty(value = "店铺公司地址1（英文）")
   @ExcelProperty(value ="店铺公司地址1（英文）")
   private String shopComAddrEn1 ;

   /** 店铺公司地址2（英文） */
   @ApiModelProperty(value = "店铺公司地址2（英文）")
   @ExcelProperty(value ="店铺公司地址2（英文）")
   private String shopComAddrEn2 ;

   /** 城市（英文） */
   @ApiModelProperty(value = "城市（英文）")
   @ExcelProperty(value ="城市（英文）")
   private String shopComCity ;

   /** 国家（英文） */
   @ApiModelProperty(value = "国家（英文）")
   @ExcelProperty(value ="国家（英文）")
   private String shopComCountry ;

   /** 省份（英文） */
   @ApiModelProperty(value = "省份（英文）")
   @ExcelProperty(value ="省份（英文）")
   private String shopComState ;

   /** 地区（英文） */
   @ApiModelProperty(value = "地区（英文）")
   @ExcelProperty(value ="地区（英文）")
   private String shopComDistrict ;

   /** 邮编 */
   @ApiModelProperty(value = "邮编")
   @ExcelProperty(value ="邮编")
   private String shopComPosCode ;

   /** 远程登录地址 */
   @ApiModelProperty(value = "远程登录地址")
   @ExcelProperty(value ="远程登录地址")
   private String shopRemLogAddress ;

   /** 法人（英文） */
   @ApiModelProperty(value = "法人（英文）")
   @ExcelProperty(value ="法人（英文）")
   private String shopLegPersonEn ;

   /** 受益人邮箱1 */
   @ApiModelProperty(value = "受益人邮箱1")
   @ExcelProperty(value ="受益人邮箱1")
   private String shopBenMail1 ;

   /** 受益人邮箱2 */
   @ApiModelProperty(value = "受益人邮箱2")
   @ExcelProperty(value ="受益人邮箱2")
   private String shopBenMail2 ;





   /** 店铺主体 */
   @ApiModelProperty(value = "店铺主体")
   @ExcelProperty(value ="店铺主体")
   private String shopMainBody ;



   /** 店铺公司地址（中文） */
   @ApiModelProperty(value = "店铺公司地址（中文）")
   @ExcelProperty(value ="店铺公司地址（中文）")
   private String shopComAddrCn ;

   /** 法人（中文） */
   @ApiModelProperty(value = "法人（中文）")
   @ExcelProperty(value ="法人（中文)")
   private String shopLegPersonCn ;


   /** 公司注册国家 */
   @ApiModelProperty(value = "公司注册国家")
   @ExcelProperty(value ="公司注册国家")
   private String shopComRegCountry ;


   /** 店铺收款银行 */
   @ApiModelProperty(value = "店铺收款银行")
   @ExcelProperty(value = "店铺收款银行")
   private String shopColAccBank ;


   /** 店铺收款银行主体 */
   @ApiModelProperty(value = "店铺收款银行主体")
   @ExcelProperty(value = "店铺收款银行主体")
   private String shopColAccBankMainBody ;



   /** 收款银行备注 */
   @ApiModelProperty(value = "收款银行备注")
   private String shopColAccBankRemark ;


   /** 账户持有人 */
   @ApiModelProperty(value = "账户持有人")
   private String shopAccountHoldName ;


   /** 店铺收款账号 */
   @ApiModelProperty(value = "店铺收款账号")
   @ExcelProperty(value = "店铺收款账号")
   private String shopColAccNo ;

   /** 店铺收款币种 */
   @ApiModelProperty(value = "店铺收款币种")
   @ExcelProperty(value = "店铺收款币种")
   private String shopColCurrency ;

   /** 收款账号邮箱 */
   @ApiModelProperty(value = "收款账号邮箱")
   @ExcelProperty(value = "收款账号邮箱")
   private String shopColAccEmail ;

   /** SWIFT Code(BIC) */
   @ApiModelProperty(value = "SWIFT Code(BIC)")
   @ExcelProperty(value = "SWIFT Code(BIC)")
   private String shopBic ;

   /** Routing number */
   @ApiModelProperty(value = "Routing number")
   @ExcelProperty(value = "Routing number")
   private String shopRoutingNumber ;

   /** Bank State Branch(BSB) */
   @ApiModelProperty(value = "Bank State Branch(BSB)")
   @ExcelProperty(value = "Bank State Branch(BSB)")
   private String shopBsb ;

   /** Bank Code */
   @ApiModelProperty(value = "Bank Code")
   @ExcelProperty(value = "Bank Code")
   private String shopBanKCode ;

   /** Branch Code */
   @ApiModelProperty(value = "Branch Code")
   @ExcelProperty(value = "Branch Code")
   private String shopBranchCode ;

   /** Account Type */
   @ApiModelProperty(value = "Account Type")
   @ExcelProperty(value = "Account Type")
   private String shopAccountType ;

   /** Sort code */
   @ApiModelProperty(value = "Sort code")
   @ExcelProperty(value = "Sort code")
   private String shopSortCode ;

   /** IBAN */
   @ApiModelProperty(value = "IBAN")
   @ExcelProperty(value = "IBAN")
   private String shopIban ;


   /** 店铺扣款信用卡号 */
   @ApiModelProperty(value = "店铺扣款信用卡号")
   @ExcelProperty(value = "店铺扣款信用卡号")
   private String shopAccountNo ;

   /** 信用卡开户行 */
   @ApiModelProperty(value = "信用卡开户行")
   @ExcelProperty(value = "信用卡开户行")
   private String shopBankName ;


   /** 有效期(开始) */
   @ApiModelProperty(value = "有效期(开始)")
   @ExcelProperty(value = "有效期(开始)")
   private Date shopEffeRangeStart ;

   /** 有效期(结束) */
   @ApiModelProperty(value = "有效期(结束)")
   @ExcelProperty(value = "有效期(结束)")
   private Date shopEffeRangeEnd ;

   /** 店铺发货地址1（英文） */
   @ApiModelProperty(value = "店铺发货地址1（英文）")
   private String shopShipAddrEn1 ;

   /** 店铺发货地址2（英文） */
   @ApiModelProperty(value = "店铺发货地址2（英文）")
   private String shopShipAddrEn2 ;












   /** 标识 */
   @ApiModelProperty(value = "标识")
   private String identification ;

   /** 是否异常数据 */
   @ApiModelProperty(value = "是否异常数据")
   private String shopDataState ;

   /** 组织编码 */
   @ApiModelProperty(value = "组织编码")
   private String shopOrgCode ;

   /** 账号同步状态 */
   @ApiModelProperty(value = "账号同步状态")
   private String shopSyncState ;

   /** 账号同步时间 */
   @ApiModelProperty(value = "账号同步时间")
   private Date shopSyncTime ;

   /** 店铺创建时间 */
   @ApiModelProperty(value = "店铺创建时间")
   private Date shopCreateTime ;

   /** 店铺状态操作人编号 */
   @ApiModelProperty(value = "店铺状态操作人编号")
   private String shopStateUpdatePersonCode ;

   /** 店铺状态操作人 */
   @ApiModelProperty(value = "店铺状态操作人")
   private String shopStateUpdatePersonName ;

   /** 店铺状态更新时间 */
   @ApiModelProperty(value = "店铺状态更新时间")
   private Date shopStateUpdateTime ;

   /** 店铺API 0 未授权 1 授权正常 2 授权失效 */
   @ApiModelProperty(value = "店铺API 0 未授权 1 授权正常 2 授权失效")
   private BigDecimal authStatus ;

   /** 店铺数据下载任务;0：不创建店铺数据下载任务，1：创建下载任务 */
   @ApiModelProperty(value = "店铺数据下载任务")
   private BigDecimal shopDataDownTask ;

   /** 备注 */
   @ApiModelProperty(value = "备注")
   private String remark ;



   /** 海外税号 */
   @ApiModelProperty(value = "海外税号")
   private String taxnOverseas ;


   /** 税务代理机构 */
   @ApiModelProperty(value = "税务代理机构")
   private String taxnAgency ;






   /** 税率 */
   @ApiModelProperty(value = "税率")
   @ExcelProperty(value ="税率")
   private BigDecimal taxnRate ;

   /** 本地税号 */
   @ApiModelProperty(value = "本地税号")
   @ExcelProperty(value ="本地税号")
   private String taxnLocal ;

   /** EORI号 */
   @ApiModelProperty(value = "EORI号")
   @ExcelProperty(value ="EORI号")
   private String taxnEori ;

   /** 税号所属公司 */
   @ApiModelProperty(value = "税号所属公司")
   @ExcelProperty(value ="税号所属公司")
   private String taxnBelongToCom ;

   /** 税号注册地址 */
   @ApiModelProperty(value = "税号注册地址")
   @ExcelProperty(value ="税号注册地址")
   private String taxnRegAddress ;

   /** 会计师通讯地址 */
   @ApiModelProperty(value = "会计师通讯地址")
   @ExcelProperty(value ="会计师通讯地址")
   private String taxnAddrOfAccountant ;

   /** VAT证书生效日期 */
   @ApiModelProperty(value = "VAT证书生效日期")
   @ExcelProperty(value ="VAT证书生效日期")
   private Date taxnEffDateOfVat ;

   /** 申报方式 */
   @ApiModelProperty(value = "申报方式")
   @ExcelProperty(value ="申报方式")
   private String taxnDecMethod ;

   /** 申报时间 */
   @ApiModelProperty(value = "申报时间")
   @ExcelProperty(value ="申报时间")
   private String taxnDecTime ;

   /** 注册税号邮箱 */
   @ApiModelProperty(value = "注册税号邮箱")
   @ExcelProperty(value ="注册税号邮箱")
   private String taxnRegEmail ;

   /** 注册税号手机号 */
   @ApiModelProperty(value = "注册税号手机号")
   @ExcelProperty(value ="注册税号手机号")
   private String taxnRegTel ;

   /** 查账邮箱 */
   @ApiModelProperty(value = "查账邮箱")
   @ExcelProperty(value ="查账邮箱")
   private String taxnAudEmail ;

   /** 备用邮箱1 */
   @ApiModelProperty(value = "备用邮箱1")
   @ExcelProperty(value ="备用邮箱1")
   private String taxnAltEmail1 ;

   /** 备用邮箱2 */
   @ApiModelProperty(value = "备用邮箱2")
   @ExcelProperty(value ="备用邮箱2")
   private String taxnAltEmail2 ;



   /** 税务代理联系方式 */
   @ApiModelProperty(value = "税务代理联系方式")
   @ExcelProperty(value ="税务代理联系方式")
   private String taxnAgencyTel ;

   /** 税务代理机构办公地址 */
   @ApiModelProperty(value = "税务代理机构办公地址")
   @ExcelProperty(value ="税务代理机构办公地址")
   private String taxnAgencyAddress ;

   /** 税号状态 */
   @ApiModelProperty(value = "税号状态")
   @ExcelProperty(value ="税号状态")
   private String taxnState ;

   /** 附件 */
   @ApiModelProperty(value = "附件")
   @ExcelProperty(value ="附件")
   private String taxnFiles ;

   /** 税号类别 */
   @ApiModelProperty(value = "税号类别")
   @ExcelProperty(value ="税号类别")
   private String taxnCate ;

   /** 有效范围(开始) */
   @ApiModelProperty(value = "有效范围(开始)")
   @ExcelProperty(value ="有效范围(开始)")
   private Date taxnEffeRangeStart ;

   /** 有效范围(结束) */
   @ApiModelProperty(value = "有效范围(结束)")
   @ExcelProperty(value ="有效范围(结束)")
   private Date taxnEffeRangeEnd ;

   /** 附件名称 */
   @ApiModelProperty(value = "附件名称")
   @ExcelProperty(value ="附件名称")
   private String taxnFilesName ;



   @ApiModelProperty(value = "店铺查账主列表")
   private List<TbComTaxAuditResult> comTaxAudit;

   @ApiModelProperty(value = "店铺查账明细列表")
   private  List<TbComTaxAuditDetResult> tbComTaxAuditDets;


   /** 税号-店铺名称 */
   @ApiModelProperty(value = "税号-店铺名称")
   @ExcelProperty(value ="税号-店铺名称")
   private String taxShopName ;




   /** 店铺公司名称（中文） */
   @ApiModelProperty(value = "店铺公司名称（中文）")
   private String shopComNameCn ;

   /** 是否额外付费店铺 */
   @ApiModelProperty(value = "是否额外付费店铺")
   @ExcelProperty(value ="是否额外付费店铺")
   private String shopIsExtPay ;











   /** MarketplaceID */
   @ApiModelProperty(value = "MarketplaceID")
   @ExcelProperty(value ="MarketplaceID")
   private String marketplaceId ;

   /** AWSAccessKeyID */
   @ApiModelProperty(value = "AWSAccessKeyID")
   @ExcelProperty(value ="AWSAccessKeyID")
   private String awsAccessKeyId ;

   /** CID */
   @ApiModelProperty(value = "CID")
   @ExcelProperty(value ="CID")
   private String cId ;


   /** 店铺状态 */
   @ApiModelProperty(value = "店铺状态")
   @ExcelProperty(value ="店铺状态")
   private String shopState ;


   /** 店铺类型 */
   @ApiModelProperty(value = "店铺类型")
   @ExcelProperty(value ="店铺类型")
   private String shopType ;


   @ExcelProperty(value ="采购模式")
   @ApiModelProperty(value = "采购模式")
   private String purchaseMode ;

}