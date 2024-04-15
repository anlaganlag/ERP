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
public class MabangGoodsSearchParm {

    //api:hwc-create-allocation-warehouse

    @ApiModelProperty("库存sku")
    private String stockSku		            ;//库存sku

    @ApiModelProperty("创建起始时间")
    private Data timeCreatedStart; //创建起始时间

    @ApiModelProperty("创建结束时间")
    private Data timeCreatedEnd; //创建结束时间

    @ApiModelProperty("更新起始时间")
    private Data updateTimeStart; //更新起始时间

    @ApiModelProperty("更新结束时间")
    private Data updateTimeEnd; //更新结束时间

    @ApiModelProperty("当前页数，默认1")
    private Integer page; //当前页数，默认1

    @ApiModelProperty("当前每页条数，默认20，最大值为100")
    private Integer rowsPerPage; //当前每页条数，默认20，最大值为100

    @ApiModelProperty("1是0否返回虚拟SKU,默认0不返回")
    private Integer showVirtualSku; //1是0否返回虚拟SKU,默认0不返回

    @ApiModelProperty("1是0否返回默认供应商,默认0不返回")
    private Integer showProvider; //1是0否返回默认供应商,默认0不返回

    @ApiModelProperty("1是0否返回仓库信息,默认0不返回")
    private Integer showWarehouse; //1是0否返回仓库信息,默认0不返回

    @ApiModelProperty("1是0否返回自定义分类,默认0不返回")
    private Integer showLabel; //1是0否返回自定义分类,默认0不返回

    @ApiModelProperty("1是0否返回多属性,默认0不返回")
    private Integer showattributes; //1是0否返回多属性,默认0不返回

}
