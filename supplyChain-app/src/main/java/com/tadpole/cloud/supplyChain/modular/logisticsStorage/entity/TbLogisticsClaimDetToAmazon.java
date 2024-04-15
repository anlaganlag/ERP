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
 * 亚马逊物流索赔明细;
 * @author : LSY
 * @date : 2023-12-29
 */
@TableName("tb_logistics_claim_det_to_amazon")
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ExcelIgnoreUnannotated
public class TbLogisticsClaimDetToAmazon implements Serializable{
 private static final long serialVersionUID = 1L;
 
    /** 索赔明细系统编号 */
    @ApiModelProperty(value = "索赔明细系统编号")
    @TableId (value = "pk_claim_det_id", type = IdType.AUTO)
    @TableField("pk_claim_det_id")
    private BigDecimal pkClaimDetId ;
 
    /** 索赔系统编号(yyyyMMddHHmmssffff) */
    @ApiModelProperty(value = "索赔系统编号(yyyyMMddHHmmssffff)")
    @TableField("pk_claim_id")
    private String pkClaimId ;
 
    /** 是否有POD */
    @ApiModelProperty(value = "是否有POD")
    @TableField("bus_is_pod")
    private String busIsPod ;
 
    /** 发货仓 */
    @ApiModelProperty(value = "发货仓")
    @TableField("bus_com_warehouse_name")
    private String busComWarehouseName ;
 
    /** 签收日期 */
    @ApiModelProperty(value = "签收日期")
    @TableField("bus_ler_sign_date")
    private Date busLerSignDate ;
 
    /** 物流单号 */
    @ApiModelProperty(value = "物流单号")
    @TableField("bus_lhr_odd_numb")
    private String busLhrOddNumb ;
 
    /** 物流状态 */
    @ApiModelProperty(value = "物流状态")
    @TableField("bus_lhr_state")
    private String busLhrState ;
 
    /** 货运方式 */
    @ApiModelProperty(value = "货运方式")
    @TableField("bus_log_tra_mode2")
    private String busLogTraMode2 ;
 
    /** SKU */
    @ApiModelProperty(value = "SKU")
    @TableField("bus_sku")
    private String busSku ;
 
    /** 发货数量 */
    @ApiModelProperty(value = "发货数量")
    @TableField("bus_send_qty")
    private Integer busSendQty ;
 
    /** 物流待发 */
    @ApiModelProperty(value = "物流待发")
    @TableField("bus_stay_deliver_qty")
    private Integer busStayDeliverQty ;
 
    /** 物流已发 */
    @ApiModelProperty(value = "物流已发")
    @TableField("bus_issued_qty")
    private Integer busIssuedQty ;
 
    /** 接受数量 */
    @ApiModelProperty(value = "接受数量")
    @TableField("bus_receive_qty")
    private Integer busReceiveQty ;
 
    /** 在途数量 */
    @ApiModelProperty(value = "在途数量")
    @TableField("bus_in_transit_qty")
    private Integer busInTransitQty ;
 
    /** 接收差异 */
    @ApiModelProperty(value = "接收差异")
    @TableField("bus_discrepancy")
    private Integer busDiscrepancy ;
 
    /** 备注 */
    @ApiModelProperty(value = "备注")
    @TableField("bus_remark")
    private String busRemark ;
 
    /** 货运方式1 */
    @ApiModelProperty(value = "货运方式1")
    @TableField("bus_log_tra_mode1")
    private String busLogTraMode1 ;


}