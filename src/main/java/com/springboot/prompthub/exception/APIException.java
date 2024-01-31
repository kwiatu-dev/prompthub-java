package com.springboot.prompthub.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

import java.util.Map;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class APIException extends RuntimeException {
    private HttpStatusCode statusCode;
    private String message;
    private Map<String, String[]> errors;

    public APIException(HttpStatusCode statusCode, String message){
        this.statusCode = statusCode;
        this.message = message;
    }
}
