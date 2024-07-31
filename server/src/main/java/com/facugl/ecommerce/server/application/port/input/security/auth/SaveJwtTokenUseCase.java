package com.facugl.ecommerce.server.application.port.input.security.auth;

import com.facugl.ecommerce.server.domain.model.security.JwtToken;

public interface SaveJwtTokenUseCase {
    JwtToken saveJwtToken(JwtToken jwtTokenToSave);
}
