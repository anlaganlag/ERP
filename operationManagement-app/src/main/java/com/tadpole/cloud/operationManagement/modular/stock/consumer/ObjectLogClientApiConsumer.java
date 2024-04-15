package com.tadpole.cloud.operationManagement.modular.stock.consumer;


import cn.stylefeng.guns.cloud.system.api.objectLog.ObjectLogClientApi;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "guns-cloud-system")
public interface ObjectLogClientApiConsumer extends ObjectLogClientApi {
}
