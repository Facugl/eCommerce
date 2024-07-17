package com.facugl.ecommerce.server.infrastructure.adapter.output.persistence.mapper.security;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.facugl.ecommerce.server.domain.model.security.Operation;
import com.facugl.ecommerce.server.infrastructure.adapter.output.persistence.entity.security.OperationEntity;

@Mapper(componentModel = "spring")
public interface PersistenceOperationMapper {

    @Mapping(target = "id", ignore = true)
    OperationEntity mapOperationToOperationEntity(Operation operation);

    Operation mapOperationEntityToOperation(OperationEntity operationEntity);

}
