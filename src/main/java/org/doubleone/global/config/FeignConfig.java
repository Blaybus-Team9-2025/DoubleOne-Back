package org.doubleone.global.config;

import feign.Logger;
import feign.RequestInterceptor;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableFeignClients(basePackages = "org.doubleone.global.feign")
public class FeignConfig {

  @Bean
  Logger.Level feignLoggerLevel() {
    return Logger.Level.FULL;
  }

  @Bean
  public RequestInterceptor requestInterceptor() {
    return requestTemplate -> requestTemplate.header("Content-type",
        "application/x-www-form-urlencoded;charset=utf-8");
  }
}
