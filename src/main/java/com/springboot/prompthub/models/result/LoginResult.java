package com.springboot.prompthub.models.result;

import com.springboot.prompthub.models.response.UserResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginResult {
    private String accessToken;
    private String refreshTokenUUID;
    private UserResponse userResponse;
    private String message;
}
