package com.facugl.ecommerce.server.infrastructure.adapter.output.persistence;

import java.util.List;

import com.facugl.ecommerce.server.application.port.output.RoleOutputPort;
import com.facugl.ecommerce.server.common.PersistenceAdapter;
import com.facugl.ecommerce.server.common.exception.generic.EntityNotFoundException;
import com.facugl.ecommerce.server.domain.model.roles.Role;
import com.facugl.ecommerce.server.infrastructure.adapter.output.persistence.entity.roles.RoleEntity;
import com.facugl.ecommerce.server.infrastructure.adapter.output.persistence.mapper.PersistenceRoleMapper;
import com.facugl.ecommerce.server.infrastructure.adapter.output.persistence.repository.RoleRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@PersistenceAdapter
public class RolePersistenceAdapter implements RoleOutputPort {

    private final RoleRepository roleRepository;

    private final PersistenceRoleMapper roleMapper;

    @Override
    public Role createRole(Role roleToCreate) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void deleteRoleById(Long id) {
        // TODO Auto-generated method stub

    }

    @Override
    public Role findRoleById(Long id) {
        RoleEntity roleEntity = roleRepository
                .findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Role with id: " + id + " not found."));

        return roleMapper.mapRoleEntityToRole(roleEntity);
    }

    @Override
    public Role findRoleByName(String name) {
        RoleEntity roleEntity = roleRepository
                .findByName(name)
                .orElseThrow(() -> new EntityNotFoundException("Role with name: " + name + " not found."));

        return roleMapper.mapRoleEntityToRole(roleEntity);
    }

    @Override
    public List<Role> getAllRoles() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Role updateRole(Long id, Role roleToUpdate) {
        // TODO Auto-generated method stub
        return null;
    }

}
