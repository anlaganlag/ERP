package com.tadpole.cloud.operationManagement.api.shopEntity.entity;

import java.io.Serializable;
import java.util.Date;
import java.lang.*;
import lombok.*;
import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;

 /**
 * @author : LSY
 * @date : 2023-7-28
 * @desc : 资源-公司实体-资源-公司实体
 */
@TableName("Tb_Com_Entity")
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ExcelIgnoreUnannotated
public class TbComEntity implements Serializable{
 private static final long serialVersionUID = 1L;
    /** 公司名称（中文） */
    @TableId(value = "com_Name_Cn", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "公司名称（中文）")
    private String comNameCn ;
    
    /** 公司名称（英文） */
    @TableField("com_Name_En")
    @ApiModelProperty(value = "公司名称（英文）")
    private String comNameEn ;
    
    /** 区域 */
    @TableField("com_Area")
    @ApiModelProperty(value = "区域")
    private String comArea ;
    
    /** 公司类型 */
    @TableField("com_Type")
    @ApiModelProperty(value = "公司类型")
    private String comType ;
    
    /** 公司性质 */
    @TableField("com_Nature")
    @ApiModelProperty(value = "公司性质")
    private String comNature ;
    
    /** 统一社会信用代码 */
    @TableField("com_Uni_Soc_Cre_Code")
    @ApiModelProperty(value = "统一社会信用代码")
    private String comUniSocCreCode ;
    
    /** 增值税纳税人 */
    @TableField("com_Vat_Payer")
    @ApiModelProperty(value = "增值税纳税人")
    private String comVatPayer ;
    
    /** 纳税信用等级 */
    @TableField("com_Tax_Cre_Rating")
    @ApiModelProperty(value = "纳税信用等级")
    private String comTaxCreRating ;
    
    /** 海关编号 */
    @TableField("com_Customs_No")
    @ApiModelProperty(value = "海关编号")
    private String comCustomsNo ;
    
    /** CPB Bond No */
    @TableField("com_Cpb_Bond_No")
    @ApiModelProperty(value = "CPB Bond No")
    private String comCpbBondNo ;
    
    /** Bond有效期 */
    @TableField("Com_Bond_Val_Date")
    @ApiModelProperty(value = "Bond有效期")
    private Date comBondValDate ;
    
    /** Importer No */
    @TableField("com_Importer_No")
    @ApiModelProperty(value = "Importer No")
    private String comImporterNo ;
    
    /** 法人 */
    @TableField("com_Leg_Person")
    @ApiModelProperty(value = "法人")
    private String comLegPerson ;
    
    /** 监事 */
    @TableField("com_Supervisor")
    @ApiModelProperty(value = "监事")
    private String comSupervisor ;
    
    /** 股东1 */
    @TableField("com_Shareholder1")
    @ApiModelProperty(value = "股东1")
    private String comShareholder1 ;
    
    /** 股东1股份占比 */
    @TableField("com_Share_Proportion1")
    @ApiModelProperty(value = "股东1股份占比")
    private String comShareProportion1 ;
    
    /** 股东2 */
    @TableField("com_Shareholder2")
    @ApiModelProperty(value = "股东2")
    private String comShareholder2 ;
    
    /** 股东2股份占比 */
    @TableField("com_Share_Proportion2")
    @ApiModelProperty(value = "股东2股份占比")
    private String comShareProportion2 ;
    
    /** 股东3 */
    @TableField("com_Shareholder3")
    @ApiModelProperty(value = "股东3")
    private String comShareholder3 ;
    
    /** 股东3股份占比 */
    @TableField("com_Share_Proportion3")
    @ApiModelProperty(value = "股东3股份占比")
    private String comShareProportion3 ;
    
    /** 注册资金 */
    @TableField("com_Reg_Capital")
    @ApiModelProperty(value = "注册资金")
    private String comRegCapital ;
    
    /** 经营期限 */
    @TableField("com_Term_Of_Operation")
    @ApiModelProperty(value = "经营期限")
    private String comTermOfOperation ;
    
    /** 公司电话 */
    @TableField("com_Tel")
    @ApiModelProperty(value = "公司电话")
    private String comTel ;
    
    /** 地址（中文） */
    @TableField("com_Addr_Cn")
    @ApiModelProperty(value = "地址（中文）")
    private String comAddrCn ;
    
    /** 地址（英文） */
    @TableField("com_Addr_En")
    @ApiModelProperty(value = "地址（英文）")
    private String comAddrEn ;
    
    /** 邮编 */
    @TableField("com_Zip_Code")
    @ApiModelProperty(value = "邮编")
    private String comZipCode ;
    
    /** 经营范围 */
    @TableField("com_Busi_Scope")
    @ApiModelProperty(value = "经营范围")
    private String comBusiScope ;
    
    /** 状态 */
    @TableField("com_State")
    @ApiModelProperty(value = "状态")
    private String comState ;
    
    /** 营业执照 */
    @TableField("com_Busi_License")
    @ApiModelProperty(value = "营业执照")
    private String comBusiLicense ;
    
    /** 公司来源 */
    @TableField("com_Source")
    @ApiModelProperty(value = "公司来源")
    private String comSource ;
    
    /** 税务类型 */
    @TableField("com_Tax_Type")
    @ApiModelProperty(value = "税务类型")
    private String comTaxType ;
    

}