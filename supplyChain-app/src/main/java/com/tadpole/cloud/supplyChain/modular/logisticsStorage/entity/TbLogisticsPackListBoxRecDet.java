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
 * 出货清单和亚马逊货件关系映射-明细;
 * @author : LSY
 * @date : 2023-12-29
 */
@TableName("tb_logistics_pack_list_box_rec_det")
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ExcelIgnoreUnannotated
public class TbLogisticsPackListBoxRecDet implements Serializable{
 private static final long serialVersionUID = 1L;
 
    /** SysDetID */
    @ApiModelProperty(value = "SysDetID")
    @TableId (value = "sys_det_id", type = IdType.AUTO)
    @TableField("sys_det_id")
    private BigDecimal sysDetId ;
 
    /** SysID */
    @ApiModelProperty(value = "SysID")
    @TableField("sys_id")
    private BigDecimal sysId ;
 
    /** ShipmentID */
    @ApiModelProperty(value = "ShipmentID")
    @TableField("shipment_id")
    private String shipmentId ;
 
    /** SKU */
    @ApiModelProperty(value = "SKU")
    @TableField("merchant_sku")
    private String merchantSku ;
 
    /** 上传箱号 */
    @ApiModelProperty(value = "上传箱号")
    @TableField("pack_det_box_num_upload")
    private String packDetBoxNumUpload ;
 
    /** 原上传箱号 */
    @ApiModelProperty(value = "原上传箱号")
    @TableField("origin_pack_det_box_num_upload")
    private String originPackDetBoxNumUpload ;
 
    /** 数量 */
    @ApiModelProperty(value = "数量")
    @TableField("quantity")
    private Integer quantity ;
 
    /** 出货清单号 */
    @ApiModelProperty(value = "出货清单号")
    @TableField("pack_code")
    private String packCode ;
 
    /** 箱条码 */
    @ApiModelProperty(value = "箱条码")
    @TableField("pack_det_box_code")
    private String packDetBoxCode ;
 
    /** 箱号 */
    @ApiModelProperty(value = "箱号")
    @TableField("pack_det_box_num")
    private Integer packDetBoxNum ;
 
    /** 仓库名称;OwName */
    @ApiModelProperty(value = "仓库名称")
    @TableField("ow_name")
    private String owName ;
 
    /** 货件实际状态 */
    @ApiModelProperty(value = "货件实际状态")
    @TableField("shipment_real_status")
    private String shipmentRealStatus ;
 
    /** 系统标识字段新版导入文件 */
    @ApiModelProperty(value = "系统标识字段新版导入文件")
    @TableField("pack_list_code")
    private String packListCode ;


}