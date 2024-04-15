package com.tadpole.cloud.supplyChain.modular.logisticsStorage.model.result;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.io.Serializable;


@ApiModel(value = "通过数据返回结果",description = "")
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ExcelIgnoreUnannotated
public class TbShipemtListClearancModel implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "sku")
    @ExcelProperty(value ="SKU")
    public String sku;

    @ApiModelProperty(value = "matModel")
    @ExcelProperty(value ="适用机型或机型")
    public String matModel;

    @ApiModelProperty(value = "matInvoiceNorm")
    @ExcelProperty(value ="款式或尺寸")
    public String matInvoiceNorm;


    @ApiModelProperty(value = "qty")
    @ExcelProperty(value ="数量")
    public String qty;

    @ApiModelProperty(value = "matQtyUnit")
    @ExcelProperty(value ="数量单位")
    public String matQtyUnit;

    @ApiModelProperty(value = "matSetAttributes")
    @ExcelProperty(value ="套装属性")
    public String matSetAttributes;

    @ApiModelProperty(value = "matSetAttributes")
    @ExcelProperty(value ="币制")
    public String currency="USD";


    @ApiModelProperty(value = "packDetBoxNum")
    @ExcelProperty(value ="箱号")
    public String packDetBoxNum;

    @ApiModelProperty(value = "packDetBoxNum")
    @ExcelProperty(value ="分单号")
    public String spiltOrderNO;


    @ApiModelProperty(value = "matComBrand")
    @ExcelProperty(value ="公司品牌")
    public String matComBrand;

    @ApiModelProperty(value = "matInvoiceProName")
    @ExcelProperty(value ="开票品名")
    public String matInvoiceProName;

    @ApiModelProperty(value = "matCode")
    @ExcelProperty(value ="物料编码")
    public String matCode;

    @ApiModelProperty(value = "comNameCN")
    @ExcelProperty(value ="报关抬头")
    public String comNameCN;

    @ApiModelProperty(value = "lhrOddNumb")
    @ExcelProperty(value ="物流单号")
    public String lhrOddNumb;

    @ApiModelProperty(value = "packCode")
    @ExcelProperty(value ="出货清单号")
    public String packCode;

}
