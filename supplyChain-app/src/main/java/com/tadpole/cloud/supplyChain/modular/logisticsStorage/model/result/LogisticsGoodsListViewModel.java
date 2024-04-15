package com.tadpole.cloud.supplyChain.modular.logisticsStorage.model.result;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.io.Serializable;
import java.util.Date;


@ApiModel(value = "货物清单报表结果类",description = "")
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ExcelIgnoreUnannotated
public class LogisticsGoodsListViewModel implements Serializable {
    private static final long serialVersionUID = 1L;
    @ApiModelProperty(value = "部门")
    @ExcelProperty("需求部门")
    public String depName ;

    @ApiModelProperty(value = "teamName")
    @ExcelProperty("需求Team")
    public String teamName ;

    @ApiModelProperty(value = "发货日期")
    @ExcelProperty("发货日期")
    public Date lhrSendGoodDate ;

    @ApiModelProperty(value = "账号站点")
    @ExcelProperty("账号站点")
    public String countryCode ;

    @ApiModelProperty(value = "出货清单号")
    @ExcelProperty("单据编号")
    public String packCode ;

    @ApiModelProperty(value = "物流商简称")
    @ExcelProperty("物流商简称")
    public String lpSimpleName ;

    @ApiModelProperty(value = "物流商")
    @ExcelProperty("物流商")
    public String lpName ;

    @ApiModelProperty(value = "出货仓")
    @ExcelProperty("发货仓")
    public String comWarehouseName ;

    @ApiModelProperty(value = "货运方式1")
    @ExcelProperty("货运方式1")
    public String logTraMode1 ;

    @ApiModelProperty(value = "货运方式2")
    @ExcelProperty("运输方式")
    public String logTraMode2 ;

    @ApiModelProperty(value = "备注")
    @ExcelProperty("备注")
    public String lhrNote ;

    @ApiModelProperty(value = "shipmentID")
    @ExcelProperty("FBA号")
    public String shipmentID ;

    @ApiModelProperty(value = "收货仓")
    @ExcelProperty("收货仓")
    public String packStoreHouseName ;

    @ApiModelProperty(value = "头程物流单号")
    @ExcelProperty("单号")
    public String lhrOddNumb ;


    @ApiModelProperty(value = "运营大类")
    @ExcelProperty("运营大类")
    public String matOperateCate ;

    @ApiModelProperty(value = "公司品牌")
    @ExcelProperty("公司品牌")
    public String matComBrand ;

    @ApiModelProperty(value = "产品名称")
    @ExcelProperty("产品名称")
    public String matName ;

    @ApiModelProperty(value = "品牌")
    @ExcelProperty("适用品牌/对象")
    public String matBrand ;

    @ApiModelProperty(value = "型号")
    @ExcelProperty("适用机型/机型")
    public String matModel ;

    @ApiModelProperty(value = "款式")
    @ExcelProperty("款式/尺码")
    public String matStyle ;

    @ApiModelProperty(value = "颜色")
    @ExcelProperty("颜色")
    public String matColor ;

    @ApiModelProperty(value = "SKU")
    @ExcelProperty("SKU")
    public String sku ;

    @ApiModelProperty(value = "数量")
    @ExcelProperty("数量")
    public int quantity ;

    @ApiModelProperty(value = "物料编码")
    @ExcelProperty("物料编码")
    public String matCode ;

    @ApiModelProperty(value = "FNSKU")
    @ExcelProperty("FNSKU")
    public String fnSKU ;

    @ApiModelProperty(value = "调拨申请单号")
    @ExcelProperty("调拨申请单号")
    public String transgerApplyBillNo ;

    @ApiModelProperty(value = "仓位")
    @ExcelProperty("仓储位置")
    public String lhrPosition ;

    @ApiModelProperty(value = "签收日期")
    @ExcelProperty("签收日期")
    public Date lerSignDate ;



}
