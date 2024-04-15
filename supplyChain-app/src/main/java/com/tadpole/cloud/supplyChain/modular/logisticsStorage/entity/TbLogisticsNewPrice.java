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
 * 物流商的价格信息;
 * @author : LSY
 * @date : 2023-12-29
 */
@TableName("tb_logistics_new_price")
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ExcelIgnoreUnannotated
public class TbLogisticsNewPrice implements Serializable{
 private static final long serialVersionUID = 1L;
 
    /** 物流价格编号 */
    @ApiModelProperty(value = "物流价格编号")
    @TableId (value = "pk_logp_id", type = IdType.AUTO)
    @TableField("pk_logp_id")
    private BigDecimal pkLogpId ;
 
    /** 创建日期 */
    @ApiModelProperty(value = "创建日期")
    @TableField("sys_create_date")
    private Date sysCreateDate ;
 
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
 
    /** 物流账号 */
    @ApiModelProperty(value = "物流账号")
    @TableField("bus_lc_code")
    private String busLcCode ;
 
    /** 站点 */
    @ApiModelProperty(value = "站点")
    @TableField("bus_lp_country_code")
    private String busLpCountryCode ;
 
    /** 分区号 */
    @ApiModelProperty(value = "分区号--新平台叫 国家地区")
    @TableField("bus_lsp_num")
    private String busLspNum ;
 
    /** 货运方式1 */
    @ApiModelProperty(value = "货运方式1")
    @TableField("bus_log_tra_mode1")
    private String busLogTraMode1 ;
 
    /** 发货方式 */
    @ApiModelProperty(value = "发货方式")
    @TableField("bus_log_tra_mode2")
    private String busLogTraMode2 ;
 
    /** 物流渠道 */
    @ApiModelProperty(value = "物流渠道")
    @TableField("bus_log_sea_tra_route")
    private String busLogSeaTraRoute ;
 
    /** 红蓝单 */
    @ApiModelProperty(value = "红蓝单")
    @TableField("bus_log_red_or_blue_list")
    private String busLogRedOrBlueList ;
 
    /** 货物特性 */
    @ApiModelProperty(value = "货物特性")
    @TableField("bus_log_good_character")
    private String busLogGoodCharacter ;
 
    /** 是否含税 */
    @ApiModelProperty(value = "是否含税")
    @TableField("bus_logp_is_inc_tax")
    private Integer busLogpIsIncTax ;
 
    /** 计费币种 */
    @ApiModelProperty(value = "计费币种")
    @TableField("bus_logp_charg_currency")
    private String busLogpChargCurrency ;
 
    /** 计费方式 */
    @ApiModelProperty(value = "计费方式")
    @TableField("bus_logp_charg_type")
    private String busLogpChargType ;


}