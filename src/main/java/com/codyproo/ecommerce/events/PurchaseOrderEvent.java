package com.codyproo.ecommerce.events;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PurchaseOrderEvent {
    private Long id;
    private String fullName;
    private String email;
    private String subject;
}
