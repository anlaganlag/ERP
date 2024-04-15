package com.tadpole.cloud.supplyChain.modular.logisticsStorage.entity;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

 /**
 * 物流单尾程信息;
 * @author : LSY
 * @date : 2023-12-29
 */
@TableName("tb_logistics_list_to_end_route")
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ExcelIgnoreUnannotated
public class TbLogisticsListToEndRoute implements Serializable{
 private static final long serialVersionUID = 1L;
 
    /** ID */
    @ApiModelProperty(value = "ID")
    @TableId (value = "id", type = IdType.AUTO)
    @TableField("id")
    private BigDecimal id ;
 
    /** 发货批次号 */
    @ApiModelProperty(value = "发货批次号")
    @TableField("lhr_code")
    private String lhrCode ;
 
    /** 头程物流单号 */
    @ApiModelProperty(value = "头程物流单号")
    @TableField("lhr_odd_numb")
    private String lhrOddNumb ;
 
    /** 尾程物流单号 */
    @ApiModelProperty(value = "尾程物流单号")
    @TableField("ler_odd_numb")
    private String lerOddNumb ;
 
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
 
    /** 发货件数 */
    @ApiModelProperty(value = "发货件数")
    @TableField("ler_out_good_num")
    private Integer lerOutGoodNum ;
 
    /** 发货重量 */
    @ApiModelProperty(value = "发货重量")
    @TableField("ler_out_good_weight")
    private BigDecimal lerOutGoodWeight ;
 
    /** 发货体积 */
    @ApiModelProperty(value = "发货体积")
    @TableField("ler_out_good_volume")
    private BigDecimal lerOutGoodVolume ;
 
    /** 尾程单状态 */
    @ApiModelProperty(value = "尾程单状态")
    @TableField("ler_state")
    private String lerState ;
 
    /** 尾程单状态流程说明 */
    @ApiModelProperty(value = "尾程单状态流程说明")
    @TableField("ler_state_note")
    private String lerStateNote ;
 
    /** 货运方式1 */
    @ApiModelProperty(value = "货运方式1")
    @TableField("log_tra_mode1")
    private String logTraMode1 ;
 
    /** 尾程物流单链接 */
    @ApiModelProperty(value = "尾程物流单链接")
    @TableField("log_end_route_link")
    private String logEndRouteLink ;
 
    /** 预估计费方式 */
    @ApiModelProperty(value = "预估计费方式")
    @TableField("ler_pre_charg_type")
    private String lerPreChargType ;
 
    /** 预估物流费 */
    @ApiModelProperty(value = "预估物流费")
    @TableField("ler_pre_log_fee")
    private BigDecimal lerPreLogFee ;
 
    /** 预估燃油附加费 */
    @ApiModelProperty(value = "预估燃油附加费")
    @TableField("ler_pre_log_fuel_fee")
    private BigDecimal lerPreLogFuelFee ;
 
    /** 预估物流附加费合计 */
    @ApiModelProperty(value = "预估物流附加费合计")
    @TableField("ler_pre_log_sur_fee_total")
    private BigDecimal lerPreLogSurFeeTotal ;
 
    /** 预估物流附加费明细 */
    @ApiModelProperty(value = "预估物流附加费明细")
    @TableField("ler_pre_log_sur_fee_det")
    private String lerPreLogSurFeeDet ;
 
    /** 预估税费合计 */
    @ApiModelProperty(value = "预估税费合计")
    @TableField("ler_pre_log_tax_fee_total")
    private BigDecimal lerPreLogTaxFeeTotal ;
 
    /** 预估税费明细 */
    @ApiModelProperty(value = "预估税费明细")
    @TableField("ler_pre_log_tax_fee_det")
    private String lerPreLogTaxFeeDet ;
 
    /** 预估总费用(系统计算) */
    @ApiModelProperty(value = "预估总费用(系统计算)")
    @TableField("ler_pre_log_fee_total")
      private BigDecimal lerPreLogFeeTotal ;

    /** 开始日期 */
    @ApiModelProperty(value = "开始日期")
    @TableField("ler_beg_date")
    private Date lerBegDate ;
 
    /** 派送日期 */
    @ApiModelProperty(value = "派送日期")
    @TableField("ler_send_date")
    private Date lerSendDate ;
 
    /** 签收日期 */
    @ApiModelProperty(value = "签收日期")
    @TableField("ler_sign_date")
    private Date lerSignDate ;
 
    /** 延期天数 */
    @ApiModelProperty(value = "延期天数")
    @TableField("ler_delay_day")
    private Integer lerDelayDay ;
 
    /** 预估旺季附加费 */
    @ApiModelProperty(value = "预估旺季附加费")
    @TableField("ler_pre_log_busy_season_add_fee")
    private BigDecimal lerPreLogBusySeasonAddFee ;
 
    /** 预估附加费及杂费 */
    @ApiModelProperty(value = "预估附加费及杂费")
    @TableField("ler_pre_log_add_and_sundry_fee")
    private BigDecimal lerPreLogAddAndSundryFee ;
 
    /** 预估报关费 */
    @ApiModelProperty(value = "预估报关费")
    @TableField("ler_pre_log_cust_dlearance_fee")
    private BigDecimal lerPreLogCustDlearanceFee ;
 
    /** 预估清关费 */
    @ApiModelProperty(value = "预估清关费")
    @TableField("ler_pre_log_cust_clearance_fee")
    private BigDecimal lerPreLogCustClearanceFee ;
 
    /** 预估税费 */
    @ApiModelProperty(value = "预估税费")
    @TableField("ler_pre_log_tax_fee")
    private BigDecimal lerPreLogTaxFee ;
 
    /** 预估总费用(人工维护) */
    @ApiModelProperty(value = "预估总费用(人工维护)")
    @TableField("ler_pre_log_fee_total_manual")
    private BigDecimal lerPreLogFeeTotalManual ;
 
    /** 预估使用的计费量 */
    @ApiModelProperty(value = "预估使用的计费量")
    @TableField("ler_log_fee_weight")
    private BigDecimal lerLogFeeWeight ;
 
    /** 计费币种 */
    @ApiModelProperty(value = "计费币种")
    @TableField("ler_log_comfirm_bill_currency")
    private String lerLogComfirmBillCurrency ;
 
    /** 预估单价 */
    @ApiModelProperty(value = "预估单价")
    @TableField("ler_pre_log_unit_price")
    private BigDecimal lerPreLogUnitPrice ;
 
    /** 预估单价类型 */
    @ApiModelProperty(value = "预估单价类型")
    @TableField("ler_pre_log_unit_price_type")
    private String lerPreLogUnitPriceType ;
 
    /** 预估附加费及杂费备注 */
    @ApiModelProperty(value = "预估附加费及杂费备注")
    @TableField("ler_pre_log_add_and_sundry_fee_remark")
    private String lerPreLogAddAndSundryFeeRemark ;

    /** 单价 */
    @ApiModelProperty(value = "单价")
    @TableField("Ler_Log_Unit_Price")
    private BigDecimal lerLogUnitPrice ;

    /** 燃油附加费 */
    @ApiModelProperty(value = "燃油附加费")
    @TableField("Ler_Log_Fuel_Fee")
    private BigDecimal lerLogFuelFee ;

    /** 旺季附加费 */
    @ApiModelProperty(value = "旺季附加费")
    @TableField("Ler_Log_Busy_Season_Add_Fee")
    private BigDecimal lerLogBusySeasonAddFee ;

    /** 附加费及杂费 */
    @ApiModelProperty(value = "附加费及杂费")
    @TableField("Ler_Log_Add_And_Sundry_Fee")
    private BigDecimal lerLogAddAndSundryFee ;

    /** 报关费 */
    @ApiModelProperty(value = "报关费")
    @TableField("Ler_Log_Cust_Dlearance_Fee")
    private BigDecimal lerLogCustDlearanceFee ;

    /** 清关费 */
    @ApiModelProperty(value = "清关费")
    @TableField("Ler_Log_Cust_Clearance_Fee")
    private BigDecimal lerLogCustClearanceFee ;

    /** 税费 */
    @ApiModelProperty(value = "税费")
    @TableField("Ler_Log_Tax_Fee")
    private BigDecimal lerLogTaxFee ;

    /** 物流费 */
    @ApiModelProperty(value = "物流费")
    @TableField("Ler_Log_Fee")
    private BigDecimal lerLogFee ;

    /** 预估总费用 */
    @ApiModelProperty(value = "预估总费用")
    @TableField("Ler_Pre_Log_Fee_Total_New")
    private BigDecimal lerPreLogFeeTotalNew ;

    /** 总费用 */
    @ApiModelProperty(value = "总费用")
    @TableField("Ler_Log_Fee_Total_New")
    private BigDecimal lerLogFeeTotalNew ;
}