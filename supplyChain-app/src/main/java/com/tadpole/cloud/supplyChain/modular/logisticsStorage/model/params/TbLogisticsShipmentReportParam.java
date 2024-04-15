package com.tadpole.cloud.supplyChain.modular.logisticsStorage.model.params;

import cn.stylefeng.guns.cloud.model.validator.BaseValidatingParam;
import cn.stylefeng.guns.cloud.model.web.request.BaseRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
* 出货清单信息-金蝶+海外仓;
* @author : LSY
* @date : 2023-12-29
*/
@ApiModel(value = "出货清单报表查询请求参数",description = "")
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TbLogisticsShipmentReportParam extends BaseRequest implements Serializable,BaseValidatingParam{
private static final long serialVersionUID = 1L;

   /** 日期-开始 */
   @ApiModelProperty(value = "日期-开始")
   private Date startDate ;

   /** 日期-结束 */
   @ApiModelProperty(value = "日期-结束")
   private Date endDate ;

   /** 出货平台 */
   @ApiModelProperty(value = "出货平台")
   private String shopPlatName ;

   /** 出货账号 */
   @ApiModelProperty(value = "出货账号")
   private String shopNameSimple ;

   /** 站点 */
   @ApiModelProperty(value = "站点")
   private String countryCode ;

   /** 票单号 */
   @ApiModelProperty(value = "出货清单号")
   private String packCode ;

   /** ShipmentID */
   @ApiModelProperty(value = "查询传参--ShipmentID")
   private String shipmentId ;

}