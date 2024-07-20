package com.facugl.ecommerce.server.infrastructure.adapter.input.rest.data.response.security;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GrantedPermissionResponse implements Serializable {
    private Long id;
    private RoleResponse role;
    private OperationResponse operation;
}
