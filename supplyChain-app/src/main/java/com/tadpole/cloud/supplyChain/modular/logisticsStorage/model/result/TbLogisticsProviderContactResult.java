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
 * 物流供应商联系信息;
 * @author : LSY
 * @date : 2023-12-29
 */
@ApiModel(value = "物流供应商联系信息",description = "")
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ExcelIgnoreUnannotated
public class TbLogisticsProviderContactResult implements Serializable{
 private static final long serialVersionUID = 1L;
 
    /** lpContactID */
    @ApiModelProperty(value = "lpContactID")
    @ExcelProperty(value ="lpContactID")
    private BigDecimal lpContactId ;
 
    /** 物流商编码 */
    @ApiModelProperty(value = "物流商编码")
    @ExcelProperty(value ="物流商编码")
    private String lpCode ;
 
    /** 联系人 */
    @ApiModelProperty(value = "联系人")
    @ExcelProperty(value ="联系人")
    private String lpContactName ;
 
    /** 职务 */
    @ApiModelProperty(value = "职务")
    @ExcelProperty(value ="职务")
    private String lpContactDuty ;
 
    /** 联系电话 */
    @ApiModelProperty(value = "联系电话")
    @ExcelProperty(value ="联系电话")
    private String lpContactTel ;
 
    /** 电子邮箱 */
    @ApiModelProperty(value = "电子邮箱")
    @ExcelProperty(value ="电子邮箱")
    private String lpContactEmail ;
 
    /** 更新时间 */
    @ApiModelProperty(value = "更新时间")
    @ExcelProperty(value ="更新时间")
    private Date sysUpdDatetime ;


}