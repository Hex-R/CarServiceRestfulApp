package com.hex.car_service_restful_app.controllers;

import com.hex.car_service_restful_app.dto.OrderDto;
import com.hex.car_service_restful_app.entities.User;
import com.hex.car_service_restful_app.services.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Tag(name = "Order controller",
        description = "Operations with orders of current user. " +
                "executionDate format: dd-MM-yyyy HH:mm " +
                "In services list you can send service's id instead of all object's fields.")
@RestController
@RequestMapping("/api/v1/orders/")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @Operation(summary = "Create new order for current user")
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public void create(@RequestBody @Valid OrderDto orderDto,
                       @AuthenticationPrincipal User user) {

        orderService.save(orderDto, user);
    }

    @Operation(summary = "Returns all orders of current user")
    @GetMapping
    public List<OrderDto> getAll(@AuthenticationPrincipal User user) {
        return orderService.getAll(user);
    }

    @Operation(summary = "Returns only active orders of current user")
    @GetMapping("active")
    public List<OrderDto> getActive(@AuthenticationPrincipal User user) {
        return orderService.getActive(user);
    }

    @Operation(summary = "Returns only completed orders of current user")
    @GetMapping("completed")
    public List<OrderDto> getCompleted(@AuthenticationPrincipal User user) {
        return orderService.getCompleted(user);
    }

    @Operation(summary = "Update order of current user")
    @PutMapping("{id}")
    public void edit(@PathVariable("id") String orderId,
                     @RequestBody @Valid OrderDto updatedOrder,
                     @AuthenticationPrincipal User user) {

        orderService.edit(orderId, updatedOrder, user);
    }

    @Operation(summary = "Delete order of current user")
    @DeleteMapping("{id}")
    public void delete(@PathVariable("id") String orderId,
                       @AuthenticationPrincipal User user) {

        orderService.delete(orderId, user);
    }
}
