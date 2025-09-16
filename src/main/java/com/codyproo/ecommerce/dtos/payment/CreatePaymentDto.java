package com.codyproo.ecommerce.dtos.payment;

import com.codyproo.ecommerce.entities.Order;
import com.codyproo.ecommerce.entities.User;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class CreatePaymentDto {
    private User user;
    private Order order;
    private BigDecimal amount;
}
