package com.tadpole.cloud.platformSettlement.modular.manage.consumer;

import cn.stylefeng.guns.cloud.system.api.BaseSystemApi;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * 系统管理消费者
 *
 * @author fengshuonan
 * @Date 2019/9/25 18:32
 */
@FeignClient(name = "guns-cloud-system")
public interface SystemConsumer extends BaseSystemApi {
}
