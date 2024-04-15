package com.tadpole.cloud.supplyChain.modular.logisticsStorage.model.params;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.Date;
import cn.stylefeng.guns.cloud.model.validator.BaseValidatingParam;
import cn.stylefeng.guns.cloud.model.web.request.BaseRequest;
import lombok.*;
import java.lang.*;
import java.math.BigDecimal;

 /**
 * 亚马逊返回的发货申请信息-新版API流程;TbLogisticsShipmentInfo
 * @author : LSY
 * @date : 2023-12-29
 */
@ApiModel(value = "亚马逊返回的发货申请信息-新版API流程",description = "TbLogisticsShipmentInfo")
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TbLogisticsShipmentInfoParam extends BaseRequest implements Serializable,BaseValidatingParam{
 private static final long serialVersionUID = 1L;
 
    /** 系统编号 */
    @ApiModelProperty(value = "系统编号")
    private BigDecimal pkShipId ;
 
    /** 出货清单号 */
    @ApiModelProperty(value = "出货清单号")
    private String packCode ;
 
    /** PlanName */
    @ApiModelProperty(value = "PlanName")
    private String planName ;
 
    /** Shipment ID */
    @ApiModelProperty(value = "Shipment ID")
    private String busShipmentId ;
 
    /** Shipment Name */
    @ApiModelProperty(value = "Shipment Name")
    private String busShipmentName ;
 
    /** Plan ID */
    @ApiModelProperty(value = "Plan ID")
    private String busPlanId ;
 
    /** Ship To */
    @ApiModelProperty(value = "Ship To")
    private String busShipTo ;
 
    /** Who will prep */
    @ApiModelProperty(value = "Who will prep")
    private String busWhoWillPrep ;
 
    /** Prep type */
    @ApiModelProperty(value = "Prep type")
    private String busPrepType ;
 
    /** Who will label */
    @ApiModelProperty(value = "Who will label")
    private String busWhoWillLabel ;


}