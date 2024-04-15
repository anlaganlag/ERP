package com.tadpole.cloud.supplyChain.api.logistics.model.params;

import io.swagger.annotations.ApiModelProperty;
import cn.stylefeng.guns.cloud.model.validator.BaseValidatingParam;

import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import lombok.*;

/**
 * <p>
 * 赛狐在线产品 入参类
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
public class TgSaihuProductParam implements Serializable, BaseValidatingParam {

    private static final long serialVersionUID = 1L;


    /** ID */
   @TableId(value = "ID", type = IdType.AUTO)
    private BigDecimal id;

    /** shopId */
    @ApiModelProperty("shopId")
    private String shopId;

    /** 店铺名称 */
    @ApiModelProperty("店铺名称")
    private String name;

    /** 账号 */
    @ApiModelProperty("账号")
    private String shopName;

    /** 站点 */
    @ApiModelProperty("站点")
    private String site;

    /** marketplaceId */
    @ApiModelProperty("marketplaceId")
    private String marketplaceId;

    /** commoditySku（物料编码） */
    @ApiModelProperty("commoditySku（物料编码）")
    private String commoditySku;

    /** sku */
    @ApiModelProperty("sku")
    private String sku;

    /** fnsku */
    @ApiModelProperty("fnsku")
    private String fnsku;

    /** asin */
    @ApiModelProperty("asin")
    private String asin;

    /** 在线状态:active(在售)，inActive(不可售) */
    @ApiModelProperty("在线状态:active(在售)，inActive(不可售)")
    private String onlineStatus;

    /** 产品状态：删除：delete,其它状态为空 */
    @ApiModelProperty("产品状态：删除：delete,其它状态为空")
    private String dxmPublishState;

    /** 挂牌价 */
    @ApiModelProperty("挂牌价")
    private BigDecimal listingPrice;

    /** 标准价格 */
    @ApiModelProperty("标准价格")
    private BigDecimal standardPrice;

    /** 落地价	 */
    @ApiModelProperty("落地价	")
    private BigDecimal listingPricing;

    /** 主图 */
    @ApiModelProperty("主图")
    private String mainImage;

    /** 商品编号 */
    @ApiModelProperty("商品编号")
    private String listingId;

    /** 在线产品最后同步时间 */
    @ApiModelProperty("在线产品最后同步时间")
    private String lastSyncTime;

    /** 上架时间 */
    @ApiModelProperty("上架时间")
    private String openDate;

    /** 币种 */
    @ApiModelProperty("币种")
    private String currency;

    /** 创建时间 */
    @ApiModelProperty("创建时间")
    private Date createTime;

    /** 创建人 */
    @ApiModelProperty("创建人")
    private String createUser;

    /** 更新时间 */
    @ApiModelProperty("更新时间")
    private Date updateTime;

    /** 更新人 */
    @ApiModelProperty("更新人")
    private String updateUser;

}
