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
* 亚马逊货件-明细-按SKU的汇总;
* @author : LSY
* @date : 2023-12-29
*/
@ApiModel(value = "根据sku，shipmentId查找该sku所在箱的信息",description = "")
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ExcelIgnoreUnannotated
public class LogisticsSingleBoxRecModel implements Serializable{
private static final long serialVersionUID = 1L;

   /** 编号 */
   @ApiModelProperty(value = "sysDetID")
   @ExcelProperty(value ="sysDetID")
   private BigDecimal sysDetID ;

   /** Shipment ID */
   @ApiModelProperty(value = "shipmentId")
   @ExcelProperty(value ="shipmentId")
   private String shipmentId ;

   /** MerchantSKU */
   @ApiModelProperty(value = "merchantSku")
   @ExcelProperty(value ="merchantSku")
   private String merchantSku ;

   /** FNSKU */
   @ApiModelProperty(value = "fnsku")
   @ExcelProperty(value ="fnsku")
   private String fnsku ;


   /** 票单号 */
   @ApiModelProperty(value = "packCode")
   @ExcelProperty(value ="packCode")
   private String packCode ;

   /** BoxedQTY */
   @ApiModelProperty(value = "sQty")
   @ExcelProperty(value ="sQty")
   private Integer sQty ;

   /** BoxedQTY */
   @ApiModelProperty(value = "sBoxNum")
   @ExcelProperty(value ="sBoxNum")
   private Integer sBoxNum ;

   /** BoxedQTY */
   @ApiModelProperty(value = "pQty")
   @ExcelProperty(value ="pQty")
   private Integer pQty ;

   /** BoxedQTY */
   @ApiModelProperty(value = "pBoxNum")
   @ExcelProperty(value ="pBoxNum")
   private Integer pBoxNum ;


   /** BoxedQTY */
   @ApiModelProperty(value = "isSet")
   @ExcelProperty(value ="isSet")
   private boolean isSet =false ;



}