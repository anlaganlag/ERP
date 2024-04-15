package com.tadpole.cloud.product.modular.consumer;

import cn.stylefeng.guns.cloud.system.api.ResourceService;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * 鉴权消费者
 *
 * @author fengshuonan
 * @Date 2019-08-12 18:52
 */
@FeignClient(name = "guns-cloud-system")
public interface ResourceServiceConsumer extends ResourceService {

}
