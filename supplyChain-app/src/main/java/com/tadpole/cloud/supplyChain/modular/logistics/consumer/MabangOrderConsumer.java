package com.tadpole.cloud.supplyChain.modular.logistics.consumer;

import com.tadpole.cloud.externalSystem.api.mabang.MabangOrderApi;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * @author: ty
 * @description: 马帮订单信息消费者
 * @date: 2023/11/21
 */
@FeignClient(name = "bsc-cloud-externalSystem")
public interface MabangOrderConsumer extends MabangOrderApi {
}
