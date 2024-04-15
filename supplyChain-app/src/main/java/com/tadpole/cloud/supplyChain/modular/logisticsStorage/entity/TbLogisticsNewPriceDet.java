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
 * 物流商的价格信息-明细;
 * @author : LSY
 * @date : 2023-12-29
 */
@TableName("tb_logistics_new_price_det")
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ExcelIgnoreUnannotated
public class TbLogisticsNewPriceDet implements Serializable{
 private static final long serialVersionUID = 1L;
 
    /** 物流价格明细编号 */
    @ApiModelProperty(value = "物流价格明细编号")
    @TableId (value = "pk_logp_det_id", type = IdType.AUTO)
    @TableField("pk_logp_det_id")
    private BigDecimal pkLogpDetId ;
 
    /** 物流价格编号 */
    @ApiModelProperty(value = "物流价格编号")
    @TableField("pk_logp_id")
    private BigDecimal pkLogpId ;
 
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
 
    /** 最后更新日期 */
    @ApiModelProperty(value = "最后更新日期")
    @TableField("sys_upd_datetime")
    private Date sysUpdDatetime ;
 
    /** 最后更新人编号 */
    @ApiModelProperty(value = "最后更新人编号")
    @TableField("sys_upd_per_code")
    private String sysUpdPerCode ;
 
    /** 最后更新人姓名 */
    @ApiModelProperty(value = "最后更新人姓名")
    @TableField("sys_upd_per_name")
    private String sysUpdPerName ;
 
    /** 重量KG(>) */
    @ApiModelProperty(value = "重量KG(>)")
    @TableField("bus_logp_det_weight_greater")
    private BigDecimal busLogpDetWeightGreater ;
 
    /** 重量KG(<) */
    @ApiModelProperty(value = "重量KG(<)")
    @TableField("bus_logp_det_weight_less")
    private BigDecimal busLogpDetWeightLess ;
 
    /** 体积CBM(>) */
    @ApiModelProperty(value = "体积CBM(>)")
    @TableField("bus_logp_det_volume_greater")
    private BigDecimal busLogpDetVolumeGreater ;
 
    /** 体积CBM(<) */
    @ApiModelProperty(value = "体积CBM(<)")
    @TableField("bus_logp_det_volume_less")
    private BigDecimal busLogpDetVolumeLess ;
 
    /** 单价费用 */
    @ApiModelProperty(value = "单价费用")
    @TableField("bus_logp_det_unit_price")
    private BigDecimal busLogpDetUnitPrice ;
 
    /** 报关费 */
    @ApiModelProperty(value = "报关费")
    @TableField("bus_logp_det_cust_dlearance_fee")
    private BigDecimal busLogpDetCustDlearanceFee ;
 
    /** 清关费 */
    @ApiModelProperty(value = "清关费")
    @TableField("bus_logp_det_cust_clearance_fee")
    private BigDecimal busLogpDetCustClearanceFee ;
 
    /** 旺季附加费KG */
    @ApiModelProperty(value = "旺季附加费KG")
    @TableField("bus_logp_det_busy_season_add_fee")
    private BigDecimal busLogpDetBusySeasonAddFee ;
 
    /** 燃油附加税率 */
    @ApiModelProperty(value = "燃油附加税率")
    @TableField("bus_logp_det_fuel_fee")
    private BigDecimal busLogpDetFuelFee ;
 
    /** 附加费及杂费KG */
    @ApiModelProperty(value = "附加费及杂费KG")
    @TableField("bus_logp_det_add_and_sundry_fee")
    private BigDecimal busLogpDetAddAndSundryFee ;
 
    /** 备注 */
    @ApiModelProperty(value = "备注")
    @TableField("bus_logp_det_remark")
    private String busLogpDetRemark ;
 
    /** 适用开始日期 */
    @ApiModelProperty(value = "适用开始日期")
    @TableField("bus_logp_det_app_start_date")
    private Date busLogpDetAppStartDate ;
 
    /** 适用结束日期 */
    @ApiModelProperty(value = "适用结束日期")
    @TableField("bus_logp_det_app_end_date")
    private Date busLogpDetAppEndDate ;
 
    /** 价格明细状态(-1:失效,0:预备中,1:生效) */
    @ApiModelProperty(value = "价格明细状态(-1:失效,0:预备中,1:生效)")
    @TableField("bus_logp_det_status")
    private Integer busLogpDetStatus ;
 
    /** 启用状态;'已启用,已禁用' */
    @ApiModelProperty(value = "启用状态")
    @TableField("bus_logp_det_use_status")
    private String busLogpDetUseStatus ;


}