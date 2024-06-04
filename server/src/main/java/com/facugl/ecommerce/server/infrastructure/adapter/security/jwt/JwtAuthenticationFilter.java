package com.facugl.ecommerce.server.infrastructure.adapter.security.jwt;

import static com.facugl.ecommerce.server.infrastructure.adapter.security.config.TokenJwtConfig.*;

import java.io.IOException;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.facugl.ecommerce.server.domain.model.users.User;
import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private AuthenticationManager authenticationManager;

    public JwtAuthenticationFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {
        // Tomo el objeto json y lo convierto a un objeto de java
        User user = null;
        String username = null;
        String password = null;

        try {
            user = new ObjectMapper().readValue(request.getInputStream(), User.class);
            username = user.getUsername();
            password = user.getPassword();
        } catch (StreamReadException e) {
            e.printStackTrace();
        } catch (DatabindException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username,
                password);

        return authenticationManager.authenticate(authenticationToken);
    }

    // pero primero necesitamos una llave secreta
    @Override
    protected void successfulAuthentication(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain chain,
            Authentication authResult) throws IOException, ServletException {

        // obtengo el username
        org.springframework.security.core.userdetails.User user = (org.springframework.security.core.userdetails.User) authResult.getPrincipal();
        String username = user.getUsername();
        Collection<? extends GrantedAuthority> roles = authResult.getAuthorities();

        // obtengo los claims, agrego los roles
        Claims claims = Jwts.claims()
                .add("authorities", new ObjectMapper().writeValueAsString(roles))
                .add("username", username)
                .build();

        // agregando los claims, el token queda más completo
        String token = Jwts.builder()
                .subject(username)
                .claims(claims)
                .expiration(new Date(System.currentTimeMillis() + 3600000))
                .issuedAt(new Date())
                .signWith(SECRET_KEY)
                .compact();

        // devuelvo el token a la vista, al cliente
        response.addHeader(HEADER_AUTHORIZATION, PREFIX_TOKEN + token);

        // también lo paso como un json
        Map<String, String> body = new HashMap<>();
        body.put("token", token);
        body.put("username", username); // lo agrego, a pesar de que va en el token
        body.put("message", String.format("Hola %s has iniciado sesion con exito!", username));

        // convierto el map en un json y escribo en la respuesta el json
        response.getWriter().write(new ObjectMapper().writeValueAsString(body));
        response.setContentType(CONTENT_TYPE);
        response.setStatus(200);
    }

    // ahora voy con el metodo para cuando falle la autenticacion
    @Override
    protected void unsuccessfulAuthentication(
            HttpServletRequest request,
            HttpServletResponse response,
            AuthenticationException failed) throws IOException, ServletException {
        Map<String, String> body = new HashMap<>();
        // nunca decir cual de los dos esta mal, por si hay algún picaro
        body.put("message", "Error en la autenticacion, username o password incorrectos.");
        body.put("error", failed.getMessage()); // mando la excepcion (ojo, porque si le pifia al username avisa)

        // y de nuevo convierto a json
        response.getWriter().write(new ObjectMapper().writeValueAsString(body));
        response.setContentType(CONTENT_TYPE);
        response.setStatus(401);

    }

}
