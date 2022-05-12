package com.fmi.cardealership.config;

import com.fmi.cardealership.service.FileStorageService;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Bean
    public ModelMapper modelMapper(FileStorageService fileStorageService) {
        return new ModelMapper();
    }
}
