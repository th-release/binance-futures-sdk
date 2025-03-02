package com.binance.sdk.common.config;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import com.binance.sdk.common.Properties.BinanceProperties;

@Configuration
@EnableConfigurationProperties({BinanceProperties.class})
@RequiredArgsConstructor
@ComponentScan(basePackages = "com.binance.sdk")
public class AutoConfig {
}
