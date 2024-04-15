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
 * 物流调拨记录明细;
 * @author : LSY
 * @date : 2023-12-29
 */
@ApiModel(value = "物流调拨记录明细",description = "")
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ExcelIgnoreUnannotated
public class TbLogisticsTransferRecordDetResult implements Serializable{
 private static final long serialVersionUID = 1L;
 
    /** 系统编号 */
    @ApiModelProperty(value = "系统编号")
    @ExcelProperty(value ="系统编号")
    private BigDecimal sysDetId ;
 
    /** 上级系统编号 */
    @ApiModelProperty(value = "上级系统编号")
    @ExcelProperty(value ="上级系统编号")
    private Integer sysId ;
 
    /** 物料编码 */
    @ApiModelProperty(value = "物料编码")
    @ExcelProperty(value ="物料编码")
    private String mateCode ;
 
    /** 箱条码 */
    @ApiModelProperty(value = "箱条码")
    @ExcelProperty(value ="箱条码")
    private String packDetBoxCode ;
 
    /** 箱序号 */
    @ApiModelProperty(value = "箱序号")
    @ExcelProperty(value ="箱序号")
    private String packDetBoxNum ;
 
    /** Sku */
    @ApiModelProperty(value = "Sku")
    @ExcelProperty(value ="Sku")
    private String sku ;
 
    /** FnSku */
    @ApiModelProperty(value = "FnSku")
    @ExcelProperty(value ="FnSku")
    private String fnSku ;
 
    /** 调出仓库编码 */
    @ApiModelProperty(value = "调出仓库编码")
    @ExcelProperty(value ="调出仓库编码")
    private String transferOutHouseCode ;
 
    /** 调出仓库名称 */
    @ApiModelProperty(value = "调出仓库名称")
    @ExcelProperty(value ="调出仓库名称")
    private String transferOutHouseName ;
 
    /** 调入仓库编码 */
    @ApiModelProperty(value = "调入仓库编码")
    @ExcelProperty(value ="调入仓库编码")
    private String transferInHouseCode ;
 
    /** 调入仓库名称 */
    @ApiModelProperty(value = "调入仓库名称")
    @ExcelProperty(value ="调入仓库名称")
    private String transferInHouseName ;
 
    /** 调拨数量 */
    @ApiModelProperty(value = "调拨数量")
    @ExcelProperty(value ="调拨数量")
    private Integer transferQuantity ;


}