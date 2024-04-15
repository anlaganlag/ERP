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
 * @desc : 资源-税号-资源-税号
 */
@TableName("Tb_Com_Tax_Num")
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ExcelIgnoreUnannotated
public class TbComTaxNum implements Serializable{
 private static final long serialVersionUID = 1L;
    /** 海外税号 */
    @TableId(value = "taxn_Overseas", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "海外税号")
    private String taxnOverseas ;
    
    /** 店铺名称 */
    @TableField("shop_Name")
    @ApiModelProperty(value = "店铺名称")
    private String shopName ;
    
    /** 税率 */
    @TableField("taxn_Rate")
    @ApiModelProperty(value = "税率")
    private BigDecimal taxnRate ;
    
    /** 本地税号 */
    @TableField("taxn_Local")
    @ApiModelProperty(value = "本地税号")
    private String taxnLocal ;
    
    /** EORI号 */
    @TableField("taxn_EORI")
    @ApiModelProperty(value = "EORI号")
    private String taxnEori ;
    
    /** 税号所属公司 */
    @TableField("taxn_Belong_To_Com")
    @ApiModelProperty(value = "税号所属公司")
    private String taxnBelongToCom ;
    
    /** 税号注册地址 */
    @TableField("taxn_Reg_Address")
    @ApiModelProperty(value = "税号注册地址")
    private String taxnRegAddress ;
    
    /** 会计师通讯地址 */
    @TableField("taxn_Addr_Of_Accountant")
    @ApiModelProperty(value = "会计师通讯地址")
    private String taxnAddrOfAccountant ;
    
    /** VAT证书生效日期 */
    @TableField("taxn_Eff_Date_Of_Vat")
    @ApiModelProperty(value = "VAT证书生效日期")
    private Date taxnEffDateOfVat ;
    
    /** 申报方式 */
    @TableField("taxn_Dec_Method")
    @ApiModelProperty(value = "申报方式")
    private String taxnDecMethod ;
    
    /** 申报时间 */
    @TableField("taxn_Dec_Time")
    @ApiModelProperty(value = "申报时间")
    private String taxnDecTime ;
    
    /** 注册税号邮箱 */
    @TableField("taxn_Reg_Email")
    @ApiModelProperty(value = "注册税号邮箱")
    private String taxnRegEmail ;
    
    /** 注册税号手机号 */
    @TableField("taxn_Reg_Tel")
    @ApiModelProperty(value = "注册税号手机号")
    private String taxnRegTel ;
    
    /** 查账邮箱 */
    @TableField("taxn_Aud_Email")
    @ApiModelProperty(value = "查账邮箱")
    private String taxnAudEmail ;
    
    /** 备用邮箱1 */
    @TableField("taxn_Alt_Email1")
    @ApiModelProperty(value = "备用邮箱1")
    private String taxnAltEmail1 ;
    
    /** 备用邮箱2 */
    @TableField("taxn_Alt_Email2")
    @ApiModelProperty(value = "备用邮箱2")
    private String taxnAltEmail2 ;
    
    /** 税务代理机构 */
    @TableField("taxn_Agency")
    @ApiModelProperty(value = "税务代理机构")
    private String taxnAgency ;
    
    /** 税务代理联系方式 */
    @TableField("taxn_Agency_Tel")
    @ApiModelProperty(value = "税务代理联系方式")
    private String taxnAgencyTel ;
    
    /** 税务代理机构办公地址 */
    @TableField("taxn_Agency_Address")
    @ApiModelProperty(value = "税务代理机构办公地址")
    private String taxnAgencyAddress ;
    
    /** 税号状态 */
    @TableField("taxn_State")
    @ApiModelProperty(value = "税号状态")
    private String taxnState ;
    
    /** 附件 */
    @TableField("taxn_Files")
    @ApiModelProperty(value = "附件")
    private String taxnFiles ;
    
    /** 税号类别 */
    @TableField("taxn_Cate")
    @ApiModelProperty(value = "税号类别")
    private String taxnCate ;
    
    /** 有效范围(开始) */
    @TableField("taxn_Effe_Range_Start")
    @ApiModelProperty(value = "有效范围(开始)")
    private Date taxnEffeRangeStart ;
    
    /** 有效范围(结束) */
    @TableField("taxn_Effe_Range_End")
    @ApiModelProperty(value = "有效范围(结束)")
    private Date taxnEffeRangeEnd ;
    
    /** 附件名称 */
    @TableField("taxn_Files_Name")
    @ApiModelProperty(value = "附件名称")
    private String taxnFilesName ;
    

}