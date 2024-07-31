package com.facugl.ecommerce.server.infrastructure.adapter.input.rest.advice.generic;

public class EntityAlreadyExistsException extends RuntimeException {
    public EntityAlreadyExistsException(String message) {
        super(message);
    }
}
