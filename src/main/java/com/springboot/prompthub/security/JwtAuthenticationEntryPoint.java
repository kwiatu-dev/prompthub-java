package com.springboot.prompthub.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.springboot.prompthub.models.response.ErrorResponse;
import com.springboot.prompthub.utils.AppConstant;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {
    private final ObjectMapper objectMapper;

    public JwtAuthenticationEntryPoint(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public void commence(
            HttpServletRequest request,
            HttpServletResponse response,
            AuthenticationException authException) throws IOException, ServletException {

        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json;charset=UTF-8");

        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setStatusCode(HttpServletResponse.SC_UNAUTHORIZED);
        errorResponse.setMessage(AppConstant.ERROR_API_AUTHENTICATION_REQUIRED);
        errorResponse.setTimestamp(new Date());
        errorResponse.setURI(request.getRequestURI());

        String errorResponseJson = objectMapper.writeValueAsString(errorResponse);

        PrintWriter writer = response.getWriter();
        writer.write(errorResponseJson);
        writer.flush();
        writer.close();
    }
}
