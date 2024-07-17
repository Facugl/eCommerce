package com.facugl.ecommerce.server.infrastructure.adapter.output.persistence.repository.security;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.facugl.ecommerce.server.infrastructure.adapter.output.persistence.entity.security.OperationEntity;

public interface OperationRepository extends JpaRepository<OperationEntity, Long> {
    @Query("SELECT COUNT(o) = 0 FROM OperationEntity o WHERE LOWER(o.name) = LOWER(:name)")
    boolean isNameUnique(@Param("name") String name);

    @Query("SELECT o FROM OperationEntity o WHERE o.permitAll = true")
    List<OperationEntity> findByPublicAccess();
}
