package com.facugl.ecommerce.server.application.mapper.security;

import org.mapstruct.Mapper;

import com.facugl.ecommerce.server.domain.model.security.Operation;
import com.facugl.ecommerce.server.infrastructure.adapter.input.rest.data.response.security.OperationResponse;

@Mapper(componentModel = "spring")
public interface ApplicationOperationMapper {

    OperationResponse mapOperationToOperationResponse(Operation operation);

}
