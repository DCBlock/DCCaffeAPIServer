package com.digicap.dcblock.caffeapiserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableAutoConfiguration
@EnableScheduling
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
