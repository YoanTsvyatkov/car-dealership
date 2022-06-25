package com.fmi.cardealership.service;

import com.fmi.cardealership.exception.EntityNotFoundException;
import com.fmi.cardealership.model.Car;
import com.fmi.cardealership.repository.CarRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CarServiceTest {

    @Mock
    CarRepository carRepository;

    @InjectMocks
    CarService carService;

    private static List<Car> cars = List.of(
            new Car(2006, "Mazda 6",
                    "https://upload.wikimedia.org/wikipedia/commons/thumb/e/ea/2018_Mazda6_Sport_NAV%2B_Diesel_2.2_Front.jpg/1920px-2018_Mazda6_Sport_NAV%2B_Diesel_2.2_Front.jpg",
                    50000, "Diesel", "Automatic", 40000, "red",
                    "black", 50, false, "test.png",
                    "Insurance Group 22, The Mazda 6 is a stylish designed saloon car with a spacious, well-designed interior, excellent amount of tech and safety spec as standard and very fun to drive. This SE-L Nav model includes; Heated/Electric Leather Seats, Heated Multi-Function Steering Wheel and Climate Control to keep extra warm on cold days. Lane Keeping Assist, Smart City Brake Support , Parking Camera, Cruise Control and Auto Start Stop result in a comfortable, carefree drive. Bluetooth, DAB Radio and MP3 Player provide entertainment."),
            new Car(2015, "Audi a6",
                    "https://cdn.motor1.com/images/mgl/qzoWZ/s2/2020-audi-a6-sedan.jpg",
                    70000, "Diesel", "Manual", 10000, "black",
                    "black", 45, false, "test.png",
                    "The Audi A6 is a real contender in the fiercely fought executive saloon market. It shows its rivals the way for quality and technology, and it’s as good as anything else in the class for practicality, desirability and running costs. The sophisticated driving manners will also appeal to buyers, but the A6’s real USP is the innovative technology it carries on board. Few cars at any price feel as technologically advanced as this."),
            new Car(2016, "Tesla Model X",
                    "https://tesla-cdn.thron.com/delivery/public/image/tesla/8c26f779-11e5-4cfc-bd7c-dcd03b18ff88/bvlatuR/std/4096x2561/Model-X-Main-Hero-Desktop-LHD",
                    100000, "Electric", "Automatic", 100, "white",
                    "black", 50, false, "test.png",
                    "Tesla has announced upcoming updates to the Model X lineup, including a new Long Range model capable of an estimated range of 360 miles. Above this, a new triple-motor model called the ‘Plaid’ is also available to order; it can sprint from 0-60mph in 2.5 seconds while managing up to 340 miles of range. According to Tesla, both new models will arrive in the UK towards the end of 2022. It’s worth noting that the models they replace are now no longer available, so there’s effectively a lull in brand new examples of the Tesla Model X arriving in the UK until at least late 2022.")
    );

    @Test
    void testWhenDbIsEmptyThenReturnsEmptyList() {
        when(carRepository.findAll()).thenReturn(List.of());

        List<Car> cars = carService.getAllCars();

        assertTrue(cars.isEmpty(), "List was not empty");
    }

    @Test
    void testWhenDbIsNotEmptyThenReturnsCorrectList() {
        when(carRepository.findAll()).thenReturn(cars);

        List<Car> actual = carService.getAllCars();

        assertIterableEquals(cars, actual, "Lists differ");
    }

    @Test
    void testAddCars() {
        carService.addCar(cars.get(0));
        verify(carRepository, times(1)).save(cars.get(0));

        carService.addCar(cars.get(1));
        verify(carRepository, times(1)).save(cars.get(1));

        verify(carRepository, times(2)).save(any());
    }

    @Test
    void testAddCarWhenNoMatchingCarThenThrowsException() {
        long testId = 1;
        when(carRepository.findById(testId)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> carService.getCarById(testId),
                "Exception was not thrown");
    }

    @Test
    void testAddCarWhenMatchingCarThenReturnsCar() {
        long testId = 1;
        Car expected = cars.get(0);

        when(carRepository.findById(testId)).thenReturn(Optional.of(expected));

        Car actual = carService.getCarById(testId);

        verify(carRepository, times(1)).findById(testId);
        assertEquals(expected, actual, "Cars were not the same");
        assertEquals(expected.getId(), actual.getId(), "Cars ids were not the same");
    }

    @Test
    void testDeleteCarWhenNoCarWithIdExistThenThrowsException() {
        long testId = 1;
        when(carRepository.findById(testId)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> carService.deleteCarById(testId),
                "Exception was not thrown");
    }

    @Test
    void testDeleteCarWhenCarWithIdExistThenCarWasDeleted() {
        long testId = 1;
        Car expected = cars.get(0);
        when(carRepository.findById(testId)).thenReturn(Optional.of(expected));

        Car actual = carService.deleteCarById(testId);

        verify(carRepository, times(1)).delete(expected);
        assertEquals(expected, actual, "Car was not deleted");
        assertEquals(expected.getId(), actual.getId(), "Car ids were not matched");
    }

    @Test
    void testUpdateCarWhenNoCarThenSaveCar() {
        long testId = 1;
        Car expected = cars.get(0);
        when(carRepository.findById(testId)).thenReturn(Optional.empty());
        when(carRepository.save(expected)).thenReturn(expected);

        Car actual = carService.updateCar(expected, testId);

        verify(carRepository, times(1)).save(expected);
        assertEquals(expected, actual, "Cars are not the same");
    }

    @Test
    void testWhenUpdateCarThenCarGetsUpdated() {
        long testId = 1;
        Car car = cars.get(0);
        Car carBeforeUpdate = Car.builder()
                        .isSold(car.isSold())
                        .name(car.getName())
                        .price(car.getPrice())
                        .build();
        when(carRepository.findById(testId)).thenReturn(Optional.of(car));
        Car update = Car.builder().isSold(true)
                .name("test")
                .price(100)
                .build();
        when(carRepository.save(any())).thenReturn(car);

        Car actual = carService.updateCar(update, testId);
        verify(carRepository, times(1)).save(car);
        assertNotEquals(carBeforeUpdate.getName(), actual.getName(), "Name is not changed");
        assertNotEquals(carBeforeUpdate.getPrice(), actual.getPrice(), "Price is not changed");
        assertNotEquals(carBeforeUpdate.isSold(), actual.isSold(), "Is sold is not changed");
    }

    @Test
    void testWhenAddCarPhotoThenCarPhotoChange() {
        String test = "test.png";
        Car car = cars.get(0);
        
        carService.addCarPhotoUrl(car, test);

        assertEquals(car.getPhotoName(), test, "Car photo was not changed");
    }
}