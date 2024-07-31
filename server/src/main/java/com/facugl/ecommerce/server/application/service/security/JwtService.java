package com.facugl.ecommerce.server.application.service.security;

import java.util.Date;
import java.util.Optional;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.facugl.ecommerce.server.application.port.input.security.auth.ExtractUsernameUseCase;
import com.facugl.ecommerce.server.application.port.input.security.auth.FindJwtTokenUseCase;
import com.facugl.ecommerce.server.application.port.input.security.auth.GenerateTokenUseCase;
import com.facugl.ecommerce.server.application.port.input.security.auth.SaveJwtTokenUseCase;
import com.facugl.ecommerce.server.application.port.input.security.auth.ValidateTokenUseCase;
import com.facugl.ecommerce.server.application.port.output.security.JwtTokenOutputPort;
import com.facugl.ecommerce.server.common.UseCase;
import com.facugl.ecommerce.server.domain.model.security.JwtToken;
import com.facugl.ecommerce.server.infrastructure.adapter.input.rest.advice.generic.EntityNotFoundException;
import com.facugl.ecommerce.server.infrastructure.adapter.output.persistence.entity.security.JwtTokenEntity;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@UseCase
public class JwtService implements
        GenerateTokenUseCase,
        ExtractUsernameUseCase,
        ValidateTokenUseCase,
        FindJwtTokenUseCase,
        SaveJwtTokenUseCase {
    @Value("${security.jwt.expiration-in-minutes}")
    private Long EXPIRATION_IN_MINUTES;

    private static final SecretKey SECRET_KEY = Jwts.SIG.HS256.key().build();
    private final JwtTokenOutputPort jwtTokenOutputPort;

    @Transactional
    @Override
    public String generateToken(UserDetails user, Claims extraClaims) {
        Date issuedAt = new Date(System.currentTimeMillis());
        Date expiration = new Date((EXPIRATION_IN_MINUTES * 60 * 1000) + issuedAt.getTime());

        String jwt = Jwts.builder()
                .header()
                .type("JWT")
                .and()
                .subject(user.getUsername())
                .issuedAt(issuedAt)
                .expiration(expiration)
                .claims(extraClaims)
                .signWith(SECRET_KEY)
                .compact();

        return jwt;
    }

    @Transactional
    @Override
    public String extractUsername(String jwt) {
        return extractAllClaims(jwt).getSubject();
    }

    private Claims extractAllClaims(String jwt) {
        return Jwts.parser().verifyWith(SECRET_KEY).build().parseSignedClaims(jwt).getPayload();
    }

    @Transactional
    @Override
    public boolean validateToken(String jwt) {
        try {
            extractUsername(jwt);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public String extractJwtFromRequest(HttpServletRequest request) {
        String authorizationHeader = request.getHeader("Authorization");

        if (!StringUtils.hasText(authorizationHeader) || !authorizationHeader.startsWith("Bearer ")) {
            return null;
        }

        return authorizationHeader.split(" ")[1];
    }

    public Date extractExpiration(String jwt) {
        return extractAllClaims(jwt).getExpiration();
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<JwtToken> findJwtTokenByToken(String jwt) {
        JwtTokenEntity jwtTokenEntity = jwtTokenOutputPort.findJwtTokenByToken(jwt)
                .orElseThrow(() -> new EntityNotFoundException("Jwt Token not found."));

        JwtToken jwtToken = JwtToken.builder()
                .id(jwtTokenEntity.getId())
                .token(jwtTokenEntity.getToken())
                .expiration(jwtTokenEntity.getExpiration())
                .isValid(jwtTokenEntity.isValid())
                .userId(jwtTokenEntity.getUser().getId())
                .build();

        return Optional.of(jwtToken);
    }

    @Transactional
    @Override
    public JwtToken saveJwtToken(JwtToken jwtTokenToSave) {
        return jwtTokenOutputPort.saveJwtToken(jwtTokenToSave);
    }
}
