package com.tadpole.cloud.supplyChain.api.logistics.model.result;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author: ty
 * @description: 物流单对账导出sheet0
 * @date: 2023/11/29
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ExcelIgnoreUnannotated
public class LsLogisticsNoCheckExport1Result implements Serializable {

    private static final long serialVersionUID = 1L;

    @ExcelProperty(value ="发货日期")
    @ApiModelProperty("发货日期")
    private Date shipmentDate;

    @ExcelProperty(value ="出货仓名称")
    @ApiModelProperty("出货仓名称（多个用英文逗号分隔）")
    private String outWarehouseName;

    @ExcelProperty(value ="账号")
    @ApiModelProperty("账号")
    private String sysShopsName;

    @ExcelProperty(value ="站点")
    @ApiModelProperty("站点")
    private String sysSite;

    @ExcelProperty(value ="合约号")
    @ApiModelProperty("合约号（物流账号）")
    private String lcCode;

    @ApiModelProperty("物流商编码")
    private String lpCode;

    @ExcelProperty(value ="物流商名称")
    @ApiModelProperty("物流商名称")
    private String lpName;

    @ApiModelProperty("物流商简称")
    private String lpSimpleName;

    @ExcelProperty(value ="是否报关")
    @ApiModelProperty("是否报关：是，否")
    private String isCustoms;

    @ExcelProperty(value ="是否包税")
    @ApiModelProperty("是否包税：是，否")
    private String hasTax;

    @ExcelProperty(value ="货运方式1")
    @ApiModelProperty("货运方式1")
    private String freightCompany;

    @ExcelProperty(value ="运输方式")
    @ApiModelProperty("运输方式")
    private String transportType;

    @ExcelProperty(value ="物流渠道")
    @ApiModelProperty("物流渠道")
    private String logisticsChannel;

    @ExcelProperty(value ="红蓝单")
    @ApiModelProperty("红蓝单")
    private String orderType;

    @ExcelProperty(value ="发货批次号")
    @ApiModelProperty("发货批次号")
    private String shipmentNum;

    @ExcelProperty(value ="物流单号")
    @ApiModelProperty("物流单号")
    private String logisticsNo;

    @ExcelProperty(value ="发货件数")
    @ApiModelProperty("发货件数")
    private Long shipmentUnit;

    @ExcelProperty(value ="出仓重量（KG）")
    @ApiModelProperty("出仓重量（KG）")
    private BigDecimal weight;

    @ExcelProperty(value ="出仓体积（CBM）")
    @ApiModelProperty("出仓体积（CBM）")
    private BigDecimal volume;

    @ExcelProperty(value ="出仓体积重（KG）")
    @ApiModelProperty("出仓体积重（KG）")
    private BigDecimal volumeWeight;

    @ExcelProperty(value ="计费类型")
    @ApiModelProperty("计费类型")
    private String confirmFeeType;

    @ExcelProperty(value ="计费量")
    @ApiModelProperty("计费量")
    private BigDecimal confirmCountFee;

    @ExcelProperty(value ="单价")
    @ApiModelProperty("单价")
    private BigDecimal unitPrice;

    @ExcelProperty(value ="物流费")
    @ApiModelProperty("物流费")
    private BigDecimal logisticsFee;

    @ExcelProperty(value ="物流费币制")
    @ApiModelProperty("物流费币制")
    private String logisticsCurrency;

    @ExcelProperty(value ="DTP")
    @ApiModelProperty("DTP")
    private BigDecimal dtp;

    @ExcelProperty(value ="报关费")
    @ApiModelProperty("报关费")
    private BigDecimal customsFee;

    @ExcelProperty(value ="清关费")
    @ApiModelProperty("清关费")
    private BigDecimal clearCustomsFee;

    @ExcelProperty(value ="旺季附加费")
    @ApiModelProperty("旺季附加费")
    private BigDecimal busySeasonFee;

    @ExcelProperty(value ="燃油附加费")
    @ApiModelProperty("燃油附加费")
    private BigDecimal oilFee;

    @ExcelProperty(value ="燃油费率（%）")
    @ApiModelProperty("燃油费率（%）")
    private String oilFeePercent;

    @ExcelProperty(value ="杂费")
    @ApiModelProperty("杂费")
    private BigDecimal sundryFee;

    @ExcelProperty(value ="产品附加费")
    @ApiModelProperty("产品附加费（附加费及杂费）")
    private BigDecimal othersFee;

    /** 产品附加费（附加费及杂费）备注 */
    @ExcelProperty(value ="附加费及杂费备注")
    @ApiModelProperty("产品附加费（附加费及杂费）备注")
    private String othersFeeRemark;

    @ExcelProperty(value ="物流对账单号")
    @ApiModelProperty("物流对账单号")
    private String logisticsCheckOrder;

    @ExcelProperty(value ="流转税（VAT/TAX/205）")
    @ApiModelProperty("流转税（VAT/TAX/205）")
    private BigDecimal changeTax;

    @ExcelProperty(value ="DUTY/201")
    @ApiModelProperty("DUTY/201")
    private BigDecimal taxFee;

    @ExcelProperty(value ="税费币制")
    @ApiModelProperty("税费币制")
    private String taxCurrency;

    @ExcelProperty(value ="税号")
    @ApiModelProperty("税号")
    private String taxOrder;

    @ExcelProperty(value ="C88单号")
    @ApiModelProperty("C88单号")
    private String c88;

    @ExcelProperty(value ="C88备注")
    @ApiModelProperty("C88备注")
    private String c88Remark;

    @ExcelProperty(value ="VAT原币金额")
    @ApiModelProperty("VAT原币金额")
    private BigDecimal vat;

    @ExcelProperty(value ="税费发票号码")
    @ApiModelProperty("税费发票号码")
    private String taxInvoiceOrder;

    @ExcelProperty(value ="合计")
    @ApiModelProperty("合计（CNY）")
    private BigDecimal totalFee;

    @ExcelProperty(value ="物流费ERP申请日期")
    @ApiModelProperty("物流费ERP申请日期")
    private Date logisticsErpDate;

    @ExcelProperty(value ="物流费单据编号")
    @ApiModelProperty("物流费单据编号")
    private String logisticsBillOrder;

    @ExcelProperty(value ="税费ERP申请日期")
    @ApiModelProperty("税费ERP申请日期")
    private Date taxErpDate;

    @ExcelProperty(value ="税费单据编号")
    @ApiModelProperty("税费单据编号")
    private String taxBillOrder;

}
