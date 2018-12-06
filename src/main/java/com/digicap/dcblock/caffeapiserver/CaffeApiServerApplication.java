package com.digicap.dcblock.caffeapiserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@SpringBootApplication
@EnableAutoConfiguration
@EnableScheduling
@EnableWebSecurity // for JWT
@EnableGlobalMethodSecurity(prePostEnabled = true) // for JWT
public class CaffeApiServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(CaffeApiServerApplication.class, args);
	}
}
