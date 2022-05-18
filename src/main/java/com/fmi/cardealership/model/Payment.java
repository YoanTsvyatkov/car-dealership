package com.fmi.cardealership.model;


import lombok.Getter;
import lombok.NonNull;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.time.LocalDate;

@Entity
@Getter
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    // one user can have many payments for different cars
    @ManyToOne
    @JoinColumn(name = "user_id")
    @NotNull(message = "UserId in payment must not be null")
    private User userId;


    @OneToOne
    @JoinColumn(name = "car_id")
    @NotNull(message = "CarId in payment must not be null")
    private Car carId;

    @Column
    @Positive(message = "Payment amount must be positive")
    private double amount;

    @Column
    @NotNull(message = "Payment date must not be null")
    private LocalDate date;


}
