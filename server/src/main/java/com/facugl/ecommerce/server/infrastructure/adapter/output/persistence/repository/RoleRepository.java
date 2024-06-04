package com.facugl.ecommerce.server.infrastructure.adapter.output.persistence.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.facugl.ecommerce.server.infrastructure.adapter.output.persistence.entity.roles.RoleEntity;

public interface RoleRepository extends JpaRepository<RoleEntity, Long> {

    Optional<RoleEntity> findByName(String name);

}
