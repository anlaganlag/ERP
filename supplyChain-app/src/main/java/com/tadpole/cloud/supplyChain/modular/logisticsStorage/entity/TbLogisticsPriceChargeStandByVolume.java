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
 * 物流价格按体积计费的--暂时不需要;
 * @author : LSY
 * @date : 2023-12-29
 */
@TableName("tb_logistics_price_charge_stand_by_volume")
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ExcelIgnoreUnannotated
public class TbLogisticsPriceChargeStandByVolume implements Serializable{
 private static final long serialVersionUID = 1L;
 
    /** lpcsvID */
    @ApiModelProperty(value = "lpcsvID")
    @TableId (value = "lpcsv_id", type = IdType.AUTO)
    @TableField("lpcsv_id")
    private BigDecimal lpcsvId ;
 
    /** logpID */
    @ApiModelProperty(value = "logpID")
    @TableField("logp_id")
    private BigDecimal logpId ;
 
    /** 操作人 */
    @ApiModelProperty(value = "操作人")
    @TableField("sys_per_name")
    private String sysPerName ;
 
    /** 更新时间 */
    @ApiModelProperty(value = "更新时间")
    @TableField("sys_upd_datetime")
    private Date sysUpdDatetime ;
 
    /** 体积大于等于 */
    @ApiModelProperty(value = "体积大于等于")
    @TableField("lpcsv_volume_l")
    private BigDecimal lpcsvVolumeL ;
 
    /** 体积小于 */
    @ApiModelProperty(value = "体积小于")
    @TableField("lpcsv_volumet_h")
    private BigDecimal lpcsvVolumetH ;
 
    /** 币种 */
    @ApiModelProperty(value = "币种")
    @TableField("lpcsv_curr_system")
    private String lpcsvCurrSystem ;
 
    /** 单价费用 */
    @ApiModelProperty(value = "单价费用")
    @TableField("lpcsv_unit_price")
    private BigDecimal lpcsvUnitPrice ;
 
    /** 适用日期起 */
    @ApiModelProperty(value = "适用日期起")
    @TableField("lpcsv_app_date")
    private Date lpcsvAppDate ;


}