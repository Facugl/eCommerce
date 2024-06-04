package com.facugl.ecommerce.server.application.port.output;

import java.util.List;

import com.facugl.ecommerce.server.domain.model.users.User;

public interface UserOutputPort {

    User createUser(User userToCreate);

    User findUserById(Long id);
    
    User findUserByUsername(String username);

    List<User> getAllUsers();

    User updateUser(Long id, User userToUpdate);

    void deleteUserById(Long id);

}
