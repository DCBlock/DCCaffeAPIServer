package com.digicap.dcblock.caffeapiserver.config;

//import com.digicap.dcblock.caffeapiserver.handler.RestAuthenticationFailureHandler;

/**
 * JWT 검증 Class.
 * 
 * @author DigiCAP
 */
//@Configuration
//@EnableResourceServer
//public class ResourceServerConfig extends ResourceServerConfigurerAdapter {
//
//    @Override
//    public void configure(HttpSecurity http) throws Exception {
//    }
//    
//    @Override
//    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {        
//        // Set Error Handler.
////        resources.authenticationEntryPoint(customAuthEntryPoint());
////        resources.resourceId(resourceId);
//    }
//
////    @Bean
////    @Primary
////    public DefaultTokenServices tokenService() {
////        DefaultTokenServices defaultTokenServices = new DefaultTokenServices();
////        defaultTokenServices.setTokenStore(tokenStore());
////        defaultTokenServices.setSupportRefreshToken(true);
////        return defaultTokenServices;
////    }
//
//    @Bean
//    public TokenStore tokenStore() {
//        return new JwtTokenStore(accessTokenConverter());
//    }  
//
//    @Bean
//    public JwtAccessTokenConverter accessTokenConverter() {     
//        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
////        converter.setSigningKey("1234");
////        converter.setVerifierKey(publicKey);
//        return converter;
//    }
//
//    @Bean
//    public AuthenticationEntryPoint customAuthEntryPoint(){
//        return new RestAuthenticationFailureHandler();
//    }
//}
