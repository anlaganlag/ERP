package com.tadpole.cloud.operationManagement.modular.stock.consumer;

import com.tadpole.cloud.platformSettlement.api.sales.InventoryDemandServiceApi;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * @author: lsy
 * @description: 销售需求
 * @date: 2023/6/2
 */
@FeignClient(name = "bsc-cloud-platformSettlement")
public interface InventoryDemandServiceConsumer extends InventoryDemandServiceApi {

}
