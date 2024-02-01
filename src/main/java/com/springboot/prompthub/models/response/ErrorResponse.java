package com.springboot.prompthub.models.response;

import com.springboot.prompthub.utils.AppConstant;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ErrorResponse {
    private int statusCode = HttpStatus.BAD_REQUEST.value();
    private final String status = "Error";
    private String message = AppConstant.ERROR_API_DEFAULT_MESSAGE;
    private Date timestamp;
    private Map<String, String[]> errors;
    private String URI;
}
