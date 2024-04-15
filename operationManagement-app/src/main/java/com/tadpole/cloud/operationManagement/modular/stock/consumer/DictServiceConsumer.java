package com.tadpole.cloud.operationManagement.modular.stock.consumer;

import cn.stylefeng.guns.cloud.system.api.base.DictServiceApi;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * @author: ty
 * @description: 领星基础数据消费者
 * @date: 2023/4/17
 */
@FeignClient(name = "guns-cloud-system")
public interface DictServiceConsumer extends DictServiceApi {

}
