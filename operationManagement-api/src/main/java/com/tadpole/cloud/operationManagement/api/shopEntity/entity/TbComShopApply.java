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
 * @desc : 资源-店铺申请-资源-店铺申请
 */
@TableName("Tb_Com_Shop_Apply")
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ExcelIgnoreUnannotated
public class TbComShopApply implements Serializable{
 private static final long serialVersionUID = 1L;
    /** 申请编号 */
    @TableId(value = "sys_Apply_Id", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "申请编号")
    private BigDecimal sysApplyId ;
    
    /** 申请人编号 */
    @TableField("sys_Apply_Per_Code")
    @ApiModelProperty(value = "申请人编号")
    private String sysApplyPerCode ;
    
    /** 申请人姓名 */
    @TableField("sys_Apply_Per_Name")
    @ApiModelProperty(value = "申请人姓名")
    private String sysApplyPerName ;
    
    /** 申请时间 */
    @TableField("sys_Apply_Date")
    @ApiModelProperty(value = "申请时间")
    private Date sysApplyDate ;
    
    /** 申请状态;申请状态(0:平台账号申请、1:资料上传、2:店铺创建、3:财务任务刘表、4:完结) */
    @TableField("sys_Apply_State")
    @ApiModelProperty(value = "申请状态")
    private Integer sysApplyState ;
    
    /** 店铺主体公司(中文) */
    @TableField("com_Name_Cn")
    @ApiModelProperty(value = "店铺主体公司(中文)")
    private String comNameCn ;
    
    /** 销售平台 */
    @TableField("ele_Platform_Name")
    @ApiModelProperty(value = "销售平台")
    private String elePlatformName ;
    
    /** 附件名称 */
    @TableField("attach_Name")
    @ApiModelProperty(value = "附件名称")
    private String attachName ;
    
    /** 申请说明 */
    @TableField("sys_Apply_Desc")
    @ApiModelProperty(value = "申请说明")
    private String sysApplyDesc ;
    
    /** 账号简称 */
    @TableField("shop_Name_Simple")
    @ApiModelProperty(value = "账号简称")
    private String shopNameSimple ;
    
    /** 法人（英文） */
    @TableField("shop_Leg_Person_En")
    @ApiModelProperty(value = "法人（英文）")
    private String shopLegPersonEn ;
    
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
    
    /** 店铺发货地址1（英文） */
    @TableField("shop_Ship_Addr_En1")
    @ApiModelProperty(value = "店铺发货地址1（英文）")
    private String shopShipAddrEn1 ;
    
    /** 店铺发货地址2（英文） */
    @TableField("shop_Ship_Addr_En2")
    @ApiModelProperty(value = "店铺发货地址2（英文）")
    private String shopShipAddrEn2 ;
    
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
    
    /** 附件地址 */
    @TableField("attach_Addrr")
    @ApiModelProperty(value = "附件地址")
    private String attachAddrr ;
    
    /** 标识 */
    @TableField("identification")
    @ApiModelProperty(value = "标识")
    private String identification ;
    
    /** 邮编 */
    @TableField("shop_Com_Pos_Code")
    @ApiModelProperty(value = "邮编")
    private String shopComPosCode ;
    

}