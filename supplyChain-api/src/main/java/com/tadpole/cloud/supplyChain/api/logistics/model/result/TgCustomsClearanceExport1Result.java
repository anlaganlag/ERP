package com.tadpole.cloud.supplyChain.api.logistics.model.result;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.alibaba.excel.annotation.write.style.HeadFontStyle;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.net.URL;
import java.util.List;

/**
 * @author: ty
 * @description: 生成清关发票导出-sheet1:清单
 * @date: 2023/6/28
 */
@Data
@ExcelIgnoreUnannotated
@HeadFontStyle(fontHeightInPoints = 11)
public class TgCustomsClearanceExport1Result implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("商品大类（清关品名）")
    private String clearNameCn;

    @ApiModelProperty("英文品名")
    @ColumnWidth(16)
    @ExcelProperty(value ="GoodsDesc", index = 1)
    private String invoiceProNameEn;

    @ApiModelProperty("英文材质")
    private String clearMaterialEn;

    @ApiModelProperty("单价")
    private BigDecimal unitPrice;

    @ApiModelProperty("单价")
    @ColumnWidth(14)
    @ExcelProperty(value ="Unit_Price", index = 5)
    private String unitPriceStr;

    @ApiModelProperty("总数量")
    @ColumnWidth(11)
    @ExcelProperty(value ="Unit", index = 4)
    private BigDecimal totalQuantity;

    @ApiModelProperty("总金额")
    private BigDecimal totalAmount;

    @ApiModelProperty("总金额")
    private String totalAmountStr;

    @ApiModelProperty("清关币种")
    private String currency;

    @ApiModelProperty("税号")
    private String importTaxNum;

    @ApiModelProperty("K3代码")
    private String k3Code;

    @ApiModelProperty("销售链接")
    private String amazonSaleLink;

    @ApiModelProperty("主图链接")
    private String amazonPictureLink;

    @ApiModelProperty("实物图片Url")
    private URL amazonPictureUrl;

    @ApiModelProperty("销售价")
    private BigDecimal amazonSalePrice;

    @ApiModelProperty("销售价")
    private String amazonSalePriceStr;

    @ApiModelProperty("清关单价")
    private BigDecimal clearanceUnitPrice;

    @ApiModelProperty("最低价")
    private BigDecimal unitPriceMin;

    @ApiModelProperty("平均价")
    private BigDecimal unitPriceAvg;

    @ApiModelProperty("清关单价")
    private String clearanceUnitPriceStr;

    @ApiModelProperty("清关总数量")
    private BigDecimal totalClearanceQuantity;

    @ApiModelProperty("清关总金额")
    private BigDecimal totalClearanceAmount;

    @ApiModelProperty("清关总金额")
    private String totalClearanceAmountStr;

    @ApiModelProperty("流转税")
    private BigDecimal changeTaxRate;

    @ApiModelProperty("流转税")
    private String changeTaxRateStr;

    @ApiModelProperty("关税")
    private BigDecimal taxRate;

    @ApiModelProperty("关税")
    private String taxRateStr;

    @ApiModelProperty("附加税")
    private BigDecimal addTaxRate;

    @ApiModelProperty("附加税")
    private String addTaxRateStr;

    @ApiModelProperty("分类条件")
    private String sortCondition;

    @ApiModelProperty("是否向上合并单元格，1：是")
    private String isMergeUpCell;

    /** 维度分组 */
    @ApiModelProperty("维度分组")
    private String groupStr;

    /** 清关明细ID */
    @ApiModelProperty("清关明细ID")
    private BigDecimal id;

    /** 清关合并明细ID */
    @ApiModelProperty("清关合并明细ID")
    private List<BigDecimal> detailIdList;

    @ApiModelProperty("清关发票快递模板维度分组条数")
    private BigDecimal totalCount;

    @ApiModelProperty("清关发票快递模板维度分组总价")
    private BigDecimal totalPrice;

    @ApiModelProperty("HSCode")
    @ColumnWidth(12)
    @ExcelProperty(value ="HSCode", index = 3)
    private String hscode;

    /** 清关物料编码 */
    @ApiModelProperty("清关物料编码")
    @ColumnWidth(14)
    @ExcelProperty(value ="PartNumber", index = 2)
    private String mainMaterialCode;

    /** 单位 */
    @ApiModelProperty("单位")
    @ColumnWidth(11)
    @ExcelProperty(value ="Measure", index = 6)
    private String unit;

    @ColumnWidth(18)
    @ExcelProperty(value ="OriginCountry", index = 7)
    private String originCountry;

    @ColumnWidth(18)
    @ExcelProperty(value ="Intl_Currency", index = 8)
    private String intlCurrency;

    @ColumnWidth(12)
    @ExcelProperty(value ="Currency", index = 9)
    private String defaultCurrency;

    @ColumnWidth(12)
    @ExcelProperty(value ="Freight", index = 10)
    private String freight;

    @ColumnWidth(18)
    @ExcelProperty(value ="CreateInvoice", index = 12)
    private String createInvoice;

    @ColumnWidth(25)
    @ExcelProperty(value ="Paperless_Invoice", index = 13)
    private String paperlessInvoice;

    @ColumnWidth(18)
    @ExcelProperty(value ="PrintCopy_PI", index = 14)
    private String printCopyPI;

    @ApiModelProperty("Link_key")
    @ColumnWidth(12)
    @ExcelProperty(value ="Link_key", index = 0)
    private String linkKey;

    @ApiModelProperty("AdditionComment")
    @ColumnWidth(21)
    @ExcelProperty(value ="AdditionComment", index = 11)
    private String additionComment;

}
