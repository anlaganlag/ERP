package com.tadpole.cloud.supplyChain.modular.logisticsStorage.model.params;

import cn.stylefeng.guns.cloud.model.validator.BaseValidatingParam;
import cn.stylefeng.guns.cloud.model.web.request.BaseRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
* 货物清单报表查询请求参数;
* @author : LSY
* @date : 2023-12-29
*/
@ApiModel(value = "货物清单报表查询请求参数",description = "")
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TbLogisticsGoodsListReportParam extends BaseRequest implements Serializable,BaseValidatingParam{
private static final long serialVersionUID = 1L;

   /** 日期-开始 */
   @ApiModelProperty(value = "日期-开始")
   private Date startDate ;

   /** 日期-结束 */
   @ApiModelProperty(value = "日期-结束")
   private Date endDate ;

   /** 出货平台 */
   @ApiModelProperty(value = "lhrOddNumb")
   private String lhrOddNumb ;


   /**
    * 物流商
    */
   @ApiModelProperty(value = "lpName")
   private String lpName ;



   /** 出货账号 */
   @ApiModelProperty(value = "logTraMode1")
   private String logTraMode1 ;

   /** 站点 */
   @ApiModelProperty(value = "shipmentID")
   private String shipmentID ;

   /** 票单号 */
   @ApiModelProperty(value = "packDirectCode")
   private String packDirectCode ;

   /** ShipmentID */
   @ApiModelProperty(value = "packStoreHouseName")
   private String packStoreHouseName ;


   /** ShipmentID */
   @ApiModelProperty(value = "lhrPosition")
   private String lhrPosition ;

   /** ShipmentID */
   @ApiModelProperty(value = "matCode")
   private String matCode ;


   /** shopNameSimpleList */
   @ApiModelProperty(value = "shopNameSimpleList")
   private List<String> shopNameSimpleList ;

   /** CountryCodeList */
   @ApiModelProperty(value = "CountryCodeList")
   private List<String> countryCodeList ;


}