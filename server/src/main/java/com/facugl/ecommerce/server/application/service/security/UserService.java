package com.facugl.ecommerce.server.application.service.security;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.facugl.ecommerce.server.application.port.input.security.users.CreateUserUseCase;
import com.facugl.ecommerce.server.application.port.input.security.users.DeleteUserUseCase;
import com.facugl.ecommerce.server.application.port.input.security.users.GetAllUsersUseCase;
import com.facugl.ecommerce.server.application.port.input.security.users.GetUserUseCase;
import com.facugl.ecommerce.server.application.port.input.security.users.UpdateUserUseCase;
import com.facugl.ecommerce.server.application.port.output.security.RoleOutputPort;
import com.facugl.ecommerce.server.application.port.output.security.UserOutputPort;
import com.facugl.ecommerce.server.common.UseCase;
import com.facugl.ecommerce.server.domain.model.security.Role;
import com.facugl.ecommerce.server.domain.model.security.User;
import com.facugl.ecommerce.server.infrastructure.adapter.input.rest.advice.generic.InvalidPasswordException;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@UseCase
public class UserService implements
        CreateUserUseCase,
        GetUserUseCase,
        GetAllUsersUseCase,
        UpdateUserUseCase,
        DeleteUserUseCase {
    private final UserOutputPort userOutputPort;
    private final RoleOutputPort roleOutputPort;
    private final PasswordEncoder passwordEncoder;

    @Value("${security.default.role}")
    private String defaultRole;

    @Transactional
    @Override
    public User createUser(User userToCreate) {
        validatePassword(userToCreate);

        Role role = roleOutputPort.findRoleByName(defaultRole);

        userToCreate.setRole(role);
        userToCreate.setPassword(passwordEncoder.encode(userToCreate.getPassword()));

        return userOutputPort.createUser(userToCreate);
    }

    private void validatePassword(User user) {
        if (!StringUtils.hasText(user.getPassword()) || !StringUtils.hasText(user.getRepeatedPassword())) {
            throw new InvalidPasswordException("Passwords don't match.");
        }

        if (!user.getPassword().equals(user.getRepeatedPassword())) {
            throw new InvalidPasswordException("Passwords don't match.");
        }
    }

    @Transactional(readOnly = true)
    @Override
    public List<User> getAllUsers() {
        return userOutputPort.getAllUsers();
    }

    @Transactional(readOnly = true)
    @Override
    public User getUserById(Long userId) {
        return userOutputPort.findUserById(userId);
    }

    @Transactional(readOnly = true)
    @Override
    public User getUserByUsername(String username) {
        return userOutputPort.findUserByUsername(username);
    }

    @Transactional
    @Override
    public User updateUser(Long userId, User userToUpdate) {
        validatePassword(userToUpdate);

        userToUpdate.setPassword(passwordEncoder.encode(userToUpdate.getPassword()));

        return userOutputPort.updateUser(userId, userToUpdate);
    }

    @Transactional
    @Override
    public void deleteUserById(Long userId) {
        userOutputPort.deleteUserById(userId);
    }
}
