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
 * 用户搜索关键词
 * </p>
 *
 * @author cyt
 * @since 2022-05-16
 */
@Data
@TableName("L_USER_SEARCH_WORDS")
public class UserSearchWords implements Serializable {

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
    @TableField("QUERY")
    private String query;

    /** 报告类型 */
    @TableField("KEYWORD_TEXT")
    private String keywordText;

    /** 收入确认类型 */
    @TableField("AD_GROUP_NAME")
    private String adGroupName;

    /** 账号 */
    @TableField("CAMPAIGN_NAME")
    private String campaignName;

    /** 站点 */
    @TableField("MATCH_TYPE")
    private String matchType;

    @TableField("TARGETING_TYPE")
    private String targetingType;

    @TableField("TARGETING_MODE")
    private String targetingMode;

    @TableField("QUERY_TYPE")
    private int queryType;

    @TableField("IMPRESSIONS")
    private String impressions;

    /** 原币 */
    @TableField("CLICKS")
    private String clicks;

    @TableField("COST")
    private String cost;

    /** advertising */
    @TableField("ORDER_QNUANTITY")
    private long orderQnuantity;

    /** storage_fee */
    @TableField("SALES_AMOUNT")
    private String salesAmount;

    @TableField("START_DATE")
    private Date startDate;

    @TableField("END_DATE")
    private Date endDate;

    @TableField("TYPE")
    private int type;

    /** 创建人 */
    @TableField("ctr")
    private String ctr;

    /** 创建时间 */
    @TableField("cpc")
    private String cpc;

    /** 创建时间 */
    @TableField("cvr")
    private String cvr;

    /** 创建时间 */
    @TableField("cpa")
    private String cpa;

    /** 创建时间 */
    @TableField("acos")
    private String acos;

}