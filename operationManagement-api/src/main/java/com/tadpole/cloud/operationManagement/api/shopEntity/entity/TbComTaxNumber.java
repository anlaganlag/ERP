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

 /**
 * @author : LSY
 * @date : 2023-7-28
 * @desc : 资源-税号管理-资源-税号管理
 */
@TableName("Tb_Com_Tax_Number")
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ExcelIgnoreUnannotated
public class TbComTaxNumber implements Serializable{
 private static final long serialVersionUID = 1L;
    /** ID;(电商平台+商家编号+站点+税号) */
    @TableId(value = "ID", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "ID")
    private String id ;
    
    /** 电商平台 */
    @TableField("ele_Platform_Name")
    @ApiModelProperty(value = "电商平台")
    private String elePlatformName ;
    
    /** 商家编号 */
    @TableField("Seller_ID")
    @ApiModelProperty(value = "商家编号")
    private String sellerId ;

    /** vc店铺编码 */
    @TableField("VC_SHOP_CODE")
    @ApiModelProperty(value = "vc店铺编码")
    private String vcShopCode ;
    
    /** 站点 */
    @TableField("Country_Code")
    @ApiModelProperty(value = "站点")
    private String countryCode ;
    
    /** 税号 */
    @TableField("Tax_Number")
    @ApiModelProperty(value = "税号")
    private String taxNumber ;
    
    /** 税率 */
    @TableField("Vat_Rate")
    @ApiModelProperty(value = "税率")
    private BigDecimal vatRate ;
    
    /** 公司名称 */
    @TableField("Com_Name")
    @ApiModelProperty(value = "公司名称")
    private String comName ;
    
    /** 公司地址 */
    @TableField("Com_Addr")
    @ApiModelProperty(value = "公司地址")
    private String comAddr ;
    
    /** 公司邮编 */
    @TableField("Com_Post_Code")
    @ApiModelProperty(value = "公司邮编")
    private String comPostCode ;
    
    /** 公司电话 */
    @TableField("Com_Tel")
    @ApiModelProperty(value = "公司电话")
    private String comTel ;
    
    /** 公司邮箱 */
    @TableField("Com_Email")
    @ApiModelProperty(value = "公司邮箱")
    private String comEmail ;
    
    /** 公司负责人 */
    @TableField("Com_Head_Name")
    @ApiModelProperty(value = "公司负责人")
    private String comHeadName ;
    
    /** 公司简介 */
    @TableField("Com_Profile")
    @ApiModelProperty(value = "公司简介")
    private String comProfile ;
    

}