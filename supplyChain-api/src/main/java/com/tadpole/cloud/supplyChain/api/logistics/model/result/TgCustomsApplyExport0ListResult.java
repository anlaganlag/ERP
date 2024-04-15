package com.tadpole.cloud.supplyChain.api.logistics.model.result;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author: ty
 * @description: 生成国内报关单导出-sheet0:报关单
 * @date: 2023/7/10
 */
@Data
public class TgCustomsApplyExport0ListResult implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("项号")
    private String orderNo;

    @ApiModelProperty("商品编码")
    private String customsNum;

    @ApiModelProperty("商品名称")
    private String goodsName;

    @ApiModelProperty("商品名称")
    private String goodsNameTemp;

    @ApiModelProperty("（要素-要素中除去第一个品名外的其它值）+（空格）套装属性")
    private String goodsType;

    @ApiModelProperty("数量")
    private BigDecimal totalQuantity;

    @ApiModelProperty("数量")
    private String totalQuantityStr;

    @ApiModelProperty("单位")
    private String unit;

    @ApiModelProperty("单价")
    private BigDecimal unitPrice;

    @ApiModelProperty("单价")
    private String unitPriceStr;

    @ApiModelProperty("总价")
    private BigDecimal totalAmount;

    @ApiModelProperty("总价")
    private String totalAmountStr;

    @ApiModelProperty("币制")
    private String currency;

    @ApiModelProperty("币制（中文）")
    private String currencyCn;

    @ApiModelProperty("原产国（地区）")
    private String sourceCountry;

    @ApiModelProperty("最终目的国（地区）")
    private String arrivalCountryName;

    @ApiModelProperty("境内货源地")
    private String sourceAddr;

    @ApiModelProperty("免征性质")
    private String exemptionNature;

    @ApiModelProperty("净重")
    private BigDecimal netWeight;

    @ApiModelProperty("重量单位")
    private String weightUnitOrg;

    @ApiModelProperty("是否向上合并单元格，1：是")
    private String isMergeUpCell;

    @ApiModelProperty("开票品名")
    private String invoiceProNameCn;

    @ApiModelProperty("（要素")
    private String essentialFactor;

    @ApiModelProperty("套装属性")
    private String attribute;

    @ApiModelProperty("物料编码")
    private String materialCode;

    @ApiModelProperty("是否含税 0：否，1：是")
    private String includeTax;

    @ApiModelProperty("采购价")
    private BigDecimal k3Price;

    @ApiModelProperty("毛利率")
    private BigDecimal grossProfitRate;

    @ApiModelProperty("开票品名（物料编码维度的产品基本信息开票品名）")
    private String subInvoiceProNameCn;

    @ApiModelProperty("款式或尺寸")
    private String style;

    @ApiModelProperty("毛重（物料编码维度属性）")
    private BigDecimal grossWeightOrg;

    @ApiModelProperty("供应商")
    private String adviceSupplier;

    @ApiModelProperty("报关金额")
    private BigDecimal subTotalAmount;

    @ApiModelProperty("报关单价")
    private BigDecimal applyPrice;

    @ApiModelProperty("报关金额")
    private BigDecimal applyAmount;

    @ApiModelProperty("分组字符")
    private String groupStr;

}
