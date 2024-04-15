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
@TableName("L_COMMODITY_POSITION")
public class CommodityPosition implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "ID",type = IdType.AUTO)
    private Long id;

    /** 会计期间 */
    @TableField("SHOP")
    private String shop;

    /** 会计期间 */
    @TableField("SITE")
    private String site;

    @TableField("request_id")
    private String requestId;

    /** 会计期间 */
    @TableField("CAMPAIGN_ID")
    private String campaignId;

    /** 报告类型 */
    @TableField("AD_GROUP_ID")
    private String adGroupId;

    /** 账号 */
    @TableField("TARGET_ID")
    private String targetId;

    /** 站点 */
    @TableField("BID")
    private String bid;

    @TableField("EXPRESSION_TYPE")
    private String expressionType;

    @TableField("STATE")
    private String state;

    @TableField("SERVING_STATUS")
    private int servingStatus;

    @TableField("CURRENCY_CODE")
    private String currencyCode;

    /** 原币 */
    @TableField("CAMPAIGN_NAME")
    private String campaignName;

    @TableField("GROUP_NAME")
    private String groupName;

    /** advertising */
    @TableField("TARGET_EXPRESSION")
    private long targetExpression;

    /** storage_fee */
    @TableField("IMPRESSIONS")
    private String impressions;

    @TableField("CLICKS")
    private String clicks;

    @TableField("COST")
    private String cost;

    @TableField("ORDER_NUM")
    private int orderNum;

    /** 创建人 */
    @TableField("SALES_AMOUNT")
    private String salesAmount;

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
    @TableField("START_DATE")
    private Date startDate ;

    /** 创建时间 */
    @TableField("END_DATE")
    private Date  endDate ;

    /** 创建时间 */
    @TableField("TYPE")
    private int type;
}