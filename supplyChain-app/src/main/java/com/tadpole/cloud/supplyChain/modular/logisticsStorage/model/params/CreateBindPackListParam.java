package com.tadpole.cloud.supplyChain.modular.logisticsStorage.model.params;

import cn.stylefeng.guns.cloud.model.validator.BaseValidatingParam;
import cn.stylefeng.guns.cloud.model.web.request.BaseRequest;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.entity.TbLogisticsPackListBoxRecDet;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
* PackList绑定出货清单请求入参
* @author : LSY
* @date : 2023-12-29
*/
@ApiModel(value = "PackList绑定出货清单请求入参",description = "")
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateBindPackListParam extends BaseRequest implements Serializable,BaseValidatingParam{
private static final long serialVersionUID = 1L;



   /** 出货仓类型 */
   @ApiModelProperty(value = "出货仓类型")
   private String comWarehouseType;

   /** 出货仓名称 */
   @ApiModelProperty(value = "出货仓名称")
   private String owName;

   /** shipmentId */
   @ApiModelProperty(value = "shipmentId")
   private String shipmentId;

   /** 票单号 */
   @ApiModelProperty(value = "票单号")
   private String packCode ;

   /** 收货仓名称 */
   @ApiModelProperty(value = "收货仓名称")
   private String shipTo;

   /** 站点 */
   @ApiModelProperty(value = "站点")
   private String countryCode;

   /** 账号简称 */
   @ApiModelProperty(value = "账号简称")
   private String shopNameSimple;

   /** 系统标识字段新版导入文件 */
   @ApiModelProperty(value = "系统标识字段新版导入文件")
   private String packListCode;

   /////////////////////////////////////////////////////////////////////////////
   /** TbLogisticsPackListBoxRecDet 明细集合List */
   @ApiModelProperty(value = "TbLogisticsPackListBoxRecDet 明细集合List")
   private List<TbLogisticsPackListBoxRecDet> logisticsPackListBoxRecDetlist;


}