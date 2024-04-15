package com.tadpole.cloud.supplyChain.api.logistics.model.params;

import cn.stylefeng.guns.cloud.model.validator.BaseValidatingParam;
import cn.stylefeng.guns.cloud.model.web.request.BaseRequest;
import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ContentFontStyle;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author: ty
 * @description: 报关单、清关单导入sheet0
 * @date: 2023/6/27
 */
@Data
@ExcelIgnoreUnannotated
public class TgCustomsApplyImportSheet0Param extends BaseRequest implements Serializable, BaseValidatingParam {

    private static final long serialVersionUID = 1L;

    /** SKU */
    @ExcelProperty(value ="SKU")
    @ApiModelProperty("SKU")
    private String sku;

    /** 规格型号 */
//    @ExcelProperty(value ="适用机型或机型")
    @ApiModelProperty("规格型号")
    private String type;

    /** 开票规格型号 */
//    @ExcelProperty(value ="款式或尺寸")
    @ApiModelProperty("开票规格型号")
    private String style;

    /** 数量 */
    @ExcelProperty(value ="数量")
    @ApiModelProperty("数量")
    private BigDecimal quantity;

    /** 数量单位 */
    @ExcelProperty(value ="数量单位")
    @ApiModelProperty("数量单位")
    private String unit;

    /** 套装属性 */
    @ExcelProperty(value ="套装属性")
    @ApiModelProperty("套装属性")
    private String attribute;

    /** 币制 */
    @ExcelProperty(value ="币制")
    @ApiModelProperty("币制")
    private String currency;

    /** 箱号 */
    @ExcelProperty(value ="箱号")
    @ApiModelProperty("箱号")
    private String boxNo;

    /** 分单号 */
    @ExcelProperty(value ="分单号")
    @ApiModelProperty("分单号")
    private String splitOrder;

    /** 公司品牌 */
//    @ExcelProperty(value ="公司品牌")
    @ApiModelProperty("公司品牌")
    private String companyBrand;

    /** 开票品名 */
//    @ExcelProperty(value ="开票品名")
    @ApiModelProperty("开票品名")
    private String invoiceProNameCn;

    /** 物料编码 */
    @ExcelProperty(value ="物料编码")
    @ApiModelProperty("物料编码")
    private String materialCode;

    /** 出货清单号 */
    @ExcelProperty(value ="出货清单号")
    @ApiModelProperty("出货清单号")
    private String packCode;

    /** 导入异常信息备注 */
    @ExcelProperty(value ="导入异常信息备注")
    @ApiModelProperty("导入异常信息备注")
    @ContentFontStyle(color = 10, fontName = "宋体", fontHeightInPoints = 11)
    private String uploadRemark;

}
