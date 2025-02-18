package org.doubleone;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
@OpenAPIDefinition(
    servers = {
        @Server(url = "https://api.doubleone.p-e.kr", description = "Default Server URL")
    }
)
public class DoubleOneBackApplication {

  public static void main(String[] args) {
    SpringApplication.run(DoubleOneBackApplication.class, args);
  }

}
