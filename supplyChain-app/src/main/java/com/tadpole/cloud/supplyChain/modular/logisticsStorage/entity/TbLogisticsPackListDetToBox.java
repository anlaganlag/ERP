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
 * 亚马逊货件-明细-多少箱每箱装多少;
 * @author : LSY
 * @date : 2023-12-29
 */
@TableName("tb_logistics_pack_list_det_to_box")
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ExcelIgnoreUnannotated
public class TbLogisticsPackListDetToBox implements Serializable{
 private static final long serialVersionUID = 1L;
 
    /** 编号 */
    @ApiModelProperty(value = "编号")
    @TableId (value = "id", type = IdType.AUTO)
    @TableField("id")
    private BigDecimal id ;
 
    /** 明细编号 */
    @ApiModelProperty(value = "明细编号")
    @TableField("ship_det_id")
    private Integer shipDetId ;
 
    /** 箱号 */
    @ApiModelProperty(value = "箱号")
    @TableField("pack_det_box_num")
    private String packDetBoxNum ;
 
    /** 箱号上传名称 */
    @ApiModelProperty(value = "箱号上传名称")
    @TableField("pack_det_box_num_upload")
    private String packDetBoxNumUpload ;
 
    /** 数量 */
    @ApiModelProperty(value = "数量")
    @TableField("quantity")
    private Integer quantity ;
 
    /** ShipmentID */
    @ApiModelProperty(value = "ShipmentID")
    @TableField("shipment_id")
    private String shipmentId ;
 
    /** 系统标识字段新版导入文件 */
    @ApiModelProperty(value = "系统标识字段新版导入文件")
    @TableField("pack_list_code")
    private String packListCode ;


}