package com.tadpole.cloud.supplyChain.modular.logistics.consumer;

import com.tadpole.cloud.platformSettlement.api.inventory.RemovalShipmentDetailApi;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * @author: ty
 * @description: 移除货件详情报告消费者
 * @date: 2023/4/13
 */
@FeignClient(name = "bsc-cloud-platformSettlement")
public interface RemovalShipmentDetailConsumer extends RemovalShipmentDetailApi {

}
