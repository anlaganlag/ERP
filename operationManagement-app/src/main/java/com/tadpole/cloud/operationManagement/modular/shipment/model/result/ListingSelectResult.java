package com.tadpole.cloud.operationManagement.modular.shipment.model.result;


import cn.stylefeng.guns.cloud.model.validator.BaseValidatingParam;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.io.Serializable;
import java.util.Set;

/**
* <p>
    * listing 下拉通用接口
    * </p>
*
* @author lsy
* @since 2023-02-02
*/
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class ListingSelectResult implements Serializable, BaseValidatingParam {

    private static final long serialVersionUID = 1L;


    @ApiModelProperty("账号")
    private Set<String> sysShopsNameList;


    @ApiModelProperty("站点")
    private Set<String> sysSiteList;


    @ApiModelProperty("物料编码")
    private Set<String> materialCodeList;

    @ApiModelProperty("ASIN")
    private Set<String> asinList;


    @ApiModelProperty("sku")
    private Set<String> skuList;


}
