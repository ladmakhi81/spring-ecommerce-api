package com.codyproo.ecommerce.dtos.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UserDto {
    private String firstName;
    private String lastName;
    private String email;
    private Long id;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    @JsonIgnore
    private String password;
}
