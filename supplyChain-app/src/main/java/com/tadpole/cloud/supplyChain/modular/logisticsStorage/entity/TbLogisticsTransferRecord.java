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
 * 物流调拨记录;
 * @author : LSY
 * @date : 2023-12-29
 */
@TableName("tb_logistics_transfer_record")
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ExcelIgnoreUnannotated
public class TbLogisticsTransferRecord implements Serializable{
 private static final long serialVersionUID = 1L;
 
    /** 系统编号 */
    @ApiModelProperty(value = "系统编号")
    @TableId (value = "sys_id", type = IdType.AUTO)
    @TableField("sys_id")
    private BigDecimal sysId ;
 
    /** 出货清单号 */
    @ApiModelProperty(value = "出货清单号")
    @TableField("pack_code")
    private String packCode ;
 
    /** 调拨方向 */
    @ApiModelProperty(value = "调拨方向")
    @TableField("transfers_direction")
    private String transfersDirection ;
 
    /** 调用类型 */
    @ApiModelProperty(value = "调用类型")
    @TableField("transfers_type")
    private String transfersType ;
 
    /** 调拨来源 */
    @ApiModelProperty(value = "调拨来源")
    @TableField("transfers_source")
    private String transfersSource ;
 
    /** 操作人 */
    @ApiModelProperty(value = "操作人")
    @TableField("transfers_operator")
    private String transfersOperator ;
 
    /** 操作人编码 */
    @ApiModelProperty(value = "操作人编码")
    @TableField("transfers_operator_code")
    private String transfersOperatorCode ;
 
    /** 调出组织编码 */
    @ApiModelProperty(value = "调出组织编码")
    @TableField("transfer_out_organize_code")
    private String transferOutOrganizeCode ;
 
    /** 调出组织名称 */
    @ApiModelProperty(value = "调出组织名称")
    @TableField("transfer_out_organize_name")
    private String transferOutOrganizeName ;
 
    /** 调入组织编码 */
    @ApiModelProperty(value = "调入组织编码")
    @TableField("transfer_in_organize_code")
    private String transferInOrganizeCode ;
 
    /** 调入组织名称 */
    @ApiModelProperty(value = "调入组织名称")
    @TableField("transfer_in_organize_name")
    private String transferInOrganizeName ;
 
    /** 调拨时间 */
    @ApiModelProperty(value = "调拨时间")
    @TableField("transfer_date")
    private Date transferDate ;
 
    /** 状态 */
    @ApiModelProperty(value = "状态")
    @TableField("status")
    private String status ;


}