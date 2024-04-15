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
 * 物流账单明细;
 * @author : LSY
 * @date : 2023-12-29
 */
@TableName("tb_logistics_bill_detail")
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ExcelIgnoreUnannotated
public class TbLogisticsBillDetail implements Serializable{
 private static final long serialVersionUID = 1L;
 
    /** 系统编号 */
    @ApiModelProperty(value = "系统编号")
    @TableId (value = "id", type = IdType.AUTO)
    @TableField("id")
    private BigDecimal id ;
 
    /** 物流商编码 */
    @ApiModelProperty(value = "物流商编码")
    @TableField("lp_code")
    private String lpCode ;
 
    /** 物流账号 */
    @ApiModelProperty(value = "物流账号")
    @TableField("lc_code")
    private String lcCode ;
 
    /** 物流对账单号 */
    @ApiModelProperty(value = "物流对账单号")
    @TableField("bill_num")
    private String billNum ;
 
    /** 物流跟踪单号（头程物流单号） */
    @ApiModelProperty(value = "物流跟踪单号（头程物流单号）")
    @TableField("lhr_odd_num")
    private String lhrOddNum ;
 
    /** 物流订单日期 */
    @ApiModelProperty(value = "物流订单日期")
    @TableField("logistics_order_date")
    private Date logisticsOrderDate ;
 
    /** 平台 */
    @ApiModelProperty(value = "平台")
    @TableField("ele_platform_name")
    private String elePlatformName ;
 
    /** 账号 */
    @ApiModelProperty(value = "账号")
    @TableField("shop_name_simple")
    private String shopNameSimple ;
 
    /** 站点 */
    @ApiModelProperty(value = "站点")
    @TableField("country_code")
    private String countryCode ;
 
    /** 金畅出货日期 */
    @ApiModelProperty(value = "金畅出货日期")
    @TableField("lhr_send_good_date")
    private Date lhrSendGoodDate ;
 
    /** 转单号 */
    @ApiModelProperty(value = "转单号")
    @TableField("transfer_num")
    private String transferNum ;
 
    /** 渠道及派送 */
    @ApiModelProperty(value = "渠道及派送")
    @TableField("channel")
    private String channel ;
 
    /** 申报价值 */
    @ApiModelProperty(value = "申报价值")
    @TableField("declared_val")
    private String declaredVal ;
 
    /** 目的国 */
    @ApiModelProperty(value = "目的国")
    @TableField("destination_country")
    private String destinationCountry ;
 
    /** 目的仓 */
    @ApiModelProperty(value = "目的仓")
    @TableField("destination_warehouse")
    private String destinationWarehouse ;
 
    /** 单价 */
    @ApiModelProperty(value = "单价")
    @TableField("unit_price")
    private BigDecimal unitPrice ;
 
    /** 汇率 */
    @ApiModelProperty(value = "汇率")
    @TableField("exchange_rate")
    private BigDecimal exchangeRate ;
 
    /** FBA ID */
    @ApiModelProperty(value = "FBA ID")
    @TableField("shipment_id")
    private String shipmentId ;
 
    /** 总费用 */
    @ApiModelProperty(value = "总费用")
    @TableField("total_cost")
    private BigDecimal totalCost ;
 
    /** 海外税号 */
    @ApiModelProperty(value = "海外税号")
    @TableField("vat_no")
    private String vatNo ;
 
    /** 海外税号的后四位 */
    @ApiModelProperty(value = "海外税号的后四位")
    @TableField("eori")
    private String eori ;
 
    /** 计费箱数 */
    @ApiModelProperty(value = "计费箱数")
    @TableField("box_num")
    private Integer boxNum ;
 
    /** 箱数 */
    @ApiModelProperty(value = "箱数")
    @TableField("diff_box_num")
    private Integer diffBoxNum ;
 
    /** 计费重量 */
    @ApiModelProperty(value = "计费重量")
    @TableField("weight")
    private BigDecimal weight ;
 
    /** 差异重量 */
    @ApiModelProperty(value = "差异重量")
    @TableField("diff_weight")
    private BigDecimal diffWeight ;
 
    /** 体积重 */
    @ApiModelProperty(value = "体积重")
    @TableField("volume_weight")
    private BigDecimal volumeWeight ;
 
    /** 差异体积重 */
    @ApiModelProperty(value = "差异体积重")
    @TableField("diff_volume_weight")
    private BigDecimal diffVolumeWeight ;
 
    /** 实重 */
    @ApiModelProperty(value = "实重")
    @TableField("real_weight")
    private BigDecimal realWeight ;
 
    /** Quantity */
    @ApiModelProperty(value = "Quantity")
    @TableField("quantity")
    private Integer quantity ;
 
    /** 发货批次号 */
    @ApiModelProperty(value = "发货批次号")
    @TableField("lhr_code")
    private String lhrCode ;
 
    /** 计费类型 */
    @ApiModelProperty(value = "计费类型")
    @TableField("charg_type")
    private String chargType ;
 
    /** 物流跟踪单 对账状态 */
    @ApiModelProperty(value = "物流跟踪单 对账状态")
    @TableField("status")
    private String status ;
 
    /** 红蓝单 */
    @ApiModelProperty(value = "红蓝单")
    @TableField("log_red_or_blue_list")
    private String logRedOrBlueList ;
 
    /** 货运方式1 */
    @ApiModelProperty(value = "货运方式1")
    @TableField("log_tra_mode1")
    private String logTraMode1 ;
 
    /** 货运方式2 */
    @ApiModelProperty(value = "货运方式2")
    @TableField("log_tra_mode2")
    private String logTraMode2 ;
 
    /** 海运路线 */
    @ApiModelProperty(value = "海运路线")
    @TableField("log_sea_tra_route")
    private String logSeaTraRoute ;
 
    /** 货物特性 */
    @ApiModelProperty(value = "货物特性")
    @TableField("log_good_character")
    private String logGoodCharacter ;
 
    /** 是否含税 */
    @ApiModelProperty(value = "是否含税")
    @TableField("logp_is_inc_tax")
    private Integer logpIsIncTax ;
 
    /** 海运货柜类型 */
    @ApiModelProperty(value = "海运货柜类型")
    @TableField("log_sea_tra_con_type")
    private String logSeaTraConType ;
 
    /** 预估计费方式 */
    @ApiModelProperty(value = "预估计费方式")
    @TableField("ler_pre_charg_type")
    private String lerPreChargType ;
 
    /** 是否无用 */
    @ApiModelProperty(value = "是否无用")
    @TableField("is_normal")
    private Integer isNormal ;
 
    /** 备注 */
    @ApiModelProperty(value = "备注")
    @TableField("remarks")
    private String remarks ;


}