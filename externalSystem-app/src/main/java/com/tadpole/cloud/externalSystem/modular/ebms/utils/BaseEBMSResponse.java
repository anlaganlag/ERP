package com.tadpole.cloud.externalSystem.modular.ebms.utils;

import lombok.Data;

/**
 * @author: ty
 * @description: EBMS响应信息基础类
 * @date: 2022/7/22
 */
@Data
public class BaseEBMSResponse {
    public static final String DEFAULT_SUCCESS_MESSAGE = "请求成功";

    public static final String DEFAULT_ERROR_MESSAGE = "网络异常";

    public static final Integer DEFAULT_SUCCESS_CODE = 0;

    /**
     * 响应状态码
     */
    private Integer code;

    /**
     * 响应信息
     */
    private String msg;

    /**
     * 响应对象
     */
    private Object data;
}
