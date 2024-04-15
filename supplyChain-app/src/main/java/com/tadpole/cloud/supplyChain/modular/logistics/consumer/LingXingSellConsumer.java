package com.tadpole.cloud.supplyChain.modular.logistics.consumer;

import com.tadpole.cloud.externalSystem.api.lingxing.LingXingSellApi;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * @author: ty
 * @description: 领星销售消费者
 * @date: 2023/6/5
 */
@FeignClient(name = "bsc-cloud-externalSystem")
public interface LingXingSellConsumer extends LingXingSellApi {

}
