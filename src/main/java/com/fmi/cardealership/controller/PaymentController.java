package com.fmi.cardealership.controller;

import com.fmi.cardealership.dto.PaymentDto;
import com.fmi.cardealership.model.Payment;
import com.fmi.cardealership.service.CarService;
import com.fmi.cardealership.service.PaymentService;
import com.fmi.cardealership.service.UserService;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {

    private final ModelMapper modelMapper = new ModelMapper();
    private static final Logger logger = LoggerFactory.getLogger(PaymentController.class);
    private final PaymentService paymentService;

    //Need UserService to map PaymentDto(userId) to User
    private final UserService userService;

    //Same for CarService
    private final CarService carService;


    @Autowired
    public PaymentController(PaymentService paymentService, CarService carService, UserService userService) {
        this.paymentService = paymentService;
        this.carService = carService;
        this.userService = userService;
        createTypeMapperEntityToDto();
        createTypeMapperDtoToEntity();
    }

    @GetMapping
    public List<PaymentDto> getAllPayments() {
        return paymentService.getAllPayments()
                .stream()
                .map(payment -> modelMapper.map(payment, PaymentDto.class))
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public PaymentDto getPaymentById(@PathVariable Long id) {
        return modelMapper.map(paymentService.getPaymentById(id), PaymentDto.class);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PaymentDto createPayment(@RequestBody @Valid PaymentDto paymentDto) {
        Payment payment = modelMapper.map(paymentDto, Payment.class);
        logger.info(String.format("User with id %d was created", paymentDto.getId()));
        return modelMapper.map(paymentService.storePayment(payment), PaymentDto.class);
    }

    @DeleteMapping("/{id}")
    public void deletePayment(@PathVariable Long id) {
        paymentService.deletePayment(id);
        logger.info(String.format("User with id %d was deleted", id));
    }

    @PutMapping("{id}")
    public void updatePayment(@PathVariable Long id, @RequestBody @Valid PaymentDto paymentDto) {
        Payment payment = modelMapper.map(paymentDto, Payment.class);
        paymentService.updatePayment(id, payment);
        logger.info(String.format("User with id %d was updated", paymentDto.getId()));
    }

    private void createTypeMapperEntityToDto() {
        TypeMap<Payment, PaymentDto> typeMap = modelMapper.createTypeMap(Payment.class, PaymentDto.class);
        typeMap.addMapping(Payment::getAmount, PaymentDto::setAmount);
        typeMap.addMapping(Payment::getId, PaymentDto::setId);
        typeMap.addMapping(Payment::getDate, PaymentDto::setDate);

        typeMap.addMappings(mapper -> mapper.map(src -> src.getCar().getId(), PaymentDto::setCarId));
        typeMap.addMappings(mapper -> mapper.map(src -> src.getUser().getId(), PaymentDto::setUserID));

    }

    private void createTypeMapperDtoToEntity() {
        TypeMap<PaymentDto, Payment> typeMap = modelMapper.createTypeMap(PaymentDto.class, Payment.class);
        typeMap.addMapping(PaymentDto::getAmount, Payment::setAmount);
        typeMap.addMapping(PaymentDto::getId, Payment::setId);
        typeMap.addMapping(PaymentDto::getDate, Payment::setDate);

        typeMap.addMappings(mapper -> mapper.map(src -> carService.getCarById(src.getCarId()), Payment::setCar));
        typeMap.addMappings(mapper -> mapper.map(src -> userService.getUserById(src.getUserID()), Payment::setUser));

    }
}
