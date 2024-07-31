package com.facugl.ecommerce.server.application.port.input.security.auth;

import com.facugl.ecommerce.server.domain.model.security.User;
import com.facugl.ecommerce.server.infrastructure.adapter.input.rest.data.response.security.UserResponse;

public interface RegisterUserUseCase {
    UserResponse registerOneCustomer(User userToCreate);
}
