package com.facugl.ecommerce.server.infrastructure.adapter.output.persistence;

import java.util.List;
import java.util.stream.Collectors;

import com.facugl.ecommerce.server.application.port.output.security.RoleOutputPort;
import com.facugl.ecommerce.server.common.PersistenceAdapter;
import com.facugl.ecommerce.server.domain.model.security.Role;
import com.facugl.ecommerce.server.infrastructure.adapter.input.rest.advice.generic.EntityAlreadyExistsException;
import com.facugl.ecommerce.server.infrastructure.adapter.input.rest.advice.generic.EntityNotFoundException;
import com.facugl.ecommerce.server.infrastructure.adapter.output.persistence.entity.security.RoleEntity;
import com.facugl.ecommerce.server.infrastructure.adapter.output.persistence.mapper.security.PersistenceGrantedPermissionMapper;
import com.facugl.ecommerce.server.infrastructure.adapter.output.persistence.mapper.security.PersistenceRoleMapper;
import com.facugl.ecommerce.server.infrastructure.adapter.output.persistence.repository.security.RoleRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@PersistenceAdapter
public class RolePersistenceAdapter implements RoleOutputPort {
    private final RoleRepository roleRepository;
    private final PersistenceRoleMapper roleMapper;
    private final PersistenceGrantedPermissionMapper grantedPermissionMapper;

    @Override
    public Role createRole(Role roleToCreate) {
        if (roleRepository.isNameUnique(roleToCreate.getName())) {
            RoleEntity roleEntity = roleMapper.mapRoleToRoleEntity(roleToCreate);

            RoleEntity savedRoleEntity = roleRepository.save(roleEntity);

            return roleMapper.mapRoleEntityToRole(savedRoleEntity);
        } else {
            throw new EntityAlreadyExistsException(
                    "Role with name: " + roleToCreate.getName() + " already exists.");
        }
    }

    @Override
    public void deleteRoleById(Long roleId) {
        RoleEntity roleEntity = roleRepository
                .findById(roleId)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Role with id: " + roleId + " not found."));

        roleRepository.delete(roleEntity);
    }

    @Override
    public Role findRoleById(Long roleId) {
        RoleEntity roleEntity = roleRepository
                .findById(roleId)
                .orElseThrow(() -> new EntityNotFoundException("Role with id: " + roleId + " not found."));

        return roleMapper.mapRoleEntityToRole(roleEntity);
    }

    @Override
    public Role findRoleByName(String roleName) {
        RoleEntity roleEntity = roleRepository
                .findByName(roleName)
                .orElseThrow(() -> new EntityNotFoundException("Role with name: " + roleName + " not found."));

        return roleMapper.mapRoleEntityToRole(roleEntity);
    }

    @Override
    public List<Role> getAllRoles() {
        return roleRepository
                .findAll()
                .stream()
                .map(roleMapper::mapRoleEntityToRole)
                .collect(Collectors.toList());

    }

    @Override
    public Role updateRole(Long roleId, Role roleToUpdate) {
        RoleEntity roleEntity = roleRepository
                .findById(roleId)
                .orElseThrow(() -> new EntityNotFoundException("Role with id: " + roleId + " not found."));

        if (roleToUpdate.getName() != null) {
            roleEntity.setName(roleToUpdate.getName());
        }

        if (roleToUpdate.getPermissions() != null) {
            roleEntity.setPermissions(
                    roleToUpdate.getPermissions().stream()
                            .map(grantedPermissionMapper::mapGrantedPermissionToGrantedPermissionEntity)
                            .collect(Collectors.toList()));
        }

        RoleEntity savedRoleEntity = roleRepository.save(roleEntity);

        return roleMapper.mapRoleEntityToRole(savedRoleEntity);
    }
}
