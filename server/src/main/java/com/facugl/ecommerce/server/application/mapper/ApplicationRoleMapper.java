package com.facugl.ecommerce.server.application.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.facugl.ecommerce.server.domain.model.roles.Role;
import com.facugl.ecommerce.server.infrastructure.adapter.input.rest.data.request.RoleRequest;
import com.facugl.ecommerce.server.infrastructure.adapter.input.rest.data.response.RoleResponse;

@Mapper(componentModel = "spring")
public interface ApplicationRoleMapper {

    @Mapping(target = "id", ignore = true)
    Role mapRoleRequestToRole(RoleRequest role);

    RoleResponse mapRoleToRoleResponse(Role role);

}
