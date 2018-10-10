package com.weiller.gateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

/**
 * 跨域设置支持
 *
 * @author weiller
 * @version 1.0 ,2018年10月9日11:09:32
 */
@Configuration
public class CorsConfig {

    @Bean
    public CorsFilter corsFilter(){
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowCredentials(true); // 允许cookies跨域
        configuration.addAllowedOrigin("*"); // #允许向该服务器提交请求的URI，*表示全部允许
        configuration.addAllowedHeader("*"); // #允许访问的头信息,*表示全部
        configuration.setMaxAge(18000L);     // 预检请求的缓存时间（秒），即在这个时间段里，对于相同的跨域请求不会再预检
        configuration.addAllowedMethod("*");// 允许提交请求的方法，*表示全部允许
        source.registerCorsConfiguration("/**",configuration);
        return new CorsFilter(source);
    }
}
