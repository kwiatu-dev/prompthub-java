package com.springboot.prompthub.exception;

import com.springboot.prompthub.models.response.ErrorResponse;
import com.springboot.prompthub.utils.AppConstant;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import javax.naming.AuthenticationException;
import java.util.Date;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(APIException.class)
    public ResponseEntity<ErrorResponse> handleAPIException(
            APIException apiException,
            WebRequest webRequest) {

        ErrorResponse errorResponse = new ErrorResponse(
                apiException.getStatusCode().value(),
                apiException.getMessage(),
                new Date(),
                apiException.getErrors(),
                webRequest.getDescription(false)
        );

        return new ResponseEntity<>(errorResponse, apiException.getStatusCode());
    }

    @ExceptionHandler(org.springframework.security.access.AccessDeniedException.class)
    public ResponseEntity<ErrorResponse> handleGlobalException(
            AccessDeniedException exception,
            WebRequest webRequest) {

        ErrorResponse errorResponse = new ErrorResponse(
                HttpStatus.FORBIDDEN.value(),
                AppConstant.ERROR_API_ACCESS_DENIED,
                new Date(),
                null,
                webRequest.getDescription(false)
        );

        return new ResponseEntity<>(errorResponse, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGlobalException(
            Exception exception,
            WebRequest webRequest) {

        ErrorResponse errorResponse = new ErrorResponse(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                exception.getMessage(),
                new Date(),
                null,
                webRequest.getDescription(false)
        );

        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
