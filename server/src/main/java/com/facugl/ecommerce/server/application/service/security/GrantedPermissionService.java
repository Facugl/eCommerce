package com.facugl.ecommerce.server.application.service.security;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.facugl.ecommerce.server.application.port.input.security.grantedPermissions.CreateGrantedPermissionUseCase;
import com.facugl.ecommerce.server.application.port.input.security.grantedPermissions.DeleteGrantedPermissionUseCase;
import com.facugl.ecommerce.server.application.port.input.security.grantedPermissions.GetAllGrantedPermissionUseCase;
import com.facugl.ecommerce.server.application.port.input.security.grantedPermissions.GetGrantedPermissionUseCase;
import com.facugl.ecommerce.server.application.port.output.security.GrantedPermissionOutputPort;
import com.facugl.ecommerce.server.application.port.output.security.OperationOutputPort;
import com.facugl.ecommerce.server.application.port.output.security.RoleOutputPort;
import com.facugl.ecommerce.server.common.UseCase;
import com.facugl.ecommerce.server.domain.model.security.GrantedPermission;
import com.facugl.ecommerce.server.domain.model.security.GrantedPermission.GrantedPermissionBuilder;
import com.facugl.ecommerce.server.domain.model.security.Operation;
import com.facugl.ecommerce.server.domain.model.security.Role;
import com.facugl.ecommerce.server.infrastructure.adapter.input.rest.data.request.security.GrantedPermissionRequest;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@UseCase
public class GrantedPermissionService implements
        CreateGrantedPermissionUseCase,
        GetGrantedPermissionUseCase,
        GetAllGrantedPermissionUseCase,
        DeleteGrantedPermissionUseCase {
    private final GrantedPermissionOutputPort grantedPermissionOutputPort;
    private final RoleOutputPort roleOutputPort;
    private final OperationOutputPort operationOutputPort;

    @Transactional
    @Override
    public GrantedPermission createGrantedPermission(GrantedPermission grantedPermissionToCreate) {
        return grantedPermissionOutputPort.createGrantedPermission(grantedPermissionToCreate);
    }

    @Transactional(readOnly = true)
    @Override
    public GrantedPermission getGrantedPermissionById(Long permissionId) {
        return grantedPermissionOutputPort.findGrantedPermissionById(permissionId);
    }

    @Transactional(readOnly = true)
    @Override
    public List<GrantedPermission> getAllGrantedPermissions() {
        return grantedPermissionOutputPort.getAllGrantedPermissions();
    }

    @Transactional
    @Override
    public void deleteGrantedPermissionById(Long permissionId) {
        grantedPermissionOutputPort.deleteGrantedPermissionById(permissionId);
    }

    @Transactional
    public GrantedPermission mapGrantedPermissionRequestToGrantedPermission(
            GrantedPermissionRequest grantedPermissionRequest) {
        GrantedPermissionBuilder grantedPermission = GrantedPermission.builder();

        if (grantedPermissionRequest.getRoleId() != null) {
            Role role = roleOutputPort.findRoleById(grantedPermissionRequest.getRoleId());
            grantedPermission.role(role);
        }

        if (grantedPermissionRequest.getOperationId() != null) {
            Operation operation = operationOutputPort.findOperationById(grantedPermissionRequest.getOperationId());
            grantedPermission.operation(operation);
        }

        return grantedPermission.build();
    }
}
