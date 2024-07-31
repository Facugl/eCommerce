package com.facugl.ecommerce.server.application.port.output.security;

import java.util.Optional;

import com.facugl.ecommerce.server.domain.model.security.JwtToken;
import com.facugl.ecommerce.server.infrastructure.adapter.output.persistence.entity.security.JwtTokenEntity;

public interface JwtTokenOutputPort {

    Optional<JwtTokenEntity> findJwtTokenByToken(String jwt);

    JwtToken saveJwtToken(JwtToken jwtTokenToSave);

}
