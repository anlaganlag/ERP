package com.tadpole.cloud.operationManagement.api.shopEntity.model.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import java.util.Date;
import cn.stylefeng.guns.cloud.model.validator.BaseValidatingParam;
import cn.stylefeng.guns.cloud.model.web.request.BaseRequest;
import lombok.*;
import java.lang.*;
import java.math.BigDecimal;

 /**
 * 资源-公司实体证件印章;资源-公司实体证件印章
 * @author : LSY
 * @date : 2023-7-28
 */
@ApiModel(value = "资源-公司实体证件印章",description = "资源-公司实体证件印章")
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TbComEntityCertificateSealParam extends BaseRequest implements Serializable,BaseValidatingParam{
 private static final long serialVersionUID = 1L;
    /** 编号 */
    @ApiModelProperty(value = "编号")
    private BigDecimal pkCode ;
    
    /** 公司名称（中文） */
    @ApiModelProperty(value = "公司名称（中文）")
    private String comNameCn ;


    /** 公司来源 */
    @ApiModelProperty(value = "公司来源")
    private String comSource;


    @ApiModelProperty(value = "状态")
    private String comState ;

    /** 创建时间 */
    @ApiModelProperty(value = "创建时间")
    private Date sysCreateTime ;
    
    /** 最后更新时间 */
    @ApiModelProperty(value = "最后更新时间")
    private String busLastUpdDate ;
    
    /** 最后更新人 */
    @ApiModelProperty(value = "最后更新人")
    private String busLastUpdPerName ;
    
    /** 最后更新编号 */
    @ApiModelProperty(value = "最后更新编号")
    private String busLastUpdPerCode ;
    
    /** 营业执照（正本） */
    @ApiModelProperty(value = "营业执照（正本）")
    private BigDecimal busBusinessLicenseOrig ;
    
    /** 营业执照（副本） */
    @ApiModelProperty(value = "营业执照（副本）")
    private BigDecimal busBusinessLicenseDupl ;
    
    /** 基本存款账户信息 */
    @ApiModelProperty(value = "基本存款账户信息")
    private BigDecimal busBasicAccountInfo ;
    
    /** 核准号 */
    @ApiModelProperty(value = "核准号")
    private String busApprovalNum ;
    
    /** 外经贸备案登记表 */
    @ApiModelProperty(value = "外经贸备案登记表")
    private BigDecimal busForeignTradeRegist ;
    
    /** 备案登记表编号 */
    @ApiModelProperty(value = "备案登记表编号")
    private String busRegistrationFormNo ;
    
    /** 海关注册证书 */
    @ApiModelProperty(value = "海关注册证书")
    private BigDecimal busCustomsRegistCertificate ;
    
    /** 公章 */
    @ApiModelProperty(value = "公章")
    private BigDecimal busOfficialSeal ;
    
    /** 财务章 */
    @ApiModelProperty(value = "财务章")
    private BigDecimal busFinancialChapter ;
    
    /** 法人私章 */
    @ApiModelProperty(value = "法人私章")
    private BigDecimal busLegalPersonSeal ;
    
    /** 发票章 */
    @ApiModelProperty(value = "发票章")
    private BigDecimal busInvoiceSeal ;
    
    /** 合同专用章 */
    @ApiModelProperty(value = "合同专用章")
    private BigDecimal busContractSpecialSeal ;
    
    /** 仓库专用章 */
    @ApiModelProperty(value = "仓库专用章")
    private BigDecimal busWarehouseSpecialSeal ;
    

}