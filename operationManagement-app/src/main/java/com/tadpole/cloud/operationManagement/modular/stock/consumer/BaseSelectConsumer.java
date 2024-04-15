package com.tadpole.cloud.operationManagement.modular.stock.consumer;

import cn.stylefeng.guns.cloud.system.api.base.BaseSelectApi;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * @author: ty
 * @description: 基础公共信息消费者
 * @date: 2023/4/12
 */
@FeignClient(name = "guns-cloud-system")
public interface BaseSelectConsumer extends BaseSelectApi {

}
