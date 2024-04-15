package com.tadpole.cloud.platformSettlement.modular.inventory.consumer;

import com.tadpole.cloud.supplyChain.api.logistics.OverseasWarehouseManageApi;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * @author: ty
 * @description: 海外仓库存列表消费者
 * @date: 2023/4/17
 */
@FeignClient(name = "bsc-cloud-supplyChain")
public interface OverseasWarehouseManageConsumer extends OverseasWarehouseManageApi {

}
