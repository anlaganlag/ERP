package com.tadpole.cloud.product;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

/**
* 新品服务
* @author AmteMa
* @date 2022/4/14
*/
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients(basePackages = {"com.tadpole.cloud.product.modular.consumer","cn.stylefeng.guns.cloud.libs.consumer"})
@ComponentScan(basePackages = {"com.tadpole.cloud.product","cn.stylefeng.guns.cloud.libs.authority.column","cn.stylefeng.guns.cloud.system.api.objectLog.client"})
public class ProductApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProductApplication.class, args);
    }

}


