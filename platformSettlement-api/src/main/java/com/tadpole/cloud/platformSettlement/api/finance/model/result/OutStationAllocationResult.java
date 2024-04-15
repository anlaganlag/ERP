package com.tadpole.cloud.platformSettlement.api.finance.model.result;

import cn.stylefeng.guns.cloud.model.validator.BaseValidatingParam;
import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
* <p>
* 补贴汇总表
* </p>
*
* @author gal
* @since 2021-12-24
*/
@Data
@ApiModel
@ExcelIgnoreUnannotated
public class OutStationAllocationResult implements  Serializable, BaseValidatingParam {

    @ApiModelProperty("ID")
    private BigDecimal id;

    /** 会计期间 */
    @TableField("FISCAL_PERIOD")
    @ExcelProperty(value= "会计期间")
    private String fiscalPeriod;

    /** 账号 */
    @TableField("SHOP_NAME")
    @ExcelProperty(value= "账号")
    private String shopName;

    /** 站点 */
    @TableField("SITE")
    @ExcelProperty(value= "站点")
    private String site;

    /** 原币 */
    @TableField("ACCOUNTING_CURRENCY")
    @ExcelProperty(value= "核算币种")
    private String accountingCurrency;

    @TableField("SKU")
    @ExcelProperty(value= "SKU")
    private String sku;

    @TableField("DEPARTMENT")
    @ExcelProperty(value= "事业部")
    private String department;

    @TableField("TEAM")
    @ExcelProperty(value= "Team")
    private String team;

    @TableField("MATERIAL_CODE")
    @ExcelProperty(value= "物料编码")
    private String materialCode;

    @ApiModelProperty("CATEGORY")
    @ExcelProperty(value = "类目")
    private String category;

    @ApiModelProperty("PRODUCT_TYPE")
    @ExcelProperty(value = "运营大类")
    private String productType;

    @ApiModelProperty("PRODUCT_NAME")
    @ExcelProperty(value = "产品名称")
    private String productName;

    @ApiModelProperty("STYLE")
    @ExcelProperty(value = "款式")
    private String style;

    @ApiModelProperty("MAIN_MATERIAL")
    @ExcelProperty(value = "主材料")
    private String mainMaterial;

    @ApiModelProperty("DESIGN")
    @ExcelProperty(value = "图案")
    private String design;

    @ApiModelProperty("FIT_BRAND")
    @ExcelProperty(value = "适用品牌或对象")
    private String fitBrand;

    @ApiModelProperty("MODEL")
    @ExcelProperty(value = "型号")
    private String model;

    @ApiModelProperty("COMPANY_BRAND")
    @ExcelProperty(value = "公司品牌")
    private String companyBrand;

    @ApiModelProperty("COLOR")
    @ExcelProperty(value = "颜色")
    private String color;

    @ApiModelProperty("SIZES")
    @ExcelProperty(value = "尺码")
    private String sizes;

    @ApiModelProperty("PACKING")
    @ExcelProperty(value = "包装数量")
    private String packing;

    @ApiModelProperty("VERSION")
    @ExcelProperty(value = "版本")
    private String version;

    @ApiModelProperty("TYPE")
    @ExcelProperty(value = "适用机型")
    private String type;

    @ApiModelProperty("SALES_BRAND")
    @ExcelProperty(value= "销售品牌")
    private String salesBrand;

    /** advertising */
    @TableField("COST_AUXILIARY_DESCRIPTION")
    @ExcelProperty(value= "费用类型辅助说明")
    private String costAuxiliaryDescription;

    @TableField("VOLUME_BILL_QUANTITY")
    @ExcelProperty(value= "Volume(测评数量)")
    private BigDecimal volumeBillQuantity;

    @TableField("OTHER_ADVERTISEMENTS")
    @ExcelProperty(value= "其它站外广告")
    private BigDecimal otherAdvertisements;

    @TableField("BRUSHING_VALUE")
    @ExcelProperty(value= "测评货值")
    private BigDecimal brushingValue;

    @TableField("BRUSHING_SERVICE_CHARGE")
    @ExcelProperty(value= "测评手续费")
    private BigDecimal brushingServiceCharge;

    @TableField("LOCAL_LOGISTICS_FEE")
    @ExcelProperty(value= "当地物流费")
    private BigDecimal localLogisticsFee;

    @TableField("OVERSEAS_WAREHOUSE_FEE")
    @ExcelProperty(value= "海外仓费用")
    private BigDecimal overseasWarehouseFee;

    @TableField("DISPOSE_PURCHASE_FEE")
    @ExcelProperty(value= "销毁成本-采购成本")
    private BigDecimal disposePurchaseFee;

    @TableField("DISPOSE_LOGISTICS_FEE")
    @ExcelProperty(value= "销毁成本-头程物流成本")
    private BigDecimal disposeLogisticsFee;

    @TableField("DOMESTIC_UNSALABLE_INVENTORY")
    @ExcelProperty(value= "国内滞销库存")
    private BigDecimal domesticUnsalableInventory;

    @TableField("MOLD_OPENING_COST")
    @ExcelProperty(value= "开模费用")
    private BigDecimal moldOpeningCost;

    @ApiModelProperty("CONFIRM_STATUS_TXT")
    @ExcelProperty(value= "确认状态")
    private String confirmStatusTxt;

    @ApiModelProperty("CONFIRM_BY")
    @ExcelProperty(value= "确认人员")
    private String confirmBy;

    @ApiModelProperty("CONFIRM_DATE")
    @ExcelProperty(value= "确认时间")
    private Date confirmDate;

    @ApiModelProperty("CONFIRM_STATUS")
    private BigDecimal confirmStatus;

}