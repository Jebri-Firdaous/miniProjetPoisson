package com.poisson.backend.Config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/poissons/**")
                .addResourceLocations("file:/home/firdaous/Bureau/stageING-ETE2025/miniProjetPoisson/images/poissons/");
    }
}
