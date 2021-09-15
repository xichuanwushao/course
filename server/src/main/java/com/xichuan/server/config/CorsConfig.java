package com.xichuan.server.config;

import com.xichuan.server.ServerApplication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {
    private static final Logger logger = LoggerFactory.getLogger(ServerApplication.class);

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        logger.warn("server模块跨域配置已经失效 使用路由模块跨域配置......");
//        logger.warn("server模块......跨域配置已经加载......");
//        registry.addMapping("/**")
//                .allowedOrigins("*")
//                .allowedHeaders(CorsConfiguration.ALL)
//                .allowedMethods(CorsConfiguration.ALL)
//                .allowCredentials(true)
//                .maxAge(3600); // 1小时内不需要再预检（发OPTIONS请求）
    }

}
