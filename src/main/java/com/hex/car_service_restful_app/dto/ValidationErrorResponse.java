package com.hex.car_service_restful_app.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Map;

@Data
public class ValidationErrorResponse {

    private int httpStatus;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm:ss dd-MM-yyyy")
    private LocalDateTime timestamp;

    private String message;

    private Map<String, String> details;

    public ValidationErrorResponse(int httpStatus, LocalDateTime timestamp, String message, Map<String, String> details) {
        super();
        this.httpStatus = httpStatus;
        this.timestamp = timestamp;
        this.message = message;
        this.details = details;
    }
}
