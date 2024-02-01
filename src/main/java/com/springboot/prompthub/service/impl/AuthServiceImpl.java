package com.springboot.prompthub.service.impl;

import com.springboot.prompthub.exception.APIException;
import com.springboot.prompthub.models.entity.Role;
import com.springboot.prompthub.models.entity.User;
import com.springboot.prompthub.models.request.LoginRequest;
import com.springboot.prompthub.models.request.RegisterRequest;
import com.springboot.prompthub.models.response.UserResponse;
import com.springboot.prompthub.models.result.LoginResult;
import com.springboot.prompthub.models.result.RegisterResult;
import com.springboot.prompthub.repository.RoleRepository;
import com.springboot.prompthub.repository.UserRepository;
import com.springboot.prompthub.security.JwtTokenProvider;
import com.springboot.prompthub.service.AuthService;
import com.springboot.prompthub.utils.AppConstant;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;

    public AuthServiceImpl(
            UserRepository userRepository,
            RoleRepository roleRepository,
            AuthenticationManager authenticationManager,
            JwtTokenProvider jwtTokenProvider,
            PasswordEncoder passwordEncoder) {

        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public LoginResult login(LoginRequest request) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
            );

            if(authentication.isAuthenticated()) {
                String accessToken = jwtTokenProvider.generateToken(authentication);

                UserResponse userResponse = new UserResponse(
                        authentication.getName(),
                        authentication.getAuthorities().stream().map(role -> role.getAuthority()).collect(Collectors.toList())
                );

                return new LoginResult(
                        accessToken,
                        userResponse,
                        AppConstant.MESSAGE_API_USER_LOGGED_IN_SUCCESSFUL
                );
            }
        }
        catch(AuthenticationException ignored) { }

        throw new APIException(
                HttpStatus.BAD_REQUEST,
                AppConstant.ERROR_API_INVALID_USERNAME_OR_PASSWORD
        );
    }

    @Override
    public RegisterResult register(RegisterRequest request) {
        if(userRepository.existsByEmail(request.getEmail())) {
            throw new APIException(
                    HttpStatus.BAD_REQUEST,
                    AppConstant.ERROR_API_USER_EXIST
            );
        }

        User user = new User();
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        Role userRole = roleRepository.findByName(AppConstant.ROLE_USER).get();
        Set<Role> roles = new HashSet<>();
        roles.add(userRole);

        user.setRoles(roles);

        userRepository.save(user);

        return new RegisterResult(AppConstant.MESSAGE_API_USER_CREATED_SUCCESSFUL);
    }
}
