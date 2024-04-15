package com.tadpole.cloud.externalSystem.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "mabang")
@Data
public class MabangConfig {
    private String  url ;
    private String  sec ;
    private String appkey;
    private String version;
    private String loginUrl;
}