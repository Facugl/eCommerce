package com.facugl.ecommerce.server.application.port.input.security.users;

import com.facugl.ecommerce.server.domain.model.security.User;

public interface UpdateUserUseCase {
    User updateUser(Long userId, User userToUpdate);
}
