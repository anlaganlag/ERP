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
 * 物流账单费用明细;
 * @author : LSY
 * @date : 2023-12-29
 */
@TableName("tb_logistics_bill_charge_detial")
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ExcelIgnoreUnannotated
public class TbLogisticsBillChargeDetial implements Serializable{
 private static final long serialVersionUID = 1L;
 
    /** 系统编号 */
    @ApiModelProperty(value = "系统编号")
    @TableId (value = "id", type = IdType.AUTO)
    @TableField("id")
    private BigDecimal id ;
 
    /** 物流对账单号 */
    @ApiModelProperty(value = "物流对账单号")
    @TableField("bill_num")
    private String billNum ;
 
    /** 物流跟踪单号（头程物流单号） */
    @ApiModelProperty(value = "物流跟踪单号（头程物流单号）")
    @TableField("lhr_odd_num")
    private String lhrOddNum ;
 
    /** 费用类型 */
    @ApiModelProperty(value = "费用类型")
    @TableField("charge_type")
    private String chargeType ;
 
    /** 费用名称 */
    @ApiModelProperty(value = "费用名称")
    @TableField("charge_name")
    private String chargeName ;
 
    /** 金额原币 */
    @ApiModelProperty(value = "金额原币")
    @TableField("amount_to_origin")
    private BigDecimal amountToOrigin ;
 
    /** 金额人民币 */
    @ApiModelProperty(value = "金额人民币")
    @TableField("amount_to_rmb")
    private BigDecimal amountToRmb ;
 
    /** 币种 */
    @ApiModelProperty(value = "币种")
    @TableField("currency")
    private String currency ;
 
    /** 备注 */
    @ApiModelProperty(value = "备注")
    @TableField("remarks")
    private String remarks ;


}