package com.tadpole.cloud.externalSystem.config;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Setter
@Getter
@Component
@ConfigurationProperties(prefix = "k3cloud")
@Data
public class K3CloudConfig {

    private String ipaddress;
    private String username;
    private String password;

    private String adminUsername;
    private String adminPassword;


}
