package com.tadpole.cloud.platformSettlement.api.finance.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;
import java.io.Serializable;

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
@TableName("TbComShop")
public class TbComShop implements Serializable {

    private static final long serialVersionUID = 1L;

    /** 平台 */
    @TableField("elePlatformName")
    private String elePlatformName;

    /** 账号 */
    @TableField("shopNameSimple")
    private String shopNameSimple;

    /** 站点 */
    @TableField("CountryCode")
    private String countryCode;

    /** 店铺 */
    @TableField("shop")
    private String shop;

    /** 店铺编码 */
    @TableField("shopOrgCode")
    private String shopOrgCode;

    /** 银行 */
    @TableField("shopColAccBank")
    private String shopColAccBank;

    /** 银行账号 */
    @TableField("shopColAccNo")
    private String shopColAccNo;
}