package com.fmi.cardealership.service;

import com.fmi.cardealership.exception.EntityNotFoundException;
import com.fmi.cardealership.model.Car;
import com.fmi.cardealership.repository.CarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CarService {
    private CarRepository carRepository;

    @Autowired
    public CarService(CarRepository carRepository) {
        this.carRepository = carRepository;
    }

    public List<Car> getAllCars() {
        return carRepository.findAll();
    }

    public Car addCar(Car vehicle) {
        return carRepository.save(vehicle);
    }

    public Car getCarById(Long id) {
        return carRepository.findById(id)
                .orElseThrow(EntityNotFoundException::new);
    }

    public void deleteCarById(Long id) {
        Car carToDelete = getCarById(id);
        carRepository.delete(carToDelete);
    }

    public void updateCar(Car car) {
        getCarById(car.getId());
        carRepository.save(car);
    }
}
