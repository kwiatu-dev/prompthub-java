package com.springboot.prompthub.controller;

import com.springboot.prompthub.models.request.LoginRequest;
import com.springboot.prompthub.models.request.RegisterRequest;
import com.springboot.prompthub.models.response.LoginResponse;
import com.springboot.prompthub.models.response.RegisterResponse;
import com.springboot.prompthub.models.result.LoginResult;
import com.springboot.prompthub.models.result.RegisterResult;
import com.springboot.prompthub.service.AuthService;
import com.springboot.prompthub.utils.AppConstant;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(AppConstant.API_VERSION + "/auth")
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping(value = { "/login", "/signin" })
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {
        LoginResult loginResult = authService.login(request);

        //todo:response with httpOnly cookie with refresh token
        //https://medium.com/spring-boot/cookie-based-jwt-authentication-with-spring-security-756f70664673
        //https://github.com/bezkoder/spring-security-refresh-token-jwt

        return new ResponseEntity<>(new LoginResponse(
                loginResult.getUserResponse(),
                loginResult.getAccessToken(),
                loginResult.getMessage()
        ), HttpStatus.OK);
    }

    @PostMapping(value = { "/register", "/signup" })
    public ResponseEntity<RegisterResponse> register(@RequestBody RegisterRequest request) {
        RegisterResult registerResult = authService.register(request);

        return new ResponseEntity<>(new RegisterResponse(
                registerResult.getMessage()
        ), HttpStatus.OK);
    }
}
