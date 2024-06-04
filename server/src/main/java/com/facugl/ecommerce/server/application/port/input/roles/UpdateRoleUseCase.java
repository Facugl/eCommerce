package com.facugl.ecommerce.server.application.port.input.roles;

import com.facugl.ecommerce.server.domain.model.roles.Role;

public interface UpdateRoleUseCase {
    Role updateRole(Long id, Role roleToUpdate);
}
