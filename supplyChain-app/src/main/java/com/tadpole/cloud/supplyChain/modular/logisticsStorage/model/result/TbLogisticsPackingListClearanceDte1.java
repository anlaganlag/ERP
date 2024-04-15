package com.tadpole.cloud.supplyChain.modular.logisticsStorage.model.result;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;

/**
* 通过信息导出-出货清单明细1-箱子长宽高重-金蝶+海外仓;
* @author : LSY
* @date : 2023-12-29
*/
@ApiModel(value = "通过信息导出-出货清单明细1-箱子长宽高重",description = "")
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ExcelIgnoreUnannotated
public class TbLogisticsPackingListClearanceDte1 implements Serializable{
private static final long serialVersionUID = 1L;



   /** 箱号 */
   @ApiModelProperty(value = "箱号")
   @ExcelProperty(value ="箱号")
   private Integer packDetBoxNum ;

   /** 箱号上传名称 */
   @ApiModelProperty(value = "箱号上传名称")
   @ExcelProperty(value ="箱号上传名称")
   private String packDetBoxNumUpload ;

   /** 箱型 */
   @ApiModelProperty(value = "箱型")
   @ExcelProperty(value ="箱型")
   private String packDetBoxType ;

   /** 重量 */
   @ApiModelProperty(value = "重量")
   @ExcelProperty(value ="重量")
   private BigDecimal packDetBoxWeight ;

   /** 箱长 */
   @ApiModelProperty(value = "箱长")
   @ExcelProperty(value ="箱长")
   private BigDecimal packDetBoxLength ;

   /** 箱宽 */
   @ApiModelProperty(value = "箱宽")
   @ExcelProperty(value ="箱宽")
   private BigDecimal packDetBoxWidth ;

   /** 箱高 */
   @ApiModelProperty(value = "箱高")
   @ExcelProperty(value ="箱高")
   private BigDecimal packDetBoxHeight ;

   /** 票单号 */
   @ApiModelProperty(value = "票单号")
   @ExcelProperty(value ="出货清单号")
   private String packCode ;

}