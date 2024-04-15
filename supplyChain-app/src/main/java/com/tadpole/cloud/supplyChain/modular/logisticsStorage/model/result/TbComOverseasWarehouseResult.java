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
 * 海外仓信息;
 * @author : LSY
 * @date : 2024-1-19
 */
@ApiModel(value = "海外仓信息",description = "")
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ExcelIgnoreUnannotated
public class TbComOverseasWarehouseResult implements Serializable{
 private static final long serialVersionUID = 1L;
 
    /** 仓库名称 */
    @ApiModelProperty(value = "仓库名称")
    @ExcelProperty(value ="仓库名称")
    private String owName ;
 
    /** 建仓日期 */
    @ApiModelProperty(value = "建仓日期")
    @ExcelProperty(value ="建仓日期")
    private Date owCreatDate ;
 
    /** 站点 */
    @ApiModelProperty(value = "站点")
    @ExcelProperty(value ="站点")
    private String countryCode ;
 
    /** 仓库状态 */
    @ApiModelProperty(value = "仓库状态")
    @ExcelProperty(value ="仓库状态")
    private String owState ;
 
    /** 仓库地址 */
    @ApiModelProperty(value = "仓库地址")
    @ExcelProperty(value ="仓库地址")
    private String owAddress ;
 
    /** 仓库联系人 */
    @ApiModelProperty(value = "仓库联系人")
    @ExcelProperty(value ="仓库联系人")
    private String owPerson ;
 
    /** 联系电话 */
    @ApiModelProperty(value = "联系电话")
    @ExcelProperty(value ="联系电话")
    private String owTel ;
 
    /** 邮箱 */
    @ApiModelProperty(value = "邮箱")
    @ExcelProperty(value ="邮箱")
    private String owEmail ;
 
    /** 电商平台 */
    @ApiModelProperty(value = "电商平台")
    @ExcelProperty(value ="电商平台")
    private String elePlatformName ;


}