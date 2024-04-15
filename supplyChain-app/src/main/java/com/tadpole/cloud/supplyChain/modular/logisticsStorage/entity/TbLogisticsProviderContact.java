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
 * 物流供应商联系信息;
 * @author : LSY
 * @date : 2023-12-29
 */
@TableName("tb_logistics_provider_contact")
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ExcelIgnoreUnannotated
public class TbLogisticsProviderContact implements Serializable{
 private static final long serialVersionUID = 1L;
 
    /** lpContactID */
    @ApiModelProperty(value = "lpContactID")
    @TableId (value = "lp_contact_id", type = IdType.AUTO)
    @TableField("lp_contact_id")
    private BigDecimal lpContactId ;
 
    /** 物流商编码 */
    @ApiModelProperty(value = "物流商编码")
    @TableField("lp_code")
    private String lpCode ;
 
    /** 联系人 */
    @ApiModelProperty(value = "联系人")
    @TableField("lp_contact_name")
    private String lpContactName ;
 
    /** 职务 */
    @ApiModelProperty(value = "职务")
    @TableField("lp_contact_duty")
    private String lpContactDuty ;
 
    /** 联系电话 */
    @ApiModelProperty(value = "联系电话")
    @TableField("lp_contact_tel")
    private String lpContactTel ;
 
    /** 电子邮箱 */
    @ApiModelProperty(value = "电子邮箱")
    @TableField("lp_contact_email")
    private String lpContactEmail ;
 
    /** 更新时间 */
    @ApiModelProperty(value = "更新时间")
    @TableField("sys_upd_datetime")
    private Date sysUpdDatetime ;

    /** 数据来源 */
    @ApiModelProperty(value = "数据来源")
    @TableField("data_source")
    private String dataSource ;
}