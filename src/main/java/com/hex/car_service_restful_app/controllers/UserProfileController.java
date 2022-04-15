package com.hex.car_service_restful_app.controllers;

import com.fasterxml.jackson.annotation.JsonView;
import com.hex.car_service_restful_app.entities.User;
import com.hex.car_service_restful_app.entities.Views;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/profile")
@RequiredArgsConstructor
public class UserProfileController {

    @JsonView(Views.UpdateProfile.class)
    @GetMapping
    public User getUser(@AuthenticationPrincipal User user) {

        user.setPassword("");
        user.setPasswordConfirmation("");

        return user;
    }

    @PutMapping
    public void updateUser(@RequestBody @Valid User user) {


    }
}
