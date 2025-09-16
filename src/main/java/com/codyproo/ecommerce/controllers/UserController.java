package com.codyproo.ecommerce.controllers;

import com.codyproo.ecommerce.dtos.common.PageableEntitiesDto;
import com.codyproo.ecommerce.dtos.user.CreateUserDto;
import com.codyproo.ecommerce.dtos.user.UserDto;
import com.codyproo.ecommerce.services.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@Tag(name = "users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<UserDto> createUser(@Valid @RequestBody CreateUserDto dto) throws Exception {
        var response = userService.createUser(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<List<UserDto>> getUsers() {
        var response = userService.getUsers();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/page")
    public ResponseEntity<PageableEntitiesDto<UserDto>> getPageableUsers(
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "pageSize", defaultValue = "10") int pageSize
    ) {
        var response = userService.getPageableUsers(page, pageSize);
        return ResponseEntity.ok(response);
    }
}
