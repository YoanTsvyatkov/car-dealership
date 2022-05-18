package com.fmi.cardealership.repository;

import com.fmi.cardealership.model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
}
