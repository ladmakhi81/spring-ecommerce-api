package com.codyproo.ecommerce.mapper;

import com.codyproo.ecommerce.dtos.user.UserDto;
import com.codyproo.ecommerce.entities.User;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDto toDto(User user);
    List<UserDto> toDtos(List<User> users);
}
