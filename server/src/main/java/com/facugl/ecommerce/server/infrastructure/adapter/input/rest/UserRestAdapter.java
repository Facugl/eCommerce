package com.facugl.ecommerce.server.infrastructure.adapter.input.rest;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.facugl.ecommerce.server.application.mapper.security.ApplicationUserMapper;
import com.facugl.ecommerce.server.application.port.input.security.auth.RegisterUserUseCase;
import com.facugl.ecommerce.server.application.port.input.security.users.DeleteUserUseCase;
import com.facugl.ecommerce.server.application.port.input.security.users.GetAllUsersUseCase;
import com.facugl.ecommerce.server.application.port.input.security.users.GetUserUseCase;
import com.facugl.ecommerce.server.application.port.input.security.users.UpdateUserUseCase;
import com.facugl.ecommerce.server.common.WebAdapter;
import com.facugl.ecommerce.server.domain.model.security.User;
import com.facugl.ecommerce.server.infrastructure.adapter.input.rest.data.request.security.UserRequest;
import com.facugl.ecommerce.server.infrastructure.adapter.input.rest.data.response.security.UserResponse;
import com.facugl.ecommerce.server.infrastructure.adapter.input.rest.validation.groups.security.users.CreateUserValidationGroup;
import com.facugl.ecommerce.server.infrastructure.adapter.input.rest.validation.groups.security.users.UpdateUserValidationGroup;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("/users")
@RestController
@WebAdapter
public class UserRestAdapter {
    private final ApplicationUserMapper userMapper;
    private final RegisterUserUseCase registerUserUseCase;
    private final GetUserUseCase getUserUseCase;
    private final GetAllUsersUseCase getAllUsersUseCase;
    private final UpdateUserUseCase updateUserUseCase;
    private final DeleteUserUseCase deleteUserUseCase;

    @PostMapping
    public ResponseEntity<UserResponse> createUser(
            @RequestBody @Validated(CreateUserValidationGroup.class) UserRequest userToCreate) {
        User user = userMapper.mapUserRequestToUser(userToCreate);

        UserResponse createdUser = registerUserUseCase.registerOneCustomer(user);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(createdUser);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getOneUser(@PathVariable Long id) {
        User user = getUserUseCase.getUserById(id);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(userMapper.mapUserToUserResponse(user));
    }

    @GetMapping
    public ResponseEntity<List<UserResponse>> getAllUsers() {
        List<UserResponse> users = getAllUsersUseCase.getAllUsers()
                .stream()
                .map(userMapper::mapUserToUserResponse)
                .collect(Collectors.toList());

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(users);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserResponse> updateUser(
            @PathVariable Long id,
            @Validated(UpdateUserValidationGroup.class) @RequestBody UserRequest userToUpdate) {
        User user = userMapper.mapUserRequestToUser(userToUpdate);

        User savedUser = updateUserUseCase.updateUser(id, user);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(userMapper.mapUserToUserResponse(savedUser));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUserById(@PathVariable Long id) {
        deleteUserUseCase.deleteUserById(id);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
