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
 * 亚马逊货件-明细-按SKU的汇总;
 * @author : LSY
 * @date : 2023-12-29
 */
@ApiModel(value = "亚马逊货件-明细-按SKU的汇总",description = "")
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ExcelIgnoreUnannotated
public class TbLogisticsPackListDetResult implements Serializable{
 private static final long serialVersionUID = 1L;
 
    /** 编号 */
    @ApiModelProperty(value = "编号")
    @ExcelProperty(value ="编号")
    private BigDecimal shipDetId ;
 
    /** 票单号 */
    @ApiModelProperty(value = "票单号")
    @ExcelProperty(value ="票单号")
    private String packCode ;
 
    /** Shipment ID */
    @ApiModelProperty(value = "Shipment ID")
    @ExcelProperty(value ="Shipment ID")
    private String shipmentId ;
 
    /** MerchantSKU */
    @ApiModelProperty(value = "MerchantSKU")
    @ExcelProperty(value ="MerchantSKU")
    private String merchantSku ;
 
    /** ASIN */
    @ApiModelProperty(value = "ASIN")
    @ExcelProperty(value ="ASIN")
    private String asin ;
 
    /** Title */
    @ApiModelProperty(value = "Title")
    @ExcelProperty(value ="Title")
    private String title ;
 
    /** FNSKU */
    @ApiModelProperty(value = "FNSKU")
    @ExcelProperty(value ="FNSKU")
    private String fnsku ;
 
    /** ExternalID */
    @ApiModelProperty(value = "ExternalID")
    @ExcelProperty(value ="ExternalID")
    private String externalId ;
 
    /** PrepType */
    @ApiModelProperty(value = "PrepType")
    @ExcelProperty(value ="PrepType")
    private String prepType ;
 
    /** WhoWillLable */
    @ApiModelProperty(value = "WhoWillLable")
    @ExcelProperty(value ="WhoWillLable")
    private String whoWillLable ;
 
    /** ExpectedQTY */
    @ApiModelProperty(value = "ExpectedQTY")
    @ExcelProperty(value ="ExpectedQTY")
    private Integer expectedQty ;
 
    /** BoxedQTY */
    @ApiModelProperty(value = "BoxedQTY")
    @ExcelProperty(value ="BoxedQTY")
    private Integer boxedQty ;
 
    /** MaxBoxNum */
    @ApiModelProperty(value = "MaxBoxNum")
    @ExcelProperty(value ="MaxBoxNum")
    private Integer maxBoxNum ;
 
    /** 货件实际状态 */
    @ApiModelProperty(value = "货件实际状态")
    @ExcelProperty(value ="货件实际状态")
    private String shipmentRealStatus ;
 
    /** 系统标识字段新版导入文件 */
    @ApiModelProperty(value = "系统标识字段新版导入文件")
    @ExcelProperty(value ="系统标识字段新版导入文件")
    private String packListCode ;
 
    /** 接收数量 */
    @ApiModelProperty(value = "接收数量")
    @ExcelProperty(value ="接收数量")
    private Integer busReceivedQty ;
 
    /** 发货数量 */
    @ApiModelProperty(value = "发货数量")
    @ExcelProperty(value ="发货数量")
    private Integer busShippedQty ;
 
    /** 最新更新时间 */
    @ApiModelProperty(value = "最新更新时间")
    @ExcelProperty(value ="最新更新时间")
    private Date busSysUpdateDate ;


}