package com.facugl.ecommerce.server.application.port.output.security;

import java.util.List;

import com.facugl.ecommerce.server.domain.model.security.GrantedPermission;

public interface GrantedPermissionOutputPort {

    GrantedPermission createGrantedPermission(GrantedPermission grantedPermissionToCreate);

    GrantedPermission findGrantedPermissionById(Long permissionId);

    List<GrantedPermission> getAllGrantedPermissions();

    void deleteGrantedPermissionById(Long permissionId);

}
