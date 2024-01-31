package com.springboot.prompthub.models.response;

import com.springboot.prompthub.utils.AppConstant;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SuccessResponse {
    private int statusCode = HttpStatus.OK.value();
    private static final String status = "Success";
    private String message = AppConstant.MESSAGE_API_DEFAULT_MESSAGE;
}
