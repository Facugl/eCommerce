package com.facugl.ecommerce.server.infrastructure.adapter.input.rest.advice.generic;

public class EntityNotFoundException extends RuntimeException {
    public EntityNotFoundException(String message) {
        super(message);
    }
}
