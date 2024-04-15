package com.tadpole.cloud.operationManagement.modular.stock.consumer;


import cn.stylefeng.guns.cloud.system.api.base.EntUserServiceApi;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * @author: lsy
 * @description: 用户信息消费者
 * @date: 2023/6/02
 */
@FeignClient(name = "guns-cloud-system")
public interface EntUserServiceConsumer extends EntUserServiceApi {

}
