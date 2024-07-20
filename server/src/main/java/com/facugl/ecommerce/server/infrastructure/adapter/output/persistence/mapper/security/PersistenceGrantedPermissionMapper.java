package com.facugl.ecommerce.server.infrastructure.adapter.output.persistence.mapper.security;

import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.facugl.ecommerce.server.common.mapping.CycleAvoidingMappingContext;
import com.facugl.ecommerce.server.common.mapping.DoIgnore;
import com.facugl.ecommerce.server.domain.model.security.GrantedPermission;
import com.facugl.ecommerce.server.infrastructure.adapter.output.persistence.entity.security.GrantedPermissionEntity;

@Mapper(componentModel = "spring")
public interface PersistenceGrantedPermissionMapper {

    @Mapping(target = "id", ignore = true)
    GrantedPermissionEntity mapGrantedPermissionToGrantedPermissionEntity(GrantedPermission grantedPermission,
            @Context CycleAvoidingMappingContext cycleAvoidingMappingContext);

    GrantedPermission mapGrantedPermissionEntityToGrantedPermission(GrantedPermissionEntity grantedPermissionEntity,
            @Context CycleAvoidingMappingContext cycleAvoidingMappingContext);

    @DoIgnore
    default GrantedPermissionEntity mapGrantedPermissionToGrantedPermissionEntity(GrantedPermission grantedPermission) {
        return mapGrantedPermissionToGrantedPermissionEntity(grantedPermission, new CycleAvoidingMappingContext());
    }

    @DoIgnore
    default GrantedPermission mapGrantedPermissionEntityToGrantedPermission(
            GrantedPermissionEntity grantedPermissionEntity) {
        return mapGrantedPermissionEntityToGrantedPermission(grantedPermissionEntity,
                new CycleAvoidingMappingContext());
    }

}
