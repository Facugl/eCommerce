package com.facugl.ecommerce.server.infrastructure.adapter.security.handler;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import com.facugl.ecommerce.server.infrastructure.adapter.input.rest.advice.DateFormat;
import com.facugl.ecommerce.server.infrastructure.adapter.input.rest.advice.ErrorMessage;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler {
	@Override
	public void handle(
			HttpServletRequest request,
			HttpServletResponse response,
			AccessDeniedException accessDeniedException) throws IOException, ServletException {

		ErrorMessage errorMessage = ErrorMessage.builder()
				.backendMessage(accessDeniedException.getLocalizedMessage())
				.url(request.getRequestURL().toString())
				.message(
						"Access denied. You do not have the necessary permissions to access this feature. Please contact the administrator if you believe this is an error.")
				.method(request.getMethod())
				.timestamp(LocalDateTime.now()
						.format(DateTimeFormatter.ofPattern(DateFormat.DATE_TIME_PATTERN)))
				.build();

		response.setContentType(MediaType.APPLICATION_JSON_VALUE);
		response.setStatus(HttpStatus.FORBIDDEN.value());

		String errorMessageAsJson = new ObjectMapper().writeValueAsString(errorMessage);
		response.getWriter().write(errorMessageAsJson);
	}
}
