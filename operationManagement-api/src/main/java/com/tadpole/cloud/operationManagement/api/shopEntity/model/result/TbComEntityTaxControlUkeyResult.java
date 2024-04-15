package com.tadpole.cloud.operationManagement.api.shopEntity.model.result;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

import lombok.*;
import java.lang.*;
import java.math.BigDecimal;
import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;

 /**
 * 资源-公司实体银行设备税控UKEY;资源-公司实体银行设备税控UKEY
 * @author : LSY
 * @date : 2023-7-28
 */
@ApiModel(value = "资源-公司实体银行设备税控UKEY",description = "资源-公司实体银行设备税控UKEY")
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ExcelIgnoreUnannotated
public class TbComEntityTaxControlUkeyResult  implements Serializable{
 private static final long serialVersionUID = 1L;
    /** 编号 */
    @ApiModelProperty(value = "编号")
    @ExcelProperty(value ="编号")
    private BigDecimal pkCode ;
    
    /** 编号2;编号 */
    @ApiModelProperty(value = "编号2")
    @ExcelProperty(value ="编号2")
    private BigDecimal pkBeCode ;
    
    /** 税控UKEY的编号 */
    @ApiModelProperty(value = "税控UKEY的编号")
    @ExcelProperty(value ="税控UKEY的编号")
    private String busTaxControlUkeyNo ;
    

}