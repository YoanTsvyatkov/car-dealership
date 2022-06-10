package com.fmi.cardealership.repository;

import com.fmi.cardealership.model.Car;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CarRepository extends JpaRepository<Car, Long> {
}