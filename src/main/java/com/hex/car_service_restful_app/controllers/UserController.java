package com.hex.car_service_restful_app.controllers;

import com.hex.car_service_restful_app.dto.AuthenticationRequestDto;
import com.hex.car_service_restful_app.dto.UserDto;
import com.hex.car_service_restful_app.entities.User;
import com.hex.car_service_restful_app.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/users/")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("registration")
    public UserDto getForm() {
        return new UserDto();
    }

    @PostMapping("registration")
    public ResponseEntity<?> createUser(@RequestBody @Valid User user){

        userService.createUser(user);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping("login")
    public ResponseEntity login(@RequestBody AuthenticationRequestDto requestDto) {

        String token = userService.login(requestDto);

        return ResponseEntity.ok(Map.of("username", requestDto.getUsername(), "token", token));
    }

    @GetMapping("update")
    public UserDto getCurrentUser(@AuthenticationPrincipal User user) {

        return new UserDto(user);
    }

    @PutMapping("update")
    public void updateCurrentUser(@RequestBody @Valid UserDto updatedUser,
                           @AuthenticationPrincipal User currentUser) {

        userService.updateCurrentUser(updatedUser, currentUser);
    }

    @DeleteMapping("{id}")
    public void deleteUser(@PathVariable String id) {
        userService.deleteUser(id);
    }
}
