package com.codyproo.ecommerce.services;

import com.codyproo.ecommerce.dtos.order.SubmitOrderDto;

public interface OrderService {
    String createOrder(Long userId, SubmitOrderDto dto) throws Exception;
}
