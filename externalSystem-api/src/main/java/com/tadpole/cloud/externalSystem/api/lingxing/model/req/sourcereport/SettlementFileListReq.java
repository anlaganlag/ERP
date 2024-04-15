package com.tadpole.cloud.externalSystem.api.lingxing.model.req.sourcereport;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author: ty
 * @description: 查询亚马逊源表-Settlement文件列表请求参数
 * @date: 2022/4/25
 */
@Data
@ApiModel(value="查询亚马逊源表-Settlement文件列表请求参数")
public class SettlementFileListReq {

    @NotNull
    @ApiModelProperty("店铺ID")
    private Long sid;

    @ApiModelProperty("开始日期，Y-m-d格式，如2019-07-13")
    private String start_date;

    @ApiModelProperty("结束日期，Y-m-d格式，如2019-07-13")
    private String end_date;

    @ApiModelProperty("分页偏移索引，默认0")
    private String offset;

    @ApiModelProperty("分页偏移长度，默认1000")
    private String length;
}
