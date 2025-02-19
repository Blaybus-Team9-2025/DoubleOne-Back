package org.doubleone;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;


@SpringBootApplication
//@EnableFeignClients(basePackages = "org.doubleone.global.feign")
public class DoubleOneBackApplication {
  public static void main(String[] args) {
    SpringApplication.run(DoubleOneBackApplication.class, args);
  }
}

