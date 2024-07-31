package com.facugl.ecommerce.server.infrastructure.adapter.input.rest.advice.generic;

public class SelfParentCategoryException extends RuntimeException {
    public SelfParentCategoryException(String message) {
        super(message);
    }
}
