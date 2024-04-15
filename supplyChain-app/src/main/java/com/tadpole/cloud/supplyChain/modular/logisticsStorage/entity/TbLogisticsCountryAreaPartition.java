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
 * 物流国家区域划分;
 * @author : LSY
 * @date : 2023-12-29
 */
@TableName("tb_logistics_country_area_partition")
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ExcelIgnoreUnannotated
public class TbLogisticsCountryAreaPartition implements Serializable{
 private static final long serialVersionUID = 1L;
 
    /** ID */
    @ApiModelProperty(value = "ID")
    @TableId (value = "ID", type = IdType.AUTO)
    @TableField("ID")
    private BigDecimal id ;
 
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
 
    /** 物流商编码 */
    @ApiModelProperty(value = "物流商编码")
    @TableField("lp_code")
    private String lpCode ;
 
    /** 国家编码 */
    @ApiModelProperty(value = "国家编码")
    @TableField("country_code")
    private String countryCode ;
 
    /** 收货地区名称+收货分区 */
    @ApiModelProperty(value = "收货地区名称+收货分区")
    @TableField("country_area_name")
    private String countryAreaName ;
 
    /** 分区号 */
    @ApiModelProperty(value = "分区号")
    @TableField("lsp_num")
    private String lspNum ;


}