package com.facugl.ecommerce.server.common.exception;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;

import lombok.Data;

@Data
public class ErrorMessage {

    private LocalDateTime date;

    private String message;

    private HttpStatus status;

}
