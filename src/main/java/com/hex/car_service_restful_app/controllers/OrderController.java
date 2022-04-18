package com.hex.car_service_restful_app.controllers;

import com.hex.car_service_restful_app.dto.ApiResponseDto;
import com.hex.car_service_restful_app.entities.Order;
import com.hex.car_service_restful_app.entities.User;
import com.hex.car_service_restful_app.services.OrderService;
import com.hex.car_service_restful_app.services.TokenService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Tag(name = "Order controller",
        description = "Create, get, update or delete current user's orders")
@RestController
@RequestMapping("/api/v1/orders/")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    private final TokenService tokenService;

    @Operation(summary = "Create new order")
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public ApiResponseDto create(@RequestBody @Valid Order order,
                                 @AuthenticationPrincipal User user) {

        orderService.save(order, user);
        return new ApiResponseDto(tokenService.createNewToken(user));
    }

    @Operation(summary = "Returns all orders of current user")
    @GetMapping
    public ApiResponseDto getAll(@AuthenticationPrincipal User user) {
        return new ApiResponseDto(tokenService.createNewToken(user), orderService.getAll(user));
    }

    @Operation(summary = "Returns only active orders of current user")
    @GetMapping("active")
    public ApiResponseDto getActive(@AuthenticationPrincipal User user) {
        return new ApiResponseDto(tokenService.createNewToken(user), orderService.getActive(user));
    }

    @Operation(summary = "Returns only completed orders of current user")
    @GetMapping("completed")
    public ApiResponseDto getCompleted(@AuthenticationPrincipal User user) {
        return new ApiResponseDto(tokenService.createNewToken(user), orderService.getCompleted(user));
    }

    @Operation(summary = "Update order")
    @PutMapping("{id}")
    public ApiResponseDto edit(@PathVariable String id,
                               @RequestBody @Valid Order order,
                               @AuthenticationPrincipal User user) {

        orderService.edit(id, order);
        return new ApiResponseDto(tokenService.createNewToken(user));
    }

    @Operation(summary = "Delete order")
    @DeleteMapping("{id}")
    public ApiResponseDto delete(@PathVariable String id,
                                 @AuthenticationPrincipal User user) {
        orderService.delete(id);
        return new ApiResponseDto(tokenService.createNewToken(user));
    }
}
