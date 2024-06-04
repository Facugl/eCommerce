package com.facugl.ecommerce.server.infrastructure.adapter.output.persistence.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.facugl.ecommerce.server.domain.model.users.User;
import com.facugl.ecommerce.server.infrastructure.adapter.output.persistence.entity.users.UserEntity;

@Mapper(componentModel = "spring")
public interface PersistenceUserMapper {

    @Mapping(target = "id", ignore = true)
    UserEntity mapUserToUserEntity(User user);

    User mapUserEntityToUser(UserEntity userEntity);

}
