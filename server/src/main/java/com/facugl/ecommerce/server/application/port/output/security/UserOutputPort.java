package com.facugl.ecommerce.server.application.port.output.security;

import java.util.List;

import com.facugl.ecommerce.server.domain.model.security.User;

public interface UserOutputPort {

    User createUser(User userToCreate);

    User findUserById(Long userId);
    
    User findUserByUsername(String username);

    List<User> getAllUsers();

    User updateUser(Long userId, User userToUpdate);

    void deleteUserById(Long userId);

}
