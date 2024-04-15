package com.tadpole.cloud.supplyChain.api.logistics.model.result;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author: ty
 * @description: K3币别
 * @date: 2023/12/12
 */
@Data
public class K3CurrencyResult implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * k3币别编码
     */
    @ApiModelProperty("k3币别编码")
    private String currencyCode;

    /**
     * 币别英文名称
     */
    @ApiModelProperty("币别英文名称")
    private String currencyEn;

    /**
     * 币别中文名称
     */
    @ApiModelProperty("币别中文名称")
    private String currencyName;
}
