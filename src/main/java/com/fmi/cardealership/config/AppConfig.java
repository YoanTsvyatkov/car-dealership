package com.fmi.cardealership.config;

import com.fmi.cardealership.dto.PaymentDto;
import com.fmi.cardealership.model.Payment;
import com.fmi.cardealership.model.User;
import com.fmi.cardealership.service.CarService;
import com.fmi.cardealership.service.FileStorageService;
import com.fmi.cardealership.service.UserService;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {
    private final ModelMapper modelMapper = new ModelMapper();

    @Autowired
    @Bean
    public ModelMapper modelMapper(FileStorageService fileStorageService, UserService userService, CarService carService) {
        createTypeMapperDtoToEntity(carService, userService);
        createTypeMapperEntityToDto();
        return modelMapper;
    }

    private void createTypeMapperEntityToDto() {
        TypeMap<Payment, PaymentDto> typeMap = modelMapper.createTypeMap(Payment.class, PaymentDto.class);
        typeMap.addMappings(mapper -> mapper.map(src -> src.getCar().getId(), PaymentDto::setCarId));
        typeMap.addMappings(mapper -> mapper.map(src -> src.getUser().getId(), PaymentDto::setUserID));

    }

    private void createTypeMapperDtoToEntity(CarService carService, UserService userService) {
        TypeMap<PaymentDto, Payment> typeMap = modelMapper.createTypeMap(PaymentDto.class, Payment.class);
        typeMap.addMappings(mapper -> mapper.map(src -> carService.getCarById(src.getCarId()), Payment::setCar));
        typeMap.addMappings(mapper -> mapper.map(src -> userService.getUserById(src.getUserID()), Payment::setUser));

    }
}
