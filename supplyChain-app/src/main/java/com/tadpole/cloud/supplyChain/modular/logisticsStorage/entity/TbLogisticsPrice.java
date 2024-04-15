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
 * 物流价格--暂时不需要;
 * @author : LSY
 * @date : 2023-12-29
 */
@TableName("tb_logistics_price")
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ExcelIgnoreUnannotated
public class TbLogisticsPrice implements Serializable{
 private static final long serialVersionUID = 1L;
 
    /** logpID */
    @ApiModelProperty(value = "logpID")
    @TableId (value = "logp_id", type = IdType.AUTO)
    @TableField("logp_id")
    private BigDecimal logpId ;
 
    /** 添加时间 */
    @ApiModelProperty(value = "添加时间")
    @TableField("sys_add_date")
    private Date sysAddDate ;
 
    /** 操作人 */
    @ApiModelProperty(value = "操作人")
    @TableField("sys_per_name")
    private String sysPerName ;
 
    /** 更新时间 */
    @ApiModelProperty(value = "更新时间")
    @TableField("sys_upd_datetime")
    private Date sysUpdDatetime ;
 
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
 
    /** 国家编码 */
    @ApiModelProperty(value = "国家编码")
    @TableField("country_code")
    private String countryCode ;
 
    /** 国家分区 */
    @ApiModelProperty(value = "国家分区")
    @TableField("lsp_num")
    private String lspNum ;
 
    /** 收货分区 */
    @ApiModelProperty(value = "收货分区")
    @TableField("country_area_name")
    private String countryAreaName ;
 
    /** 货运方式1 */
    @ApiModelProperty(value = "货运方式1")
    @TableField("log_tra_mode1")
    private String logTraMode1 ;
 
    /** 货运方式2 */
    @ApiModelProperty(value = "货运方式2")
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
 
    /** 计费方式 */
    @ApiModelProperty(value = "计费方式")
    @TableField("logp_charg_type")
    private String logpChargType ;


}