package com.tadpole.cloud.externalSystem.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "oa")
@Data
public class OaConfig {
    private String  url ;
    private String appid;
}