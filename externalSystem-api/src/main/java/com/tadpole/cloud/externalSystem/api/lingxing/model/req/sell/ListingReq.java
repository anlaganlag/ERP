package com.tadpole.cloud.externalSystem.api.lingxing.model.req.sell;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author: ty
 * @description: 查询亚马逊Listing请求参数
 * @date: 2023/6/5
 */
@Data
@ApiModel(value="查询亚马逊Listing请求参数")
public class ListingReq {
    @NotNull
    @ApiModelProperty("店铺ID")
    private String sid;

    @ApiModelProperty("分页偏移索引，默认0")
    private Integer offset;

    @ApiModelProperty("分页偏移长度，默认1000")
    private Integer length;

    @ApiModelProperty("是否已配对 1：已配对，2：未配对")
    private Integer is_pair;

    @ApiModelProperty("配对更新开始时间，格式Y-m-d H:i:s，该字段有值时pair_update_end_time必须同时有值，且is_pair=1")
    private String pair_update_start_time;

    @ApiModelProperty("配对更新结束时间，格式Y-m-d H:i:s，该字段有值时pair_update_start_time必须同时有值，且is_pair=1")
    private String pair_update_end_time;

    @ApiModelProperty("listing更新开始时间，格式Y-m-d H:i:s ，该字段有值时listing_update_end_time必须同时有值")
    private String listing_update_start_time;

    @ApiModelProperty("listing更新结束时间，格式Y-m-d H:i:s ，该字段有值时listing_update_start_time必须同时有值")
    private String listing_update_end_time;
}
