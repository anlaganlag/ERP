package com.tadpole.cloud.supplyChain.modular.logisticsStorage.utils.createPackListModel;

import lombok.Data;

/**
 * @author:lsy
 * @description:
 * @date:2022/4/29
 */
@Data
public class AmzBoxSkuInfoDTO {
    private String merchantSKU;
    private String asin;
    private String title;
    private String fnsku;
    private Integer expectedQty;
    private String shipmentID;
    private Integer packDetBoxNum;
    private Integer quantity;
}
