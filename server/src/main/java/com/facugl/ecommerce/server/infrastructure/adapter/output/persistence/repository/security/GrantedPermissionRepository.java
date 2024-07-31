package com.facugl.ecommerce.server.infrastructure.adapter.output.persistence.repository.security;

import org.springframework.data.jpa.repository.JpaRepository;

import com.facugl.ecommerce.server.infrastructure.adapter.output.persistence.entity.security.GrantedPermissionEntity;

public interface GrantedPermissionRepository extends JpaRepository<GrantedPermissionEntity, Long> {
    boolean existsById(Long id);
}
