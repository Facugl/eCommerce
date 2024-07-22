package com.facugl.ecommerce.server.infrastructure.adapter.output.persistence;

import java.util.List;
import java.util.stream.Collectors;

import com.facugl.ecommerce.server.application.port.output.security.UserOutputPort;
import com.facugl.ecommerce.server.common.PersistenceAdapter;
import com.facugl.ecommerce.server.domain.model.security.User;
import com.facugl.ecommerce.server.infrastructure.adapter.input.rest.advice.generic.EntityAlreadyExistsException;
import com.facugl.ecommerce.server.infrastructure.adapter.input.rest.advice.generic.EntityNotFoundException;
import com.facugl.ecommerce.server.infrastructure.adapter.output.persistence.entity.security.UserEntity;
import com.facugl.ecommerce.server.infrastructure.adapter.output.persistence.mapper.security.PersistenceUserMapper;
import com.facugl.ecommerce.server.infrastructure.adapter.output.persistence.repository.security.UserRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@PersistenceAdapter
public class UserPersistenceAdapter implements UserOutputPort {
    private final UserRepository userRepository;
    private final PersistenceUserMapper userMapper;

    @Override
    public User createUser(User userToCreate) {
        if (userRepository.isUsernameUnique(userToCreate.getUsername())) {
            UserEntity userEntity = userMapper.mapUserToUserEntity(userToCreate);

            UserEntity savedUserEntity = userRepository.save(userEntity);

            return userMapper.mapUserEntityToUser(savedUserEntity);
        } else {
            throw new EntityAlreadyExistsException(
                    "User with username: " + userToCreate.getUsername() + " already exists.");
        }
    }

    @Override
    public void deleteUserById(Long userId) {
        UserEntity userEntity = userRepository
                .findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User with id: " + userId + " not found."));

        userRepository.delete(userEntity);

    }

    @Override
    public User findUserById(Long userId) {
        return userRepository
                .findById(userId)
                .map(userMapper::mapUserEntityToUser)
                .orElseThrow(() -> new EntityNotFoundException("User with id: " + userId + " not found."));
    }

    @Override
    public User findUserByUsername(String username) {
        return userRepository
                .findByUsername(username)
                .map(userMapper::mapUserEntityToUser)
                .orElseThrow(() -> new EntityNotFoundException("User with username: " + username + " not found."));
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository
                .findAll()
                .stream()
                .map(userMapper::mapUserEntityToUser)
                .collect(Collectors.toList());
    }

    @Override
    public User updateUser(Long userId, User userToUpdate) {
        UserEntity userEntity = userRepository
                .findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User with id: " + userId + " not found."));

        if (userToUpdate.getFirstName() != null) {
            userEntity.setFirstName(userToUpdate.getFirstName());
        }

        if (userToUpdate.getLastName() != null) {
            userEntity.setLastName(userToUpdate.getLastName());
        }

        if (userToUpdate.getUsername() != null) {
            userEntity.setUsername(userToUpdate.getUsername());
        }

        if (userToUpdate.getPassword() != null) {
            userEntity.setPassword(userToUpdate.getPassword());
        }

        if (userToUpdate.getEmail() != null) {
            userEntity.setEmail(userToUpdate.getEmail());
        }

        if (userToUpdate.getPhoneNumber() != null) {
            userEntity.setEmail(userToUpdate.getPhoneNumber());
        }

        UserEntity savedUserEntity = userRepository.save(userEntity);

        return userMapper.mapUserEntityToUser(savedUserEntity);
    }
}
