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
public class PurchaseAddItem {

    @ApiModelProperty("库存sku")
    private String stockSku;//库存sku

    @ApiModelProperty("采购价格")
    private String price;//采购价格

    @ApiModelProperty("采购量")
    private String purchaseNum;//采购量

    @ApiModelProperty("商品备注")
    private String remark;//商品备注
}
