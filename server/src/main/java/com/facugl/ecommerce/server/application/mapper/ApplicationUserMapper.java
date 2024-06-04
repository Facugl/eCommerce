package com.facugl.ecommerce.server.application.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.facugl.ecommerce.server.domain.model.users.User;
import com.facugl.ecommerce.server.infrastructure.adapter.input.rest.data.request.UserRequest;
import com.facugl.ecommerce.server.infrastructure.adapter.input.rest.data.response.UserResponse;

@Mapper(componentModel = "spring")
public interface ApplicationUserMapper {

    @Mapping(target = "id", ignore = true)
    User mapUserRequestToUser(UserRequest user);

    UserResponse mapUserToUserResponse(User user);

}
