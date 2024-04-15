package com.tadpole.cloud.platformSettlement.api.finance.entity;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.util.Date;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * ;
 *
 * @author : LSY
 * @date : 2023-12-22
 */
@TableName("LT_WALMART_SETTLEMENT_REPORT")
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ExcelIgnoreUnannotated
public class LtWalmartSettlementReport implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @ApiModelProperty("id")
    @TableField("ID")
    private String id;

    /**
     * 平台
     */
    @ApiModelProperty("平台")
    @ExcelProperty(value = "平台")
    private String platform;

    /**
     * 会计期间
     */
    @ApiModelProperty("会计期间")
    @ExcelProperty(value = "会计期间")
    @TableField("FISCAL_PERIOD")
    private String fiscalPeriod;

    /**
     * 账号
     */
    @ApiModelProperty("账号")
    @ExcelProperty(value = "账号")
    @TableField("SHOP_NAME")
    private String shopName;

    /**
     * 站点
     */
    @ApiModelProperty("站点")
    @ExcelProperty(value = "站点")
    @TableField("SITE")
    private String site;

    /**
     * 事业部
     */
    @ApiModelProperty("事业部")
    @ExcelProperty(value = "事业部")
    @TableField("DEPARTMENT")
    private String department;

    /**
     * Team
     */
    @ApiModelProperty("Team")
    @ExcelProperty(value = "Team")
    @TableField("TEAM")
    private String team;

    /**
     * 店铺
     */
    @ApiModelProperty("店铺")
    @ExcelProperty(value = "店铺")
    @TableField("SHOP")
    private String shop;

    /**
     * 发货方式
     */
    @ApiModelProperty("发货方式")
    @ExcelProperty(value = "发货方式")
    @TableField("DELIVERY_MODE")
    private String deliveryMode;

    /**
     * 订单号
     */
    @ApiModelProperty("订单号")
    @ExcelProperty(value = "订单号")
    @TableField("ORDER_NUM")
    private String orderNum;

    /**
     * 核算币种
     */
    @ApiModelProperty("核算币种")
    @ExcelProperty(value = "核算币种")
    @TableField("ACCOUNTING_CURRENCY")
    private String accountingCurrency;

    /**
     * 物料编码
     */
    @ApiModelProperty("物料编码")
    @ExcelProperty(value = "物料编码")
    @TableField("MAT_CODE")
    private String matCode;

    /**
     * 类目
     */
    @ApiModelProperty("类目")
    @ExcelProperty(value = "类目")
    @TableField("CATEGORY")
    private String category;

    /**
     * 运营大类
     */
    @ApiModelProperty("运营大类")
    @ExcelProperty(value = "运营大类")
    @TableField("PRODUCT_TYPE")
    private String productType;

    /**
     * 产品名称
     */
    @ApiModelProperty("产品名称")
    @ExcelProperty(value = "产品名称")
    @TableField("PRODUCT_NAME")
    private String productName;

    /**
     * 款式
     */
    @ApiModelProperty("款式")
    @ExcelProperty(value = "款式")
    @TableField("STYLE")
    private String style;

    /**
     * 主材料
     */
    @ApiModelProperty("主材料")
    @ExcelProperty(value = "主材料")
    @TableField("MAIN_MATERIAL")
    private String mainMaterial;

    /**
     * 图案
     */
    @ApiModelProperty("图案")
    @ExcelProperty(value = "图案")
    @TableField("DESIGN")
    private String design;

    /**
     * 公司品牌
     */
    @ApiModelProperty("公司品牌")
    @ExcelProperty(value = "公司品牌")
    @TableField("COMPANY_BRAND")
    private String companyBrand;

    /**
     * 适用品牌或对象
     */
    @ApiModelProperty("适用品牌或对象")
    @ExcelProperty(value = "适用品牌或对象")
    @TableField("FIT_BRAND")
    private String fitBrand;

    /**
     * 型号
     */
    @ApiModelProperty("型号")
    @ExcelProperty(value = "型号")
    @TableField("MODEL")
    private String model;

    /**
     * 颜色
     */
    @ApiModelProperty("颜色")
    @ExcelProperty(value = "颜色")
    @TableField("COLOR")
    private String color;

    /**
     * 尺码
     */
    @ApiModelProperty("尺码")
    @ExcelProperty(value = "尺码")
    @TableField("SIZES")
    private String sizes;

    /**
     * 包装数量
     */
    @ApiModelProperty("包装数量")
    @ExcelProperty(value = "包装数量")
    @TableField("PACKING")
    private String packing;

    /**
     * 版本
     */
    @ApiModelProperty("版本")
    @ExcelProperty(value = "版本")
    @TableField("VERSION")
    private String version;

    /**
     * 适用机型
     */
    @ApiModelProperty("适用机型")
    @ExcelProperty(value = "适用机型")
    @TableField("TYPE")
    private String type;

    /**
     * 销售品牌
     */
    @ApiModelProperty("销售品牌")
    @ExcelProperty(value = "销售品牌")
    @TableField("SALES_BRAND")
    private String salesBrand;

    /**
     * 费用类型辅助说明
     */
    @ApiModelProperty("费用类型辅助说明")
    @ExcelProperty(value = "费用类型辅助说明")
    @TableField("COST_AUXILIARY_DESCRIPTION")
    private String costAuxiliaryDescription;

    /**
     * 数量
     */
    @ApiModelProperty("数量")
    @ExcelProperty(value = "数量")
    @TableField("QTY")
    private BigDecimal qty;

    /**
     * 测评数量
     */
    @ApiModelProperty("测评数量")
    @ExcelProperty(value = "测评数量")
    @TableField("VOLUME_BILL_QUANTITY")
    private BigDecimal volumeBillQuantity;

    /**
     * Product_Price
     */
    @ApiModelProperty("Product Price")
    @ExcelProperty(value = "Product_Price")
    @TableField("PRODUCT_PRICE")
    private BigDecimal productPrice;

    /**
     * Product_Commission
     */
    @ApiModelProperty("productCommission")
    @ExcelProperty(value = "Product_Commission")
    @TableField("PRODUCT_COMMISSION")
    private BigDecimal productCommission;

    /**
     * Product_tax
     */
    @ApiModelProperty("productTax")
    @ExcelProperty(value = "Product_tax")
    @TableField("PRODUCT_TAX")
    private BigDecimal productTax;

    /**
     * Product_tax_withheld
     */
    @ApiModelProperty("productTaxWithheld")
    @ExcelProperty(value = "Product_tax_withheld")
    @TableField("PRODUCT_TAX_WITHHELD")
    private BigDecimal productTaxWithheld;


    /**
     * Refund
     */
    @ApiModelProperty("refund")
    @ExcelProperty(value = "Refund")
    @TableField("REFUND")
    private BigDecimal refund;

    /**
     * Refund_Commission
     */
    @ApiModelProperty("refundCommission")
    @ExcelProperty(value = "Refund_Commission")
    @TableField("REFUND_COMMISSION")
    private BigDecimal refundCommission;

    /**
     * Refund_tax
     */
    @ApiModelProperty("refundTax")
    @ExcelProperty(value = "Refund_tax")
    @TableField("REFUND_TAX")
    private BigDecimal refundTax;

    /**
     * Refund_tax_withheld
     */
    @ApiModelProperty("refundTaxWithheld")
    @ExcelProperty(value = "Refund_tax_withheld")
    @TableField("REFUND_TAX_WITHHELD")
    private BigDecimal refundTaxWithheld;

    /**
     * WFS_Fulfillment_fee
     */
    @ApiModelProperty("wfsFulfillmentFee")
    @ExcelProperty(value = "WFS_Fulfillment_fee")
    @TableField("WFS_FULFILLMENT_FEE")
    private BigDecimal wfsFulfillmentFee;

    /**
     * StorageFee
     */
    @ApiModelProperty("storageFee")
    @ExcelProperty(value = "StorageFee")
    @TableField("STORAGE_FEE")
    private BigDecimal storageFee;

    /**
     * InventoryDisposalFee
     */
    @ApiModelProperty("inventoryDisposalFee")
    @ExcelProperty(value = "InventoryDisposalFee")
    @TableField("INVENTORY_DISPOSAL_FEE")
    private BigDecimal inventoryDisposalFee;

    /**
     * Inbound_Fee
     */
    @ApiModelProperty("")
    @ExcelProperty(value = "Inbound_Fee")
    @TableField("INBOUND_FEE")
    private BigDecimal inboundFee;

    /**
     * 移除费
     */
    @ApiModelProperty("移除费")
    @ExcelProperty(value = "移除费")
    @TableField("REMOVAL_DEAL")
    private BigDecimal removalDeal;

    /**
     * Sales_Shipping
     */
    @ApiModelProperty("")
    @ExcelProperty(value = "Sales_Shipping")
    @TableField("SALES_SHIPPING")
    private BigDecimal salesShipping;

    /**
     * Sales_Shipping_Reversal
     */
    @ApiModelProperty("")
    @ExcelProperty(value = "Sales_Shipping_Reversal")
    @TableField("SALES_SHIPPING_REVERSAL")
    private BigDecimal salesShippingReversal;

    /**
     * Other_tax
     */
    @ApiModelProperty("")
    @ExcelProperty(value = "Other_tax")
    @TableField("OTHER_TAX")
    private BigDecimal otherTax;

    /**
     * Other
     */
    @ApiModelProperty("")
    @ExcelProperty(value = "Other")
    @TableField("OTHER")
    private BigDecimal other;

    /**
     * 回款
     */
    @ApiModelProperty("回款")
    @ExcelProperty(value = "回款")
    @TableField("COLLECTION")
    private BigDecimal collection;

    /**
     * Advertising
     */
    @ApiModelProperty("")
    @ExcelProperty(value = "Advertising")
    @TableField("ADVERTISING")
    private BigDecimal advertising;

    /**
     * 回款期间
     */
    @ApiModelProperty("回款期间")
    @ExcelProperty(value = "回款期间")
    @TableField("COLLECTION_PERIOD")
    private String collectionPeriod;

    /**
     * 站外物流费
     */
    @ApiModelProperty("站外物流费")
    @ExcelProperty(value = "站外物流费")
    @TableField("STATION_OUT_LOG")
    private BigDecimal stationOutLog;

    /**
     * 站外广告费
     */
    @ApiModelProperty("站外广告费")
    @ExcelProperty(value = "站外广告费")
    @TableField("STATION_OUT_AD")
    private BigDecimal stationOutAd;

    /**
     * 站外仓储费
     */
    @ApiModelProperty("站外仓储费")
    @ExcelProperty(value = "站外仓储费")
    @TableField("STATION_OUT_STORAGE")
    private BigDecimal stationOutStorage;

    /**
     * 滞销库存利息费
     */
    @ApiModelProperty("滞销库存利息费")
    @ExcelProperty(value = "滞销库存利息费")
    @TableField("DOMESTIC_UNSALABLE_INVENTORY")
    private BigDecimal domesticUnsalableInventory;

    /**
     * 测评费_货值
     */
    @ApiModelProperty("测评货值")
    @ExcelProperty(value = "测评货值")
    @TableField("BRUSHING_VALUE")
    private BigDecimal brushingValue;

    /**
     * 测评费_手续费
     */
    @ApiModelProperty("测评手续费")
    @ExcelProperty(value = "测评手续费")
    @TableField("BRUSHING_SERVICE_CHARGE")
    private BigDecimal brushingServiceCharge;

    /**
     * 鼓励金
     */
    @ApiModelProperty("鼓励金")
    @ExcelProperty(value = "鼓励金")
    @TableField("INCENTIVE_FUND")
    private BigDecimal incentiveFund;

    /**
     * 成本核算金额
     */
    @ApiModelProperty("成本核算金额")
    @ExcelProperty(value = "成本核算金额")
    @TableField("COST_ACCOUNTING_FEE")
    private BigDecimal costAccountingFee;

    /**
     * 附加成本金额
     */
    @ApiModelProperty("附加成本金额")
    @ExcelProperty(value = "附加成本金额")
    @TableField("ADDITIONAL_COST_AMOUNT")
    private BigDecimal additionalCostAmount;

    /**
     * 头程物流费
     */
    @ApiModelProperty("头程物流费")
    @ExcelProperty(value = "头程物流费")
    @TableField("FIRST_TRIP_FEE")
    private BigDecimal firstTripFee;

    /**
     * Profit
     */
    @ApiModelProperty("Profit")
    @ExcelProperty(value = "Profit")
    @TableField("PROFIT")
    private BigDecimal profit;

    /**
     * 销量占比
     */
    @ApiModelProperty("销量占比")
    @ExcelProperty(value = "销量占比")
    @TableField("REVENUE_RATION")
    private BigDecimal revenueRation;

    /**
     * 人数
     */
    @ApiModelProperty("人数")
    @ExcelProperty(value = "人数")
    @TableField("PEOPLE_NUM")
    private BigDecimal peopleNum;

    /**
     * 人工成本
     */
    @ApiModelProperty("人工成本")
    @ExcelProperty(value = "人工成本")
    @TableField("PEOPLE_COST")
    private BigDecimal peopleCost;

    /**
     * 成本单价CNY
     */
    @ApiModelProperty("成本单价CNY")
    @ExcelProperty(value = "成本单价CNY")
    @TableField("UNIT_COST_CNY")
    private BigDecimal unitCostCny;

    /**
     * 含税单价CNY
     */
    @ApiModelProperty("含税单价CNY")
    @ExcelProperty(value = "含税单价CNY")
    @TableField("TAX_PRICE_CNY")
    private BigDecimal taxPriceCny;

    /**
     * 物流单价CNY
     */
    @ApiModelProperty("物流单价CNY")
    @ExcelProperty(value = "物流单价CNY")
    @TableField("LOG_PRICE_CNY")
    private BigDecimal logPriceCny;

    /**
     *
     */
    @ApiModelProperty("")
    @TableField("UPDATE_TIME")
    private Date updateTime;

    /**
     *
     */
    @ApiModelProperty("")
    @TableField("UPDATE_BY")
    private String updateBy;

    /**
     *
     */
    @ApiModelProperty("")
    @TableField("CREATE_TIME")
    private Date createTime;

    /**
     *
     */
    @ApiModelProperty("")
    @TableField("CREATE_BY")
    private String createBy;


    @TableField("CONFIRM_STATUS")
    private BigDecimal confirmStatus;


}