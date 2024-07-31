package com.facugl.ecommerce.server.application.mapper.security;

import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.facugl.ecommerce.server.common.mapping.CycleAvoidingMappingContext;
import com.facugl.ecommerce.server.common.mapping.DoIgnore;
import com.facugl.ecommerce.server.domain.model.security.Role;
import com.facugl.ecommerce.server.infrastructure.adapter.input.rest.data.request.security.RoleRequest;
import com.facugl.ecommerce.server.infrastructure.adapter.input.rest.data.response.security.RoleResponse;

@Mapper(componentModel = "spring")
public interface ApplicationRoleMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "permissions", ignore = true)
    Role mapRoleRequestToRole(RoleRequest role, @Context CycleAvoidingMappingContext cycleAvoidingMappingContext);

    RoleResponse mapRoleToRoleResponse(Role role, @Context CycleAvoidingMappingContext cycleAvoidingMappingContext);

    @DoIgnore
    default Role mapRoleRequestToRole(RoleRequest role) {
        return mapRoleRequestToRole(role, new CycleAvoidingMappingContext());
    }

    @DoIgnore
    default RoleResponse mapRoleToRoleResponse(Role role) {
        return mapRoleToRoleResponse(role, new CycleAvoidingMappingContext());
    }

}
