package com.tadpole.cloud.operationManagement.api.shopEntity.model.result;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 资源-税号查账记录;资源-税号查账记录
 * @author : LSY
 * @date : 2023-7-28
 */
@ApiModel(value = "资源-税号查账记录",description = "资源-税号查账记录")
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ExcelIgnoreUnannotated
public class TbComTaxAuditResult  implements Serializable{
 private static final long serialVersionUID = 1L;
    /** Case No */
    @ApiModelProperty(value = "Case No")
    @ExcelProperty(value ="Case No")
    private String caseNo ;
    
    /** 海外税号 */
    @ApiModelProperty(value = "海外税号")
    @ExcelProperty(value ="海外税号")
    private String taxnOverseas ;
    
    /** 开始日期 */
    @ApiModelProperty(value = "开始日期")
    @ExcelProperty(value ="开始日期")
    private Date taxAuditDateB ;
    
    /** 结束日期 */
    @ApiModelProperty(value = "结束日期")
    @ExcelProperty(value ="结束日期")
    private Date taxAuditDateE ;

   @ApiModelProperty(value = "查账明细列表")
   private List<TbComTaxAuditDetResult> comTaxAuditDetList = new ArrayList<TbComTaxAuditDetResult>();
    

}