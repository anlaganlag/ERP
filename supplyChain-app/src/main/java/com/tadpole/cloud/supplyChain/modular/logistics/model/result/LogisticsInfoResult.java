package com.tadpole.cloud.supplyChain.modular.logistics.model.result;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.io.Serializable;

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
public class LogisticsInfoResult implements Serializable{
 private static final long serialVersionUID = 1L;
   @ApiModelProperty(value = "物流商名称")
   @ExcelProperty(value ="")
   private String lpName ;

    @ApiModelProperty(value = "站点")
    @ExcelProperty(value ="")
    private String site ;


    @ApiModelProperty(value = "分区号")
    @ExcelProperty(value ="")
    private String lspNum ;


    @ApiModelProperty(value = "货运方式1")
    @ExcelProperty(value ="")
    private String freightCompany ;


    @ApiModelProperty(value = "运输方式")
    @ExcelProperty(value ="")
    private String transportType ;

   @ApiModelProperty(value = "物流渠道")
   @ExcelProperty(value ="")
   private String logisticsChannel ;



  @ApiModelProperty(value = "红蓝单")
  @ExcelProperty(value ="")
  private String orderType ;

  @ApiModelProperty(value = "是否含税")
  @ExcelProperty(value ="")
  private String hasTax ;

}