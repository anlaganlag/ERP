package com.tadpole.cloud.supplyChain.api.logistics.model.result;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author: ty
 * @description: 物流实际结算明细结果类
 * @date: 2022/11/14
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ExcelIgnoreUnannotated
public class LogisticsSettlementDetailResult implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "ID")
    @TableId(value = "ID", type = IdType.AUTO)
    private BigDecimal id;

    @ApiModelProperty(value = "发货批次号")
    private String shipmentNum;

    @ApiModelProperty(value = "物流计费重")
    private BigDecimal logisticsCountFee;

    @ApiModelProperty(value = "单价")
    private BigDecimal unitPrice;

    @ApiModelProperty(value = "物流费")
    private BigDecimal logisticsFee;

    @ApiModelProperty(value = "物流费币制")
    private String logisticsCurrency;

    @ApiModelProperty(value = "DTP（CNY）")
    private BigDecimal dtp;

    @ApiModelProperty(value = "报关费（CNY）")
    private BigDecimal tariffFee;

    @ApiModelProperty(value = "清关费（CNY）")
    private BigDecimal clearTariffFee;

    @ApiModelProperty(value = "旺季附加费（CNY）")
    private BigDecimal busySeasonFee;

    @ApiModelProperty(value = "燃油附加费（CNY）")
    private BigDecimal oilFee;

    @ApiModelProperty(value = "燃油费率（%）")
    private String oilFeePercent;

    @ApiModelProperty(value = "杂费（CNY）")
    private BigDecimal othersFee;

    @ApiModelProperty(value = "产品附加费（CNY）")
    private BigDecimal productFee;

    @ApiModelProperty(value = "物流对账单号")
    private String logisticsSettlementOrder;

    @ApiModelProperty(value = "流转税（VAT/TAX/205）")
    private BigDecimal changeTax;

    @ApiModelProperty(value = "DUTY/201")
    private BigDecimal duty;

    @ApiModelProperty(value = "税费币制")
    private String taxCurrency;

    @ApiModelProperty(value = "税号")
    private String taxOrder;

    @ApiModelProperty(value = "C88单号")
    private String c88;

    @ApiModelProperty(value = "C88备注")
    private String c88Remark;

    @ApiModelProperty(value = "VAT原币金额")
    private BigDecimal vat;

    @ApiModelProperty(value = "税费发票号码")
    private String taxInvoiceOrder;

    @ApiModelProperty(value = "合计（CNY）")
    private BigDecimal totalFee;

    @ApiModelProperty(value = "物流费ERP申请日期")
    private String logisticsErpDate;

    @ApiModelProperty(value = "物流费单据编号")
    private String logisticsBillOrder;

    @ApiModelProperty(value = "税费ERP申请日期")
    private String taxErpDate;

    @ApiModelProperty(value = "税费单据编号")
    private String taxBillOrder;

    @ApiModelProperty(value = "备注")
    private String remark;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "创建人")
    private String createUser;

    @ApiModelProperty(value = "更新时间")
    private Date updateTime;

    @ApiModelProperty(value = "更新人")
    private String updateUser;

    @ApiModelProperty(value = "序号")
    private Integer seqNo;

    @ApiModelProperty(value = "数据月份")
    private String dataMonths;

    @ApiModelProperty(value = "数据状态 0：删除，1：正常")
    private Integer dataStatus;
}
