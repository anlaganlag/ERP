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
 * 亚马逊发货申请记录-明细项;
 * @author : LSY
 * @date : 2023-12-29
 */
@TableName("tb_logistics_shipment_det")
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ExcelIgnoreUnannotated
public class TbLogisticsShipmentDet implements Serializable{
 private static final long serialVersionUID = 1L;
 
    /** 编号 */
    @ApiModelProperty(value = "编号")
    @TableId (value = "ship_det_id", type = IdType.AUTO)
    @TableField("ship_det_id")
    private BigDecimal shipDetId ;
 
    /** 出货清单号 */
    @ApiModelProperty(value = "出货清单号")
    @TableField("pack_code")
    private String packCode ;
 
    /** PlanName */
    @ApiModelProperty(value = "PlanName")
    @TableField("plan_name")
    private String planName ;
 
    /** MerchantSKU */
    @ApiModelProperty(value = "MerchantSKU")
    @TableField("merchant_sku")
    private String merchantSku ;
 
    /** 数量 */
    @ApiModelProperty(value = "数量")
    @TableField("quantity")
    private Integer quantity ;


}