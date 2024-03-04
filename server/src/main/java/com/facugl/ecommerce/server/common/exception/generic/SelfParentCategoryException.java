package com.facugl.ecommerce.server.common.exception.generic;

public class SelfParentCategoryException extends RuntimeException {
    public SelfParentCategoryException(String message) {
        super(message);
    }
}
