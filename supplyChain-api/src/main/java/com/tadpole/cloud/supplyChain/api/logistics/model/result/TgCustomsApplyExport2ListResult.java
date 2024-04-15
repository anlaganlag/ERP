package com.tadpole.cloud.supplyChain.api.logistics.model.result;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author: ty
 * @description: 生成国内报关单导出-sheet2:装箱单
 * @date: 2023/7/10
 */
@Data
public class TgCustomsApplyExport2ListResult implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("箱号")
    private String boxNo;

    @ApiModelProperty("商品名称")
    private String goodsName;

    @ApiModelProperty("（要素")
    private String essentialFactor;

    @ApiModelProperty("套装属性")
    private String attribute;

    @ApiModelProperty("总数（件）")
    private BigDecimal totalBoxQuantity;

    @ApiModelProperty("总净重")
    private BigDecimal totalNetWeight;

    @ApiModelProperty("总净重")
    private String totalNetWeightStr;

    @ApiModelProperty("总毛重")
    private BigDecimal totalGrossWeight;

    @ApiModelProperty("总毛重")
    private String totalGrossWeightStr;

    @ApiModelProperty("数量")
    private BigDecimal totalQuantity;

    @ApiModelProperty("是否向上合并单元格，1：是")
    private String isMergeUpCell;

}
