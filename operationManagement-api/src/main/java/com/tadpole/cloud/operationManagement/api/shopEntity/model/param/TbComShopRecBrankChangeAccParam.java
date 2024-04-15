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
 * 资源-店铺收款银行账号变更;资源-店铺收款银行账号变更
 * @author : LSY
 * @date : 2023-7-28
 */
@ApiModel(value = "资源-店铺收款银行账号变更",description = "资源-店铺收款银行账号变更")
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TbComShopRecBrankChangeAccParam extends BaseRequest implements Serializable,BaseValidatingParam{
 private static final long serialVersionUID = 1L;
    /** 收款银行账号变更编号 */
    @ApiModelProperty(value = "收款银行账号变更编号")
    private BigDecimal sysId ;
    
    /** 店铺名称 */
    @ApiModelProperty(value = "店铺名称")
    private String shopName ;
    
    /** 店铺收款银行 */
    @ApiModelProperty(value = "店铺收款银行")
    private String shopColAccBank ;
    
    /** 店铺收款银行主体 */
    @ApiModelProperty(value = "店铺收款银行主体")
    private String shopColAccBankMainBody ;
    
    /** 店铺收款账号 */
    @ApiModelProperty(value = "店铺收款账号")
    private String shopColAccNo ;
    
    /** 店铺收款币种 */
    @ApiModelProperty(value = "店铺收款币种")
    private String shopColCurrency ;
    
    /** 收款账号邮箱 */
    @ApiModelProperty(value = "收款账号邮箱")
    private String shopColAccEmail ;
    
    /** SWIFT Code(BIC) */
    @ApiModelProperty(value = "SWIFT Code(BIC)")
    private String shopBic ;
    
    /** Routing number */
    @ApiModelProperty(value = "Routing number")
    private String shopRoutingNumber ;
    
    /** Bank State Branch(BSB) */
    @ApiModelProperty(value = "Bank State Branch(BSB)")
    private String shopBsb ;
    
    /** Bank Code */
    @ApiModelProperty(value = "Bank Code")
    private String shopBanKCode ;
    
    /** Branch Code */
    @ApiModelProperty(value = "Branch Code")
    private String shopBranchCode ;
    
    /** Account Type */
    @ApiModelProperty(value = "Account Type")
    private String shopAccountType ;
    
    /** Sort code */
    @ApiModelProperty(value = "Sort code")
    private String shopSortCode ;
    
    /** IBAN */
    @ApiModelProperty(value = "IBAN")
    private String shopIban ;
    
    /** 原店铺收款银行 */
    @ApiModelProperty(value = "原店铺收款银行")
    private String shopColAccBankOrig ;
    
    /** 原店铺收款银行主体 */
    @ApiModelProperty(value = "原店铺收款银行主体")
    private String shopColAccBankMainBodyOrig ;
    
    /** 原店铺收款账号 */
    @ApiModelProperty(value = "原店铺收款账号")
    private String shopColAccNoOrig ;
    
    /** 原店铺收款币种 */
    @ApiModelProperty(value = "原店铺收款币种")
    private String shopColCurrencyOrig ;
    
    /** 原收款账号邮箱 */
    @ApiModelProperty(value = "原收款账号邮箱")
    private String shopColAccEmailOrig ;
    
    /** 原SWIFT Code(BIC) */
    @ApiModelProperty(value = "原SWIFT Code(BIC)")
    private String shopBicOrig ;
    
    /** 原Routing number */
    @ApiModelProperty(value = "原Routing number")
    private String shopRoutingNumberOrig ;
    
    /** 原Bank State Branch(BSB) */
    @ApiModelProperty(value = "原Bank State Branch(BSB)")
    private String shopBsbOrig ;
    
    /** 原Bank Code */
    @ApiModelProperty(value = "原Bank Code")
    private String shopBanKCodeOrig ;
    
    /** 原Branch Code */
    @ApiModelProperty(value = "原Branch Code")
    private String shopBranchCodeOrig ;
    
    /** 原Account Type */
    @ApiModelProperty(value = "原Account Type")
    private String shopAccountTypeOrig ;
    
    /** 原Sort code */
    @ApiModelProperty(value = "原Sort code")
    private String shopSortCodeOrig ;
    
    /** 原IBAN */
    @ApiModelProperty(value = "原IBAN")
    private String shopIbanOrig ;
    
    /** 完成人姓名 */
    @ApiModelProperty(value = "完成人姓名")
    private String sysFinishPerName ;
    
    /** 完成人编号 */
    @ApiModelProperty(value = "完成人编号")
    private String sysFinishPerCode ;
    
    /** 完成时间 */
    @ApiModelProperty(value = "完成时间")
    private Date sysFinishDate ;
    
    /** 申请人姓名 */
    @ApiModelProperty(value = "申请人姓名")
    private String syApplyPerName2 ;
    
    /** 申请人编号 */
    @ApiModelProperty(value = "申请人编号")
    private String sysApplyPerCode2 ;
    
    /** 申请时间 */
    @ApiModelProperty(value = "申请时间")
    private Date sysApplyDate ;
    
    /** 接收人姓名 */
    @ApiModelProperty(value = "接收人姓名")
    private String sysRecPerName ;
    
    /** 接收人编号 */
    @ApiModelProperty(value = "接收人编号")
    private String sysRecPerCode ;
    
    /** 接收人时间 */
    @ApiModelProperty(value = "接收人时间")
    private Date sysRecDate ;
    
    /** 申请变更状态;申请变更状态(1:变更申请、2:接收确认、3:完成确认) */
    @ApiModelProperty(value = "申请变更状态")
    private BigDecimal sysAppChangeStatus ;
    
    /** 变更状态;变更状态(已申请、已变更) */
    @ApiModelProperty(value = "变更状态")
    private String sysChangeStatus ;

    @ApiModelProperty(value = "电商平台")
    private String elePlatformName ;

    @ApiModelProperty(value = "账号")
    private String shopNameSimple ;

    @ApiModelProperty(value = "站点")
    private String countryCode ;

    @ApiModelProperty(value = "店铺主体")
    private String shopMainBody ;
    

}