package com.codyproo.ecommerce.repositories;

import com.codyproo.ecommerce.entities.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
    Optional<Payment> findByAuthority(String authority);
}
