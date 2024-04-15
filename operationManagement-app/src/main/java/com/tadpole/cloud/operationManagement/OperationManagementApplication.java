package com.tadpole.cloud.operationManagement;


import cn.stylefeng.guns.cloud.libs.config.*;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;

/**
 * 运营管理
 *
 * @author AmteMa
 * @date 2022/4/14
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients(basePackages = {"com.tadpole.cloud.operationManagement.modular.*.consumer", "cn.stylefeng.guns.cloud.libs.consumer"})
@ComponentScan(basePackages = {"com.tadpole.cloud.operationManagement", "cn.stylefeng.guns.cloud.libs","cn.stylefeng.guns.cloud.system.api.objectLog.client"},
        excludeFilters = {@ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE,
                value = {ContextConfig.class, DataSourceConfig.class}
        )})

public class OperationManagementApplication {

    public static void main(String[] args) {

        SpringApplication.run(OperationManagementApplication.class, args);
    }

}


