package com.tadpole.cloud.platformSettlement.api.finance.model.result;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import java.util.Date;

import java.io.Serializable;
import java.math.BigDecimal;

 /**
 * ;
 * @author : LSY
 * @date : 2023-12-22
 */
@ApiModel(value = "",description = "")
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ExcelIgnoreUnannotated
public class LtWalmartSettlementReportResult implements Serializable{
 private static final long serialVersionUID = 1L;
 
    /**  */
    @ApiModelProperty(value = "")
    private String id ;


    @ExcelProperty(value ="平台")
    private String platform;
 
    /** 会计期间 */
    @ApiModelProperty(value = "会计期间")
    @ExcelProperty(value ="会计期间")
    private String fiscalPeriod ;
 
    /** 账号 */
    @ApiModelProperty(value = "账号")
    @ExcelProperty(value ="账号")
    private String shopName ;
 
    /** 站点 */
    @ApiModelProperty(value = "站点")
    @ExcelProperty(value ="站点")
    private String site ;

    /** 事业部 */
    @ApiModelProperty(value = "事业部")
    @ExcelProperty(value ="事业部")
    private String department ;

    /** Team */
    @ApiModelProperty(value = "Team")
    @ExcelProperty(value ="Team")
    private String team ;
 
    /** 店铺 */
    @ApiModelProperty(value = "店铺")
    @ExcelProperty(value ="店铺")
    private String shop ;
 
    /** 发货方式 */
    @ApiModelProperty(value = "发货方式")
    @ExcelProperty(value ="发货方式")
    private String deliveryMode ;
 
    /** 订单号 */
    @ApiModelProperty(value = "订单号")
    @ExcelProperty(value ="订单号")
    private String orderNum ;
 
    /** 核算币种 */
    @ApiModelProperty(value = "核算币种")
    @ExcelProperty(value ="核算币种")
    private String accountingCurrency ;


 
    /** 物料编码 */
    @ApiModelProperty(value = "物料编码")
    @ExcelProperty(value ="物料编码")
    private String matCode ;
 
    /** 类目 */
    @ApiModelProperty(value = "类目")
    @ExcelProperty(value ="类目")
    private String category ;
 
    /** 运营大类 */
    @ApiModelProperty(value = "运营大类")
    @ExcelProperty(value ="运营大类")
    private String productType ;
 
    /** 产品名称 */
    @ApiModelProperty(value = "产品名称")
    @ExcelProperty(value ="产品名称")
    private String productName ;
 
    /** 款式 */
    @ApiModelProperty(value = "款式")
    @ExcelProperty(value ="款式")
    private String style ;
 
    /** 主材料 */
    @ApiModelProperty(value = "主材料")
    @ExcelProperty(value ="主材料")
    private String mainMaterial ;
 
    /** 图案 */
    @ApiModelProperty(value = "图案")
    @ExcelProperty(value ="图案")
    private String design ;
 
    /** 公司品牌 */
    @ApiModelProperty(value = "公司品牌")
    @ExcelProperty(value ="公司品牌")
    private String companyBrand ;
 
    /** 适用品牌或对象 */
    @ApiModelProperty(value = "适用品牌或对象")
    @ExcelProperty(value ="适用品牌或对象")
    private String fitBrand ;
 
    /** 型号 */
    @ApiModelProperty(value = "型号")
    @ExcelProperty(value ="型号")
    private String model ;
 
    /** 颜色 */
    @ApiModelProperty(value = "颜色")
    @ExcelProperty(value ="颜色")
    private String color ;
 
    /** 尺码 */
    @ApiModelProperty(value = "尺码")
    @ExcelProperty(value ="尺码")
    private String sizes ;
 
    /** 包装数量 */
    @ApiModelProperty(value = "包装数量")
    @ExcelProperty(value ="包装数量")
    private String packing ;
 
    /** 版本 */
    @ApiModelProperty(value = "版本")
    @ExcelProperty(value ="版本")
    private String version ;
 
    /** 适用机型 */
    @ApiModelProperty(value = "适用机型")
    @ExcelProperty(value ="适用机型")
    private String type ;
 
    /** 销售品牌 */
    @ApiModelProperty(value = "销售品牌")
    @ExcelProperty(value ="销售品牌")
    private String salesBrand ;
 
    /** 费用类型辅助说明 */
    @ApiModelProperty(value = "费用类型辅助说明")
    @ExcelProperty(value ="费用类型辅助说明")
    private String costAuxiliaryDescription ;
 
    /** 数量 */
    @ApiModelProperty(value = "数量")
    @ExcelProperty(value ="数量")
    private BigDecimal qty ;
 
    /** 测评数量 */
    @ApiModelProperty(value = "测评数量")
    @ExcelProperty(value ="测评数量")
    private BigDecimal volumeBillQuantity ;
    /** Product Price */
    @ApiModelProperty("Product Price")
    @ExcelProperty(value="Product_Price")
    @TableField("PRODUCT_PRICE")
    private BigDecimal productPrice ;

    /**  */
    @ApiModelProperty("productCommission")
    @ExcelProperty(value="Product_Commission")
    @TableField("PRODUCT_COMMISSION")
    private BigDecimal productCommission ;

    /**  */
    @ApiModelProperty("productTax")
    @ExcelProperty(value="Product_tax")
    @TableField("PRODUCT_TAX")
    private BigDecimal productTax ;


    @ApiModelProperty("productTaxWithheld")
    @ExcelProperty(value="Product_tax_withheld")
    @TableField("PRODUCT_TAX_WITHHELD")
    private BigDecimal productTaxWithheld ;



    /**  */
    @ApiModelProperty("refund")
    @ExcelProperty(value="Refund")
    @TableField("REFUND")
    private BigDecimal refund ;

    /**  */
    @ApiModelProperty("refundCommission")
    @ExcelProperty(value="Refund_Commission")
    @TableField("REFUND_COMMISSION")
    private BigDecimal refundCommission ;

    /**  */
    @ApiModelProperty("refundTax")
    @ExcelProperty(value="Refund_tax")
    @TableField("REFUND_TAX")
    private BigDecimal refundTax ;

    /**  */
    @ApiModelProperty("refundTaxWithheld")
    @ExcelProperty(value="Refund_tax_withheld")
    @TableField("REFUND_TAX_WITHHELD")
    private BigDecimal refundTaxWithheld ;

    /**  */
    @ApiModelProperty("wfsFulfillmentFee")
    @ExcelProperty(value="WFS_Fulfillment_fee")
    @TableField("WFS_FULFILLMENT_FEE")
    private BigDecimal wfsFulfillmentFee ;

    /**  */
    @ApiModelProperty("storageFee")
    @ExcelProperty(value="storageFee")
    @TableField("STORAGE_FEE")
    private BigDecimal storageFee ;

    /**  */
    @ApiModelProperty("inventoryDisposalFee")
    @ExcelProperty(value="InventoryDisposalFee")
    @TableField("INVENTORY_DISPOSAL_FEE")
    private BigDecimal inventoryDisposalFee ;

    /**  */
    @ApiModelProperty("")
    @ExcelProperty(value="Inbound_Fee")
    @TableField("INBOUND_FEE")
    private BigDecimal inboundFee ;

    /** 移除费 */
    @ApiModelProperty(value = "移除费")
    @ExcelProperty(value ="移除费")
    private BigDecimal removalDeal ;
 
    /**  */
    @ApiModelProperty(value = "")
    @ExcelProperty(value ="Sales_Shipping")
    private BigDecimal salesShipping ;
 
    /**  */
    @ApiModelProperty(value = "")
    @ExcelProperty(value ="Sales_Shipping_Reversal")
    private BigDecimal salesShippingReversal ;
 
    /**  */
    @ApiModelProperty(value = "")
    @ExcelProperty(value ="Other_tax")
    private BigDecimal otherTax ;
 
    /**  */
    @ApiModelProperty(value = "")
    @ExcelProperty(value ="Other")
    private BigDecimal other ;
 
    /** 回款 */
    @ApiModelProperty(value = "回款")
    @ExcelProperty(value ="回款")
    private BigDecimal collection ;
 
    /**  */
    @ApiModelProperty(value = "")
    @ExcelProperty(value ="Advertising")
    private BigDecimal advertising ;
 
    /** 回款期间 */
    @ApiModelProperty(value = "回款期间")
    @ExcelProperty(value ="回款期间")
    private String collectionPeriod ;
 
    /** 站外物流费 */
    @ApiModelProperty(value = "站外物流费")
    @ExcelProperty(value ="站外物流费")
    private BigDecimal stationOutLog ;
 
    /** 站外广告费 */
    @ApiModelProperty(value = "站外广告费")
    @ExcelProperty(value ="站外广告费")
    private BigDecimal stationOutAd ;
 
    /** 站外仓储费 */
    @ApiModelProperty(value = "站外仓储费")
    @ExcelProperty(value ="站外仓储费")
    private BigDecimal stationOutStorage ;
 
    /** 滞销库存利息费 */
    @ApiModelProperty(value = "滞销库存利息费")
    @ExcelProperty(value ="滞销库存利息费")
    private BigDecimal domesticUnsalableInventory ;
 
    /**测评数量 */
    @ApiModelProperty(value = "测评货值")
    @ExcelProperty(value ="测评货值")
    private BigDecimal brushingValue ;
 
    /** 测评手续费 */
    @ApiModelProperty(value = "测评手续费")
    @ExcelProperty(value ="测评手续费")
    private BigDecimal brushingServiceCharge ;
 
    /** 鼓励金 */
    @ApiModelProperty(value = "鼓励金")
    @ExcelProperty(value ="鼓励金")
    private BigDecimal incentiveFund ;
 
    /** 成本核算金额 */
    @ApiModelProperty(value = "成本核算金额")
    @ExcelProperty(value ="成本核算金额")
    private BigDecimal costAccountingFee ;
 
    /** 附加成本金额 */
    @ApiModelProperty(value = "附加成本金额")
    @ExcelProperty(value ="附加成本金额")
    private BigDecimal additionalCostAmount ;
 
    /** 头程物流费 */
    @ApiModelProperty(value = "头程物流费")
    @ExcelProperty(value ="头程物流费")
    private BigDecimal firstTripFee ;
 
    /** Profit */
    @ApiModelProperty(value = "Profit")
    @ExcelProperty(value ="Profit")
    private BigDecimal profit ;

    @ApiModelProperty("销量占比")
    @ExcelProperty(value="销量占比")
    @TableField("REVENUE_RATION")
    private BigDecimal revenueRation ;

    /** 人数 */
    @ApiModelProperty("人数")
    @ExcelProperty(value="人数")
    @TableField("PEOPLE_NUM")
    private BigDecimal peopleNum ;

    /** 人工成本 */
    @ApiModelProperty("人工成本")
    @ExcelProperty(value="人工成本")
    @TableField("PEOPLE_COST")
    private BigDecimal peopleCost ;
 
    /** 成本单价CNY */
    @ApiModelProperty(value = "成本单价CNY")
    @ExcelProperty(value ="成本单价CNY")
    private BigDecimal unitCostCny ;
 
    /** 含税单价CNY */
    @ApiModelProperty(value = "含税单价CNY")
    @ExcelProperty(value ="含税单价CNY")
    private BigDecimal taxPriceCny ;
 
    /** 物流单价CNY */
    @ApiModelProperty(value = "物流单价CNY")
    @ExcelProperty(value ="物流单价CNY")
    private BigDecimal logPriceCny ;
 
    /**  */
    @ApiModelProperty(value = "")
    private Date updateTime ;
 
    /**  */
    @ApiModelProperty(value = "")
    private String updateBy ;
 
    /**  */
    @ApiModelProperty(value = "")
    private Date createTime ;
 
    /**  */
    @ApiModelProperty(value = "")
    private String createBy ;





    @ApiModelProperty("CONFIRM_STATUS")
    private BigDecimal confirmStatus;

    private  String structId;
    private  BigDecimal amazonAlloc;
    ;

    private BigDecimal detailsalesvol;

    private BigDecimal teamsalesvol;



}