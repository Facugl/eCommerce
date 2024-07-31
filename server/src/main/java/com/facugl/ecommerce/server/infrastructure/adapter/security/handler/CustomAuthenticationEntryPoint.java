package com.facugl.ecommerce.server.infrastructure.adapter.security.handler;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import com.facugl.ecommerce.server.infrastructure.adapter.input.rest.advice.DateFormat;
import com.facugl.ecommerce.server.infrastructure.adapter.input.rest.advice.ErrorMessage;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(
            HttpServletRequest request,
            HttpServletResponse response,
            AuthenticationException authException) throws IOException, ServletException {

        ErrorMessage errorMessage = ErrorMessage.builder()
                .backendMessage(authException.getLocalizedMessage())
                .url(request.getRequestURL().toString())
                .message("No authentication credentials were found. Please log in to access this function.")
                .method(request.getMethod())
                .timestamp(LocalDateTime.now().format(DateTimeFormatter.ofPattern(DateFormat.DATE_TIME_PATTERN)))
                .build();

        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(HttpStatus.UNAUTHORIZED.value());

        String errorMessageAsJson = new ObjectMapper().writeValueAsString(errorMessage);
        response.getWriter().write(errorMessageAsJson);
    }
}
