package com.hex.car_service_restful_app.dto;

import lombok.Data;

@Data
public class AuthenticationRequestDto {

    private String username;

    private String password;
}
