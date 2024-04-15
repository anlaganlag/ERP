package com.tadpole.cloud.operationManagement.modular.shopEntity.consumer;

import cn.stylefeng.guns.cloud.system.api.base.JcSiteApi;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "guns-cloud-system")
public interface JcSiteApiConsumer extends JcSiteApi {
}
