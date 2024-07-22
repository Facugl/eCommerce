package com.facugl.ecommerce.server.application.port.input.security.roles;

import com.facugl.ecommerce.server.domain.model.security.Role;

public interface UpdateRoleUseCase {
    Role updateRole(Long id, Role roleToUpdate);
}
