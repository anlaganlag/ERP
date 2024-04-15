package com.tadpole.cloud.externalSystem.modular.mabang.consumer;


import cn.stylefeng.guns.cloud.system.api.objectLog.ObjectLogClientApi;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "guns-cloud-system")
public interface ObjectLogClientApiConsumer extends ObjectLogClientApi {
}
