package com.binance.sdk.common.Properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "binance")
public class BinanceProperties {
    private String api;
    private String secret;
    private boolean logging;
}
