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
public class LsLogisticsNoCheckExport0Result implements Serializable {

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

    @ExcelProperty(value ="ShipmentID")
    @ApiModelProperty("ShipmentID")
    private String shipmentId;

    @ExcelProperty(value ="发货数量")
    @ApiModelProperty("发货数量")
    private Long shipmentQuantity;

    @ExcelProperty(value ="预估单价（CNY）")
    @ApiModelProperty("预估单价（CNY）")
    private BigDecimal predictUnitPrice;

    @ExcelProperty(value ="预估物流费（CNY）")
    @ApiModelProperty("预估物流费（CNY）")
    private BigDecimal predictLogisticsFee;

    @ExcelProperty(value ="预估燃油附加费（CNY）")
    @ApiModelProperty("预估燃油附加费（CNY）")
    private BigDecimal predictOilFee;

    @ExcelProperty(value ="预估旺季附加费（CNY）")
    @ApiModelProperty("预估旺季附加费（CNY）")
    private BigDecimal predictBusySeasonFee;

    @ExcelProperty(value ="预估附加费及杂费（CNY）")
    @ApiModelProperty("预估附加费及杂费（CNY）")
    private BigDecimal predictOthersFee;

    @ExcelProperty(value ="预估报关费（CNY）")
    @ApiModelProperty("预估报关费（CNY）")
    private BigDecimal predictCustomsFee;

    @ExcelProperty(value ="预估清关费（CNY）")
    @ApiModelProperty("预估清关费（CNY）")
    private BigDecimal predictClearCustomsFee;

    @ExcelProperty(value ="预估税费（CNY）")
    @ApiModelProperty("预估税费（CNY）")
    private BigDecimal predictTaxFee;

    @ExcelProperty(value ="预估总费用（CNY）")
    @ApiModelProperty("预估总费用（CNY）")
    private BigDecimal predictTotalFee;

    @ExcelProperty(value ="总费用差异（CNY）")
    @ApiModelProperty("总费用差异（CNY）")
    private BigDecimal diffTotalFee;

    @ExcelProperty(value ="备注")
    @ApiModelProperty("备注")
    private String remark;

    @ExcelProperty(value ="签收日期")
    @ApiModelProperty("签收日期")
    private Date signDate;

    @ExcelProperty(value ="支付次数")
    @ApiModelProperty("支付次数")
    private Long paymentCount;

}
