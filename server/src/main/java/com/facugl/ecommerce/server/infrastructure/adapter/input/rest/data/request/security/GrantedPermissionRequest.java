package com.facugl.ecommerce.server.infrastructure.adapter.input.rest.data.request.security;

import java.io.Serializable;

import com.facugl.ecommerce.server.infrastructure.adapter.input.rest.validation.groups.security.grantedPermissions.CreateGrantedPermissionValidateGroup;
import com.facugl.ecommerce.server.infrastructure.adapter.input.rest.validation.groups.security.grantedPermissions.UpdateGrantedPermissionValidateGroup;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GrantedPermissionRequest implements Serializable {

    @NotNull(message = "roleId can not be null.", groups = {
            CreateGrantedPermissionValidateGroup.class })
    @Positive(message = "roleId must be a positive number.", groups = {
            CreateGrantedPermissionValidateGroup.class, UpdateGrantedPermissionValidateGroup.class })
    private Long roleId;

    @NotNull(message = "operationId can not be null.", groups = {
            CreateGrantedPermissionValidateGroup.class })
    @Positive(message = "operationId must be a positive number.", groups = {
            CreateGrantedPermissionValidateGroup.class, UpdateGrantedPermissionValidateGroup.class })
    private Long operationId;

}
