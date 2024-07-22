package com.facugl.ecommerce.server.infrastructure.adapter.output.persistence.repository.security;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.facugl.ecommerce.server.infrastructure.adapter.output.persistence.entity.security.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    @Query("SELECT COUNT(u) = 0 FROM UserEntity u WHERE LOWER(u.username) = LOWER(:username)")
    boolean isUsernameUnique(@Param("username") String username);

    @Query("SELECT u FROM UserEntity u WHERE LOWER(u.username) = LOWER(:username)")
    Optional<UserEntity> findByUsername(@Param("username") String username);
}
