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
 * 资源-店铺申请详情表;资源-店铺申请详情表
 * @author : LSY
 * @date : 2023-7-28
 */
@ApiModel(value = "资源-店铺申请详情表",description = "资源-店铺申请详情表")
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TbComShopApplyDetParam extends BaseRequest implements Serializable,BaseValidatingParam{
 private static final long serialVersionUID = 1L;
    /** 申请详情编号 */
    @ApiModelProperty(value = "申请详情编号")
    private BigDecimal sysApplyDetId ;
    
    /** 申请编号 */
    @ApiModelProperty(value = "申请编号")
    private BigDecimal sysApplyId ;
    
    /** 创建人编号 */
    @ApiModelProperty(value = "创建人编号")
    private String sysCreatePerCode ;
    
    /** 创建人姓名 */
    @ApiModelProperty(value = "创建人姓名")
    private String sysCreatePerName ;
    
    /** 创建时间 */
    @ApiModelProperty(value = "创建时间")
    private Date sysCreateDate ;
    
    /** 店铺名称 */
    @ApiModelProperty(value = "店铺名称")
    private String shopName ;
    
    /** 是否额外付费店铺 */
    @ApiModelProperty(value = "是否额外付费店铺")
    private String shopIsExtPay ;
    
    /** 站点 */
    @ApiModelProperty(value = "站点")
    private String countryCode ;
    
    /** 后台店铺名称 */
    @ApiModelProperty(value = "后台店铺名称")
    private String shopNamePlat ;
    
    /** SellerID */
    @ApiModelProperty(value = "SellerID")
    private String sellerId ;

    /** vc店铺编码 */
    @ApiModelProperty(value = "vc店铺编码")
    private String vcShopCode ;
    
    /** MarketplaceID */
    @ApiModelProperty(value = "MarketplaceID")
    private String marketplaceId ;
    
    /** AWSAccessKeyID */
    @ApiModelProperty(value = "AWSAccessKeyID")
    private String awsAccessKeyId ;
    
    /** CID */
    @ApiModelProperty(value = "CID")
    private String cId ;
    
    /** 远程登陆地址 */
    @ApiModelProperty(value = "远程登陆地址")
    private String shopRemLogAddress ;
    
    /** 注册邮箱 */
    @ApiModelProperty(value = "注册邮箱")
    private String shopMail ;
    
    /** 邮箱类型 */
    @ApiModelProperty(value = "邮箱类型")
    private String shopMailType ;
    
    /** 邮编 */
    @ApiModelProperty(value = "邮编")
    private String shopComPosCode ;
    
    /** 受益人邮箱1 */
    @ApiModelProperty(value = "受益人邮箱1")
    private String shopBenMail1 ;
    
    /** 受益人邮箱2 */
    @ApiModelProperty(value = "受益人邮箱2")
    private String shopBenMail2 ;
    
    /** 是否虚拟店铺 */
    @ApiModelProperty(value = "是否虚拟店铺")
    private BigDecimal isVirtualShop ;
    
    /** 创建银行收款账号 */
    @ApiModelProperty(value = "创建银行收款账号")
    private BigDecimal shopBankColleAcc ;
    
    /** 创建税号 */
    @ApiModelProperty(value = "创建税号")
    private BigDecimal shopTaxn ;

    /** 店铺数据下载任务;0：不创建店铺数据下载任务，1：创建下载任务 */
    @ApiModelProperty(value = "店铺数据下载任务")
    private BigDecimal shopDataDownTask ;
    
    /** 收款银行备注 */
    @ApiModelProperty(value = "收款银行备注")
    private String shopColAccBankRemark ;


    /** 店铺类型 */
    @ApiModelProperty(value = "店铺类型")
    private String shopType ;


    @ApiModelProperty(value = "采购模式")
    private String purchaseMode ;
    

}