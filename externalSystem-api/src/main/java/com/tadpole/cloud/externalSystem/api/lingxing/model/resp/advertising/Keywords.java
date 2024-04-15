package com.tadpole.cloud.externalSystem.api.lingxing.model.resp.advertising;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>
 * 关键词
 * </p>
 *
 * @author cyt
 * @since 2022-05-17
 */
@Data
@TableName("L_KEYWORDS")
public class Keywords implements Serializable {

        private static final long serialVersionUID = 1L;

        @TableId(type = IdType.AUTO)

        private BigDecimal id;


        private String shop;


        private String site;


        private String keywordId;


        private String keywordText;


        private String bid;


        private String matchType;


        private String state;


        private String campaignId;


        private String adFormat;


        private String campaignName;


        private String adGroupId;


        private String adGroupName;


        private String currencyCode;


        private String impressions;


        private String clicks;


        private String cost;


        private BigDecimal orderNum;


        private String salesAmount;


        private String ctr;


        private String cpc;


        private String cvr;


        private String cpa;


        private String acos;


        private Date startDate;


        private int type;


        private String servingStatus;


        private Date endDate;

}