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
* 通过数据;
* @author : LSY
* @date : 2023-12-29
*/
@ApiModel(value = "通过数据",description = "")
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ExcelIgnoreUnannotated
public class TbClearancModel implements Serializable{
private static final long serialVersionUID = 1L;



   /** SKU */
   @ApiModelProperty(value = "SKU")
   @ExcelProperty(value ="SKU")
   public String sku ;


   /** 款式或尺寸 */
   @ApiModelProperty(value = "款式或尺寸")
   @ExcelProperty(value ="款式或尺寸")
   public String invoiceNorm ;


   /** 适用机型或机型 */
   @ApiModelProperty(value = "适用机型或机型")
   @ExcelProperty(value ="适用机型或机型")
   public String model ;

   /** 数量 */
   @ApiModelProperty(value = "数量")
   @ExcelProperty(value ="数量")
   public Integer qty ;


   /** 数量单位 */
   @ApiModelProperty(value = "数量单位")
   @ExcelProperty(value ="数量单位")
   public String matQtyUnit ;



   /** 套装属性 */
   @ApiModelProperty(value = "套装属性")
   @ExcelProperty(value ="套装属性")
   public String matSetAttributes ;

   /** 币别 */
   @ApiModelProperty("币别")
   @ExcelProperty(value ="币制")
   private String currency="USD";


   /** 箱号 */
   @ApiModelProperty(value = "箱号")
   @ExcelProperty(value ="箱号")
   public Integer packDetBoxNum ;

   /** 头程物流单号 */
   @ApiModelProperty(value = "头程物流单号")
   @ExcelProperty(value ="分单号")
   private String lhrOddNumb=" " ;


   /** 公司品牌 */
   @ApiModelProperty(value = "公司品牌")
   @ExcelProperty(value ="公司品牌")
   public String comBrand ;

   /** 开票品名 */
   @ApiModelProperty(value = "开票品名")
   @ExcelProperty(value ="开票品名")
   public String invoiceProName ;

   /** 物料编码 */
   @ApiModelProperty(value = "物料编码")
   @ExcelProperty(value ="物料编码")
   public String mateCode ;

   /** 出货清单号 */
   @ApiModelProperty(value = "出货清单号")
   @ExcelProperty(value ="出货清单号")
   private String packCode ;

}