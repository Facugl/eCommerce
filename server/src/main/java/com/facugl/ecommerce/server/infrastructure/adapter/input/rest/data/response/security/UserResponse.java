package com.facugl.ecommerce.server.infrastructure.adapter.input.rest.data.response.security;

import java.io.Serializable;

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
public class UserResponse implements Serializable {
    private Long id;
    private String firstName;
    private String lastName;
    private String username;
    private String email;    
    private String phoneNumber;    
    private String role;
    private String jwt;
}
