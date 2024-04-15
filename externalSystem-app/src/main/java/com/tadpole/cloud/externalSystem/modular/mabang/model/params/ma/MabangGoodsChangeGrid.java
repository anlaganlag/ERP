package com.tadpole.cloud.externalSystem.modular.mabang.model.params.ma;


import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 商品修改仓位
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MabangGoodsChangeGrid {

    //api:stock-change-grid

    @ApiModelProperty("库存sku")
    private String stockSku		            ;//库存sku

    @ApiModelProperty("仓库ID")
    private String warehouseId		            ;//仓库ID

    @ApiModelProperty("仓位号")
    private String gridCode	            ;//仓位号

}
