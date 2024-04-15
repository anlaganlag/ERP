package com.tadpole.cloud.platformSettlement.api.finance.entity;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
* <p>
*
* </p>
*
* @author gal
* @since 2022-04-28
*/
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("CW_LINGXING_DATARANGE")
@ExcelIgnoreUnannotated
public class LingxingDatarange implements Serializable {

    private static final long serialVersionUID = 1L;

    /** ID */
    @TableId(value = "ID", type = IdType.AUTO)
    private BigDecimal id;

    /** 店铺ID	领星ERP对企业已授权店铺的唯一标识 */
    @TableField("SID")
    @ExcelProperty(value ="店铺ID	领星ERP对企业已授权店铺的唯一标识")
    private Long sid;

    /** 1:2B 0:2C */
    @TableField("IS_TO_B")
    @ExcelProperty(value ="1:2B 0:2C")
    private Integer isToB;

    /** CLIENT_TASK的月份 */
    @TableField("REPORT_DATE_MONTH")
    @ExcelProperty(value ="CLIENT_TASK的月份")
    private String reportDateMonth;

    /** 报表行索引 */
    @TableField("REPORT_INDEX")
    @ExcelProperty(value ="报表行索引")
    private BigDecimal reportIndex;

    /** 原本的日期字符串 */
    @TableField("DATE_STR")
    @ExcelProperty(value ="原本的日期字符串")
    private String dateStr;

    /** 当地日期 */
    @TableField("DATE_LOCALE")
    @ExcelProperty(value ="当地日期")
    private String dateLocale;

    /** ISO时间 */
    @TableField("DATE_ISO")
    @ExcelProperty(value ="ISO时间")
    private String dateIso;

    /** 结算编号 */
    @TableField("SETTLEMENT_ID")
    @ExcelProperty(value ="结算编号")
    private String settlementId;

    /** 类型 */
    @TableField("TYPE")
    @ExcelProperty(value ="类型")
    private BigDecimal type;

    /** 类型说明 */
    @TableField("TYPE_STR")
    @ExcelProperty(value ="类型说明")
    private String typeStr;

    /** 订单号 */
    @TableField("ORDER_ID")
    @ExcelProperty(value ="订单号")
    private String orderId;

    /** SKU */
    @TableField("SKU")
    @ExcelProperty(value ="SKU")
    private String sku;

    /** 商品描述 */
    @TableField("DESCRIPTION")
    @ExcelProperty(value ="商品描述")
    private String description;

    /** 商品数量 */
    @TableField("QUANTITY")
    @ExcelProperty(value ="商品数量")
    private BigDecimal quantity;

    /** 销售市场 */
    @TableField("MARKETPLACE")
    @ExcelProperty(value ="销售市场")
    private String marketplace;

    /** 发货方式 */
    @TableField("FULFILLMENT")
    @ExcelProperty(value ="发货方式")
    private String fulfillment;

    /** 城市 */
    @TableField("ORDER_CITY")
    @ExcelProperty(value ="城市")
    private String orderCity;

    /** 州 */
    @TableField("ORDER_STATE")
    @ExcelProperty(value ="州")
    private String orderState;

    /** 邮编 */
    @TableField("ORDER_POSTAL")
    @ExcelProperty(value ="邮编")
    private String orderPostal;

    /** 销售价格 */
    @TableField("PRODUCT_SALES")
    @ExcelProperty(value ="销售价格")
    private BigDecimal productSales;

    /** 销售税 */
    @TableField("PRODUCT_SALES_TAX")
    @ExcelProperty(value ="销售税")
    private BigDecimal productSalesTax;

    /** 币种 */
    @TableField("CURRENCY")
    @ExcelProperty(value ="币种")
    private String currency;

    /** 运费 */
    @TableField("SHIPPING_CREDITS")
    @ExcelProperty(value ="运费")
    private BigDecimal shippingCredits;

    /** 运费税（荷兰，瑞典，沙特，阿联酋无该税费） */
    @TableField("SHIPPING_CREDITS_TAX")
    @ExcelProperty(value ="运费税（荷兰，瑞典，沙特，阿联酋无该税费）")
    private BigDecimal shippingCreditsTax;

    /** 礼品包装费 */
    @TableField("GIFT_WRAP_CREDITS")
    @ExcelProperty(value ="礼品包装费")
    private BigDecimal giftWrapCredits;

    /** 礼品包装税（荷兰，瑞典，沙特，阿联酋无该税费） */
    @TableField("GIFT_WRAP_CREDITS_TAX")
    @ExcelProperty(value ="礼品包装税（荷兰，瑞典，沙特，阿联酋无该税费）")
    private BigDecimal giftWrapCreditsTax;

    /** 促销返点 */
    @TableField("PROMOTIONAL_REBATES")
    @ExcelProperty(value ="促销返点")
    private BigDecimal promotionalRebates;

    /** 促销返点税（荷兰，瑞典，沙特，阿联酋无该税费） */
    @TableField("PROMOTIONAL_REBATES_TAX")
    @ExcelProperty(value ="促销返点税（荷兰，瑞典，沙特，阿联酋无该税费）")
    private BigDecimal promotionalRebatesTax;

    /** 销售税（仅荷兰，瑞典，沙特，阿联酋有该税费） */
    @TableField("SALES_TAX_COLLECTED")
    @ExcelProperty(value ="销售税（仅荷兰，瑞典，沙特，阿联酋有该税费）")
    private BigDecimal salesTaxCollected;

    /** 市场税 */
    @TableField("MARKETPLACE_FACILITATOR_TAX")
    @ExcelProperty(value ="市场税")
    private BigDecimal marketplaceFacilitatorTax;

    /** 平台费（佣金） */
    @TableField("SELLING_FEES")
    @ExcelProperty(value ="平台费（佣金）")
    private BigDecimal sellingFees;

    /** FBA发货费 */
    @TableField("FBA_FEES")
    @ExcelProperty(value ="FBA发货费")
    private BigDecimal fbaFees;

    /** 其他交易费 */
    @TableField("OTHER_TRANSACTION_FEES")
    @ExcelProperty(value ="其他交易费")
    private BigDecimal otherTransactionFees;

    /** 其他费 */
    @TableField("OTHER")
    @ExcelProperty(value ="其他费")
    private BigDecimal other;

    /** 总计 */
    @TableField("TOTAL")
    @ExcelProperty(value ="总计")
    private BigDecimal total;

    /** 创建时间 */
    @TableField("CREATE_DATE")
    @ExcelProperty(value ="创建时间")
    private Date createDate;

    /** 更新时间 */
    @TableField("UPDATE_DATE")
    @ExcelProperty(value ="更新时间")
    private Date updateDate;

    @TableField("DATE_TIME")
    private Date dateTime;

    /** 账号 */
    @TableField("SHOP_NAME")
    private String shopName;

    /** 站点 */
    @TableField("SITE")
    private String site;
}