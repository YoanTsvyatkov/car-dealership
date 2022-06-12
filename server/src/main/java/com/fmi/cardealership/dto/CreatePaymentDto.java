package com.fmi.cardealership.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Positive;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreatePaymentDto {
    private Long carId;
    @Positive(message = "Payment amount cannot be negative")
    private double amount;
}
