package com.tadpole.cloud.platformSettlement.api.finance.entity;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
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
 * @author ty
 * @since 2022-05-06
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("AMAZON_ORDER_DETAIL")
@ApiModel(value="AmazonOrderDetail对象", description="所有订单明细表")
@ExcelIgnoreUnannotated
public class AmazonOrderDetail implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "ID", type = IdType.AUTO)
    private BigDecimal id;

    @ApiModelProperty(value = "订单号")
    private String amazonOrderId;

    @ApiModelProperty(value = "订单商品编码")
    private String amazonOrderItemCode;

    private Date purchaseDate;

    @ApiModelProperty(value = "ASIN")
    private String asin;

    @ApiModelProperty(value = "SKU")
    private String sku;

    @ApiModelProperty(value = "商品状态")
    private String itemStatus;

    @ApiModelProperty(value = "数量")
    private Long quantity;

    @ApiModelProperty(value = "商品金额")
    private BigDecimal principal;

    @ApiModelProperty(value = "商品税")
    private BigDecimal tax;

    @ApiModelProperty(value = "交易货币")
    private String currency;

    @ApiModelProperty(value = "运费")
    private BigDecimal shipping;

    @ApiModelProperty(value = "运费税")
    private BigDecimal shippingTax;

    @ApiModelProperty(value = "礼品包装")
    private BigDecimal giftwrap;

    @ApiModelProperty(value = "礼品包装税")
    private BigDecimal giftwrapTax;

    @ApiModelProperty(value = "促销列表")
    private String promotionIds;

    @ApiModelProperty(value = "商品促销折扣")
    private BigDecimal itemPromotionDiscount;

    @ApiModelProperty(value = "运费促销折扣")
    private BigDecimal shipPromotionDiscount;

    @ApiModelProperty(value = "站点")
    private String sysSite;

    @ApiModelProperty(value = "账号简称")
    private String sysShopsName;

    @ApiModelProperty(value = "入库时间")
    private Date createTime;

    @ApiModelProperty(value = "报告日期")
    private Date uploadDate;

    @ApiModelProperty(value = "商品销售平台(亚马逊代发)")
    private String platform;
}