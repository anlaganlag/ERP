package com.tadpole.cloud.externalSystem.api.lingxing.model.resp.auth;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author: ty
 * @description: 领星授权响应信息
 * @date: 2022/4/26
 */
@Data
@ApiModel(value="领星授权响应信息")
public class LingXingAuthRespData {

    public static final String DEFAULT_SUCCESS_MESSAGE = "请求成功";

    public static final String DEFAULT_ERROR_MESSAGE = "请求异常";

    public static final String DEFAULT_SUCCESS_CODE = "200";

    @ApiModelProperty("响应状态码")
    private String code;

    @ApiModelProperty("响应信息")
    private String msg;

    @ApiModelProperty("响应对象")
    private LingXingToken data;
}
