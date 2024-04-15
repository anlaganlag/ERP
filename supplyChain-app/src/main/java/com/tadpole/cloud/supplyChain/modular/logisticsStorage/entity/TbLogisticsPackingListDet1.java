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
 * 出货清单明细1-箱子长宽高重-金蝶+海外仓;
 * @author : LSY
 * @date : 2023-12-29
 */
@TableName("tb_logistics_packing_list_det1")
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ExcelIgnoreUnannotated
public class TbLogisticsPackingListDet1 implements Serializable{
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
 
    /** 箱号 */
    @ApiModelProperty(value = "箱号")
    @TableField("pack_det_box_num")
    private Integer packDetBoxNum ;
 
    /** 箱号上传名称 */
    @ApiModelProperty(value = "箱号上传名称")
    @TableField("pack_det_box_num_upload")
    private String packDetBoxNumUpload ;
 
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
 
    /** 重量 */
    @ApiModelProperty(value = "重量")
    @TableField("pack_det_box_weight")
    private BigDecimal packDetBoxWeight ;
 
    /** packDetBoxCode;箱号 */
    @ApiModelProperty(value = "packDetBoxCode")
    @TableField("pack_det_box_code")
    private String packDetBoxCode ;
 
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
 
    /** 物流状态 */
    @ApiModelProperty(value = "物流状态")
    @TableField("pack_det_box_log_state")
    private String packDetBoxLogState ;
 
    /** 发货申请状态 */
    @ApiModelProperty(value = "发货申请状态")
    @TableField("pack_det_box_plan_state")
    private String packDetBoxPlanState ;
 
    /** 头程单号 */
    @ApiModelProperty(value = "头程单号")
    @TableField("pack_det_box_fir_log_no")
    private String packDetBoxFirLogNo ;
 
    /** 发货方式2 */
    @ApiModelProperty(value = "发货方式2")
    @TableField("log_tra_mode2")
    private String logTraMode2 ;
 
    /** 尾程单号 */
    @ApiModelProperty(value = "尾程单号")
    @TableField("pack_det_box_end_log_no")
    private String packDetBoxEndLogNo ;
 
    /** 账单状态 */
    @ApiModelProperty(value = "账单状态")
    @TableField("pack_det_box_bill_state")
    private String packDetBoxBillState ;
 
    /** 物流对账单号 */
    @ApiModelProperty(value = "物流对账单号")
    @TableField("pack_det_box_log_cost_no")
    private String packDetBoxLogCostNo ;
 
    /** 税费发票单号 */
    @ApiModelProperty(value = "税费发票单号")
    @TableField("pack_det_box_tax_cost_no")
    private String packDetBoxTaxCostNo ;
 
    /** 物流索赔状态 */
    @ApiModelProperty(value = "物流索赔状态")
    @TableField("pack_det_box_log_claim_state")
    private String packDetBoxLogClaimState ;
 
    /** ShipmentID */
    @ApiModelProperty(value = "ShipmentID")
    @TableField("shipment_id")
    private String shipmentId ;
 
    /** 货运方式1 */
    @ApiModelProperty(value = "货运方式1")
    @TableField("log_tra_mode1")
    private String logTraMode1 ;


}