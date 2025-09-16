package com.codyproo.ecommerce.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "_carts")
@Getter
@Setter
@NoArgsConstructor
public class Cart extends BaseEntity {
    public Cart(User user) {
        this.user = user;
    }

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;


    @OneToMany(mappedBy = "cart")
    private List<CartItem> items = new ArrayList<>();
}
