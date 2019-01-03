package com.digicap.dcblock.caffeapiserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableAutoConfiguration
@EnableScheduling
//@EnableWebSecurity // for JWT
//@EnableGlobalMethodSecurity(prePostEnabled = true) // for JWT
public class CaffeApiServerApplication {

  public static void main(String[] args) {
    SpringApplication.run(CaffeApiServerApplication.class, args);
  }
}
