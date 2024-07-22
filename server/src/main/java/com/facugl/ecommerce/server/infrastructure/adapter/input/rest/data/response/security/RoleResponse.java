package com.facugl.ecommerce.server.infrastructure.adapter.input.rest.data.response.security;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RoleResponse implements Serializable {

    private Long id;
    private String name;

    @JsonIgnoreProperties("role")
    private List<GrantedPermissionResponse> permissions;

}
