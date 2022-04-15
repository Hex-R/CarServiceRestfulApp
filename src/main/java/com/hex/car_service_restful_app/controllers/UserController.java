package com.hex.car_service_restful_app.controllers;

import com.fasterxml.jackson.annotation.JsonView;
import com.hex.car_service_restful_app.dto.AuthenticationRequestDto;
import com.hex.car_service_restful_app.entities.User;
import com.hex.car_service_restful_app.entities.Views;
import com.hex.car_service_restful_app.exceptions.PasswordConfirmationException;
import com.hex.car_service_restful_app.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @JsonView(Views.RegistrationForm.class)
    @GetMapping("registration")
    public User getForm() {
        return new User();
    }

    @PostMapping("registration")
    public void createUser(@RequestBody @Valid User user){

        if (!user.getPassword().equals(user.getPasswordConfirmation())){
            throw new PasswordConfirmationException();
        }

        userService.addUser(user);
    }

    @PostMapping("login")
    public ResponseEntity login(@RequestBody AuthenticationRequestDto requestDto) {

        String token = userService.loginUser(requestDto);

        return ResponseEntity.ok(Map.of("username", requestDto.getUsername(), "token", token));
    }

    @PutMapping("update")
    public void updateUser(@RequestBody @Valid User user) {


    }
}
