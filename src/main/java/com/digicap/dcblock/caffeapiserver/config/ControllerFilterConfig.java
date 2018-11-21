package com.digicap.dcblock.caffeapiserver.config;

import com.digicap.dcblock.caffeapiserver.handler.ControllerFilter;
import com.digicap.dcblock.caffeapiserver.util.ApplicationProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.CommonsRequestLoggingFilter;

@Configuration
public class ControllerFilterConfig {

    ApplicationProperties properties;

    @Autowired
    public ControllerFilterConfig(ApplicationProperties properties) {
        this.properties = properties;
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
        filter.setIncludeHeaders(true);
        filter.setIncludePayload(true);
        filter.setIncludeQueryString(true);
        filter.setMaxPayloadLength(1000);
        return filter;
    }
}
