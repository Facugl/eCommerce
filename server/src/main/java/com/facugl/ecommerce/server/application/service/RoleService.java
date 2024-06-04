package com.facugl.ecommerce.server.application.service;

import org.springframework.transaction.annotation.Transactional;

import com.facugl.ecommerce.server.application.port.input.roles.GetRoleUseCase;
import com.facugl.ecommerce.server.application.port.output.RoleOutputPort;
import com.facugl.ecommerce.server.common.UseCase;
import com.facugl.ecommerce.server.domain.model.roles.Role;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@UseCase
public class RoleService implements GetRoleUseCase {

    private final RoleOutputPort roleOutputPort;

    @Transactional(readOnly = true)
    @Override
    public Role getRoleById(Long id) {
        return roleOutputPort.findRoleById(id);
    }
    
    @Transactional(readOnly = true)
    @Override
    public Role getRoleByName(String name) {
        return roleOutputPort.findRoleByName(name);
    }

}
