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
 * 物流账单;
 * @author : LSY
 * @date : 2023-12-29
 */
@TableName("tb_logistics_bill")
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ExcelIgnoreUnannotated
public class TbLogisticsBill implements Serializable{
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
 
    /** 费用到期日 */
    @ApiModelProperty(value = "费用到期日")
    @TableField("expense_due_date")
    private Date expenseDueDate ;
 
    /** 物流账号 */
    @ApiModelProperty(value = "物流账号")
    @TableField("lc_code")
    private String lcCode ;
 
    /** 物流商编码 */
    @ApiModelProperty(value = "物流商编码")
    @TableField("lp_code")
    private String lpCode ;
 
    /** 物流商简称 */
    @ApiModelProperty(value = "物流商简称")
    @TableField("lp_simple_name")
    private String lpSimpleName ;
 
    /** 总箱数 */
    @ApiModelProperty(value = "总箱数")
    @TableField("total_box_num")
    private BigDecimal totalBoxNum ;
 
    /** 总实重（输入重量） */
    @ApiModelProperty(value = "总实重（输入重量）")
    @TableField("total_real_weight")
    private BigDecimal totalRealWeight ;
 
    /** 总体积重 */
    @ApiModelProperty(value = "总体积重")
    @TableField("total_volume_weight")
    private BigDecimal totalVolumeWeight ;
 
    /** 总计费重 */
    @ApiModelProperty(value = "总计费重")
    @TableField("total_weight")
    private BigDecimal totalWeight ;
 
    /** 总数量 */
    @ApiModelProperty(value = "总数量")
    @TableField("total_quantity")
    private Integer totalQuantity ;
 
    /** 总金额原币 */
    @ApiModelProperty(value = "总金额原币")
    @TableField("total_amount_to_origin")
    private BigDecimal totalAmountToOrigin ;
 
    /** 总金额人民币 */
    @ApiModelProperty(value = "总金额人民币")
    @TableField("total_amount_to_rmb")
    private BigDecimal totalAmountToRmb ;
 
    /** 是否已请款 */
    @ApiModelProperty(value = "是否已请款")
    @TableField("has_please_pay")
    private Integer hasPleasePay ;
 
    /** 请款人 */
    @ApiModelProperty(value = "请款人")
    @TableField("please_payer")
    private String pleasePayer ;
 
    /** 请款日期 */
    @ApiModelProperty(value = "请款日期")
    @TableField("pleasr_pay_date")
    private Date pleasrPayDate ;
 
    /** 是否已审核 */
    @ApiModelProperty(value = "是否已审核")
    @TableField("has_check")
    private BigDecimal hasCheck ;
 
    /** 审核人 */
    @ApiModelProperty(value = "审核人")
    @TableField("checker")
    private String checker ;
 
    /** 审核日期 */
    @ApiModelProperty(value = "审核日期")
    @TableField("check_date")
    private Date checkDate ;
 
    /** 付款日期 */
    @ApiModelProperty(value = "付款日期")
    @TableField("pay_date")
    private Date payDate ;
 
    /** 是否已抵扣 */
    @ApiModelProperty(value = "是否已抵扣")
    @TableField("has_deduction")
    private Integer hasDeduction ;
 
    /** 添加日期 */
    @ApiModelProperty(value = "添加日期")
    @TableField("add_date")
    private Date addDate ;
 
    /** 操作人 */
    @ApiModelProperty(value = "操作人")
    @TableField("operator")
    private String operator ;
 
    /** 工号 */
    @ApiModelProperty(value = "工号")
    @TableField("operator_code")
    private String operatorCode ;


}