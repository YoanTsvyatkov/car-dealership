package com.fmi.cardealership.controller;

import com.fmi.cardealership.dto.CarDto;
import com.fmi.cardealership.dto.UpdateCarDto;
import com.fmi.cardealership.model.Car;
import com.fmi.cardealership.service.CarService;
import com.fmi.cardealership.service.FileStorageService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.websocket.server.PathParam;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/cars")
public class CarController {

    private final CarService carService;
    private final ModelMapper modelMapper;
    private final FileStorageService fileStorageService;
    private static final Logger logger = LoggerFactory.getLogger(CarController.class);

    public CarController(CarService carService, ModelMapper modelMapper, FileStorageService fileStorageService) {
        this.carService = carService;
        this.modelMapper = modelMapper;
        this.fileStorageService = fileStorageService;
    }

    @GetMapping
    public List<CarDto> getAllCars() {
        return carService.getAllCars()
                .stream().map(car -> modelMapper.map(car, CarDto.class))
                .collect(Collectors.toList());
    }

    @GetMapping("{id}")
    public CarDto getCarById(@PathVariable("id") Long id) {
        return modelMapper.map(carService.getCarById(id), CarDto.class);
    }

    @PostMapping
    public CarDto addCar(
            @RequestParam MultipartFile file,
            @RequestParam int year,
            @RequestParam String name,
            @RequestParam double price,
            @RequestParam String fuelType,
            @RequestParam String transmission,
            @RequestParam int millage,
            @RequestParam String exteriorColor,
            @RequestParam String interiorColor,
            @RequestParam int mpg,
            @RequestParam String carDescription
    ) {
        String fileName = fileStorageService.storeFile(file);
        Car car = new Car();
        car.setYear(year);
        car.setName(name);
        car.setPrice(price);
        car.setFuelType(fuelType);
        car.setTransmission(transmission);
        car.setMillage(millage);
        car.setExteriorColor(exteriorColor);
        car.setInteriorColor(interiorColor);
        car.setMpg(mpg);
        car.setPhotoName(fileName);
        car.setCarDescription(carDescription);
        Car addedCar = carService.addCar(car);

        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(String.format("api/cars/%d/photo", addedCar.getId()))
                .toUriString();
        addedCar = carService.addCarPhotoUrl(addedCar, fileDownloadUri);
        return modelMapper.map(addedCar, CarDto.class);
    }

    @GetMapping("/{id}/photo")
    public ResponseEntity<Resource> getCarPhoto(@PathVariable("id") Long id, HttpServletRequest request) {
        Car car = carService.getCarById(id);
        Resource resource = fileStorageService.loadFileAsResource(car.getPhotoName());

        String contentType = getContentType(resource, request);
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }

    @DeleteMapping("/{id}")
    public CarDto deleteCarById(@PathVariable("id") Long id) {
        return modelMapper.map(carService.deleteCarById(id), CarDto.class);
    }

    @PutMapping("/{id}")
    public CarDto updateCar(@PathVariable("id") Long id,
                            @RequestParam(required = false) MultipartFile file,
                            @RequestParam(required = false) Integer year,
                            @RequestParam(required = false) String name,
                            @RequestParam(required = false) Double price,
                            @RequestParam(required = false) String fuelType,
                            @RequestParam(required = false) String transmission,
                            @RequestParam(required = false) Integer millage,
                            @RequestParam(required = false) String exteriorColor,
                            @RequestParam(required = false) String interiorColor,
                            @RequestParam(required = false) Integer mpg,
                            @RequestParam(required = false) String carDescription) {
        String fileName = null;
        if (file != null) {
            fileName = fileStorageService.storeFile(file);
        }
        Car car = new Car();
        car.setYear(year);
        car.setName(name);
        car.setPrice(price);
        car.setFuelType(fuelType);
        car.setTransmission(transmission);
        car.setMillage(millage);
        car.setExteriorColor(exteriorColor);
        car.setInteriorColor(interiorColor);
        car.setMpg(mpg);
        car.setPhotoName(fileName);
        car.setCarDescription(carDescription);
        Car updatedCar = carService.updateCar(car, id);

        if (file != null) {
            String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                    .path(String.format("api/cars/%d/photo", updatedCar.getId()))
                    .toUriString();
            updatedCar = carService.addCarPhotoUrl(updatedCar, fileDownloadUri);
        }
        return modelMapper.map(updatedCar, CarDto.class);
    }

    private String getContentType(Resource resource, HttpServletRequest request) {
        String contentType = null;
        try {
            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        } catch (IOException ex) {
            logger.info("Could not determine file type.");
        }

        // Fallback to the default content type if type could not be determined
        if (contentType == null) {
            contentType = "application/octet-stream";
        }

        return contentType;
    }
}
