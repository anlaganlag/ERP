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
 * 广告
 * </p>
 *
 * @author cyt
 * @since 2022-05-17
 */
@Data
@TableName("L_ADVERTISING")
public class Advertising implements Serializable {

        private static final long serialVersionUID = 1L;

        @TableId(type = IdType.AUTO)

        private BigDecimal id;


        private String shop;


        private String site;


        private String asin;


        private String sku;


        private String servingStatus;


        private String adGroupId;


        private String campaignId;


        private String imageUrl;


        private String campaignName;


        private String groupName;


        private int type;


        private String impressions;


        private String clicks;


        private String cost;


        private BigDecimal orderNum;


        private String salesAmount;


        private String currencyCode;


        private String ctr;


        private String cpc;


        private String cvr;


        private String cpa;


        private String acos;


        private Date eventDate;

}