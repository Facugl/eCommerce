package com.facugl.ecommerce.server.application.port.input.security.grantedPermissions;

public interface DeleteGrantedPermissionUseCase {
    void deleteGrantedPermissionById(Long permissionId);
}
