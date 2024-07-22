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
public class User {
    private Long id;
    private String firstName;
    private String lastName;
    private String username;
    private String password;
    private String repeatedPassword;
    private String email;
    private String phoneNumber;
    private Role role;
}
