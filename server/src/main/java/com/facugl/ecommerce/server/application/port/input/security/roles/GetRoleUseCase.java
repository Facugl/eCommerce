package com.facugl.ecommerce.server.application.port.input.security.roles;

import com.facugl.ecommerce.server.domain.model.security.Role;

public interface GetRoleUseCase {

    Role getRoleById(Long id);

    Role getRoleByName(String name);

}
