package com.fmi.cardealership.service;

import com.fmi.cardealership.exception.EntityNotFoundException;
import com.fmi.cardealership.model.Payment;
import com.fmi.cardealership.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

public class PaymentService {

    private final PaymentRepository paymentRepository;

    @Autowired
    public PaymentService(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    public List<Payment> getAllPayments() {
        return paymentRepository.findAll();
    }

    public Payment getPaymentById(Long id) {
        return paymentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.format("User with id %d not found", id)));
    }

    public Payment storePayment(Payment payment) {
        return paymentRepository.save(payment);
    }

    public void deletePayment(Long id) {
        paymentRepository.deleteById(id);
    }

    public Payment updatePayment(Long paymentId, Payment payment) {
        Optional<Payment> dbPayment = paymentRepository.findById(paymentId);
        if (dbPayment.isPresent()) {
            Payment newPayment = dbPayment.get();
            newPayment.setAmount(payment.getAmount());
            newPayment.setCar(payment.getCar());
            newPayment.setDate(payment.getDate());
            newPayment.setUser(payment.getUser());
            paymentRepository.save(newPayment);

        } else {
            paymentRepository.save(payment);
        }
        return payment;
    }
}
