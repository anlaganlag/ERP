package com.tadpole.cloud.externalSystem.api.saihu.model.params;

import cn.stylefeng.guns.cloud.model.validator.BaseValidatingParam;
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
 * 赛狐店铺列表 入参类
 * </p>
 *
 * @author ty
 * @since 2024-02-22
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("SAIHU_SHOP")
public class SaihuShopParam implements Serializable, BaseValidatingParam {

    private static final long serialVersionUID = 1L;

    /** ID */
    @TableId(value = "ID", type = IdType.AUTO)
    private BigDecimal id;

    /** 店铺ID */
    @ApiModelProperty("店铺ID")
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

    /** 区域 */
    @ApiModelProperty("区域")
    private String region;

    /** marketplaceId */
    @ApiModelProperty("marketplaceId")
    private String marketplaceId;

    /** adStatus */
    @ApiModelProperty("adStatus")
    private String adStatus;

    /** status */
    @ApiModelProperty("status")
    private String status;

    /** sellerId */
    @ApiModelProperty("sellerId")
    private String sellerId;

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
