package com.facugl.ecommerce.server.infrastructure.adapter.input.rest;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.facugl.ecommerce.server.application.mapper.ApplicationUserMapper;
import com.facugl.ecommerce.server.application.port.input.users.CreateUserUseCase;
import com.facugl.ecommerce.server.application.port.input.users.GetAllUsersUseCase;
import com.facugl.ecommerce.server.common.WebAdapter;
import com.facugl.ecommerce.server.domain.model.users.User;
import com.facugl.ecommerce.server.infrastructure.adapter.input.rest.data.request.UserRequest;
import com.facugl.ecommerce.server.infrastructure.adapter.input.rest.data.response.UserResponse;
import com.facugl.ecommerce.server.infrastructure.adapter.input.rest.validation.groups.users.CreateUserValidationGroup;

import lombok.RequiredArgsConstructor;

@CrossOrigin(origins = "http://localhost:3000", originPatterns = "*")
@RequiredArgsConstructor
@RequestMapping("/users")
@RestController
@WebAdapter
public class UserRestAdapter {

    private final ApplicationUserMapper userMapper;

    private final CreateUserUseCase createUserUseCase;
    private final GetAllUsersUseCase getAllUsersUseCase;

    @PostMapping
    public ResponseEntity<UserResponse> createUser(
            @RequestBody @Validated(CreateUserValidationGroup.class) UserRequest userToCreate) {
        User user = userMapper.mapUserRequestToUser(userToCreate);

        User createdUser = createUserUseCase.createUser(user);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(userMapper.mapUserToUserResponse(createdUser));
    }

    @PostMapping("/register")
    public ResponseEntity<UserResponse> registerUser(
            @RequestBody @Validated(CreateUserValidationGroup.class) UserRequest userToCreate) {
        User user = userMapper.mapUserRequestToUser(userToCreate);
        user.setAdmin(false);

        User createdUser = createUserUseCase.createUser(user);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(userMapper.mapUserToUserResponse(createdUser));
    }

    @GetMapping
    public ResponseEntity<List<UserResponse>> getAllUsers() {
        List<User> users = getAllUsersUseCase.getAllUsers();

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(users
                        .stream()
                        .map(userMapper::mapUserToUserResponse)
                        .collect(Collectors.toList()));
    }

}
