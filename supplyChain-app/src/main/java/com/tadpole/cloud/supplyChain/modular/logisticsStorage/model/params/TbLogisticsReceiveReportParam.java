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
* 收货差异报表查询请求参数;
* @author : LSY
* @date : 2023-12-29
*/
@ApiModel(value = "收货差异报表查询请求参数",description = "")
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TbLogisticsReceiveReportParam extends BaseRequest implements Serializable,BaseValidatingParam{
private static final long serialVersionUID = 1L;

   /** 日期-开始 */
   @ApiModelProperty(value = "日期-开始")
   private Date startDate ;

   /** 日期-结束 */
   @ApiModelProperty(value = "日期-结束")
   private Date endDate ;


   /** 站点 */
   @ApiModelProperty(value = "shipmentID")
   private String shipmentID ;

   /** 出货账号 */
   @ApiModelProperty(value = "出货账号")
   private String shopNameSimple ;

   /** 站点 */
   @ApiModelProperty(value = "站点")
   private String countryCode ;





}