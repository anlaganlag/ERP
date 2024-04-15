package com.tadpole.cloud.supplyChain.api.logistics.model.params;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author: cyt
 * @description: 店铺物料信息入参
 * @date: 2022/8/2
 */
@Data
public class CustomerMaterialInfoParam {
    /** SHOP_NAME */
    @NotBlank(message = "SHOP_NAME不能为空")
    @ApiModelProperty("SHOP_NAME")
    private String shopName;

    @NotBlank(message = "物料编码不能为空")
    @ApiModelProperty("物料编码")
    private String materialCode;
}
