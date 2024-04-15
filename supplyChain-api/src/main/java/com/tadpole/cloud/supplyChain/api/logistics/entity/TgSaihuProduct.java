package com.tadpole.cloud.supplyChain.api.logistics.entity;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
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
 * 赛狐在线产品 实体类
 * </p>
 *
 * @author ty
 * @since 2024-02-23
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("TG_SAIHU_PRODUCT")
@ExcelIgnoreUnannotated
public class TgSaihuProduct implements Serializable {

    private static final long serialVersionUID = 1L;

    /** ID */
    @TableId(value = "ID", type = IdType.AUTO)
    private BigDecimal id;

    /** shopId */
    @TableField("SHOP_ID")
    private String shopId;

    /** 店铺名称 */
    @TableField("NAME")
    private String name;

    /** 账号 */
    @TableField("SHOP_NAME")
    private String shopName;

    /** 站点 */
    @TableField("SITE")
    private String site;

    /** marketplaceId */
    @TableField("MARKETPLACE_ID")
    private String marketplaceId;

    /** commoditySku（物料编码） */
    @TableField("COMMODITY_SKU")
    private String commoditySku;

    /** sku */
    @TableField("SKU")
    private String sku;

    /** fnsku */
    @TableField("FNSKU")
    private String fnsku;

    /** asin */
    @TableField("ASIN")
    private String asin;

    /** 在线状态:active(在售)，inActive(不可售) */
    @TableField("ONLINE_STATUS")
    private String onlineStatus;

    /** 产品状态：删除：delete,其它状态为空 */
    @TableField("DXM_PUBLISH_STATE")
    private String dxmPublishState;

    /** 挂牌价 */
    @TableField("LISTING_PRICE")
    private BigDecimal listingPrice;

    /** 标准价格 */
    @TableField("STANDARD_PRICE")
    private BigDecimal standardPrice;

    /** 落地价	 */
    @TableField("LISTING_PRICING")
    private BigDecimal listingPricing;

    /** 主图 */
    @TableField("MAIN_IMAGE")
    private String mainImage;

    /** 商品编号 */
    @TableField("LISTING_ID")
    private String listingId;

    /** 在线产品最后同步时间 */
    @TableField("LAST_SYNC_TIME")
    private String lastSyncTime;

    /** 上架时间 */
    @TableField("OPEN_DATE")
    private String openDate;

    /** 币种 */
    @TableField("CURRENCY")
    private String currency;

    /** 创建时间 */
    @TableField("CREATE_TIME")
    private Date createTime;

    /** 创建人 */
    @TableField("CREATE_USER")
    private String createUser;

    /** 更新时间 */
    @TableField("UPDATE_TIME")
    private Date updateTime;

    /** 更新人 */
    @TableField("UPDATE_USER")
    private String updateUser;

}