package org.doubleone;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class DoubleOneBackApplication {

  public static void main(String[] args) {
    SpringApplication.run(DoubleOneBackApplication.class, args);
  }

}
