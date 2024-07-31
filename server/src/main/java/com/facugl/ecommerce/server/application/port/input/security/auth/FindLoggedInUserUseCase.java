package com.facugl.ecommerce.server.application.port.input.security.auth;

import com.facugl.ecommerce.server.infrastructure.adapter.input.rest.data.response.security.LoggedInUserResponse;

public interface FindLoggedInUserUseCase {
    LoggedInUserResponse findLoggedInUser();
}
