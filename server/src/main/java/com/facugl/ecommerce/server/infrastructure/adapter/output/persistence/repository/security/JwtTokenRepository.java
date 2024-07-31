package com.facugl.ecommerce.server.infrastructure.adapter.output.persistence.repository.security;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.facugl.ecommerce.server.infrastructure.adapter.output.persistence.entity.security.JwtTokenEntity;

public interface JwtTokenRepository extends JpaRepository<JwtTokenEntity, Long> {
    Optional<JwtTokenEntity> findByToken(String jwt);
}
