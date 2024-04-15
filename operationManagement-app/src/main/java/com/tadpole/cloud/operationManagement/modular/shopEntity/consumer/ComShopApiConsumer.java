package com.tadpole.cloud.operationManagement.modular.shopEntity.consumer;

import com.tadpole.cloud.externalSystem.api.ebms.ComShopApi;
import org.springframework.cloud.openfeign.FeignClient;


/**
 * @author: LSY
 * @description: EBMS店铺实体消费者
 * @date: 2023/8/14
 */
@FeignClient(name = "bsc-cloud-externalSystem")
public interface ComShopApiConsumer extends ComShopApi {

}
