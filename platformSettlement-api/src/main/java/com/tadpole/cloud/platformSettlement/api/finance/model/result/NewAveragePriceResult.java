package com.tadpole.cloud.platformSettlement.api.finance.model.result;

import cn.stylefeng.guns.cloud.model.validator.BaseValidatingParam;
import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
* <p>
* 新核算库存平均单价表
* </p>
*
* @author gal
* @since 2021-12-24
*/
@Data
@ApiModel
public class NewAveragePriceResult implements Serializable, BaseValidatingParam {

    private static final long serialVersionUID = 1L;

    /** 会计期间 */
    @ApiModelProperty("FISCAL_PERIOD")
    @ExcelProperty(value= "会计期间")
    private String fiscalPeriod;

    /** 账号 */
    @ApiModelProperty("SHOP_NAME")
    @ExcelProperty(value = "账号")
    private String shopName;

    /** 站点 */
    @ApiModelProperty("SITE")
    @ExcelProperty(value= "站点")
    private String site;

    @ApiModelProperty("MATERIAL_CODE")
    @ExcelProperty(value= "物料编码")
    private String materialCode;

    @ApiModelProperty("LOGISTICS_UNIT_PRICE")
    @ExcelProperty(value= "物流单价（CNY）")
    private BigDecimal logisticsUnitPrice;

    @ApiModelProperty("PURCHASE_UNIT_PRICE")
    @ExcelProperty(value= "采购单价（CNY）")
    private BigDecimal purchaseUnitPrice;

    @ApiModelProperty("ADDITIONAL_UNIT_PRICE")
    @ExcelProperty(value= "附加单价（CNY）")
    private BigDecimal additionalUnitPrice;

    @ApiModelProperty("CONFIRM_DATE")
    @ExcelProperty(value= "确认时间")
    private String confirmDate;

    @ApiModelProperty("CONFIRM_BY")
    @ExcelProperty(value= "确认人")
    private String confirmBy;

    @ExcelIgnore
    @ApiModelProperty("CONFIRM_STATUS")
    private BigDecimal confirmStatus;

    @ApiModelProperty("CONFIRM_STATUS_TXT")
    @ExcelProperty(value= "确认状态")
    private String confirmStatusTxt;
    
    /** 创建人 */
    @ApiModelProperty("CREATE_BY")
    private String createBy;

    /** 创建时间 */
    @ApiModelProperty("CREATE_AT")
    private Date createAt;

    @ExcelIgnore
    @ApiModelProperty("ID")
    private BigDecimal id;


    @ApiModelProperty("CATEGORY")
    @ExcelProperty(value= "类目")
    private String category;

    @ApiModelProperty("PRODUCT_TYPE")
    @ExcelProperty(value= "运营大类")
    private String productType;

    @ApiModelProperty("PRODUCT_NAME")
    @ExcelProperty(value= "产品名称")
    private String productName;

    @ApiModelProperty("STYLE")
    @ExcelProperty(value= "款式")
    private String style;

    @ApiModelProperty("MAIN_MATERIAL")
    @ExcelProperty(value= "主材料")
    private String mainMaterial;

    @ApiModelProperty("DESIGN")
    @ExcelProperty(value= "图案")
    private String design;

    @ApiModelProperty("COMPANY_BRAND")
    @ExcelProperty(value= "公司品牌")
    private String companyBrand;

    @ApiModelProperty("FIT_BRAND")
    @ExcelProperty(value= "适用品牌或对象")
    private String fitBrand;

    @ApiModelProperty("MODEL")
    @ExcelProperty(value= "型号")
    private String model;

    @ApiModelProperty("COLOR")
    @ExcelProperty(value= "颜色")
    private String color;

    @ApiModelProperty("SIZES")
    @ExcelProperty(value= "尺码")
    private String sizes;

    @ApiModelProperty("PACKING")
    @ExcelProperty(value= "包装数量")
    private String packing;

    @ApiModelProperty("VERSION")
    @ExcelProperty(value= "版本")
    private String version;

    @ApiModelProperty("TYPE")
    @ExcelProperty(value= "适用机型")
    private String type;

}