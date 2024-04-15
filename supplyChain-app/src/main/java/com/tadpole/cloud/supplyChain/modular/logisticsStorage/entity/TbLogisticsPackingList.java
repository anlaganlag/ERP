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
 * 出货清单信息-金蝶+海外仓;
 * @author : LSY
 * @date : 2023-12-29
 */
@TableName("tb_logistics_packing_list")
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ExcelIgnoreUnannotated
public class TbLogisticsPackingList implements Serializable{
 private static final long serialVersionUID = 1L;
 
    /** 票单号 */
    @ApiModelProperty(value = "票单号")
    @TableId (value = "pack_code", type = IdType.ASSIGN_ID)
    @TableField("pack_code")
    private String packCode ;
 
    /** 装箱日期 */
    @ApiModelProperty(value = "装箱日期")
    @TableField("pack_date")
    private Date packDate ;
 
    /** 员工编号 */
    @ApiModelProperty(value = "员工编号")
    @TableField("pack_per_code")
    private String packPerCode ;
 
    /** 员工姓名 */
    @ApiModelProperty(value = "员工姓名")
    @TableField("pack_per_name")
    private String packPerName ;
 
    /** 收货账号 */
    @ApiModelProperty(value = "收货账号")
    @TableField("shop_name_simple")
    private String shopNameSimple ;
 
    /** 收货站点 */
    @ApiModelProperty(value = "收货站点")
    @TableField("country_code")
    private String countryCode ;
 
    /** 票单上传状态 */
    @ApiModelProperty(value = "票单上传状态")
    @TableField("pack_upload_state")
    private String packUploadState ;
 
    /** 物流状态 */
    @ApiModelProperty(value = "物流状态")
    @TableField("pack_log_state")
    private String packLogState ;
 
    /** 最后更新日期 */
    @ApiModelProperty(value = "最后更新日期")
    @TableField("sys_upd_datetime")
    private Date sysUpdDatetime ;
 
    /** Shipment索赔状态 */
    @ApiModelProperty(value = "Shipment索赔状态")
    @TableField("pack_shipment_claim_state")
    private String packShipmentClaimState ;
 
    /** 收货仓类型 */
    @ApiModelProperty(value = "收货仓类型")
    @TableField("pack_store_house_type")
    private String packStoreHouseType ;
 
    /** 收货仓名称 */
    @ApiModelProperty(value = "收货仓名称")
    @TableField("pack_store_house_name")
    private String packStoreHouseName ;
 
    /** 异常状态 */
    @ApiModelProperty(value = "异常状态")
    @TableField("pack_abnormal_status")
    private Integer packAbnormalStatus ;
 
    /** 异常原因 */
    @ApiModelProperty(value = "异常原因")
    @TableField("pack_abnormal_reason")
    private String packAbnormalReason ;
 
    /** 无用 */
    @ApiModelProperty(value = "无用")
    @TableField("shipment_id")
    private String shipmentId ;
 
    /** isNormal;无用 */
    @ApiModelProperty(value = "isNormal")
    @TableField("is_normal")
    private Integer isNormal ;
 
    /** 出货仓类型 */
    @ApiModelProperty(value = "出货仓类型")
    @TableField("com_warehouse_type")
    private String comWarehouseType ;
 
    /** 出货仓名称 */
    @ApiModelProperty(value = "出货仓名称")
    @TableField("com_warehouse_name")
    private String comWarehouseName ;
 
    /** FBA回传日期 */
    @ApiModelProperty(value = "FBA回传日期")
    @TableField("fba_turn_date")
    private Date fbaTurnDate ;
 
    /** 出货平台 */
    @ApiModelProperty(value = "出货平台")
    @TableField("com_shop_plat_name")
    private String comShopPlatName ;
 
    /** 出货账号 */
    @ApiModelProperty(value = "出货账号")
    @TableField("com_shop_name_simple")
    private String comShopNameSimple ;
 
    /** 出货站点 */
    @ApiModelProperty(value = "出货站点")
    @TableField("com_country_code")
    private String comCountryCode ;
 
    /** 收货平台 */
    @ApiModelProperty(value = "收货平台")
    @TableField("shop_plat_name")
    private String shopPlatName ;
 
    /** 发货点 */
    @ApiModelProperty(value = "发货点")
    @TableField("delivery_point_name")
    private String deliveryPointName ;
 
    /** 业务类型 */
    @ApiModelProperty(value = "业务类型")
    @TableField("bill_type")
    private String billType ;
 
    /** 总重量 */
    @ApiModelProperty(value = "总重量")
    @TableField("pack_total_weight")
    private BigDecimal packTotalWeight ;
 
    /** 总体积 */
    @ApiModelProperty(value = "总体积")
    @TableField("pack_total_volume")
    private BigDecimal packTotalVolume ;
 
    /** 总体积重 */
    @ApiModelProperty(value = "总体积重")
    @TableField("pack_total_volume_weight")
    private BigDecimal packTotalVolumeWeight ;


}