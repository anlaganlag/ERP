package com.tadpole.cloud.platformSettlement.modular.manage.consumer;

import cn.stylefeng.guns.cloud.system.api.FileApi;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * @author: ty
 * @description: 文件服务消费者
 * @date: 2023/4/17
 */
@FeignClient(name = "guns-cloud-system")
public interface FileConsumer extends FileApi {

}
