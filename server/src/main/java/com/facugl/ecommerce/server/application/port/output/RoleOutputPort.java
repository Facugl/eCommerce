package com.facugl.ecommerce.server.application.port.output;

import java.util.List;

import com.facugl.ecommerce.server.domain.model.roles.Role;

public interface RoleOutputPort {

    Role createRole(Role roleToCreate);

    Role findRoleById(Long id);

    Role findRoleByName(String name);

    List<Role> getAllRoles();

    Role updateRole(Long id, Role roleToUpdate);

    void deleteRoleById(Long id);

}
