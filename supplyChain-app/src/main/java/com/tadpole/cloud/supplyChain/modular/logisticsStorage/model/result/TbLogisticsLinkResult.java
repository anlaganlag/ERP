package com.tadpole.cloud.supplyChain.modular.logisticsStorage.model.result;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

 /**
 * 物流信息查询;
 * @author : LSY
 * @date : 2023-12-29
 */
@ApiModel(value = "物流信息查询",description = "")
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ExcelIgnoreUnannotated
public class TbLogisticsLinkResult implements Serializable{
 private static final long serialVersionUID = 1L;
 
    /** ID */
    @ApiModelProperty(value = "ID")
    @ExcelProperty(value ="ID")
    private BigDecimal id ;
 
    /** 添加时间 */
    @ApiModelProperty(value = "添加时间")
    @ExcelProperty(value ="添加时间")
    private Date sysAddDate ;
 
    /** 操作人 */
    @ApiModelProperty(value = "操作人")
    @ExcelProperty(value ="操作人")
    private String sysPerName ;
 
    /** 更新时间 */
    @ApiModelProperty(value = "更新时间")
    @ExcelProperty(value ="更新时间")
    private Date sysUpdDatetime ;
 
    /** 货运方式1 */
    @ApiModelProperty(value = "货运方式1")
    @ExcelProperty(value ="货运方式1")
    private String logTraMode1 ;
 
    /** 物流单链接模板 */
    @ApiModelProperty(value = "物流单链接模板")
    @ExcelProperty(value ="物流单链接模板")
    private String logListLinkTemp ;

}