package com.facugl.ecommerce.server.application.port.input.security.auth;

public interface ExtractUsernameUseCase {
    String extractUsername(String jwt);
}
