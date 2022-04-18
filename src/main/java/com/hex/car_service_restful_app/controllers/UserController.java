package com.hex.car_service_restful_app.controllers;

import com.hex.car_service_restful_app.dto.ApiResponseDto;
import com.hex.car_service_restful_app.dto.AuthenticationRequestDto;
import com.hex.car_service_restful_app.dto.UserRegistrationDto;
import com.hex.car_service_restful_app.dto.UserUpdateDto;
import com.hex.car_service_restful_app.entities.User;
import com.hex.car_service_restful_app.services.TokenService;
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

    private final TokenService tokenService;

    @Operation(summary = "Register new user")
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("registration")
    public void createUser(@RequestBody @Valid UserRegistrationDto userDto){
        userService.createUser(userDto);
    }

    @Operation(summary = "Login with username and password. Returns username and JWT token")
    @PostMapping("login")
    public ApiResponseDto login(@RequestBody AuthenticationRequestDto requestDto) {
        return new ApiResponseDto(userService.login(requestDto));
    }

    @Operation(summary = "Returns current user data with empty password." +
            " Can be used for update user's profile")
    @GetMapping("update")
    public ApiResponseDto getCurrentUser(@AuthenticationPrincipal User user) {
        return new ApiResponseDto(tokenService.createNewToken(user), new UserUpdateDto(user));
    }

    @Operation(summary = "Update current user's data")
    @PutMapping("update")
    public ApiResponseDto updateCurrentUser(@RequestBody @Valid UserUpdateDto updatedUser,
                                            @AuthenticationPrincipal User currentUser) {

        userService.updateCurrentUser(updatedUser, currentUser);
        return new ApiResponseDto(tokenService.createNewToken(currentUser));
    }

    @Operation(summary = "Delete user. Requires 'ADMIN' authority")
    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("{id}")
    public ApiResponseDto deleteUser(@PathVariable String id,
                                     @AuthenticationPrincipal User user) {

        userService.deleteUser(id);
        return new ApiResponseDto(tokenService.createNewToken(user));
    }
}
