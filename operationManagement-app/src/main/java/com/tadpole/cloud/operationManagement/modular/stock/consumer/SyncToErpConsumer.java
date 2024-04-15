package com.tadpole.cloud.operationManagement.modular.stock.consumer;

import com.tadpole.cloud.externalSystem.api.k3.SyncToErpApi;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * @author: ty
 * @description: 同步K3消费者
 * @date: 2023/4/19
 */
@FeignClient(name = "bsc-cloud-externalSystem")
public interface SyncToErpConsumer extends SyncToErpApi {

}
