package com.facugl.ecommerce.server.application.mapper.security;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.facugl.ecommerce.server.domain.model.security.Module;
import com.facugl.ecommerce.server.infrastructure.adapter.input.rest.data.request.security.ModuleRequest;
import com.facugl.ecommerce.server.infrastructure.adapter.input.rest.data.response.security.ModuleResponse;

@Mapper(componentModel = "spring")
public interface ApplicationModuleMapper {

    @Mapping(target = "id", ignore = true)
    Module mapModuleRequestToModule(ModuleRequest module);

    ModuleResponse mapModuleToModuleResponse(Module module);

}
