package com.tadpole.cloud.externalSystem.api.lingxing.model.req.sell;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author: ty
 * @description: 查询亚马逊订单列表请求参数
 * @date: 2022/8/12
 */
@Data
@ApiModel(value="查询亚马逊订单列表请求参数")
public class OrderReq {

    @NotNull
    @ApiModelProperty("店铺ID")
    private Long sid;

    @ApiModelProperty("时间查询类型，1：下单日期 2：亚马逊订单更新时间 默认1")
    private Integer date_type;

    @NotNull
    @ApiModelProperty("亚马逊当地下单时间左闭区间，Y-m-d格式，如2019-07-13")
    private String start_date;

    @NotNull
    @ApiModelProperty("亚马逊当地下单时间右开区间，Y-m-d格式，如2019-07-13")
    private String end_date;

    @ApiModelProperty("分页偏移索引，默认0")
    private String offset;

    @ApiModelProperty("分页偏移长度，默认1000")
    private String length;
}
