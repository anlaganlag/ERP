package com.tadpole.cloud.supplyChain.modular.logisticsStorage.model.params;

import com.alibaba.fastjson.annotation.JSONField;
import io.swagger.annotations.ApiModel;
import lombok.*;

import java.io.Serializable;
import java.util.List;

/**
* 金蝶调jcErp推送出货清单信息请求参数
* @author : LSY
* @date : 2023-12-29
*/
@ApiModel(value = "金蝶调jcErp推送出货清单信息请求参数",description = "")
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TbLogisticsPackingListK3Param  implements Serializable{
private static final long serialVersionUID = 1L;

   @JSONField(name = "PackCode")
   private String packCode;
   @JSONField(name = "BillType")
   private String billType;
   @JSONField(name = "PackSugShipMethod")
   private Object packSugShipMethod;
   @JSONField(name = "PackDate")
   private String packDate;
   @JSONField(name = "PackPerName")
   private String packPerName;
   @JSONField(name = "DemandOrg")
   private String demandOrg;
   @JSONField(name = "DeliveryPointName")
   private String deliveryPointName;
   @JSONField(name = "BoxDet")
   private List<BoxDetDTO> boxDet;

   @NoArgsConstructor
   @Data
   public static class BoxDetDTO {
      @JSONField(name = "PackDetBoxCode")
      private String packDetBoxCode;
      @JSONField(name = "PackDetBoxNum")
      private Integer packDetBoxNum;
      @JSONField(name = "PackDetBoxType")
      private String packDetBoxType;
      @JSONField(name = "PackDetBoxSpeUnit")
      private String packDetBoxSpeUnit;
      @JSONField(name = "PackDetBoxLength")
      private Integer packDetBoxLength;
      @JSONField(name = "PackDetBoxWidth")
      private Integer packDetBoxWidth;
      @JSONField(name = "PackDetBoxHeight")
      private Integer packDetBoxHeight;
      @JSONField(name = "PackDetBoxVoluUnit")
      private Object packDetBoxVoluUnit;
      @JSONField(name = "PackDetBoxVolume")
      private Integer packDetBoxVolume;
      @JSONField(name = "PackDetBoxWeigUnit")
      private String packDetBoxWeigUnit;
      @JSONField(name = "PackDetBoxWeight")
      private Double packDetBoxWeight;
      @JSONField(name = "SkuDet")
      private List<SkuDetDTO> skuDet;

      @NoArgsConstructor
      @Data
      public static class SkuDetDTO {
         @JSONField(name = "SKU")
         private String sku;
         @JSONField(name = "FnSKU")
         private String fnSKU;
         @JSONField(name = "MateCode")
         private String mateCode;
         @JSONField(name = "Quantity")
         private Integer quantity;
         @JSONField(name = "PickListCode")
         private String pickListCode;
         @JSONField(name = "DepName")
         private String depName;
         @JSONField(name = "TeamName")
         private String teamName;
         @JSONField(name = "PerName")
         private String perName;
         @JSONField(name = "PackSugShipMethod")
         private String packSugShipMethod;
         @JSONField(name = "Description")
         private String description;
         @JSONField(name = "PackStoreHouseName")
         private String packStoreHouseName;
         @JSONField(name = "PackDirectCode")
         private String packDirectCode;
      }
   }
}