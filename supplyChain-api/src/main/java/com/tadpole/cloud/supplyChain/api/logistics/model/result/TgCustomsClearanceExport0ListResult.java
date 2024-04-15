package com.tadpole.cloud.supplyChain.api.logistics.model.result;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.net.URL;

/**
 * @author: ty
 * @description: 生成清关发票导出-sheet0:清关发票明细
 * @date: 2023/7/4
 */
@Data
public class TgCustomsClearanceExport0ListResult implements Serializable {

    private static final long serialVersionUID = 1L;

    /** 箱号 */
    @ApiModelProperty("箱号")
    private String boxNo;

    @ApiModelProperty("清关品名")
    private String clearNameCn;

    @ApiModelProperty("英文品名")
    private String invoiceProNameEn;

    @ApiModelProperty("HSCode")
    private String hscode;

    @ApiModelProperty("总数量")
    private BigDecimal totalQuantity;

    @ApiModelProperty("单价")
    private BigDecimal unitPrice;

    @ApiModelProperty("单价")
    private String unitPriceStr;

    @ApiModelProperty("最低价")
    private BigDecimal unitPriceMin;

    @ApiModelProperty("平均价")
    private BigDecimal unitPriceAvg;

    @ApiModelProperty("清关单价")
    private BigDecimal customsUnitPrice;

    @ApiModelProperty("总金额")
    private BigDecimal totalAmount;

    @ApiModelProperty("总金额")
    private String totalAmountStr;

    @ApiModelProperty("毛重")
    private BigDecimal grossWeight;

    @ApiModelProperty("毛重")
    private String grossWeightStr;

    @ApiModelProperty("净重")
    private BigDecimal netWeight;

    @ApiModelProperty("净重")
    private String netWeightStr;

    @ApiModelProperty("实重")
    private BigDecimal weight;

    @ApiModelProperty("实重")
    private String weightStr;

    @ApiModelProperty("长")
    private BigDecimal goodsLong;

    @ApiModelProperty("宽")
    private BigDecimal wide;

    @ApiModelProperty("高")
    private BigDecimal high;

    @ApiModelProperty("总体积")
    private BigDecimal totalVolume;

    @ApiModelProperty("总体积")
    private String totalVolumeStr;

    @ApiModelProperty("实物图片")
    private String amazonPictureLink;

    @ApiModelProperty("实物图片Url")
    private URL amazonPictureUrl;

    @ApiModelProperty("带电")
    private String isCharged;

    @ApiModelProperty("带磁")
    private String isMagnet;

    @ApiModelProperty("材质")
    private String clearMaterialEn;

    @ApiModelProperty("用途")
    private String fitBrand;

    @ApiModelProperty("品牌")
    private String companyBrand;

    @ApiModelProperty("型号")
    private String style;

    @ApiModelProperty("销售链接")
    private String amazonSaleLink;

    @ApiModelProperty("是否向上合并单元格，1：是")
    private String isMergeUpCell;

    /** 序号 */
    @ApiModelProperty("序号")
    private String no;

    /** 单位 */
    @ApiModelProperty("单位")
    private String unit;

    /** 货箱件数 */
    @ApiModelProperty("货箱件数")
    private BigDecimal boxNum;

    @ApiModelProperty("流转税")
    private BigDecimal changeTaxRate;

    @ApiModelProperty("关税率")
    private BigDecimal taxRate;

    @ApiModelProperty("附加税")
    private BigDecimal addTaxRate;

    /** 单个品名毛重 */
    @ApiModelProperty("单个品名毛重")
    private BigDecimal avgWeight;

    /** 清关合并状态 */
    @ApiModelProperty("清关合并状态：未合并（人工未合并，系统未合并）已合并（人工已合并，系统已合并）")
    private String clearMergeStatus;

    /** 清关物料编码 */
    @ApiModelProperty("清关物料编码")
    private String mainMaterialCode;

    /** 清关明细ID */
    @ApiModelProperty("清关明细ID")
    private BigDecimal id;

    /** 清关合并ID（包含人工合并和系统合并，分别对应表的id） */
    @ApiModelProperty("清关合并ID")
    private BigDecimal mergeId;

    /** 出货清单号 */
    @ApiModelProperty("出货清单号")
    private String packCode;

    /** 维度分组 */
    @ApiModelProperty("维度分组")
    private String groupStr;

    @ApiModelProperty("数量")
    private BigDecimal quantity;

    @ApiModelProperty("清关币种")
    private String currency;

    @ApiModelProperty("税号")
    private String importTaxNum;

    @ApiModelProperty("SKU")
    private String sku;

    @ApiModelProperty("销售价")
    private BigDecimal amazonSalePrice;

    @ApiModelProperty("清关发票快递模板维度分组条数")
    private BigDecimal totalCount;

    @ApiModelProperty("清关发票快递模板维度分组总价")
    private BigDecimal totalPrice;

    @ApiModelProperty("汇率")
    private BigDecimal indirectRate;

}
