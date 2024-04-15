package com.tadpole.cloud.platformSettlement.api.finance.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;
import java.io.Serializable;

/**
* <p>
* 财务站点信息
* </p>
*
* @author gal
* @since 2021-10-25
*/
@Data
@TableName("TbAmazonMarketplace")
public class TbAmazonMarketplace implements Serializable {

    private static final long serialVersionUID = 1L;

    /** 平台 */
    @TableField("elePlatformName")
    private String elePlatformName;

    /** 站点 */
    @TableField("CountryCode")
    private String countryCode;

}