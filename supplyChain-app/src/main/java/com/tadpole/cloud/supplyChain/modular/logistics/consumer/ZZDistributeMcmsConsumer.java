package com.tadpole.cloud.supplyChain.modular.logistics.consumer;

import com.tadpole.cloud.platformSettlement.api.inventory.ZZDistributeMcmsApi;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * @author: ty
 * @description: K3物料分配入库消费者
 * @date: 2023/4/14
 */
@FeignClient(name = "bsc-cloud-platformSettlement")
public interface ZZDistributeMcmsConsumer extends ZZDistributeMcmsApi {

}
