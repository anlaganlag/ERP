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
 * 亚马逊物流索赔;
 * @author : LSY
 * @date : 2023-12-29
 */
@TableName("tb_logistics_claim_to_amazon")
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ExcelIgnoreUnannotated
public class TbLogisticsClaimToAmazon implements Serializable{
 private static final long serialVersionUID = 1L;
 
    /** 索赔系统编号(yyyyMMddHHmmssffff) */
    @ApiModelProperty(value = "索赔系统编号(yyyyMMddHHmmssffff)")
    @TableId (value = "pk_claim_id", type = IdType.ASSIGN_ID)
    @TableField("pk_claim_id")
    private String pkClaimId ;
 
    /** 创建日期 */
    @ApiModelProperty(value = "创建日期")
    @TableField("sys_create_date")
    private Date sysCreateDate ;
 
    /** 创建人编号 */
    @ApiModelProperty(value = "创建人编号")
    @TableField("sys_create_per_code")
    private String sysCreatePerCode ;
 
    /** 创建人姓名 */
    @ApiModelProperty(value = "创建人姓名")
    @TableField("sys_create_per_name")
    private String sysCreatePerName ;
 
    /** 账号 */
    @ApiModelProperty(value = "账号")
    @TableField("bus_shop_name_simple")
    private String busShopNameSimple ;
 
    /** 站点 */
    @ApiModelProperty(value = "站点")
    @TableField("bus_country_code")
    private String busCountryCode ;
 
    /** FBA号 */
    @ApiModelProperty(value = "FBA号")
    @TableField("bus_shipment_id")
    private String busShipmentId ;
 
    /** FBA状态 */
    @ApiModelProperty(value = "FBA状态")
    @TableField("bus_shipment_status")
    private String busShipmentStatus ;
 
    /** ShipTo */
    @ApiModelProperty(value = "ShipTo")
    @TableField("bus_ship_to")
    private String busShipTo ;
 
    /** Total SKUs */
    @ApiModelProperty(value = "Total SKUs")
    @TableField("bus_total_skus")
    private Integer busTotalSkus ;
 
    /** Total Units */
    @ApiModelProperty(value = "Total Units")
    @TableField("bus_total_units")
    private Integer busTotalUnits ;
 
    /** Case 创建时间 */
    @ApiModelProperty(value = "Case 创建时间")
    @TableField("bus_case_create_date")
    private Date busCaseCreateDate ;
 
    /** CaseID */
    @ApiModelProperty(value = "CaseID")
    @TableField("bus_case_id")
    private String busCaseId ;
 
    /** Case 备注 */
    @ApiModelProperty(value = "Case 备注")
    @TableField("bus_case_remarks")
    private String busCaseRemarks ;
 
    /** Case 创建人编号 */
    @ApiModelProperty(value = "Case 创建人编号")
    @TableField("bus_case_per_code")
    private String busCasePerCode ;
 
    /** Case 创建人姓名 */
    @ApiModelProperty(value = "Case 创建人姓名")
    @TableField("bus_case_per_name")
    private String busCasePerName ;


}