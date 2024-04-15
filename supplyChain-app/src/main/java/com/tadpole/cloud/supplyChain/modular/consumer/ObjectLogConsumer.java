package com.tadpole.cloud.supplyChain.modular.consumer;

import cn.stylefeng.guns.cloud.system.api.BaseSystemApi;
import cn.stylefeng.guns.cloud.system.api.objectLog.ObjectLogClientApi;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * 系统管理消费者
 *
 * @author fengshuonan
 * @Date 2019/9/25 18:32
 */
@FeignClient(name = "guns-cloud-system")
public interface ObjectLogConsumer extends ObjectLogClientApi {
}
