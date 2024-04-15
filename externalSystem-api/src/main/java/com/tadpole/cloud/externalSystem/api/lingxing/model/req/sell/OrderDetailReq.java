package com.tadpole.cloud.externalSystem.api.lingxing.model.req.sell;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author: ty
 * @description: 订单详情入参
 * @date: 2022/8/12
 */
@Data
@ApiModel(value="订单详情入参")
public class OrderDetailReq {

    @NotNull
    @ApiModelProperty("订单集合字符串 如['202-5663477-5452313,028-5345606-0663529']")
    private String order_id;
}
