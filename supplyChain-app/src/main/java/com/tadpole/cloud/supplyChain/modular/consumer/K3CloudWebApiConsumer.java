package com.tadpole.cloud.supplyChain.modular.manage.consumer;

import com.tadpole.cloud.externalSystem.api.k3.K3CloudWebApi;
import org.springframework.cloud.openfeign.FeignClient;


/**
 * k3系统webapi接口调用
 */
@FeignClient(name = "bsc-cloud-externalSystem")
public interface K3CloudWebApiConsumer extends K3CloudWebApi {
}
