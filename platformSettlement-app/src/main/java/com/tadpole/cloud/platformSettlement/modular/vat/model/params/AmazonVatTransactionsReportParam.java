package com.tadpole.cloud.platformSettlement.modular.vat.model.params;

import cn.stylefeng.guns.cloud.model.validator.BaseValidatingParam;
import cn.stylefeng.guns.cloud.model.web.request.BaseRequest;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

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
public class AmazonVatTransactionsReportParam extends BaseRequest implements Serializable, BaseValidatingParam {

    private static final long serialVersionUID = 1L;

    /** id--自增 */
    @TableId(value = "ID", type = IdType.AUTO)
    private BigDecimal id;

    /** 店铺编号 */
    @ApiModelProperty("UNIQUE_ACCOUNT_IDENTIFIER")
    private String uniqueAccountIdentifier;

    /** 源仓任务名称 */
    @ApiModelProperty("ORIGINAL_TASK_NAME")
    private String originalTaskName;

    /** 文件存放路径 */
    @ApiModelProperty("FILE_PATH")
    private String filePath;

    /** 账号简称 */
    @ApiModelProperty("SYS_SHOPS_NAME")
    private String sysShopsName;

    /** 审核状态(默认1.未审核	2.已自动审核	3.已手动审核	4.已作废) */
    @ApiModelProperty("VERIFY_STATUS")
    private String verifyStatus;

    /** 期间 */
    @ApiModelProperty("ACTIVITY_PERIOD")
    private String activityPeriod;

    /** 销售渠道 */
    @ApiModelProperty("SALES_CHANNEL")
    private String salesChannel;

    /** 市场 */
    @ApiModelProperty("MARKETPLACE")
    private String marketplace;

    /** 项目类型 */
    @ApiModelProperty("PROGRAM_TYPE")
    private String programType;

    /** 交易类型 */
    @ApiModelProperty("TRANSACTION_TYPE")
    private String transactionType;

    /** 销售订单号 */
    @ApiModelProperty("TRANSACTION_EVENT_ID")
    private String transactionEventId;

    /** 计税日期 */
    @ApiModelProperty("TAX_CALCULATION_DATE")
    private Date taxCalculationDate;

    /** 交易发货日 */
    @ApiModelProperty("TRANSACTION_DEPART_DATE")
    private Date transactionDepartDate;

    /** 交易完成日 */
    @ApiModelProperty("TRANSACTION_COMPLETE_DATE")
    private Date transactionCompleteDate;

    /** SKU */
    @ApiModelProperty("SELLER_SKU")
    private String sellerSku;

    /** ASIN */
    @ApiModelProperty("ASIN")
    private String asin;

    /** 数量 */
    @ApiModelProperty("QTY")
    private BigDecimal qty;

    /** 税率 */
    @ApiModelProperty("PRICE_OF_ITEMS_VAT_RATE_PERCENT")
    private BigDecimal priceOfItemsVatRatePercent;

    /** 原含税售价 */
    @ApiModelProperty("TOTAL_ACTIVITY_VALUE_AMT_VAT_INCL")
    private BigDecimal totalActivityValueAmtVatIncl;

    /** 币种 */
    @ApiModelProperty("TRANSACTION_CURRENCY_CODE")
    private String transactionCurrencyCode;

    /** 发货国 */
    @ApiModelProperty("SALE_DEPART_COUNTRY")
    private String saleDepartCountry;

    /** 目的国 */
    @ApiModelProperty("SALE_ARRIVAL_COUNTRY")
    private String saleArrivalCountry;

    /** 卖家VAT税号 */
    @ApiModelProperty("SELLER_DEPART_COUNTRY_VAT_NUMB")
    private String sellerDepartCountryVatNumb;

    /** 买家VAT税号所在国 */
    @ApiModelProperty("BUYER_VAT_NUMBER_COUNTRY")
    private String buyerVatNumberCountry;

    /** 买家VAT税号 */
    @ApiModelProperty("BUYER_VAT_NUMBER")
    private String buyerVatNumber;

    /** 欧盟出口 */
    @ApiModelProperty("EXPORT_OUTSIDE_EU")
    private String exportOutsideEu;

    /** VAT发票号码 */
    @ApiModelProperty("VAT_INV_NUMBER")
    private String vatInvNumber;

    /** 税收责任方 */
    @ApiModelProperty("TAX_COLLECTION_RESPONSIBILITY")
    private String taxCollectionResponsibility;

    /** 新含税售价 */
    @ApiModelProperty("TOTAL_ACTIVITY_VALUE_AMT_VAT_INCL_NEW")
    private BigDecimal totalActivityValueAmtVatInclNew;

    /** 含税售价减去新含税售价 */
    @ApiModelProperty("PRICE_DIFFERENCE")
    private BigDecimal priceDifference;

    /** DATA RANGE SALES TOTAL */
    @ApiModelProperty("SALES_TOTAL")
    private BigDecimal salesTotal;

    /** 1.是	2.否	3.空（含税售价或新含税售价都为空） */
    @ApiModelProperty("PRICE_ABNORMAL")
    private String priceAbnormal;

    /** 商品计重 */
    @ApiModelProperty("ITEM_WEIGHT")
    private BigDecimal itemWeight;

    /** 商品总计重 */
    @ApiModelProperty("TOTAL_ACTIVITY_WEIGHT")
    private BigDecimal totalActivityWeight;

    /** 商品总价（含税） */
    @ApiModelProperty("PRICE_OF_ITEMS_AMT_VAT_INCL")
    private BigDecimal priceOfItemsAmtVatIncl;

    /** 总商品价（含税） */
    @ApiModelProperty("TOTAL_PRICE_OF_ITEMS_AMT_VAT_I")
    private BigDecimal totalPriceOfItemsAmtVatI;

    /** 运费总价（含税） */
    @ApiModelProperty("SHIP_CHARGE_AMT_VAT_INCL")
    private BigDecimal shipChargeAmtVatIncl;

    /** 运费折扣合计（含税） */
    @ApiModelProperty("PROMO_SHIP_CHARGE_AMT_VAT_INCL")
    private BigDecimal promoShipChargeAmtVatIncl;

    /** 总运费（含税） */
    @ApiModelProperty("TOTAL_SHIP_CHARGE_AMT_VAT_INCL")
    private BigDecimal totalShipChargeAmtVatIncl;

    /** 礼品包装总价（含税） */
    @ApiModelProperty("GIFT_WRAP_AMT_VAT_INCL")
    private BigDecimal giftWrapAmtVatIncl;

    /** 礼品包装折扣合计（含税） */
    @ApiModelProperty("PROMO_GIFT_WRAP_AMT_VAT_INCL")
    private BigDecimal promoGiftWrapAmtVatIncl;

    /** 总礼品包装价（含税） */
    @ApiModelProperty("TOTAL_GIFT_WRAP_AMT_VAT_INCL")
    private BigDecimal totalGiftWrapAmtVatIncl;

    /** 产品税代码 */
    @ApiModelProperty("PRODUCT_TAX_CODE")
    private String productTaxCode;

    /** SYS_SELLER_ID */
    @ApiModelProperty("SYS_SELLER_ID")
    private String sysSellerId;

    /** USER_NAME */
    @ApiModelProperty("USER_NAME")
    private String userName;

    /** 报告文件上传日期(生成上传报告任务的日期) */
    @ApiModelProperty("UPLOAD_DATE")
    private Date uploadDate;

    /** ACTIVITY_TRANSACTION_ID */
    @ApiModelProperty("ACTIVITY_TRANSACTION_ID")
    private String activityTransactionId;

    /** 创建时间--默认值：getdate,首次创建 */
    @ApiModelProperty("CREATE_TIME")
    private Date createTime;

    /** 最后更新时间--默认值：getdate */
    @ApiModelProperty("UPDATE_TIME")
    private Date updateTime;

    /** 最后更新人名称--默认值：当前修改人名称 */
    @ApiModelProperty("UPDATE_PERSON")
    private String updatePerson;

    /** 备注 */
    @ApiModelProperty("REMARK")
    private String remark;

    //店铺集合
    private List sysShopsNames;

    //发货国集合
    private List saleDepartCountrys;

    //目的国集合
    private List saleArrivalCountrys;

    //欧盟出口集合
    private List exportOutsideEus;

    //币种集合
    private List transactionCurrencyCodes;

    //售价异常集合
    private List priceAbnormals;

    //税收责任方集合
    private List taxCollectionResponsibilitys;

    //审核状态集合
    private List verifyStatuss;
}