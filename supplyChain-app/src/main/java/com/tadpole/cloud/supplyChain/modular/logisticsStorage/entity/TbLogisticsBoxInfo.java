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
 * 物流箱信息-长宽高重;
 * @author : LSY
 * @date : 2023-12-29
 */
@TableName("tb_logistics_box_info")
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ExcelIgnoreUnannotated
public class TbLogisticsBoxInfo implements Serializable{
 private static final long serialVersionUID = 1L;
 
    /** 箱号 */
    @ApiModelProperty(value = "箱号")
    @TableId (value = "pack_det_box_num", type = IdType.AUTO)
    @TableField("pack_det_box_num")
    private BigDecimal packDetBoxNum ;
 
    /** 箱型 */
    @ApiModelProperty(value = "箱型")
    @TableField("pack_det_box_type")
    private String packDetBoxType ;
 
    /** 箱长 */
    @ApiModelProperty(value = "箱长")
    @TableField("pack_det_box_length")
    private BigDecimal packDetBoxLength ;
 
    /** 箱宽 */
    @ApiModelProperty(value = "箱宽")
    @TableField("pack_det_box_width")
    private BigDecimal packDetBoxWidth ;
 
    /** 箱高 */
    @ApiModelProperty(value = "箱高")
    @TableField("pack_det_box_height")
    private BigDecimal packDetBoxHeight ;
 
    /** 规格单位 */
    @ApiModelProperty(value = "规格单位")
    @TableField("pack_det_box_spe_unit")
    private String packDetBoxSpeUnit ;
 
    /** 体积单位 */
    @ApiModelProperty(value = "体积单位")
    @TableField("pack_det_box_volu_unit")
    private String packDetBoxVoluUnit ;
 
    /** 体积 */
    @ApiModelProperty(value = "体积")
    @TableField("pack_det_box_volume")
    private BigDecimal packDetBoxVolume ;
 
    /** 重量单位 */
    @ApiModelProperty(value = "重量单位")
    @TableField("pack_det_box_weig_unit")
    private String packDetBoxWeigUnit ;


}