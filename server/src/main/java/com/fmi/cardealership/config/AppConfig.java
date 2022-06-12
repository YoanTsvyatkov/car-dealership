package com.fmi.cardealership.config;

import com.fmi.cardealership.dto.CreatePaymentDto;
import com.fmi.cardealership.dto.PaymentDto;
import com.fmi.cardealership.model.Payment;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.modelmapper.TypeMap;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

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
        return modelMapper;
    }

    @Bean
    public PasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }
}
