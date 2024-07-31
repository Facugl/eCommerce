package com.facugl.ecommerce.server.application.port.input.security.users;

import com.facugl.ecommerce.server.domain.model.security.User;

public interface CreateUserUseCase {
    User createUser(User userToCreate);
}
