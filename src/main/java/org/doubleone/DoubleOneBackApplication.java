package org.doubleone;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;


@SpringBootApplication
@EnableJpaAuditing
@OpenAPIDefinition(
    servers = {
        @Server(url = "https://api.doubleone.p-e.kr", description = "Default Server URL")
    }
)
//@EnableFeignClients(basePackages = "org.doubleone.global.feign")
public class DoubleOneBackApplication {

  public static void main(String[] args) {
    SpringApplication.run(DoubleOneBackApplication.class, args);
  }

}
