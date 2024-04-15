package com.tadpole.cloud.platformSettlement.modular.finance.model.params;

import cn.stylefeng.guns.cloud.model.validator.BaseValidatingParam;
import cn.stylefeng.guns.cloud.model.web.request.BaseRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

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
public class BoxDimensionParam extends BaseRequest implements Serializable,BaseValidatingParam{
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


   private String boxNum  ;
   private Double boxCount ;


   /** 商品数量 */
   @ApiModelProperty(value = "商品数量")
   private Double qty ;;


}