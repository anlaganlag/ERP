package com.tadpole.cloud.operationManagement.api.shopEntity.model.param;

import cn.stylefeng.guns.cloud.model.validator.BaseValidatingParam;
import cn.stylefeng.guns.cloud.model.web.request.BaseRequest;
import com.baomidou.mybatisplus.annotation.TableField;
import com.tadpole.cloud.operationManagement.api.shopEntity.entity.TbComEntityAccount;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 资源-公司实体;资源-公司实体
 * @author : LSY
 * @date : 2023-7-28
 */
@ApiModel(value = "资源-公司实体",description = "资源-公司实体")
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TbComEntityParam extends BaseRequest implements Serializable,BaseValidatingParam{
 private static final long serialVersionUID = 1L;
    /** 公司名称（中文） */
    @ApiModelProperty(value = "公司名称（中文）")
    private String comNameCn ;
    
    /** 公司名称（英文） */
    @ApiModelProperty(value = "公司名称（英文）")
    private String comNameEn ;
    
    /** 区域 */
    @ApiModelProperty(value = "区域")
    private String comArea ;
    
    /** 公司类型 */
    @ApiModelProperty(value = "公司类型")
    private String comType ;
    
    /** 公司性质 */
    @ApiModelProperty(value = "公司性质")
    private String comNature ;
    
    /** 统一社会信用代码 */
    @ApiModelProperty(value = "统一社会信用代码")
    private String comUniSocCreCode ;
    
    /** 增值税纳税人 */
    @ApiModelProperty(value = "增值税纳税人")
    private String comVatPayer ;
    
    /** 纳税信用等级 */
    @ApiModelProperty(value = "纳税信用等级")
    private String comTaxCreRating ;
    
    /** 海关编号 */
    @ApiModelProperty(value = "海关编号")
    private String comCustomsNo ;
    
    /** CPB Bond No */
    @ApiModelProperty(value = "CPB Bond No")
    private String comCpbBondNo ;
    
    /** Bond有效期 */
    @ApiModelProperty(value = "Bond有效期")
    private Date comBondValDate ;
    
    /** Importer No */
    @ApiModelProperty(value = "Importer No")
    private String comImporterNo ;
    
    /** 法人 */
    @ApiModelProperty(value = "法人")
    private String comLegPerson ;
    
    /** 监事 */
    @ApiModelProperty(value = "监事")
    private String comSupervisor ;
    
    /** 股东1 */
    @ApiModelProperty(value = "股东1")
    private String comShareholder1 ;
    
    /** 股东1股份占比 */
    @ApiModelProperty(value = "股东1股份占比")
    private String comShareProportion1 ;
    
    /** 股东2 */
    @ApiModelProperty(value = "股东2")
    private String comShareholder2 ;
    
    /** 股东2股份占比 */
    @ApiModelProperty(value = "股东2股份占比")
    private String comShareProportion2 ;
    
    /** 股东3 */
    @ApiModelProperty(value = "股东3")
    private String comShareholder3 ;
    
    /** 股东3股份占比 */
    @ApiModelProperty(value = "股东3股份占比")
    private String comShareProportion3 ;
    
    /** 注册资金 */
    @ApiModelProperty(value = "注册资金")
    private String comRegCapital ;
    
    /** 经营期限 */
    @ApiModelProperty(value = "经营期限")
    private String comTermOfOperation ;
    
    /** 公司电话 */
    @ApiModelProperty(value = "公司电话")
    private String comTel ;
    
    /** 地址（中文） */
    @ApiModelProperty(value = "地址（中文）")
    private String comAddrCn ;
    
    /** 地址（英文） */
    @ApiModelProperty(value = "地址（英文）")
    private String comAddrEn ;
    
    /** 邮编 */
    @ApiModelProperty(value = "邮编")
    private String comZipCode ;
    
    /** 经营范围 */
    @ApiModelProperty(value = "经营范围")
    private String comBusiScope ;
    
    /** 状态 */
    @ApiModelProperty(value = "状态")
    private String comState ;
    
    /** 营业执照 */
    @ApiModelProperty(value = "营业执照")
    private String comBusiLicense ;
    
    /** 公司来源 */
    @ApiModelProperty(value = "公司来源")
    private String comSource ;
    
    /** 税务类型 */
    @ApiModelProperty(value = "税务类型")
    private String comTaxType ;


   @ApiModelProperty(value = "营业执照（正本）")
   private BigDecimal busBusinessLicenseOrig ;

   /** 营业执照（副本） */
   @ApiModelProperty(value = "营业执照（副本）")
   private BigDecimal busBusinessLicenseDupl ;

   /** 公司实体账户信息List */
   @ApiModelProperty(value = "公司实体账户信息List")
   private List<TbComEntityAccount> comEntityAccountList ;

}