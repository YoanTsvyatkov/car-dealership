package com.fmi.cardealership;

import com.fmi.cardealership.model.Car;
import com.fmi.cardealership.model.Status;
import com.fmi.cardealership.model.User;
import com.fmi.cardealership.model.UserRole;
import com.fmi.cardealership.property.FileStorageProperties;
import com.fmi.cardealership.service.CarService;
import com.fmi.cardealership.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import java.time.LocalDate;
import java.util.List;

@SpringBootApplication
@EnableConfigurationProperties({
		FileStorageProperties.class
})
public class CarDealershipApplication implements CommandLineRunner {

	@Autowired
	private UserService userService;

	@Autowired
	private CarService carService;

	private static List<User> users = List.of(
			new User("Ivan", "ivan1", "1234",
					"ivan@gmail.com", LocalDate.of(1999, 1, 1),
					UserRole.ADMIN,
					"0988773312", Status.ACTIVE ),
			new User("Pe6o", "pesho1", "1234",
					"pesho@gmail.com", LocalDate.of(2001, 1, 1), UserRole.USER,
					"0958773312", Status.ACTIVE ),
			new User("Go6o", "gosho", "1234",
					"gosho@gmail.com", LocalDate.of(2005, 1, 1), UserRole.USER,
					"0988774512", Status.ACTIVE )
	);

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

	public static void main(String[] args) {
		SpringApplication.run(CarDealershipApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		if (userService.getAllUsers().isEmpty()) users.forEach(userService::addUser);
		if (carService.getAllCars().isEmpty()) cars.forEach(carService::addCar);
	}
}
