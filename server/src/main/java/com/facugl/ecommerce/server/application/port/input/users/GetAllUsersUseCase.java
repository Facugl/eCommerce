package com.facugl.ecommerce.server.application.port.input.users;

import java.util.List;

import com.facugl.ecommerce.server.domain.model.users.User;

public interface GetAllUsersUseCase {
    List<User> getAllUsers();
}
