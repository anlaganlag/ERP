package com.tadpole.cloud.supplyChain.api.logistics.model.result;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author: ty
 * @description: 生成国内报关单导出-sheet4:清单
 * @date: 2023/7/6
 */
@Data
public class TgCustomsApplyExport4ListResult implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("商品名称")
    private String goodsName;

    @ApiModelProperty("币制")
    private String currency;

    @ApiModelProperty("币制（中文）")
    private String currencyCn;

    @ApiModelProperty("总数量")
    private BigDecimal totalQuantity;

    @ApiModelProperty("总重量")
    private BigDecimal totalWeight;

    @ApiModelProperty("总重量")
    private String totalWeightStr;

    @ApiModelProperty("指运港")
    private String directPort;

    @ApiModelProperty("单价")
    private BigDecimal unitPrice;

    @ApiModelProperty("单价")
    private String unitPriceStr;

    @ApiModelProperty("报关销售额")
    private BigDecimal totalSales;

    @ApiModelProperty("报关销售额")
    private String totalSalesStr;

    @ApiModelProperty("开票品名")
    private String invoiceProNameCn;

    @ApiModelProperty("款式或尺寸")
    private String style;

    @ApiModelProperty("数量")
    private BigDecimal quantity;

    @ApiModelProperty("重量")
    private BigDecimal weight;

    @ApiModelProperty("重量")
    private String weightStr;

    @ApiModelProperty("K3单价")
    private BigDecimal k3Price;

    @ApiModelProperty("K3单价")
    private String k3PriceStr;

    @ApiModelProperty("报关单价")
    private BigDecimal applyPrice;

    @ApiModelProperty("报关单价")
    private String applyPriceStr;

    @ApiModelProperty("报关金额")
    private BigDecimal applyAmount;

    @ApiModelProperty("报关金额")
    private String applyAmountStr;

    @ApiModelProperty("供应商")
    private String adviceSupplier;

    @ApiModelProperty("K3代码")
    private String k3Code;

    @ApiModelProperty("物料编码")
    private String materialCode;

    @ApiModelProperty("要素")
    private String essentialFactor;

    @ApiModelProperty("套装属性")
    private String attribute;

    @ApiModelProperty("是否向上合并单元格，1：是")
    private String isMergeUpCell;

    @ApiModelProperty("分组字符")
    private String groupStr;

    @ApiModelProperty("毛重（维度属性）")
    private BigDecimal sumGrossWeightOrg;

}
