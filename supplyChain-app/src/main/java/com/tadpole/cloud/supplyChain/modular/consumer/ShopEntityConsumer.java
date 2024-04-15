package com.tadpole.cloud.supplyChain.modular.manage.consumer;

import com.tadpole.cloud.operationManagement.api.shopEntity.ShopEntityApi;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * @author: lsy
 * @description: 店铺信息
 * @date: 2023/8/15
 */
@FeignClient(name = "bsc-cloud-operationManagement")
public interface ShopEntityConsumer extends ShopEntityApi {

}
