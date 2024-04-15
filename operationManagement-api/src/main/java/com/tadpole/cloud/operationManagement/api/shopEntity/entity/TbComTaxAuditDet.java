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
 * @desc : 资源-税号查账记录明细-资源-税号查账记录明细
 */
@TableName("Tb_Com_Tax_Audit_Det")
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ExcelIgnoreUnannotated
public class TbComTaxAuditDet implements Serializable{
 private static final long serialVersionUID = 1L;
    /** 编号 */
    @TableId(value = "Case_No_Det_ID", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "编号")
    private BigDecimal caseNoDetId ;
    
    /** Case No */
    @TableField("Case_No")
    @ApiModelProperty(value = "Case No")
    private String caseNo ;
    
    /** 查账日期 */
    @TableField("audit_Date")
    @ApiModelProperty(value = "查账日期")
    private Date auditDate ;
    
    /** 需提供资料 */
    @TableField("require_Info")
    @ApiModelProperty(value = "需提供资料")
    private String requireInfo ;
    
    /** 结果反馈 */
    @TableField("audit_Result")
    @ApiModelProperty(value = "结果反馈")
    private String auditResult ;
    
    /** 附件 */
    @TableField("Case_No_Det_Files")
    @ApiModelProperty(value = "附件")
    private String caseNoDetFiles ;

    @TableField("Case_No_Det_Files_Name")
    @ApiModelProperty(value = "附件名称")
    private String caseNoDetFilesName ;
    

}