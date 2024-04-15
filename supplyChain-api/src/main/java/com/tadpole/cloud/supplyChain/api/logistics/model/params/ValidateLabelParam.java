package com.tadpole.cloud.supplyChain.api.logistics.model.params;

import cn.stylefeng.guns.cloud.model.web.request.BaseRequest;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author: ty
 * @description: 校验标签入参
 * @date: 2022/7/27
 */
@Data
public class ValidateLabelParam extends BaseRequest {

    /** 店铺 */
    @NotBlank(message = "店铺不能为空")
    @ApiModelProperty("店铺")
    private String shopName;

    /** FNSKU */
    @NotBlank(message = "FNSKU不能为空")
    @ApiModelProperty("FNSKU")
    private String fnSku;

    /** SKU */
    @NotBlank(message = "SKU不能为空")
    @ApiModelProperty("SKU")
    private String sku;
}
