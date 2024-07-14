package com.facugl.ecommerce.server.application.port.input.users;

import com.facugl.ecommerce.server.domain.model.users.User;

public interface CreateUserUseCase {
    User createUser(User userToCreate);
}