package com.facugl.ecommerce.server.application.port.input.roles;

import java.util.List;

import com.facugl.ecommerce.server.domain.model.roles.Role;

public interface GetAllRolesUseCase {
    List<Role> getAllRoles();
}
