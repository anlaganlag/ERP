package com.tadpole.cloud.externalSystem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

/**
* 物流服务
* @author AmteMa
* @date 2022/4/14
*/
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients(basePackages = {"com.tadpole.cloud.externalSystem.modular.*.consumer","cn.stylefeng.guns.cloud.libs.consumer"})
@ComponentScan(basePackages = {"com.tadpole.cloud.externalSystem","cn.stylefeng.guns.cloud.libs.authority.column","cn.stylefeng.guns.cloud.libs.util","cn.stylefeng.guns.cloud.system.api.objectLog.client"})
public class ExternalSystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(ExternalSystemApplication.class, args);
    }

}


