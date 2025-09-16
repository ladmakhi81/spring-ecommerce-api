package com.codyproo.ecommerce.services.impl;

import com.codyproo.ecommerce.common.exceptions.DuplicateException;
import com.codyproo.ecommerce.common.exceptions.NotFoundException;
import com.codyproo.ecommerce.dtos.common.PageableEntitiesDto;
import com.codyproo.ecommerce.dtos.user.CreateBasicUserDto;
import com.codyproo.ecommerce.dtos.user.CreateUserDto;
import com.codyproo.ecommerce.dtos.user.UserDto;
import com.codyproo.ecommerce.entities.User;
import com.codyproo.ecommerce.mapper.UserMapper;
import com.codyproo.ecommerce.repositories.UserRepository;
import com.codyproo.ecommerce.services.UserService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, UserMapper userMapper, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User createBasicUser(CreateBasicUserDto dto) throws Exception {
        _validateNewUserExistence(dto.getEmail());
        var user = User.builder()
                .email(dto.getEmail())
                .password(passwordEncoder.encode(dto.getPassword()))
                .build();
        userRepository.save(user);
        return user;
    }

    @Override
    public UserDto createUser(CreateUserDto dto) throws Exception {
        _validateNewUserExistence(dto.getEmail());
        var user = User.builder()
                .email(dto.getEmail())
                .password(passwordEncoder.encode(dto.getPassword()))
                .firstName(dto.getFirstName())
                .lastName(dto.getLastName())
                .build();
        userRepository.save(user);
        return userMapper.toDto(user);
    }

    @Override
    public User getUserById(Long id) throws Exception {
        return userRepository.findById(id).orElseThrow(() -> new NotFoundException("user not found"));
    }

    @Override
    public User getUserByEmail(String email) throws Exception {
        return userRepository.findByEmail(email).orElseThrow(() -> new NotFoundException("user not found"));
    }

    @Override
    public List<UserDto> getUsers() {
        var sort = Sort.by("createdAt").descending();
        var users = userRepository.findAll(sort);
        return users
                .stream()
                .map(userMapper::toDto)
                .toList();
    }

    @Override
    public PageableEntitiesDto<UserDto> getPageableUsers(int page, int pageSize) {
        var sort = Sort.by("createdAt").descending();
        var pageRequest = PageRequest.of(page, pageSize, sort);
        var paginatedUsers = userRepository.findAll(pageRequest);
        var users = userMapper.toDtos(paginatedUsers.getContent());
        return new PageableEntitiesDto<>(
                paginatedUsers.getTotalElements(),
                paginatedUsers.getTotalPages(),
                users
        );
    }

    private void _validateNewUserExistence(String email) throws Exception {
        boolean isUserDuplicate = userRepository.existsByEmail(email);
        if (isUserDuplicate) {
            throw new DuplicateException("user email already exists");
        }
    }
}
