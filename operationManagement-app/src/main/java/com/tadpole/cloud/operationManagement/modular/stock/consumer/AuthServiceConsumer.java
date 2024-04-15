package com.tadpole.cloud.operationManagement.modular.stock.consumer;

import cn.stylefeng.guns.cloud.auth.api.AuthService;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * 鉴权消费者
 *
 * @author fengshuonan
 * @Date 2019-08-12 18:52
 */
@FeignClient(name = "guns-cloud-auth")
public interface AuthServiceConsumer extends AuthService {

}
