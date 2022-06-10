package com.fmi.cardealership.config;

import com.fmi.cardealership.dto.CreatePaymentDto;
import com.fmi.cardealership.dto.PaymentDto;
import com.fmi.cardealership.model.Payment;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.modelmapper.TypeMap;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {
    @Bean
    public ModelMapper modelMapper() {
        final ModelMapper modelMapper = new ModelMapper();
        modelMapper.addMappings(new PropertyMap<CreatePaymentDto, Payment>() {
            @Override
            protected void configure() {
                skip(destination.getId());
            }
        });

        TypeMap<Payment, PaymentDto> typeMap = modelMapper.createTypeMap(Payment.class, PaymentDto.class);
        typeMap.addMappings(mapper -> mapper.map(src -> src.getCar().getId(), PaymentDto::setCarId));
        typeMap.addMappings(mapper -> mapper.map(src -> src.getUser().getId(), PaymentDto::setUserId));
        return modelMapper;
    }
}