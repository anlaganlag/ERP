package com.tadpole.cloud.externalSystem.api.lingxing.model.resp;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author: ty
 * @description: 领星响应信息
 * @date: 2022/4/22
 */
@Data
@ApiModel(value="领星业务响应信息")
public class LingXingBaseRespData {

    public static final String DEFAULT_SUCCESS_MESSAGE = "请求成功";

    public static final String DEFAULT_ERROR_MESSAGE = "请求异常";

    public static final Integer DEFAULT_SUCCESS_CODE = 0;

    @ApiModelProperty("响应状态码")
    private Integer code;

    @ApiModelProperty("消息提示")
    private String message;

    @ApiModelProperty("请求ID")
    private String request_id;

    @ApiModelProperty("数据校验失败时的错误详情")
    private List<Object> error_details;

    @ApiModelProperty("响应时间")
    private String response_time;

    @ApiModelProperty("响应数据")
    private List<Object> data;

    @ApiModelProperty("总记录数")
    private Integer total;

    @ApiModelProperty("类型，0：固定值；1：先进先出；")
    private Integer fifo_type;

    @ApiModelProperty("类型，0：固定值；1：先进先出；")
    private String req_time_sequence;

    @ApiModelProperty("是否结账数据：0否 1是")
    private Integer is_closing_data;
}
