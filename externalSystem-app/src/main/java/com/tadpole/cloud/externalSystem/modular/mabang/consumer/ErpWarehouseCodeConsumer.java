package com.tadpole.cloud.externalSystem.modular.mabang.consumer;

import com.tadpole.cloud.platformSettlement.api.inventory.ErpWarehouseCodeApi;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * @author: ty
 * @description: ERP仓库组织编码消费者
 * @date: 2023/4/14
 */
@FeignClient(name = "bsc-cloud-platformSettlement")
public interface ErpWarehouseCodeConsumer extends ErpWarehouseCodeApi {

}
