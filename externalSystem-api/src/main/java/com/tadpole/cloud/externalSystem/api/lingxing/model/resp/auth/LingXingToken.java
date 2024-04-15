package com.tadpole.cloud.externalSystem.api.lingxing.model.resp.auth;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author: ty
 * @description: 领星token信息
 * @date: 2022/4/22
 */
@Data
@ApiModel(value="领星授权token信息")
public class LingXingToken {

    @ApiModelProperty("access_token")
    private String access_token;

    @ApiModelProperty("refresh_token")
    private String refresh_token;

    @ApiModelProperty("过期时间，有效时间为2小时（单位：秒）")
    private String expires_in;
}
