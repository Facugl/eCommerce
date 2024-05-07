package com.facugl.ecommerce.server.common.exception;

import org.springframework.http.HttpStatus;

import lombok.Data;

@Data
public class ErrorMessage {

    private String timestamp;

    private String message;

    private HttpStatus status;

}
