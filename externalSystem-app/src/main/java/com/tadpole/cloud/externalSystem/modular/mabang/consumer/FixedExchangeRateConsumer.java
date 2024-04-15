package com.tadpole.cloud.externalSystem.modular.mabang.consumer;

import com.tadpole.cloud.platformSettlement.api.finance.FixedExchangeRateApi;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * @author: ty
 * @description: ERP固定汇率消费者
 * @date: 2023/4/13
 */
@FeignClient(name = "bsc-cloud-platformSettlement")
public interface FixedExchangeRateConsumer extends FixedExchangeRateApi {

}
