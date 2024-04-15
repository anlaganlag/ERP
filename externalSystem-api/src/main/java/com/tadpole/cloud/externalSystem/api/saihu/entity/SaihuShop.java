package com.tadpole.cloud.externalSystem.api.saihu.entity;

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
 * 赛狐店铺列表 实体类
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
@ExcelIgnoreUnannotated
public class SaihuShop implements Serializable {

    private static final long serialVersionUID = 1L;

    /** ID */
    @TableId(value = "ID", type = IdType.AUTO)
    private BigDecimal id;

    /** 店铺ID */
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

    /** 区域 */
    @TableField("REGION")
    private String region;

    /** marketplaceId */
    @TableField("MARKETPLACE_ID")
    private String marketplaceId;

    /** adStatus */
    @TableField("AD_STATUS")
    private String adStatus;

    /** status */
    @TableField("STATUS")
    private String status;

    /** sellerId */
    @TableField("SELLER_ID")
    private String sellerId;

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