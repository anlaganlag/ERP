package com.tadpole.cloud.platformSettlement.modular.vat.entity;

import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

/**
 * <p>
 * 源报告表
 * </p>
 *
 * @author cyt
 * @since 2022-08-06
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("AMAZON_VAT_TRANSACTIONS_REPORT")
@ExcelIgnoreUnannotated
public class AmazonVatTransactionsReport implements Serializable {

    private static final long serialVersionUID = 1L;

    /** id--自增 */
    @TableId(value = "ID", type = IdType.AUTO)
    private BigDecimal id;

    /** 店铺编号 */
    @TableField("UNIQUE_ACCOUNT_IDENTIFIER")
    private String uniqueAccountIdentifier;

    /** 源仓任务名称 */
    @TableField("ORIGINAL_TASK_NAME")
    private String originalTaskName;

    /** 文件存放路径 */
    @TableField("FILE_PATH")
    private String filePath;

    /** 账号简称 */
    @TableField("SYS_SHOPS_NAME")
    private String sysShopsName;

    /** 站点 */
    @TableField("SYS_SITE")
    private String sysSite;

    /** 审核状态(默认1.未审核	2.已自动审核	3.已手动审核	4.已作废) */
    @TableField("VERIFY_STATUS")
    private BigDecimal verifyStatus;

    /** 期间 */
    @TableField("ACTIVITY_PERIOD")
    private String activityPeriod;

    /** 销售渠道 */
    @TableField("SALES_CHANNEL")
    private String salesChannel;

    /** 市场 */
    @TableField("MARKETPLACE")
    private String marketplace;

    /** 项目类型 */
    @TableField("PROGRAM_TYPE")
    private String programType;

    /** 交易类型 */
    @TableField("TRANSACTION_TYPE")
    private String transactionType;

    /** 销售订单号 */
    @TableField("TRANSACTION_EVENT_ID")
    private String transactionEventId;

    /** 计税日期 */
    @TableField("TAX_CALCULATION_DATE")
    private Date taxCalculationDate;

    /** 交易发货日 */
    @TableField("TRANSACTION_DEPART_DATE")
    private Date transactionDepartDate;

    /** 交易完成日 */
    @TableField("TRANSACTION_COMPLETE_DATE")
    private Date transactionCompleteDate;

    /** SKU */
    @TableField("SELLER_SKU")
    private String sellerSku;

    /** ASIN */
    @TableField("ASIN")
    private String asin;

    /** 数量 */
    @TableField("QTY")
    private BigDecimal qty;

    /** 税率 */
    @TableField("PRICE_OF_ITEMS_VAT_RATE_PERCENT")
    private BigDecimal priceOfItemsVatRatePercent;

    /** 原含税售价 */
    @TableField("TOTAL_ACTIVITY_VALUE_AMT_VAT_INCL")
    private BigDecimal totalActivityValueAmtVatIncl;

    /** 币种 */
    @TableField("TRANSACTION_CURRENCY_CODE")
    private String transactionCurrencyCode;

    /** 发货国 */
    @TableField("SALE_DEPART_COUNTRY")
    private String saleDepartCountry;

    /** 目的国 */
    @TableField("SALE_ARRIVAL_COUNTRY")
    private String saleArrivalCountry;

    /** 卖家VAT税号 */
    @TableField("SELLER_DEPART_COUNTRY_VAT_NUMB")
    private String sellerDepartCountryVatNumb;

    /** 买家VAT税号所在国 */
    @TableField("BUYER_VAT_NUMBER_COUNTRY")
    private String buyerVatNumberCountry;

    /** 买家VAT税号 */
    @TableField("BUYER_VAT_NUMBER")
    private String buyerVatNumber;

    /** 欧盟出口 */
    @TableField("EXPORT_OUTSIDE_EU")
    private String exportOutsideEu;

    /** VAT发票号码 */
    @TableField("VAT_INV_NUMBER")
    private String vatInvNumber;

    /** 税收责任方 */
    @TableField("TAX_COLLECTION_RESPONSIBILITY")
    private String taxCollectionResponsibility;

    /** 新含税售价 */
    @TableField("TOTAL_ACTIVITY_VALUE_AMT_VAT_INCL_NEW")
    private BigDecimal totalActivityValueAmtVatInclNew;

    /** 含税售价减去新含税售价 */
    @TableField("PRICE_DIFFERENCE")
    private BigDecimal priceDifference;

    /** DATA RANGE SALES TOTAL */
    @TableField("SALES_TOTAL")
    private BigDecimal salesTotal;

    /** 1.是	2.否	3.空（含税售价或新含税售价都为空） */
    @TableField("PRICE_ABNORMAL")
    private String priceAbnormal;

    /** 商品计重 */
    @TableField("ITEM_WEIGHT")
    private BigDecimal itemWeight;

    /** 商品总计重 */
    @TableField("TOTAL_ACTIVITY_WEIGHT")
    private BigDecimal totalActivityWeight;

    /** 商品总价（含税） */
    @TableField("PRICE_OF_ITEMS_AMT_VAT_INCL")
    private BigDecimal priceOfItemsAmtVatIncl;

    /** 总商品价（含税） */
    @TableField("TOTAL_PRICE_OF_ITEMS_AMT_VAT_INCL")
    private BigDecimal totalPriceOfItemsAmtVatIncl;

    /** 运费总价（含税） */
    @TableField("SHIP_CHARGE_AMT_VAT_INCL")
    private BigDecimal shipChargeAmtVatIncl;

    /** 运费折扣合计（含税） */
    @TableField("PROMO_SHIP_CHARGE_AMT_VAT_INCL")
    private BigDecimal promoShipChargeAmtVatIncl;

    /** 总运费（含税） */
    @TableField("TOTAL_SHIP_CHARGE_AMT_VAT_INCL")
    private BigDecimal totalShipChargeAmtVatIncl;

    /** 礼品包装总价（含税） */
    @TableField("GIFT_WRAP_AMT_VAT_INCL")
    private BigDecimal giftWrapAmtVatIncl;

    /** 礼品包装折扣合计（含税） */
    @TableField("PROMO_GIFT_WRAP_AMT_VAT_INCL")
    private BigDecimal promoGiftWrapAmtVatIncl;

    /** 总礼品包装价（含税） */
    @TableField("TOTAL_GIFT_WRAP_AMT_VAT_INCL")
    private BigDecimal totalGiftWrapAmtVatIncl;

    /** 产品税代码 */
    @TableField("PRODUCT_TAX_CODE")
    private String productTaxCode;

    /** SYS_SELLER_ID */
    @TableField("SYS_SELLER_ID")
    private String sysSellerId;

    /** USER_NAME */
    @TableField("USER_NAME")
    private String userName;

    /** 报告文件上传日期(生成上传报告任务的日期) */
    @TableField("UPLOAD_DATE")
    private Date uploadDate;

    /** ACTIVITY_TRANSACTION_ID */
    @TableField("ACTIVITY_TRANSACTION_ID")
    private String activityTransactionId;

    /** 创建时间--默认值：getdate,首次创建 */
    @TableField("CREATE_TIME")
    private Date createTime;

    /** 最后更新时间--默认值：getdate */
    @TableField("UPDATE_TIME")
    private Date updateTime;

    /** 最后更新人名称--默认值：当前修改人名称 */
    @TableField("UPDATE_PERSON")
    private String updatePerson;

    /** 汇率后金额，按发货国，UK目标币，其它站点目标币GBP */
    @TableField("EXCHANGE_RATE_AMOUNT")
    private String exchangeRateAmount;

    /** 汇率后金额，按目的国，UK目标币，其它站点目标币GBP */
    @TableField("EXCHANGE_RATE_AMOUNT_MDG")
    private String exchangeRateAmountMdg;

    /** 备注 */
    @TableField("REMARK")
    private String remark;
}