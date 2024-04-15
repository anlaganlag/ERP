package com.tadpole.cloud.supplyChain.modular.logistics.model.result;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.Date;
import lombok.*;
import java.lang.*;
import java.math.BigDecimal;
import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import oracle.sql.DATE;

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
public class EstLogisticsEstimateFeeDetResult implements Serializable{
 private static final long serialVersionUID = 1L;
 
    /**  */
    @ApiModelProperty(value = "")
    @ExcelProperty(value ="")
    private String id ;
 
    /**  */
    @ApiModelProperty(value = "")
    @ExcelProperty(value ="")
    private String estId ;
 
    /**  */
    @ApiModelProperty(value = "")
    @ExcelProperty(value ="")
    private String fbaconfigFee ;
 
    /**  */
    @ApiModelProperty(value = "")
    @ExcelProperty(value ="")
    private String fbaconfigCurrency ;
 
    /**  */
    @ApiModelProperty(value = "")
    @ExcelProperty(value ="")
    private String lpname ;
 
    /**  */
    @ApiModelProperty(value = "")
    @ExcelProperty(value ="")
    private String site ;
 
    /**  */
    @ApiModelProperty(value = "货运方式1")
    @ExcelProperty(value ="")
    private String freightCompany ;
 
    /**  */
    @ApiModelProperty(value = "运输方式")
    @ExcelProperty(value ="")
    private String transportType ;
 
    /**  */
    @ApiModelProperty(value = "物流渠道")
    @ExcelProperty(value ="")
    private String logisticsChannel ;
 
    /**  */
    @ApiModelProperty(value = "红蓝但")
    @ExcelProperty(value ="")
    private String orderType ;
 
    /**  */
    @ApiModelProperty(value = "")
    @ExcelProperty(value ="")
    private String hasTax ;
 
    /**  */
    @ApiModelProperty(value = "")
    @ExcelProperty(value ="")
    private String shipmentId ;
 
    /**  */
    @ApiModelProperty(value = "")
    @ExcelProperty(value ="")
    private String shipTo ;
 
    /**  */
    @ApiModelProperty(value = "")
    @ExcelProperty(value ="")
    private String lspNum ;
 
    /**  */
    @ApiModelProperty(value = "")
    @ExcelProperty(value ="")
    private String packlist ;
 
    /**  */
    @ApiModelProperty(value = "")
    @ExcelProperty(value ="")
    private String postcode ;
 
    /**  */
    @ApiModelProperty(value = "")
    @ExcelProperty(value ="")
    private Double weightKg ;
 
    /**  */
    @ApiModelProperty(value = "")
    @ExcelProperty(value ="")
    private Double volweightKg ;
 
    /**  */
    @ApiModelProperty(value = "")
    @ExcelProperty(value ="")
    private String confirmFeeType ;
 
    /**  */
    @ApiModelProperty(value = "")
    @ExcelProperty(value ="")
    private Double confirmCountFee ;
 
    /**  */
    @ApiModelProperty(value = "")
    @ExcelProperty(value ="")
    private Double logisticsFee ;
 
    /**  */
    @ApiModelProperty(value = "")
    @ExcelProperty(value ="")
    private Double oilfee ;
 
    /**  */
    @ApiModelProperty(value = "")
    @ExcelProperty(value ="")
    private Double busyseasonFee ;
 
    /**  */
    @ApiModelProperty(value = "")
    @ExcelProperty(value ="")
    private Double othersFee ;
 
    /**  */
    @ApiModelProperty(value = "")
    @ExcelProperty(value ="")
    private Double applyCustomsfee ;
 
    /**  */
    @ApiModelProperty(value = "")
    @ExcelProperty(value ="")
    private Double clearCustomsfee ;
 
    /**  */
    @ApiModelProperty(value = "")
    @ExcelProperty(value ="")
    private Double toalFee ;
 
    /**  */
    @ApiModelProperty(value = "")
    @ExcelProperty(value ="")
    private Date updatedTime ;
 
    /**  */
    @ApiModelProperty(value = "")
    @ExcelProperty(value ="")
    private String updatedBy ;
 
    /**  */
    @ApiModelProperty(value = "")
    @ExcelProperty(value ="")
    private String createdBy ;
 
    /**  */
    @ApiModelProperty(value = "")
    @ExcelProperty(value ="")
    private Date createdTime ;
 
    /**  */

 
    /**  */
    @ApiModelProperty(value = "")
    @ExcelProperty(value ="")
    private Double totalFee ;
 
    /**  */
    @ApiModelProperty(value = "")
    @ExcelProperty(value ="")
    private String perName ;
 
    /**  */
    @ApiModelProperty(value = "")
    @ExcelProperty(value ="")
    private String perCode ;
 
    /**  */

    @ApiModelProperty(value = "测算日期")
    private Date estDate ;





   /** 箱号 */
   @ApiModelProperty(value = "箱号")
   @ExcelProperty(value ="箱号")
   private String boxNum ;


   /** 箱数 */
   @ApiModelProperty(value = "箱数")
   @ExcelProperty(value ="箱数")
   private Double boxCount ;


   /** 商品数量 */
   @ApiModelProperty(value = "商品数量")
   @ExcelProperty(value ="商品数量")
   private Double qty ; ;

   @ApiModelProperty(value = "人工录入单价")
   @ExcelProperty(value ="人工录入单价")
   private Integer isEntryUnitPrice;

   @ApiModelProperty(value = "录入单价费用(CNY/KG)")
   @ExcelProperty(value ="录入单价费用(CNY/KG)")
   private Double entryUnitPrice;


}