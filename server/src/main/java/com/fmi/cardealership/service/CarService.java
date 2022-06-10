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
                .orElseThrow(() ->
                        new EntityNotFoundException(String.format("Car with id: %d, does not exist", id)));
    }

    public Car deleteCarById(Long id) {
        Car carToDelete = getCarById(id);
        carRepository.delete(carToDelete);
        return carToDelete;
    }

    public Car updateCar(Car car, Long id) {
        return carRepository.findById(id)
                .map(dbCar -> updateCar(dbCar, car))
                .orElseGet(() -> carRepository.save(car));
    }

    private Car updateCar(Car dbCar, Car request) {
        dbCar.setExteriorColor(request.getExteriorColor());
        dbCar.setFuelType(request.getFuelType());
        dbCar.setYear(request.getYear());
        dbCar.setName(request.getName());
        dbCar.setPhotoUrl(request.getPhotoUrl());
        dbCar.setPrice(request.getPrice());
        dbCar.setTransmission(request.getTransmission());
        dbCar.setMillage(request.getMillage());
        dbCar.setExteriorColor(request.getExteriorColor());
        dbCar.setInteriorColor(request.getInteriorColor());
        dbCar.setMpg(request.getMpg());
        dbCar.setSold(request.isSold());
        return carRepository.save(dbCar);
    }

    public Car addCarPhotoUrl(Car addedCar, String carPhotoUrl) {
        addedCar.setPhotoUrl(carPhotoUrl);
        return carRepository.save(addedCar);
    }
}
