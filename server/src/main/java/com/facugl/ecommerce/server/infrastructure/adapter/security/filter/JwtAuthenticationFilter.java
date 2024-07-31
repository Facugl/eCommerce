package com.facugl.ecommerce.server.infrastructure.adapter.security.filter;

import java.io.IOException;
import java.util.Date;
import java.util.Optional;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.facugl.ecommerce.server.application.port.input.security.auth.ExtractUsernameUseCase;
import com.facugl.ecommerce.server.application.service.CustomUserDetailsService;
import com.facugl.ecommerce.server.application.service.security.JwtService;
import com.facugl.ecommerce.server.domain.model.security.JwtToken;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final ExtractUsernameUseCase extractUsernameUseCase;
    private final CustomUserDetailsService userDetailsService;
    private final JwtService jwtService;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain) throws ServletException, IOException {

        String jwt = jwtService.extractJwtFromRequest(request);

        if (jwt == null || !StringUtils.hasText(jwt)) {
            filterChain.doFilter(request, response);
            return;
        }

        Optional<JwtToken> jwtToken = jwtService.findJwtTokenByToken(jwt);
        boolean isValid = validateToken(jwtToken);

        if (!isValid) {
            filterChain.doFilter(request, response);
        }

        String username = extractUsernameUseCase.extractUsername(jwt);
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);

        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                username,
                null,
                userDetails.getAuthorities());

        authToken.setDetails(new WebAuthenticationDetails(request));

        SecurityContextHolder.getContext().setAuthentication(authToken);

        filterChain.doFilter(request, response);
    }

    private boolean validateToken(Optional<JwtToken> optionalJwtToken) {
        if (!optionalJwtToken.isPresent()) {
            return false;
        }

        JwtToken jwtToken = optionalJwtToken.get();
        Date now = new Date(System.currentTimeMillis());
        boolean isValid = jwtToken.isValid() && jwtToken.getExpiration().after(now);

        if (!isValid) {
            updateJwtTokenStatus(jwtToken);
        }

        return isValid;
    }

    private void updateJwtTokenStatus(JwtToken jwtToken) {
        jwtToken.setValid(false);

        jwtService.saveJwtToken(jwtToken);
    }
}
