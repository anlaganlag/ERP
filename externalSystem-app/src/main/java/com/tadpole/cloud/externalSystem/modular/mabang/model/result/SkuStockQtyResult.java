package com.tadpole.cloud.externalSystem.modular.mabang.model.result;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SkuStockQtyResult {

    /**
     *
     * 库存sku，多个用英文逗号隔开，最大支持100
     */
    private String stockSku;


    /**
     *  库存总数量
     */
    private String stockAllQuantity;




    /**
     * 仓库名称
     */
    private String warehouseName;


    /**
     * 仓库编码
     */
    private String warehouseId;


     /**
     * 库存数量
     */
    private String stockQuantity;

    /**
     * 未发货数量
     */
    private String waitingQuantity;





         /**
     * 库存数量
     */
    private String allotShippingQuantity;

    
     /**
     * 采购在途数量
     */
    private String shippingQuantity;


    
     /**
     * 加工在途量
     */
    private String processingQuantity;



    
     /**
     * fba未发货量
     */
    private String fbaWaitingQuantity;


    
     /**
     * 仓位
     */
    private String gridCode;


    












}
