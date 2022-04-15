package com.hex.car_service_restful_app.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class UserExistsException extends RuntimeException {

    public UserExistsException() {
        super("User with this username already exists");
    }
}
