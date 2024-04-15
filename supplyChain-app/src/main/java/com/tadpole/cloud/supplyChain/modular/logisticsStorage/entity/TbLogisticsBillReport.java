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
 * 物流账单报告;
 * @author : LSY
 * @date : 2023-12-29
 */
@TableName("tb_logistics_bill_report")
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ExcelIgnoreUnannotated
public class TbLogisticsBillReport implements Serializable{
 private static final long serialVersionUID = 1L;
 
    /** ID */
    @ApiModelProperty(value = "ID")
    @TableId (value = "id", type = IdType.AUTO)
    @TableField("id")
    private BigDecimal id ;
 
    /** 物流跟踪单号（头程物流单号） */
    @ApiModelProperty(value = "物流跟踪单号（头程物流单号）")
    @TableField("lhr_odd_num")
    private String lhrOddNum ;
 
    /** 金畅出货日期 */
    @ApiModelProperty(value = "金畅出货日期")
    @TableField("lhr_send_good_date")
    private Date lhrSendGoodDate ;
 
    /** 店铺简称 */
    @ApiModelProperty(value = "店铺简称")
    @TableField("shop_name_simple")
    private String shopNameSimple ;
 
    /** 国家编码 */
    @ApiModelProperty(value = "国家编码")
    @TableField("country_code")
    private String countryCode ;
 
    /** 物流账号 */
    @ApiModelProperty(value = "物流账号")
    @TableField("lc_code")
    private String lcCode ;
 
    /** 是否报关 */
    @ApiModelProperty(value = "是否报关")
    @TableField("logp_is_entry")
    private Integer logpIsEntry ;
 
    /** 货运方式1 */
    @ApiModelProperty(value = "货运方式1")
    @TableField("log_tra_mode1")
    private String logTraMode1 ;
 
    /** 货运方式2 */
    @ApiModelProperty(value = "货运方式2")
    @TableField("log_tra_mode2")
    private String logTraMode2 ;
 
    /** 红蓝单 */
    @ApiModelProperty(value = "红蓝单")
    @TableField("log_red_or_blue_list")
    private String logRedOrBlueList ;
 
    /** 发货批次号 */
    @ApiModelProperty(value = "发货批次号")
    @TableField("lhr_code")
    private String lhrCode ;
 
    /** 出仓发货件数 */
    @ApiModelProperty(value = "出仓发货件数")
    @TableField("lhr_out_good_num")
    private Integer lhrOutGoodNum ;
 
    /** 出仓发货重量 */
    @ApiModelProperty(value = "出仓发货重量")
    @TableField("lhr_out_good_weight")
    private BigDecimal lhrOutGoodWeight ;
 
    /** 计费重量 */
    @ApiModelProperty(value = "计费重量")
    @TableField("chargeable_weight")
    private BigDecimal chargeableWeight ;
 
    /** 差异重量 */
    @ApiModelProperty(value = "差异重量")
    @TableField("diff_weight")
    private BigDecimal diffWeight ;
 
    /** 总费用 */
    @ApiModelProperty(value = "总费用")
    @TableField("total_cost")
    private BigDecimal totalCost ;
 
    /** 燃油费率 */
    @ApiModelProperty(value = "燃油费率")
    @TableField("lfr_ruel_rate")
    private BigDecimal lfrRuelRate ;
 
    /** 单价费用 */
    @ApiModelProperty(value = "单价费用")
    @TableField("lpcsw_unit_price")
    private BigDecimal lpcswUnitPrice ;
 
    /** ShipmentID */
    @ApiModelProperty(value = "ShipmentID")
    @TableField("shipment_id")
    private String shipmentId ;
 
    /** Quantity */
    @ApiModelProperty(value = "Quantity")
    @TableField("quantity")
    private Integer quantity ;
 
    /** 物流对账单号 */
    @ApiModelProperty(value = "物流对账单号")
    @TableField("bill_num")
    private String billNum ;
 
    /** 状态 */
    @ApiModelProperty(value = "状态")
    @TableField("state")
    private String state ;
 
    /** 物流审核时间 */
    @ApiModelProperty(value = "物流审核时间")
    @TableField("logistics_audit")
    private Date logisticsAudit ;
 
    /** 财务审核时间 */
    @ApiModelProperty(value = "财务审核时间")
    @TableField("finance_audit")
    private Date financeAudit ;
 
    /** 是否支付 */
    @ApiModelProperty(value = "是否支付")
    @TableField("is_payment")
    private Integer isPayment ;
 
    /** 纳税时间 */
    @ApiModelProperty(value = "纳税时间")
    @TableField("tax_payment")
    private Date taxPayment ;
 
    /** 物流成本核算时间 */
    @ApiModelProperty(value = "物流成本核算时间")
    @TableField("logistics_costs")
    private Date logisticsCosts ;


}