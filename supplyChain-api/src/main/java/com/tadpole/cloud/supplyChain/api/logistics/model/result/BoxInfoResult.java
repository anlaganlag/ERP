package com.tadpole.cloud.supplyChain.api.logistics.model.result;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @author: ty
 * @description: 箱子信息
 * @date: 2022/7/26
 */
@Data
public class BoxInfoResult {

    /** 箱型 */
    @ApiModelProperty("箱型")
    private String packDetBoxType;

    /**  */
    @ApiModelProperty("长")
    private BigDecimal packDetBoxLength;

    /** 宽 */
    @ApiModelProperty("宽")
    private BigDecimal packDetBoxWidth;

    /** 高 */
    @ApiModelProperty("高")
    private BigDecimal packDetBoxHeight;

    /** 体积 */
    @ApiModelProperty("体积")
    private BigDecimal volume;
}
