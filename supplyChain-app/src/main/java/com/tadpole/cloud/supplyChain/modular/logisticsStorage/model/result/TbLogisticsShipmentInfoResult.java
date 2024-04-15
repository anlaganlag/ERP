package com.tadpole.cloud.supplyChain.modular.logisticsStorage.model.result;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.Date;
import lombok.*;
import java.lang.*;
import java.math.BigDecimal;
import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;

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
@ExcelIgnoreUnannotated
public class TbLogisticsShipmentInfoResult implements Serializable{
 private static final long serialVersionUID = 1L;
 
    /** 系统编号 */
    @ApiModelProperty(value = "系统编号")
    @ExcelProperty(value ="系统编号")
    private BigDecimal pkShipId ;
 
    /** 出货清单号 */
    @ApiModelProperty(value = "出货清单号")
    @ExcelProperty(value ="出货清单号")
    private String packCode ;
 
    /** PlanName */
    @ApiModelProperty(value = "PlanName")
    @ExcelProperty(value ="PlanName")
    private String planName ;
 
    /** Shipment ID */
    @ApiModelProperty(value = "Shipment ID")
    @ExcelProperty(value ="Shipment ID")
    private String busShipmentId ;
 
    /** Shipment Name */
    @ApiModelProperty(value = "Shipment Name")
    @ExcelProperty(value ="Shipment Name")
    private String busShipmentName ;
 
    /** Plan ID */
    @ApiModelProperty(value = "Plan ID")
    @ExcelProperty(value ="Plan ID")
    private String busPlanId ;
 
    /** Ship To */
    @ApiModelProperty(value = "Ship To")
    @ExcelProperty(value ="Ship To")
    private String busShipTo ;
 
    /** Who will prep */
    @ApiModelProperty(value = "Who will prep")
    @ExcelProperty(value ="Who will prep")
    private String busWhoWillPrep ;
 
    /** Prep type */
    @ApiModelProperty(value = "Prep type")
    @ExcelProperty(value ="Prep type")
    private String busPrepType ;
 
    /** Who will label */
    @ApiModelProperty(value = "Who will label")
    @ExcelProperty(value ="Who will label")
    private String busWhoWillLabel ;


}