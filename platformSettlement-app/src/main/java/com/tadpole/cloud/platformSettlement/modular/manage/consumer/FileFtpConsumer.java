package com.tadpole.cloud.platformSettlement.modular.manage.consumer;

import cn.stylefeng.guns.cloud.system.api.FileFtpApi;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * @author: ty
 * @description: 文件FTP服务消费者
 * @date: 2023/4/17
 */
@FeignClient(name = "guns-cloud-system")
public interface FileFtpConsumer extends FileFtpApi {

}
