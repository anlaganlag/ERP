package com.tadpole.cloud.platformSettlement.modular.manage.consumer;

import cn.stylefeng.guns.cloud.system.api.base.RpMaterialApi;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * @author: ty
 * @description: 物料属性基础数据消费者
 * @date: 2023/4/17
 */
@FeignClient(name = "guns-cloud-system")
public interface RpMaterialConsumer extends RpMaterialApi {

}
