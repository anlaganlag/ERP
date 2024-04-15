package com.tadpole.cloud.supplyChain.modular.logistics.model.params;

import cn.stylefeng.guns.cloud.model.validator.BaseValidatingParam;
import cn.stylefeng.guns.cloud.model.web.request.BaseRequest;
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
public class EstLogisticsEstimateFeeDetUploadParam extends BaseRequest implements Serializable,BaseValidatingParam{
private static final long serialVersionUID = 1L;


   @ApiModelProperty(value = "")
   private String estId ;

   /**  */
   @ApiModelProperty(value = "")
   private String fbaconfigFee ;

   /**  */
   @ApiModelProperty(value = "")
   private String fbaconfigCurrency ;

   /**  */
   @ApiModelProperty(value = "")
   private String lpname ;

   /**  */
   @ApiModelProperty(value = "")
   private String site ;

   /**  */
   @ApiModelProperty(value = "")
   private String freightCompany ;

   /**  */
   @ApiModelProperty(value = "")
   private String transportType ;

   /**  */
   @ApiModelProperty(value = "")
   private String logisticsChannel ;

   /**  */
   @ApiModelProperty(value = "")
   private String orderType ;

   /**  */
   @ApiModelProperty(value = "")
   private String hasTax ;

   /**  */
   @ApiModelProperty(value = "")
   private String shipmentId ;

   /**  */
   @ApiModelProperty(value = "")
   private String shipTo ;

   /**  */
   @ApiModelProperty(value = "")
   private String lspNum ;

   /**  */
   @ApiModelProperty(value = "")
   private String packlist ;





}