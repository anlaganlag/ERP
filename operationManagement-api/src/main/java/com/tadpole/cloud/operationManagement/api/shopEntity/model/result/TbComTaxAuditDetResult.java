package com.tadpole.cloud.operationManagement.api.shopEntity.model.result;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

 /**
 * 资源-税号查账记录明细;资源-税号查账记录明细
 * @author : LSY
 * @date : 2023-7-28
 */
@ApiModel(value = "资源-税号查账记录明细",description = "资源-税号查账记录明细")
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ExcelIgnoreUnannotated
public class TbComTaxAuditDetResult  implements Serializable{
 private static final long serialVersionUID = 1L;
    /** 编号 */
    @ApiModelProperty(value = "编号")
    @ExcelProperty(value ="编号")
    private BigDecimal caseNoDetId ;
    
    /** Case No */
    @ApiModelProperty(value = "Case No")
    @ExcelProperty(value ="Case No")
    private String caseNo ;
    
    /** 查账日期 */
    @ApiModelProperty(value = "查账日期")
    @ExcelProperty(value ="查账日期")
    private Date auditDate ;
    
    /** 需提供资料 */
    @ApiModelProperty(value = "需提供资料")
    @ExcelProperty(value ="需提供资料")
    private String requireInfo ;
    
    /** 结果反馈 */
    @ApiModelProperty(value = "结果反馈")
    @ExcelProperty(value ="结果反馈")
    private String auditResult ;
    
    /** 附件 */
    @ApiModelProperty(value = "附件")
    @ExcelProperty(value ="附件")
    private String caseNoDetFiles ;



    @ApiModelProperty(value = "附件名称")
    private String caseNoDetFilesName ;
}