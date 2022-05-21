package com.fmi.cardealership.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Positive;

@Getter
@Setter
@AllArgsConstructor
public class CreatePaymentDto {
    private Long userId;

    private Long carId;

    @Positive(message = "Payment amount cannot be negative")
    private double amount;
}
