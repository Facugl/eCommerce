package com.facugl.ecommerce.server.infrastructure.adapter.input.rest.data.request.security;

import java.io.Serializable;

import com.facugl.ecommerce.server.infrastructure.adapter.input.rest.validation.groups.security.users.CreateUserValidationGroup;
import com.facugl.ecommerce.server.infrastructure.adapter.input.rest.validation.groups.security.users.UpdateUserValidationGroup;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserRequest implements Serializable {

    @NotBlank(message = "First name can not be null or empty.", groups = { CreateUserValidationGroup.class })
    @Size(max = 20, message = "First name must be at most 20 characters.", groups = {
            CreateUserValidationGroup.class, UpdateUserValidationGroup.class })
    private String firstName;

    @NotBlank(message = "Last name can not be null or empty.", groups = { CreateUserValidationGroup.class })
    @Size(max = 20, message = "Last name must be at most 20 characters.", groups = {
            CreateUserValidationGroup.class, UpdateUserValidationGroup.class })
    private String lastName;

    @NotBlank(message = "Username can not be null or empty.", groups = { CreateUserValidationGroup.class })
    @Size(min = 4, max = 20, message = "Username must be between 4 and 20 characters.", groups = {
            CreateUserValidationGroup.class, UpdateUserValidationGroup.class })
    private String username;

    @NotBlank(message = "Password can not be null or empty.", groups = { CreateUserValidationGroup.class })
    @Size(min = 8, max = 60, message = "Password must be at least 8 characters long.", groups = {
            CreateUserValidationGroup.class, UpdateUserValidationGroup.class })
    private String password;

    @NotBlank(message = "Repeated password can not be null or empty.", groups = { CreateUserValidationGroup.class })
    @Size(min = 8, max = 60, message = "Password must be at least 8 characters long.", groups = {
            CreateUserValidationGroup.class, UpdateUserValidationGroup.class })
    private String repeatedPassword;

    @NotBlank(message = "Email can not be null or empty.", groups = { CreateUserValidationGroup.class })
    @Size(max = 250, message = "Email must be at most 250 characters.", groups = {
            CreateUserValidationGroup.class, UpdateUserValidationGroup.class })
    private String email;

    @NotBlank(message = "Phone number can not be null or empty.", groups = { CreateUserValidationGroup.class })
    @Size(max = 15, message = "Phone number must be at most 15 characters.", groups = {
            CreateUserValidationGroup.class, UpdateUserValidationGroup.class })
    private String phoneNumber;

}
