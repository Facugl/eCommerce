package com.facugl.ecommerce.server.domain.model.security;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GrantedPermission {
    private Long id;
    private Role role;
    private Operation operation;
}
