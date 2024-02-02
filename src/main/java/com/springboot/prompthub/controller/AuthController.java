package com.springboot.prompthub.controller;

import com.springboot.prompthub.exception.APIException;
import com.springboot.prompthub.models.request.LoginRequest;
import com.springboot.prompthub.models.request.RegisterRequest;
import com.springboot.prompthub.models.response.LoginResponse;
import com.springboot.prompthub.models.response.RefreshTokenResponse;
import com.springboot.prompthub.models.response.RegisterResponse;
import com.springboot.prompthub.models.response.SuccessResponse;
import com.springboot.prompthub.models.result.LoginResult;
import com.springboot.prompthub.models.result.LogoutResult;
import com.springboot.prompthub.models.result.RefreshTokenResult;
import com.springboot.prompthub.models.result.RegisterResult;
import com.springboot.prompthub.service.AuthService;
import com.springboot.prompthub.utils.AppConstant;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(AppConstant.API_VERSION + "/auth")
public class AuthController {
    private final AuthService authService;

    @Value("${app-refreshtoken-cookie-name}")
    private String refreshTokenCookieName;

    @Value("${app-refreshtoken-expiration-milliseconds}")
    private long refreshTokenExpiration;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping(value = { "/login", "/signin" })
    public ResponseEntity<LoginResponse> login(
            @RequestBody LoginRequest request,
            HttpServletResponse response) {

        LoginResult loginResult = authService.login(request);

        setRefreshTokenCookie(response,
                loginResult.getRefreshTokenUUID(),
                refreshTokenExpiration / 1000);

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

    @GetMapping("refresh/token")
    public ResponseEntity<RefreshTokenResponse> refreshToken(
            HttpServletRequest request,
            HttpServletResponse response
    ) {
        String refreshTokenUUID = getRefreshTokenCookie(request);
        RefreshTokenResult refreshTokenResult = authService.refreshToken(refreshTokenUUID);

        setRefreshTokenCookie(response,
                refreshTokenResult.getRefreshTokenUUID(),
                refreshTokenExpiration / 1000);

        return new ResponseEntity<>(new RefreshTokenResponse(
            refreshTokenResult.getUserResponse(),
            refreshTokenResult.getAccessToken(),
            refreshTokenResult.getMessage()
        ), HttpStatus.OK);
    }

    @DeleteMapping("logout")
    public ResponseEntity<SuccessResponse> logout(
            HttpServletRequest request,
            HttpServletResponse response) {

        String refreshTokenUUID = getRefreshTokenCookie(request);
        LogoutResult logoutResult = authService.logout(refreshTokenUUID);

        setRefreshTokenCookie(response,
                "",
                0);

        return new ResponseEntity<>(new SuccessResponse(
                logoutResult.getMessage()), HttpStatus.OK);

    }

    private void setRefreshTokenCookie(
            HttpServletResponse response,
            String refreshTokenUUID,
            long refreshTokenExpiration) {

        if(refreshTokenUUID != null){
            ResponseCookie refreshTokenCookie = ResponseCookie.from(
                            refreshTokenCookieName,
                            refreshTokenUUID)
                    .httpOnly(true)
                    .secure(false)
                    .path("/")
                    .sameSite("Strict")
                    .maxAge(refreshTokenExpiration)
                    .build();

            response.addHeader(
                    HttpHeaders.SET_COOKIE,
                    refreshTokenCookie.toString());
        }
    }

    private String getRefreshTokenCookie(HttpServletRequest request) {
        String refreshTokenUUID = null;

        if(request.getCookies() != null) {
            for(Cookie cookie: request.getCookies()) {
                if(cookie.getName().equals(refreshTokenCookieName)){
                    refreshTokenUUID = cookie.getValue();
                    break;
                }
            }
        }

        if(refreshTokenUUID != null) {
            return refreshTokenUUID;
        }

        throw new APIException(
                HttpStatus.BAD_REQUEST,
                AppConstant.ERROR_REFRESH_TOKEN_COOKIE_EMPTY_OR_NULL
        );
    }
}
