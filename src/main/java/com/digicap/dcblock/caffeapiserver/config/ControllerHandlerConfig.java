package com.digicap.dcblock.caffeapiserver.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class ControllerHandlerConfig implements WebMvcConfigurer {

    private HandlerInterceptor handlerInterceptor;

    @Autowired
    public void setSecurityInterceptor(HandlerInterceptor securityInterceptor) {
        this.handlerInterceptor = securityInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(handlerInterceptor).addPathPatterns("/api/caffe/**");
    }
}
