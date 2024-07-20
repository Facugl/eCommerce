package com.facugl.ecommerce.server.application.port.input.security.grantedPermissions;

import java.util.List;

import com.facugl.ecommerce.server.domain.model.security.GrantedPermission;

public interface GetAllGrantedPermissionUseCase {
    List<GrantedPermission> getAllGrantedPermissions();
}
