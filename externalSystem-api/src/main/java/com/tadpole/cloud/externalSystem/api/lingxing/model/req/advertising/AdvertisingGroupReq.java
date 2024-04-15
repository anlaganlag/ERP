package com.tadpole.cloud.externalSystem.api.lingxing.model.req.advertising;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author: dyl
 * @description: 查询广告管理 - 广告组
 * @date: 2022/5/11
 */
@Data
@ApiModel(value="查询广告管理 - 广告组")
public class AdvertisingGroupReq {

    @NotNull
    @ApiModelProperty("店铺")
    private int sid;

    @ApiModelProperty("店铺名称")
    private int name;

    @NotNull
    @ApiModelProperty("亚马逊当地下单时间左闭区间，Y-m-d格式，如2019-07-13")
    private String start_date;

    @NotNull
    @ApiModelProperty("亚马逊当地下单时间右开区间，Y-m-d格式，如2019-07-13")
    private String end_date;

    @NotNull
    @ApiModelProperty("拉取数据偏移量 默认：0")
    private int offset;

    @NotNull
    @ApiModelProperty("一页拉取的数据量 默认：20条")
    private int length;

    @NotNull
    @ApiModelProperty("广告类型 ：1:SP,3：SD")
    private int type;
}
