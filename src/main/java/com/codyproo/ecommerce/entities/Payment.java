package com.codyproo.ecommerce.entities;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "_payments")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Payment extends BaseEntity {
    @Column(name = "authority", nullable = false, unique = true)
    private String authority;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

    @Column(name = "amount", nullable = false, precision = 10, scale = 2)
    private BigDecimal amount;

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    @Builder.Default
    private PaymentStatus status = PaymentStatus.Pending;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "pay_link", nullable = false)
    private String payLink;

    @Column(name = "expired_at")
    private LocalDateTime expiredAt;

    @Column(name = "expiration_date", nullable = false)
    private LocalDateTime expirationDate;

    @Column(name = "payed_date")
    private LocalDateTime payedDate;
}
