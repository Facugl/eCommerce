package com.facugl.ecommerce.server.application.mapper.security;

import org.mapstruct.Context;
import org.mapstruct.Mapper;

import com.facugl.ecommerce.server.common.mapping.CycleAvoidingMappingContext;
import com.facugl.ecommerce.server.common.mapping.DoIgnore;
import com.facugl.ecommerce.server.domain.model.security.GrantedPermission;
import com.facugl.ecommerce.server.infrastructure.adapter.input.rest.data.response.security.GrantedPermissionResponse;

@Mapper(componentModel = "spring")
public interface ApplicationGrantedPermissionMapper {

    GrantedPermissionResponse mapGrantedPermissionToGrantedPermissionResponse(GrantedPermission grantedPermission,
            @Context CycleAvoidingMappingContext cycleAvoidingMappingContext);

    @DoIgnore
    default GrantedPermissionResponse mapGrantedPermissionToGrantedPermissionResponse(
            GrantedPermission grantedPermission) {
        return mapGrantedPermissionToGrantedPermissionResponse(grantedPermission, new CycleAvoidingMappingContext());
    }

}
