package com.tadpole.cloud.externalSystem.modular.mabang.model.params.ma;


import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 分仓调拨单sku明细
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AllocationWarehouseSku {

    @ApiModelProperty("sku")
    private String sku		            ;//调拨商品sku

    @ApiModelProperty("调拨数量")
    private  String num;

    @ApiModelProperty("类型: 1库存sku;2组合sku")
    private String type;

}
