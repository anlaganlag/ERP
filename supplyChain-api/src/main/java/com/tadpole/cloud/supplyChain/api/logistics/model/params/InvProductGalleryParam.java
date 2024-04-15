package com.tadpole.cloud.supplyChain.api.logistics.model.params;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author: ty
 * @description:
 * @date: 2023/2/2
 */
@Data
public class InvProductGalleryParam {
    /** 账号 */
    @ApiModelProperty("账号")
    private String sysShopsName;

    /** 站点 */
    @ApiModelProperty("站点")
    private String sysSite;

    /** SKU */
    @ApiModelProperty("SKU")
    private String sku;

    /** 事业部 */
    @ApiModelProperty("事业部")
    private String department;

    /** Team */
    @ApiModelProperty("Team")
    private String team;
}
