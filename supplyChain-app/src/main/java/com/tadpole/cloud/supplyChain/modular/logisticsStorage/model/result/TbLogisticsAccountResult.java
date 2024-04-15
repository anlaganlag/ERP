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
 * 物流账号;
 * @author : LSY
 * @date : 2023-12-29
 */
@ApiModel(value = "物流账号",description = "")
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ExcelIgnoreUnannotated
public class TbLogisticsAccountResult implements Serializable{
 private static final long serialVersionUID = 1L;
 
    /** 添加时间;添加时间 */
    @ApiModelProperty(value = "添加时间")
    @ExcelProperty(value ="添加时间")
    private Date sysAddDate ;
 
    /** 操作人;操作人 */
    @ApiModelProperty(value = "操作人")
    @ExcelProperty(value ="操作人")
    private String sysPerName ;
 
    /** 更新时间;更新时间 */
    @ApiModelProperty(value = "更新时间")
    @ExcelProperty(value ="更新时间")
    private Date sysUpdDatetime ;
 
    /** 物流账号;物流账号 */
    @ApiModelProperty(value = "物流账号")
    @ExcelProperty(value ="物流账号")
    private String lcCode ;
 
    /** 物流商编码;物流商编码 */
    @ApiModelProperty(value = "物流商编码")
    @ExcelProperty(value ="物流商编码")
    private String lpCode ;
 
    /** 公司名称;在物流供应商哪里开户的公司 */
    @ApiModelProperty(value = "公司名称")
    @ExcelProperty(value ="公司名称")
    private String comNameCn ;
 
    /** 状态;禁用，正常 */
    @ApiModelProperty(value = "状态")
    @ExcelProperty(value ="状态")
    private String lcState ;


}