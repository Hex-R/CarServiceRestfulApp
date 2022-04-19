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
        description = "Create, get, update or delete current user's orders")
@RestController
@RequestMapping("/api/v1/orders/")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @Operation(summary = "Create new order")
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

    @Operation(summary = "Update order")
    @PutMapping("{id}")
    public void edit(@PathVariable String orderId,
                     @RequestBody @Valid OrderDto updatedOrder,
                     @AuthenticationPrincipal User user) {

        orderService.edit(orderId, updatedOrder, user);
    }

    @Operation(summary = "Delete order")
    @DeleteMapping("{id}")
    public void delete(@PathVariable String orderId,
                       @AuthenticationPrincipal User user) {

        orderService.delete(orderId, user);
    }
}
