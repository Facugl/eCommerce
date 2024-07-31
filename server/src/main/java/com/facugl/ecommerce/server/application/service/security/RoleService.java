package com.facugl.ecommerce.server.application.service.security;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.facugl.ecommerce.server.application.port.input.security.roles.CreateRoleUseCase;
import com.facugl.ecommerce.server.application.port.input.security.roles.DeleteRoleUseCase;
import com.facugl.ecommerce.server.application.port.input.security.roles.GetAllRolesUseCase;
import com.facugl.ecommerce.server.application.port.input.security.roles.GetRoleUseCase;
import com.facugl.ecommerce.server.application.port.input.security.roles.UpdateRoleUseCase;
import com.facugl.ecommerce.server.application.port.output.security.RoleOutputPort;
import com.facugl.ecommerce.server.common.UseCase;
import com.facugl.ecommerce.server.domain.model.security.Role;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@UseCase
public class RoleService implements
        CreateRoleUseCase,
        GetRoleUseCase,
        GetAllRolesUseCase,
        UpdateRoleUseCase,
        DeleteRoleUseCase {
    private final RoleOutputPort roleOutputPort;

    @Override
    public Role createRole(Role roleToCreate) {
        return roleOutputPort.createRole(roleToCreate);
    }

    @Transactional(readOnly = true)
    @Override
    public Role getRoleById(Long roleId) {
        return roleOutputPort.findRoleById(roleId);
    }

    @Transactional(readOnly = true)
    @Override
    public Role getRoleByName(String roleName) {
        return roleOutputPort.findRoleByName(roleName);
    }

    @Transactional(readOnly = true)
    @Override
    public List<Role> getAllRoles() {
        return roleOutputPort.getAllRoles();
    }

    @Transactional
    @Override
    public void deleteRoleById(Long roleId) {
        roleOutputPort.deleteRoleById(roleId);
    }

    @Transactional
    @Override
    public Role updateRole(Long roleId, Role roleToUpdate) {
        return roleOutputPort.updateRole(roleId, roleToUpdate);
    }
}
