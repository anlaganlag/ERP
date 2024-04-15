package com.tadpole.cloud.supplyChain.modular.logisticsStorage.model.params;

import cn.stylefeng.guns.cloud.model.validator.BaseValidatingParam;
import cn.stylefeng.guns.cloud.model.web.request.BaseRequest;
import io.swagger.annotations.ApiModel;
import lombok.*;

import java.io.Serializable;

/**
* 关闭在途请求入参
* @author : LSY
* @date : 2023-12-29
*/
@ApiModel(value = "关闭在途请求入参",description = "")
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CloseLogisticsInGoodsParam extends BaseRequest implements Serializable,BaseValidatingParam{
private static final long serialVersionUID = 1L;
   private String busPlanName;
   private String busShopNameSimple;
   private String busCountryCode;
   private String busComWarehouseName;
   private String busLerSignDate;
   private String busFbaTurnDate;
   private String busReturnStatus;
   private String busShipmentID;
   private String busAmaRecState;
   private String busLhrOddNumb;
   private String busLhrState;
   private String busLogTraMode1;
   private String busLogTraMode2;
   private String busSku;
   private String busCASIN;
   private String busMatCode;
   private String busSendQty;
   private String busStayDeliverQty;
   private String busIssuedQty;
   private String busInTransitQty;
   private String busReceiveQty;
   private String busDiscrepancy;
   private String busRemark;
   /**
    * 是否索赔
    */
   private String busIsClaims;
   private String busIsPod;
}