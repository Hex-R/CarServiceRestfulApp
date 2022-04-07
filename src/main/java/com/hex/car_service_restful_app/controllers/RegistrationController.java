package com.hex.car_service_restful_app.controllers;

import com.fasterxml.jackson.annotation.JsonView;
import com.hex.car_service_restful_app.entities.User;
import com.hex.car_service_restful_app.entities.Views;
import com.hex.car_service_restful_app.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/registration")
@RequiredArgsConstructor
public class RegistrationController {

    private final UserService userService;

    @JsonView(Views.RegistrationForm.class)
    @GetMapping
    public User getForm() {
        return new User();
    }

    @PostMapping
    public void processRegistration(@RequestBody @Valid User user){

        boolean isPasswordConfirmationIncorrect = !user.getPassword().equals(user.getPasswordConfirmation());

        userService.addUser(user);
    }
}
