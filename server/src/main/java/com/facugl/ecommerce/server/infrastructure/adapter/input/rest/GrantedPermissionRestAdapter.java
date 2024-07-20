package com.facugl.ecommerce.server.infrastructure.adapter.input.rest;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.facugl.ecommerce.server.application.mapper.security.ApplicationGrantedPermissionMapper;
import com.facugl.ecommerce.server.application.port.input.security.grantedPermissions.CreateGrantedPermissionUseCase;
import com.facugl.ecommerce.server.application.port.input.security.grantedPermissions.DeleteGrantedPermissionUseCase;
import com.facugl.ecommerce.server.application.port.input.security.grantedPermissions.GetAllGrantedPermissionUseCase;
import com.facugl.ecommerce.server.application.port.input.security.grantedPermissions.GetGrantedPermissionUseCase;
import com.facugl.ecommerce.server.application.service.security.GrantedPermissionService;
import com.facugl.ecommerce.server.common.WebAdapter;
import com.facugl.ecommerce.server.domain.model.security.GrantedPermission;
import com.facugl.ecommerce.server.infrastructure.adapter.input.rest.data.request.security.GrantedPermissionRequest;
import com.facugl.ecommerce.server.infrastructure.adapter.input.rest.data.response.security.GrantedPermissionResponse;
import com.facugl.ecommerce.server.infrastructure.adapter.input.rest.validation.groups.security.grantedPermissions.CreateGrantedPermissionValidateGroup;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("/granted-permissions")
@RestController
@WebAdapter
public class GrantedPermissionRestAdapter {
	private final ApplicationGrantedPermissionMapper grantedPermissionMapper;
	private final CreateGrantedPermissionUseCase createGrantedPermissionUseCase;
	private final GetGrantedPermissionUseCase getGrantedPermissionUseCase;
	private final GetAllGrantedPermissionUseCase getAllGrantedPermissionUseCase;
	private final DeleteGrantedPermissionUseCase deleteGrantedPermissionUseCase;
	private final GrantedPermissionService grantedPermissionService;

	@PostMapping
	public ResponseEntity<GrantedPermissionResponse> createGrantedPermission(
			@Validated(CreateGrantedPermissionValidateGroup.class) @RequestBody GrantedPermissionRequest grantedPermissionToCreate) {
		GrantedPermission grantedPermission = grantedPermissionService
				.mapGrantedPermissionRequestToGrantedPermission(grantedPermissionToCreate);

		GrantedPermission createdGrantedPermission = createGrantedPermissionUseCase
				.createGrantedPermission(grantedPermission);

		return ResponseEntity
				.status(HttpStatus.CREATED)
				.body(grantedPermissionMapper
						.mapGrantedPermissionToGrantedPermissionResponse(createdGrantedPermission));
	}

	@GetMapping("/{id}")
	public ResponseEntity<GrantedPermissionResponse> getOneGrantedPermission(@PathVariable Long id) {
		GrantedPermission grantedPermission = getGrantedPermissionUseCase.getGrantedPermissionById(id);

		return ResponseEntity
				.status(HttpStatus.OK)
				.body(grantedPermissionMapper
						.mapGrantedPermissionToGrantedPermissionResponse(grantedPermission));
	}

	@GetMapping
	public ResponseEntity<List<GrantedPermissionResponse>> getAllGrantedPermissions() {
		List<GrantedPermissionResponse> grantedPermissions = getAllGrantedPermissionUseCase
				.getAllGrantedPermissions()
				.stream()
				.map(grantedPermissionMapper::mapGrantedPermissionToGrantedPermissionResponse)
				.collect(Collectors.toList());

		return ResponseEntity
				.status(HttpStatus.OK)
				.body(grantedPermissions);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteGrantedPermissionById(@PathVariable Long id) {
		deleteGrantedPermissionUseCase.deleteGrantedPermissionById(id);

		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}
}
