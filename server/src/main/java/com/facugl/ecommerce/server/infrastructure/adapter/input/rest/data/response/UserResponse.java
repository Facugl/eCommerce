package com.facugl.ecommerce.server.infrastructure.adapter.input.rest.data.response;

import java.util.HashSet;
import java.util.Set;

import com.facugl.ecommerce.server.domain.model.roles.Role;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {

    private Long id;

    private String firstName;

    private String lastName;

    private String username;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;
    
    private String email;
    
    private String phoneNumber;
    
    private Set<Role> roles = new HashSet<>();
    
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private boolean admin;

}
