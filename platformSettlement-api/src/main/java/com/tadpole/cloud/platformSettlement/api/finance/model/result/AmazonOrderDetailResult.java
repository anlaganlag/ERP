package com.tadpole.cloud.platformSettlement.api.finance.model.result;

import cn.stylefeng.guns.cloud.model.validator.BaseValidatingParam;
import com.alibaba.excel.annotation.ExcelProperty;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>
 * 所有订单明细表
 * </p>
 *
 * @author gal
 * @since 2022-05-06
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel
public class AmazonOrderDetailResult implements Serializable, BaseValidatingParam {

    private static final long serialVersionUID = 1L;

    @TableId(value = "ID", type = IdType.AUTO)
    private BigDecimal id;

    /** 订单号 */
    @ApiModelProperty("AMAZON_ORDER_ID")
    @ExcelProperty(value ="订单号")
    private String amazonOrderId;

    /** 订单商品编码 */
    @ApiModelProperty("AMAZON_ORDER_ITEM_CODE")
    @ExcelProperty(value ="订单商品编码")
    private String amazonOrderItemCode;

    @ApiModelProperty("PURCHASE_DATE")
    @ExcelProperty(value ="")
    private Date purchaseDate;

    /** ASIN */
    @ApiModelProperty("ASIN")
    @ExcelProperty(value ="ASIN")
    private String asin;

    /** SKU */
    @ApiModelProperty("SKU")
    @ExcelProperty(value ="SKU")
    private String sku;

    /** 商品状态 */
    @ApiModelProperty("ITEM_STATUS")
    @ExcelProperty(value ="商品状态")
    private String itemStatus;

    /** 数量 */
    @ApiModelProperty("QUANTITY")
    @ExcelProperty(value ="数量")
    private Long quantity;

    /** 商品金额 */
    @ApiModelProperty("PRINCIPAL")
    @ExcelProperty(value ="商品金额")
    private BigDecimal principal;

    /** 商品税 */
    @ApiModelProperty("TAX")
    @ExcelProperty(value ="商品税")
    private BigDecimal tax;

    /** 交易货币 */
    @ApiModelProperty("CURRENCY")
    @ExcelProperty(value ="交易货币")
    private String currency;

    /** 运费 */
    @ApiModelProperty("SHIPPING")
    @ExcelProperty(value ="运费")
    private BigDecimal shipping;

    /** 运费税 */
    @ApiModelProperty("SHIPPING_TAX")
    @ExcelProperty(value ="运费税")
    private BigDecimal shippingTax;

    /** 礼品包装 */
    @ApiModelProperty("GIFTWRAP")
    @ExcelProperty(value ="礼品包装")
    private BigDecimal giftwrap;

    /** 礼品包装税 */
    @ApiModelProperty("GIFTWRAP_TAX")
    @ExcelProperty(value ="礼品包装税")
    private BigDecimal giftwrapTax;

    /** 促销列表 */
    @ApiModelProperty("PROMOTION_IDS")
    @ExcelProperty(value ="促销列表")
    private String promotionIds;

    /** 商品促销折扣 */
    @ApiModelProperty("ITEM_PROMOTION_DISCOUNT")
    @ExcelProperty(value ="商品促销折扣")
    private BigDecimal itemPromotionDiscount;

    /** 运费促销折扣 */
    @ApiModelProperty("SHIP_PROMOTION_DISCOUNT")
    @ExcelProperty(value ="运费促销折扣")
    private BigDecimal shipPromotionDiscount;

    /** 站点 */
    @ApiModelProperty("SYS_SITE")
    @ExcelProperty(value ="站点")
    private String sysSite;

    /** 账号简称 */
    @ApiModelProperty("SYS_SHOPS_NAME")
    @ExcelProperty(value ="账号简称")
    private String sysShopsName;

    /** 入库时间 */
    @ApiModelProperty("CREATE_TIME")
    @ExcelProperty(value ="入库时间")
    private Date createTime;

    /** 报告日期 */
    @ApiModelProperty("UPLOAD_DATE")
    @ExcelProperty(value ="报告日期")
    private Date uploadDate;

    /** 商品销售平台(亚马逊代发) */
    @ApiModelProperty("PLATFORM")
    @ExcelProperty(value ="商品销售平台(亚马逊代发)")
    private String platform;

}