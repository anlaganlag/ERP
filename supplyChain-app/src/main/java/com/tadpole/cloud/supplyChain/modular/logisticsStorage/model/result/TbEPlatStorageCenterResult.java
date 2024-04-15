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
 * 电商平台仓储中心;
 * @author : LSY
 * @date : 2024-1-2
 */
@ApiModel(value = "电商平台仓储中心",description = "")
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ExcelIgnoreUnannotated
public class TbEPlatStorageCenterResult implements Serializable{
 private static final long serialVersionUID = 1L;
 
    /** ID */
    @ApiModelProperty(value = "ID")
    @ExcelProperty(value ="ID")
    private BigDecimal id ;
 
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
 
    /** 平台名称 */
    @ApiModelProperty(value = "平台名称")
    @ExcelProperty(value ="平台名称")
    private String elePlatformName ;
 
    /** 国家编码 */
    @ApiModelProperty(value = "国家编码")
    @ExcelProperty(value ="国家编码")
    private String countryCode ;
 
    /** FBA编码 */
    @ApiModelProperty(value = "FBA编码")
    @ExcelProperty(value ="FBA编码")
    private String fbaCode ;
 
    /** 城市 */
    @ApiModelProperty(value = "城市")
    @ExcelProperty(value ="城市")
    private String city ;
 
    /** 状态 */
    @ApiModelProperty(value = "状态")
    @ExcelProperty(value ="状态")
    private String state ;
 
    /** 邮编 */
    @ApiModelProperty(value = "邮编")
    @ExcelProperty(value ="邮编")
    private String zip ;
 
    /** 地址 */
    @ApiModelProperty(value = "地址")
    @ExcelProperty(value ="地址")
    private String address ;


}