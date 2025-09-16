package com.codyproo.ecommerce.services.impl;

import com.codyproo.ecommerce.common.exceptions.NotFoundException;
import com.codyproo.ecommerce.dtos.cart.AddCartItemDto;
import com.codyproo.ecommerce.dtos.cart.CartDto;
import com.codyproo.ecommerce.dtos.cart.CartItemDto;
import com.codyproo.ecommerce.entities.Cart;
import com.codyproo.ecommerce.entities.CartItem;
import com.codyproo.ecommerce.mapper.CartMapper;
import com.codyproo.ecommerce.repositories.CartItemRepository;
import com.codyproo.ecommerce.repositories.CartRepository;
import com.codyproo.ecommerce.repositories.CategoryRepository;
import com.codyproo.ecommerce.services.CartService;
import com.codyproo.ecommerce.services.ProductService;
import com.codyproo.ecommerce.services.UserService;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class CartServiceImpl implements CartService {
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final UserService userService;
    private final CartMapper cartMapper;
    private final ProductService productService;

    public CartServiceImpl(CartRepository cartRepository, CartItemRepository cartItemRepository, UserService userService, CartMapper cartMapper, ProductService productService, CategoryRepository categoryRepository) {
        this.cartRepository = cartRepository;
        this.cartItemRepository = cartItemRepository;
        this.userService = userService;
        this.cartMapper = cartMapper;
        this.productService = productService;
    }

    @Override
    public CartDto createCart(Long userId) throws Exception {
        var cart = cartRepository.findByUserId(userId).orElse(null);
        if (cart != null) {
            return cartMapper.toDto(cart);
        }
        var user = userService.getUserById(userId);
        cart = new Cart(user);
        cartRepository.save(cart);
        return cartMapper.toDto(cart);
    }

    @Override
    public void addItemToCart(AddCartItemDto dto, Long userId) throws Exception {
        var cart = cartRepository.findByUserId(userId)
                .orElseThrow(() -> new NotFoundException("cart not found"));
        var isCartItemAddedBefore = cartItemRepository.existsByUserIdAndProductId(userId, dto.getProductId());
        if (isCartItemAddedBefore) {
            return;
        }
        var product = productService.getProductById(dto.getProductId());
        var cartItem = CartItem.builder()
                .cart(cart)
                .product(product)
                .userId(userId)
                .build();
        cartItemRepository.save(cartItem);
    }

    @Override
    public CartDto getItems(Long userId) throws Exception {
        var cart = cartRepository.findByUserId(userId)
                .orElseThrow(() -> new NotFoundException("user don't have any cart"));
        return cartMapper.toDto(cart);
    }

    @Override
    public void deleteItemFromCart(Long productId, Long userId) throws Exception {
        var cartItem = cartItemRepository.findByUserIdAndProductId(userId, productId)
                .orElseThrow(() -> new NotFoundException("cart item not found"));
        cartItemRepository.delete(cartItem);
    }

    @Override
    public void clearCartByUserId(Long userId) throws Exception {
        var cart = cartRepository.findByUserId(userId)
                .orElseThrow(() -> new NotFoundException("user don't have any cart"));
        cartItemRepository.deleteAll(cart.getItems());
    }

    @Override
    public List<CartItem> getItemsByIds(Long userId, List<Long> cartItemIds) {
        return cartItemRepository.findByIdInAndUserId(cartItemIds, userId);
    }

    @Override
    public void deleteItemByIds(List<Long> ids, Long userId) throws Exception {
        var items = cartItemRepository.findByIdInAndUserId(ids, userId);
        if (items.isEmpty() || items.size() != ids.size()) {
            throw new NotFoundException("invalid cart items");
        }
        cartItemRepository.deleteAll(items);
    }
}
