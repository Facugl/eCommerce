package com.facugl.ecommerce.server.infrastructure.adapter.output.persistence.repository.security;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.facugl.ecommerce.server.infrastructure.adapter.output.persistence.entity.security.RoleEntity;

public interface RoleRepository extends JpaRepository<RoleEntity, Long> {
    @Query("SELECT COUNT(r) = 0 FROM RoleEntity r WHERE LOWER(r.name) = LOWER(:roleName)")
    boolean isNameUnique(@Param("roleName") String roleName);

    Optional<RoleEntity> findByName(String roleName);
}
