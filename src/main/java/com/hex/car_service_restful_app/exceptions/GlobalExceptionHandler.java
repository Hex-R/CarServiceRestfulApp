package com.hex.car_service_restful_app.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ValidationErrorResponse onMethodArgumentNotValidException(MethodArgumentNotValidException exception) {

        Map<String, String> details = new HashMap<>();
        BindingResult bindingResult = exception.getBindingResult();

        for (FieldError fieldError : bindingResult.getFieldErrors()) {
            details.put(fieldError.getField(), fieldError.getDefaultMessage());
        }

        return new ValidationErrorResponse(HttpStatus.BAD_REQUEST.value(), LocalDateTime.now(),
                "Validation error", details);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    ValidationErrorResponse onConstraintValidationException(ConstraintViolationException exception) {

        Map<String, String> details = new HashMap<>();

        for (ConstraintViolation violation : exception.getConstraintViolations()) {
            details.put(violation.getPropertyPath().toString(), violation.getMessage());
        }

        return new ValidationErrorResponse(HttpStatus.BAD_REQUEST.value(), LocalDateTime.now(),
                "Validation error", details);
    }

    @ExceptionHandler(PasswordConfirmationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ValidationErrorResponse onPasswordConfirmationException(PasswordConfirmationException exception) {

        return new ValidationErrorResponse(HttpStatus.BAD_REQUEST.value(),
                LocalDateTime.now(), "Validation error",
                Map.of("passwordConfirmation", "Пароли не совпадают"));
    }

    @ExceptionHandler(PasswordIncorrectException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ValidationErrorResponse onPasswordIncorrectException(PasswordIncorrectException exception) {

        return new ValidationErrorResponse(HttpStatus.BAD_REQUEST.value(),
                LocalDateTime.now(), "Validation error",
                Map.of("password", "Необходимо 6 - 30 символов"));
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ValidationErrorResponse onIllegalArgumentException(IllegalArgumentException exception) {

        return new ValidationErrorResponse(HttpStatus.BAD_REQUEST.value(),
                LocalDateTime.now(), exception.getMessage(), null);
    }

    /*@ExceptionHandler(JwtAuthenticationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ValidationErrorResponse onJwtAuthenticationException(JwtAuthenticationException exception) {

        return new ValidationErrorResponse(HttpStatus.BAD_REQUEST.value(),
                LocalDateTime.now(), exception.getMessage(), null);
    }*/
}
