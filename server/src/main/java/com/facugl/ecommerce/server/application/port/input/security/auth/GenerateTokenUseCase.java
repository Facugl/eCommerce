package com.facugl.ecommerce.server.application.port.input.security.auth;

import org.springframework.security.core.userdetails.UserDetails;

import io.jsonwebtoken.Claims;

public interface GenerateTokenUseCase {
    String generateToken(UserDetails user, Claims extraClaims);
}
