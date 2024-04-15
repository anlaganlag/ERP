package com.tadpole.cloud.externalSystem.modular.mabang.model.params.ma;


import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 创建分仓调拨单
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateAllocationWarehouseParm {

    //api:hwc-create-allocation-warehouse

    @ApiModelProperty("调拨商品清单")
    private String sku		            ;//调拨商品清单

    @ApiModelProperty("起始仓库ID")
    private Integer startWarehouseId    ;//起始仓库ID

    @ApiModelProperty("目标仓库ID")
    private Integer targetWarehouseId   ;//目标仓库ID

    @ApiModelProperty("运输方式 1.陆地运输 2.空运 3.海运")
    private Integer transportType	    ;//运输方式 1.陆地运输 2.空运 3.海运

    @ApiModelProperty("运费")
    private String freight	            ;//运费

    @ApiModelProperty("物流渠道")
    private String channel	            ;//物流渠道

    @ApiModelProperty("物流单号")
    private String trackNumber	        ;//物流单号

    @ApiModelProperty("运费分摊方式 1.重量,2体积重")
    private String shareMethod	        ;//运费分摊方式 1.重量,2体积重

    @ApiModelProperty("备注")
    private String remark	            ;//备注

}
