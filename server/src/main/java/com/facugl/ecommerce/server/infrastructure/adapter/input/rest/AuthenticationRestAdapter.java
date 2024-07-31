package com.facugl.ecommerce.server.infrastructure.adapter.input.rest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.facugl.ecommerce.server.application.port.input.security.auth.FindLoggedInUserUseCase;
import com.facugl.ecommerce.server.application.port.input.security.auth.LoginUseCase;
import com.facugl.ecommerce.server.application.port.input.security.auth.LogoutUseCase;
import com.facugl.ecommerce.server.application.service.security.JwtService;
import com.facugl.ecommerce.server.common.WebAdapter;
import com.facugl.ecommerce.server.infrastructure.adapter.input.rest.data.request.security.AuthenticationRequest;
import com.facugl.ecommerce.server.infrastructure.adapter.input.rest.data.response.security.AuthenticationResponse;
import com.facugl.ecommerce.server.infrastructure.adapter.input.rest.data.response.security.LoggedInUserResponse;
import com.facugl.ecommerce.server.infrastructure.adapter.input.rest.data.response.security.LogoutResponse;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("/auth")
@RestController
@WebAdapter
public class AuthenticationRestAdapter {
    private final LoginUseCase loginUseCase;
    private final LogoutUseCase logoutUseCase;
    private final FindLoggedInUserUseCase findLoggedInUserUseCase;
    private final JwtService jwtService;

    @GetMapping("/validate-token")
    public ResponseEntity<Boolean> validate(@RequestParam String jwt) {
        boolean isTokenValid = jwtService.validateToken(jwt);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(isTokenValid);
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest authRequest) {
        AuthenticationResponse response = loginUseCase.login(authRequest);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response);
    }

    @PostMapping("/logout")
    public ResponseEntity<LogoutResponse> logout(HttpServletRequest request) {
        logoutUseCase.logout(request);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new LogoutResponse("Successful logout!"));
    }

    @GetMapping("/profile")
    public ResponseEntity<LoggedInUserResponse> findMyProfile() {
        LoggedInUserResponse loggedInUser = findLoggedInUserUseCase.findLoggedInUser();

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(loggedInUser);
    }
}
