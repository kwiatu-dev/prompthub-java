package com.springboot.prompthub.service.impl;

import com.springboot.prompthub.exception.APIException;
import com.springboot.prompthub.models.entity.User;
import com.springboot.prompthub.repository.UserRepository;
import com.springboot.prompthub.utils.AppConstant;
import com.springboot.prompthub.utils.UserPrincipal;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserRepository userRepository;

    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new APIException(
                        HttpStatus.BAD_REQUEST,
                        AppConstant.ERROR_API_USER_NOT_FOUND
                ));

        Set<GrantedAuthority> authorities = user
                .getRoles()
                .stream()
                .map(role -> new SimpleGrantedAuthority(role.getName()))
                .collect(Collectors.toSet());

        return new UserPrincipal(user);
    }
}
