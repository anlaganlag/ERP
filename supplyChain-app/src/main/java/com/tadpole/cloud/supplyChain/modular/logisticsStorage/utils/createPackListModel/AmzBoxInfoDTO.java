package com.tadpole.cloud.supplyChain.modular.logisticsStorage.utils.createPackListModel;

import lombok.Data;

/**
 * @author:lsy
 * @description:
 * @date:2022/5/5
 */
@Data
public class AmzBoxInfoDTO {
    private Integer packDetBoxNum;
    private Double packDetBoxLength;
    private Double packDetBoxWidth;
    private Double packDetBoxHeight;
    private double packDetBoxWeight;

}
