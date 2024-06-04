package com.facugl.ecommerce.server.infrastructure.adapter.input.rest.data.request;

import com.facugl.ecommerce.server.infrastructure.adapter.input.rest.validation.groups.users.CreateUserValidationGroup;
import com.facugl.ecommerce.server.infrastructure.adapter.input.rest.validation.groups.users.UpdateUserValidationGroup;

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
public class UserRequest {

    @NotBlank(message = "Firstname can not be null or empty", groups = { CreateUserValidationGroup.class })
    @Size(max = 20, message = "Firstname must be at most 20 characters", groups = {
            CreateUserValidationGroup.class, UpdateUserValidationGroup.class })
    private String firstName;

    @NotBlank(message = "Lastname can not be null or empty", groups = { CreateUserValidationGroup.class })
    @Size(max = 20, message = "Lastname must be at most 20 characters", groups = {
            CreateUserValidationGroup.class, UpdateUserValidationGroup.class })
    private String lastName;

    @NotBlank(message = "Username can not be null or empty", groups = { CreateUserValidationGroup.class })
    @Size(min = 4, max = 20, message = "Name must be between 4 and 20 characters", groups = {
            CreateUserValidationGroup.class, UpdateUserValidationGroup.class })
    private String username;

    @NotBlank(message = "Password can not be null or empty", groups = { CreateUserValidationGroup.class })
    @Size(max = 60, message = "Password must be at most 60 characters", groups = {
            CreateUserValidationGroup.class, UpdateUserValidationGroup.class })
    private String password;

    @NotBlank(message = "Email can not be null or empty", groups = { CreateUserValidationGroup.class })
    @Size(max = 250, message = "Email must be at most 250 characters", groups = {
            CreateUserValidationGroup.class, UpdateUserValidationGroup.class })
    private String email;

    @NotBlank(message = "Phone number can not be null or empty", groups = { CreateUserValidationGroup.class })
    @Size(max = 15, message = "Phone number must be at most 15 characters", groups = {
            CreateUserValidationGroup.class, UpdateUserValidationGroup.class })
    private String phoneNumber;

    private boolean admin;

}
