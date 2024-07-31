package com.facugl.ecommerce.server.infrastructure.adapter.output.persistence.repository.security;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.facugl.ecommerce.server.infrastructure.adapter.output.persistence.entity.security.ModuleEntity;

public interface ModuleRepository extends JpaRepository<ModuleEntity, Long> {
    @Query("SELECT COUNT(m) = 0 FROM ModuleEntity m WHERE LOWER(m.name) = LOWER(:moduleName)")
    boolean isNameUnique(@Param("moduleName") String moduleName);
}
