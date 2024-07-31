package com.facugl.ecommerce.server.application.port.input.security.roles;

import java.util.List;

import com.facugl.ecommerce.server.domain.model.security.Role;

public interface GetAllRolesUseCase {
    List<Role> getAllRoles();
}
