package com.codyproo.ecommerce.dtos.payment;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class InitRequestDto {
    private BigDecimal amount;
}
