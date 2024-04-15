package com.tadpole.cloud.supplyChain.api.logistics.model.result;

import cn.stylefeng.guns.cloud.model.validator.BaseValidatingParam;
import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>
 * 物流费付款明细 出参类
 * </p>
 *
 * @author ty
 * @since 2023-12-01
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ExcelIgnoreUnannotated
@TableName("LS_LOGISTICS_FEE_PAYMENT_DETAIL")
public class LsLogisticsFeePaymentDetailResult implements Serializable, BaseValidatingParam {

    private static final long serialVersionUID = 1L;

    @TableId(value = "ID", type = IdType.AUTO)
    private BigDecimal id;

    @ApiModelProperty("物流费编号")
    private String logisticsFeeNo;

    @ApiModelProperty("发货批次号")
    private String shipmentNum;

    @ApiModelProperty("物流对账单号")
    private String logisticsCheckOrder;

    @ApiModelProperty("物流单号")
    private String logisticsNo;

    @ApiModelProperty("单价")
    private BigDecimal unitPrice;

    @ApiModelProperty("物流费")
    private BigDecimal logisticsFee;

    @ApiModelProperty("物流费币制")
    private String logisticsCurrency;

    @ApiModelProperty("报关费")
    private BigDecimal customsFee;

    @ApiModelProperty("清关费")
    private BigDecimal clearCustomsFee;

    @ApiModelProperty("燃油附加费")
    private BigDecimal oilFee;

    @ApiModelProperty("旺季附加费")
    private BigDecimal busySeasonFee;

    @ApiModelProperty("产品附加费（附加费及杂费）")
    private BigDecimal othersFee;

    @ApiModelProperty("燃油费率（%）")
    private BigDecimal oilFeePercent;

    @ApiModelProperty("杂费")
    private BigDecimal sundryFee;

    @ApiModelProperty("DTP")
    private BigDecimal dtp;

    @ApiModelProperty("流转税（VAT/TAX/205）")
    private BigDecimal changeTax;

    @ApiModelProperty("税费（DUTY/201）")
    private BigDecimal taxFee;

    @ApiModelProperty("税费币制")
    private String taxCurrency;

    @ApiModelProperty("税号")
    private String taxOrder;

    @ApiModelProperty("C88单号")
    private String c88;

    @ApiModelProperty("C88备注")
    private String c88Remark;

    @ApiModelProperty("VAT原币金额")
    private BigDecimal vat;

    @ApiModelProperty("税费发票号码")
    private String taxInvoiceOrder;

    @ApiModelProperty("合计（CNY）")
    private BigDecimal totalFee;

    @ApiModelProperty("序号")
    private Integer orderNum;

    @ApiModelProperty("创建时间")
    private Date createTime;

    @ApiModelProperty("创建人")
    private String createUser;

    @ApiModelProperty("更新时间")
    private Date updateTime;

    @ApiModelProperty("更新人")
    private String updateUser;

}
