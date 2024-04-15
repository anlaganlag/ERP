package com.tadpole.cloud.externalSystem.api.lingxing.model.resp.advertising;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * <p>
 * 商品定位
 * </p>
 *
 * @author cyt
 * @since 2022-05-17
 */
@Data
@TableName("L_OPERATION_LOG")
public class OperationLog implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.AUTO)

    private BigDecimal id;


    private String shop;


    private String site;


    private String adsType;


    private String campaignId;


    private String adGroupId;


    private String operatorId;


    private String execType;


    private String operateType;


    private String operateObject;


    private String operateObjectDetail;


    private String operateResult;


    private String operateResultNote;


    private String operateBefore;


    private String operateAfter;


    private String operateTime;


    private String campaignName;


    private String operatorName;


    private String adGroupName;


    private int type;
}