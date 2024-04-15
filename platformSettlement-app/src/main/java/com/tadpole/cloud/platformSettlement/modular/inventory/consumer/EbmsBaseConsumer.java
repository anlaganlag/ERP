package com.tadpole.cloud.platformSettlement.modular.inventory.consumer;

import com.tadpole.cloud.externalSystem.api.ebms.EbmsBaseApi;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * @author: ty
 * @description: EBMS基础信息消费者
 * @date: 2023/4/17
 */
@FeignClient(name = "bsc-cloud-externalSystem")
public interface EbmsBaseConsumer extends EbmsBaseApi {

}
