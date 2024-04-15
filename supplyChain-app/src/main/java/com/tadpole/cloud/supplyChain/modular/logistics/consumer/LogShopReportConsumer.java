package com.tadpole.cloud.supplyChain.modular.logistics.consumer;

import com.tadpole.cloud.operationManagement.api.shopEntity.LogShopReportApi;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * @author: ty
 * @description: 海外仓调用EBMS系统消费者
 * @date: 2023/4/14
 */
@FeignClient(name = "bsc-cloud-operationManagement")

public interface LogShopReportConsumer extends LogShopReportApi {

}
