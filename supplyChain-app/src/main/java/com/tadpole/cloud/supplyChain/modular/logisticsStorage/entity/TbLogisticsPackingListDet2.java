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
 * 出货清单明细2-装箱内容-金蝶+海外仓;
 * @author : LSY
 * @date : 2023-12-29
 */
@TableName("tb_logistics_packing_list_det2")
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ExcelIgnoreUnannotated
public class TbLogisticsPackingListDet2 implements Serializable{
 private static final long serialVersionUID = 1L;
 
    /** 编号 */
    @ApiModelProperty(value = "编号")
    @TableId (value = "pack_det_id", type = IdType.AUTO)
    @TableField("pack_det_id")
    private BigDecimal packDetId ;
 
    /** 票单号 */
    @ApiModelProperty(value = "票单号")
    @TableField("pack_code")
    private String packCode ;
 
    /** SKU */
    @ApiModelProperty(value = "SKU")
    @TableField("sku")
    private String sku ;
 
    /** 数量 */
    @ApiModelProperty(value = "数量")
    @TableField("quantity")
    private Integer quantity ;
 
    /** 物料编码 */
    @ApiModelProperty(value = "物料编码")
    @TableField("mate_code")
    private String mateCode ;
 
    /** 箱号上传名称 */
    @ApiModelProperty(value = "箱号上传名称")
    @TableField("pack_det_box_num_upload")
    private String packDetBoxNumUpload ;
 
    /** 箱号 */
    @ApiModelProperty(value = "箱号")
    @TableField("pack_det_box_num")
    private Integer packDetBoxNum ;
 
    /** FnSKU */
    @ApiModelProperty(value = "FnSKU")
    @TableField("fn_sku")
    private String fnSku ;
 
    /** 箱条码 */
    @ApiModelProperty(value = "箱条码")
    @TableField("pack_det_box_code")
    private String packDetBoxCode ;
 
    /** 拣货单单号 */
    @ApiModelProperty(value = "拣货单单号")
    @TableField("pick_list_code")
    private String pickListCode ;
 
    /** 需求部门 */
    @ApiModelProperty(value = "需求部门")
    @TableField("dep_name")
    private String depName ;
 
    /** 需求Team */
    @ApiModelProperty(value = "需求Team")
    @TableField("team_name")
    private String teamName ;
 
    /** 需求人员 */
    @ApiModelProperty(value = "需求人员")
    @TableField("per_name")
    private String perName ;
 
    /** 建议发货方式 */
    @ApiModelProperty(value = "建议发货方式")
    @TableField("pack_sug_ship_method")
    private String packSugShipMethod ;
 
    /** 调拨申请单号 */
    @ApiModelProperty(value = "调拨申请单号")
    @TableField("pack_direct_code")
    private String packDirectCode ;


}