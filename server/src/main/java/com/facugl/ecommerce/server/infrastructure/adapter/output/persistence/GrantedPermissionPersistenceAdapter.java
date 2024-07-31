package com.facugl.ecommerce.server.infrastructure.adapter.output.persistence;

import java.util.List;
import java.util.stream.Collectors;

import com.facugl.ecommerce.server.application.port.output.security.GrantedPermissionOutputPort;
import com.facugl.ecommerce.server.common.PersistenceAdapter;
import com.facugl.ecommerce.server.domain.model.security.GrantedPermission;
import com.facugl.ecommerce.server.infrastructure.adapter.input.rest.advice.generic.EntityNotFoundException;
import com.facugl.ecommerce.server.infrastructure.adapter.output.persistence.entity.security.GrantedPermissionEntity;
import com.facugl.ecommerce.server.infrastructure.adapter.output.persistence.entity.security.RoleEntity;
import com.facugl.ecommerce.server.infrastructure.adapter.output.persistence.mapper.security.PersistenceGrantedPermissionMapper;
import com.facugl.ecommerce.server.infrastructure.adapter.output.persistence.repository.security.GrantedPermissionRepository;
import com.facugl.ecommerce.server.infrastructure.adapter.output.persistence.repository.security.RoleRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@PersistenceAdapter
public class GrantedPermissionPersistenceAdapter implements GrantedPermissionOutputPort {
	private final GrantedPermissionRepository grantedPermissionRepository;
	private final RoleRepository roleRepository;
	private final PersistenceGrantedPermissionMapper grantedPermissionMapper;

	@Override
	public GrantedPermission createGrantedPermission(GrantedPermission grantedPermissionToCreate) {
		GrantedPermissionEntity grantedPermissionEntity = grantedPermissionMapper
				.mapGrantedPermissionToGrantedPermissionEntity(grantedPermissionToCreate);

		Long roleId = grantedPermissionEntity.getRole().getId();
		RoleEntity roleEntity = roleRepository
				.findById(roleId)
				.orElseThrow(() -> new EntityNotFoundException("Role with id: " + roleId + " not found."));
		grantedPermissionEntity.setRole(roleEntity);

		GrantedPermissionEntity savedGrantedPermissionEntity = grantedPermissionRepository
				.save(grantedPermissionEntity);

		return grantedPermissionMapper.mapGrantedPermissionEntityToGrantedPermission(savedGrantedPermissionEntity);
	}

	@Override
	public void deleteGrantedPermissionById(Long permissionId) {
		GrantedPermissionEntity grantedPermissionEntity = grantedPermissionRepository
				.findById(permissionId)
				.orElseThrow(() -> new EntityNotFoundException(
						"Granted permission with id: " + permissionId + " not found."));

		grantedPermissionRepository.delete(grantedPermissionEntity);
	}

	@Override
	public GrantedPermission findGrantedPermissionById(Long permissionId) {
		return grantedPermissionRepository
				.findById(permissionId)
				.map(grantedPermissionMapper::mapGrantedPermissionEntityToGrantedPermission)
				.orElseThrow(() -> new EntityNotFoundException(
						"Granted permission with id: " + permissionId + " not found."));
	}

	@Override
	public List<GrantedPermission> getAllGrantedPermissions() {
		return grantedPermissionRepository
				.findAll()
				.stream()
				.map(grantedPermissionMapper::mapGrantedPermissionEntityToGrantedPermission)
				.collect(Collectors.toList());
	}
}
