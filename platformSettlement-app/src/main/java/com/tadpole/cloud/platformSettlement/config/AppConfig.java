/**
 * Copyright 2018-2020 stylefeng & fengshuonan (sn93@qq.com)
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.tadpole.cloud.platformSettlement.config;

import cn.stylefeng.guns.cloud.libs.config.ContextConfig;
import cn.stylefeng.guns.cloud.libs.web.error.GlobalErrorView;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * 默认web配置
 *
 * @author fengshuonan
 * @Date 2018/7/24 下午3:27
 */
@Configuration
@Import(ContextConfig.class)
public class AppConfig {

    /**
     * 默认错误页面，返回json
     */
    @Bean("error")
    public GlobalErrorView error() {
        return new GlobalErrorView();
    }

}


