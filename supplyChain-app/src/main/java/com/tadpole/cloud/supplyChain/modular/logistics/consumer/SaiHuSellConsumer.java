package com.tadpole.cloud.supplyChain.modular.logistics.consumer;

import com.tadpole.cloud.externalSystem.api.saihu.model.SaiHuSellApi;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * @author: ty
 * @description: 赛狐销售消费者
 * @date: 2024/2/23
 */
@FeignClient(name = "bsc-cloud-externalSystem")
public interface SaiHuSellConsumer extends SaiHuSellApi {

}
