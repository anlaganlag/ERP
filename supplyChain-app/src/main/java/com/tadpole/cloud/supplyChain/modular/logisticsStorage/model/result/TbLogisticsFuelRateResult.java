package com.tadpole.cloud.supplyChain.modular.logisticsStorage.model.result;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.Date;
import lombok.*;
import java.lang.*;
import java.math.BigDecimal;
import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;

 /**
 * 物流燃料费率;
 * @author : LSY
 * @date : 2023-12-29
 */
@ApiModel(value = "物流燃料费率",description = "")
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ExcelIgnoreUnannotated
public class TbLogisticsFuelRateResult implements Serializable{
 private static final long serialVersionUID = 1L;
 
    /** lfrID */
    @ApiModelProperty(value = "lfrID")
    @ExcelProperty(value ="lfrID")
    private BigDecimal lfrId ;
 
    /** 添加时间 */
    @ApiModelProperty(value = "添加时间")
    @ExcelProperty(value ="添加时间")
    private Date sysAddDate ;
 
    /** 物流商编码 */
    @ApiModelProperty(value = "物流商编码")
    @ExcelProperty(value ="物流商编码")
    private String lpCode ;
 
    /** 燃油费率 */
    @ApiModelProperty(value = "燃油费率")
    @ExcelProperty(value ="燃油费率")
    private BigDecimal lfrRuelRate ;
 
    /** 生效日期 */
    @ApiModelProperty(value = "生效日期")
    @ExcelProperty(value ="生效日期")
    private Date effectiveDate ;


}