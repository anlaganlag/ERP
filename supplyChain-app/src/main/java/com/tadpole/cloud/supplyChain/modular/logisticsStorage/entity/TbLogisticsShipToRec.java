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
 * 平台货件接收地址;
 * @author : LSY
 * @date : 2023-12-29
 */
@TableName("tb_logistics_ship_to_rec")
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ExcelIgnoreUnannotated
public class TbLogisticsShipToRec implements Serializable{
 private static final long serialVersionUID = 1L;
 
    /** 系统编号 */
    @ApiModelProperty(value = "系统编号")
    @TableId (value = "lfr_id", type = IdType.AUTO)
    @TableField("lfr_id")
    private BigDecimal lfrId ;
 
    /** 添加日期 */
    @ApiModelProperty(value = "添加日期")
    @TableField("sys_add_date")
    private Date sysAddDate ;
 
    /** 姓名 */
    @ApiModelProperty(value = "姓名")
    @TableField("sys_per_name")
    private String sysPerName ;
 
    /** 最后更新日期 */
    @ApiModelProperty(value = "最后更新日期")
    @TableField("sys_upd_datetime")
    private Date sysUpdDatetime ;
 
    /** 电商平台 */
    @ApiModelProperty(value = "电商平台")
    @TableField("ele_platform_name")
    private String elePlatformName ;
 
    /** 国家/地区 */
    @ApiModelProperty(value = "国家/地区")
    @TableField("country_code")
    private String countryCode ;
 
    /** 城市 */
    @ApiModelProperty(value = "城市")
    @TableField("city")
    private String city ;
 
    /** 州/省/郡 */
    @ApiModelProperty(value = "州/省/郡")
    @TableField("state")
    private String state ;
 
    /** 收货地址1 */
    @ApiModelProperty(value = "收货地址1")
    @TableField("log_rec_address1")
    private String logRecAddress1 ;
 
    /** 收货地址2 */
    @ApiModelProperty(value = "收货地址2")
    @TableField("log_rec_address2")
    private String logRecAddress2 ;
 
    /** 收货地址3 */
    @ApiModelProperty(value = "收货地址3")
    @TableField("log_rec_address3")
    private String logRecAddress3 ;
 
    /** 邮政编码 */
    @ApiModelProperty(value = "邮政编码")
    @TableField("log_rec_zip")
    private String logRecZip ;
 
    /** 收件人 */
    @ApiModelProperty(value = "收件人")
    @TableField("log_rec_person")
    private String logRecPerson ;
 
    /** 收件公司 */
    @ApiModelProperty(value = "收件公司")
    @TableField("log_rec_company")
    private String logRecCompany ;
 
    /** 收件电话 */
    @ApiModelProperty(value = "收件电话")
    @TableField("log_rec_person_tel")
    private String logRecPersonTel ;
 
    /** 收货仓名称 */
    @ApiModelProperty(value = "收货仓名称")
    @TableField("log_rec_house_name")
    private String logRecHouseName ;


}