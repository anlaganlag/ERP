/*
Copyright [2020] [https://www.stylefeng.cn]

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

  http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.

Guns采用APACHE LICENSE 2.0开源协议，您在使用过程中，需要注意以下几点：

1.请不要删除和修改根目录下的LICENSE文件。
2.请不要删除和修改Guns源码头部的版权声明。
3.请保留源码和相关描述文件的项目出处，作者声明等。
4.分发源码时候，请注明软件出处 https://gitee.com/stylefeng/guns-separation
5.在修改包名，模块名称，项目代码等时，请注明软件出处 https://gitee.com/stylefeng/guns-separation
6.若您的项目无法满足以上几点，可申请商业授权，获取Guns商业授权许可，请在官网购买授权，地址为 https://www.stylefeng.cn
 */
package com.tadpole.cloud.externalSystem.config;

import cn.hutool.core.util.RandomUtil;
import cn.stylefeng.guns.cloud.libs.config.properties.DruidProperties;
import cn.stylefeng.guns.cloud.system.core.dbs.DynamicDataSource;
import cn.stylefeng.guns.cloud.system.core.dbs.aop.MultiSourceExAop;
import com.alibaba.druid.support.http.StatViewServlet;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.util.HashMap;

/**
 * 多数据源druid配置
 *
 * @author fengshuonan
 * @date 2020/8/24
*/
@Configuration
@EnableTransactionManagement(proxyTargetClass = true)
@MapperScan(basePackages = {"com.tadpole.cloud.externalSystem.modular.*.mapper"})
@Slf4j
public class MultiDataSourceConfig {

    /**
     * druid配置
     */
    @Bean
    @ConfigurationProperties(prefix = "spring.datasource")
    public DruidProperties druidProperties() {
        return new DruidProperties();
    }
    /**
     * 多数据源的连接池
     *
     * @author fengshuonan
     * @date 2020/8/25
     */
    @Bean
    public DynamicDataSource dataSource() {
        return new DynamicDataSource();
    }


    /**
     * 数据源切换的AOP
     *
     * @author fengshuonan
     * @date 2020/8/25
     */
    @Bean
    public MultiSourceExAop multiSourceExAop() {
        return new MultiSourceExAop();
    }

    /**
     * druid监控，配置StatViewServlet
     *
     * @author xuyuxiang
     * @date 2020/6/28 16:03
     */
    @Bean
    public ServletRegistrationBean<StatViewServlet> druidServletRegistration() {

        // 随机生成druid账号和密码
        String account = RandomUtil.randomString(8);
        String password = RandomUtil.randomString(8);

        log.info("生成druid账号:{}，密码:{}", account, password);

        // 设置servelet的参数
        HashMap<String, String> statViewServletParams =new HashMap<>();
        statViewServletParams.put("resetEnable", "true");
        statViewServletParams.put("loginUsername", "account");
        statViewServletParams.put("loginPassword", "password");

        ServletRegistrationBean<StatViewServlet> registration = new ServletRegistrationBean<>(new StatViewServlet());
        registration.addUrlMappings("/druid/*");
        registration.setInitParameters(statViewServletParams);
        return registration;
    }


}
