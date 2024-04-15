package com.tadpole.cloud.supplyChain.modular.logistics.model.params;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.io.Serializable;

/**
* ;
* @author : LSY
* @date : 2023-12-22
*/
@ApiModel(value = "",description = "")
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BoxDimensionParam  implements Serializable{
private static final long serialVersionUID = 1L;
   /** id */
   @ApiModelProperty(value = "id")
   private String id ;


   /** weight */
   @ApiModelProperty(value = "weight")
   private Double weight ;

   /** width */
   @ApiModelProperty(value = "width")
   private Double width ;


   /** length */
   @ApiModelProperty(value = "length")
   private Double length ;


   /** high */
   @ApiModelProperty(value = "high")
   private Double high ;

   /** 箱号 */
   @ApiModelProperty(value = "箱号")
   private String boxNum ;


   /** 箱数 */
   @ApiModelProperty(value = "箱数")
   private Double boxCount ;


   /** 商品数量 */
   @ApiModelProperty(value = "商品数量")
   private Double qty ; ;




}