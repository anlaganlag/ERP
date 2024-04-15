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
 * 物流价格附加项目--暂时不需要;
 * @author : LSY
 * @date : 2023-12-29
 */
@ApiModel(value = "物流价格附加项目--暂时不需要",description = "")
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ExcelIgnoreUnannotated
public class TbLogisticsPriceSurchargeItemsResult implements Serializable{
 private static final long serialVersionUID = 1L;
 
    /** lpsiID */
    @ApiModelProperty(value = "lpsiID")
    @ExcelProperty(value ="lpsiID")
    private BigDecimal lpsiId ;
 
    /** logpID */
    @ApiModelProperty(value = "logpID")
    @ExcelProperty(value ="logpID")
    private BigDecimal logpId ;
 
    /** 操作人 */
    @ApiModelProperty(value = "操作人")
    @ExcelProperty(value ="操作人")
    private String sysPerName ;
 
    /** 更新时间 */
    @ApiModelProperty(value = "更新时间")
    @ExcelProperty(value ="更新时间")
    private Date sysUpdDatetime ;
 
    /** 项目类别 */
    @ApiModelProperty(value = "项目类别")
    @ExcelProperty(value ="项目类别")
    private String lpsiItemType ;
 
    /** 项目名称 */
    @ApiModelProperty(value = "项目名称")
    @ExcelProperty(value ="项目名称")
    private String lpsiItemName ;
 
    /** 币种 */
    @ApiModelProperty(value = "币种")
    @ExcelProperty(value ="币种")
    private String lpsiCurrSystem ;
 
    /** 单价费用 */
    @ApiModelProperty(value = "单价费用")
    @ExcelProperty(value ="单价费用")
    private BigDecimal lpsiUnitPrice ;
 
    /** 适用开始日期 */
    @ApiModelProperty(value = "适用开始日期")
    @ExcelProperty(value ="适用开始日期")
    private Date lpcswAppStartDate ;
 
    /** 适用结束日期 */
    @ApiModelProperty(value = "适用结束日期")
    @ExcelProperty(value ="适用结束日期")
    private Date lpcswAppEndDate ;


}