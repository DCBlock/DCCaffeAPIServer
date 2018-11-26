package com.digicap.dcblock.caffeapiserver.config;

import com.digicap.dcblock.caffeapiserver.util.ApplicationProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.CommonsRequestLoggingFilter;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class ControllerHandlerConfig implements WebMvcConfigurer {

    private ApplicationProperties properties;

    private HandlerInterceptor interceptor;

    @Autowired
    public ControllerHandlerConfig(ApplicationProperties properties, HandlerInterceptor interceptor) {
        this.properties = properties;
        this.interceptor = interceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(interceptor)
            .addPathPatterns("/api/caffe/**");
//            .excludePathPatterns("/public/**");
    }

//    @Bean
//    public FilterRegistrationBean<ControllerFilter> getFilterRegistrationBean() {
//        FilterRegistrationBean<ControllerFilter> registrationBean = new FilterRegistrationBean();
//        registrationBean.setFilter(new ControllerFilter(properties));
//        registrationBean.addUrlPatterns("/api/caffe/*");
//        return registrationBean;
//    }

    @Bean
    public CommonsRequestLoggingFilter requestLoggingFilter() {
        CommonsRequestLoggingFilter filter = new CommonsRequestLoggingFilter();
        filter.setIncludeClientInfo(true);
        filter.setIncludePayload(true);
        filter.setIncludeQueryString(true);
        return filter;
    }
}
