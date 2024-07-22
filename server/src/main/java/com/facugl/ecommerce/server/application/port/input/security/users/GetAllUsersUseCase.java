package com.facugl.ecommerce.server.application.port.input.security.users;

import java.util.List;

import com.facugl.ecommerce.server.domain.model.security.User;

public interface GetAllUsersUseCase {
    List<User> getAllUsers();
}
