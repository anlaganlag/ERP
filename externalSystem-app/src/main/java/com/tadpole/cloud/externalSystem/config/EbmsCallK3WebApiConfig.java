package com.tadpole.cloud.externalSystem.config;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Setter
@Getter
@Component
@ConfigurationProperties(prefix = "ebms-call-k3-webapi")
@Data
public class EbmsCallK3WebApiConfig {
    private String userName;
    private String password;
    private String accountID;
    private String apiAccountURL;
    private String apiLoginURL;
    private String apiFbaURL;

}
