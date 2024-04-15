package com.tadpole.cloud.externalSystem.api.lingxing.model.resp.advertising;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 商品定位
 * </p>
 *
 * @author cyt
 * @since 2022-05-17
 */
@Data
@TableName("L_ADVERTISING_ACTIVITY")
public class AdvertisingActivity implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "ID",type = IdType.AUTO)
    private Long id;

    /** 会计期间 */
    @TableField("SHOP")
    private String shop;

    /** 会计期间 */
    @TableField("SITE")
    private String site;


    /** 会计期间 */
    @TableField("CAMPAIGN_ID")
    private String campaignId;

    /** 报告类型 */
    @TableField("CAMPAIGN_NAME")
    private String campaignName;

    /** 账号 */
    @TableField("STATE")
    private String state;

    /** 站点 */
    @TableField("PORTFOLIO_ID")
    private String portfolioId;

    @TableField("CREATIVE_TYPE")
    private String creativeType;

    @TableField("PORTFOLIO_NAME")
    private String portfolioName;

    @TableField("AD_FORMAT")
    private int adFormat;

    @TableField("SERVING_STATUS")
    private String servingStatus;

    /** 原币 */
    @TableField("TARGETING_TYPE")
    private String targetingType;

    @TableField("START_DATE")
    private Date startDate;

    /** advertising */
    @TableField("END_DATE")
    private Date endDate;

    /** storage_fee */
    @TableField("DAILY_BUDGET")
    private String dailyBudget;

    @TableField("STRATEGY")
    private String strategy;

    @TableField("IMPRESSIONS")
    private String impressions;

    @TableField("CLICKS")
    private int clicks;

    /** 创建人 */
    @TableField("COST")
    private String cost;

    /** 创建人 */
    @TableField("ORDER_NUM")
    private String orderNum;

    /** 创建人 */
    @TableField("SALES_AMOUNT")
    private String salesAmount;

    /** 创建人 */
    @TableField("CURRENCY_CODE")
    private String currencyCode;

    /** 创建人 */
    @TableField("ASIN")
    private String asin;

    /** 创建时间 */
    @TableField("CTR")
    private String ctr;

    /** 创建时间 */
    @TableField("CPC")
    private String cpc;

    /** 创建时间 */
    @TableField("CVR")
    private String cvr;

    /** 创建时间 */
    @TableField("CPA")
    private String cpa;

    /** 创建时间 */
    @TableField("ACOS")
    private String acos;

    /** 创建时间 */
    @TableField("ROAS")
    private String roas;

    /** 创建时间 */
    @TableField("START_DATE_PARAM")
    private Date startDateParam ;

    /** 创建时间 */
    @TableField("END_DATE_PARAM")
    private Date  endDateParam ;

    /** 创建时间 */
    @TableField("TYPE")
    private int type;
}