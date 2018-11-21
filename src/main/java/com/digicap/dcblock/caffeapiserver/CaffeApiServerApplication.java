package com.digicap.dcblock.caffeapiserver;

import static springfox.documentation.builders.PathSelectors.regex;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableAutoConfiguration
//@EnableSwagger2
public class CaffeApiServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(CaffeApiServerApplication.class, args);
	}

//    @Bean
//    public Docket newsApi() {
//        return new Docket(DocumentationType.SWAGGER_2)
//            .groupName("greetings")
//            .apiInfo(apiInfo())
//            .select()
//            .paths(regex("/api/caffe.*"))
//            .build();
//    }
//
//    private ApiInfo apiInfo() {
//        return new ApiInfoBuilder()
//            .title("DcBlock Caffe API Server")
//            .version("1.0")
//            .build();
//    }
}
