package com.springboot.prompthub.service;

import com.springboot.prompthub.models.request.LoginRequest;
import com.springboot.prompthub.models.request.RegisterRequest;
import com.springboot.prompthub.models.result.LoginResult;
import com.springboot.prompthub.models.result.RegisterResult;

public interface AuthService {
    LoginResult login(LoginRequest request);
    RegisterResult register(RegisterRequest request);
}
