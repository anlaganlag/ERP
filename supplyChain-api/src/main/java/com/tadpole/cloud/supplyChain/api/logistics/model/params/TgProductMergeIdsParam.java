package com.tadpole.cloud.supplyChain.api.logistics.model.params;

import cn.stylefeng.guns.cloud.model.validator.BaseValidatingParam;
import cn.stylefeng.guns.cloud.model.web.request.BaseRequest;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author: ty
 * @description: 清关产品合并ID
 * @date: 2023/5/31
 */
@Data
public class TgProductMergeIdsParam extends BaseRequest implements Serializable, BaseValidatingParam {

    /** 产品基本信息主表ID */
    @ApiModelProperty("产品基本信息主表ID")
    private BigDecimal id;

    /** 产品基本信息明细表ID */
    @ApiModelProperty("产品基本信息明细表ID")
    private BigDecimal detailId;

    /** 是否选中 */
    @ApiModelProperty("是否选中（规则合并预览、自定义预览不传，规则合并预览保存和自定义合并预览保存传值） 0：未选中，1：选中")
    private String isSelect;
}
