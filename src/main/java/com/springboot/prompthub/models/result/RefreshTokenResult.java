package com.springboot.prompthub.models.result;

import com.springboot.prompthub.models.entity.RefreshToken;
import com.springboot.prompthub.models.response.UserResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RefreshTokenResult {
    private String accessToken;
    private String refreshToken;
    private UserResponse userResponse;
    private String message;
}
