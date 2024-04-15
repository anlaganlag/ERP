package com.tadpole.cloud.supplyChain.api.logistics.model.result;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author: ty
 * @description: 生成国内报关单导出-sheet3:发票
 * @date: 2023/7/7
 */
@Data
public class TgCustomsApplyExport3ListResult implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("标记号码")
    private String markNo;

    @ApiModelProperty("商品名称")
    private String goodsName;

    @ApiModelProperty("占用空格")
    private String goodsNameTemp;

    @ApiModelProperty("要素")
    private String essentialFactor;

    @ApiModelProperty("套装属性")
    private String attribute;

    @ApiModelProperty("数量")
    private BigDecimal totalQuantity;

    @ApiModelProperty("单位")
    private String unit;

    @ApiModelProperty("单价")
    private BigDecimal unitPrice;

    @ApiModelProperty("单价")
    private String unitPriceStr;

    @ApiModelProperty("币制")
    private String currency;

    @ApiModelProperty("币制（中文）")
    private String currencyCn;

    @ApiModelProperty("总金额")
    private BigDecimal totalAmount;

    @ApiModelProperty("总金额")
    private String totalAmountStr;

    @ApiModelProperty("是否向上合并单元格，1：是")
    private String isMergeUpCell;

    @ApiModelProperty("分组字符")
    private String groupStr;




}
