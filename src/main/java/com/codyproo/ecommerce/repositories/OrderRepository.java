package com.codyproo.ecommerce.repositories;

import com.codyproo.ecommerce.entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}

// user select cart ids and send to backend
// read cart by user id
// validate cart ids with sended ids
// create order with order item
// generate payment