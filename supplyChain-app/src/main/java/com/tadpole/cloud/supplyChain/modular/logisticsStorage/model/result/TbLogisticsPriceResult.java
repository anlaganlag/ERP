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
 * 物流价格--暂时不需要;
 * @author : LSY
 * @date : 2023-12-29
 */
@ApiModel(value = "物流价格--暂时不需要",description = "")
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ExcelIgnoreUnannotated
public class TbLogisticsPriceResult implements Serializable{
 private static final long serialVersionUID = 1L;
 
    /** logpID */
    @ApiModelProperty(value = "logpID")
    @ExcelProperty(value ="logpID")
    private BigDecimal logpId ;
 
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
 
    /** 国家编码 */
    @ApiModelProperty(value = "国家编码")
    @ExcelProperty(value ="国家编码")
    private String countryCode ;
 
    /** 国家分区 */
    @ApiModelProperty(value = "国家分区")
    @ExcelProperty(value ="国家分区")
    private String lspNum ;
 
    /** 收货分区 */
    @ApiModelProperty(value = "收货分区")
    @ExcelProperty(value ="收货分区")
    private String countryAreaName ;
 
    /** 货运方式1 */
    @ApiModelProperty(value = "货运方式1")
    @ExcelProperty(value ="货运方式1")
    private String logTraMode1 ;
 
    /** 货运方式2 */
    @ApiModelProperty(value = "货运方式2")
    @ExcelProperty(value ="货运方式2")
    private String logTraMode2 ;
 
    /** 物流渠道 */
    @ApiModelProperty(value = "物流渠道")
    @ExcelProperty(value ="物流渠道")
    private String logSeaTraRoute ;
 
    /** 海运货柜类型 */
    @ApiModelProperty(value = "海运货柜类型")
    @ExcelProperty(value ="海运货柜类型")
    private String logSeaTraConType ;
 
    /** 红蓝单 */
    @ApiModelProperty(value = "红蓝单")
    @ExcelProperty(value ="红蓝单")
    private String logRedOrBlueList ;
 
    /** 货物特性 */
    @ApiModelProperty(value = "货物特性")
    @ExcelProperty(value ="货物特性")
    private String logGoodCharacter ;
 
    /** 是否含税 */
    @ApiModelProperty(value = "是否含税")
    @ExcelProperty(value ="是否含税")
    private Integer logpIsIncTax ;
 
    /** 计费方式 */
    @ApiModelProperty(value = "计费方式")
    @ExcelProperty(value ="计费方式")
    private String logpChargType ;


}