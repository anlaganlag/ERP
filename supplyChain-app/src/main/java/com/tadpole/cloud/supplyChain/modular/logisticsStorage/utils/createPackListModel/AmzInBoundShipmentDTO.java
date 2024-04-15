package com.tadpole.cloud.supplyChain.modular.logisticsStorage.utils.createPackListModel;

import com.tadpole.cloud.supplyChain.modular.logisticsStorage.entity.TbLogisticsPackList;
import lombok.Data;

@Data
public class AmzInBoundShipmentDTO extends TbLogisticsPackList {
    private String sellerId;
    private String marketplaceId;
    private String awsRegion;
    private String endpoint;
    private Integer pkShipId;

}
