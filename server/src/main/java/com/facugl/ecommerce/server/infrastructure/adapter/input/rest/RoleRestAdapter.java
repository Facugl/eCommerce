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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.facugl.ecommerce.server.application.mapper.security.ApplicationRoleMapper;
import com.facugl.ecommerce.server.application.port.input.security.roles.CreateRoleUseCase;
import com.facugl.ecommerce.server.application.port.input.security.roles.DeleteRoleUseCase;
import com.facugl.ecommerce.server.application.port.input.security.roles.GetAllRolesUseCase;
import com.facugl.ecommerce.server.application.port.input.security.roles.GetRoleUseCase;
import com.facugl.ecommerce.server.application.port.input.security.roles.UpdateRoleUseCase;
import com.facugl.ecommerce.server.common.WebAdapter;
import com.facugl.ecommerce.server.domain.model.security.Role;
import com.facugl.ecommerce.server.infrastructure.adapter.input.rest.data.request.security.RoleRequest;
import com.facugl.ecommerce.server.infrastructure.adapter.input.rest.data.response.security.RoleResponse;
import com.facugl.ecommerce.server.infrastructure.adapter.input.rest.validation.groups.security.roles.CreateRoleValidationGroup;
import com.facugl.ecommerce.server.infrastructure.adapter.input.rest.validation.groups.security.roles.UpdateRoleValidationGroup;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("/roles")
@RestController
@WebAdapter
public class RoleRestAdapter {
    private final ApplicationRoleMapper roleMapper;
    private final CreateRoleUseCase createRoleUseCase;
    private final GetRoleUseCase getRoleUseCase;
    private final GetAllRolesUseCase getAllRolesUseCase;
    private final UpdateRoleUseCase updateRoleUseCase;
    private final DeleteRoleUseCase deleteRoleUseCase;

    @PostMapping
    public ResponseEntity<RoleResponse> createRole(
            @Validated(CreateRoleValidationGroup.class) @RequestBody RoleRequest roleToCreate) {
        Role role = roleMapper.mapRoleRequestToRole(roleToCreate);

        Role createdRole = createRoleUseCase.createRole(role);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(roleMapper.mapRoleToRoleResponse(createdRole));
    }

    @GetMapping("/{id}")
    public ResponseEntity<RoleResponse> getOneRole(@PathVariable Long id) {
        Role role = getRoleUseCase.getRoleById(id);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(roleMapper.mapRoleToRoleResponse(role));
    }

    @GetMapping
    public ResponseEntity<List<RoleResponse>> getAllRoles() {
        List<RoleResponse> roles = getAllRolesUseCase.getAllRoles()
                .stream()
                .map(roleMapper::mapRoleToRoleResponse)
                .collect(Collectors.toList());

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(roles);
    }

    @PutMapping("/{id}")
    public ResponseEntity<RoleResponse> updateRole(
            @PathVariable Long id,
            @Validated(UpdateRoleValidationGroup.class) @RequestBody RoleRequest roleToUpdate) {
        Role role = roleMapper.mapRoleRequestToRole(roleToUpdate);

        Role updatedRole = updateRoleUseCase.updateRole(id, role);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(roleMapper.mapRoleToRoleResponse(updatedRole));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRole(@PathVariable Long id) {
        deleteRoleUseCase.deleteRoleById(id);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
