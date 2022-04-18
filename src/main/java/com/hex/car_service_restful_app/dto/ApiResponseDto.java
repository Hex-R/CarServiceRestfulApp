package com.hex.car_service_restful_app.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ApiResponseDto {

    private String token;

    private Object data;

    public ApiResponseDto(String token){
        this.token = token;
    }

    public ApiResponseDto(String token, Object data){
        this.token = token;
        this.data = data;
    }
}
