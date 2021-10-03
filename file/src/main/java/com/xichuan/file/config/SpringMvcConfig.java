package com.xichuan.file.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class SpringMvcConfig implements WebMvcConfigurer {
    @Value("${file.path}")
    private String FILE_PATH ;//= "F:\\Data\\JAVA\\course\\";

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        System.out.println("SpringMvcConfig FILE_PATH"+FILE_PATH);
        System.out.println("SpringMvcConfig FILE_PATH"+FILE_PATH);
        System.out.println("SpringMvcConfig FILE_PATH"+FILE_PATH);
        registry.addResourceHandler("/f/**").addResourceLocations("file:"+FILE_PATH );
    }
}