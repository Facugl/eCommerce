package com.facugl.ecommerce.server.application.port.input.roles;

import com.facugl.ecommerce.server.domain.model.roles.Role;

public interface GetRoleUseCase {

    Role getRoleById(Long id);

    Role getRoleByName(String name);

}
