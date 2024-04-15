package com.tadpole.cloud.platformSettlement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
* 物流服务
* @author AmteMa
* @date 2022/4/14
*/
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients(basePackages = {"com.tadpole.cloud.platformSettlement.modular.*.consumer","cn.stylefeng.guns.cloud.libs.consumer"})
public class PlatformSettlementApplication {

    public static void main(String[] args) {
        SpringApplication.run(PlatformSettlementApplication.class, args);
    }

}


