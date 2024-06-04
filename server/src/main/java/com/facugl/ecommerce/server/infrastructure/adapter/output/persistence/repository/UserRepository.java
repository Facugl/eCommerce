package com.facugl.ecommerce.server.infrastructure.adapter.output.persistence.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.facugl.ecommerce.server.infrastructure.adapter.output.persistence.entity.users.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, Long> {

    @Query("SELECT COUNT(u) = 0 FROM UserEntity u WHERE LOWER(u.username) = LOWER(:username)")
    boolean isUsernameUnique(@Param("username") String username);

    Optional<UserEntity> findByUsername(String username);

}
