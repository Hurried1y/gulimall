package com.example.gulimall.gateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;

/**
 * User：H11
 * Date：2022/11/16
 * Description：
 */
@Configuration
public class GulimallCorsConfiguration {
    @Bean
    public CorsWebFilter corsWebFilter(){
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        //添加跨域配置
        corsConfiguration.addAllowedHeader("*"); //允许哪些请求头跨域
        corsConfiguration.addAllowedMethod("*"); //允许哪些请求方法跨域
        corsConfiguration.addAllowedOrigin("*"); //允许哪些来源跨域
        corsConfiguration.setAllowCredentials(true); //允许携带cookie进行跨域，否则跨域会丢失cookie
        //任意路径都要添加跨域陪住
        source.registerCorsConfiguration("/**", corsConfiguration);
        return new CorsWebFilter(source);
    }
}
