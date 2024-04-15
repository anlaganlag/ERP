package com.tadpole.cloud.platformSettlement.api.finance.model.result;

import cn.stylefeng.guns.cloud.model.validator.BaseValidatingParam;
import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
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
@ExcelIgnoreUnannotated
@TableName("CW_LINGXING_DATARANGE")
public class LingxingDatarangeResult implements Serializable, BaseValidatingParam {

    private static final long serialVersionUID = 1L;

    /** ID */
    @TableId(value = "ID", type = IdType.AUTO)
    private BigDecimal id;

    /** 店铺ID	领星ERP对企业已授权店铺的唯一标识 */
    @ApiModelProperty("SID")
    @ExcelProperty(value ="店铺ID	领星ERP对企业已授权店铺的唯一标识")
    private BigDecimal sid;

    /** 1:2B 0:2C */
    @ApiModelProperty("IS_TO_B")
    @ExcelProperty(value ="1:2B 0:2C")
    private Integer isToB;

    /** CLIENT_TASK的月份 */
    @ApiModelProperty("REPORT_DATE_MONTH")
    @ExcelProperty(value ="CLIENT_TASK的月份")
    private String reportDateMonth;

    /** 报表行索引 */
    @ApiModelProperty("REPORT_INDEX")
    @ExcelProperty(value ="报表行索引")
    private BigDecimal reportIndex;

    /** 原本的日期字符串 */
    @ApiModelProperty("DATE_STR")
    @ExcelProperty(value ="原本的日期字符串")
    private String dateStr;

    /** 当地日期 */
    @ApiModelProperty("DATE_LOCALE")
    @ExcelProperty(value ="当地日期")
    private String dateLocale;

    /** ISO时间 */
    @ApiModelProperty("DATE_ISO")
    @ExcelProperty(value ="ISO时间")
    private String dateIso;

    /** 结算编号 */
    @ApiModelProperty("SETTLEMENT_ID")
    @ExcelProperty(value ="结算编号")
    private String settlementId;

    /** 类型 */
    @ApiModelProperty("TYPE")
    @ExcelProperty(value ="类型")
    private BigDecimal type;

    /** 类型说明 */
    @ApiModelProperty("TYPE_STR")
    @ExcelProperty(value ="类型说明")
    private String typeStr;

    /** 订单号 */
    @ApiModelProperty("ORDER_ID")
    @ExcelProperty(value ="订单号")
    private String orderId;

    /** SKU */
    @ApiModelProperty("SKU")
    @ExcelProperty(value ="SKU")
    private String sku;

    /** 商品描述 */
    @ApiModelProperty("DESCRIPTION")
    @ExcelProperty(value ="商品描述")
    private String description;

    /** 商品数量 */
    @ApiModelProperty("QUANTITY")
    @ExcelProperty(value ="商品数量")
    private BigDecimal quantity;

    /** 销售市场 */
    @ApiModelProperty("MARKETPLACE")
    @ExcelProperty(value ="销售市场")
    private String marketplace;

    /** 发货方式 */
    @ApiModelProperty("FULFILLMENT")
    @ExcelProperty(value ="发货方式")
    private String fulfillment;

    /** 城市 */
    @ApiModelProperty("ORDER_CITY")
    @ExcelProperty(value ="城市")
    private String orderCity;

    /** 州 */
    @ApiModelProperty("ORDER_STATE")
    @ExcelProperty(value ="州")
    private String orderState;

    /** 邮编 */
    @ApiModelProperty("ORDER_POSTAL")
    @ExcelProperty(value ="邮编")
    private String orderPostal;

    /** 销售价格 */
    @ApiModelProperty("PRODUCT_SALES")
    @ExcelProperty(value ="销售价格")
    private BigDecimal productSales;

    /** 销售税 */
    @ApiModelProperty("PRODUCT_SALES_TAX")
    @ExcelProperty(value ="销售税")
    private BigDecimal productSalesTax;

    /** 币种 */
    @ApiModelProperty("CURRENCY")
    @ExcelProperty(value ="币种")
    private String currency;

    /** 运费 */
    @ApiModelProperty("SHIPPING_CREDITS")
    @ExcelProperty(value ="运费")
    private BigDecimal shippingCredits;

    /** 运费税（荷兰，瑞典，沙特，阿联酋无该税费） */
    @ApiModelProperty("SHIPPING_CREDITS_TAX")
    @ExcelProperty(value ="运费税（荷兰，瑞典，沙特，阿联酋无该税费）")
    private BigDecimal shippingCreditsTax;

    /** 礼品包装费 */
    @ApiModelProperty("GIFT_WRAP_CREDITS")
    @ExcelProperty(value ="礼品包装费")
    private BigDecimal giftWrapCredits;

    /** 礼品包装税（荷兰，瑞典，沙特，阿联酋无该税费） */
    @ApiModelProperty("GIFT_WRAP_CREDITS_TAX")
    @ExcelProperty(value ="礼品包装税（荷兰，瑞典，沙特，阿联酋无该税费）")
    private BigDecimal giftWrapCreditsTax;

    /** 促销返点 */
    @ApiModelProperty("PROMOTIONAL_REBATES")
    @ExcelProperty(value ="促销返点")
    private BigDecimal promotionalRebates;

    /** 促销返点税（荷兰，瑞典，沙特，阿联酋无该税费） */
    @ApiModelProperty("PROMOTIONAL_REBATES_TAX")
    @ExcelProperty(value ="促销返点税（荷兰，瑞典，沙特，阿联酋无该税费）")
    private BigDecimal promotionalRebatesTax;

    /** 销售税（仅荷兰，瑞典，沙特，阿联酋有该税费） */
    @ApiModelProperty("SALES_TAX_COLLECTED")
    @ExcelProperty(value ="销售税（仅荷兰，瑞典，沙特，阿联酋有该税费）")
    private BigDecimal salesTaxCollected;

    /** 市场税 */
    @ApiModelProperty("MARKETPLACE_FACILITATOR_TAX")
    @ExcelProperty(value ="市场税")
    private BigDecimal marketplaceFacilitatorTax;

    /** 平台费（佣金） */
    @ApiModelProperty("SELLING_FEES")
    @ExcelProperty(value ="平台费（佣金）")
    private BigDecimal sellingFees;

    /** FBA发货费 */
    @ApiModelProperty("FBA_FEES")
    @ExcelProperty(value ="FBA发货费")
    private BigDecimal fbaFees;

    /** 其他交易费 */
    @ApiModelProperty("OTHER_TRANSACTION_FEES")
    @ExcelProperty(value ="其他交易费")
    private BigDecimal otherTransactionFees;

    /** 其他费 */
    @ApiModelProperty("OTHER")
    @ExcelProperty(value ="其他费")
    private BigDecimal other;

    /** 总计 */
    @ApiModelProperty("TOTAL")
    @ExcelProperty(value ="总计")
    private BigDecimal total;

    /** 创建时间 */
    @ApiModelProperty("CREATE_DATE")
    @ExcelProperty(value ="创建时间")
    private Date createDate;

    /** 更新时间 */
    @ApiModelProperty("UPDATE_DATE")
    @ExcelProperty(value ="更新时间")
    private Date updateDate;

    @ApiModelProperty("DATE_TIME")
    private Date dateTime;

    private String ReportType;

    private String platformName;

    private String shopName;

    private String site;
}