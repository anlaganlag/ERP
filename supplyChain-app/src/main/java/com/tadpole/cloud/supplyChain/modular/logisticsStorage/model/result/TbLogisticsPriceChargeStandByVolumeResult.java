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
 * 物流价格按体积计费的--暂时不需要;
 * @author : LSY
 * @date : 2023-12-29
 */
@ApiModel(value = "物流价格按体积计费的--暂时不需要",description = "")
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ExcelIgnoreUnannotated
public class TbLogisticsPriceChargeStandByVolumeResult implements Serializable{
 private static final long serialVersionUID = 1L;
 
    /** lpcsvID */
    @ApiModelProperty(value = "lpcsvID")
    @ExcelProperty(value ="lpcsvID")
    private BigDecimal lpcsvId ;
 
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
 
    /** 体积大于等于 */
    @ApiModelProperty(value = "体积大于等于")
    @ExcelProperty(value ="体积大于等于")
    private BigDecimal lpcsvVolumeL ;
 
    /** 体积小于 */
    @ApiModelProperty(value = "体积小于")
    @ExcelProperty(value ="体积小于")
    private BigDecimal lpcsvVolumetH ;
 
    /** 币种 */
    @ApiModelProperty(value = "币种")
    @ExcelProperty(value ="币种")
    private String lpcsvCurrSystem ;
 
    /** 单价费用 */
    @ApiModelProperty(value = "单价费用")
    @ExcelProperty(value ="单价费用")
    private BigDecimal lpcsvUnitPrice ;
 
    /** 适用日期起 */
    @ApiModelProperty(value = "适用日期起")
    @ExcelProperty(value ="适用日期起")
    private Date lpcsvAppDate ;


}