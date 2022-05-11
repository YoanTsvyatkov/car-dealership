package com.fmi.cardealership.controller;

import com.fmi.cardealership.dto.CarDto;
import com.fmi.cardealership.dto.CreateCarDto;
import com.fmi.cardealership.model.Car;
import com.fmi.cardealership.service.CarService;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/cars")
public class CarController {

    private CarService carService;
    private ModelMapper modelMapper;

    public CarController(CarService carService, ModelMapper modelMapper) {
        this.carService = carService;
        this.modelMapper = modelMapper;
    }

    @GetMapping
    public List<CarDto> getAllCars() {
        return carService.getAllCars()
                .stream().map(car -> modelMapper.map(car, CarDto.class))
                .collect(Collectors.toList());
    }

    @PostMapping
    public CarDto addCar(@RequestBody CreateCarDto carDto) {
        Car car = modelMapper.map(carDto, Car.class);
        Car addedCar = carService.addCar(car);
        return modelMapper.map(addedCar, CarDto.class);
    }
}
