package com.tadpole.cloud.platformSettlement.api.finance.model.result;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import java.math.BigDecimal;

/**
 * @author: ty
 * @description: AZ销售订单预结算响应参数
 * @date: 2022/5/30
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ExcelIgnoreUnannotated
public class OrderPreSettlementResult {

    @ApiModelProperty("销售时间")
    @ExcelProperty(value ="销售时间")
    private String purchaseDate;

    @ApiModelProperty("订单号")
    @ExcelProperty(value ="订单号")
    private String amazonOrderId;

    @ApiModelProperty("账号")
    @ExcelProperty(value ="账号")
    private String sysShopsName;

    @ApiModelProperty("站点")
    @ExcelProperty(value ="站点")
    private String sysSite;

    @ApiModelProperty("sku")
    @ExcelProperty(value ="sku")
    private String sku;

    @ApiModelProperty("物料编码")
    @ExcelProperty(value ="物料编码")
    private String materialCode;

    @ApiModelProperty("销售品牌")
    @ExcelProperty(value ="销售品牌")
    private String salesBrand;

    @ApiModelProperty("事业部")
    @ExcelProperty(value ="事业部")
    private String department;

    @ApiModelProperty("Team")
    @ExcelProperty(value ="Team")
    private String team;

    @ApiModelProperty("采购员")
    @ExcelProperty(value ="采购员")
    private String buyer;

    @ApiModelProperty("开发人员")
    @ExcelProperty(value ="开发人员")
    private String developer;

    @ApiModelProperty("核算日期")
    @ExcelProperty(value ="销售时间")
    private String accountDate;

    @ApiModelProperty("是否新品")
    @ExcelProperty(value ="是否新品")
    private String isNew;

    @ApiModelProperty("类目")
    @ExcelProperty(value ="类目")
    private String category;

    @ApiModelProperty("运营大类")
    @ExcelProperty(value ="运营大类")
    private String productType;

    @ApiModelProperty("产品名称")
    @ExcelProperty(value ="产品名称")
    private String productName;

    @ApiModelProperty("款式")
    @ExcelProperty(value ="款式")
    private String style;

    @ApiModelProperty("主材料")
    @ExcelProperty(value ="主材料")
    private String mainMaterial;

    @ApiModelProperty("图案")
    @ExcelProperty(value ="图案")
    private String design;

    @ApiModelProperty("适用品牌或对象")
    @ExcelProperty(value ="适用品牌或对象")
    private String fitBrand;

    @ApiModelProperty("型号")
    @ExcelProperty(value ="型号")
    private String model;

    @ApiModelProperty("公司品牌")
    @ExcelProperty(value ="公司品牌")
    private String companyBrand;

    @ApiModelProperty("颜色")
    @ExcelProperty(value ="颜色")
    private String color;

    @ApiModelProperty("尺码")
    @ExcelProperty(value ="尺码")
    private String sizes;

    @ApiModelProperty("包装数量")
    @ExcelProperty(value ="包装数量")
    private String packing;

    @ApiModelProperty("版本")
    @ExcelProperty(value ="版本")
    private String version;

    @ApiModelProperty("适用机型")
    @ExcelProperty(value ="适用机型")
    private String type;

    @ApiModelProperty("款式二级标签")
    @ExcelProperty(value = "款式二级标签")
    private String styleSecondLabel;

    @ApiModelProperty("核算币别")
    @ExcelProperty(value ="核算币别")
    private String currency;

    @ExcelProperty(value = "Volume（正常发货）")
    @ApiModelProperty(value = "Volume（正常发货）")
    private Integer volumeNormal;

    @ExcelProperty(value = "Volume（补发货）")
    @ApiModelProperty(value = "Volume（补发货）")
    private Integer volumeReissue;

    @ExcelProperty(value = "Volume（刷单数量）")
    @ApiModelProperty(value = "Volume（刷单数量）")
    private Integer volumeBillQuantity;

    @ExcelProperty(value = "Volume（退货）")
    @ApiModelProperty(value = "Volume（退货）")
    private Integer volumeReturn;

    @ExcelProperty(value = "Sales_excluding_tax")
    @ApiModelProperty(value = "Sales_excluding_tax")
    private BigDecimal salesExcludingTax;

    @ExcelProperty(value = "Tax")
    @ApiModelProperty(value = "Tax")
    private BigDecimal tax;

    @ExcelProperty(value = "Sales Promotion")
    @ApiModelProperty(value = "Sales Promotion")
    private BigDecimal salesPromotion;

    @ExcelProperty(value = "Refund")
    @ApiModelProperty(value = "Refund")
    private BigDecimal refund;

    @ExcelProperty(value = "Refund Promotion")
    @ApiModelProperty(value = "Refund Promotion")
    private BigDecimal refundPromotion;

    @ExcelProperty(value = "Giftwarp_Shipping")
    @ApiModelProperty(value = "GiftWarp&Shipping")
    private BigDecimal giftWarpShipping;

    @ExcelProperty(value = "Net Sales")
    @ApiModelProperty(value = "Net Sales")
    private BigDecimal netSales;

    @ExcelProperty(value = "Commission")
    @ApiModelProperty(value = "Commission")
    private BigDecimal commission;

    @ExcelProperty(value = "Refund Commission")
    @ApiModelProperty(value = "Refund Commission")
    private BigDecimal refundCommission;

    @ExcelProperty(value = "Goodwill")
    @ApiModelProperty(value = "Goodwill")
    private BigDecimal goodwill;

    @ExcelProperty(value = "Amazon_Fees")
    @ApiModelProperty(value = "Amazon Fees")
    private BigDecimal amazonFees;

    @ExcelProperty(value = "Storage fee")
    @ApiModelProperty(value = "Storage fee")
    private BigDecimal storageFee;

    @ExcelProperty(value = "Reimbursement")
    @ApiModelProperty(value = "Reimbursement")
    private BigDecimal reimbursement;

    @ExcelProperty(value = "Other")
    @ApiModelProperty(value = "Other")
    private BigDecimal other;

    @ExcelProperty(value = "Withheld_tax")
    @ApiModelProperty(value = "Withheld_tax")
    private BigDecimal withheldTax;

    @ExcelProperty(value = "销毁费")
    @ApiModelProperty(value = "销毁费")
    private BigDecimal disposeFee;

    @ExcelProperty(value = "移除费")
    @ApiModelProperty(value = "移除费")
    private BigDecimal removalDeal;

    @ExcelProperty(value = "回款")
    @ApiModelProperty(value = "回款")
    private BigDecimal collection;

    @ExcelProperty(value = "Advertising")
    @ApiModelProperty(value = "Advertising")
    private BigDecimal advertising;

    @ExcelProperty(value = "Kindle广告费")
    @ApiModelProperty(value = "Kindle广告费")
    private BigDecimal kindleAdvertisingFee;

    @ExcelProperty(value = "其他站外广告")
    @ApiModelProperty(value = "其他站外广告")
    private BigDecimal otherAdvertisements;

    @ExcelProperty(value = "刷单费-货值")
    @ApiModelProperty(value = "刷单费-货值")
    private BigDecimal brushingValue;

    @ExcelProperty(value = "刷单费-手续费")
    @ApiModelProperty(value = "刷单费-手续费")
    private BigDecimal brushingServiceCharge;

    @ExcelProperty(value = "成本核算金额")
    @ApiModelProperty(value = "成本核算金额")
    private BigDecimal costAccountingFee;

    @ExcelProperty(value = "附加成本金额")
    @ApiModelProperty(value = "附加成本金额")
    private BigDecimal additionalCostAmount;

    @ExcelProperty(value = "头程物流费")
    @ApiModelProperty(value = "头程物流费")
    private BigDecimal firstTripFee;

    @ExcelProperty(value = "当地物流费")
    @ApiModelProperty(value = "当地物流费")
    private BigDecimal localLogisticsFee;

    @ExcelProperty(value = "海外仓费用")
    @ApiModelProperty(value = "海外仓费用")
    private BigDecimal abroadWarehouseTaxes;

    @ExcelProperty(value = "海外税费")
    @ApiModelProperty(value = "海外税费")
    private BigDecimal overseaTaxFee;

    @ExcelProperty(value = "销毁成本-采购成本")
    @ApiModelProperty(value = "销毁成本-采购成本")
    private BigDecimal disposePurchaseFee;

    @ExcelProperty(value = "销毁成本-头程物流成本")
    @ApiModelProperty(value = "销毁成本-头程物流成本")
    private BigDecimal disposeLogisticsFee;

    @ExcelProperty(value = "滞销库存利息费")
    @ApiModelProperty(value = "滞销库存利息费")
    private BigDecimal domesticUnsalableInventory;

    @ExcelProperty(value = "预估Profit")
    @ApiModelProperty(value = "预估Profit")
    private BigDecimal preProfit;

    @ExcelProperty(value = "广告费补贴")
    @ApiModelProperty(value = "广告费补贴")
    private BigDecimal advertisingSubsidy;

    @ExcelProperty(value = "鼓励金")
    @ApiModelProperty(value = "鼓励金")
    private BigDecimal incentiveFund;

//    @ExcelProperty(value = "预估利润率")
    @ApiModelProperty(value = "预估利润率")
    private BigDecimal preProfitRate;

    @ExcelProperty(value = "预估利润率百分率")
    @ApiModelProperty(value = "预估利润率百分率")
    private String preProfitRatePercent;

    @ExcelProperty(value = "是否异常")
    @ApiModelProperty(value = "是否异常")
    private String isError;
}
