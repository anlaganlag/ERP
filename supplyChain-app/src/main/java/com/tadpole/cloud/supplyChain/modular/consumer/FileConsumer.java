package com.tadpole.cloud.supplyChain.modular.manage.consumer;

import cn.stylefeng.guns.cloud.system.api.FileApi;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * @author: lsy
 * @description: 文件服务消费者
 * @date: 2024/3/20
 */
@FeignClient(name = "guns-cloud-system")
public interface FileConsumer extends FileApi {

}
