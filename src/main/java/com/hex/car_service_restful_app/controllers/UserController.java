package com.hex.car_service_restful_app.controllers;

import com.hex.car_service_restful_app.dto.AuthenticationRequestDto;
import com.hex.car_service_restful_app.dto.UserRegistrationDto;
import com.hex.car_service_restful_app.dto.UserUpdateDto;
import com.hex.car_service_restful_app.entities.User;
import com.hex.car_service_restful_app.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Tag(name = "User controller",
        description = "Registration, authorization and operations with user data")
@RestController
@RequestMapping("/api/v1/users/")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @Operation(summary = "Register new user")
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("registration")
    public void createUser(@RequestBody @Valid UserRegistrationDto userDto){
        userService.createUser(userDto);
    }

    @Operation(summary = "Login with username and password. Returns JWT token")
    @PostMapping("login")
    public String login(@RequestBody AuthenticationRequestDto requestDto) {
        return userService.login(requestDto);
    }

    @Operation(summary = "Returns current user data with empty password." +
            " Can be used for update user's profile")
    @GetMapping("update")
    public UserUpdateDto getCurrentUser(@AuthenticationPrincipal User user) {
        return new UserUpdateDto(user);
    }

    @Operation(summary = "Update data of current user")
    @PutMapping("update")
    public void updateCurrentUser(@RequestBody @Valid UserUpdateDto updatedUser,
                                  @AuthenticationPrincipal User currentUser) {

        userService.updateCurrentUser(updatedUser, currentUser);
    }

    @Operation(summary = "Delete user. Requires 'ADMIN' authority")
    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("{id}")
    public void deleteUser(@PathVariable String id) {
        userService.deleteUser(id);
    }
}
