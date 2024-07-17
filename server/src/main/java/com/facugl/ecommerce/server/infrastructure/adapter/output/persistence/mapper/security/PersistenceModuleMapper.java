package com.facugl.ecommerce.server.infrastructure.adapter.output.persistence.mapper.security;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.facugl.ecommerce.server.domain.model.security.Module;
import com.facugl.ecommerce.server.infrastructure.adapter.output.persistence.entity.security.ModuleEntity;

@Mapper(componentModel = "spring")
public interface PersistenceModuleMapper {

    @Mapping(target = "id", ignore = true)
    ModuleEntity mapModuleToModuleEntity(Module module);

    Module mapModuleEntityToModule(ModuleEntity moduleEntity);

}
