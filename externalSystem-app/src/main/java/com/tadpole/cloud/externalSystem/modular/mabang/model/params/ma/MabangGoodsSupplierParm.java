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
public class MabangGoodsSupplierParm {

    @ApiModelProperty("供应商名称,必填")
    private String name;//供应商名称,必填

    @ApiModelProperty("供应商地址")
    private String productLinkAddress;//供应商地址

    @ApiModelProperty("1:默认供应商 2.非默认供应商")
    private String flag;//1:默认供应商 2.非默认供应商

}
