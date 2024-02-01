package com.springboot.prompthub.service.impl;

import com.springboot.prompthub.exception.APIException;
import com.springboot.prompthub.models.entity.RefreshToken;
import com.springboot.prompthub.models.entity.Role;
import com.springboot.prompthub.models.entity.User;
import com.springboot.prompthub.models.request.LoginRequest;
import com.springboot.prompthub.models.request.RegisterRequest;
import com.springboot.prompthub.models.response.UserResponse;
import com.springboot.prompthub.models.result.LoginResult;
import com.springboot.prompthub.models.result.LogoutResult;
import com.springboot.prompthub.models.result.RefreshTokenResult;
import com.springboot.prompthub.models.result.RegisterResult;
import com.springboot.prompthub.repository.RefreshTokenRepository;
import com.springboot.prompthub.repository.RoleRepository;
import com.springboot.prompthub.repository.UserRepository;
import com.springboot.prompthub.security.AuthenticationFacade;
import com.springboot.prompthub.security.JwtTokenProvider;
import com.springboot.prompthub.security.RefreshTokenProvider;
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
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;
    private final RefreshTokenProvider refreshTokenProvider;
    private final RefreshTokenRepository refreshTokenRepository;
    private final AuthenticationFacade authenticationFacade;

    public AuthServiceImpl(
            UserRepository userRepository,
            RoleRepository roleRepository,
            AuthenticationManager authenticationManager,
            JwtTokenProvider jwtTokenProvider,
            PasswordEncoder passwordEncoder,
            RefreshTokenProvider refreshTokenProvider,
            RefreshTokenRepository refreshTokenRepository,
            AuthenticationFacade authenticationFacade) {

        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
        this.passwordEncoder = passwordEncoder;
        this.refreshTokenProvider = refreshTokenProvider;
        this.refreshTokenRepository = refreshTokenRepository;
        this.authenticationFacade = authenticationFacade;
    }

    @Override
    public LoginResult login(LoginRequest request) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
            );

            if(authentication.isAuthenticated()) {
                String accessToken = jwtTokenProvider.generateToken(authentication);
                RefreshToken refreshToken = refreshTokenProvider.createRefreshToken();

                UserResponse userResponse = new UserResponse(
                        authentication.getName(),
                        authentication.getAuthorities().stream().map(role -> role.getAuthority()).collect(Collectors.toList())
                );

                return new LoginResult(
                        accessToken,
                        refreshToken.getToken(),
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

    @Override
    public RefreshTokenResult refreshToken(String refreshTokenUUID) {
        RefreshToken refreshToken = refreshTokenRepository.findByToken(refreshTokenUUID)
                .orElseThrow(() -> new APIException(
                        HttpStatus.BAD_REQUEST,
                        AppConstant.ERROR_REFRESH_TOKEN_NOT_EXIST));


        if(refreshToken.isRevoked()) {
            refreshTokenProvider.revokeDescendantRefreshTokens(refreshToken);
        }


        Authentication authentication = authenticationFacade.getAuthentication();
        String accessToken = jwtTokenProvider.generateToken(authentication);
        RefreshToken newRefreshToken = refreshTokenProvider.rotateRefreshToken(refreshToken);

        User user = userRepository.findByEmail(authentication.getName())
                .orElseThrow(() -> new APIException(
                        HttpStatus.BAD_REQUEST,
                        AppConstant.ERROR_API_USER_NOT_FOUND));

        refreshTokenProvider.removeObsoleteRefreshTokens(user);

        UserResponse userResponse = new UserResponse(
                user.getEmail(),
                user.getRoles().stream()
                        .map(role -> role.getName())
                        .collect(Collectors.toList())
        );

        return new RefreshTokenResult(
                accessToken,
                newRefreshToken.getToken(),
                userResponse,
                AppConstant.MESSAGE_REFRESH_TOKEN_ROTATED_SUCCESSFUL
        );
    }

    @Override
    public LogoutResult logout(String refreshTokenUUID) {
        RefreshToken refreshToken = refreshTokenRepository.findByToken(refreshTokenUUID)
                .orElseThrow(() -> new APIException(
                HttpStatus.BAD_REQUEST,
                AppConstant.ERROR_REFRESH_TOKEN_NOT_EXIST));

        refreshTokenProvider.revokeRefreshToken(
                refreshToken,
                null,
                AppConstant.MESSAGE_REFRESH_TOKEN_USER_LOGOUT);

        return new LogoutResult(
                AppConstant.MESSAGE_API_USER_LOGOUT);
    }
}
