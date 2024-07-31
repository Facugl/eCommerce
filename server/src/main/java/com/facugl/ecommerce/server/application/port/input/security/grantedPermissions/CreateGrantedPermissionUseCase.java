package com.facugl.ecommerce.server.application.port.input.security.grantedPermissions;

import com.facugl.ecommerce.server.domain.model.security.GrantedPermission;

public interface CreateGrantedPermissionUseCase {
    GrantedPermission createGrantedPermission(GrantedPermission grantedPermissionToCreate);
}
