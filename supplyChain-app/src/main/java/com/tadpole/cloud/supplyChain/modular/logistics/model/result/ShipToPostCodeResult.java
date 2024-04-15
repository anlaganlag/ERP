package com.tadpole.cloud.supplyChain.modular.logistics.model.result;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
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
public class ShipToPostCodeResult implements Serializable{
 private static final long serialVersionUID = 1L;
   @ApiModelProperty(value = "")
   @ExcelProperty(value ="")
   private String postcode ;

   @ApiModelProperty(value = "")
   @ExcelProperty(value ="")
   private String shipTo ;

}