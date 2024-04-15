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
 * @desc : 资源-税号查账记录-资源-税号查账记录
 */
@TableName("Tb_Com_Tax_Audit")
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ExcelIgnoreUnannotated
public class TbComTaxAudit implements Serializable{
 private static final long serialVersionUID = 1L;
    /** Case No */
    @TableId(value = "Case_No", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "Case No")
    private String caseNo ;
    
    /** 海外税号 */
    @TableField("taxn_Overseas")
    @ApiModelProperty(value = "海外税号")
    private String taxnOverseas ;
    
    /** 开始日期 */
    @TableField("tax_Audit_Date_B")
    @ApiModelProperty(value = "开始日期")
    private Date taxAuditDateB ;
    
    /** 结束日期 */
    @TableField("tax_Audit_Date_E")
    @ApiModelProperty(value = "结束日期")
    private Date taxAuditDateE ;
    

}