package com.digicap.dcblock.caffeapiserver.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.security.web.AuthenticationEntryPoint;

import com.digicap.dcblock.caffeapiserver.handler.RestAuthenticationFailureHandler;

/**
 * JWT 검증 Class.
 * 
 * @author DigiCAP
 *
 */
@Configuration
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {

    @Bean
    public TokenStore tokenStore() {
        return new JwtTokenStore(accessTokenConverter());
    }
    
    @Bean
    public JwtAccessTokenConverter accessTokenConverter() {     
        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
        //converter.setSigningKey("secret");
        //converter.setVerifierKey(publicKey);
        return converter;
    }
    
    @Bean
    @Primary
    public DefaultTokenServices tokenService() {
        DefaultTokenServices defaultTokenServices = new DefaultTokenServices();
        defaultTokenServices.setTokenStore(tokenStore());
//        defaultTokenServices.setSupportRefreshToken(true);
        return defaultTokenServices;
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
//        .antMatchers("/api/caffe/menus/**").hasRole("admin")
//        .antMatchers("/api/caffe/menus/**").hasAuthority("company")
        ;
    }

    @Bean
    public AuthenticationEntryPoint customAuthEntryPoint(){
        return new RestAuthenticationFailureHandler();
    }
    
    @Override
    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {        
        // Set Error Handler.
        resources.authenticationEntryPoint(customAuthEntryPoint());
//        resources.resourceId(resourceId);
    }
}
