package com.springboot.prompthub.models.response;

import com.springboot.prompthub.models.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class LoginResponse extends SuccessResponse{
    private UserResponse user;
    private String accessToken;
    private String message;
}
