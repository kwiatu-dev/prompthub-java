package com.springboot.prompthub.service;

import com.springboot.prompthub.models.request.LoginRequest;
import com.springboot.prompthub.models.request.RegisterRequest;
import com.springboot.prompthub.models.result.LoginResult;
import com.springboot.prompthub.models.result.LogoutResult;
import com.springboot.prompthub.models.result.RefreshTokenResult;
import com.springboot.prompthub.models.result.RegisterResult;

public interface AuthService {
    LoginResult login(LoginRequest request);
    RegisterResult register(RegisterRequest request);
    RefreshTokenResult refreshToken(String refreshTokenUUID);
    LogoutResult logout(String refreshTokenUUID);
}
