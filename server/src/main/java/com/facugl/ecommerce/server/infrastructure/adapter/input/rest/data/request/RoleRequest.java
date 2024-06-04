package com.facugl.ecommerce.server.infrastructure.adapter.input.rest.data.request;

import com.facugl.ecommerce.server.infrastructure.adapter.input.rest.validation.groups.roles.CreateRoleValidationGroup;
import com.facugl.ecommerce.server.infrastructure.adapter.input.rest.validation.groups.roles.UpdateRoleValidationGroup;

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
public class RoleRequest {

    @NotBlank(message = "Name shouldn't be null or empty", groups = { CreateRoleValidationGroup.class })
    @Size(max = 60, message = "Name must be at most 60 characters", groups = { CreateRoleValidationGroup.class,
            UpdateRoleValidationGroup.class })
    private String name;

}
