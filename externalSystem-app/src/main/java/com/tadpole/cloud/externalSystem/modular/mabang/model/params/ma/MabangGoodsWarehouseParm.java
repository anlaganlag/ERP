package com.tadpole.cloud.externalSystem.modular.mabang.model.params.ma;


import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MabangGoodsWarehouseParm {

    @ApiModelProperty("仓库名称,必填")
    private String name;//仓库名称,必填

    @ApiModelProperty("仓库成本价")
    private String stockCost;//仓库成本价

    @ApiModelProperty("1:默认仓库2.非默认仓库")
    private String isDefault;//1:默认仓库2.非默认仓库

    @ApiModelProperty("采购天数")
    private String purchaseDays;//采购天数

    @ApiModelProperty("最小采购数量")
    private String minPurchaseQuantity;//最小采购数量

}
