package com.tadpole.cloud.supplyChain.modular.logistics.model.result;

import com.tadpole.cloud.supplyChain.modular.logistics.entity.EstLogisticsEstimateFeeDet;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.Date;
import lombok.*;
import java.lang.*;
import java.math.BigDecimal;
import java.util.List;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import oracle.sql.DATE;

/**
 * 物流费用测算;
 * @author : LSY
 * @date : 2024-3-14
 */
@ApiModel(value = "物流费用测算",description = "")
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ExcelIgnoreUnannotated
public class EstLogisticsEstimateFeeResult implements Serializable{
 private static final long serialVersionUID = 1L;
 
    /** 测算id */
    @ApiModelProperty(value = "测算id")
    @ExcelProperty(value ="测算id")
    private String estId ;
 
    /**  */
    @ApiModelProperty(value = "")
    @ExcelProperty(value ="")
    private String perCode ;
 
    /**  */
    @ApiModelProperty(value = "")
    @ExcelProperty(value ="")
    private String perName ;
 
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
    @ApiModelProperty(value = "")
    @ExcelProperty(value ="")
    private Double totalQty ;
 
    /**  */
    @ApiModelProperty(value = "")
    @ExcelProperty(value ="")
    private Double totalFee ;
 
    /**  */
    @ApiModelProperty(value = "")
    @ExcelProperty(value ="")
    private Date estDate ;
 



   /** 测算明细 */
   @ApiModelProperty(value = "测算明细")
   private List<EstLogisticsEstimateFeeDet> detailList ;

   @ApiModelProperty(value = "人工录入单价")
   @ExcelProperty(value ="人工录入单价")
   private Integer isEntryUnitPrice;

   @ApiModelProperty(value = "录入单价费用(CNY/KG)")
   @ExcelProperty(value ="录入单价费用(CNY/KG)")
   private Double entryUnitPrice;

}