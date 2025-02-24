package com.binance.sdk;

import org.springframework.context.annotation.Import;
import com.binance.sdk.common.config.AutoConfig;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)  // 클래스, 인터페이스, 애너테이션에서 사용 가능
@Retention(RetentionPolicy.RUNTIME)  // 런타임에도 유지됨
@Import({AutoConfig.class})  // 자동으로 Import할 클래스 목록
public @interface EnabledBinance {
}
