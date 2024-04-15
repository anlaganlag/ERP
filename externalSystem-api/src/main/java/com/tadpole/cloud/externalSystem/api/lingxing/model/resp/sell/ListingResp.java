package com.tadpole.cloud.externalSystem.api.lingxing.model.resp.sell;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @author: ty
 * @description: 查询亚马逊Listing响应参数
 * @date: 2023/6/6
 */
@Data
@ApiModel(value="查询亚马逊Listing响应参数")
public class ListingResp {

    @ApiModelProperty("店铺ID")
    private String sid;

    @ApiModelProperty("国家")
    private String marketplace;

    @ApiModelProperty("状态 1：在售，0：下架")
    private String status;

    @ApiModelProperty("是否删除（1：是，0：否）")
    private String is_delete;

    @ApiModelProperty("物料编码")
    private String local_sku;

    @ApiModelProperty("sku")
    private String seller_sku;

    @ApiModelProperty("listing的显示售价（实际优惠价）")
    private BigDecimal listing_price;

    /** 商品的原价 */
    @ApiModelProperty("商品的原价")
    private BigDecimal price;

    /** 卖家自己产品的销售价格 */
    @ApiModelProperty("卖家自己产品的销售价格")
    private BigDecimal landedPrice;

    @ApiModelProperty("主图URL")
    private String small_image_url;

    @ApiModelProperty("asin")
    private String asin;

    /** 亚马逊定义的listing的id */
    @ApiModelProperty("亚马逊定义的listing的id")
    private String listingId;

    /** 币种 */
    @ApiModelProperty("币种")
    private String currencyCode;

    /** listing更新时间 */
    @ApiModelProperty("listing更新时间")
    private String listingUpdateDate;

    /** 配对更新时间 */
    @ApiModelProperty("配对更新时间")
    private String pairUpdateTime;
}
