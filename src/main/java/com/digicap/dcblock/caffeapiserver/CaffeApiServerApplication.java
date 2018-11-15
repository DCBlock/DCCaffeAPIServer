package com.digicap.dcblock.caffeapiserver;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableAutoConfiguration
public class CaffeApiServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(CaffeApiServerApplication.class, args);
	}
}
