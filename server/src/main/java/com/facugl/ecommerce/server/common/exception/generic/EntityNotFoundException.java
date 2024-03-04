package com.facugl.ecommerce.server.common.exception.generic;

public class EntityNotFoundException extends RuntimeException {
    public EntityNotFoundException(String message) {
        super(message);
    }
}
