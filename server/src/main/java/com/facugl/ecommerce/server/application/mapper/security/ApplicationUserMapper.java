package com.facugl.ecommerce.server.application.mapper.security;

import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.facugl.ecommerce.server.common.mapping.CycleAvoidingMappingContext;
import com.facugl.ecommerce.server.common.mapping.DoIgnore;
import com.facugl.ecommerce.server.domain.model.security.User;
import com.facugl.ecommerce.server.infrastructure.adapter.input.rest.data.request.security.UserRequest;
import com.facugl.ecommerce.server.infrastructure.adapter.input.rest.data.response.security.UserResponse;

@Mapper(componentModel = "spring")
public interface ApplicationUserMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "role", ignore = true)
    User mapUserRequestToUser(UserRequest user,
            @Context CycleAvoidingMappingContext cycleAvoidingMappingContext);

    @Mapping(target = "role", source = "role.name")
    @Mapping(target = "jwt", ignore = true)
    UserResponse mapUserToUserResponse(User user,
            @Context CycleAvoidingMappingContext cycleAvoidingMappingContext);

    @DoIgnore
    default User mapUserRequestToUser(UserRequest user) {
        return mapUserRequestToUser(user, new CycleAvoidingMappingContext());
    }

    @DoIgnore
    default UserResponse mapUserToUserResponse(User user) {
        return mapUserToUserResponse(user, new CycleAvoidingMappingContext());
    }

}
