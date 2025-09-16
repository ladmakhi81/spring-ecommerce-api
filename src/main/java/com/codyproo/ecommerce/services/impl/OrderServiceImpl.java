package com.codyproo.ecommerce.services.impl;

import com.codyproo.ecommerce.common.exceptions.BadRequestException;
import com.codyproo.ecommerce.dtos.order.SubmitOrderDto;
import com.codyproo.ecommerce.dtos.payment.CreatePaymentDto;
import com.codyproo.ecommerce.entities.Order;
import com.codyproo.ecommerce.entities.OrderItem;
import com.codyproo.ecommerce.repositories.OrderItemRepository;
import com.codyproo.ecommerce.repositories.OrderRepository;
import com.codyproo.ecommerce.services.CartService;
import com.codyproo.ecommerce.services.OrderService;
import com.codyproo.ecommerce.services.PaymentService;
import com.codyproo.ecommerce.services.UserService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;


@Service
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final CartService cartService;
    private final UserService userService;
    private final PaymentService paymentService;

    public OrderServiceImpl(OrderRepository orderRepository, CartService cartService, UserService userService, PaymentService paymentService) {
        this.orderRepository = orderRepository;
        this.cartService = cartService;
        this.userService = userService;
        this.paymentService = paymentService;
    }

    @Override
    @Transactional
    public String createOrder(Long userId, SubmitOrderDto dto) throws Exception {
        var customer = userService.getUserById(userId);
        var cartItems = cartService.getItemsByIds(userId, dto.getCartItemIds());
        if (cartItems.isEmpty() || cartItems.size() != dto.getCartItemIds().size()) {
            throw new BadRequestException("invalid cart items");
        }
        Order order = new Order();
        for (var cartItem : cartItems) {
            var product = cartItem.getProduct();
            order.addProductPrice(product.getPrice());
            var item = OrderItem.builder()
                    .order(order)
                    .price(product.getPrice())
                    .user(customer)
                    .product(product)
                    .build();
            order.addOrderItem(item);
        }
        orderRepository.save(order);
        cartService.deleteItemByIds(dto.getCartItemIds(), userId);
        var payment = paymentService.createPayment(
                CreatePaymentDto.builder()
                        .order(order)
                        .amount(order.getTotalPrice())
                        .user(customer)
                        .build()
        );
        return payment.getPayLink();
    }
}
