package com.tadpole.cloud.supplyChain.api.logistics.model.params;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author: ty
 * @description: 物流实际结算明细Excel导入入参类
 * @date: 2022/11/17
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ExcelIgnoreUnannotated
public class LogisticsSettlementDetailImportParam implements Serializable {

    private static final long serialVersionUID = 1L;

    @ExcelProperty(value ="发货日期")
    @ApiModelProperty(value = "发货日期")
    private String shipmentDate;

    /*@ExcelProperty(value ="亚马逊账号")
    @ApiModelProperty(value = "账号")
    private String shopsName;

    @ExcelProperty(value ="亚马逊站点")
    @ApiModelProperty(value = "站点")
    private String site;

    @ExcelProperty(value ="合约号")
    @ApiModelProperty(value = "合约号")
    private String contractNo;

    @ExcelProperty(value ="是/否报关")
    @ApiModelProperty(value = "是否报关")
    private String isCustoms;

    @ExcelProperty(value ="是/否包税")
    @ApiModelProperty(value = "是否包税")
    private String hasTax;

    @ExcelProperty(value ="货运方式1")
    @ApiModelProperty(value = "货运方式1")
    private String freightCompany;

    @ExcelProperty(value ="运输方式")
    @ApiModelProperty(value = "运输方式")
    private String transportType;

    @ExcelProperty(value ="物流渠道")
    @ApiModelProperty(value = "物流渠道")
    private String logisticsChannel;

    @ExcelProperty(value ="红/蓝单")
    @ApiModelProperty(value = "物流单类型：红单/蓝单")
    private String orderType;*/

    @ExcelProperty(value ="发货批次号")
    @ApiModelProperty(value = "发货批次号")
    private String shipmentNum;

    /*@ExcelProperty(value ="物流单号")
    @ApiModelProperty(value = "物流单号")
    private String logisticsOrder;

    @ExcelProperty(value ="发货件数")
    @ApiModelProperty(value = "发货件数")
    private Long shipmentUnit;

    @ExcelProperty(value ="确认计费类型")
    @ApiModelProperty(value = "确认计费类型：重量/体积")
    private String confirmFeeType;

    @ExcelProperty(value ="预估使用的计费量")
    @ApiModelProperty(value = "预估使用的计费量")
    private BigDecimal predictCountFee;*/

    @ExcelProperty(value ="物流计费重")
    @ApiModelProperty(value = "物流计费重")
    private String logisticsCountFeeStr;

    @ApiModelProperty(value = "物流计费重")
    private BigDecimal logisticsCountFee;

    /*@ExcelProperty(value ="差异")
    @ApiModelProperty(value = "差异")
    private BigDecimal diffCountFee;

    @ExcelProperty(value ="ShipmentID")
    @ApiModelProperty(value = "ShipmentID（多个用都好隔开）")
    private String shipmentId;

    @ExcelProperty(value ="发货数量")
    @ApiModelProperty(value = "发货数量")
    private Long shipmentQuantity;*/

    @ExcelProperty(value ="单价")
    @ApiModelProperty(value = "单价")
    private String unitPriceStr;

    @ApiModelProperty(value = "单价")
    private BigDecimal unitPrice;

    @ExcelProperty(value ="物流费")
    @ApiModelProperty(value = "物流费")
    private String logisticsFeeStr;

    @ApiModelProperty(value = "物流费")
    private BigDecimal logisticsFee;

    @ExcelProperty(value ="物流费币制")
    @ApiModelProperty(value = "物流费币制")
    private String logisticsCurrency;

    @ExcelProperty(value ="DTP")
    @ApiModelProperty(value = "DTP")
    private String dtpStr;

    @ApiModelProperty(value = "DTP")
    private BigDecimal dtp;

    @ExcelProperty(value ="报关费")
    @ApiModelProperty(value = "报关费")
    private String tariffFeeStr;

    @ApiModelProperty(value = "报关费")
    private BigDecimal tariffFee;

    @ExcelProperty(value ="清关费")
    @ApiModelProperty(value = "清关费")
    private String clearTariffFeeStr;

    @ApiModelProperty(value = "清关费")
    private BigDecimal clearTariffFee;

    @ExcelProperty(value ="旺季附加费")
    @ApiModelProperty(value = "旺季附加费")
    private String busySeasonFeeStr;

    @ApiModelProperty(value = "旺季附加费")
    private BigDecimal busySeasonFee;

    @ExcelProperty(value ="燃油附加费")
    @ApiModelProperty(value = "燃油附加费")
    private String oilFeeStr;

    @ApiModelProperty(value = "燃油附加费")
    private BigDecimal oilFee;

    @ExcelProperty(value ="燃油费率")
    @ApiModelProperty(value = "燃油费率（%）")
    private String oilFeePercent;

    @ExcelProperty(value ="杂费")
    @ApiModelProperty(value = "杂费")
    private String othersFeeStr;

    @ApiModelProperty(value = "杂费")
    private BigDecimal othersFee;

    @ExcelProperty(value ="产品附加费")
    @ApiModelProperty(value = "产品附加费")
    private String productFeeStr;

    @ApiModelProperty(value = "产品附加费")
    private BigDecimal productFee;

    @ExcelProperty(value ="物流对账单号")
    @ApiModelProperty(value = "物流对账单号")
    private String logisticsSettlementOrder;

    @ExcelProperty(value ="流转税（VAT/TAX）")
    @ApiModelProperty(value = "流转税（VAT/TAX/205）")
    private String changeTaxStr;

    @ApiModelProperty(value = "流转税（VAT/TAX/205）")
    private BigDecimal changeTax;

    @ExcelProperty(value ="DUTY")
    @ApiModelProperty(value = "DUTY/201")
    private String dutyStr;

    @ApiModelProperty(value = "DUTY/201")
    private BigDecimal duty;

    @ExcelProperty(value ="税费币制")
    @ApiModelProperty(value = "税费币制")
    private String taxCurrency;

    @ExcelProperty(value ="税号")
    @ApiModelProperty(value = "税号")
    private String taxOrder;

    @ExcelProperty(value ="C88单号")
    @ApiModelProperty(value = "C88单号")
    private String c88;

    @ExcelProperty(value ="C88备注")
    @ApiModelProperty(value = "C88备注")
    private String c88Remark;

    @ExcelProperty(value ="VAT原币金额")
    @ApiModelProperty(value = "VAT原币金额")
    private String vatStr;

    @ApiModelProperty(value = "VAT原币金额")
    private BigDecimal vat;

    @ExcelProperty(value ="税费发票号码")
    @ApiModelProperty(value = "税费发票号码")
    private String taxInvoiceOrder;

    @ApiModelProperty(value = "合计（CNY）")
    private BigDecimal totalFee;

    @ExcelProperty(value ="物流费ERP申请日期")
    @ApiModelProperty(value = "物流费ERP申请日期")
    private String logisticsErpDate;

    @ExcelProperty(value ="物流费单据编号")
    @ApiModelProperty(value = "物流费单据编号")
    private String logisticsBillOrder;

    @ExcelProperty(value ="税费ERP申请日期")
    @ApiModelProperty(value = "税费ERP申请日期")
    private String taxErpDate;

    @ExcelProperty(value ="税费单据编号")
    @ApiModelProperty(value = "税费单据编号")
    private String taxBillOrder;

    @ExcelProperty(value ="备注")
    @ApiModelProperty(value = "备注")
    private String remark;

    @ApiModelProperty(value = "序号")
    private Integer seqNo;

    @ApiModelProperty(value = "数据月份")
    private String dataMonths;

    @ApiModelProperty(value = "数据状态 0：删除，1：正常")
    private Integer dataStatus;
}
