package com.doci.webPrj.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Value("${profile.dir}")
    private String profileDir;

    @Value("${record.dir}")
    private String recordDir;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/upload/image/profile/**" , "/upload/image/record/**")
                .addResourceLocations("file:" +  profileDir , "file:" + recordDir);
    }
}