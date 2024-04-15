package com.tadpole.cloud.externalSystem.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "saihu")
@Data
public class SaihuConfig {
    private String  baseUrl ;
    private String  client_secret ;
    private String client_id;
    private String grant_type;
}