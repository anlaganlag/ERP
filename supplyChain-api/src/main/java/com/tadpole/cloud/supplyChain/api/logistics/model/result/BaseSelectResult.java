package com.tadpole.cloud.supplyChain.api.logistics.model.result;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author: ty
 * @description: 下拉值
 * @date: 2023/5/23
 */
@Data
public class BaseSelectResult implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 下拉编码
     */
    @ApiModelProperty("下拉编码")
    private String code;

    /**
     * 下拉名称
     */
    @ApiModelProperty("下拉名称")
    private String name;
}
