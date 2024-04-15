package com.tadpole.cloud.supplyChain.api.logistics.entity;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
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
 * 领星亚马逊Listing信息 实体类
 * </p>
 *
 * @author ty
 * @since 2023-05-22
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("TG_LX_LISTING_INFO")
@ExcelIgnoreUnannotated
public class TgLxListingInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    /** ID */
    @TableId(value = "ID", type = IdType.AUTO)
    private BigDecimal id;

    /** 店铺ID */
    @ApiModelProperty("店铺ID")
    @TableField("SID")
    private String sid;

    /** 账号 */
    @ApiModelProperty("账号")
    @TableField("SHOP_NAME")
    private String shopName;

    /** 站点 */
    @ApiModelProperty("站点")
    @TableField("SITE")
    private String site;

    /** 国家 */
    @ApiModelProperty("国家")
    @TableField("MARKETPLACE")
    private String marketplace;

    /** 物料编码 */
    @ApiModelProperty("物料编码")
    @TableField("LOCAL_SKU")
    private String localSku;

    /** sku */
    @ApiModelProperty("sku")
    @TableField("SELLER_SKU")
    private String sellerSku;

    /** 状态 1：在售，0：下架 */
    @ApiModelProperty("状态 1：在售，0：下架")
    @TableField("STATUS")
    private String status;

    /** 是否删除（1：是，0：否） */
    @ApiModelProperty("是否删除（1：是，0：否）")
    @TableField("IS_DELETE")
    private String isDelete;

    /** listing的显示售价（实际优惠价） */
    @ApiModelProperty("listing的显示售价（实际优惠价）")
    @TableField("LISTING_PRICE")
    private BigDecimal listingPrice;

    /** 商品的原价 */
    @ApiModelProperty("商品的原价")
    @TableField("PRICE")
    private BigDecimal price;

    /** 卖家自己产品的销售价格 */
    @ApiModelProperty("卖家自己产品的销售价格")
    @TableField("LANDED_PRICE")
    private BigDecimal landedPrice;

    /** 主图URL */
    @ApiModelProperty("主图URL")
    @TableField("SMALL_IMAGE_URL")
    private String smallImageUrl;

    /** asin */
    @ApiModelProperty("asin")
    @TableField("ASIN")
    private String asin;

    /** 亚马逊定义的listing的id */
    @ApiModelProperty("亚马逊定义的listing的id")
    @TableField("LISTING_ID")
    private String listingId;

    /** 币种 */
    @ApiModelProperty("币种")
    @TableField("CURRENCY_CODE")
    private String currencyCode;

    /** listing更新时间 */
    @ApiModelProperty("listing更新时间")
    @TableField("LISTING_UPDATE_DATE")
    private String listingUpdateDate;

    /** 配对更新时间 */
    @ApiModelProperty("配对更新时间")
    @TableField("PAIR_UPDATE_TIME")
    private String pairUpdateTime;

    /** 创建时间 */
    @ApiModelProperty("创建时间")
    @TableField("CREATE_TIME")
    private Date createTime;

    /** 创建人 */
    @ApiModelProperty("创建人")
    @TableField("CREATE_USER")
    private String createUser;

    /** 更新时间 */
    @ApiModelProperty("更新时间")
    @TableField("UPDATE_TIME")
    private Date updateTime;

    /** 更新人 */
    @ApiModelProperty("更新人")
    @TableField("UPDATE_USER")
    private String updateUser;
}
