package com.tadpole.cloud.supplyChain.modular.logisticsStorage.entity;

import java.io.Serializable;
import java.lang.*;
import lombok.*;
import java.math.BigDecimal;
import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import java.util.Date;

 /**
 * 亚马逊返回的发货申请信息-新版API流程;TbLogisticsShipmentInfo
 * @author : LSY
 * @date : 2023-12-29
 */
@TableName("tb_logistics_shipment_info")
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ExcelIgnoreUnannotated
public class TbLogisticsShipmentInfo implements Serializable{
 private static final long serialVersionUID = 1L;
 
    /** 系统编号 */
    @ApiModelProperty(value = "系统编号")
    @TableId (value = "pk_ship_id", type = IdType.AUTO)
    @TableField("pk_ship_id")
    private BigDecimal pkShipId ;
 
    /** 出货清单号 */
    @ApiModelProperty(value = "出货清单号")
    @TableField("pack_code")
    private String packCode ;
 
    /** PlanName */
    @ApiModelProperty(value = "PlanName")
    @TableField("plan_name")
    private String planName ;
 
    /** Shipment ID */
    @ApiModelProperty(value = "Shipment ID")
    @TableField("bus_shipment_id")
    private String busShipmentId ;
 
    /** Shipment Name */
    @ApiModelProperty(value = "Shipment Name")
    @TableField("bus_shipment_name")
    private String busShipmentName ;
 
    /** Plan ID */
    @ApiModelProperty(value = "Plan ID")
    @TableField("bus_plan_id")
    private String busPlanId ;
 
    /** Ship To */
    @ApiModelProperty(value = "Ship To")
    @TableField("bus_ship_to")
    private String busShipTo ;
 
    /** Who will prep */
    @ApiModelProperty(value = "Who will prep")
    @TableField("bus_who_will_prep")
    private String busWhoWillPrep ;
 
    /** Prep type */
    @ApiModelProperty(value = "Prep type")
    @TableField("bus_prep_type")
    private String busPrepType ;
 
    /** Who will label */
    @ApiModelProperty(value = "Who will label")
    @TableField("bus_who_will_label")
    private String busWhoWillLabel ;


}