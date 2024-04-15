package com.tadpole.cloud.operationManagement.api.shopEntity.model.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

import cn.stylefeng.guns.cloud.model.validator.BaseValidatingParam;
import cn.stylefeng.guns.cloud.model.web.request.BaseRequest;
import lombok.*;
import java.lang.*;
import java.math.BigDecimal;

 /**
 * 资源-税号管理;资源-税号管理
 * @author : LSY
 * @date : 2023-7-28
 */
@ApiModel(value = "资源-税号管理",description = "资源-税号管理")
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TbComTaxNumberParam extends BaseRequest implements Serializable,BaseValidatingParam{
 private static final long serialVersionUID = 1L;
    /** ID;(电商平台+商家编号+站点+税号) */
    @ApiModelProperty(value = "ID")
    private String id ;
    
    /** 电商平台 */
    @ApiModelProperty(value = "电商平台")
    private String elePlatformName ;
    
    /** 商家编号 */
    @ApiModelProperty(value = "商家编号")
    private String sellerId ;

    /** vc店铺编码 */
    @ApiModelProperty(value = "vc店铺编码")
    private String vcShopCode ;
    
    /** 站点 */
    @ApiModelProperty(value = "站点")
    private String countryCode ;
    
    /** 税号 */
    @ApiModelProperty(value = "税号")
    private String taxNumber ;
    
    /** 税率 */
    @ApiModelProperty(value = "税率")
    private BigDecimal vatRate ;
    
    /** 公司名称 */
    @ApiModelProperty(value = "公司名称")
    private String comName ;
    
    /** 公司地址 */
    @ApiModelProperty(value = "公司地址")
    private String comAddr ;
    
    /** 公司邮编 */
    @ApiModelProperty(value = "公司邮编")
    private String comPostCode ;
    
    /** 公司电话 */
    @ApiModelProperty(value = "公司电话")
    private String comTel ;
    
    /** 公司邮箱 */
    @ApiModelProperty(value = "公司邮箱")
    private String comEmail ;
    
    /** 公司负责人 */
    @ApiModelProperty(value = "公司负责人")
    private String comHeadName ;
    
    /** 公司简介 */
    @ApiModelProperty(value = "公司简介")
    private String comProfile ;
    

}