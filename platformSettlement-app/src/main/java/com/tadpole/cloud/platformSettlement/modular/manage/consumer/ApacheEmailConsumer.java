package com.tadpole.cloud.platformSettlement.modular.manage.consumer;

import cn.stylefeng.guns.cloud.system.api.base.ApacheEmailApi;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * @author: ty
 * @description: ApacheEmail邮件消费者
 * @date: 2023/4/18
 */
@FeignClient(name = "guns-cloud-system")
public interface ApacheEmailConsumer extends ApacheEmailApi {

}
