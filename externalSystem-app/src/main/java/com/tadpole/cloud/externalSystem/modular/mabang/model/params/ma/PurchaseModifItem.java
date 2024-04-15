package com.tadpole.cloud.externalSystem.modular.mabang.model.params.ma;


import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 修改采购单
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PurchaseModifItem {

    @ApiModelProperty("库存sku")
    private String stockSku;            //库存sku     必须

    @ApiModelProperty("采购数量,不支持修改成空,添加采购单商品时必填")
    private String purchaseNum;         //采购数量,不支持修改成空,添加采购单商品时必填

    @ApiModelProperty("采购价格,不支持修改成空,添加采购单商品时必填")
    private String sellPrice;           //采购价格,不支持修改成空,添加采购单商品时必填
}
