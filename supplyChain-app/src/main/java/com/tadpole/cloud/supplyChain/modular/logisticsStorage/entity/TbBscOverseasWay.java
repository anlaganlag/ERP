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
 * 发货汇总表;
 * @author : LSY
 * @date : 2024-1-10
 */
@TableName("Tb_Bsc_Overseas_Way")
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ExcelIgnoreUnannotated
public class TbBscOverseasWay implements Serializable{
 private static final long serialVersionUID = 1L;
 
    /**  */
    @ApiModelProperty(value = "")
    @TableId (value = "ID", type = IdType.AUTO)
    @TableField("ID")
    private BigDecimal id ;
 
    /** sku */
    @ApiModelProperty(value = "sku")
    @TableField("sku")
    private String sku ;
 
    /** DELIVER_STATUS */
    @ApiModelProperty(value = "DELIVER_STATUS")
    @TableField("pack_Code")
    private String packCode ;
 
    /** 物流发货状态 */
    @ApiModelProperty(value = "物流发货状态")
    @TableField("deliver_Status")
    private String deliverStatus ;
 
    /** ERP回传状态 */
    @ApiModelProperty(value = "ERP回传状态")
    @TableField("return_Status")
    private String returnStatus ;
 
    /** FBA状态 */
    @ApiModelProperty(value = "FBA状态")
    @TableField("status")
    private String status ;
 
    /** 物料代码 */
    @ApiModelProperty(value = "物料代码")
    @TableField("mat_Code")
    private String matCode ;
 
    /** 出货数量 */
    @ApiModelProperty(value = "出货数量")
    @TableField("delivery_Num")
    private Integer deliveryNum ;
 
    /** 货运方式 */
    @ApiModelProperty(value = "货运方式")
    @TableField("deliver_Type")
    private String deliverType ;
 
    /** 业务数据：1 历史数据：2  */
    @ApiModelProperty(value = "业务数据：1 历史数据：2 ")
    @TableField("ow_Name")
    private String owName ;
 
    /** 站点 */
    @ApiModelProperty(value = "站点")
    @TableField("country_Code")
    private String countryCode ;
 
    /** 账号 */
    @ApiModelProperty(value = "账号")
    @TableField("shop_Name_Simple")
    private String shopNameSimple ;
 
    /** FBA_SHIPMENT_ID */
    @ApiModelProperty(value = "FBA_SHIPMENT_ID")
    @TableField("shipment_ID")
    private String shipmentId ;
 
    /** 平台 */
    @ApiModelProperty(value = "平台")
    @TableField("plate_Form")
    private String plateForm ;
 
    /** 收货仓名称 */
    @ApiModelProperty(value = "收货仓名称")
    @TableField("receive_Warehouse")
    private String receiveWarehouse ;
 
    /** 出货仓名称 */
    @ApiModelProperty(value = "出货仓名称")
    @TableField("deliver_Warehouse")
    private String deliverWarehouse ;
 
    /** 回传时间 */
    @ApiModelProperty(value = "回传时间")
    @TableField("update_Time")
    private Date updateTime ;
 
    /** 箱号 */
    @ApiModelProperty(value = "箱号")
    @TableField("pack_Det_Box_Num")
    private Integer packDetBoxNum ;
 
    /** 货件实际状态 */
    @ApiModelProperty(value = "货件实际状态")
    @TableField("Shipment_Real_Status")
    private String shipmentRealStatus ;


}