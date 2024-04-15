package com.tadpole.cloud.operationManagement.api.shopEntity.model.param;

import cn.stylefeng.guns.cloud.model.validator.BaseValidatingParam;
import cn.stylefeng.guns.cloud.model.web.request.BaseRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

 /**
 * 资源-店铺申请税务任务;资源-店铺申请税务任务
 * @author : LSY
 * @date : 2023-7-28
 */
@ApiModel(value = "资源-店铺申请税务任务",description = "资源-店铺申请税务任务")
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TbComShopApplyTaxnTaskParam extends BaseRequest implements Serializable,BaseValidatingParam{
 private static final long serialVersionUID = 1L;
    /** 税务任务编号 */
    @ApiModelProperty(value = "税务任务编号")
    private BigDecimal taxnId ;
    
    /** 申请详情编号 */
    @ApiModelProperty(value = "申请详情编号")
    private BigDecimal sysApplyDetId ;
    
    /** 最后更新人姓名 */
    @ApiModelProperty(value = "最后更新人姓名")
    private String sysLastUpdPerName ;
    
    /** 最后更新人编号 */
    @ApiModelProperty(value = "最后更新人编号")
    private String sysLastUpdPerCode ;
    
    /** 最后更新时间 */
    @ApiModelProperty(value = "最后更新时间")
    private Date sysLastUpdDate ;
    
    /** 店铺名称 */
    @ApiModelProperty(value = "店铺名称")
    private String shopName ;
    
    /** 海外税号 */
    @ApiModelProperty(value = "海外税号")
    private String taxnOverseas ;
    
    /** 本地税号 */
    @ApiModelProperty(value = "本地税号")
    private String taxnLocal ;
    
    /** EORI号 */
    @ApiModelProperty(value = "EORI号")
    private String taxnEori ;
    
    /** 税号所属公司 */
    @ApiModelProperty(value = "税号所属公司")
    private String taxnBelongToCom ;
    
    /** 税号注册地址 */
    @ApiModelProperty(value = "税号注册地址")
    private String taxnRegAddress ;
    
    /** 会计师通讯地址 */
    @ApiModelProperty(value = "会计师通讯地址")
    private String taxnAddrOfAccountant ;
    
    /** VAT证书生效日期 */
    @ApiModelProperty(value = "VAT证书生效日期")
    private Date taxnEffDateOfVat ;
    
    /** 申报方式 */
    @ApiModelProperty(value = "申报方式")
    private String taxnDecMethod ;
    
    /** 申报时间 */
    @ApiModelProperty(value = "申报时间")
    private String taxnDecTime ;
    
    /** 附件 */
    @ApiModelProperty(value = "附件")
    private String taxnFiles ;
    
    /** 注册税号邮箱 */
    @ApiModelProperty(value = "注册税号邮箱")
    private String taxnRegEmail ;
    
    /** 注册税号手机号 */
    @ApiModelProperty(value = "注册税号手机号")
    private String taxnRegTel ;
    
    /** 查账邮箱 */
    @ApiModelProperty(value = "查账邮箱")
    private String taxnAudEmail ;
    
    /** 备用邮箱1 */
    @ApiModelProperty(value = "备用邮箱1")
    private String taxnAltEmail1 ;
    
    /** 备用邮箱2 */
    @ApiModelProperty(value = "备用邮箱2")
    private String taxnAltEmail2 ;
    
    /** 税务代理机构 */
    @ApiModelProperty(value = "税务代理机构")
    private String taxnAgency ;
    
    /** 税务代理联系方式 */
    @ApiModelProperty(value = "税务代理联系方式")
    private String taxnAgencyTel ;
    
    /** 税务代理机构办公地址 */
    @ApiModelProperty(value = "税务代理机构办公地址")
    private String taxnAgencyAddress ;
    
    /** 税率 */
    @ApiModelProperty(value = "税率")
    private BigDecimal taxnRate ;
    
    /** 税号状态 */
    @ApiModelProperty(value = "税号状态")
    private String taxnState ;
    
    /** 税号类别 */
    @ApiModelProperty(value = "税号类别")
    private String taxnCate ;
    
    /** 有效范围(开始) */
    @ApiModelProperty(value = "有效范围(开始)")
    private Date taxnEffeRangeStart ;
    
    /** 有效范围(结束) */
    @ApiModelProperty(value = "有效范围(结束)")
    private Date taxnEffeRangeEnd ;
    
    /** 税务任务状态;税务任务状态(未完成、完成) */
    @ApiModelProperty(value = "税务任务状态")
    private String taxnTaskState ;

    /**
     * 保存 Or 提交
     */
    @ApiModelProperty(value = "保存 Or 提交")
    private String saveOrSubmit;

    /** 是否虚拟店铺 */
    @ApiModelProperty(value = "是否虚拟店铺")
    private BigDecimal isVirtualShop ;
    

}