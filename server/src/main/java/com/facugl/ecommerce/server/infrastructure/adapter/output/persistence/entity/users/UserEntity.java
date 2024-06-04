package com.facugl.ecommerce.server.infrastructure.adapter.output.persistence.entity.users;

import java.util.HashSet;
import java.util.Set;

import com.facugl.ecommerce.server.infrastructure.adapter.output.persistence.entity.roles.RoleEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import jakarta.persistence.UniqueConstraint;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Entity
@Table(name = "users")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "first_name", nullable = false)
    private String firstName;
    
    @Column(name = "last_name", nullable = false)
    private String lastName;
    
    @Column(name = "username", nullable = false, unique = true)
    private String username;
    
    @Column(name = "password", nullable = false)
    private String password;
    
    @Column(name = "email", nullable = false, unique = true)
    private String email;
    
    @Column(name = "phone_number", nullable = false)
    private String phoneNumber;

    @ManyToMany
    @JoinTable(
        name="users_roles",
        joinColumns= @JoinColumn(name="users_id"),
        inverseJoinColumns= @JoinColumn(name="roles_id"),
        uniqueConstraints={@UniqueConstraint(columnNames = {"users_id", "roles_id"})}
    )
    private Set<RoleEntity> roles = new HashSet<>();

    @Transient
    private boolean admin;

}
