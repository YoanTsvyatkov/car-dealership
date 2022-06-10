package com.fmi.cardealership.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateCarDto {
    private int year;
    private String name;
    private double price;
    private String fuelType;
    private String transmission;
    private int millage;
    private String exteriorColor;
    private String interiorColor;
    private int mpg;
    private boolean isSold;
}
