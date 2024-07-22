package com.facugl.ecommerce.server.application.port.input.security.roles;

import com.facugl.ecommerce.server.domain.model.security.Role;

public interface CreateRoleUseCase {
    Role createRole(Role roleToCreate);
}
