package com.tadpole.cloud.operationManagement.api.shopEntity.model.result;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 资源-税号;资源-税号
 * @author : LSY
 * @date : 2023-7-28
 */
@ApiModel(value = "资源-税号",description = "资源-税号")
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ExcelIgnoreUnannotated
public class TbComTaxNumResult  implements Serializable{
 private static final long serialVersionUID = 1L;
    /** 海外税号 */
    @ApiModelProperty(value = "海外税号")
    @ExcelProperty(value ="海外税号")
    private String taxnOverseas ;
    
    /** 店铺名称 */
    @ApiModelProperty(value = "店铺名称")
    @ExcelProperty(value ="店铺名称")
    private String shopName ;
    
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
    
    /** 税务代理机构 */
    @ApiModelProperty(value = "税务代理机构")
    @ExcelProperty(value ="税务代理机构")
    private String taxnAgency ;
    
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

    @ApiModelProperty(value = "店铺主体")
    private String shopMainBody ;

    @ApiModelProperty(value = "后台店铺名称")
    private String shopNamePlat;

    @ApiModelProperty(value = "店铺查账主列表")
    private List<TbComTaxAuditResult> comTaxAudit;

    @ApiModelProperty(value = "店铺查账明细列表")
    private  List<TbComTaxAuditDetResult> tbComTaxAuditDets;


 }