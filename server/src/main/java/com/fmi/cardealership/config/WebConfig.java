package com.fmi.cardealership.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/cars/**").allowedOrigins("*").allowedMethods("GET", "POST","PUT", "DELETE");
        registry.addMapping("/api/payments/**").allowedOrigins("*").allowedMethods("GET", "POST","PUT", "DELETE");
        registry.addMapping("/api/users/**").allowedOrigins("*").allowedMethods("GET", "POST","PUT", "DELETE");
        registry.addMapping("/api/login").allowedOrigins("*").allowedMethods("POST");
        registry.addMapping("/api/register").allowedOrigins("*").allowedMethods("POST");
    }
}