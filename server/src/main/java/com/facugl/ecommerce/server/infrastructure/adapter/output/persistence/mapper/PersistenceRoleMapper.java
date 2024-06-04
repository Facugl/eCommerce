package com.facugl.ecommerce.server.infrastructure.adapter.output.persistence.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.facugl.ecommerce.server.domain.model.roles.Role;
import com.facugl.ecommerce.server.infrastructure.adapter.output.persistence.entity.roles.RoleEntity;

@Mapper(componentModel = "spring")
public interface PersistenceRoleMapper {

    @Mapping(target = "id", ignore = true)
    RoleEntity mapRoleToRoleEntity(Role role);

    Role mapRoleEntityToRole(RoleEntity roleEntity);

}
