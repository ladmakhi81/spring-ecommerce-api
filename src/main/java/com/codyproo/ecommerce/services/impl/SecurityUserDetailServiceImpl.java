package com.codyproo.ecommerce.services.impl;

import com.codyproo.ecommerce.dtos.common.SecuredUserDetailsDto;
import com.codyproo.ecommerce.repositories.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class SecurityUserDetailServiceImpl implements UserDetailsService {
    private final UserRepository userRepository;

    public SecurityUserDetailServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        var user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("invalid authentication credentials"));
        return new SecuredUserDetailsDto(user);
    }
}
