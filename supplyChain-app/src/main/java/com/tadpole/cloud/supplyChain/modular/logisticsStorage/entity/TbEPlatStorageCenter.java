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
 * 电商平台仓储中心;
 * @author : LSY
 * @date : 2024-1-2
 */
@TableName("Tb_E_Plat_Storage_Center")
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ExcelIgnoreUnannotated
public class TbEPlatStorageCenter implements Serializable{
 private static final long serialVersionUID = 1L;
 
    /** ID */
    @ApiModelProperty(value = "ID")
    @TableId (value = "ID", type = IdType.AUTO)
    @TableField("ID")
    private BigDecimal id ;
 
    /** 添加时间;添加时间 */
    @ApiModelProperty(value = "添加时间")
    @TableField("sys_add_date")
    private Date sysAddDate ;
 
    /** 操作人;操作人 */
    @ApiModelProperty(value = "操作人")
    @TableField("sys_per_name")
    private String sysPerName ;
 
    /** 更新时间;更新时间 */
    @ApiModelProperty(value = "更新时间")
    @TableField("sys_upd_datetime")
    private Date sysUpdDatetime ;
 
    /** 平台名称 */
    @ApiModelProperty(value = "平台名称")
    @TableField("ele_Platform_Name")
    private String elePlatformName ;
 
    /** 国家编码 */
    @ApiModelProperty(value = "国家编码")
    @TableField("country_Code")
    private String countryCode ;
 
    /** FBA编码 */
    @ApiModelProperty(value = "FBA编码")
    @TableField("FBA_Code")
    private String fbaCode ;
 
    /** 城市 */
    @ApiModelProperty(value = "城市")
    @TableField("city")
    private String city ;
 
    /** 状态 */
    @ApiModelProperty(value = "状态")
    @TableField("state")
    private String state ;
 
    /** 邮编 */
    @ApiModelProperty(value = "邮编")
    @TableField("zip")
    private String zip ;
 
    /** 地址 */
    @ApiModelProperty(value = "地址")
    @TableField("address")
    private String address ;


}