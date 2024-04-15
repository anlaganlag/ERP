package com.tadpole.cloud.product.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "printlabel")
@Data
public class PrintLabelConfig {
    private String  kfyServerUrl ;
}