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
 * 物流体积重量;
 * @author : LSY
 * @date : 2023-12-29
 */
@TableName("tb_logistics_volume_weight")
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ExcelIgnoreUnannotated
public class TbLogisticsVolumeWeight implements Serializable{
 private static final long serialVersionUID = 1L;
 
    /** lvwID */
    @ApiModelProperty(value = "lvwID")
    @TableId (value = "lvw_id", type = IdType.AUTO)
    @TableField("lvw_id")
    private BigDecimal lvwId ;
 
    /** sysAddDate */
    @ApiModelProperty(value = "sysAddDate")
    @TableField("sys_add_date")
    private Date sysAddDate ;
 
    /** sysPerName */
    @ApiModelProperty(value = "sysPerName")
    @TableField("sys_per_name")
    private String sysPerName ;
 
    /** sysUpdDatetime */
    @ApiModelProperty(value = "sysUpdDatetime")
    @TableField("sys_upd_datetime")
    private Date sysUpdDatetime ;
 
    /** 物流商编码 */
    @ApiModelProperty(value = "物流商编码")
    @TableField("lp_code")
    private String lpCode ;
 
    /** 货运方式2 */
    @ApiModelProperty(value = "货运方式2")
    @TableField("log_tra_mode2")
    private String logTraMode2 ;
 
    /** 箱型 */
    @ApiModelProperty(value = "箱型")
    @TableField("lvw_box_type")
    private String lvwBoxType ;
 
    /** 体积单位 */
    @ApiModelProperty(value = "体积单位")
    @TableField("lvw_unit")
    private String lvwUnit ;
 
    /** 体积重 */
    @ApiModelProperty(value = "体积重")
    @TableField("lvw_vaule")
    private BigDecimal lvwVaule ;


}