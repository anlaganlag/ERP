package com.tadpole.cloud.externalSystem.modular.mabang.consumer;

import com.tadpole.cloud.platformSettlement.api.finance.PaymentConfirmVoucherApi;
import org.springframework.cloud.openfeign.FeignClient;


@FeignClient(name = "bsc-cloud-platformSettlement")
public interface PaymentConfirmVoucherConsumer extends PaymentConfirmVoucherApi {
}
