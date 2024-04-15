package com.tadpole.cloud.supplyChain.api.logistics.model.result;

import java.math.BigDecimal;
import cn.stylefeng.guns.cloud.model.validator.BaseValidatingParam;
import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

/**
 * <p>
 * 赛狐在线产品 出参类
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
@ExcelIgnoreUnannotated
@TableName("TG_SAIHU_PRODUCT")
public class TgSaihuProductResult implements Serializable, BaseValidatingParam {

    private static final long serialVersionUID = 1L;


   @TableId(value = "ID", type = IdType.AUTO)
    private BigDecimal id;

    @ApiModelProperty("shopId")
    private String shopId;

    @ApiModelProperty("店铺名称")
    private String name;

    @ApiModelProperty("账号")
    private String shopName;

    @ApiModelProperty("站点")
    private String site;

    @ApiModelProperty("marketplaceId")
    private String marketplaceId;

    @ApiModelProperty("commoditySku（物料编码）")
    private String commoditySku;

    @ApiModelProperty("sku")
    private String sku;

    @ApiModelProperty("fnsku")
    private String fnsku;

    @ApiModelProperty("asin")
    private String asin;

    @ApiModelProperty("在线状态:active(在售)，inActive(不可售)")
    private String onlineStatus;

    @ApiModelProperty("产品状态：删除：delete,其它状态为空")
    private String dxmPublishState;

    @ApiModelProperty("挂牌价")
    private BigDecimal listingPrice;

    @ApiModelProperty("标准价格")
    private BigDecimal standardPrice;

    @ApiModelProperty("落地价	")
    private BigDecimal listingPricing;

    @ApiModelProperty("主图")
    private String mainImage;

    @ApiModelProperty("商品编号")
    private String listingId;

    @ApiModelProperty("在线产品最后同步时间")
    private String lastSyncTime;

    @ApiModelProperty("上架时间")
    private String openDate;

    @ApiModelProperty("币种")
    private String currency;

    @ApiModelProperty("创建时间")
    private Date createTime;

    @ApiModelProperty("创建人")
    private String createUser;

    @ApiModelProperty("更新时间")
    private Date updateTime;

    @ApiModelProperty("更新人")
    private String updateUser;

}
