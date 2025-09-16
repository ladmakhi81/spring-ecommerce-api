package com.codyproo.ecommerce.repositories;

import com.codyproo.ecommerce.entities.Cart;
import com.codyproo.ecommerce.entities.CartItem;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface CartItemRepository extends CrudRepository<CartItem, Long> {
    boolean existsByUserIdAndProductId(Long userId, Long productId);

    Optional<CartItem> findByUserIdAndProductId(Long userId, Long productId);

    List<CartItem> findByIdInAndUserId(List<Long> ids, Long userId);

    List<Long> cart(Cart cart);
}
