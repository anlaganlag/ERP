package com.tadpole.cloud.externalSystem.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "k3webapi")
@Data
public class K3CloudWebapiConfig {
    private String  acctid ;
    private String  username ;
    private String appid;
    private String appsec;
    private String serverurl;
    private String loginurl;
}