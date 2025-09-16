package com.codyproo.ecommerce.services;

import com.codyproo.ecommerce.dtos.cart.AddCartItemDto;
import com.codyproo.ecommerce.dtos.cart.CartDto;
import com.codyproo.ecommerce.entities.CartItem;

import java.util.List;

public interface CartService {
    CartDto createCart(Long userId) throws Exception;

    void addItemToCart(AddCartItemDto dto, Long userId) throws Exception;

    CartDto getItems(Long userId) throws Exception;

    void deleteItemFromCart(Long productId, Long userId) throws Exception;

    void clearCartByUserId(Long userId) throws Exception;

    List<CartItem> getItemsByIds(Long userId, List<Long> cartItemIds);

    void deleteItemByIds(List<Long> ids, Long userId) throws Exception;
}
