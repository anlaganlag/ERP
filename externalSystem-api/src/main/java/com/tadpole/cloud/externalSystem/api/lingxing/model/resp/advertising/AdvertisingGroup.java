package com.tadpole.cloud.externalSystem.api.lingxing.model.resp.advertising;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>
 * 广告组
 * </p>
 *
 * @author gal
 * @since 2021-12-24
 */
@Data
@TableName("L_ADVERTISING_GROUP")
public class AdvertisingGroup implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "ID",type = IdType.AUTO)
    private Long id;

    /** 会计期间 */
    @TableField("shop")
    private String shop;

    /** 会计期间 */
    @TableField("site")
    private String site;


    /** 会计期间 */
    @TableField("campaign_id")
    private long campaignId;

    /** 报告类型 */
    @TableField("ad_group_id")
    private String adGroupId;

    /** 收入确认类型 */
    @TableField("ad_group_name")
    private String adGroupName;

    /** 账号 */
    @TableField("state")
    private String state;

    /** 站点 */
    @TableField("serving_status")
    private String servingStatus;

    @TableField("default_bid")
    private BigDecimal defaultBid;

    @TableField("targeting_mode")
    private String targetingMode;

    @TableField("targeting_type")
    private String targetingType;

    @TableField("product_num")
    private String productNum;

    @TableField("impressions")
    private String impressions;

    /** 原币 */
    @TableField("clicks")
    private String clicks;

    @TableField("cost")
    private BigDecimal cost;

    /** advertising */
    @TableField("order_num")
    private long orderNum;

    /** storage_fee */
    @TableField("sales_amount")
    private BigDecimal salesAmount;

    @TableField("currency_code")
    private String currencyCode;

    @TableField("campaign_name")
    private String campaignName;

    @TableField("START_DATE")
    private Date startDate;

    @TableField("END_DATE")
    private Date endDate;

    @TableField("TYPE")
    private int type;

    /** 创建人 */
    @TableField("ctr")
    private BigDecimal ctr;

    /** 创建时间 */
    @TableField("cpc")
    private BigDecimal cpc;

    /** 创建时间 */
    @TableField("cvr")
    private BigDecimal cvr;

    /** 创建时间 */
    @TableField("cpa")
    private BigDecimal cpa;

    /** 创建时间 */
    @TableField("acos")
    private BigDecimal acos;

    /** 创建时间 */
    @TableField("roas")
    private BigDecimal roas;


}