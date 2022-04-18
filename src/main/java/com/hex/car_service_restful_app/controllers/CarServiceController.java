package com.hex.car_service_restful_app.controllers;

import com.hex.car_service_restful_app.dto.ApiResponseDto;
import com.hex.car_service_restful_app.entities.CarService;
import com.hex.car_service_restful_app.entities.User;
import com.hex.car_service_restful_app.services.CarServiceService;
import com.hex.car_service_restful_app.services.TokenService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Tag(name = "Car service controller",
        description = "Get all services or services with selected type." +
                " Create, update and delete operations require 'ADMIN' authority")
@RestController
@RequestMapping("/api/v1/services/")
@RequiredArgsConstructor
public class CarServiceController {

    private final CarServiceService carServiceService;

    private final TokenService tokenService;

    @Operation(summary = "Returns all car services from database")
    @GetMapping
    public ApiResponseDto getAll(@AuthenticationPrincipal User user) {
        return new ApiResponseDto(tokenService.createNewToken(user), carServiceService.getAll());
    }

    @Operation(summary = "Returns car services of selected type." +
            " Type is enum, so write it with upper case chars")
    @GetMapping("{type}")
    public ApiResponseDto getByType(@PathVariable String type,
                                    @AuthenticationPrincipal User user) {
        return new ApiResponseDto(tokenService.createNewToken(user), carServiceService.getByType(type));
    }

    @Operation(summary = "Add new car service to database. Requires 'ADMIN' authority")
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping
    public ApiResponseDto add(@RequestBody @Valid CarService carService,
                              @AuthenticationPrincipal User user) {

        carServiceService.add(carService);
        return new ApiResponseDto(tokenService.createNewToken(user));
    }

    //@PathVariable("id") Message messageFromDB Получать сразу объект из базы по id
    @Operation(summary = "Update car service. Requires 'ADMIN' authority")
    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("{id}")
    public ApiResponseDto edit(@PathVariable String id,
                     @RequestBody @Valid CarService carService,
                     @AuthenticationPrincipal User user) {

        carServiceService.edit(id, carService);
        return new ApiResponseDto(tokenService.createNewToken(user));
    }

    @Operation(summary = "Delete car service. Requires 'ADMIN' authority")
    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("{id}")
    public ApiResponseDto delete(@PathVariable String id,
                       @AuthenticationPrincipal User user) {

        carServiceService.delete(id);
        return new ApiResponseDto(tokenService.createNewToken(user));
    }
}
