package com.fmi.cardealership.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CarDto {
    private Long id;
    private int year;
    private String name;
    private String photo;
    private BigDecimal price;
    private String fuelType;
    private String transmission;
    private int millage;
    private String exteriorColor;
    private String interiorColor;
    private int mpg;
    private boolean isSold;
}
