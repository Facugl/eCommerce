package com.facugl.ecommerce.server.domain.model.users;

import java.util.HashSet;
import java.util.Set;

import com.facugl.ecommerce.server.domain.model.roles.Role;

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
public class User {

    private Long id;

    private String firstName;

    private String lastName;

    private String username;

    private String password;

    private String email;

    private String phoneNumber;

    private Set<Role> roles = new HashSet<>();

    private boolean admin;

}
