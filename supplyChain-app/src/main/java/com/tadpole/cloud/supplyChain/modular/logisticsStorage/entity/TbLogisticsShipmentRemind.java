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
 * TbLogisticsShipmentRemind--暂时不需要;
 * @author : LSY
 * @date : 2023-12-29
 */
@TableName("tb_logistics_shipment_remind")
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ExcelIgnoreUnannotated
public class TbLogisticsShipmentRemind implements Serializable{
 private static final long serialVersionUID = 1L;
 
    /** SysID */
    @ApiModelProperty(value = "SysID")
    @TableId (value = "sys_id", type = IdType.AUTO)
    @TableField("sys_id")
    private BigDecimal sysId ;
 
    /** 出货清单编码 */
    @ApiModelProperty(value = "出货清单编码")
    @TableField("pack_code")
    private String packCode ;
 
    /** 装箱日期 */
    @ApiModelProperty(value = "装箱日期")
    @TableField("pack_date")
    private Date packDate ;
 
    /** 收仓库类型 */
    @ApiModelProperty(value = "收仓库类型")
    @TableField("pack_store_house_type")
    private String packStoreHouseType ;
 
    /** 收货仓名称 */
    @ApiModelProperty(value = "收货仓名称")
    @TableField("pack_store_house_name")
    private String packStoreHouseName ;
 
    /** 店铺简称账号 */
    @ApiModelProperty(value = "店铺简称账号")
    @TableField("shop_name_simple")
    private String shopNameSimple ;
 
    /** 国家编码 */
    @ApiModelProperty(value = "国家编码")
    @TableField("country_code")
    private String countryCode ;
 
    /** ShipmentID */
    @ApiModelProperty(value = "ShipmentID")
    @TableField("shipment_id")
    private String shipmentId ;


}