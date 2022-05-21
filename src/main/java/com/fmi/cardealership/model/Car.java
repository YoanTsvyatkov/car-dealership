package com.fmi.cardealership.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Car {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column
    private int year;

    @Column
    private String name;

    @Column
    private String photo;

    @Column
    private double price;

    @Column
    private String fuelType;

    @Column
    private String transmission;

    @Column
    private int millage;

    @Column
    private String exteriorColor;

    @Column
    private String interiorColor;

    @Column
    private int mpg;

    @Column
    private boolean isSold;
}
