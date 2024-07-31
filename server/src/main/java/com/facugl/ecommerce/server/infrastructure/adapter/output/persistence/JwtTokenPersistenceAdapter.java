package com.facugl.ecommerce.server.infrastructure.adapter.output.persistence;

import java.util.Optional;

import com.facugl.ecommerce.server.application.port.output.security.JwtTokenOutputPort;
import com.facugl.ecommerce.server.common.PersistenceAdapter;
import com.facugl.ecommerce.server.domain.model.security.JwtToken;
import com.facugl.ecommerce.server.infrastructure.adapter.input.rest.advice.generic.EntityNotFoundException;
import com.facugl.ecommerce.server.infrastructure.adapter.output.persistence.entity.security.JwtTokenEntity;
import com.facugl.ecommerce.server.infrastructure.adapter.output.persistence.entity.security.UserEntity;
import com.facugl.ecommerce.server.infrastructure.adapter.output.persistence.repository.security.JwtTokenRepository;
import com.facugl.ecommerce.server.infrastructure.adapter.output.persistence.repository.security.UserRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@PersistenceAdapter
public class JwtTokenPersistenceAdapter implements JwtTokenOutputPort {
    private final JwtTokenRepository jwtTokenRepository;
    private final UserRepository userRepository;

    @Override
    public Optional<JwtTokenEntity> findJwtTokenByToken(String jwt) {
        return jwtTokenRepository.findByToken(jwt);
    }

    @Override
    public JwtToken saveJwtToken(JwtToken jwtTokenToSave) {
        JwtTokenEntity jwtTokenEntity = JwtTokenEntity.builder()
                .token(jwtTokenToSave.getToken())
                .expiration(jwtTokenToSave.getExpiration())
                .isValid(jwtTokenToSave.isValid())
                .build();

        if (jwtTokenToSave.getUserId() != null) {
            UserEntity userEntity = userRepository.findById(jwtTokenToSave.getUserId())
                    .orElseThrow(() -> new EntityNotFoundException(
                            "User with id: " + jwtTokenToSave.getUserId() + " not found."));

            jwtTokenEntity.setUser(userEntity);
        }

        JwtTokenEntity savedJwtToken = jwtTokenRepository.save(jwtTokenEntity);

        return JwtToken.builder()
                .id(savedJwtToken.getId())
                .token(savedJwtToken.getToken())
                .expiration(savedJwtToken.getExpiration())
                .isValid(savedJwtToken.isValid())
                .build();
    }
}
