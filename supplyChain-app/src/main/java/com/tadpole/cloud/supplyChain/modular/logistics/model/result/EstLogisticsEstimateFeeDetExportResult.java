package com.tadpole.cloud.supplyChain.modular.logistics.model.result;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.format.NumberFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.io.Serializable;
import java.util.Date;

/**
 * ;
 * @author : LSY
 * @date : 2024-3-14
 */
@ApiModel(value = "",description = "")
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ExcelIgnoreUnannotated
public class EstLogisticsEstimateFeeDetExportResult implements Serializable{
 private static final long serialVersionUID = 1L;
 
    /**  */

    /**  */
    @ApiModelProperty(value = "费用测算编号")
    @ExcelProperty(value ="费用测算编号")
    private String estId ;
 

    @ApiModelProperty(value = "ShipmentId")
    @ExcelProperty(value ="ShipmentId")
    private String shipmentId ;

 
    /**  */

    /**  */
    @ApiModelProperty(value = "邮编")
    @ExcelProperty(value ="邮编")
    private String postcode ;

    @NumberFormat("0.00")
    @ApiModelProperty(value = "重量KG")
    @ExcelProperty(value ="重量KG")
    private Double weightKg ;
 
    /**  */
    @NumberFormat("0.00")
    @ApiModelProperty(value = "体积重KG")
    @ExcelProperty(value ="体积重KG")
    private Double volweightKg ;
 
    /**  */
    @ApiModelProperty(value = "计费类型")
    @ExcelProperty(value ="计费类型")
    private String confirmFeeType ;
 
    /**  */
    @ApiModelProperty(value = "计费量KG")
    @ExcelProperty(value ="计费量KG")
    @NumberFormat("0.00")
    private Double confirmCountFee ;

    @ApiModelProperty(value = "录入单价费用(CNY/KG)")
    @ExcelProperty(value ="录入单价费用(CNY/KG)")
    @NumberFormat("0.00")
    private Double entryUnitPrice;
 
    /**  */
    @ApiModelProperty(value = "物流费CNY")
    @ExcelProperty(value ="物流费CNY")
    @NumberFormat("0.00")
    private Double logisticsFee ;
 
    /**  */
    @ApiModelProperty(value = "燃油附加费CNY")
    @ExcelProperty(value ="燃油附加费CNY")
    @NumberFormat("0.00")
    private Double oilfee ;
 
    /**  */
    @ApiModelProperty(value = "旺季附加费CNY")
    @ExcelProperty(value ="旺季附加费CNY")
    @NumberFormat("0.00")
    private Double busyseasonFee ;
 
    /**  */
    @ApiModelProperty(value = "附加费及杂费CNY")
    @ExcelProperty(value ="附加费及杂费CNY")
    @NumberFormat("0.00")
    private Double othersFee ;
 
    /**  */
    @ApiModelProperty(value = "报关费CNY")
    @ExcelProperty(value ="报关费CNY")
    @NumberFormat("0.00")
    private Double applyCustomsfee ;
 
    /**  */
    @ApiModelProperty(value = "清关费CNY")
    @ExcelProperty(value ="清关费CNY")
    @NumberFormat("0.00")
    private Double clearCustomsfee ;
 
    /**  */
    @ApiModelProperty(value = "FBA配置费CNY")
    @ExcelProperty(value ="FBA配置费CNY")
    @NumberFormat("0.00")
    private Double fbaconfigFee ;
 
    /**  */
    @ApiModelProperty(value = "费用合计CNY")
    @ExcelProperty(value ="费用合计CNY")
    @NumberFormat("0.00")
    private Double totalFee ;


   @ApiModelProperty(value = "测算日期")
   @ExcelProperty(value ="测算日期")
   private Date estDate ;





   /** 箱号 */
   @ApiModelProperty(value = "箱号")
   @ExcelProperty(value ="箱号")
   private String boxNum ;


   /** 箱数 */
   @ApiModelProperty(value = "箱数")
   @ExcelProperty(value ="箱数")
   @NumberFormat("0.00")
   private Double boxCount ;




   @ApiModelProperty(value = "人工录入单价")
   @NumberFormat("0.00")
   private Double isEntryUnitPrice;

    /** shipto */
    @ApiModelProperty(value = "shipto")
    @ExcelProperty(value ="shipto")
    private String shipto ;


    /** 数量 */
    @ApiModelProperty(value = "数量")
    @ExcelProperty(value ="数量")
    @NumberFormat("0.00")
    private Double qty ;



}