package com.facugl.ecommerce.server.application.port.input.security.auth;

import com.facugl.ecommerce.server.infrastructure.adapter.input.rest.data.request.security.AuthenticationRequest;
import com.facugl.ecommerce.server.infrastructure.adapter.input.rest.data.response.security.AuthenticationResponse;

public interface LoginUseCase {
    AuthenticationResponse login(AuthenticationRequest authenticationRequest);
}
