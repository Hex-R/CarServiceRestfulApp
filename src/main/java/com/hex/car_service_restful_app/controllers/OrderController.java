package com.hex.car_service_restful_app.controllers;

import com.hex.car_service_restful_app.entities.Order;
import com.hex.car_service_restful_app.entities.User;
import com.hex.car_service_restful_app.services.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/orders/")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<?> create(@RequestBody @Valid Order order,
                                    @AuthenticationPrincipal User user) {

        orderService.save(order, user);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping
    public List<Order> getAll(@AuthenticationPrincipal User user) {
        return orderService.getAll(user);
    }

    @GetMapping("active")
    public List<Order> getActive(@AuthenticationPrincipal User user) {
        return orderService.getActive(user);
    }

    @GetMapping("completed")
    public List<Order> getCompleted(@AuthenticationPrincipal User user) {
        return orderService.getCompleted(user);
    }

    @PutMapping("{id}")
    public void edit(@PathVariable String id,
                     @RequestBody @Valid Order order) {

        orderService.edit(id, order);
    }


    @DeleteMapping("{id}")
    public void delete(@PathVariable String id) {
        orderService.delete(id);
    }
}
