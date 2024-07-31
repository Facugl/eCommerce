package com.facugl.ecommerce.server.infrastructure.adapter.output.persistence.mapper.security;

import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.facugl.ecommerce.server.common.mapping.CycleAvoidingMappingContext;
import com.facugl.ecommerce.server.common.mapping.DoIgnore;
import com.facugl.ecommerce.server.domain.model.security.User;
import com.facugl.ecommerce.server.infrastructure.adapter.output.persistence.entity.security.UserEntity;

@Mapper(componentModel = "spring")
public interface PersistenceUserMapper {

    @Mapping(target = "id", ignore = true)
    UserEntity mapUserToUserEntity(User user, @Context CycleAvoidingMappingContext cycleAvoidingMappingContext);

    @Mapping(target = "repeatedPassword", ignore = true)
    User mapUserEntityToUser(UserEntity userEntity, @Context CycleAvoidingMappingContext cycleAvoidingMappingContext);

    @DoIgnore
    default UserEntity mapUserToUserEntity(User user) {
        return mapUserToUserEntity(user, new CycleAvoidingMappingContext());
    }

    @DoIgnore
    default User mapUserEntityToUser(UserEntity userEntity) {
        return mapUserEntityToUser(userEntity, new CycleAvoidingMappingContext());
    }

}
