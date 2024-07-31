package com.facugl.ecommerce.server.application.port.input.security.auth;

public interface ValidateTokenUseCase {
    boolean validateToken(String jwt);
}
