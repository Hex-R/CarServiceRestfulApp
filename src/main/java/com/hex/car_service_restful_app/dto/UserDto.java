package com.hex.car_service_restful_app.dto;

import com.hex.car_service_restful_app.entities.User;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
public class UserDto {

    private String username;

    private String password;

    private String passwordConfirmation;

    @Email(message = "Введите корректный адрес почты")
    @NotBlank(message = "Email не может быть пустым")
    @Size(max = 50, message = "Максимум 50 символов")
    private String email;

    @NotBlank(message = "Номер телефона не может быть пустым")
    @Size(min = 7, max = 12, message = "Необходимо 7 - 12 знаков без пробелов и скобок")
    private String phoneNumber;

    public UserDto(User user) {
        this.setEmail(user.getEmail());
        this.setPhoneNumber(user.getPhoneNumber());
    }
}
