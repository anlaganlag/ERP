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
 * 物流账单;
 * @author : LSY
 * @date : 2023-12-29
 */
@ApiModel(value = "物流账单",description = "")
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ExcelIgnoreUnannotated
public class TbLogisticsBillResult implements Serializable{
 private static final long serialVersionUID = 1L;
 
    /** 系统编号 */
    @ApiModelProperty(value = "系统编号")
    @ExcelProperty(value ="系统编号")
    private BigDecimal id ;
 
    /** 物流对账单号 */
    @ApiModelProperty(value = "物流对账单号")
    @ExcelProperty(value ="物流对账单号")
    private String billNum ;
 
    /** 费用到期日 */
    @ApiModelProperty(value = "费用到期日")
    @ExcelProperty(value ="费用到期日")
    private Date expenseDueDate ;
 
    /** 物流账号 */
    @ApiModelProperty(value = "物流账号")
    @ExcelProperty(value ="物流账号")
    private String lcCode ;
 
    /** 物流商编码 */
    @ApiModelProperty(value = "物流商编码")
    @ExcelProperty(value ="物流商编码")
    private String lpCode ;
 
    /** 物流商简称 */
    @ApiModelProperty(value = "物流商简称")
    @ExcelProperty(value ="物流商简称")
    private String lpSimpleName ;
 
    /** 总箱数 */
    @ApiModelProperty(value = "总箱数")
    @ExcelProperty(value ="总箱数")
    private BigDecimal totalBoxNum ;
 
    /** 总实重（输入重量） */
    @ApiModelProperty(value = "总实重（输入重量）")
    @ExcelProperty(value ="总实重（输入重量）")
    private BigDecimal totalRealWeight ;
 
    /** 总体积重 */
    @ApiModelProperty(value = "总体积重")
    @ExcelProperty(value ="总体积重")
    private BigDecimal totalVolumeWeight ;
 
    /** 总计费重 */
    @ApiModelProperty(value = "总计费重")
    @ExcelProperty(value ="总计费重")
    private BigDecimal totalWeight ;
 
    /** 总数量 */
    @ApiModelProperty(value = "总数量")
    @ExcelProperty(value ="总数量")
    private Integer totalQuantity ;
 
    /** 总金额原币 */
    @ApiModelProperty(value = "总金额原币")
    @ExcelProperty(value ="总金额原币")
    private BigDecimal totalAmountToOrigin ;
 
    /** 总金额人民币 */
    @ApiModelProperty(value = "总金额人民币")
    @ExcelProperty(value ="总金额人民币")
    private BigDecimal totalAmountToRmb ;
 
    /** 是否已请款 */
    @ApiModelProperty(value = "是否已请款")
    @ExcelProperty(value ="是否已请款")
    private Integer hasPleasePay ;
 
    /** 请款人 */
    @ApiModelProperty(value = "请款人")
    @ExcelProperty(value ="请款人")
    private String pleasePayer ;
 
    /** 请款日期 */
    @ApiModelProperty(value = "请款日期")
    @ExcelProperty(value ="请款日期")
    private Date pleasrPayDate ;
 
    /** 是否已审核 */
    @ApiModelProperty(value = "是否已审核")
    @ExcelProperty(value ="是否已审核")
    private BigDecimal hasCheck ;
 
    /** 审核人 */
    @ApiModelProperty(value = "审核人")
    @ExcelProperty(value ="审核人")
    private String checker ;
 
    /** 审核日期 */
    @ApiModelProperty(value = "审核日期")
    @ExcelProperty(value ="审核日期")
    private Date checkDate ;
 
    /** 付款日期 */
    @ApiModelProperty(value = "付款日期")
    @ExcelProperty(value ="付款日期")
    private Date payDate ;
 
    /** 是否已抵扣 */
    @ApiModelProperty(value = "是否已抵扣")
    @ExcelProperty(value ="是否已抵扣")
    private Integer hasDeduction ;
 
    /** 添加日期 */
    @ApiModelProperty(value = "添加日期")
    @ExcelProperty(value ="添加日期")
    private Date addDate ;
 
    /** 操作人 */
    @ApiModelProperty(value = "操作人")
    @ExcelProperty(value ="操作人")
    private String operator ;
 
    /** 工号 */
    @ApiModelProperty(value = "工号")
    @ExcelProperty(value ="工号")
    private String operatorCode ;


}