package com.facugl.ecommerce.server.infrastructure.adapter.output.persistence;

import java.util.List;
import java.util.stream.Collectors;

import com.facugl.ecommerce.server.application.port.output.UserOutputPort;
import com.facugl.ecommerce.server.common.PersistenceAdapter;
import com.facugl.ecommerce.server.common.exception.generic.EntityAlreadyExistsException;
import com.facugl.ecommerce.server.common.exception.generic.EntityNotFoundException;
import com.facugl.ecommerce.server.domain.model.users.User;
import com.facugl.ecommerce.server.infrastructure.adapter.output.persistence.entity.users.UserEntity;
import com.facugl.ecommerce.server.infrastructure.adapter.output.persistence.mapper.PersistenceUserMapper;
import com.facugl.ecommerce.server.infrastructure.adapter.output.persistence.repository.UserRepository;

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
    public void deleteUserById(Long id) {
        // TODO Auto-generated method stub

    }

    @Override
    public User findUserById(Long id) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public User findUserByUsername(String username) {
        return userRepository
                .findByUsername(username)
                .map(userMapper::mapUserEntityToUser)
                .orElseThrow(() -> new EntityNotFoundException("User with username " + username + " not found."));
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
    public User updateUser(Long id, User userToUpdate) {
        // TODO Auto-generated method stub
        return null;
    }

}
