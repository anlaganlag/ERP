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
 * 物流账号;
 * @author : LSY
 * @date : 2023-12-29
 */
@TableName("tb_logistics_account")
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ExcelIgnoreUnannotated
public class TbLogisticsAccount implements Serializable{
 private static final long serialVersionUID = 1L;
 
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
 
    /** 物流账号;物流账号 */
    @ApiModelProperty(value = "物流账号")
    @TableId (value = "lc_code", type = IdType.ASSIGN_ID)
    @TableField("lc_code")
    private String lcCode ;
 
    /** 物流商编码;物流商编码 */
    @ApiModelProperty(value = "物流商编码")
    @TableField("lp_code")
    private String lpCode ;
 
    /** 公司名称;在物流供应商哪里开户的公司 */
    @ApiModelProperty(value = "公司名称")
    @TableField("com_name_cn")
    private String comNameCn ;
 
    /** 状态;禁用，正常 */
    @ApiModelProperty(value = "状态")
    @TableField("lc_state")
    private String lcState ;


}