package com.fmi.cardealership.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Positive;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaymentDto {

    @NotNull
    @Positive(message = "PaymentDto id must be a positive integer")
    private Long id;

    @NotNull(message = "Payment's user must not be null")
    @Positive(message = "Payment's userId must be positive")
    private Long userID;

    @NotNull(message = "Payment car object must not be null")
    @Positive(message = "Payment's carId must be positive")
    private Long carId;

    @Positive(message = "Payment amount must not be null")
    private double amount;

    @NotNull(message = "Payment date must not be null")
    @Past(message = "Payment date must the current date or a date before the current date")
    private LocalDate date;


}
