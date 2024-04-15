package com.tadpole.cloud.platformSettlement.api.finance.model.params;

import cn.stylefeng.guns.cloud.model.validator.BaseValidatingParam;
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
 * @author: ty
 * @description: 所有订单明细表请求参数
 * @date: 2022/5/6
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel
public class AmazonOrderDetailParam implements Serializable, BaseValidatingParam {

    private static final long serialVersionUID = 1L;

    @TableId(value = "ID", type = IdType.AUTO)
    private BigDecimal id;

    /** 订单号 */
    @ApiModelProperty("订单号")
    private String amazonOrderId;

    /** 订单商品编码 */
    @ApiModelProperty("订单商品编码")
    private String amazonOrderItemCode;

    @ApiModelProperty("PURCHASE_DATE")
    private Date purchaseDate;

    /** ASIN */
    @ApiModelProperty("ASIN")
    private String asin;

    /** SKU */
    @ApiModelProperty("SKU")
    private String sku;

    /** 商品状态 */
    @ApiModelProperty("商品状态")
    private String itemStatus;

    /** 数量 */
    @ApiModelProperty("数量")
    private Long quantity;

    /** 商品金额 */
    @ApiModelProperty("商品金额")
    private BigDecimal principal;

    /** 商品税 */
    @ApiModelProperty("商品税")
    private BigDecimal tax;

    /** 交易货币 */
    @ApiModelProperty("交易货币")
    private String currency;

    /** 运费 */
    @ApiModelProperty("运费")
    private BigDecimal shipping;

    /** 运费税 */
    @ApiModelProperty("运费税")
    private BigDecimal shippingTax;

    /** 礼品包装 */
    @ApiModelProperty("礼品包装")
    private BigDecimal giftwrap;

    /** 礼品包装税 */
    @ApiModelProperty("礼品包装税")
    private BigDecimal giftwrapTax;

    /** 促销列表 */
    @ApiModelProperty("促销列表")
    private String promotionIds;

    /** 商品促销折扣 */
    @ApiModelProperty("商品促销折扣")
    private BigDecimal itemPromotionDiscount;

    /** 运费促销折扣 */
    @ApiModelProperty("运费促销折扣")
    private BigDecimal shipPromotionDiscount;

    /** 站点 */
    @ApiModelProperty("站点")
    private String sysSite;

    /** 账号简称 */
    @ApiModelProperty("账号简称")
    private String sysShopsName;

    /** 入库时间 */
    @ApiModelProperty("入库时间")
    private Date createTime;

    /** 报告日期 */
    @ApiModelProperty("报告日期")
    private Date uploadDate;

    /** 商品销售平台(亚马逊代发) */
    @ApiModelProperty("商品销售平台(亚马逊代发)")
    private String platform;

}