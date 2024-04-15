package com.tadpole.cloud.platformSettlement.api.finance.entity;

import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import lombok.*;

/**
* <p>
* 店铺报告币别
* </p>
*
* @author gal
* @since 2021-10-25
*/
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("CW_SHOP_CURRENCY")
public class ShopCurrency implements Serializable {

    private static final long serialVersionUID = 1L;

    /** id */
    @TableId(value="ID",type= IdType.AUTO)
    private BigDecimal id;

    /** 平台 */
    @TableField("PLATFORM_NAME")
    private String platformName;

    /** 账号 */
    @TableField("SHOP_NAME")
    private String shopName;

    /** 站点 */
    @TableField("SITE")
    private String site;

    /** 账号 */
    @TableField("SHOP")
    private String shop;

    /** 账号 */
    @TableField("SHOP_CODE")
    private String shopCode;

    /** 原币 */
    @TableField("ORIGINAL_CURRENCY")
    private String originalCurrency;

    /** 收款币 */
    @TableField("PROCEEDS_CURRENCY")
    private String proceedsCurrency;

    /** 创建时间 */
    @TableField("CREATE_AT")
    private LocalDateTime createAt;

    /** 修改时间 */
    @TableField("UPDATE_AT")
    private LocalDateTime updateAt;

    /** 创建人 */
    @TableField("CREATE_BY")
    private String createBy;

    /** 修改人 */
    @TableField("UPDATE_BY")
    private String updateBy;

}