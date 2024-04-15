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
 * @desc : 资源-公司实体证件印章-资源-公司实体证件印章
 */
@TableName("Tb_Com_Entity_Certificate_Seal")
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ExcelIgnoreUnannotated
public class TbComEntityCertificateSeal implements Serializable{
 private static final long serialVersionUID = 1L;
    /** 编号 */
    @TableId(value = "pk_Code", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "编号")
    private BigDecimal pkCode ;
    
    /** 公司名称（中文） */
    @TableField("com_Name_Cn")
    @ApiModelProperty(value = "公司名称（中文）")
    private String comNameCn ;
    
    /** 创建时间 */
    @TableField("sys_Create_Time")
    @ApiModelProperty(value = "创建时间")
    private Date sysCreateTime ;
    
    /** 最后更新时间 */
    @TableField("bus_Last_Upd_Date")
    @ApiModelProperty(value = "最后更新时间")
    private String busLastUpdDate ;
    
    /** 最后更新人 */
    @TableField("bus_Last_Upd_Per_Name")
    @ApiModelProperty(value = "最后更新人")
    private String busLastUpdPerName ;
    
    /** 最后更新编号 */
    @TableField("bus_Last_Upd_Per_Code")
    @ApiModelProperty(value = "最后更新编号")
    private String busLastUpdPerCode ;
    
    /** 营业执照（正本） */
    @TableField("bus_Business_License_Orig")
    @ApiModelProperty(value = "营业执照（正本）")
    private BigDecimal busBusinessLicenseOrig ;
    
    /** 营业执照（副本） */
    @TableField("bus_Business_License_Dupl")
    @ApiModelProperty(value = "营业执照（副本）")
    private BigDecimal busBusinessLicenseDupl ;
    
    /** 基本存款账户信息 */
    @TableField("bus_Basic_Account_Info")
    @ApiModelProperty(value = "基本存款账户信息")
    private BigDecimal busBasicAccountInfo ;
    
    /** 核准号 */
    @TableField("bus_Approval_Num")
    @ApiModelProperty(value = "核准号")
    private String busApprovalNum ;
    
    /** 外经贸备案登记表 */
    @TableField("bus_Foreign_Trade_Regist")
    @ApiModelProperty(value = "外经贸备案登记表")
    private BigDecimal busForeignTradeRegist ;
    
    /** 备案登记表编号 */
    @TableField("bus_Registration_Form_No")
    @ApiModelProperty(value = "备案登记表编号")
    private String busRegistrationFormNo ;
    
    /** 海关注册证书 */
    @TableField("bus_Customs_Regist_Certificate")
    @ApiModelProperty(value = "海关注册证书")
    private BigDecimal busCustomsRegistCertificate ;
    
    /** 公章 */
    @TableField("bus_Official_Seal")
    @ApiModelProperty(value = "公章")
    private BigDecimal busOfficialSeal ;
    
    /** 财务章 */
    @TableField("bus_Financial_Chapter")
    @ApiModelProperty(value = "财务章")
    private BigDecimal busFinancialChapter ;
    
    /** 法人私章 */
    @TableField("bus_Legal_Person_Seal")
    @ApiModelProperty(value = "法人私章")
    private BigDecimal busLegalPersonSeal ;
    
    /** 发票章 */
    @TableField("bus_Invoice_Seal")
    @ApiModelProperty(value = "发票章")
    private BigDecimal busInvoiceSeal ;
    
    /** 合同专用章 */
    @TableField("bus_Contract_Special_Seal")
    @ApiModelProperty(value = "合同专用章")
    private BigDecimal busContractSpecialSeal ;
    
    /** 仓库专用章 */
    @TableField("bus_Warehouse_Special_Seal")
    @ApiModelProperty(value = "仓库专用章")
    private BigDecimal busWarehouseSpecialSeal ;
    

}