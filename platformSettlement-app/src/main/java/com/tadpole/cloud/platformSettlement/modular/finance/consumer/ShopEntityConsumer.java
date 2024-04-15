package com.tadpole.cloud.platformSettlement.modular.finance.consumer;

import com.tadpole.cloud.operationManagement.api.shopEntity.ShopEntityApi;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * @author: htj
 * @description: 财务结算消费店铺信息维护
 * @date: 2023/8/15
 */
@FeignClient(name = "bsc-cloud-operationManagement")
public interface ShopEntityConsumer extends ShopEntityApi {

}
