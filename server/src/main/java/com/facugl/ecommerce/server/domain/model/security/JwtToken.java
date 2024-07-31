package com.facugl.ecommerce.server.domain.model.security;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class JwtToken {
    private Long id;
    private String token;
    private Date expiration;
    private boolean isValid;
    private Long userId;
}
