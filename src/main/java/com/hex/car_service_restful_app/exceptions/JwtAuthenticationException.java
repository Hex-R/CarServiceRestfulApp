package com.hex.car_service_restful_app.exceptions;

import org.springframework.security.core.AuthenticationException;

public class JwtAuthenticationException extends AuthenticationException {

    public JwtAuthenticationException(String message, Throwable t) {
        super(message, t);
    }

    public JwtAuthenticationException(String message) {
        super(message);
    }
}
