package com.codyproo.ecommerce.services;

import com.codyproo.ecommerce.dtos.common.PageableEntitiesDto;
import com.codyproo.ecommerce.dtos.user.CreateBasicUserDto;
import com.codyproo.ecommerce.dtos.user.CreateUserDto;
import com.codyproo.ecommerce.dtos.user.UserDto;
import com.codyproo.ecommerce.entities.User;

import java.util.List;

public interface UserService {
    User createBasicUser(CreateBasicUserDto dto) throws Exception;

    UserDto createUser(CreateUserDto dto) throws Exception;

    User getUserById(Long id) throws Exception;

    User getUserByEmail(String email) throws Exception;

    List<UserDto> getUsers();

    PageableEntitiesDto<UserDto> getPageableUsers(int page, int pageSize);
}
