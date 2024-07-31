package com.facugl.ecommerce.server.infrastructure.adapter.input.rest.advice.generic;

public class InvalidPasswordException extends RuntimeException {
    public InvalidPasswordException() {

    }

    public InvalidPasswordException(String message) {
        super(message);
    }

    public InvalidPasswordException(String message, Throwable cause) {
        super(message, cause);
    }
}
