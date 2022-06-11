package com.fmi.cardealership.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import javax.persistence.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class Car {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column
    private int year;

    @Column
    private String name;

    @Column
    private String photoUrl;

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

    @Column
    private String photoName;

    public void setPhotoName(String photoName) {
        if (photoName == null || photoName.isBlank()) {
            return;
        }

        this.photoName = photoName;
    }

    public void setYear(Integer year) {
        if (year == null || year <= 0) {
            return;
        }

        this.year = year;
    }

    public void setName(String name) {
        if (name == null) {
            return;
        }

        this.name = name;
    }

    public void setPhotoUrl(String photoUrl) {
        if (photoUrl == null || photoUrl.isBlank()) {
            return;
        }

        this.photoUrl = photoUrl;
    }

    public void setPrice(Double price) {
        if (price == null || price <= 0) {
            return;
        }

        this.price = price;
    }

    public void setFuelType(String fuelType) {
        if (fuelType == null || fuelType.isBlank()) {
            return;
        }
        this.fuelType = fuelType;
    }

    public void setTransmission(String transmission) {
        if (transmission == null || transmission.isBlank()) {
            return;
        }
        this.transmission = transmission;
    }

    public void setMillage(Integer millage) {
        if (millage == null || millage <= 0) {
            return;
        }
        this.millage = millage;
    }

    public void setExteriorColor(String exteriorColor) {
        if (exteriorColor == null || exteriorColor.isBlank()) {
            return;
        }
        this.exteriorColor = exteriorColor;
    }

    public void setInteriorColor(String interiorColor) {
        if (interiorColor == null || interiorColor.isBlank()) {
            return;
        }
        this.interiorColor = interiorColor;
    }

    public void setMpg(Integer mpg) {
        if (mpg == null || mpg <= 0) {
            return;
        }

        this.mpg = mpg;
    }

    public void setSold(boolean sold) {
        isSold = sold;
    }
}
