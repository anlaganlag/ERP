package com.tadpole.cloud.supplyChain.api.logistics.model.result;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author: ty
 * @description: EBMS标签管理
 * @date: 2022/8/15
 */
@Data
public class ValidateLabelResult {

    /** 店铺 */
    @ApiModelProperty("店铺")
    private String shopName;

    /** FNSKU */
    @ApiModelProperty("FNSKU")
    private String fnSku;

    /** SKU */
    @ApiModelProperty("SKU")
    private String sku;

    @ApiModelProperty("物料编码")
    private String materialCode;

    @ApiModelProperty("站点")
    private String site;

    @ApiModelProperty("标签使用状态")
    private String labUseState;
}
