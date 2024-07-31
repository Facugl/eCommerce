package com.facugl.ecommerce.server.application.port.input.security.auth;

import java.util.Optional;

import com.facugl.ecommerce.server.domain.model.security.JwtToken;

public interface FindJwtTokenUseCase {
    Optional<JwtToken> findJwtTokenByToken(String jwt);
}
