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
 * 物流调拨记录明细;
 * @author : LSY
 * @date : 2023-12-29
 */
@TableName("tb_logistics_transfer_record_det")
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ExcelIgnoreUnannotated
public class TbLogisticsTransferRecordDet implements Serializable{
 private static final long serialVersionUID = 1L;
 
    /** 系统编号 */
    @ApiModelProperty(value = "系统编号")
    @TableId (value = "sys_det_id", type = IdType.AUTO)
    @TableField("sys_det_id")
    private BigDecimal sysDetId ;
 
    /** 上级系统编号 */
    @ApiModelProperty(value = "上级系统编号")
    @TableField("sys_id")
    private BigDecimal sysId ;
 
    /** 物料编码 */
    @ApiModelProperty(value = "物料编码")
    @TableField("mate_code")
    private String mateCode ;
 
    /** 箱条码 */
    @ApiModelProperty(value = "箱条码")
    @TableField("pack_det_box_code")
    private String packDetBoxCode ;
 
    /** 箱序号 */
    @ApiModelProperty(value = "箱序号")
    @TableField("pack_det_box_num")
    private String packDetBoxNum ;
 
    /** Sku */
    @ApiModelProperty(value = "Sku")
    @TableField("sku")
    private String sku ;
 
    /** FnSku */
    @ApiModelProperty(value = "FnSku")
    @TableField("fn_sku")
    private String fnSku ;
 
    /** 调出仓库编码 */
    @ApiModelProperty(value = "调出仓库编码")
    @TableField("transfer_out_house_code")
    private String transferOutHouseCode ;
 
    /** 调出仓库名称 */
    @ApiModelProperty(value = "调出仓库名称")
    @TableField("transfer_out_house_name")
    private String transferOutHouseName ;
 
    /** 调入仓库编码 */
    @ApiModelProperty(value = "调入仓库编码")
    @TableField("transfer_in_house_code")
    private String transferInHouseCode ;
 
    /** 调入仓库名称 */
    @ApiModelProperty(value = "调入仓库名称")
    @TableField("transfer_in_house_name")
    private String transferInHouseName ;
 
    /** 调拨数量 */
    @ApiModelProperty(value = "调拨数量")
    @TableField("transfer_quantity")
    private Integer transferQuantity ;


}