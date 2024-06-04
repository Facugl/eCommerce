package com.facugl.ecommerce.server.application.port.input.roles;

import com.facugl.ecommerce.server.domain.model.roles.Role;

public interface CreateRoleUseCase {
    Role createRole(Role roleToCreate);
}
