package com.codyproo.ecommerce.mapper;

import com.codyproo.ecommerce.dtos.cart.CartDto;
import com.codyproo.ecommerce.entities.Cart;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CartMapper {
    @Mapping(source = "items", target = "cartItems")
    CartDto toDto(Cart cart);
}
