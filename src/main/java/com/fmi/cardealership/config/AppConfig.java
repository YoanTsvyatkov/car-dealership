package com.fmi.cardealership.config;

import com.fmi.cardealership.service.FileStorageService;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Bean
    public ModelMapper modelMapper(FileStorageService fileStorageService) {
        ModelMapper modelMapper = new ModelMapper();
//        TypeMap<Car, CarDto> propertyMapper = modelMapper.createTypeMap(Car.class, CarDto.class);
//        Converter<String, Resource> fileNameToResource = f -> fileStorageService.loadFileAsResource(f.getSource());
//
//        propertyMapper.addMappings(
//                mapper -> mapper.using(fileNameToResource).map(Car::getPhoto, CarDto::setPhoto)
//        );
        return modelMapper;
    }
}
