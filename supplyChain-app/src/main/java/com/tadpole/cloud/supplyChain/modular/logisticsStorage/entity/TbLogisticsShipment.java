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
 * 亚马逊发货申请记录-EBMS形成记录;
 * @author : LSY
 * @date : 2023-12-29
 */
@TableName("tb_logistics_shipment")
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ExcelIgnoreUnannotated
public class TbLogisticsShipment implements Serializable{
 private static final long serialVersionUID = 1L;
 
    /** ID */
    @ApiModelProperty(value = "ID")
    @TableId (value = "ID", type = IdType.AUTO)
    @TableField("ID")
    private BigDecimal id ;
 
    /** 出货清单号 */
    @ApiModelProperty(value = "出货清单号")
    @TableField("pack_code")
    private String packCode ;
 
    /** 创建日期 */
    @ApiModelProperty(value = "创建日期")
    @TableField("sys_add_date")
    private Date sysAddDate ;
 
    /** 员工编号 */
    @ApiModelProperty(value = "员工编号")
    @TableField("sys_per_code")
    private String sysPerCode ;
 
    /** 员工姓名 */
    @ApiModelProperty(value = "员工姓名")
    @TableField("sys_per_name")
    private String sysPerName ;
 
    /** 店铺简称 */
    @ApiModelProperty(value = "店铺简称")
    @TableField("shop_name_simple")
    private String shopNameSimple ;
 
    /** PlanName */
    @ApiModelProperty(value = "PlanName")
    @TableField("plan_name")
    private String planName ;
 
    /** ShipToCountry */
    @ApiModelProperty(value = "ShipToCountry")
    @TableField("ship_to_country")
    private String shipToCountry ;
 
    /** AddressName */
    @ApiModelProperty(value = "AddressName")
    @TableField("address_name")
    private String addressName ;
 
    /** AddressFieldOne */
    @ApiModelProperty(value = "AddressFieldOne")
    @TableField("address_field_one")
    private String addressFieldOne ;
 
    /** AddressFieldTwo */
    @ApiModelProperty(value = "AddressFieldTwo")
    @TableField("address_field_two")
    private String addressFieldTwo ;
 
    /** AddressCity */
    @ApiModelProperty(value = "AddressCity")
    @TableField("address_city")
    private String addressCity ;
 
    /** AddressCountryCode */
    @ApiModelProperty(value = "AddressCountryCode")
    @TableField("address_country_code")
    private String addressCountryCode ;
 
    /** AddressStateOrRegion */
    @ApiModelProperty(value = "AddressStateOrRegion")
    @TableField("address_state_or_region")
    private String addressStateOrRegion ;
 
    /** AddressDistrict */
    @ApiModelProperty(value = "AddressDistrict")
    @TableField("address_district")
    private String addressDistrict ;
 
    /** AddressPostalCode */
    @ApiModelProperty(value = "AddressPostalCode")
    @TableField("address_postal_code")
    private String addressPostalCode ;
 
    /** 最后更新日期 */
    @ApiModelProperty(value = "最后更新日期")
    @TableField("sys_upd_datetime")
    private Date sysUpdDatetime ;
 
    /** AGL */
    @ApiModelProperty(value = "AGL")
    @TableField("bus_is_agl")
    private Integer busIsAgl ;
 
    /** Who will label */
    @ApiModelProperty(value = "Who will label")
    @TableField("bus_who_will_label")
    private String busWhoWillLabel ;
 
    /** Who will prep */
    @ApiModelProperty(value = "Who will prep")
    @TableField("bus_who_will_prep")
    private String busWhoWillPrep ;
 
    /** 运输方式 */
    @ApiModelProperty(value = "运输方式")
    @TableField("bus_shipping_method")
    private String busShippingMethod ;
 
    /** 申请状态(待创建,完成创建) */
    @ApiModelProperty(value = "申请状态(待创建,完成创建)")
    @TableField("bus_app_status")
    private String busAppStatus ;
 
    /** 最大箱数 */
    @ApiModelProperty(value = "最大箱数")
    @TableField("bus_max_box_num")
    private Integer busMaxBoxNum ;


}