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
 * 物流价格附加项目--暂时不需要;
 * @author : LSY
 * @date : 2023-12-29
 */
@TableName("tb_logistics_price_surcharge_items")
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ExcelIgnoreUnannotated
public class TbLogisticsPriceSurchargeItems implements Serializable{
 private static final long serialVersionUID = 1L;
 
    /** lpsiID */
    @ApiModelProperty(value = "lpsiID")
    @TableId (value = "lpsi_id", type = IdType.AUTO)
    @TableField("lpsi_id")
    private BigDecimal lpsiId ;
 
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
 
    /** 项目类别 */
    @ApiModelProperty(value = "项目类别")
    @TableField("lpsi_item_type")
    private String lpsiItemType ;
 
    /** 项目名称 */
    @ApiModelProperty(value = "项目名称")
    @TableField("lpsi_item_name")
    private String lpsiItemName ;
 
    /** 币种 */
    @ApiModelProperty(value = "币种")
    @TableField("lpsi_curr_system")
    private String lpsiCurrSystem ;
 
    /** 单价费用 */
    @ApiModelProperty(value = "单价费用")
    @TableField("lpsi_unit_price")
    private BigDecimal lpsiUnitPrice ;
 
    /** 适用开始日期 */
    @ApiModelProperty(value = "适用开始日期")
    @TableField("lpcsw_app_start_date")
    private Date lpcswAppStartDate ;
 
    /** 适用结束日期 */
    @ApiModelProperty(value = "适用结束日期")
    @TableField("lpcsw_app_end_date")
    private Date lpcswAppEndDate ;


}