package com.springboot.prompthub.models.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class RefreshTokenResponse extends SuccessResponse{
    private UserResponse user;
    private String accessToken;
    private String message;
}
