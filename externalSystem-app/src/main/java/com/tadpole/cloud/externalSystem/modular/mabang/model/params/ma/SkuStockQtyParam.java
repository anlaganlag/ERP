package com.tadpole.cloud.externalSystem.modular.mabang.model.params.ma;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SkuStockQtyParam {

    /**
     *
     * 库存sku，多个用英文逗号隔开，最大支持100
     */
    private String stockSkus;


    /**
     * 更新时间；注：stockSkus填写时，此参数将无效  2021-05-01（以天维度进行查询）
     */
    private String updateTime;




    /**
     * 仓库名称
     */
    private String warehouseName;


    /**
     * 当前页数，默认1
     */
    private  int page=1;




}
