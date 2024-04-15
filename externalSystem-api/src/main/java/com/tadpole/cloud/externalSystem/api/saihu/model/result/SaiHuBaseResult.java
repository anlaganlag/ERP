package com.tadpole.cloud.externalSystem.api.saihu.model.result;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author: ty
 * @description: 赛狐基础响应信息
 * @date: 2024/1/30
 */
@Data
@ApiModel(value="赛狐基础响应信息")
public class SaiHuBaseResult {

    public static final String DEFAULT_SUCCESS_MESSAGE = "请求成功";

    public static final String DEFAULT_ERROR_MESSAGE = "请求异常";

    public static final Integer DEFAULT_SUCCESS_CODE = 0;

    @ApiModelProperty("code(默认0代表成功)")
    private Integer code;

    @ApiModelProperty("错误信息")
    private String msg;

    @ApiModelProperty("请求ID")
    private String requestId;

    @ApiModelProperty("响应数据")
    private Object data;
}
