package com.tadpole.cloud.platformSettlement.api.finance.entity;

import com.alibaba.excel.annotation.ExcelProperty;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
* <p>
* 结算报告
* </p>
*
* @author gal
* @since 2021-12-24
*/
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("RP_SETTLEMENT_REPORT_FINAL")
public class SettlementReportFinal implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "ID", type = IdType.ASSIGN_ID)
    private String id;

    /** 会计期间 */
    @ExcelProperty(value="会计期间")
    @TableField("FISCAL_PERIOD")
    private String fiscalPeriod;

    /** 报告类型 */
    @ExcelProperty(value="报告类型")
    @TableField("REPORT_TYPE")
    private String reportType;

    /** 收入确认类型 */
    @ExcelProperty(value="收入确认类型")
    @TableField("INCOME_TYPE")
    private String incomeType;

    @TableField("platform")
    @ExcelProperty(value="账号")
    @ApiModelProperty(value ="平台" )
    private String platform = "Amazon";

    /** 账号 */
    @ExcelProperty(value="账号")
    @TableField("SHOP_NAME")
    private String shopName;

    /** 站点 */
    @ExcelProperty(value="站点")
    @TableField("SITE")
    private String site;

    /** 原币 */
    @ExcelProperty(value="核算币别")
    @TableField("ACCOUNTING_CURRENCY")
    private String accountingCurrency;

    @TableField("SKU")
    @ExcelProperty(value="SKU")
    private String sku;

    @TableField("DEPARTMENT")
    @ExcelProperty(value="事业部")
    private String department;

    @TableField("TEAM")
    @ExcelProperty(value="Team")
    private String team;

    @TableField("MATERIAL_CODE")
    @ExcelProperty(value="物料编码")
    private String materialCode;

//    @TableField("category")
//    @ExcelProperty(value="类目")
//    private String category;

    @TableField("PRODUCT_TYPE")
    @ExcelProperty(value="运营大类")
    private String productType;


//    @TableField("PRODUCT_NAME")
//    @ExcelProperty(value="产品名称")
//    private String productName;
//
//    @ApiModelProperty("款式")
//    @ExcelProperty(value ="款式")
//    private String style;
//
//    @ApiModelProperty("主材料")
//    @ExcelProperty(value ="主材料")
//    private String mainMaterial;
//
//    @ApiModelProperty("图案")
//    @ExcelProperty(value ="图案")
//    private String design;
//
//    @ApiModelProperty("适用品牌或对象")
//    @ExcelProperty(value ="适用品牌或对象")
//    private String fitBrand;
//
//    @ApiModelProperty("型号")
//    @ExcelProperty(value ="型号")
//    private String model;
//
//    @ApiModelProperty("公司品牌")
//    @ExcelProperty(value ="公司品牌")
//    private String companyBrand;
//
//    @ApiModelProperty("颜色")
//    @ExcelProperty(value ="颜色")
//    private String color;
//
//    @ApiModelProperty("尺码")
//    @ExcelProperty(value ="尺码")
//    private String sizes;
//
//    @ApiModelProperty("包装数量")
//    @ExcelProperty(value ="包装数量")
//    private String packing;
//
//    @ApiModelProperty("版本")
//    @ExcelProperty(value ="版本")
//    private String version;
//
//    @ApiModelProperty("适用机型")
//    @ExcelProperty(value ="适用机型")
//    private String type;

//    @ApiModelProperty("款式二级标签")
//    @ExcelProperty(value ="款式二级标签")
//    private String styleSecondLabel;



    @TableField("SALES_BRAND")
    @ExcelProperty(value="销售品牌")
    private String salesBrand;


//    @ApiModelProperty("BUYER")
//    @ExcelProperty(value = "采购员")
//    private String buyer;

//    @ApiModelProperty("DEVELOPER")
//    @ExcelProperty(value = "开发人员")
//    private String developer;


//    @ApiModelProperty("ACCOUNT_DATE")
//    @ExcelProperty(value = "核算日期")
//    private String accountDate;


    @TableField("COST_AUXILIARY_DESCRIPTION")
    @ExcelProperty(value="费用类型辅助说明")
    private String costAuxiliaryDescription;

    @TableField("VOLUME_NORMAL")
    @ExcelProperty(value="Volume(正常发货)")
    private BigDecimal volumeNormal;

    @TableField("VOLUME_REISSUE")
    @ExcelProperty(value="Volume(补发货)")
    private BigDecimal volumeReissue;

    @TableField("VOLUME_BILL_QUANTITY")
    @ExcelProperty(value="Volume(测评数量)")
    private BigDecimal volumeBillQuantity;

    @TableField("VOLUME_RETURN")
    @ExcelProperty(value ="Volume（退货）")
    private BigDecimal volumeReturn;

    /** Sales_excluding_tax */
    @ExcelProperty(value="Sales_excluding_tax")
    @TableField("SALES_EXCLUDING_TAX")
    private BigDecimal salesExcludingTax;

    @TableField("TAX")
    @ExcelProperty(value="Tax")
    private BigDecimal tax;

    /** sales_promotion */
    @ExcelProperty(value="Sales_Promotion")
    @TableField("Sales_Promotion")
    private BigDecimal salesPromotion;

    /** refund_total */
    @ExcelProperty(value="Refund")
    @TableField("Refund")
    private BigDecimal refund;

    @TableField("REFUND_PROMOTION")
    @ExcelProperty(value="Refund_Promotion")
    private BigDecimal refundPromotion;

    /** giftwarp_shipping */
    @ExcelProperty(value="Giftwarp_Shipping")
    @TableField("GIFTWARP_SHIPPING")
    private BigDecimal giftwarpShipping;

    @TableField("NET_SALES")
    @ExcelProperty(value ="Net_Sales")
    private BigDecimal netSales;

    /** commission_total */
    @ExcelProperty(value="Commission")
    @TableField("Commission")
    private BigDecimal commission;

    @TableField("REFUND_COMMISSION")
    @ExcelProperty(value="Refund_Commission")
    private BigDecimal refundCommission;

    /** goodwill */
    @ExcelProperty(value="Goodwill")
    @TableField("GOODWILL")
    private BigDecimal goodwill;

    /** Amazon Fees */
    @ExcelProperty(value="Amazon_Fees")
    @TableField("AMAZON_FEES")
    private BigDecimal amazonFees;

    /** storage_fee */
    @ExcelProperty(value="Storage_fee")
    @TableField("STORAGE_FEE")
    private BigDecimal storageFee;

    /** reimbursement */
    @ExcelProperty(value="Reimbursement")
    @TableField("Reimbursement")
    private BigDecimal reimbursement;

    /** other */
    @ExcelProperty(value="Other")
    @TableField("Other")
    private BigDecimal other;

    /** withheld_tax */
    @ExcelProperty(value="Withheld_tax")
    @TableField("Withheld_tax")
    private BigDecimal withheldTax;

    @TableField("DISPOSE_FEE")
    @ExcelProperty(value="销毁费")
    private BigDecimal disposeFee;

    /** removal deal */
    @ExcelProperty(value="移除费")
    @TableField("REMOVAL_DEAL")
    private BigDecimal removalDeal;




    @TableField("COLLECTION")
    @ExcelProperty(value="回款")
    private BigDecimal collection;

    /** advertising */
    @ExcelProperty(value="Advertising")
    @TableField("Advertising")
    private BigDecimal advertising;

    @TableField("KINDLE_ADVERTISING_FEE")
    @ExcelProperty(value="Kindle广告费")
    private BigDecimal kindleAdvertisingFee;

    @TableField("OTHER_ADVERTISEMENTS")
    @ExcelProperty(value="其它站外广告")
    private BigDecimal otherAdvertisements;

    @TableField("BRUSHING_VALUE")
    @ExcelProperty(value="测评货值")
    private BigDecimal brushingValue;

    @TableField("BRUSHING_SERVICE_CHARGE")
    @ExcelProperty(value="测评手续费")
    private BigDecimal brushingServiceCharge;

    @ApiModelProperty("COST_ACCOUNTING_FEE")
    @ExcelProperty(value= "成本核算金额")
    private BigDecimal costAccountingFee;

    @ExcelProperty(value = "附加成本金额")
    @ApiModelProperty(value = "附加成本金额")
    private BigDecimal additionalCostAmount;

    @ExcelProperty(value = "头程物流费")
    @ApiModelProperty(value = "头程物流费")
    private BigDecimal firstTripFee;

    @TableField("LOCAL_LOGISTICS_FEE")
    @ExcelProperty(value="当地物流费")
    private BigDecimal localLogisticsFee;

    @TableField("OVERSEAS_WAREHOUSE_FEE")
    @ExcelProperty(value="海外仓费用")
    private BigDecimal overseasWarehouseFee;

    @TableField("OVERSEA_TAX_FEE")
    @ApiModelProperty(value ="海外税费")
    private BigDecimal overseaTaxFee;


    @TableField("DISPOSE_PURCHASE_FEE")
    @ExcelProperty(value="销毁成本-采购成本")
    private BigDecimal disposePurchaseFee;

    @TableField("DISPOSE_LOGISTICS_FEE")
    @ExcelProperty(value="销毁成本-头程物流成本")
    private BigDecimal disposeLogisticsFee;

    @ApiModelProperty("DOMESTIC_UNSALABLE_INVENTORY")
    @ExcelProperty(value= "滞销库存利息费")
    private BigDecimal domesticUnsalableInventory;

    @TableField("MOLD_OPENING_COST")
    @ExcelProperty(value= "开模费用")
    private BigDecimal moldOpeningCost;



    @TableField("PROFIT")
    @ExcelProperty(value ="Profit")
    private BigDecimal profit;

    @TableField("ADVERTISING_SUBSIDY")
    @ApiModelProperty(value ="广告费补贴")
    private BigDecimal advertisingSubsidy;

    @TableField("INCENTIVE_FUND")
    @ApiModelProperty(value ="鼓励金")
    private BigDecimal incentiveFund;





    @TableField("CONFIRM_STATUS")
    @ExcelProperty(value="确认状态")
    private BigDecimal confirmStatus;

    //    @TableField("CONFIRM_DATE")
//    @ExcelProperty(value="确认日期")
    private Date confirmDate;

    //    @TableField("CONFIRM_BY")
//    @ExcelProperty(value="确认人员")
    private String confirmBy;



    @TableField("REPORT_ID")
    @ApiModelProperty(value ="报告ID" )
    private String reportId;


    @TableField("REVENUE_RATIO")
    @ApiModelProperty(value ="销量占比")
    private BigDecimal revenueRatio;


    @TableField("PEOPLE_NUM")
    @ApiModelProperty(value ="人数")
    private BigDecimal peopleNum;

    @TableField("PEOPLE_COST")
    @ApiModelProperty(value ="人工成本")
    private BigDecimal peopleCost;



    /**  */
    @ApiModelProperty("")
    @TableField("CREATE_TIME")
    private Date createTime ;

    /**  */
    @ApiModelProperty("")
    @TableField("CREATE_BY")
    private String createBy ;

    @ApiModelProperty("")
    @TableField("UPDATE_TIME")
    private Date updateTime ;

    /**  */
    @ApiModelProperty("")
    @TableField("UPDATE_BY")
    private String updateBy ;



}