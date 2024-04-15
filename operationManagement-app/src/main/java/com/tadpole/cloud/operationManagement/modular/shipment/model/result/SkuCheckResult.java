package com.tadpole.cloud.operationManagement.modular.shipment.model.result;


import cn.stylefeng.guns.cloud.model.validator.BaseValidatingParam;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.io.Serializable;

/**
* <p>
    * 查询team占用物料数量
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
public class SkuCheckResult implements Serializable, BaseValidatingParam {

    private static final long serialVersionUID = 1L;


    @ApiModelProperty("shopName")
    private String shopName;


    @ApiModelProperty("item_sku")
    private String item_sku;



    @ApiModelProperty("labState")
    private String labState;


    @ApiModelProperty("labSyncState")
    private String labSyncState;

    @ApiModelProperty("labUseState")
    private String labUseState;





}
