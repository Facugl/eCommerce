package com.facugl.ecommerce.server.application.service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import com.facugl.ecommerce.server.application.port.input.users.CreateUserUseCase;
import com.facugl.ecommerce.server.application.port.input.users.GetAllUsersUseCase;
import com.facugl.ecommerce.server.application.port.output.RoleOutputPort;
import com.facugl.ecommerce.server.application.port.output.UserOutputPort;
import com.facugl.ecommerce.server.common.UseCase;
import com.facugl.ecommerce.server.domain.model.roles.Role;
import com.facugl.ecommerce.server.domain.model.users.User;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@UseCase
public class UserService implements CreateUserUseCase, GetAllUsersUseCase {

    private final UserOutputPort userOutputPort;
    private final RoleOutputPort roleOutputPort;

    private final PasswordEncoder passwordEncoder;

    @Transactional
    @Override
    public User createUser(User userToCreate) {
        Role roleUser = roleOutputPort.findRoleByName("ROLE_USER");

        Set<Role> roles = new HashSet<>();
        roles.add(roleUser);

        if(userToCreate.isAdmin()) {
            Role roleAdmin = roleOutputPort.findRoleByName("ROLE_ADMINISTRATOR");
            roles.add(roleAdmin);
        }

        userToCreate.setRoles(roles);
        userToCreate.setPassword(passwordEncoder.encode(userToCreate.getPassword()));

        return userOutputPort.createUser(userToCreate);
    }

    @Transactional(readOnly = true)
    @Override
    public List<User> getAllUsers() {
        return userOutputPort.getAllUsers();
    }

}
