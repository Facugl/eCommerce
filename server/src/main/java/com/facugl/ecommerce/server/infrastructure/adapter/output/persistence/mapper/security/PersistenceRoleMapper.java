package com.facugl.ecommerce.server.infrastructure.adapter.output.persistence.mapper.security;

import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.facugl.ecommerce.server.common.mapping.CycleAvoidingMappingContext;
import com.facugl.ecommerce.server.common.mapping.DoIgnore;
import com.facugl.ecommerce.server.domain.model.security.Role;
import com.facugl.ecommerce.server.infrastructure.adapter.output.persistence.entity.security.RoleEntity;

@Mapper(componentModel = "spring")
public interface PersistenceRoleMapper {

    @Mapping(target = "id", ignore = true)
    RoleEntity mapRoleToRoleEntity(Role role, @Context CycleAvoidingMappingContext cycleAvoidingMappingContext);

    Role mapRoleEntityToRole(RoleEntity roleEntity, @Context CycleAvoidingMappingContext cycleAvoidingMappingContext);

    @DoIgnore
    default RoleEntity mapRoleToRoleEntity(Role role) {
        return mapRoleToRoleEntity(role, new CycleAvoidingMappingContext());
    }

    @DoIgnore
    default Role mapRoleEntityToRole(RoleEntity roleEntity) {
        return mapRoleEntityToRole(roleEntity, new CycleAvoidingMappingContext());
    }

}
