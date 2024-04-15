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
 * 物流单头程信息;
 * @author : LSY
 * @date : 2023-12-29
 */
@TableName("tb_logistics_list_to_head_route")
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ExcelIgnoreUnannotated
public class TbLogisticsListToHeadRoute implements Serializable{
 private static final long serialVersionUID = 1L;
 
    /** 系统编号 */
    @ApiModelProperty(value = "系统编号")
    @TableId (value = "id", type = IdType.AUTO)
    @TableField("id")
    private BigDecimal id ;
 
    /** 发货批次号 */
    @ApiModelProperty(value = "发货批次号")
    @TableField("lhr_code")
    private String lhrCode ;
 
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
 
    /** 待发区仓位 */
    @ApiModelProperty(value = "待发区仓位")
    @TableField("com_wait_area")
    private String comWaitArea ;
 
    /** 电商平台 */
    @ApiModelProperty(value = "电商平台")
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
 
    /** 收仓库类型 */
    @ApiModelProperty(value = "收仓库类型")
    @TableField("pack_store_house_type")
    private String packStoreHouseType ;
 
    /** 收货仓名称 */
    @ApiModelProperty(value = "收货仓名称")
    @TableField("pack_store_house_name")
    private String packStoreHouseName ;
 
    /** 出仓发货件数 */
    @ApiModelProperty(value = "出仓发货件数")
    @TableField("lhr_out_good_num")
    private Integer lhrOutGoodNum ;
 
    /** 出仓发货重量 */
    @ApiModelProperty(value = "出仓发货重量")
    @TableField("lhr_out_good_weight")
    private BigDecimal lhrOutGoodWeight ;
 
    /** 出仓体积 */
    @ApiModelProperty(value = "出仓体积")
    @TableField("lhr_out_good_volume")
    private BigDecimal lhrOutGoodVolume ;
 
    /** LhrChargeWeight */
    @ApiModelProperty(value = "LhrChargeWeight")
    @TableField("lhr_charge_weight")
    private BigDecimal lhrChargeWeight ;
 
    /** 头程单状态 */
    @ApiModelProperty(value = "头程单状态")
    @TableField("lhr_state")
    private String lhrState ;
 
    /** 头程单状态流程说明 */
    @ApiModelProperty(value = "头程单状态流程说明")
    @TableField("lhr_state_note")
    private String lhrStateNote ;
 
    /** 通关数据同步状态 */
    @ApiModelProperty(value = "通关数据同步状态")
    @TableField("lhr_data_syn_state")
    private String lhrDataSynState ;
 
    /** 是否报关 */
    @ApiModelProperty(value = "是否报关")
    @TableField("logp_is_entry")
    private Integer logpIsEntry ;
 
    /** 出仓日期 */
    @ApiModelProperty(value = "出仓日期")
    @TableField("lhr_out_warehouse_date")
    private Date lhrOutWarehouseDate ;
 
    /** 物流账号 */
    @ApiModelProperty(value = "物流账号")
    @TableField("lc_code")
    private String lcCode ;
 
    /** 物流商编码 */
    @ApiModelProperty(value = "物流商编码")
    @TableField("lp_code")
    private String lpCode ;
 
    /** 物流商简称 */
    @ApiModelProperty(value = "物流商简称")
    @TableField("lp_simple_name")
    private String lpSimpleName ;
 
    /** 所属公司 */
    @ApiModelProperty(value = "所属公司")
    @TableField("com_name_cn")
    private String comNameCn ;
 
    /** 头程物流单号 */
    @ApiModelProperty(value = "头程物流单号")
    @TableField("lhr_odd_numb")
    private String lhrOddNumb ;
 
    /** 发货日期 */
    @ApiModelProperty(value = "发货日期")
    @TableField("lhr_send_good_date")
    private Date lhrSendGoodDate ;
 
    /** 国家分区 */
    @ApiModelProperty(value = "国家分区")
    @TableField("lsp_num")
    private String lspNum ;
 
    /** 目的地三字代码 */
    @ApiModelProperty(value = "目的地三字代码")
    @TableField("lhr_des_thr_char_code")
    private String lhrDesThrCharCode ;
 
    /** 收货分区 */
    @ApiModelProperty(value = "收货分区")
    @TableField("country_area_name")
    private String countryAreaName ;
 
    /** 国家/地区 */
    @ApiModelProperty(value = "国家/地区")
    @TableField("log_rec_country")
    private String logRecCountry ;
 
    /** 城市 */
    @ApiModelProperty(value = "城市")
    @TableField("log_rec_city")
    private String logRecCity ;
 
    /** 州/省/郡 */
    @ApiModelProperty(value = "州/省/郡")
    @TableField("log_rec_state")
    private String logRecState ;
 
    /** 收货地址1 */
    @ApiModelProperty(value = "收货地址1")
    @TableField("log_rec_address1")
    private String logRecAddress1 ;
 
    /** 收货地址2 */
    @ApiModelProperty(value = "收货地址2")
    @TableField("log_rec_address2")
    private String logRecAddress2 ;
 
    /** 收获地址3 */
    @ApiModelProperty(value = "收获地址3")
    @TableField("log_rec_address3")
    private String logRecAddress3 ;
 
    /** 邮编 */
    @ApiModelProperty(value = "邮编")
    @TableField("log_rec_zip")
    private String logRecZip ;
 
    /** 收件人 */
    @ApiModelProperty(value = "收件人")
    @TableField("log_rec_person")
    private String logRecPerson ;
 
    /** 联系电话 */
    @ApiModelProperty(value = "联系电话")
    @TableField("log_rec_person_tel")
    private String logRecPersonTel ;
 
    /** 收件公司 */
    @ApiModelProperty(value = "收件公司")
    @TableField("log_rec_company")
    private String logRecCompany ;
 
    /** 货运方式1 */
    @ApiModelProperty(value = "货运方式1")
    @TableField("log_tra_mode1")
    private String logTraMode1 ;
 
    /** 头程物流单链接 */
    @ApiModelProperty(value = "头程物流单链接")
    @TableField("log_head_route_link")
    private String logHeadRouteLink ;
 
    /** 运输方式 */
    @ApiModelProperty(value = "运输方式")
    @TableField("log_tra_mode2")
    private String logTraMode2 ;
 
    /** 物流渠道 */
    @ApiModelProperty(value = "物流渠道")
    @TableField("log_sea_tra_route")
    private String logSeaTraRoute ;
 
    /** 海运货柜类型 */
    @ApiModelProperty(value = "海运货柜类型")
    @TableField("log_sea_tra_con_type")
    private String logSeaTraConType ;
 
    /** 红蓝单 */
    @ApiModelProperty(value = "红蓝单")
    @TableField("log_red_or_blue_list")
    private String logRedOrBlueList ;
 
    /** 货物特性 */
    @ApiModelProperty(value = "货物特性")
    @TableField("log_good_character")
    private String logGoodCharacter ;
 
    /** 是否含税 */
    @ApiModelProperty(value = "是否含税")
    @TableField("logp_is_inc_tax")
    private Integer logpIsIncTax ;
 
    /** 预计到货日期 */
    @ApiModelProperty(value = "预计到货日期")
    @TableField("lhr_pre_arrive_date")
    private Date lhrPreArriveDate ;
 
    /** 起飞日期 */
    @ApiModelProperty(value = "起飞日期")
    @TableField("lhr_flight_date")
    private Date lhrFlightDate ;
 
    /** 到达日期 */
    @ApiModelProperty(value = "到达日期")
    @TableField("lhr_arrival_date")
    private Date lhrArrivalDate ;
 
    /** 结单日期 */
    @ApiModelProperty(value = "结单日期")
    @TableField("lhr_end_list_date")
    private Date lhrEndListDate ;
 
    /** 时效 */
    @ApiModelProperty(value = "时效")
    @TableField("lhr_log_time_count")
    private Integer lhrLogTimeCount ;
 
    /** 备注 */
    @ApiModelProperty(value = "备注")
    @TableField("lhr_note")
    private String lhrNote ;
 
    /** 仓储位置 */
    @ApiModelProperty(value = "仓储位置")
    @TableField("lhr_position")
    private String lhrPosition ;
 
    /** 报关公司 */
    @ApiModelProperty(value = "报关公司")
    @TableField("lhr_cust_broker")
    private String lhrCustBroker ;
 
    /** 是否递延 */
    @ApiModelProperty(value = "是否递延")
    @TableField("log_is_deferred")
    private String logIsDeferred ;
 
    /** 确认计费类型 */
    @ApiModelProperty(value = "确认计费类型")
    @TableField("log_comfirm_bill_type")
    private String logComfirmBillType ;
 
    /** 确认计费量 */
    @ApiModelProperty(value = "确认计费量")
    @TableField("log_comfirm_bill_volume")
    private BigDecimal logComfirmBillVolume ;
 
    /** 境外收货人 */
    @ApiModelProperty(value = "境外收货人")
    @TableField("lhr_overseas_consignee")
    private String lhrOverseasConsignee ;
 
    /** 预估税费 */
    @ApiModelProperty(value = "预估税费")
    @TableField("lhr_pre_log_tax_fee")
    private BigDecimal lhrPreLogTaxFee ;
 
    /** 预估总费用(人工维护) */
    @ApiModelProperty(value = "预估总费用(人工维护)")
    @TableField("lhr_pre_log_fee_total_manual")
    private BigDecimal lhrPreLogFeeTotalManual ;
 
    /** 预估费用是否确认 */
    @ApiModelProperty(value = "预估费用是否确认")
    @TableField("lhr_pre_log_fee_is_confirm")
    private String lhrPreLogFeeIsConfirm ;
 
    /** 预估使用的计费量 */
    @ApiModelProperty(value = "预估使用的计费量")
    @TableField("lhr_log_fee_weight")
    private BigDecimal lhrLogFeeWeight ;
 
    /** 计费币种 */
    @ApiModelProperty(value = "计费币种")
    @TableField("log_comfirm_bill_currency")
    private String logComfirmBillCurrency ;
 
    /** 预估单价 */
    @ApiModelProperty(value = "预估单价")
    @TableField("lhr_pre_log_unit_price")
    private BigDecimal lhrPreLogUnitPrice ;
 
    /** 预估燃油附加费 */
    @ApiModelProperty(value = "预估燃油附加费")
    @TableField("lhr_pre_log_fuel_fee")
    private BigDecimal lhrPreLogFuelFee ;
 
    /** 预估旺季附加费 */
    @ApiModelProperty(value = "预估旺季附加费")
    @TableField("lhr_pre_log_busy_season_add_fee")
    private BigDecimal lhrPreLogBusySeasonAddFee ;
 
    /** 预估附加费及杂费 */
    @ApiModelProperty(value = "预估附加费及杂费")
    @TableField("lhr_pre_log_add_and_sundry_fee")
    private BigDecimal lhrPreLogAddAndSundryFee ;
 
    /** 预估报关费 */
    @ApiModelProperty(value = "预估报关费")
    @TableField("lhr_pre_log_cust_dlearance_fee")
    private BigDecimal lhrPreLogCustDlearanceFee ;
 
    /** 预估清关费 */
    @ApiModelProperty(value = "预估清关费")
    @TableField("lhr_pre_log_cust_clearance_fee")
    private BigDecimal lhrPreLogCustClearanceFee ;
 
    /** 预估单价类型 */
    @ApiModelProperty(value = "预估单价类型")
    @TableField("lhr_pre_log_unit_price_type")
    private String lhrPreLogUnitPriceType ;
 
    /** 预估附加费及杂费备注 */
    @ApiModelProperty(value = "预估附加费及杂费备注")
    @TableField("lhr_pre_log_add_and_sundry_fee_remark")
    private String lhrPreLogAddAndSundryFeeRemark ;



    /** 单价 */
    @ApiModelProperty(value = "单价")
    @TableField("lhr_log_unit_price")
    private BigDecimal lhrLogUnitPrice ;

    /** 燃油附加费 */
    @ApiModelProperty(value = "燃油附加费")
    @TableField("lhr_log_fuel_fee")
    private BigDecimal lhrLogFuelFee ;

    /** 旺季附加费 */
    @ApiModelProperty(value = "旺季附加费")
    @TableField("lhr_log_busy_season_add_fee")
    private BigDecimal lhrLogBusySeasonAddFee ;

    /** 附加费及杂费 */
    @ApiModelProperty(value = "附加费及杂费")
    @TableField("lhr_log_add_and_sundry_fee")
    private BigDecimal lhrLogAddAndSundryFee ;

    /** 报关费 */
    @ApiModelProperty(value = "报关费")
    @TableField("lhr_log_cust_dlearance_fee")
    private BigDecimal lhrLogCustDlearanceFee ;

    /** 清关费 */
    @ApiModelProperty(value = "清关费")
    @TableField("lhr_log_cust_clearance_fee")
    private BigDecimal lhrLogCustClearanceFee ;

    /** 税费 */
    @ApiModelProperty(value = "税费")
    @TableField("lhr_log_tax_fee")
    private BigDecimal lhrLogTaxFee ;

    /** 物流费 */
    @ApiModelProperty(value = "物流费")
    @TableField("lhr_log_fee")
    private BigDecimal lhrLogFee ;

    /** 预估总费用 */
    @ApiModelProperty(value = "预估总费用")
    @TableField("lhr_pre_log_fee_total_new")
    private BigDecimal lhrPreLogFeeTotalNew ;

    /** 总费用 */
    @ApiModelProperty(value = "总费用")
    @TableField("lhr_log_fee_total_new")
    private BigDecimal lhrLogFeeTotalNew ;

    /** 预估物流费 */
    @ApiModelProperty(value = "预估物流费")
    @TableField("lhr_Pre_Log_Fee")
    private BigDecimal lhrPreLogFee ;

    /** 物流单状态 */
    @ApiModelProperty(value = "物流单状态")
    @TableField("lhr_Log_Status")
    private String lhrLogStatus ;


}