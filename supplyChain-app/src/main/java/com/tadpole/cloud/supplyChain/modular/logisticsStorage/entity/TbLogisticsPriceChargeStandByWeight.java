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
 * 物流价格按重量计费的--暂时不需要;
 * @author : LSY
 * @date : 2023-12-29
 */
@TableName("tb_logistics_price_charge_stand_by_weight")
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ExcelIgnoreUnannotated
public class TbLogisticsPriceChargeStandByWeight implements Serializable{
 private static final long serialVersionUID = 1L;
 
    /** lpcswID */
    @ApiModelProperty(value = "lpcswID")
    @TableId (value = "lpcsw_id", type = IdType.AUTO)
    @TableField("lpcsw_id")
    private BigDecimal lpcswId ;
 
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
 
    /** 重量大于 */
    @ApiModelProperty(value = "重量大于")
    @TableField("lpcsw_weight_l")
    private BigDecimal lpcswWeightL ;
 
    /** 重量小于等于 */
    @ApiModelProperty(value = "重量小于等于")
    @TableField("lpcsw_weight_h")
    private BigDecimal lpcswWeightH ;
 
    /** 币种 */
    @ApiModelProperty(value = "币种")
    @TableField("lpcsw_curr_system")
    private String lpcswCurrSystem ;
 
    /** 单价费用 */
    @ApiModelProperty(value = "单价费用")
    @TableField("lpcsw_unit_price")
    private BigDecimal lpcswUnitPrice ;
 
    /** 适用日期起 */
    @ApiModelProperty(value = "适用日期起")
    @TableField("lpcsw_app_date")
    private Date lpcswAppDate ;


}