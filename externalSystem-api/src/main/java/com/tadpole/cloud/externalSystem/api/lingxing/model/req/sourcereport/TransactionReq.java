package com.tadpole.cloud.externalSystem.api.lingxing.model.req.sourcereport;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author: ty
 * @description: 查询亚马逊源报表-交易明细请求参数
 * @date: 2022/4/25
 */
@Data
@ApiModel(value="查询亚马逊源报表-交易明细请求参数")
public class TransactionReq {

    @NotNull
    @ApiModelProperty("店铺ID")
    private Long sid;

    @NotNull
    @ApiModelProperty("报表日期，Y-m-d格式。（每月３日后支持查询上月数据），如2019-07-13")
    private String event_date;

    @ApiModelProperty("分页偏移索引，默认0")
    private Integer offset;

    @ApiModelProperty("分页偏移长度，默认1000")
    private Integer length;
}
