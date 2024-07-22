package com.facugl.ecommerce.server.application.port.output.security;

import java.util.List;

import com.facugl.ecommerce.server.domain.model.security.Role;

public interface RoleOutputPort {

    Role createRole(Role roleToCreate);

    Role findRoleById(Long roleId);

    Role findRoleByName(String roleName);

    List<Role> getAllRoles();

    Role updateRole(Long roleId, Role roleToUpdate);

    void deleteRoleById(Long roleId);

}
