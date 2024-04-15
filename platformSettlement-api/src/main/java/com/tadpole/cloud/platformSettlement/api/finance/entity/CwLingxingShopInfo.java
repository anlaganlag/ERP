package com.tadpole.cloud.platformSettlement.api.finance.entity;

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
 *  领星亚马逊店铺信息实体类
 * </p>
 *
 * @author ty
 * @since 2022-04-29
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("CW_LINGXING_SHOP_INFO")
@ApiModel(value="领星亚马逊店铺信息实体类", description="")
public class CwLingxingShopInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "ID")
    @TableId(value = "ID", type = IdType.AUTO)
    private BigDecimal id;

    @ApiModelProperty(value = "账号（根据领星店铺名截取账号）")
    private String shopName;

    @ApiModelProperty(value = "站点（根据领星店铺名截取站点）")
    private String site;

    @ApiModelProperty(value = "店铺ID	领星ERP对企业已授权店铺的唯一标识")
    private BigDecimal sid;

    @ApiModelProperty(value = "站点ID")
    private BigDecimal mid;

    @ApiModelProperty(value = "店铺名（账号-站点）")
    private String name;

    @ApiModelProperty(value = "SELLER_ID")
    private String sellerId;

    @ApiModelProperty(value = "账号")
    private String accountName;

    @ApiModelProperty(value = "账号ID")
    private BigDecimal sellerAccountId;

    @ApiModelProperty(value = "站点简称")
    private String region;

    @ApiModelProperty(value = "国家")
    private String country;

    @ApiModelProperty(value = "创建时间")
    private Date createDate;

    @ApiModelProperty(value = "更新时间")
    private Date updateDate;


}
