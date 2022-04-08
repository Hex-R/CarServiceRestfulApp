package com.hex.car_service_restful_app.exceptions;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Map;

@Data
public class ValidationErrorDetails {

    private int httpStatus;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm:ss dd-MM-yyyy")
    private LocalDateTime timestamp;

    private String message;

    private Map<String, String> details;

    public ValidationErrorDetails(int httpStatus, LocalDateTime timestamp, String message, Map<String, String> details) {
        super();
        this.httpStatus = httpStatus;
        this.timestamp = timestamp;
        this.message = message;
        this.details = details;
    }
}
