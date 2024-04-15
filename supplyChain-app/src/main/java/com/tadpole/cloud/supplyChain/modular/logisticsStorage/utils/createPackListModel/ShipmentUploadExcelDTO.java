package com.tadpole.cloud.supplyChain.modular.logisticsStorage.utils.createPackListModel;

import lombok.Data;

import java.util.List;

@Data
public class ShipmentUploadExcelDTO {
    /**
     * 货件信息
     */
    private AmzInBoundShipmentDTO amzInBoundShipment;
    /**
     * 货件SKU信息
     */
    private List<AmzBoxSkuInfoDTO> amzBoxSkuInfoList;
    /**
     * 货件箱子信息
     */
    private List<AmzBoxInfoDTO> amzBoxInfoList;

}
