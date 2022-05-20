package com.fmi.cardealership.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Positive;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    // one user can have many payments for different cars
    @ManyToOne
    @JoinColumn(name = "user_id")
    @NotNull(message = "UserId in payment must not be null")
    private User user;


    @OneToOne
    @JoinColumn(name = "car_id")
    @NotNull(message = "CarId in payment must not be null")
    private Car car;

    @Column
    @Positive(message = "Payment amount must be positive")
    private double amount;

    @Column
    @NotNull(message = "Payment date must not be null")
    @Past(message = "Payment date must the current date or a date before the current date")
    private LocalDate date;


}
