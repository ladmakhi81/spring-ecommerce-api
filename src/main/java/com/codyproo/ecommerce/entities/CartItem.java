package com.codyproo.ecommerce.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "_cart_items")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CartItem extends BaseEntity {
    @ManyToOne
    @JoinColumn(name = "cart_id")
    private Cart cart;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;
}
