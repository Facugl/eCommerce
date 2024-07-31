package com.facugl.ecommerce.server.application.port.input.security.auth;

import jakarta.servlet.http.HttpServletRequest;

public interface LogoutUseCase {
    void logout(HttpServletRequest request);
}
